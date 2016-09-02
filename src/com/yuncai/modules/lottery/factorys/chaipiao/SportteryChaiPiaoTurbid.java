package com.yuncai.modules.lottery.factorys.chaipiao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.ticket.SetTicket;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModelTurbid;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItemTurbid;
import com.yuncai.modules.lottery.sporttery.support.PassMode;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.SportteryTicketModelTurbid;
import com.yuncai.modules.lottery.sporttery.support.ExtensionCombCallBack;
import com.yuncai.modules.lottery.sporttery.support.ExtensionMathUtils;

public abstract class SportteryChaiPiaoTurbid<T extends SportteryBetContentModelTurbid<X> , X extends MatchItemTurbid> implements ChaiPiao {

	private static final int AMOUNT = 2; // 一注的价钱

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List chaiPiao(final LotteryPlan lotteryPlan) throws Exception {
		List<Ticket> ticketList = getBaseTicketList(lotteryPlan);
		List<Ticket> tempticketList = new ArrayList<Ticket>();
		// 处理倍数
		for (Ticket t : ticketList) {
			// 找到最接近2万元的倍数
			int tempMaxBeishu = 20000 / (t.getAmount() / t.getMultiple());
			if (tempMaxBeishu > 99)// 如果倍数大于99
				tempMaxBeishu = 99;
			// 实际的倍数与最接近2万元的倍数比较
			if (tempMaxBeishu > t.getMultiple())
				tempticketList.add(t);
			else {
				List<Ticket> tT = new ArrayList<Ticket>();
				tT.add(t);
				tT = SetTicket.processBeishu(tT, t.getMultiple(), tempMaxBeishu);
				tempticketList.addAll(tT);
			}
		}
		return tempticketList;
	}

	public class SetTicketModel implements Serializable {
		private static final long serialVersionUID = 1L;
		private SportteryTicketModelTurbid<X> tiketModel;
		private LotteryPlan lotteryPlan;

		public SetTicketModel(SportteryTicketModelTurbid<X> tiketModel, LotteryPlan lotteryPlan) {
			this.tiketModel = tiketModel;
			this.lotteryPlan = lotteryPlan;
		}

		public Ticket getTicket() {
			Ticket ticket = new Ticket();
			int amount = tiketModel.countUnit() * AMOUNT;
			amount *= tiketModel.getMultiple();
			ticket.setAmount(amount);
			ticket.setContent(JSONObject.fromObject(tiketModel).toString());
			ticket.setPlayType(tiketModel.getPassType().getType());
			ticket = SetTicket.setTicket(ticket, lotteryPlan);
			return ticket;
		}
		
		public Ticket getHtTicket() {
			//如果是去除单一,有是单一的票返回null
			if(tiketModel.isOnlyPlay()){
				boolean onlyPlay = isOnlyPlay();
				if(onlyPlay){
					return null;
				}
			}
			Ticket ticket = new Ticket();
			int amount = tiketModel.countHtUnit() * AMOUNT;
			amount *= tiketModel.getMultiple();
			ticket.setAmount(amount);
			ticket.setContent(JSONObject.fromObject(tiketModel).toString());
			ticket.setPlayType(tiketModel.getPassType().getType());
			ticket = SetTicket.setTicket(ticket, lotteryPlan);
			
			/********混投拆票判断********/
			getHtTicketLotteryType(ticket);
			
			return ticket;
		}
		
		public Ticket getHtTicketLotteryType(Ticket ticket){
			Map<String, Integer> lotteryTypeMap = getLotteryTypeMap();
			for (String key : lotteryTypeMap.keySet()) {
				if (tiketModel.getPassType().getMatchCount() <= lotteryTypeMap.get(key)) {
					ticket.setLotteryType(LotteryType.getItem(Integer.valueOf(key)));
				}
			}
			return ticket;
		}
		
		public boolean isOnlyPlay(){
			Map<String, Integer> lotteryTypeMap = getLotteryTypeMap();
			boolean isOnly = false;
			for (String key : lotteryTypeMap.keySet()) {
				if (tiketModel.getPassType().getMatchCount() <= lotteryTypeMap.get(key)) {
					isOnly = true;
				}
			}
			return isOnly;
		}
		
		public Map<String, Integer> getLotteryTypeMap(){
			Map<String, Integer> lotteryTypeMap = new HashMap<String, Integer>();
			List<X> matchItems = tiketModel.getMatchItems();
			for (X x : matchItems) {
				for (SportteryOption sportteryOption : x.getOptions()) {
					if (lotteryTypeMap.containsKey(sportteryOption.getType())) {
						Integer typeNum = lotteryTypeMap.get(sportteryOption.getType()) + 1;
						lotteryTypeMap.put(sportteryOption.getType(), typeNum);
					} else {
						lotteryTypeMap.put(sportteryOption.getType(), 1);
					}
				}
			}
			return lotteryTypeMap;
		}
	}
	

	public double getBetAmount(final LotteryPlan lotteryPlan) throws Exception {
		List<Ticket> ticketList = getBaseTicketList(lotteryPlan);
		double betAmount = 0;
		for (Ticket ticket : ticketList) {
			betAmount += ticket.getAmount();
		}
		return betAmount;
	}

