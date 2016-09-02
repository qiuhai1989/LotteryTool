package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;

/**
 * .这句话是J2SE API帮助里面的话，意思是要让实例调用clone方法就需要让此类实现Cloneable接口，API里面还有句话是：如果在没有实现 Cloneable 接口的实例上调用 Object 的 clone 方法，则会导致抛出 CloneNotSupportedException 异常，这便是“合法”的含义。
 *  但请注意，Cloneable接口只是个标签接口，不含任何需要实现的方法，就像Serializable接口一样。
 * @author Administrator
 *
 */
public class Ticket extends AbstractTicket implements java.lang.Cloneable{
	public Ticket() {
	}

	/** minimal constructor */
	public Ticket(Integer planNo, String term, Date createDateTime, Date dealDateTime, Integer multiple, PlayType playType, TicketStatus status,
			String account) {
		super(planNo, term, createDateTime, dealDateTime, multiple, playType, status, account);
	}

	/** full constructor */
	public Ticket(Integer planNo, String content, String term, LotteryType lotteryType, Integer amount, Date createDateTime, Date sendDateTime,
			Date printDateTime, Date dealDateTime, Integer multiple, PlayType playType, Integer isBingo, Double bingoAmount, String bingoContent,
			TicketStatus status, String addAttribute, String account, String outId, Integer noInBatch, String batchNo, Integer isConvert,
			Date convertDateTime) {
		super(planNo, content, term, lotteryType, amount, createDateTime, sendDateTime, printDateTime, dealDateTime, multiple, playType, isBingo,
				bingoAmount, bingoContent, status, addAttribute, account, outId, noInBatch, batchNo, isConvert, convertDateTime);
	}
	
	public Ticket clone() throws CloneNotSupportedException {
		return (Ticket) super.clone();
	}

}
