package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.RechargeScoreType;

public class AbstractMemberChargeLine implements java.io.Serializable {
	
	private String chargeNo;
	private String account;
	private Date createDateTime;
	private Date successDateTime;
	private Double amount;
	private CommonStatus status;
	private String payedNo;
//	private String requestInfo;
	private String responseInfo;
	private String bank;
	private ChargeType chargeType;
	private String bankCard;
	private RechargeScoreType rechargeScoreType;
	
	private Double formalitiesFees; //手续费
	
	private String channel; //操作来源

	// Constructors

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/** default constructor */
	public AbstractMemberChargeLine() {
	}

	/** minimal constructor */
	public AbstractMemberChargeLine(String chargeNo) {
		this.chargeNo = chargeNo;
	}

	/** full constructor */
	public AbstractMemberChargeLine(String chargeNo, String account, Date createDateTime, Date successDateTime, Double amount, CommonStatus status,
			String payedNo, String requestInfo, String responseInfo, String bank, ChargeType chargeType, String bankCard,RechargeScoreType rechargeScoreType,double formalitiesFees) {
		this.chargeNo = chargeNo;
		this.account = account;
		this.createDateTime = createDateTime;
		this.successDateTime = successDateTime;
		this.amount = amount;
		this.status = status;
		this.payedNo = payedNo;
//		this.requestInfo = requestInfo;
		this.responseInfo = responseInfo;
		this.bank = bank;
		this.chargeType = chargeType;
		this.bankCard = bankCard;
		this.rechargeScoreType = rechargeScoreType;
		this.formalitiesFees=formalitiesFees;
	}

	public String getChargeNo() {
		return chargeNo;
	}

	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getSuccessDateTime() {
		return successDateTime;
	}

	public void setSuccessDateTime(Date successDateTime) {
		this.successDateTime = successDateTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getPayedNo() {
		return payedNo;
	}

	public void setPayedNo(String payedNo) {
		this.payedNo = payedNo;
	}

//	public String getRequestInfo() {
//		return requestInfo;
//	}
//
//	public void setRequestInfo(String requestInfo) {
//		this.requestInfo = requestInfo;
//	}

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public RechargeScoreType getRechargeScoreType() {
		return rechargeScoreType;
	}

	public void setRechargeScoreType(RechargeScoreType rechargeScoreType) {
		this.rechargeScoreType = rechargeScoreType;
	}

	public Double getFormalitiesFees() {
		return formalitiesFees;
	}

	public void setFormalitiesFees(Double formalitiesFees) {
		this.formalitiesFees = formalitiesFees;
	}

	

	
}
