package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PageType;

public class AbstractPagesInfo implements java.io.Serializable {
	private Integer id;
	private String pageName;
	private String pageId;
	private LotteryType lotteryType;
	private Integer isRoot;
	private PageType pageType;
	private Date createDateTime;
	private String platform;
	private String address;
	private Integer isLogin;
		

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public AbstractPagesInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AbstractPagesInfo(Integer id, String pageName, String pageId, LotteryType lotteryType, Integer isRoot, PageType pageType,
			Date createDateTime, String platform, String address) {
		super();
		this.id = id;
		this.pageName = pageName;
		this.pageId = pageId;
		this.lotteryType = lotteryType;
		this.isRoot = isRoot;
		this.pageType = pageType;
		this.createDateTime = createDateTime;
		this.platform = platform;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public LotteryType getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Integer getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(Integer isRoot) {
		this.isRoot = isRoot;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public PageType getPageType() {
		return pageType;
	}

	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}
}
