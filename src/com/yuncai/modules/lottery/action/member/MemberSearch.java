package com.yuncai.modules.lottery.action.member;

import java.util.Date;

import com.yuncai.core.tools.DateTools;

public class MemberSearch {
	private String account;
	private String startDate;
	private String endDate;
	private String name;
	private String email;
	private String certNo;
	private String mobile;
	private String provider;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
