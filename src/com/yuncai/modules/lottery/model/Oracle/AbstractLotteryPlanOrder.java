package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberFollowingType;

public class AbstractLotteryPlanOrder implements java.io.Serializable{
	// Fields
	// 订单编号
	private Integer orderNo;
	// 方案编号
	private Integer planNo;
	// 帐号
	private String account;
	// 购买类型
	private BuyType buyType;
	// 发起时间
	private Date createDateTime;
	// 订单状态
	private PlanOrderStatus status;
	// 认购份数
	private Integer part;
	// 金额
	private Integer amount;
	// 税后奖金
	private Double posttaxPrize;
	// 奖金结算状态
	private Integer prizeSettleStatus;
	// 奖金结算时间
	private Date prizeSettleDateTime;
	// 购买所获得的积分
	private Integer score;
	// 中奖后所获得的经验值
	private Integer experience;
	// 验证码
	private String verifyMd5;
	// 返款时间
	private Date returnAmountDateTime;
	// 会员id
	private Integer memberId;
	// 使用的钱包类型
	private WalletType walletType;
	//是否自动跟单，自动跟单类型
	private MemberFollowingType followingType;
	
	private String channel;
	
	private String nickName;
	
	private Double quotaAmount;//每笔扣取的提款金额QUOTA_AMOUNT;

	
	// Constructors


	public Double getQuotaAmount() {
		return quotaAmount;
	}

	public void setQuotaAmount(Double quotaAmount) {
		this.quotaAmount = quotaAmount;
	}

	/** default constructor */
	public AbstractLotteryPlanOrder() {
	}

	/** minimal constructor */
	public AbstractLotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, PlanOrderStatus status, Integer part,
			Integer amount, String verifyMd5, Integer memberId, WalletType walletType) {
		this.orderNo = orderNo;
		this.planNo = planNo;
		this.account = account;
		this.buyType = buyType;
		this.status = status;
		this.part = part;
		this.amount = amount;
		this.verifyMd5 = verifyMd5;
		this.memberId = memberId;
		this.walletType = walletType;
	}

	/** full constructor */
	public AbstractLotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, Date createDateTime, PlanOrderStatus status,
			Integer part, Integer amount, Double posttaxPrize, Integer prizeSettleStatus, Date prizeSettleDateTime, Integer score,
			Integer experience, String verifyMd5, Date returnAmountDateTime, Integer memberId, WalletType walletType) {
		this.orderNo = orderNo;
		this.planNo = planNo;
		this.account = account;
		this.buyType = buyType;
		this.createDateTime = createDateTime;
		this.status = status;
		this.part = part;
		this.amount = amount;
		this.posttaxPrize = posttaxPrize;
		this.prizeSettleStatus = prizeSettleStatus;
		this.prizeSettleDateTime = prizeSettleDateTime;
		this.score = score;
		this.experience = experience;
		this.verifyMd5 = verifyMd5;
		this.returnAmountDateTime = returnAmountDateTime;
		this.memberId = memberId;
		this.walletType = walletType;
	}

	public AbstractLotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, Date createDateTime, PlanOrderStatus status,
			Integer part, Integer amount, Double posttaxPrize, Integer prizeSettleStatus, Date prizeSettleDateTime, Integer score,
			Integer experience, String verifyMd5, Date returnAmountDateTime, Integer memberId, WalletType walletType,String channel) {
		this.orderNo = orderNo;
		this.planNo = planNo;
		this.account = account;
		this.buyType = buyType;
		this.createDateTime = createDateTime;
		this.status = status;
		this.part = part;
		this.amount = amount;
		this.posttaxPrize = posttaxPrize;
		this.prizeSettleStatus = prizeSettleStatus;
		this.prizeSettleDateTime = prizeSettleDateTime;
		this.score = score;
		this.experience = experience;
		this.verifyMd5 = verifyMd5;
		this.returnAmountDateTime = returnAmountDateTime;
		this.memberId = memberId;
		this.walletType = walletType;
		this.channel = channel;
	}
	
	
	public AbstractLotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, Date createDateTime, PlanOrderStatus status,
			Integer part, Integer amount, Double posttaxPrize, Integer prizeSettleStatus, Date prizeSettleDateTime, Integer score,
			Integer experience, String verifyMd5, Date returnAmountDateTime, Integer memberId, WalletType walletType,
			MemberFollowingType followingType, String channel, String nickName) {
		super();
		this.orderNo = orderNo;
		this.planNo = planNo;
		this.account = account;
		this.buyType = buyType;
		this.createDateTime = createDateTime;
		this.status = status;
		this.part = part;
		this.amount = amount;
		this.posttaxPrize = posttaxPrize;
		this.prizeSettleStatus = prizeSettleStatus;
		this.prizeSettleDateTime = prizeSettleDateTime;
		this.score = score;
		this.experience = experience;
		this.verifyMd5 = verifyMd5;
		this.returnAmountDateTime = returnAmountDateTime;
		this.memberId = memberId;
		this.walletType = walletType;
		this.followingType = followingType;
		this.channel = channel;
		this.nickName = nickName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPlanNo() {
		return planNo;
	}

	public void setPlanNo(Integer planNo) {
		this.planNo = planNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BuyType getBuyType() {
		return buyType;
	}

	public void setBuyType(BuyType buyType) {
		this.buyType = buyType;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public PlanOrderStatus getStatus() {
		return status;
	}

	public void setStatus(PlanOrderStatus status) {
		this.status = status;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPosttaxPrize() {
		return posttaxPrize;
	}

	public void setPosttaxPrize(Double posttaxPrize) {
		this.posttaxPrize = posttaxPrize;
	}

	public Integer getPrizeSettleStatus() {
		return prizeSettleStatus;
	}

	public void setPrizeSettleStatus(Integer prizeSettleStatus) {
		this.prizeSettleStatus = prizeSettleStatus;
	}

	public Date getPrizeSettleDateTime() {
		return prizeSettleDateTime;
	}

	public void setPrizeSettleDateTime(Date prizeSettleDateTime) {
		this.prizeSettleDateTime = prizeSettleDateTime;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getVerifyMd5() {
		return verifyMd5;
	}

	public void setVerifyMd5(String verifyMd5) {
		this.verifyMd5 = verifyMd5;
	}

	public Date getReturnAmountDateTime() {
		return returnAmountDateTime;
	}

	public void setReturnAmountDateTime(Date returnAmountDateTime) {
		this.returnAmountDateTime = returnAmountDateTime;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	public MemberFollowingType getFollowingType() {
		return followingType;
	}

	public void setFollowingType(MemberFollowingType followingType) {
		this.followingType = followingType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}



}
