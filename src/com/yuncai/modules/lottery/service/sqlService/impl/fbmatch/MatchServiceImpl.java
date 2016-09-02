package com.yuncai.modules.lottery.service.sqlService.impl.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.fbmatch.MatchDao;
import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.MatchService;

public class MatchServiceImpl extends BaseServiceImpl<Match, Integer> implements MatchService {

	private MatchDao matchDao;

	public void setMatchDao(MatchDao matchDao) {
		this.matchDao = matchDao;
	}

	@Override
	public List<Match> findMatchList(List<Integer> ids) {
		// TODO Auto-generated method stub
		return matchDao.findMatchList(ids);
	}

	@Override
	public List<Object[]> findMatchAndPassRate(List<Integer> ids) {
		// TODO Auto-generated method stub
		return matchDao.findMatchAndPassRate(ids);
	}

}
