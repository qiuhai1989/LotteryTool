package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.action.member.WalletLineSearch;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberChargeLineDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberWalletDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberWalletLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.CheapHepler;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.system.SystemErrorManager;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreLineDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberDao;


import com.yuncai.core.common.Constant;
import com.yuncai.core.common.DbDataVerify;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;

import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Sql.Users;

public class MemberWalletServiceImpl extends BaseServiceImpl<MemberWallet, Integer> implements MemberWalletService{

	private MemberWalletDAO memberWalletDao;
	
	private MemberWalletLineDAO memberWalletLineDao;

	private MemberScoreDAO memberScoreDao;

	private MemberScoreLineDAO memberScoreLineDao;
	private MemberChargeLineDAO memberChargeLineDAO;
	private MemberOperLineDAO memberOperLineDao;
	private MemberDao memberDao;
	private IpSeeker ipSeeker;
	

	/**
	 * 按照会员ID和钱包类型找到相应的钱包帐号
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public MemberWallet findByMemberIdAndWalletTypeForUpdate(final Integer memberId, final WalletType walletType) {
		return this.memberWalletDao.findByMemberIdAndWalletTypeForUpdate(memberId, walletType);
	}

	public MemberWallet findByMemberIdAndType(final Integer memberId, final WalletType walletType) {
		return this.memberWalletDao.findByMemberIdAndType(memberId, walletType);
	}
	
	//重置提现额度
	public void resetTakeCashQuota(Member member, Integer walletType,double quota)throws ServiceException{
		MemberWallet wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
		// #1.1验证数据是否正确
		if (DbDataVerify.checkMemberWalletVerify(wallet, wallet.getVerifyMd5()) && member.getStatus().intValue() == 0) {
			wallet.setTakeCashQuota(quota);
			wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
		} else {
			throw new ServiceException("4001", "系统故障,请联系客服人员!");
		}
		memberWalletDao.saveOrUpdate(wallet);
	}
	//增减提现额度
	public void increaseTakeCashQuota(Member member, Double amount, int walletType)throws ServiceException{
		MemberWallet wallet = null;
		if (walletType == WalletType.DUOBAO.getValue() || walletType == WalletType.ZENGJIN.getValue()) {
			wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
			// #1.1验证数据是否正确
			if (DbDataVerify.checkMemberWalletVerify(wallet, wallet.getVerifyMd5()) && member.getStatus().intValue() == 0) {
				if (amount.longValue()<0&&wallet.getTakeCashQuota().longValue() < amount.longValue()) {
					throw new ServiceException("2001", "对不起，超过限额!");
				}
				BigDecimal takeCashQuotaDec = BigDecimal.valueOf(wallet.getTakeCashQuota());
				BigDecimal amountDec = BigDecimal.valueOf(amount);
				wallet.setTakeCashQuota(takeCashQuotaDec.add(amountDec).doubleValue());
				wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
			} else {
				throw new ServiceException("4001", "系统故障,请联系客服人员!");
			}
			memberWalletDao.saveOrUpdate(wallet);
		}
	}
	public void increaseAble(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, int walletType,
			int transType, Integer agentId, String memo) throws ServiceException {
		// #1判断是否是本站帐号消费，否则不用进行钱包扣款
		MemberWallet wallet = null;
		if (walletType == WalletType.DUOBAO.getValue() || walletType == WalletType.ZENGJIN.getValue()) {
			wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
			// #1.1验证数据是否正确
			if (DbDataVerify.checkMemberWalletVerify(wallet, wallet.getVerifyMd5()) && member.getStatus().intValue() == 0) {

				BigDecimal ableDec = BigDecimal.valueOf(wallet.getAbleBalance());
				BigDecimal amountDec = BigDecimal.valueOf(amount);
				//中奖金额
				BigDecimal prizeDec = BigDecimal.valueOf(wallet.getHeapPrize());
				
				wallet.setAbleBalance(ableDec.add(amountDec).doubleValue());
				if(transType==WalletTransType.RETURN_PRIZE.getValue()){
					wallet.setTakeCashQuota(wallet.getTakeCashQuota()+amount);
					wallet.setHeapPrize(prizeDec.add(amountDec).doubleValue()); //中奖金额
				}
				//注册增送||彩金上赠送
				if(transType == WalletTransType.REG_PRESENT.getValue() || transType==WalletTransType.CARD_PRESENT.getValue()){
					wallet.setHeapGold(wallet.getHeapGold()+amount);
				}
				
				wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
			} else {
				throw new ServiceException("4001", "系统故障,请联系客服人员!");
			}
			memberWalletDao.saveOrUpdate(wallet);
		}
		// 保存钱包交易流水
		MemberWalletLine walletLine = new MemberWalletLine();
		walletLine.setAccount(member.getAccount());
		walletLine.setSourceId(member.getSourceId());
		walletLine.setAmount(amount > 0 ? amount : -amount);
		walletLine.setCreateDateTime(new Date());
		walletLine.setLotteryType(LotteryType.getItem(lotteryType.intValue()));
		walletLine.setMemberId(member.getId());
		walletLine.setOperLineNo(operLineNo);
		walletLine.setOrderNo(orderNo);
		walletLine.setPlanNo(planNo);
		walletLine.setStatus(new Integer(0));
		walletLine.setTransType(WalletTransType.getItem(transType));
		walletLine.setWalletType(WalletType.getItem(walletType));
		walletLine.setAbleBalance(wallet.getAbleBalance());
		walletLine.setFreezeBalance(wallet.getFreezeBalance());
		walletLine.setHeapBalance(wallet.getHeapBalance());
		walletLine.setHeapPrize(wallet.getHeapPrize());
		walletLine.setHeapGold(wallet.getHeapGold());
		walletLine.setRemark(memo);
		walletLine.setVerifyMd5(DbDataVerify.getMemberWalletLineVerify(walletLine));
		memberWalletLineDao.save(walletLine);
		try{
			ErrorType errorType=null;
			if(transType==WalletTransType.CONSUME.getValue()){
				errorType=ErrorType.购彩;
			}else if(transType==WalletTransType.CHARGE.getValue()){
				errorType=ErrorType.充值;
			}else if(transType==WalletTransType.SUBSTRACT.getValue()){
				errorType=ErrorType.提现;
			}
			if(errorType!=null&&Constant.VirtualAccount.indexOf(member.getAccount())==-1){
				SystemErrorManager.addError(errorType,LotteryType.getItem(lotteryType.intValue()),null,walletLine.getWalletLineNo()+"",member.getAccount(),amount,member.getAccount()+WalletTransType.getItem(transType).getName()+amount, null);
			}
		}catch (Exception e) {}
		
	}
	
	//消费
	public double consume(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, Integer walletType,
			Integer sourceId, String memo,Integer planType,Integer selectStatus,Integer change) throws ServiceException {
		// #1判断是否是本站帐号消费，否则不用进行钱包扣款
		MemberWallet wallet = null;
		double quotaAmount=0d;
		if (walletType == WalletType.DUOBAO.getValue() || walletType == WalletType.ZENGJIN.getValue()) {
			wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
			// #1.1验证数据是否正确
			if (DbDataVerify.checkMemberWalletVerify(wallet, wallet.getVerifyMd5()) && member.getStatus().intValue() == 0) {

				if(wallet.getAbleBalance().doubleValue()<=0d){
					throw new ServiceException("4002", "对不起，您的余额不足");
				}
				if (wallet.getAbleBalance().longValue() < amount.longValue()) {
					throw new ServiceException("4002", "对不起，您的余额不足!");
				}

				BigDecimal ableDec = BigDecimal.valueOf(wallet.getAbleBalance());
				BigDecimal heapDec = BigDecimal.valueOf(wallet.getHeapBalance());
				BigDecimal amountDec = BigDecimal.valueOf(amount);

				wallet.setAbleBalance(ableDec.subtract(amountDec).doubleValue());
				wallet.setHeapBalance(heapDec.add(amountDec).doubleValue());
				//重算提款额度，不超过余额加冻结
//				BigDecimal left_amount = ( BigDecimal.valueOf(wallet.getAbleBalance()) ).add(BigDecimal.valueOf( wallet.getFreezeBalance()) )
//				.subtract(BigDecimal.valueOf(wallet.getHeapGold()==null ? 0:wallet.getHeapGold()));
//				if(wallet.getTakeCashQuota().doubleValue()> left_amount.doubleValue() ){
//					wallet.setTakeCashQuota( left_amount.doubleValue() );
//				}
//				
//				BigDecimal left_amount = (BigDecimal.valueOf(wallet.getTakeCashQuota())).subtract(amountDec);
//				//.add(BigDecimal.valueOf( wallet.getFreezeBalance()) );
//				if(left_amount.doubleValue() >0 ){
//					wallet.setTakeCashQuota( left_amount.doubleValue() );
//				}else{
//					wallet.setTakeCashQuota(0d);
//				}
				
				
				//处理提款金额 如果是合买就不进行操作,因为在冻结金额已做处理
				if(planType==null || planType!=2){
					BigDecimal left_amount = (BigDecimal.valueOf(wallet.getTakeCashQuota())).subtract(amountDec);
					
					if(left_amount.doubleValue() >0 ){
						wallet.setTakeCashQuota( left_amount.doubleValue() );
						quotaAmount=amountDec.doubleValue();
					}else{
						quotaAmount=wallet.getTakeCashQuota();
						wallet.setTakeCashQuota(0d);
					}
				}
					
				wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
			} else {
				throw new ServiceException("4001", "系统故障,请联系客服人员!");
			}
			memberWalletDao.saveOrUpdate(wallet);
			
		}
		// 保存钱包交易流水
		MemberWalletLine walletLine = new MemberWalletLine();
		walletLine.setAccount(member.getAccount());
		walletLine.setSourceId(member.getSourceId());
		walletLine.setAmount(amount);
		walletLine.setCreateDateTime(new Date());
		walletLine.setLotteryType(LotteryType.getItem(lotteryType.intValue()));
		walletLine.setMemberId(member.getId());
		walletLine.setOperLineNo(operLineNo);
		walletLine.setOrderNo(orderNo);
		walletLine.setPlanNo(planNo);
		walletLine.setStatus(new Integer(0));
		walletLine.setTransType(WalletTransType.CONSUME);
		walletLine.setWalletType(WalletType.getItem(walletType));
		walletLine.setSourceId(sourceId);
		walletLine.setAbleBalance(wallet.getAbleBalance());
		walletLine.setFreezeBalance(wallet.getFreezeBalance());
		walletLine.setHeapBalance(wallet.getHeapBalance());
		walletLine.setHeapPrize(wallet.getHeapPrize());
		walletLine.setHeapGold(wallet.getHeapGold());
		walletLine.setRemark(memo);
		walletLine.setVerifyMd5(DbDataVerify.getMemberWalletLineVerify(walletLine));
		walletLine.setSelectStatus(selectStatus);
		memberWalletLineDao.save(walletLine);
		

		if((planType!=null && planType==2) && (change!=null && change!=0)){
			//指针对合买
			//更新原有冻结情况状态(这个指是针对合买的)
			WalletLineSearch search=new WalletLineSearch();
			search.setPlanNo(planNo);
			search.setTransType(WalletTransType.FREEZE.getValue());
			search.setAccount(member.getAccount());
			List<MemberWalletLine> listLine=this.memberWalletLineDao.findBySelectStatus(search);
			if(listLine !=null && !listLine.isEmpty()){
				for(MemberWalletLine l:listLine){
					l.setSelectStatus(1);
					this.memberWalletLineDao.saveOrUpdate(l);
				}
			}
		}
		

		try{
			if(Constant.VirtualAccount.indexOf(member.getAccount())==-1){
				SystemErrorManager.addError(ErrorType.购彩,LotteryType.getItem(lotteryType.intValue()),null,walletLine.getWalletLineNo()+"",member.getAccount(),amount,member.getAccount()+WalletTransType.CONSUME.getName()+amount, null);
			}
		}catch (Exception e) {}
		
		return quotaAmount;
	}

	//冻结
	public double freeze(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, Integer walletType,
			Integer SourceId, String memo,Integer planType,Integer selectStatus) throws ServiceException {
		// #1判断是否是本站帐号消费，否则不用进行钱包扣款
		MemberWallet wallet = null;
		Users user = null;//SQL数据库中对应的记录
		double quotaAmount=0d;
		if (walletType == WalletType.DUOBAO.getValue() || walletType == WalletType.ZENGJIN.getValue()) {
			wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
			// #1.1验证数据是否正确
			if (DbDataVerify.checkMemberWalletVerify(wallet, wallet.getVerifyMd5()) && member.getStatus().intValue() == 0) {
				if(amount.doubleValue()<0){
					throw new ServiceException("4002", "对不起，冻结金额不能小于0!");
				}
				if (amount.doubleValue() - wallet.getAbleBalance().doubleValue() > 0.001 ) {
					throw new ServiceException("4002", "对不起，您的余额不足!");
				}
				BigDecimal ableDec = BigDecimal.valueOf(wallet.getAbleBalance());
				BigDecimal freeDec = BigDecimal.valueOf(wallet.getFreezeBalance());
				BigDecimal amountDec = BigDecimal.valueOf(amount);
				wallet.setAbleBalance(ableDec.subtract(amountDec).doubleValue());
				wallet.setFreezeBalance(freeDec.add(amountDec).doubleValue());
				wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
				
				//处理提款金额
				if(planType!=null && planType==2){
					BigDecimal left_amount = (BigDecimal.valueOf(wallet.getTakeCashQuota())).subtract(amountDec);
					if(left_amount.doubleValue() >0 ){
						wallet.setTakeCashQuota( left_amount.doubleValue() );
						quotaAmount=amountDec.doubleValue();
					}else{
						quotaAmount=wallet.getTakeCashQuota();
						wallet.setTakeCashQuota(0d);
					}
				}
				
			} else {
				throw new ServiceException("4001", "系统故障,请联系客服人员!");
			}
			memberWalletDao.saveOrUpdate(wallet);
		}

		// 保存钱包交易流水
		MemberWalletLine walletLine = new MemberWalletLine();
		walletLine.setAccount(member.getAccount());
		walletLine.setSourceId(member.getSourceId());
		walletLine.setAmount(amount);
		walletLine.setCreateDateTime(new Date());
		walletLine.setLotteryType(LotteryType.getItem(lotteryType.intValue()));
		walletLine.setMemberId(member.getId());
		walletLine.setOperLineNo(operLineNo);
		walletLine.setOrderNo(orderNo);
		walletLine.setPlanNo(planNo);
		walletLine.setStatus(new Integer(0));
		walletLine.setTransType(WalletTransType.FREEZE);
		walletLine.setWalletType(WalletType.getItem(walletType));
		walletLine.setSourceId(SourceId);
		walletLine.setAbleBalance(wallet.getAbleBalance());
		walletLine.setFreezeBalance(wallet.getFreezeBalance());
		walletLine.setHeapBalance(wallet.getHeapBalance());
		walletLine.setHeapPrize(wallet.getHeapPrize());
		walletLine.setHeapGold(wallet.getHeapGold());
		walletLine.setRemark(memo);
		walletLine.setVerifyMd5(DbDataVerify.getMemberWalletLineVerify(walletLine));
		walletLine.setSelectStatus(selectStatus); //针对冻结的
		memberWalletLineDao.save(walletLine);
		
		return quotaAmount;
	}

	public void freezeToAble(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer sourceId, String memo,Integer buyType,Integer change,double quotaAmount) throws ServiceException {
		// #1判断是否是本站帐号消费，否则不用进行钱包操作
		MemberWallet wallet = null;
		if (walletType == WalletType.DUOBAO.getValue() || walletType == WalletType.ZENGJIN.getValue()) {
			wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
			// #1.1验证数据是否正确
			if (DbDataVerify.checkMemberWalletVerify(wallet, wallet.getVerifyMd5())) {

				if (wallet.getFreezeBalance() < amount.longValue()) {
					throw new ServiceException("4002", "对不起，您的冻结余额不足!");
				}
				
				BigDecimal ableDec = BigDecimal.valueOf(wallet.getAbleBalance());
				BigDecimal freeDec = BigDecimal.valueOf(wallet.getFreezeBalance());
				BigDecimal amountDec = BigDecimal.valueOf(amount);
				wallet.setAbleBalance(ableDec.add(amountDec).doubleValue());
				wallet.setFreezeBalance(freeDec.subtract(amountDec).doubleValue());
				wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
				
				//返款(保底,撤单,追号)
				if(buyType!=null && buyType==2){
					BigDecimal left_amount = (BigDecimal.valueOf(wallet.getTakeCashQuota())).add(amountDec);
					if(buyType==2){
						BigDecimal amountDec_ = BigDecimal.valueOf(quotaAmount);
						left_amount=(BigDecimal.valueOf(wallet.getTakeCashQuota())).add(amountDec_);
					}
					if(left_amount.doubleValue() >0 ){
						wallet.setTakeCashQuota( left_amount.doubleValue() );
					}else{
						wallet.setTakeCashQuota(0d);
					}
				}
				
			} else {
				throw new ServiceException("4001", "系统故障,请联系客服人员!");
			}
			memberWalletDao.saveOrUpdate(wallet);
		}
		
		// 保存钱包交易流水
		MemberWalletLine walletLine = new MemberWalletLine();
		walletLine.setAccount(member.getAccount());
		walletLine.setSourceId(sourceId);//
		walletLine.setAmount(amount);
		walletLine.setCreateDateTime(new Date());
		walletLine.setLotteryType(LotteryType.getItem(lotteryType.intValue()));
		walletLine.setMemberId(member.getId());
		walletLine.setOperLineNo(operLineNo);
		walletLine.setOrderNo(orderNo);
		walletLine.setPlanNo(planNo);
		walletLine.setStatus(new Integer(0));
		walletLine.setTransType(WalletTransType.UN_FREEZE);
		walletLine.setWalletType(WalletType.getItem(walletType));
		walletLine.setSourceId(sourceId);
		walletLine.setAbleBalance(wallet.getAbleBalance());
		walletLine.setFreezeBalance(wallet.getFreezeBalance());
		walletLine.setHeapBalance(wallet.getHeapBalance());
		walletLine.setHeapPrize(wallet.getHeapPrize());
		walletLine.setHeapGold(wallet.getHeapGold());
		walletLine.setRemark(memo);
		walletLine.setVerifyMd5(DbDataVerify.getMemberWalletLineVerify(walletLine));
		if(change!=null && change==0){
			walletLine.setSelectStatus(0);
		}else{
			walletLine.setSelectStatus(1);
		}
		memberWalletLineDao.save(walletLine);
		

		//更新原有冻结情况状态(这个针对撤单,流单)
		if((change==null || change!=0)){
			WalletLineSearch search=new WalletLineSearch();
			search.setPlanNo(planNo);
			search.setTransType(WalletTransType.FREEZE.getValue());
			search.setAccount(member.getAccount());
			List<MemberWalletLine> listLine=this.memberWalletLineDao.findBySelectStatus(search);
			if(listLine !=null && !listLine.isEmpty()){
				for(MemberWalletLine l:listLine){
					l.setSelectStatus(1);
					this.memberWalletLineDao.saveOrUpdate(l);
				}
			}
		}
	}

	public void freezeToConsume(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer SourceId, String memo,Integer planType,Integer selectStatus,Integer change) throws ServiceException {
		freezeToAble(member, walletType, amount, lotteryType, planNo, orderNo, operLineNo, SourceId, memo,null,change,0d);
		cheapConsume(member, amount, lotteryType, planNo, orderNo, operLineNo, walletType, SourceId, memo,planType,selectStatus,change);
		
	}

	public void freezeToConsumeNoScore(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer SourceId, String memo) throws ServiceException {
		freezeToAble(member, walletType, amount, lotteryType, planNo, orderNo, operLineNo, SourceId, memo,null,null,0d);
		increaseAble(member, -amount, lotteryType, planNo, orderNo, operLineNo, walletType, WalletTransType.CONSUME.getValue(), SourceId, memo);
		
	}

	public void heapToAble(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer sourceId, String memo) throws ServiceException {
		// #1判断是否是本站帐号消费，否则不用进行钱包操作
		MemberWallet wallet = null;
		if (walletType == WalletType.DUOBAO.getValue() || walletType == WalletType.ZENGJIN.getValue()) {
			wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(member.getId(), WalletType.getItem(walletType));
			// #1.1验证数据是否正确
			if (DbDataVerify.checkMemberWalletVerify(wallet, wallet.getVerifyMd5())) {

				if (wallet.getHeapBalance() < amount.longValue()) {
					throw new ServiceException("4002", "对不起，消费金额不足");
				}
				BigDecimal ableDec = BigDecimal.valueOf(wallet.getAbleBalance());
				BigDecimal heapDec = BigDecimal.valueOf(wallet.getHeapBalance());
				BigDecimal amountDec = BigDecimal.valueOf(amount);

				wallet.setAbleBalance(ableDec.add(amountDec).doubleValue());
				wallet.setHeapBalance(heapDec.subtract(amountDec).doubleValue());
				wallet.setVerifyMd5(DbDataVerify.getMemberWalletVerify(wallet));
				
			} else {
				throw new ServiceException("4001", "系统故障,请联系客服人员!");
			}
			memberWalletDao.saveOrUpdate(wallet);
		}

		// 保存钱包交易流水
		MemberWalletLine walletLine = new MemberWalletLine();
		walletLine.setAccount(member.getAccount());
		walletLine.setAmount(amount);
		walletLine.setCreateDateTime(new Date());
		walletLine.setLotteryType(LotteryType.getItem(lotteryType.intValue()));
		walletLine.setMemberId(member.getId());
		walletLine.setOperLineNo(operLineNo);
		walletLine.setOrderNo(orderNo);
		walletLine.setPlanNo(planNo);
		walletLine.setStatus(new Integer(0));
		walletLine.setTransType(WalletTransType.RETURN);
		walletLine.setWalletType(WalletType.getItem(walletType));
		walletLine.setSourceId(sourceId);
		walletLine.setAbleBalance(wallet.getAbleBalance());
		walletLine.setFreezeBalance(wallet.getFreezeBalance());
		walletLine.setHeapBalance(wallet.getHeapBalance());
		walletLine.setHeapPrize(wallet.getHeapPrize());
		walletLine.setHeapGold(wallet.getHeapGold());
		walletLine.setRemark(memo);
		walletLine.setVerifyMd5(DbDataVerify.getMemberWalletLineVerify(walletLine));
		memberWalletLineDao.save(walletLine);
		
		
	}

	//查询过期的彩金赠送用户
	public List findRegPresentforRemove(final Date startDate, final Date endDate, final WalletType walletType) {
		List<MemberWalletLine> walletLineList = null;//memberWalletLineDAO.findFailureRegPresent(startDate, endDate);
		return walletLineList;
	}

	// 移除赠送的资金
	public void removePresent(final int memberId, final WalletType walletType) throws ServiceException {
		MemberWallet wallet = memberWalletDao.findByMemberIdAndWalletTypeForUpdate(memberId, walletType);
		Member member = memberDao.find(Member.class, memberId);
		if (wallet.getAbleBalance() > 0) {
			LogUtil.out("-------- 清除注册送的彩金记录，用户：" + wallet.getAccount() + " "+ wallet.getAbleBalance());
			increaseAble(member, - wallet.getAbleBalance(), LotteryType.NENO.getValue(), 0, 0, 0, WalletType.DUOBAO.getValue(), WalletTransType.PRESENT_REMOVE
					.getValue(), member.getSourceId(), "彩金过期");
		}
	}

	//提款时的钱包操作
	public void drawing(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, int walletType,
			Integer agentId, String memo) throws ServiceException {
		freezeToAble(member, walletType, amount, lotteryType, planNo, orderNo, operLineNo, member.getSourceId(), memo,null,new Integer(0),0d);
		increaseAble(member, -amount, lotteryType, planNo, orderNo, operLineNo, walletType, WalletTransType.SUBSTRACT.getValue(), agentId, memo);
	}
	
	//优惠消费接口 add 2013-05-16 
	public double cheapConsume(Member member, Double amount, Integer lotteryType, Integer planNo, Integer orderNo, Integer operLineNo, Integer walletType,
			Integer sourceId, String memo,Integer planType,Integer selectStatus,Integer change) throws ServiceException {
		Double cheapAmount = CheapHepler.getCheapAmount(lotteryType.intValue(),amount);//优惠金额
		String lotteryTypeName = LotteryType.getItem(lotteryType.intValue()).getName();
		
		if(cheapAmount.doubleValue() > 0) {
			increaseAble(member, cheapAmount, lotteryType, planNo, orderNo, operLineNo, walletType,
				WalletTransType.CHEAP_PRESENT.getValue(), sourceId, lotteryTypeName+"优惠赠送:"+ NumberTools.doubleToMoneyString(cheapAmount) + "元");
		}
		
		double left_amount=consume(member,amount,lotteryType,planNo,orderNo,operLineNo,walletType,sourceId,memo,planType,selectStatus,change);
		
		return left_amount;
	}
	
	//优惠累加接口 add 2011-08-16
	public void cheapHeapToAble(Member member, Integer walletType, Double amount, Integer lotteryType, Integer planNo, Integer orderNo,
			Integer operLineNo, Integer sourceId, String memo) throws ServiceException {
		Double cheapAmount = CheapHepler.getCheapAmount(lotteryType.intValue(),amount);//优惠金额

		heapToAble(member, walletType, amount,lotteryType, planNo, orderNo, operLineNo, sourceId, memo);
		
		if(cheapAmount.doubleValue() > 0) {
			increaseAble(member, cheapAmount, lotteryType, planNo, orderNo, operLineNo, walletType,
					WalletTransType.PRESENT_REMOVE.getValue(), sourceId, memo);
		}
	}

	public void attachClean(MemberWallet instance) {
		memberWalletDao.attachClean(instance);
	}

	
	public void saves(MemberWallet transientInstance) {
		this.memberWalletDao.saves(transientInstance);
		
	}
	


	public MemberWallet findByAccount(Object account) {
		List list = memberWalletDao.findByProperty(MemberWallet.class, "account", account);
		if (list == null || list.size() == 0) {
			return null;
		}
		return (MemberWallet) list.get(0);
	}

	

	public void update(MemberWallet memberWallet) {
		this.memberWalletDao.update(memberWallet);
		
	}

	public void setMemberWalletDao(MemberWalletDAO memberWalletDao) {
		this.memberWalletDao = memberWalletDao;
	}

	public void setMemberWalletLineDao(MemberWalletLineDAO memberWalletLineDao) {
		this.memberWalletLineDao = memberWalletLineDao;
	}

	public void setMemberScoreDao(MemberScoreDAO memberScoreDao) {
		this.memberScoreDao = memberScoreDao;
	}

	public void setMemberScoreLineDao(MemberScoreLineDAO memberScoreLineDao) {
		this.memberScoreLineDao = memberScoreLineDao;
	}

	public void setMemberChargeLineDAO(MemberChargeLineDAO memberChargeLineDAO) {
		this.memberChargeLineDAO = memberChargeLineDAO;
	}

	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

}
