package com.yuncai.modules.lottery.action.member;
import java.util.*;

import com.yuncai.core.tools.DateTools;

public class WalletLineSearch {
	private String account;
	private int transType = -1;
	private String startDate;
	private String endDate;
	private int walletLineNo;
	private int planNo;
	private int orderNo;
	private int chaseNo;
	private int sourceId;
	private int lotteryType = -1;
	private String provider;
	private int isRealBuy = -1;
	private String remark;
	
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getWalletLineNo() {
		return walletLineNo;
	}

	public void setWalletLineNo(int walletLineNo) {
		this.walletLineNo = walletLineNo;
	}

	public int getPlanNo() {
		return planNo;
	}

	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getChaseNo() {
		return chaseNo;
	}

	public void setChaseNo(int chaseNo) {
		this.chaseNo = chaseNo;
	}

	public int getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Date getStartDate1() {
		if (this.startDate != null && !"".equals(startDate)) {
			return DateTools.StringToDate(startDate + " 00:00:00");
		}
		return null;
	}

	public Date getEndDate1() {
		if (this.endDate != null && !"".equals(endDate)) {
			return DateTools.StringToDate(endDate + " 23:59:59");
		}
		return null;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getIsRealBuy() {
		return isRealBuy;
	}

	public void setIsRealBuy(int isRealBuy) {
		this.isRealBuy = isRealBuy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static void main(String[] args) {
		
		//System.out.println(Constant.getVirtualAccounts());
	}

	

}
