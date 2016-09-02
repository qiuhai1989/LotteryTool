package com.yuncai.modules.lottery.model.Sql.vo;

public class ChaseTasksBean {
	private Integer chaseTaskId;  //追号ID
	private Integer isuseId;    //期号ID
	private Integer multiple;   //倍数
	private Double money;        //金额
	private Short quashStatusDetail;  //详情中是否撤销 撤销状态:0 未撤销 1 用户撤销 2 系统撤销
	private boolean executed;         //是否购买。。执行任务
	private Integer schemeId;         //方案ID
	private Short secrecyLevel;        //方案是否保密
	private String lotteryNumber;       //方案内容
	private Integer share;             //总份数
	private Integer buyedShare;         //购买份数
	private Integer userId;              //用户ID
	private Short quashStatus;            //追号是否撤销
	private Double stopWhenWinMoney; //0 不自动停止 <> 0 当某期达到此奖金，后面的追号任务自动终止
	private Integer lotteryId;
	private Integer playTypeId;
	private  Integer fromClient;
	private Integer detailId; //详情ID
	
	
	
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getPlayTypeId() {
		return playTypeId;
	}
	public void setPlayTypeId(Integer playTypeId) {
		this.playTypeId = playTypeId;
	}
	public Integer getFromClient() {
		return fromClient;
	}
	public void setFromClient(Integer fromClient) {
		this.fromClient = fromClient;
	}
	public Integer getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Integer getChaseTaskId() {
		return chaseTaskId;
	}
	public void setChaseTaskId(Integer chaseTaskId) {
		this.chaseTaskId = chaseTaskId;
	}
	public Integer getIsuseId() {
		return isuseId;
	}
	public void setIsuseId(Integer isuseId) {
		this.isuseId = isuseId;
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
	public Short getQuashStatusDetail() {
		return quashStatusDetail;
	}
	public void setQuashStatusDetail(Short quashStatusDetail) {
		this.quashStatusDetail = quashStatusDetail;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	

}
