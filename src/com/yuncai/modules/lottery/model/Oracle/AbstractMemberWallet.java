package com.yuncai.modules.lottery.model.Oracle;

import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;

public class AbstractMemberWallet implements java.io.Serializable {

	// Fields
	// 无逻辑主键
	private Integer id;
	// 会员编号
	private Integer memberId;
	// 帐号
	private String account;
	// 可用余额
	private Double ableBalance;
	// 冻结余额
	private Double freezeBalance;
	//提现配额
	private Double takeCashQuota=0d;
	// 历史消费余额
	private Double heapBalance;
	// 历史中奖余额
	private Double heapPrize;
	
	//历史彩金
	private Double heapGold=0d;
	// 验证码
	private String verifyMd5;
	// 钱包类型
	private WalletType walletType;

	// Constructors

	/** default constructor */
	public AbstractMemberWallet() {
	}

	/** minimal constructor */
	public AbstractMemberWallet(String account, Double ableBalance, Double freezeBalance, Double heapBalance, Double heapPrize, String verifyMd5) {
		this.account = account;
		this.ableBalance = ableBalance;
		this.freezeBalance = freezeBalance;
		this.heapBalance = heapBalance;
		this.heapPrize = heapPrize;
		this.verifyMd5 = verifyMd5;
	}

	/** full constructor */
	public AbstractMemberWallet(Integer memberId, String account, Double ableBalance, Double freezeBalance, Double heapBalance, Double heapPrize,
			String verifyMd5, WalletType walletType) {
		this.memberId = memberId;
		this.account = account;
		this.ableBalance = ableBalance;
		this.freezeBalance = freezeBalance;
		this.heapBalance = heapBalance;
		this.heapPrize = heapPrize;
		this.verifyMd5 = verifyMd5;
		this.walletType = walletType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getTakeCashQuota() {
		return takeCashQuota;
	}

	public void setTakeCashQuota(Double takeCashQuota) {
		this.takeCashQuota = takeCashQuota;
	}

	public Double getHeapBalance() {
		return heapBalance;
	}

	public void setHeapBalance(Double heapBalance) {
		this.heapBalance = heapBalance;
	}

	public Double getHeapPrize() {
		return heapPrize;
	}

	public void setHeapPrize(Double heapPrize) {
		this.heapPrize = heapPrize;
	}

	public String getVerifyMd5() {
		return verifyMd5;
	}

	public void setVerifyMd5(String verifyMd5) {
		this.verifyMd5 = verifyMd5;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public Double getHeapGold() {
		return heapGold;
	}

	public void setHeapGold(Double heapGold) {
		this.heapGold = heapGold;
	}

}
