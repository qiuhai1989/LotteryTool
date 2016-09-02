package com.yuncai.modules.lottery.action.admin.search;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

public class PagesInfoSearch {
	private String pageName;
	private String pageId;
	private Integer lotteryType = -1;
	private Integer pageType;
	private String platform ;
	
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Integer getPageType() {
		return pageType;
	}
	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
