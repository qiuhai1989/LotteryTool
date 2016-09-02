package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtMatchDao;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;

public class FtMatchServiceImpl extends BaseServiceImpl<FtMatch, Integer> implements FtMatchService {

	private FtMatchDao ftMatchDao;

	public void setFtMatchDao(FtMatchDao ftMatchDao) {
		this.ftMatchDao = ftMatchDao;
	}

	@Override
	public List<FtMatch> findFtMatchs(String date) {
		return ftMatchDao.findFtMatchs(date);
	}

	@Override
	public List<FtMatch> findByDetachedCriteria(DetachedCriteria criteria) {
		return this.ftMatchDao.findMatchListBetweenIntTimes(criteria);
	}

	//获取赛事对阵
	@Override
	public List<FtMatch> findMatchListBetweenIntTimes(Integer beginIntTIme, Integer endIntTime) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FtMatch.class);
		criteria.add(Restrictions.ge("intTime", beginIntTIme));
		criteria.add(Restrictions.le("intTime", endIntTime));
		criteria.addOrder(Order.asc("intTime"));
		criteria.addOrder(Order.asc("lineId"));
		return this.ftMatchDao.findMatchListBetweenIntTimes(criteria);
	}
	
	public List<FtMatch> findMatchByStatus(Integer status) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FtMatch.class);
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("intTime"));
		criteria.addOrder(Order.asc("lineId"));
		return this.ftMatchDao.findByDetachedCriteria(criteria);
	}

	@Override
	public List<Object[]> findCurrentMatch() {
		// TODO Auto-generated method stub
		return this.ftMatchDao.findCurrentMatch();
	}

	@Override
	public String findMatchKey() {
		// TODO Auto-generated method stub
		return this.ftMatchDao.findMatchKey();
	}

	@Override
	public List<Object[]> findCurrentMatch(Integer initTime,Integer initTime2) {
		// TODO Auto-generated method stub
		return this.ftMatchDao.findCurrentMatch(initTime,initTime2);
	}

}
