package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;

public class VipBonusLine extends AbstractVipBonusLine implements java.io.Serializable {

	public VipBonusLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VipBonusLine(Integer id, String account, Integer isTop, Date createDateTime, Integer ticketNum, Double ticketAmount, Integer childNum, Integer childTicketNum, Double childTicketAmount,
			CommonStatus status, Double bonus, Date settlementTime, Double percent) {
		super(id, account, isTop, createDateTime, ticketNum, ticketAmount, childNum, childTicketNum, childTicketAmount, status, bonus, settlementTime, percent);
		// TODO Auto-generated constructor stub
	}

	
}
