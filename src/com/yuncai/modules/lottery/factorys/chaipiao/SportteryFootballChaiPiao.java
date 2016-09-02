package com.yuncai.modules.lottery.factorys.chaipiao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ticket.SetTicket;

import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;


import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.sporttery.model.FootBallBetContent;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.football.FootballTicketModel;

public class SportteryFootballChaiPiao extends SportteryChaiPiao<FootBallBetContent, FootBallMatchItem>
{
	
	@Override
	public Class<FootBallMatchItem> getMatchItemClass() {
		return FootBallMatchItem.class;
	}

	@Override
	public Class<FootBallBetContent> getModelClass() {
		return FootBallBetContent.class;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List chaiPiao(final LotteryPlan lotteryPlan) throws Exception {
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<Ticket> tempTicketList = getBaseTicketList(lotteryPlan);
		
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
			String content = t.getContent();
			FootballTicketModel btm = CommonUtils.Object4TikectJson(content, FootballTicketModel.class, FootBallMatchItem.class);
			btm.setMultiple(t.getMultiple());
			JSONObject jsonObject = JSONObject.fromObject(btm);
			t.setContent(jsonObject.toString());
			tempList.add(t);
		}
		return tempList;
	}
	
	@Override
	public List<Ticket> getBaseTicketList(LotteryPlan lotteryPlan) throws Exception {
		//LogUtil.out("------------------>>拆票ID："+lotteryPlan.getPlanNo());
		if (SelectType.UPLOAD.getValue() != lotteryPlan.getSelectType().getValue()) {// 非过滤投注
			return super.getBaseTicketList(lotteryPlan);
		}else
			return null; 
	}


}
