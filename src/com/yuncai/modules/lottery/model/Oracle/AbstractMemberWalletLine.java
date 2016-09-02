package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;

public class AbstractMemberWalletLine implements java.io.Serializable {
	// Fields
	// 钱包流水
	private Integer walletLineNo;
	// 方案编号
	private Integer planNo;
	public Integer getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(Integer selectStatus) {
		this.selectStatus = selectStatus;
	}

	// 订单编号
	private Integer orderNo;
	// 操作 流水编号
	private Integer operLineNo;
	// 帐号
	private String account;
	// 会员编号
	private Integer memberId;
	// 交易类型
	private WalletTransType transType;
	// 钱包类型
	private WalletType walletType;
	// 发生金额
	private Double amount;
	// 彩种
	private LotteryType lotteryType;
	// 发生时间
	private Date createDateTime;
	// 状态
	private Integer status;
	// 验证码
	private String verifyMd5;
	// 会员的来源编号
	private Integer sourceId;

	// 可用金额
	private Double ableBalance;
	// 冻结金额
	private Double freezeBalance;
	// 历史消费金额
	private Double heapBalance;
	//历史中奖金额
	private Double heapPrize;
	
	//历史彩金
	private Double heapGold; 
	// 积分备注
	private String remark = "";
	

	private Integer selectStatus; //操作动作,主要是区分于解冻与冻结的问题


	
	
	// Constructors

	/** default constructor */
	public AbstractMemberWalletLine() {
	}

	/** minimal constructor */
	public AbstractMemberWalletLine(Integer walletLineNo, String account, Integer memberId, WalletTransType transType, WalletType walletType,
			Double amount, Integer status, String verifyMd5, Integer sourceId) {
		this.walletLineNo = walletLineNo;
		this.account = account;
		this.memberId = memberId;
		this.transType = transType;
		this.walletType = walletType;
		this.amount = amount;
		this.status = status;
		this.verifyMd5 = verifyMd5;
		this.sourceId = sourceId;
	}

	/** full constructor */
	public AbstractMemberWalletLine(Integer walletLineNo, Integer planNo, Integer orderNo, Integer operLineNo, String account, Integer memberId,
			WalletTransType transType, WalletType walletType, Double amount, LotteryType lotteryType, Date createDateTime, Integer status,
			String verifyMd5, Integer sourceId) {
		this.walletLineNo = walletLineNo;
		this.planNo = planNo;
		this.orderNo = orderNo;
		this.operLineNo = operLineNo;
		this.account = account;
		this.memberId = memberId;
		this.transType = transType;
		this.walletType = walletType;
		this.amount = amount;
		this.lotteryType = lotteryType;
		this.createDateTime = createDateTime;
		this.status = status;
		this.verifyMd5 = verifyMd5;
		this.sourceId = sourceId;
	}

	public Integer getWalletLineNo() {
		return walletLineNo;
	}

	public void setWalletLineNo(Integer walletLineNo) {
		this.walletLineNo = walletLineNo;
	}

	public Integer getPlanNo() {
		return planNo;
	}

	public void setPlanNo(Integer planNo) {
		this.planNo = planNo;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOperLineNo() {
		return operLineNo;
	}

	public void setOperLineNo(Integer operLineNo) {
		this.operLineNo = operLineNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public WalletTransType getTransType() {
		return transType;
	}

	public void setTransType(WalletTransType transType) {
		this.transType = transType;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVerifyMd5() {
		return verifyMd5;
	}

	public void setVerifyMd5(String verifyMd5) {
		this.verifyMd5 = verifyMd5;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Double getAbleBalance() {
		return ableBalance;
	}

	public void setAbleBalance(Double ableBalance) {
		this.ableBalance = ableBalance;
	}

	public Double getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(Double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public Double getHeapBalance() {
		return heapBalance;
	}

	public void setHeapBalance(Double heapBalance) {
		this.heapBalance = heapBalance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Double getIncome() {
		if ( WalletTransType.outTypeList.contains(this.transType) ) {
			return null;
		} else {
			return getAmount();
		}
	}

	public Double getPay() {
		if ( WalletTransType.outTypeList.contains(this.transType) ) {
			return getAmount();
		} else {
			return null;
		}
	}

	public Double getHeapPrize() {
		return heapPrize;
	}

	public void setHeapPrize(Double heapPrize) {
		this.heapPrize = heapPrize;
	}

	public Double getHeapGold() {
		return heapGold;
	}

	public void setHeapGold(Double heapGold) {
		this.heapGold = heapGold;
	}


}