	public List<Ticket> getBaseTicketList(final LotteryPlan lotteryPlan) throws Exception {
		// 拿出方案，过关方式PASSTYPE和playType要注意，可通过对应的playType,拿到过关方式 方案内容JSON格式如下
		// {"matchItems":[{"DXF":0,"RF":0,"intTime":20100817,"lineId":305,"options":[{"award":1.8,"value":"3"},{"award":2.56,"value":"0"}],"shedan":false},{"DXF":0,"RF":0,"intTime":20100817,"lineId":306,"options":[{"award":2,"value":"3"},{"award":1.55,"value":"0"}],"shedan":false},{"DXF":0,"RF":0,"intTime":20100819,"lineId":301,"options":[{"award":0,"value":"3"},{"award":0,"value":"0"}],"shedan":false}],"multiple":1,"passMode":1,"passTypes":["P2_1","P3_1"]}
		final T model = CommonUtils.Object4JsonTurbid(lotteryPlan.getContent(), getModelClass(), getMatchItemClass());
		final List<X> matchItems = model.getMatchItems();
		final List<Ticket> ticketList = new ArrayList<Ticket>();
		List<SportteryPassType> passTypes = model.getPassTypes();
		if (model.getPassMode().intValue() == PassMode.SINGLE.ordinal()) {// 单关
			//默认6场一票， 配合hongbo ,单关一场一票
			int ct = 1;
			if ( matchItems.size() <= ct) {// 如果不超过6场，就不用拆了,直接设入就行了
				SportteryTicketModelTurbid<X> tiketModel = new SportteryTicketModelTurbid<X>();
				tiketModel.setPassMode(PassMode.SINGLE.ordinal());
				tiketModel.setMultiple(lotteryPlan.getMultiple());
				tiketModel.setPassType(SportteryPassType.P1);
				tiketModel.setMatchItems(matchItems);
				SetTicketModel sm = new SetTicketModel(tiketModel, lotteryPlan);
				ticketList.add(sm.getTicket());
			} else {// 如果超过8场，将对阵选项拆开,每个组合有一张票，注意了！很有可能某一张票没有出成功而导致订单失败，出了999张，第1000张的时候出票方时间截止了
				Map<Integer, List<X>> tempMatchMap = new HashMap<Integer, List<X>>();
				int i = 0;
				for (X x : matchItems) {
					List<X> tempList = tempMatchMap.get(i);
					if (tempList == null)
						tempList = new ArrayList<X>();
					tempList.add(x);
					tempMatchMap.put(i, tempList);
					if (tempList.size() == ct)
						i++;
				}
				for (Integer tempInt : tempMatchMap.keySet()) {
					SportteryTicketModelTurbid<X> tiketModel = new SportteryTicketModelTurbid<X>();
					tiketModel.setPassMode(PassMode.SINGLE.ordinal());
					tiketModel.setMultiple(model.getMultiple());
					tiketModel.setPassType(SportteryPassType.P1);
					tiketModel.setMatchItems(tempMatchMap.get(tempInt));
					SetTicketModel sm = new SetTicketModel(tiketModel, lotteryPlan);
					ticketList.add(sm.getTicket());
				}
			}
		} else {// 过关
			final List<X> shedanList = new ArrayList<X>();
			final List<X> undanList = new ArrayList<X>();
			for (X x : matchItems) {// 找出设胆和非设胆的
				if (x.isShedan())
					shedanList.add(x);
				else
					undanList.add(x);
			}
			
			// 遍历过关方式
			for (final SportteryPassType type : passTypes) {
				int needMatchCount = type.getMatchCount();// 拿到过关的场次数目
				if (shedanList.size() > needMatchCount)
					throw new ServiceException("设胆错误");
				ExtensionMathUtils.efficientCombExtension(needMatchCount, shedanList.size(), undanList.size(), new ExtensionCombCallBack() {
					public void run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
						List<X> tempMatchs = new ArrayList<X>();
						for (int i = 0; i < comb1.length; i++) {
							if (comb1[i])
								tempMatchs.add(shedanList.get(i));
						}
						for (int i = 0; i < comb2.length; i++) {
							if (comb2[i])
								tempMatchs.add(undanList.get(i));
						}
						SportteryTicketModelTurbid<X> tiketModel = new SportteryTicketModelTurbid<X>();
						tiketModel.setPassMode(PassMode.PASS.ordinal());
						tiketModel.setMultiple(lotteryPlan.getMultiple());
						//判断竞彩篮球前2,2串1
//						if(SportteryPassType.Q2_P2_1 == type){
//							tiketModel.setPassType(SportteryPassType.P2_1);
//						}else{
							tiketModel.setPassType(type);
						//}
						tiketModel.setOnlyPlay(false);
						tiketModel.setMatchItems(tempMatchs);
						SetTicketModel sm = new SetTicketModel(tiketModel, lotteryPlan);
						ticketList.add(sm.getTicket());
					}
				});

			}

		}
		return ticketList;
	}

	public abstract Class<T> getModelClass();

	public abstract Class<X> getMatchItemClass();
}

