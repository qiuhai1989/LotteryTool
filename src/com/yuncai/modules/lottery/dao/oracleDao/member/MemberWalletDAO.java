package com.yuncai.modules.lottery.dao.oracleDao.member;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;

public interface MemberWalletDAO extends GenericDao<MemberWallet, Integer> {
	public MemberWallet findByMemberIdAndType(final Integer memberId, final WalletType walletType);

	/**
	 * 按照会员ID和钱包类型找到相应的钱包帐号
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public MemberWallet findByMemberIdAndWalletTypeForUpdate(final Integer memberId, final WalletType walletType);

	public abstract void saves(MemberWallet transientInstance);
	public abstract void update(MemberWallet transientInstance);
	public abstract void attachClean(MemberWallet instance);
	
}
