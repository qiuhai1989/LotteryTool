package com.yuncai.modules.lottery.bean.search;

import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.DateTools;

public class VipBonusLineSearch {
	private String account;
	private String startDate;// 开始时间
	private String endDate;// 结束时间
	private CommonStatus status;//结算状态
	private Integer id;
	
	
	public Date getStartDate1() {
		if (this.startDate != null && !"".equals(startDate)) {
			//return DateTools.StringToDate(startDate + " 00:00:00");
			return DateTools.StringToDate(startDate);
		}
		return null;
	}

	public Date getEndDate1() {
		if (this.endDate != null && !"".equals(endDate)) {
			//return DateTools.StringToDate(endDate + " 23:59:59");
			return DateTools.StringToDate(endDate);
		}
		return null;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public CommonStatus getStatus() {
		return status;
	}
	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
