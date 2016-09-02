package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.CardStatus;

public class Card  extends AbstractCard implements java.io.Serializable{
	public Card() {
		super();
	}

	public Card(Integer  id,String batchNo,String no,Double amount,Date createDateTime,Date expireDateTime,Integer memberId,String account,CardStatus status,Date useDateTime,String provider,Integer providerId,Integer isValid) {
		super(id, batchNo, no, amount, createDateTime, expireDateTime, memberId, account, status, useDateTime, provider, providerId, isValid);
	}
	
	
}
