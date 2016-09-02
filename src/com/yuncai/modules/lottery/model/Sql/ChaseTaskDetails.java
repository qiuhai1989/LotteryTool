package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * 追号详情表
 * @author Administrator
 *
 */
public class ChaseTaskDetails {
	private Integer id;
	//站点
	private Integer siteId;
	//追号ID
	private Integer chaseTaskId;
	//创建时间
	private Date dateTime;
	//彩期ID
	private Integer isuseId;
	//玩法ID
	private Integer playTypeId;
	//倍数
	private Integer multiple;
	//认购金额
	private Double money;
	//撤单状态:0 未撤单 1 用户撤单 2 系统撤单
	private Short quashStatus;
	//是否执行追号
	private boolean executed;
	//如果执行了，自动生成的方案ID
	private Integer schemeId;
	//0 不保密 1 到截止 2 到开奖 3 永远
	private Short secrecyLevel;
	//方案内容
	private String lotteryNumber;
	//总份数
	private Integer share;
	//认购份数
	private Integer buyedShare;
	
	private Integer assureShare;
	
	//渠道
	private String channel;
	
	//提款额度
	private Double quotaAmount;
	
	public Double getQuotaAmount() {
		return quotaAmount;
	}
	public void setQuotaAmount(Double quotaAmount) {
		this.quotaAmount = quotaAmount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
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
	public Integer getChaseTaskId() {
		return chaseTaskId;
	}
	public void setChaseTaskId(Integer chaseTaskId) {
		this.chaseTaskId = chaseTaskId;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public Integer getIsuseId() {
		return isuseId;
	}
	public void setIsuseId(Integer isuseId) {
		this.isuseId = isuseId;
	}
	public Integer getPlayTypeId() {
		return playTypeId;
	}
	public void setPlayTypeId(Integer playTypeId) {
		this.playTypeId = playTypeId;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Short getQuashStatus() {
		return quashStatus;
	}
	public void setQuashStatus(Short quashStatus) {
		this.quashStatus = quashStatus;
	}
	public boolean isExecuted() {
		return executed;
	}
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
	public Integer getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}
	public Short getSecrecyLevel() {
		return secrecyLevel;
	}
	public void setSecrecyLevel(Short secrecyLevel) {
		this.secrecyLevel = secrecyLevel;
	}
	public String getLotteryNumber() {
		return lotteryNumber;
	}
	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
	public Integer getShare() {
		return share;
	}
	public void setShare(Integer share) {
		this.share = share;
	}
	public Integer getBuyedShare() {
		return buyedShare;
	}
	public void setBuyedShare(Integer buyedShare) {
		this.buyedShare = buyedShare;
	}
	public Integer getAssureShare() {
		return assureShare;
	}
	public void setAssureShare(Integer assureShare) {
		this.assureShare = assureShare;
	}

}
