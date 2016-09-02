package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * 购买详情表
 * BuyDetailsId entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class BuyDetails implements java.io.Serializable {

	// Fields

	private Integer id;
	//站点
	private Integer siteId;
	//用户ID
	private Integer userId;
	//创建时间
	private Date dateTime;
	//方案ID
	private Integer schemeId;
	//加入份数
	private Integer share;
	//撤单状态:0 未撤单 1 用户撤单 2 系统撤单
	private Short quashStatus;
	//是否发起方案时的发起人的第一条认购记录
	private Boolean isWhenInitiate;
	//得到的税后奖金
	private Double winMoneyNoWithTax;
	//是否是自动跟单
	private Boolean isAutoFollowScheme;
	//购买金额
	private Double detailMoney;
	//渠道来源：(详情来自地方可以根据不同的渠道定义) MemberFollowingType类格式
	private Integer fromClient;
	
	public Integer getFromClient() {
		return fromClient;
	}

	public void setFromClient(Integer fromClient) {
		this.fromClient = fromClient;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	public Integer getShare() {
		return share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}

	public Short getQuashStatus() {
		return quashStatus;
	}

	public void setQuashStatus(Short quashStatus) {
		this.quashStatus = quashStatus;
	}

	public Boolean getIsWhenInitiate() {
		return isWhenInitiate;
	}

	public void setIsWhenInitiate(Boolean isWhenInitiate) {
		this.isWhenInitiate = isWhenInitiate;
	}

	public Double getWinMoneyNoWithTax() {
		return winMoneyNoWithTax;
	}

	public void setWinMoneyNoWithTax(Double winMoneyNoWithTax) {
		this.winMoneyNoWithTax = winMoneyNoWithTax;
	}

	public Boolean getIsAutoFollowScheme() {
		return isAutoFollowScheme;
	}

	public void setIsAutoFollowScheme(Boolean isAutoFollowScheme) {
		this.isAutoFollowScheme = isAutoFollowScheme;
	}

	public Double getDetailMoney() {
		return detailMoney;
	}

	public void setDetailMoney(Double detailMoney) {
		this.detailMoney = detailMoney;
	}

	public BuyDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BuyDetails(Integer siteId, Integer userId, Date dateTime,
			Integer schemeId, Integer share, Short quashStatus,
			Boolean isWhenInitiate, Double winMoneyNoWithTax,
			Boolean isAutoFollowScheme, Double detailMoney) {
		super();
		this.siteId = siteId;
		this.userId = userId;
		this.dateTime = dateTime;
		this.schemeId = schemeId;
		this.share = share;
		this.quashStatus = quashStatus;
		this.isWhenInitiate = isWhenInitiate;
		this.winMoneyNoWithTax = winMoneyNoWithTax;
		this.isAutoFollowScheme = isAutoFollowScheme;
		this.detailMoney = detailMoney;
	}

	
	

}