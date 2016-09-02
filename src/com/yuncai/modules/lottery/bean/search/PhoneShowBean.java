package com.yuncai.modules.lottery.bean.search;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

public class PhoneShowBean {
	private LotteryType lotteryType; // 彩种
	private int status; //状态
	private int order; //排序
	private String term; //彩期
	private String listPlanNo; //单据号集合
	private String account; //用户
	private int planType; //购彩类型
	
	private int scoreLineNo; //积分流水号
	
	private int walletLineNo; //钱包流水号
	
	private String provider; //渠道
	private int planNo;
	

	void PhoneShowBean(){
		lotteryType=null;
		status=-1;
		order=-1;
		term=null;
		listPlanNo=null;
		account=null;
		planType=0;
		scoreLineNo=-1;
		walletLineNo=-1;
		provider=null;
		planNo=-1;
	}
	
	
	

	public int getPlanNo() {
		return planNo;
	}




	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}




	public int getScoreLineNo() {
		return scoreLineNo;
	}
	public void setScoreLineNo(int scoreLineNo) {
		this.scoreLineNo = scoreLineNo;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public LotteryType getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getListPlanNo() {
		return listPlanNo;
	}
	public void setListPlanNo(String listPlanNo) {
		this.listPlanNo = listPlanNo;
	}
	
	public int getPlanType() {
		return planType;
	}
	public void setPlanType(int planType) {
		this.planType = planType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getWalletLineNo() {
		return walletLineNo;
	}
	public void setWalletLineNo(int walletLineNo) {
		this.walletLineNo = walletLineNo;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
