package com.yuncai.modules.lottery.service.oracleService.member;

import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.BaseService;
import java.util.*;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.model.Oracle.Member;

public interface MemberWalletService extends BaseService<MemberWallet, Integer> {
	
	// 移除赠送的资金
	public void removePresent(final int memberId, final WalletType walletType) throws ServiceException;
	//查询过期的彩金赠送用户
	public List findRegPresentforRemove(final Date startDate, final Date endDate, final WalletType walletType);
	
	public MemberWallet findByAccount(Object account);

	/**
	 * 按照会员ID和钱包类型找到相应的钱包帐号
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public MemberWallet findByMemberIdAndWalletTypeForUpdate(final Integer memberId, final WalletType walletType);

	public MemberWallet findByMemberIdAndType(final Integer memberId, final WalletType walletType);
	
	/**
	 * 增加或减少提现额度
	 * @param member
	 * @param amount
	 * @param walletType
	 * @throws ServiceException
	 */
	public void increaseTakeCashQuota(Member member, Double amount, int walletType)throws ServiceException;
	
	public void increaseAble(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, int walletType,
			int transType, Integer agentId, String memo) throws ServiceException;
	/**
	 * 提款请求已汇款需解冻提款金额且从余额中扣除提款金额
	 * @param member
	 * @param amount
	 * @param lotteryType
	 * @param planNo
	 * @param orderNo
	 * @param operLineNo
	 * @param walletType
	 * @param agentId
	 * @param memo
	 * @throws ServiceException
	 */
	public void drawing(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, int walletType,
			Integer agentId, String memo) throws ServiceException;
	
	public double consume(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, Integer walletType,
			Integer sourceId, String memo,Integer planType,Integer selectStatus,Integer change) throws ServiceException;

	public double freeze(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, Integer walletType,
			Integer SourceId, String memo,Integer planType,Integer selectStatus) throws ServiceException;

	public void freezeToAble(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer sourceId, String memo,Integer buyType,Integer change,double quotaAmount) throws ServiceException;

	public void freezeToConsume(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer SourceId, String memo,Integer planType,Integer selectStatus,Integer change) throws ServiceException;

	public void freezeToConsumeNoScore(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer SourceId, String memo) throws ServiceException;

	public void heapToAble(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer sourceId, String memo) throws ServiceException;

	//优惠消费接口 add 2011-08-16 
	public double cheapConsume(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, Integer walletType,
			Integer sourceId, String memo,Integer planType,Integer selectStatus,Integer change) throws ServiceException;

	//优惠累加接口 add 2011-08-16
	public void cheapHeapToAble(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer sourceId, String memo) throws ServiceException;
	/**
	 * 重置提款额度
	 * @param member
	 * @param walletType
	 * @param quota
	 * @throws ServiceException
	 */
	public void resetTakeCashQuota(Member member, Integer walletType,double quota)throws ServiceException;
	
	
	
	public abstract void saves(MemberWallet transientInstance);
	
	public abstract void attachClean(MemberWallet instance);
	//修改钱包
	public abstract void update(MemberWallet memberWallet);
}
