package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;

public class AbstractVipBonusLine implements java.io.Serializable {
	private Integer id;
	// 帐号
	private String account;
	// 是否是顶层VIP
	private Integer isTop;
	// 创建时间
	private Date createDateTime;
	// 日投注数
	private Integer ticketNum;
	// 日投注金额
	private Double ticketAmount;
	// 下级日投注用户数
	private Integer childNum;
	// 下级日投注数量合计
	private Integer childTicketNum;
	// 下级日出票金额合计
	private Double childTicketAmount;
	// 结算状态 0:未结算,1:已结算
	private CommonStatus status;
	// 返利金额
	private Double bonus;
	// 结算时间
	private Date settlementTime;
	// 返利比例
	private Double percent;
	// 消费时间
	private String ticketDate;

	
	public AbstractVipBonusLine() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AbstractVipBonusLine(Integer id, String account, Integer isTop, Date createDateTime, Integer ticketNum, Double ticketAmount, Integer childNum, Integer childTicketNum,
			Double childTicketAmount, CommonStatus status, Double bonus, Date settlementTime, Double percent) {
		super();
		this.id = id;
		this.account = account;
		this.isTop = isTop;
		this.createDateTime = createDateTime;
		this.ticketNum = ticketNum;
		this.ticketAmount = ticketAmount;
		this.childNum = childNum;
		this.childTicketNum = childTicketNum;
		this.childTicketAmount = childTicketAmount;
		this.status = status;
		this.bonus = bonus;
		this.settlementTime = settlementTime;
		this.percent = percent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Integer getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Double getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(Double ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public Integer getChildTicketNum() {
		return childTicketNum;
	}

	public void setChildTicketNum(Integer childTicketNum) {
		this.childTicketNum = childTicketNum;
	}

	public Double getChildTicketAmount() {
		return childTicketAmount;
	}

	public void setChildTicketAmount(Double childTicketAmount) {
		this.childTicketAmount = childTicketAmount;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public String getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}	
}
