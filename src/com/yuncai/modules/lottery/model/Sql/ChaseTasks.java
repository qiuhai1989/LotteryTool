package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * 追号表
 * @author Administrator
 *
 */
public class ChaseTasks {
	private Integer id;
	//站点
	private Integer siteId;
	//用户ID
	private Integer userId;
	//创建时间
	private Date dateTime;
	//标题
	private String title;
	//彩种ID
	private Integer lotteryId;
	//撤单状态:0 未撤单 1 用户撤单 2 系统撤单
	private Short quashStatus;
	//追号停止条件:1代表中奖后追止 0 不停止
	private Double stopWhenWinMoney;
	//描述
	private String description;
	//佣金比例
	private Double schemeBonusScale;
	//方案总来自地方 GenType 类定义的格式
	private Integer fromClient;
	
	private Double amount;
	
	private Integer sum;
	
	private Integer finished;
	
	private Integer canceled;
	
	private String status;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Short getQuashStatus() {
		return quashStatus;
	}
	public void setQuashStatus(Short quashStatus) {
		this.quashStatus = quashStatus;
	}
	public Double getStopWhenWinMoney() {
		return stopWhenWinMoney;
	}
	public void setStopWhenWinMoney(Double stopWhenWinMoney) {
		this.stopWhenWinMoney = stopWhenWinMoney;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getSchemeBonusScale() {
		return schemeBonusScale;
	}
	public void setSchemeBonusScale(Double schemeBonusScale) {
		this.schemeBonusScale = schemeBonusScale;
	}
	public Integer getFromClient() {
		return fromClient;
	}
	public void setFromClient(Integer fromClient) {
		this.fromClient = fromClient;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public Integer getFinished() {
		return finished;
	}
	public void setFinished(Integer finished) {
		this.finished = finished;
	}
	public Integer getCanceled() {
		return canceled;
	}
	public void setCanceled(Integer canceled) {
		this.canceled = canceled;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
