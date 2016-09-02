package com.yuncai.modules.lottery.factorys.chaipiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yuncai.core.tools.ticket.SetTicket;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.sporttery.model.baskball.BasketBallBetContent;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.ExtensionCombCallBack;
import com.yuncai.modules.lottery.sporttery.support.ExtensionMathUtils;
import com.yuncai.modules.lottery.sporttery.support.PassMode;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.SportteryTicketModelTurbid;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallTicketModel;


public class SportteryBasketHTChaiPiao extends  SportteryChaiPiaoTurbid<BasketBallBetContent, BasketBallMatchItem>{

	@Override
	public Class<BasketBallBetContent> getModelClass() {
		return BasketBallBetContent.class;
	}

	@Override
	public Class<BasketBallMatchItem> getMatchItemClass() {
		return BasketBallMatchItem.class;
	}
	
	@SuppressWarnings("unchecked")
	public List chaiPiao(final LotteryPlan lotteryPlan) throws Exception {
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<Ticket> tempTicketList = getLqHtTicketList(lotteryPlan);
		// 处理倍数
		for (Ticket t : tempTicketList) {
			// 找到最接近2万元的倍数
			int tempMaxBeishu = 20000 / (t.getAmount() / t.getMultiple());
			if (tempMaxBeishu > 99)// 如果倍数大于99
				tempMaxBeishu = 99;
			// 实际的倍数与最接近2万元的倍数比较
			if (tempMaxBeishu > t.getMultiple())
				ticketList.add(t);
			else {
				List<Ticket> tT = new ArrayList<Ticket>();
				tT.add(t);
				tT = SetTicket.processBeishu(tT, t.getMultiple(), tempMaxBeishu);
				ticketList.addAll(tT);
			}
		}
		List<Ticket> tempList = new ArrayList<Ticket>();
		for (Ticket t : ticketList) {
			String content = t.getContent();
			BasketBallTicketModel btm = CommonUtils.Object4TikectJsonTurbid(content, BasketBallTicketModel.class, BasketBallMatchItem.class);
			btm.setMultiple(t.getMultiple());
			JSONObject jsonObject = JSONObject.fromObject(btm);
			t.setContent(jsonObject.toString());
			tempList.add(t);
		}
		return tempList;
	}
	
