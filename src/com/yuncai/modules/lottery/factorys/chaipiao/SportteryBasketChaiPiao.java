package com.yuncai.modules.lottery.factorys.chaipiao;


import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.yuncai.core.tools.ticket.SetTicket;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.sporttery.model.baskball.BasketBallBetContent;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallTicketModel;

public class SportteryBasketChaiPiao extends  SportteryChaiPiaoTurbid<BasketBallBetContent, BasketBallMatchItem>{

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
		List<Ticket> tempTicketList = getBaseTicketList(lotteryPlan);
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
	
	public List<Ticket> getBaseTicketList(LotteryPlan lotteryPlan) throws Exception {
		if (SelectType.UPLOAD.getValue() != lotteryPlan.getSelectType().getValue()) {// 非过滤投注
			return super.getBaseTicketList(lotteryPlan);
		}else
			return null; 
	}

}

