package com.yuncai.modules.lottery.service.oracleService.ticket;

import java.util.HashMap;
import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;
import com.yuncai.modules.lottery.service.BaseService;

public interface TicketService extends BaseService<Ticket, Integer> {
	
	/**
	 * 拆票
	 */
	public void chaipiao(LotteryPlan lotteryPlan) throws Exception;
	
	public List<Ticket> findByStatusAndlotteryType(LotteryType lotteryType, TicketStatus status);
	
	public void checkPiaoFullNet(Ticket ticket) throws Exception;
	
	public void songPiaoFullNet(List<Ticket> tickets)throws Exception;
	
	
	public void songPiaoFullNetJC(Ticket ticket) throws Exception;
	
	public void checkPiaoFullNetJC(Ticket ticket) throws Exception;
	/**
	 * 出票成功的开奖工式
	 * @param openResultMap
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public String TicketCheckBingo(HashMap<String, String> openResultMap, Ticket t,LotteryType lotterytype) throws Exception;

}
