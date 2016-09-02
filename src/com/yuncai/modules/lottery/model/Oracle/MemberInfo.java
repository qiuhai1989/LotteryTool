package com.yuncai.modules.lottery.model.Oracle;

import com.yuncai.core.tools.StringTools;

public class MemberInfo extends AbstractMemberInfo implements java.io.Serializable{

	// Constructors

	/** default constructor */
	public MemberInfo() {
	}

	/** minimal constructor */
	public MemberInfo(Integer memberId, String account) {
		super(memberId, account);
	}

	/** full constructor */
	public MemberInfo(Integer memberId, String account, String bank, String bankCard, Integer isDNAPayUser) {
		super(memberId, account, bank, bankCard, isDNAPayUser);
	}

	public String getBankCardSec() {
		String src = this.getBankCard();
		if(src == null ) return "";
		return StringTools.getSecStr(src, 4, src.length() - 2);

	}
}
