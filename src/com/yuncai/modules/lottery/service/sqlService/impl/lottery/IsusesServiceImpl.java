package com.yuncai.modules.lottery.service.sqlService.impl.lottery;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.lottery.IsusesDAO;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;

public class IsusesServiceImpl extends BaseServiceImpl<Isuses, Integer> implements IsusesService {

	private IsusesDAO sqlIsusesDAO;

	@Override
	public Isuses findByLotteryTypeAndTerm(LotteryType lotType, String term) {

		return this.sqlIsusesDAO.findByLotteryTypeAndTerm(lotType, term);
	}

	public void setSqlIsusesDAO(IsusesDAO sqlIsusesDAO) {
		this.sqlIsusesDAO = sqlIsusesDAO;
	}

	@Override
	public Isuses findByIsusesTerm(LotteryType lottery, String termID) {

		return this.sqlIsusesDAO.findByIsusesTerm(lottery, termID);
	}

	@Override
	public Isuses findNextByLotteryTypeAndTerm(LotteryType lottery, String term) {

		return this.sqlIsusesDAO.findNextByLotteryTypeAndTerm(lottery, term);
	}

	@Override
	public Isuses findCurrentTermByDate(LotteryType lotteryType, Date nowDate) {
		// TODO Auto-generated method stub
		return this.sqlIsusesDAO.findCurrentTermByDate(lotteryType, nowDate);
	}

	@Override
	public Isuses findPreByLotteryTypeAndTerm(LotteryType lotteryType, String name) {
		// TODO Auto-generated method stub
		return this.sqlIsusesDAO.findPreByLotteryTypeAndTerm(lotteryType, name);
	}

	@Override
	public List<Isuses> findOverdueByDateAndUnReturnPrizeTerm(LotteryType lotteryType) {
		// TODO Auto-generated method stub
		return this.sqlIsusesDAO.findOverdueByDateAndUnReturnPrizeTerm(lotteryType);
	}

	@Override
	public void initCurrentFlag(LotteryType lotteryType) {
		// TODO Auto-generated method stub
		this.sqlIsusesDAO.initCurrentFlag(lotteryType);
	}

	@Override
	public List<Isuses> FindPrizeHistory(Integer lotteryId, String term, Integer offset, Integer order, Integer pageSize) {

		return this.sqlIsusesDAO.FindPrizeHistory(lotteryId, term, offset, order, pageSize);
	}

	@Override
	public List<Isuses> findCurrentTermList(Integer lotteryType) {
		// TODO Auto-generated method stub
		return this.sqlIsusesDAO.findCurrentTermList(lotteryType);
	}

	@Override
	public List<Isuses> findAfterTerms(Integer lotteryId, Integer count,String currentTerm) {
		// TODO Auto-generated method stub
		return this.sqlIsusesDAO.findAfterTerms(lotteryId, count,currentTerm);
	}

	@Override
	public List<Isuses> findCurrentTermList(List<Integer> list) {
		// TODO Auto-generated method stub
		return this.sqlIsusesDAO.findCurrentTermList(list);
	}

	@Override
	public List<Isuses> findCurrentTermList2() {
		// TODO Auto-generated method stub
		return this.sqlIsusesDAO.findCurrentTermList2();
	}
	
	@Override
	public List<Isuses> findPre100OpenPrizeTerm(LotteryType lotteryType){
		return this.sqlIsusesDAO.findPre100OpenPrizeTerm(lotteryType);
	}
}
