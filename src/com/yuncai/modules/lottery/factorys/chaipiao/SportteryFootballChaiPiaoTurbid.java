package com.yuncai.modules.lottery.factorys.chaipiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yuncai.core.tools.ticket.SetTicket;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContentTurbid;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.PassMode;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;
import com.yuncai.modules.lottery.sporttery.support.SportteryPassType;
import com.yuncai.modules.lottery.sporttery.support.SportteryTicketModelTurbid;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootballTicketModelTurbid;

import com.yuncai.core.longExce.WebDataException;
import com.yuncai.modules.lottery.sporttery.support.ExtensionCombCallBack;
import com.yuncai.modules.lottery.sporttery.support.ExtensionMathUtils;

public class SportteryFootballChaiPiaoTurbid extends SportteryChaiPiaoTurbid<FootBallBetContentTurbid, FootBallMatchTurbidItem>{

	@Override
	public Class<FootBallMatchTurbidItem> getMatchItemClass() {
		return FootBallMatchTurbidItem.class;
	}

	@Override
	public Class<FootBallBetContentTurbid> getModelClass() {
		return FootBallBetContentTurbid.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List chaiPiao(final LotteryPlan lotteryPlan) throws Exception {
		List<Ticket> ticketList = new ArrayList<Ticket>();
		
		String content = lotteryPlan.getContent();
		List<Ticket> tempTicketList = null;
		tempTicketList = getZqHtTicketList(lotteryPlan);
		
		//重复票归集
		Map<String,Ticket> ticketMap = new HashMap<String,Ticket>();
		List<Ticket> noRepeatList = new ArrayList<Ticket>();
		for (Ticket t : tempTicketList) {
			String key = t.getContent();
			Ticket mt = ticketMap.get(key);
			if(mt == null ){//新票
				ticketMap.put(key, t);
			}else{//重复票，累加倍数
				int mult = t.getMultiple() + mt.getMultiple();
				int amount = t.getAmount() + mt.getAmount();
				mt.setMultiple(mult);
				mt.setAmount(amount);
			}
		}
		Iterator<Ticket> it = ticketMap.values().iterator(); 
		while(it.hasNext()){
			noRepeatList.add(it.next());
		}
		
		// 处理倍数
		for (Ticket t : noRepeatList) {
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
			String contents = t.getContent();
			FootballTicketModelTurbid btm = CommonUtils.Object4TikectJsonTurbid(contents, FootballTicketModelTurbid.class, FootBallMatchTurbidItem.class);
			btm.setMultiple(t.getMultiple());
			JSONObject jsonObject = JSONObject.fromObject(btm);
			t.setContent(jsonObject.toString());
			tempList.add(t);
		}
		return tempList;
	}
	
	
	public List<Ticket> getZqHtTicketList(final LotteryPlan lotteryPlan) throws Exception {
		final FootBallBetContentTurbid model = CommonUtils.Object4JsonTurbid(lotteryPlan.getContent(), getModelClass(), getMatchItemClass());
		final List<FootBallMatchTurbidItem> matchItems = (List<FootBallMatchTurbidItem>) model.getMatchItems();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<SportteryPassType> passTypes = model.getPassTypes();
		final List<FootBallMatchTurbidItem> unDanList = new ArrayList<FootBallMatchTurbidItem>();
		final List<FootBallMatchTurbidItem> sheDanList = new ArrayList<FootBallMatchTurbidItem>();
		final List<Integer> sheDanMatch = new ArrayList<Integer>();
		int sheDanTotal = 0;
		for (FootBallMatchTurbidItem x : matchItems) {// 找出设胆和非设胆的
			List<SportteryOption> sportteryOptionList = new ArrayList<SportteryOption>();
			for(SportteryOption sportteryOption : x.getOptions()){
				sportteryOptionList.add(sportteryOption);
			}
			
			for(SportteryOption sportteryOption : sportteryOptionList){
				List<SportteryOption> list = new ArrayList<SportteryOption>();
				list.add(sportteryOption);
				FootBallMatchTurbidItem fb = new FootBallMatchTurbidItem();
				
				fb.setIntTime(x.getIntTime());
				fb.setLineId(x.getLineId());
				fb.setOptions(list);
				fb.setShedan(x.isShedan());
				if(x.isShedan()){
					sheDanList.add(fb);
				}else{
					unDanList.add(fb);
				}
			}
			
			if(x.isShedan()){
				sheDanMatch.add(sportteryOptionList.size());
				sheDanTotal += sportteryOptionList.size();
			}
		}
		
		if(sheDanMatch.size()>0){
			ticketList = this.getSheDanTicketList(passTypes,sheDanMatch.size(),unDanList.size(),sheDanList,unDanList,lotteryPlan,model,ticketList,sheDanTotal);
		}else{
			ticketList = this.getUnDanTicketList(passTypes,0,unDanList.size(),unDanList,lotteryPlan,model,ticketList);
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
	private List<Ticket> getSheDanTicketList(List<SportteryPassType> passTypes,final int sheDanSize,final int unDanSize,final List<FootBallMatchTurbidItem> sheDanList,
			final List<FootBallMatchTurbidItem> unDanList,final LotteryPlan lotteryPlan,final FootBallBetContentTurbid model,final List<Ticket> ticketList,final int sheDanTotal){
		// 遍历过关方式
		for (final SportteryPassType type : passTypes) {
			final int needMatchCount = type.getMatchCount();// 拿到过关的场次数目
			final Map<Integer,List<FootBallMatchTurbidItem>> danMatchs = new HashMap<Integer,List<FootBallMatchTurbidItem>>();
			ExtensionMathUtils.efficientCombExtension(sheDanSize, 0, sheDanTotal, new ExtensionCombCallBack() {
				public void run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
					List<String> list = new ArrayList<String>();
					List<FootBallMatchTurbidItem> danList = new ArrayList<FootBallMatchTurbidItem>();
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
						
						List<FootBallMatchTurbidItem> tempMatchs = new ArrayList<FootBallMatchTurbidItem>();
								
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
							SportteryTicketModelTurbid<FootBallMatchTurbidItem> tiketModel = new SportteryTicketModelTurbid<FootBallMatchTurbidItem>();
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
	private List<Ticket> getUnDanTicketList(List<SportteryPassType> passTypes,int sheDanSize,int unDanSize,final List<FootBallMatchTurbidItem> unDanList,
			final LotteryPlan lotteryPlan,final FootBallBetContentTurbid model,final List<Ticket> ticketList){
		// 遍历过关方式
		for (final SportteryPassType type : passTypes) {
			int needMatchCount = type.getMatchCount();// 拿到过关的场次数目
				ExtensionMathUtils.efficientCombExtension(needMatchCount, sheDanSize, unDanSize, new ExtensionCombCallBack() {
					public void run(boolean[] comb1, int m1, boolean[] comb2, int m2) {
						List<FootBallMatchTurbidItem> tempMatchs = new ArrayList<FootBallMatchTurbidItem>();
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
							SportteryTicketModelTurbid<FootBallMatchTurbidItem> tiketModel = new SportteryTicketModelTurbid<FootBallMatchTurbidItem>();
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
		List<Ticket> ticketList = getZqHtTicketList(lotteryPlan);
		double betAmount = 0;
		for (Ticket ticket : ticketList) {
			betAmount += ticket.getAmount();
		}
		return betAmount;
	}


}
