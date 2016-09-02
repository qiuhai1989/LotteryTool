package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class AbstractVipGiftDetail implements java.io.Serializable {
	  private Integer id;
	  //活动名称
	  private String giftName;
	  //平台ID
	  private Integer platFormeId; 
	  //平台名称
	  private String  platformeName;
	  //渠道ID
	  private Integer  channelId; 
	  //渠道名称
	  private String channelName;
	  
	  //用户ID
	  private Integer memberId;
	  
	  //用户名
	  private String account;
	  
	  //金额
	  private Double amount;
	  //创建时间
	  private Date createTimeDate;
	  
	  //赠送者ID
	  private Integer vipId;
	  //赠送者账号名
	  private String vipAccount;
	  
	  
	public AbstractVipGiftDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AbstractVipGiftDetail(Integer id, String giftName, Integer platFormeId, String platformeName, Integer channelId, String channelName, Integer memberId, String account, Double amount,
			Date createTimeDate, Integer vipId, String vipAccount) {
		super();
		this.id = id;
		this.giftName = giftName;
		this.platFormeId = platFormeId;
		this.platformeName = platformeName;
		this.channelId = channelId;
		this.channelName = channelName;
		this.memberId = memberId;
		this.account = account;
		this.amount = amount;
		this.createTimeDate = createTimeDate;
		this.vipId = vipId;
		this.vipAccount = vipAccount;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public Integer getPlatFormeId() {
		return platFormeId;
	}
	public void setPlatFormeId(Integer platFormeId) {
		this.platFormeId = platFormeId;
	}
	public String getPlatformeName() {
		return platformeName;
	}
	public void setPlatformeName(String platformeName) {
		this.platformeName = platformeName;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
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
	public Date getCreateTimeDate() {
		return createTimeDate;
	}
	public void setCreateTimeDate(Date createTimeDate) {
		this.createTimeDate = createTimeDate;
	}
	public String getVipAccount() {
		return vipAccount;
	}
	public void setVipAccount(String vipAccount) {
		this.vipAccount = vipAccount;
	}

	public Integer getVipId() {
		return vipId;
	}
	public void setVipId(Integer vipId) {
		this.vipId = vipId;
	}
	  
}
