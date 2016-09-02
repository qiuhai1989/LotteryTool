package com.yuncai.core.tools.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;

public class SetTicket {
	// ticket公有的属性设置， 传入的ticket需要设置价格、玩法、内容
	public static Ticket setTicket(Ticket ticket, LotteryPlan lotteryPlan) {
		ticket.setLotteryType(lotteryPlan.getLotteryType());
		ticket.setTerm(lotteryPlan.getTerm());
		ticket.setPlanNo(lotteryPlan.getPlanNo());
		ticket.setStatus(TicketStatus.WSP);
		ticket.setCreateDateTime(new Date());
		ticket.setDealDateTime(lotteryPlan.getDealDateTime());
		ticket.setMultiple(lotteryPlan.getMultiple());
		ticket.setIsBingo(new Integer(1));
		ticket.setIsConvert(new Integer(1));
		ticket.setAccount(lotteryPlan.getAccount());
		return ticket;
	}

	public static List processBeishu(List<Ticket> srcList, int currBeishu, int maxBeishu) {
		List<Ticket> newList = new ArrayList();
		 {
			try {
				
				// 如果倍数超过最大倍数时，拆分
				for (Ticket t : srcList) {

					//计算单背金额
					int singleAmount = t.getAmount()/t.getMultiple();
					//计算2万的倍数
					int bei2W = 20000/singleAmount;
					//以两者小的那个做为最大倍数
					int limitBeishu = bei2W < maxBeishu ? bei2W : maxBeishu;
					
					for (int i = 0; i < currBeishu / limitBeishu; i++) {
						Ticket newTicket = t.clone();
						newTicket.setMultiple(limitBeishu);
						// 重算金额
						newTicket.setAmount(t.getAmount() / currBeishu * limitBeishu);
						newList.add(newTicket);
					}
					// 处理倍数的尾数
					int wei = currBeishu % limitBeishu;
					if (wei != 0) {
						Ticket newTicket = t.clone();
						newTicket.setMultiple(wei);
						// 重算金额
						newTicket.setAmount(t.getAmount() / currBeishu * wei);
						newList.add(newTicket);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return newList;
		}
	}

}
