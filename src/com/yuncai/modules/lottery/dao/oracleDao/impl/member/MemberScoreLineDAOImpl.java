package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.action.member.WalletLineSearch;
import com.yuncai.modules.lottery.bean.search.PhoneShowBean;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberScoreLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.ScoreType;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class MemberScoreLineDAOImpl extends GenericDaoImpl<MemberScoreLine, Integer> implements MemberScoreLineDAO{
	private static final Log log = LogFactory.getLog(MemberScoreLineDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<MemberScoreLine> findScoreLineByAccount(final String account, final int offset, final int length) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from MemberScoreLine as model where model.account=:account order by model.id desc";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}
		});
	}

	/**
	 * 软件购彩
	 */
	@SuppressWarnings("unchecked")
	public List<MemberScoreLine> findScoreLineByAccount(final PhoneShowBean bean,final Date startDate,final Date endDate,final int offset,final int pagesize) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from MemberScoreLine as model where model.account=:account ";
				if(bean.getScoreLineNo()>0){
					if(bean.getOrder()==0){
						hql += " and scoreLineNo >= " + bean.getScoreLineNo();
					}else if(bean.getOrder()==1){
						hql += " and scoreLineNo <= " + bean.getScoreLineNo();
					}
				}
			    if (startDate != null) {
					hql += " and createDateTime >= :startDate ";
				}
				if (endDate != null) {
					hql += " and createDateTime <= :endDate ";
				}
				
					
				hql +="order by model.id desc";
				Query query = session.createQuery(hql);
				query.setParameter("account", bean.getAccount());
				if(startDate!=null){
					query.setParameter("startDate", startDate);
				}
				if(endDate !=null){
					query.setParameter("endDate", endDate);
				}
				
				query.setFirstResult(offset);
				query.setMaxResults(pagesize);
				return query.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	public int getCountByByAccount(final String account) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select count(model.scoreLineNo) from MemberScoreLine as model where model.account=:account order by model.id desc";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				return ((Long) query.iterate().next()).intValue();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<MemberScoreLine> findByAccountAndScoreType(final String account, final ScoreType scoreType) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from MemberScoreLine as model where model.account=:account ";
				if (scoreType != null) {
					hql += " and model.scoreType=:scoreType ";
				}
				hql += " order by model.id desc";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				if (scoreType != null) {
					query.setParameter("scoreType", scoreType);
				}
				return query.list();
			}
		});
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cailele.lottery.dao.impl.member.MemberScoreLineDAO#attachClean(com.cailele.lottery.entity.member.MemberScoreLine)
	 */
	public void attachClean(MemberScoreLine instance) {
		log.debug("attaching clean MemberScoreLine instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}



	@SuppressWarnings("unchecked")
	public Integer getRecordCount(final Map<String, Object> parameterMap, final Date startDate, final Date endDate) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select count(model.scoreLineNo) from MemberScoreLine as model where 1=1 ";
				Set<String> parameterKeySet = parameterMap.keySet();
				StringBuffer sb = new StringBuffer(hql);
				for (String parameterName : parameterKeySet) {
					if (parameterMap.get(parameterName) != null) {
						{
							sb.append(" and model." + parameterName + "= :" + parameterName);
						}
					}
				}
				hql = sb.toString();
				if (startDate != null && !"".equals(startDate)) {
					hql += " and model.createDateTime >= :startDate ";
				}

				if (endDate != null && !"".equals(endDate)) {
					hql += " and model.createDateTime <= :endDate";
				}

				LogUtil.out(hql);

				Query query = session.createQuery(hql);
				for (String parameterName : parameterKeySet) {
					if (parameterMap.get(parameterName) != null) {
						query.setParameter(parameterName, parameterMap.get(parameterName));
					}
				}
				if (startDate != null && !"".equals(startDate)) {
					query.setParameter("startDate", startDate);
				}

				if (endDate != null && !"".equals(endDate)) {
					query.setParameter("endDate", endDate);
				}
				Integer count = 0;
				try {
					count = ((Long) query.iterate().next()).intValue();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return count;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Long getSumScoreValue(final Map<String, Object> parameterMap, final Date startDate, final Date endDate) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select sum(model.value) from MemberScoreLine as model where 1=1 ";
				Set<String> parameterKeySet = parameterMap.keySet();
				StringBuffer sb = new StringBuffer(hql);
				for (String parameterName : parameterKeySet) {
					if (parameterMap.get(parameterName) != null) {
						{
							sb.append(" and model." + parameterName + "= :" + parameterName);
						}
					}
				}
				hql = sb.toString();
				if (startDate != null && !"".equals(startDate)) {
					hql += " and model.createDateTime >= :startDate ";
				}

				if (endDate != null && !"".equals(endDate)) {
					hql += " and model.createDateTime <= :endDate";
				}

				LogUtil.out(hql);

				Query query = session.createQuery(hql);
				for (String parameterName : parameterKeySet) {
					if (parameterMap.get(parameterName) != null) {
						query.setParameter(parameterName, parameterMap.get(parameterName));
					}
				}
				if (startDate != null && !"".equals(startDate)) {
					query.setParameter("startDate", startDate);
				}

				if (endDate != null && !"".equals(endDate)) {
					query.setParameter("endDate", endDate);
				}
				Long sum = null;
				if (query.iterate().next() != null) {
					sum = ((Long) query.iterate().next()).longValue();
					return sum;
				} else {
					return new Long(0);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List findBySearch(final WalletLineSearch search, final int offset, final int length) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select model from MemberScoreLine as model,Member as mModel where 1=1 and model.memberId =mModel.id ";
				String hqlPost = " order by model.scoreLineNo desc ";
				Query query = genQuery(session, hql, hqlPost, search);

				query.setFirstResult(offset);
				query.setMaxResults(length);
				List list = query.list();
				return list;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int getCountBySearch(final WalletLineSearch search) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select count(model.scoreLineNo) from MemberScoreLine as model,Member as mModel where 1=1 and model.memberId =mModel.id ";

				Query query = genQuery(session, hql, "", search);

				return ((Long) query.iterate().next()).intValue();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public int getCountByScoreSearch(final WalletLineSearch search, final int countAmountBase) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select sum(model.value)/2000 from MemberScoreLine as model,Member as mModel where 1=1 and model.memberId =mModel.id ";

				Query query = genQuery(session, hql, "", search);
				return (query.iterate().next())!=null ? countAmountBase - ((Long) query.iterate().next()).intValue() : countAmountBase;
			}
		});
	}

	private Query genQuery(Session session, String hql, String postHql, WalletLineSearch search) {

		if (search.getAccount() != null && !"".equals(search.getAccount())) {
			hql += " and model.account = :account";
		}

		if (search.getTransType() != -1) {
			hql += " and model.scoreType = :transType";
		}

		if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
			hql += " and model.createDateTime >= :startDate";
		}
		if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
			hql += " and model.createDateTime <= :endDate";
		}

		hql += postHql;

		Query query = session.createQuery(hql);
		if (search.getAccount() != null && !"".equals(search.getAccount())) {
			query.setParameter("account", search.getAccount());
		}

		if (search.getTransType() != -1) {
			query.setParameter("transType", ScoreType.getItem(search.getTransType()));
		}
		if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
			query.setParameter("startDate", search.getStartDate1());
		}
		if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
			query.setParameter("endDate", search.getEndDate1());
		}

		return query;

	}
}
