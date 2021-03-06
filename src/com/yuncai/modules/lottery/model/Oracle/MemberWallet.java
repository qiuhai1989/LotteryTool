package com.yuncai.modules.lottery.model.Oracle;

import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;

public class MemberWallet extends AbstractMemberWallet implements java.io.Serializable{
	// Constructors

	/** default constructor */
	public MemberWallet() {
	}

	/** minimal constructor */
	public MemberWallet(String account, Double ableBalance, Double freezeBalance, Double heapBalance, Double heapPrize, String verifyMd5) {
		super(account, ableBalance, freezeBalance, heapBalance, heapPrize, verifyMd5);
	}

	/** full constructor */
	public MemberWallet(Integer memberId, String account, Double ableBalance, Double freezeBalance, Double heapBalance, Double heapPrize,
			String verifyMd5, WalletType walletType) {
		super(memberId, account, ableBalance, freezeBalance, heapBalance, heapPrize, verifyMd5, walletType);
	}

}
