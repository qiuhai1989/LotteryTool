package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.member.MemberWalletLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;

public class MemberWalletLineServiceImpl extends BaseServiceImpl<MemberWalletLine, Integer> implements MemberWalletLineService {
	private MemberWalletLineDAO memberWalletLineDao;

	public void setMemberWalletLineDao(MemberWalletLineDAO memberWalletLineDao) {
		this.memberWalletLineDao = memberWalletLineDao;
	}



	@Override
	public Double countAmount(String account, Date startDate, Date endDate, List<WalletTransType> transTypes) {
		// TODO Auto-generated method stub
		return this.memberWalletLineDao.countAmount(account, startDate, endDate, transTypes);
	}


	@Override
	public List<MemberWalletLine> findByAccountAndDate(String account, Date begin, Date end, Integer order, Integer pageSize, Date date,
			List<WalletTransType> transTypes) {
		// TODO Auto-generated method stub
		return this.memberWalletLineDao.findByAccountAndDate(account, begin, end, order, pageSize, date, transTypes);
	}



	@Override
	public List<MemberWalletLine> findByAccountAndDate(String account, Date begin, Date end, Integer pageSize,
			List<WalletTransType> transTypes, int pageNo) {
		// TODO Auto-generated method stub
		return this.memberWalletLineDao.findByAccountAndDate(account, begin, end, pageSize, transTypes, pageNo);
	}



	@Override
	public List<Object[]> listMoney(String account, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return this.memberWalletLineDao.listMoney(account, startDate, endDate);
	}


}
