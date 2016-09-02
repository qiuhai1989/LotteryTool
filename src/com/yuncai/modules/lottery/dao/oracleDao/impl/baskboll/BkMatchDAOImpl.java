package com.yuncai.modules.lottery.dao.oracleDao.impl.baskboll;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.baskboll.BkMatchDAO;
import com.yuncai.modules.lottery.model.Oracle.BkMatch;


public class BkMatchDAOImpl extends GenericDaoImpl<BkMatch, Integer> implements BkMatchDAO{

	private static final Log log = LogFactory.getLog(BkMatchDAOImpl.class);

	@Override
	public List<BkMatch> findFtMatchs(final String date) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<BkMatch>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<BkMatch> doInHibernate(Session session)
					throws HibernateException, SQLException {
					return session.createCriteria(BkMatch.class).add(Restrictions.eq("matchDate", date)).addOrder(Order.asc("intTime")).addOrder(Order.asc("lineId")).list();
			}
		});
	}

	//获取赛事
	@SuppressWarnings("unchecked")
	@Override
	public List<BkMatch> findMatchListBetweenIntTimes(final DetachedCriteria detachedCriteria) {
		return (List<BkMatch>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				org.hibernate.Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
			}

		});
	}
	
	@SuppressWarnings("unchecked")
	public List<BkMatch> findByDetachedCriteria(final DetachedCriteria detachedCriteria) {
		return (List<BkMatch>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
			}

		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchAndPassRate(final List<Integer> ids) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select match,pass from BkMatch as match ,BkSpRate as pass where match.mid = pass.mid and match.id in(:ids) order by match.matchDate";
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				
				return query.list();
			}
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchAndPassRate(final List<Integer> intTimes,final List<String> lineIds) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select match,pass from BkMatch as match ,BkSpRate as pass where match.mid = pass.mid and match.intTime in(:intTimes) and match.lineId in (:lineIds) and pass.passMode=1 order by match.matchDate";
				Query query = session.createQuery(hql);
				query.setParameterList("intTimes", intTimes);
				query.setParameterList("lineIds", lineIds);
				
				return query.list();
			}
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchAndPassRateByIntTimes(final List<Integer> intTimes) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select match,pass from BkMatch as match ,BkSpRate as pass where match.mid = pass.mid and match.intTime in(:intTime) and pass.passMode=1 order by match.matchDate";
				Query query = session.createQuery(hql);
				query.setParameterList("intTime", intTimes);
				
				return query.list();
			}
			
		});
	}

}

