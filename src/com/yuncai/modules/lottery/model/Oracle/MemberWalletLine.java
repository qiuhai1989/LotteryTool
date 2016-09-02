package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;

public class MemberWalletLine extends AbstractMemberWalletLine implements java.io.Serializable{
	// Constructors

	/** default constructor */
	public MemberWalletLine() {
	}

	/** minimal constructor */
	public MemberWalletLine(Integer walletLineNo, String account, Integer memberId, WalletTransType transType, WalletType walletType, Double amount,
			Integer status, String verifyMd5, Integer sourceId) {
		super(walletLineNo, account, memberId, transType, walletType, amount, status, verifyMd5, sourceId);
	}

	/** full constructor */
	public MemberWalletLine(Integer walletLineNo, Integer planNo, Integer orderNo, Integer operLineNo, String account, Integer memberId,
			WalletTransType transType, WalletType walletType, Double amount, LotteryType lotteryType, Date createDateTime, Integer status,
			String verifyMd5, Integer sourceId) {
		super(walletLineNo, planNo, orderNo, operLineNo, account, memberId, transType, walletType, amount, lotteryType, createDateTime, status,
				verifyMd5, sourceId);
	}

}
