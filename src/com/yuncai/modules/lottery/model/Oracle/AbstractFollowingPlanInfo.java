package com.yuncai.modules.lottery.model.Oracle;

public class AbstractFollowingPlanInfo implements java.io.Serializable{
	private Integer planNo;			//方案No
	private Integer followingType;	//跟单类型
	private Integer planStatus;		//方案跟单状态
	private Integer processedStatus;//处理状态	-1未处理（或出现错误）	0已处理
	public Integer getPlanNo() {
		return planNo;
	}
	public void setPlanNo(Integer planNo) {
		this.planNo = planNo;
	}
	public Integer getFollowingType() {
		return followingType;
	}
	public void setFollowingType(Integer followingType) {
		this.followingType = followingType;
	}
	public Integer getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(Integer planStatus) {
		this.planStatus = planStatus;
	}
	public Integer getProcessedStatus() {
		return processedStatus;
	}
	public void setProcessedStatus(Integer processedStatus) {
		this.processedStatus = processedStatus;
	}
}
