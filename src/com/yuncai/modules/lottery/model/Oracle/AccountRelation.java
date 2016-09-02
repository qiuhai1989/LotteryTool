package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.AbstractAccountRelation;

public class AccountRelation extends AbstractAccountRelation implements java.io.Serializable {
	public AccountRelation() {
		super();
	}

	public AccountRelation(Integer id, String account, String parentAccount, Integer levelId, Date creTime) {
		super(id, account, parentAccount, levelId, creTime);
	}
}
