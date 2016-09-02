package com.yuncai.modules.lottery.model.Oracle.toolType;
import java.util.*;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;

public class PlanSearch {
	private String account;
	private LotteryType lotteryType = LotteryType.NENO;
	private String term;
	private PlanStatus planStatus = PlanStatus.ALL;
	private PlayType playType = PlayType.ALL;
	private String startDate;
	private String endDate;
	private int planNo;
	private int posttaxPrize = -1;
	private int amount = -1;
	private WinStatus winStatus = WinStatus.ALL;
	
	private GenType genTypeSJ=GenType.ALL;
	
	private PlanType planType=PlanType.ALL;
	
	private int isRealBuy = -1;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getLotteryType() {
		return lotteryType.getValue();
	}

	public void setLotteryType(int lotteryType) {
		LogUtil.out(">>>>>>>>>>>>>>>>>>>>>" + lotteryType);
		this.lotteryType = LotteryType.getItem(Integer.valueOf(lotteryType));
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getPlanStatus() {
		return planStatus.getValue();
	}

	public void setPlanStatus(int planStatus) {
		this.planStatus = PlanStatus.getItem(Integer.valueOf(planStatus));
	}

	public int getPlayType() {
		return playType.getValue();
	}

	public void setPlayType(int playType) {
		this.playType = PlayType.getItem(Integer.valueOf(playType));
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

	public int getPlanNo() {
		return planNo;
	}

	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}

	public int getWinStatus() {
		return winStatus.getValue();
	}

	public void setWinStatus(int winStatus) {
		this.winStatus = WinStatus.getItem(Integer.valueOf(winStatus));
	}
	public int getGenTypeSJ() {
		return genTypeSJ.getValue();
	}

	public void setGenTypeSJ(int genTypeSJ) {
		this.genTypeSJ = GenType.getItem(Integer.valueOf(genTypeSJ));
	}
	
	public int getPlanType() {
		return planType.getValue();
	}

	public void setPlanType(int planType) {
		this.planType = PlanType.getItem(Integer.valueOf(planType));
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

	public int getPosttaxPrize() {
		return posttaxPrize;
	}

	public void setPosttaxPrize(int posttaxPrize) {
		this.posttaxPrize = posttaxPrize;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getIsRealBuy() {
		return isRealBuy;
	}

	public void setIsRealBuy(int isRealBuy) {
		this.isRealBuy = isRealBuy;
	}

	// real account ? 真实账号??
	public String getRealAccount() {
		String[] vaArr = Constant.VirtualAccount.split(",");
		String realAccount = "";
		for(String s : vaArr){
			if(s.length()> 0)realAccount += "'" + s + "',"; 
		}
		realAccount = realAccount.substring(0,realAccount.length() -1);
		return realAccount;
	}

	public static void main(String[] arg){
		PlanSearch ps = new PlanSearch();
		System.out.println(ps.getRealAccount());
	}


}