	public List<Ticket> getLqHtTicketList(final LotteryPlan lotteryPlan) throws Exception {
		final BasketBallBetContent model =CommonUtils.Object4JsonTurbid(lotteryPlan.getContent(), getModelClass(), getMatchItemClass());
		final List<BasketBallMatchItem> matchItems = (List<BasketBallMatchItem>)model.getMatchItems();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<SportteryPassType> passTypes = model.getPassTypes();
		final List<BasketBallMatchItem> unDanList = new ArrayList<BasketBallMatchItem>();
		final List<BasketBallMatchItem> sheDanList =new ArrayList<BasketBallMatchItem>();
		final List<Integer> sheDanMatch = new ArrayList<Integer>();
		int sheDanToal=0;
		for(BasketBallMatchItem x : matchItems){ //找出设胆与非设胆
			List<SportteryOption> sportteryOptionList = new ArrayList<SportteryOption>();
			for(SportteryOption sportteryOption : x.getOptions()){
				sportteryOptionList.add(sportteryOption);
			}
			for(SportteryOption sportteryOption : sportteryOptionList){
				List<SportteryOption> list = new ArrayList<SportteryOption>();
				list.add(sportteryOption);
				
				BasketBallMatchItem bk =new BasketBallMatchItem();
				bk.setIntTime(x.getIntTime());
				bk.setLineId(x.getLineId());
				bk.setOptions(list);
				bk.setShedan(x.isShedan());
				bk.setDXF(x.getDXF());
				bk.setRF(x.getRF());
				if(x.isShedan()){
					sheDanList.add(bk);
				}else{
					unDanList.add(bk);
				}
				
			}
			if(x.isShedan()){
				sheDanMatch.add(sportteryOptionList.size());
				sheDanToal +=sportteryOptionList.size();
			}
		}
		
		if(sheDanMatch.size()>0){
			ticketList = this.getSheDanTicketList(passTypes, sheDanMatch.size(), unDanList.size(), sheDanList, unDanList, lotteryPlan, model, ticketList, sheDanToal);
		}else{
			ticketList = this.getUnDanTicketList(passTypes, sheDanMatch.size(), unDanList.size(), unDanList, lotteryPlan, model, ticketList);
		}
		
		return ticketList;
	}
	
	
	/**
	 * 设胆玩法进行拆分
	 * @param passTypes
	 * @param sheDanSize
	 * @param unDanSize
	 * @param sheDanList
	 * @param unDanList
	 * @param lotteryPlan
	 * @param model
	 * @param ticketList
	 * @param sheDanTotal
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<Ticket> getSheDanTicketList(List<SportteryPassType> passTypes,final int sheDanSize,final int unDanSize,final List<BasketBallMatchItem> sheDanList,
			final List<BasketBallMatchItem> unDanList,final LotteryPlan lotteryPlan,final BasketBallBetContent model,final List<Ticket> ticketList,final int sheDanTotal){
		// 遍历过关方式
		for (final SportteryPassType type : passTypes) {
			final int needMatchCount = type.getMatchCount();// 拿到过关的场次数目
			final Map<Integer,List<BasketBallMatchItem>> danMatchs = new HashMap<Integer,List<BasketBallMatchItem>>();
			ExtensionMathUtils.efficientCombExtension(sheDanSize, 0, sheDanTotal, new ExtensionCombCallBack() {
				public void run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
					List<String> list = new ArrayList<String>();
					List<BasketBallMatchItem> danList = new ArrayList<BasketBallMatchItem>();
					for (int i = 0; i < comb2.length; i++) {
						String intTime = String.valueOf(sheDanList.get(i).getIntTime());
						String lineId = String.valueOf(sheDanList.get(i).getLineId());
						String key = intTime+""+lineId;
						if (comb2[i] && !list.contains(key)){
							list.add(key);
							danList.add(sheDanList.get(i));
						}
					}
					if(danList.size() == sheDanSize){
						danMatchs.put(danMatchs.size()+1, danList);
					}
				}
			});
			
			ExtensionMathUtils.efficientCombExtension(needMatchCount, sheDanSize, unDanSize, new ExtensionCombCallBack() {
				public void run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
					List<String> unList = new ArrayList<String>();
					
					for(Integer danKey : danMatchs.keySet()){
						List<String> list = new ArrayList<String>();
						
						List<BasketBallMatchItem> tempMatchs = new ArrayList<BasketBallMatchItem>();
								
						tempMatchs.addAll(danMatchs.get(danKey));
							
						for (int i = 0; i < comb2.length; i++) {
							//因为会存在一场比赛选择了多种玩法的情况，所以这边要添加场次判断
							String intTime = String.valueOf(unDanList.get(i).getIntTime());
							String lineId = String.valueOf(unDanList.get(i).getLineId());
							String key = intTime+""+lineId;
							if (comb2[i] && !list.contains(key)){
								list.add(key);
								tempMatchs.add(unDanList.get(i));
							}
						}
								
						if (tempMatchs.size() > type.getMatchCount() - 1) {
							SportteryTicketModelTurbid<BasketBallMatchItem> tiketModel = new SportteryTicketModelTurbid<BasketBallMatchItem>();
							tiketModel.setPassMode(PassMode.PASS.ordinal());
							tiketModel.setMultiple(lotteryPlan.getMultiple());
							tiketModel.setPassType(type);
							tiketModel.setOnlyPlay(model.getIsOnlyPlay());

							tiketModel.setMatchItems(tempMatchs);
							SetTicketModel sm = new SetTicketModel(tiketModel, lotteryPlan);
							Ticket ticket = sm.getHtTicket();
							if(ticket != null){
								ticketList.add(sm.getHtTicket());
							}
						}
					}
				}
			});
		}
		return ticketList;
	}
	
	/**
	 * 不设胆玩法
	 * @param passTypes
	 * @param sheDanSize
	 * @param unDanSize
	 * @param unDanList
	 * @param lotteryPlan
	 * @param model
	 * @param ticketList
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<Ticket> getUnDanTicketList(List<SportteryPassType> passTypes,int sheDanSize,int unDanSize,final List<BasketBallMatchItem> unDanList,
			final LotteryPlan lotteryPlan,final BasketBallBetContent model,final List<Ticket> ticketList){
		// 遍历过关方式
		for (final SportteryPassType type : passTypes) {
			int needMatchCount = type.getMatchCount();// 拿到过关的场次数目
			ExtensionMathUtils.efficientCombExtension(needMatchCount, sheDanSize, unDanSize, new ExtensionCombCallBack() {
				public void run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
					List<BasketBallMatchItem> tempMatchs=new ArrayList<BasketBallMatchItem>();
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < comb2.length; i++) {
						String intTime = String.valueOf(unDanList.get(i).getIntTime());
						String lineId = String.valueOf(unDanList.get(i).getLineId());
						String key = intTime+""+lineId;
						if (comb2[i] && !list.contains(key)){
							list.add(key);
							tempMatchs.add(unDanList.get(i));
						}
					}
					
					if (tempMatchs.size() > type.getMatchCount()-1) {
						SportteryTicketModelTurbid<BasketBallMatchItem> tiketModel=new SportteryTicketModelTurbid<BasketBallMatchItem>();
						tiketModel.setPassMode(PassMode.PASS.ordinal());
						tiketModel.setMultiple(lotteryPlan.getMultiple());
						tiketModel.setPassType(type);
						tiketModel.setOnlyPlay(model.getIsOnlyPlay());
						
						tiketModel.setMatchItems(tempMatchs);
						SetTicketModel sm = new SetTicketModel(tiketModel, lotteryPlan);
						Ticket ticket = sm.getHtTicket();
						if(ticket != null){
							ticketList.add(sm.getHtTicket());
						}
					}
				}
			});
		}
		return ticketList;
	}
	
	public double getBetAmount(final LotteryPlan lotteryPlan) throws Exception {
		List<Ticket> ticketList = getLqHtTicketList(lotteryPlan);
		double betAmount = 0;
		for (Ticket ticket : ticketList) {
			betAmount += ticket.getAmount();
		}
		return betAmount;
	}
}
