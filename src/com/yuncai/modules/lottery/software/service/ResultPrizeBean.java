package com.yuncai.modules.lottery.software.service;

public class ResultPrizeBean  {
	private String account; //用户
	private Double amount;    //税前金额
	private Double amountPer; //税后金额
	private String schemeNumber; //方案编号
	private String orderNo; //方案详情编号
	//private String term; //期数
	private int type;
	private Double pretaxPrize; //总方案税前
	private Double posttaxPrize; //方案税后
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getAmountPer() {
		return amountPer;
	}
	public void setAmountPer(Double amountPer) {
		this.amountPer = amountPer;
	}
	public String getSchemeNumber() {
		return schemeNumber;
	}
	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getPretaxPrize() {
		return pretaxPrize;
	}
	public void setPretaxPrize(Double pretaxPrize) {
		this.pretaxPrize = pretaxPrize;
	}
	public Double getPosttaxPrize() {
		return posttaxPrize;
	}
	public void setPosttaxPrize(Double posttaxPrize) {
		this.posttaxPrize = posttaxPrize;
	}

}
