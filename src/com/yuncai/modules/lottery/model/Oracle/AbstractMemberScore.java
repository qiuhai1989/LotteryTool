package com.yuncai.modules.lottery.model.Oracle;

public class AbstractMemberScore implements java.io.Serializable{
	// Fields
	// 会员编号
	private Integer memberId;
	// 帐号
	private String account;
	// 可用积分
	private Integer ableScore;
	// 历史积分
	private Integer heapScore;

	// Constructors

	/** default constructor */
	public AbstractMemberScore() {
	}

	/** full constructor */
	public AbstractMemberScore(Integer memberId, String account, Integer ableScore, Integer heapScore) {
		this.memberId = memberId;
		this.account = account;
		this.ableScore = ableScore;
		this.heapScore = heapScore;
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

	public Integer getAbleScore() {
		return ableScore;
	}

	public void setAbleScore(Integer ableScore) {
		this.ableScore = ableScore;
	}

	public Integer getHeapScore() {
		return heapScore;
	}

	public void setHeapScore(Integer heapScore) {
		this.heapScore = heapScore;
	}


}
