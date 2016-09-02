package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

public class CustomFriendFollowSchemes {
	private int id;
	
	//用户ID
	private int userId; 
	//跟单userId
	private int followUserId;
	//彩种ID
	private int lotteryId;
	//玩法
	private int playTypeId;
	//方案金额范围起点
	private Double moneyStart;
	//方案金额范围终点,2个设置都为0时表示不限制方案金额
	private Double moneyEnd;
	//每次跟单金额
	private int buyShareStart;
	private int buyShareEnd;
	//定制类别：1 用户定制 2 发起人指定
	private int type;
	//定制的时间，参与跟单的会员以时间为序进行跟单
	private Date dateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFollowUserId() {
		return followUserId;
	}
	public void setFollowUserId(int followUserId) {
		this.followUserId = followUserId;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public int getPlayTypeId() {
		return playTypeId;
	}
	public void setPlayTypeId(int playTypeId) {
		this.playTypeId = playTypeId;
	}
	public Double getMoneyStart() {
		return moneyStart;
	}
	public void setMoneyStart(Double moneyStart) {
		this.moneyStart = moneyStart;
	}
	public Double getMoneyEnd() {
		return moneyEnd;
	}
	public void setMoneyEnd(Double moneyEnd) {
		this.moneyEnd = moneyEnd;
	}
	public int getBuyShareStart() {
		return buyShareStart;
	}
	public void setBuyShareStart(int buyShareStart) {
		this.buyShareStart = buyShareStart;
	}
	public int getBuyShareEnd() {
		return buyShareEnd;
	}
	public void setBuyShareEnd(int buyShareEnd) {
		this.buyShareEnd = buyShareEnd;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	

}
