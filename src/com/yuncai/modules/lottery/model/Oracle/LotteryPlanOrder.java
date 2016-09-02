package com.yuncai.modules.lottery.model.Oracle;
import java.util.Date;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberFollowingType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;

public class LotteryPlanOrder extends AbstractLotteryPlanOrder implements java.io.Serializable{
	// Constructors
	/** default constructor */
	public LotteryPlanOrder() {
	}

	/** minimal constructor */
	public LotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, PlanOrderStatus status, Integer part, Integer amount,
			String verifyMd5, Integer memberId, WalletType walletType) {
		super(orderNo, planNo, account, buyType, status, part, amount, verifyMd5, memberId, walletType);
	}

	/** full constructor */
	public LotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, Date createDateTime, PlanOrderStatus status,
			Integer part, Integer amount, Double posttaxPrize, Integer prizeSettleStatus, Date prizeSettleDateTime, Integer score,
			Integer experience, String verifyMd5, Date returnAmountDateTime, Integer memberId, WalletType walletType) {
		super(orderNo, planNo, account, buyType, createDateTime, status, part, amount, posttaxPrize, prizeSettleStatus, prizeSettleDateTime, score,
				experience, verifyMd5, returnAmountDateTime, memberId, walletType);
	}
	
	/** full constructor */
	public LotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, Date createDateTime, PlanOrderStatus status,
			Integer part, Integer amount, Double posttaxPrize, Integer prizeSettleStatus, Date prizeSettleDateTime, Integer score,
			Integer experience, String verifyMd5, Date returnAmountDateTime, Integer memberId, WalletType walletType,String channel) {

		super(orderNo, planNo, account, buyType, createDateTime, status, part, amount, posttaxPrize, prizeSettleStatus, prizeSettleDateTime, score,
				experience, verifyMd5, returnAmountDateTime, memberId, walletType,channel);
	}	
	public LotteryPlanOrder(Integer orderNo, Integer planNo, String account, BuyType buyType, Date createDateTime, PlanOrderStatus status,
			Integer part, Integer amount, Double posttaxPrize, Integer prizeSettleStatus, Date prizeSettleDateTime, Integer score,
			Integer experience, String verifyMd5, Date returnAmountDateTime, Integer memberId, WalletType walletType,
			MemberFollowingType followingType, String channel, String nickName){
		super(orderNo, planNo, account, buyType, createDateTime, status, part, amount, posttaxPrize, prizeSettleStatus, prizeSettleDateTime, score, experience, verifyMd5, returnAmountDateTime, memberId, walletType, followingType, channel, nickName);
	}
}
