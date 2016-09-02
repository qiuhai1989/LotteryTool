package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.ScoreType;

public class AbstractMemberScoreLine implements java.io.Serializable{
	// Fields
	// 积分流水号
	private Integer scoreLineNo;
	// 会员编号
	private Integer memberId;
	// 帐号
	private String account;
	// 积分余额
	private Integer ableScore;
	// 发生的积分值
	private Integer value;
	// 积分产生类型
	private ScoreType scoreType;
	// 发生时间
	private Date createDateTime;
	// 积分备注
	private String remark = "";
	// 是否支出
	private boolean consumeStatus;
	//方案编号
	private Integer planNo;
	//推荐人ID
	private Integer relatedUserId; //RELATED_USER_ID
	
	

	// Constructors

	/** default constructor */
	public AbstractMemberScoreLine() {
	}

	/** minimal constructor */
	public AbstractMemberScoreLine(Integer scoreLineNo, Integer memberId, String account, Integer value, ScoreType scoreType) {
		this.scoreLineNo = scoreLineNo;
		this.memberId = memberId;
		this.account = account;
		this.value = value;
		this.scoreType = scoreType;
	}

	/** full constructor */
	public AbstractMemberScoreLine(Integer scoreLineNo, Integer memberId, String account, Integer value, ScoreType scoreType, Date createDateTime) {
		this.scoreLineNo = scoreLineNo;
		this.memberId = memberId;
		this.account = account;
		this.value = value;
		this.scoreType = scoreType;
		this.createDateTime = createDateTime;
	}

	public Integer getScoreLineNo() {
		return scoreLineNo;
	}

	public void setScoreLineNo(Integer scoreLineNo) {
		this.scoreLineNo = scoreLineNo;
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public ScoreType getScoreType() {
		return scoreType;
	}

	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(boolean consumeStatus) {
		this.consumeStatus = consumeStatus;
	}

	public Integer getPlanNo() {
		return planNo;
	}

	public void setPlanNo(Integer planNo) {
		this.planNo = planNo;
	}

	public Integer getRelatedUserId() {
		return relatedUserId;
	}

	public void setRelatedUserId(Integer relatedUserId) {
		this.relatedUserId = relatedUserId;
	}


}
