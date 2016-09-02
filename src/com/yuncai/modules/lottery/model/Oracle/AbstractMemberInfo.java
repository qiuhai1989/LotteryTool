package com.yuncai.modules.lottery.model.Oracle;

public class AbstractMemberInfo implements java.io.Serializable {
	
	// Fields
	// 会员编号
	private Integer memberId;
	// 帐号
	private String account;
	// 银行
	private String bank;
	// 卡号
	private String bankCard;
	// 支行名称
	private String bankPart;
	// 是否DNAPAY用户
	private Integer isDNAPayUser;

	// Constructors

	/** default constructor */
	public AbstractMemberInfo() {
	}

	/** minimal constructor */
	public AbstractMemberInfo(Integer memberId, String account) {
		this.memberId = memberId;
		this.account = account;
	}

	/** full constructor */
	public AbstractMemberInfo(Integer memberId, String account, String bank, String bankCard, Integer isDNAPayUser) {
		this.memberId = memberId;
		this.account = account;
		this.bank = bank;
		this.bankCard = bankCard;
		this.isDNAPayUser = isDNAPayUser;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankPart() {
		return bankPart;
	}

	public void setBankPart(String bankPart) {
		this.bankPart = bankPart;
	}

	public Integer getIsDNAPayUser() {
		return isDNAPayUser;
	}

	public void setIsDNAPayUser(Integer isDNAPayUser) {
		this.isDNAPayUser = isDNAPayUser;
	}
	
}
