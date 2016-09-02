package com.yuncai.modules.lottery.service.oracleService.Impl.baskboll;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.yuncai.modules.lottery.dao.oracleDao.baskboll.BkMatchDAO;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.baskboll.BkMatchService;

public class BkMatchServiceImpl extends BaseServiceImpl<BkMatch, Integer> implements BkMatchService {
	private BkMatchDAO bkMatchDAO;

	public void setBkMatchDAO(BkMatchDAO bkMatchDAO) {
		this.bkMatchDAO = bkMatchDAO;
	}

	@Override
	public List<BkMatch> findFtMatchs(String date) {
		
		return this.bkMatchDAO.findFtMatchs(date);
	}

	@Override
	public List<BkMatch> findByDetachedCriteria(DetachedCriteria criteria) {
		
		return this.bkMatchDAO.findMatchListBetweenIntTimes(criteria);
	}

	//获取赛事对阵
	@Override
	public List<BkMatch> findMatchListBetweenIntTimes(Integer beginIntTIme,Integer endIntTime) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BkMatch.class);
		criteria.add(Restrictions.ge("intTime", beginIntTIme));
		criteria.add(Restrictions.le("intTime", endIntTime));
		criteria.addOrder(Order.asc("intTime"));
		criteria.addOrder(Order.asc("lineId"));
		return this.bkMatchDAO.findMatchListBetweenIntTimes(criteria);
	}

	@Override
	public List<BkMatch> findMatchByStatus(Integer status) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BkMatch.class);
		criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("intTime"));
		criteria.addOrder(Order.asc("lineId"));
		return this.bkMatchDAO.findByDetachedCriteria(criteria);
	}

	@Override
	public List<BkMatch> findMatchsByNoStart() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BkMatch.class);
		criteria.add(Restrictions.ge("startTime", new Date()));
		criteria.addOrder(Order.asc("mid"));
		return this.bkMatchDAO.findMatchListBetweenIntTimes(criteria);
	}

	@Override
	public List<Object[]> findMatchAndPassRate(List<Integer> ids) {
		// TODO Auto-generated method stub
		return this.bkMatchDAO.findMatchAndPassRate(ids);
	}

	@Override
	public List<Object[]> findMatchAndPassRate(List<Integer> intTimes, List<String> lineIds) {
		// TODO Auto-generated method stub
		return this.bkMatchDAO.findMatchAndPassRate(intTimes,lineIds);
	}

	@Override
	public List<Object[]> findMatchAndPassRateByIntTimes(List<Integer> intTimes) {
		// TODO Auto-generated method stub
		return this.bkMatchDAO.findMatchAndPassRateByIntTimes(intTimes);
	}

}

