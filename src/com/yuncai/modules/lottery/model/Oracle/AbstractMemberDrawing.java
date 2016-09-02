package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.DrawingStatus;

public class AbstractMemberDrawing implements java.io.Serializable{

	// Fields

	//编号
	private String id;
	//银行
	private String bank;
	//银行地址
	private String partBank;
	//银行卡
	private String bankCard;
	//金额
	private Float amount;
	//账户
	private String account;
	//状态
	private DrawingStatus status;
	//创建时间
	private Date createDateTime;
	//处理时间
	private Date dealDateTime; 
	//发送时间
	private Date sendDateTime;
	//手续费
	private Double formalitiesFees;
	//操作渠道方式  如 手机 网页 
	private String channel;
	
	// Constructors
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getSendDateTime() {
		return sendDateTime;
	}

	public void setSendDateTime(Date sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	/** default constructor */
	public AbstractMemberDrawing() {
	}

	/** minimal constructor */
	public AbstractMemberDrawing(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractMemberDrawing(String id, String bank, String partBank, String bankCard, Float amount, String account, DrawingStatus status,
			Date createDateTime,Double formalitiesFees) {
		this.id = id;
		this.bank = bank;
		this.partBank = partBank;
		this.bankCard = bankCard;
		this.amount = amount;
		this.account = account;
		this.status = status;
		this.createDateTime = createDateTime;
		this.formalitiesFees=formalitiesFees;
	}

	public AbstractMemberDrawing(String id, String bank, String partBank, String bankCard, Float amount, String account, DrawingStatus status,
			Date createDateTime, Date dealDateTime, Date sendDateTime, Double formalitiesFees, String channel) {
		super();
		this.id = id;
		this.bank = bank;
		this.partBank = partBank;
		this.bankCard = bankCard;
		this.amount = amount;
		this.account = account;
		this.status = status;
		this.createDateTime = createDateTime;
		this.dealDateTime = dealDateTime;
		this.sendDateTime = sendDateTime;
		this.formalitiesFees = formalitiesFees;
		this.channel = channel;
	}

	public Double getFormalitiesFees() {
		return formalitiesFees;
	}

	public void setFormalitiesFees(Double formalitiesFees) {
		this.formalitiesFees = formalitiesFees;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getPartBank() {
		return partBank;
	}

	public void setPartBank(String partBank) {
		this.partBank = partBank;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public DrawingStatus getStatus() {
		return status;
	}

	public void setStatus(DrawingStatus status) {
		this.status = status;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getDealDateTime() {
		return dealDateTime;
	}

	public void setDealDateTime(Date dealDateTime) {
		this.dealDateTime = dealDateTime;
	}
}
