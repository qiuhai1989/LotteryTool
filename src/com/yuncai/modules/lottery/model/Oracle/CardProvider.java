package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class CardProvider extends AbstractCardProvider implements java.io.Serializable{

	public CardProvider(){
		
	}
	
	public CardProvider(Integer id, String name, String deramk, Date createDateTime, String account, Integer memberId, Integer isfd,
			Integer fdpercent){
		super(id, name, deramk, createDateTime, account, memberId, isfd, fdpercent);
	}
	
}
