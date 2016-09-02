package com.yuncai.modules.lottery.model.Sql.vo;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

public class CustomFriendBean {
	private int id;
	private int userId;  //用户ID
	private String userName; //用户名
	private LotteryType lotteryType; //彩种
	private int followUserID;  //根单人员ID
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getLotteryType() {
		return lotteryType.getValue();
	}
	public void setLotteryType(int lotteryType) {
		this.lotteryType =LotteryType.getItem(lotteryType);
	}
	public int getFollowUserID() {
		return followUserID;
	}
	public void setFollowUserID(int followUserID) {
		this.followUserID = followUserID;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
