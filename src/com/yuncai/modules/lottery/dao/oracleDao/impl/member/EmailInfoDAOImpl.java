package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.util.Collections3;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.EmailInfoDAO;
import com.yuncai.modules.lottery.model.Oracle.EmailInfo;

public class EmailInfoDAOImpl extends  GenericDaoImpl<EmailInfo,Integer> implements EmailInfoDAO {
	private static final Log log = LogFactory.getLog(EmailInfoDAOImpl.class);
	/**
	 * 按分页查找所有邮箱
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EmailInfo> findAllEmailByPage(Class<EmailInfo> entityClass, final int offset, final int length) {
		final StringBuilder queryBuilder = new StringBuilder(
				"from "
						+ entityClass.getSimpleName()
						+ " as model where 1=1 order by id desc");
		return getHibernateTemplate().execute(new HibernateCallback<List<EmailInfo>>() {
			@SuppressWarnings("unchecked")
			public List<EmailInfo> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(queryBuilder.toString());
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return ((List<EmailInfo>) query.list());
			}
		});
	}
	
	/**
	 * 查找所有新闻的数量
	 */
	public long findAllCount() {
		try {
			final StringBuilder queryBuilder = new StringBuilder("select count(model) from EmailInfo as model where 1=1");

			return getHibernateTemplate().execute(new HibernateCallback<Long>() {
				public Long doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(queryBuilder.toString());
					return (Long) query.iterate().next();
				}
			});
		} catch (RuntimeException re) {
			log.error("get all count EmailInfo failure", re);
			throw re;
		}
	}

	/**
	 * 按照参数和分页查找邮箱
	 */
	public List<EmailInfo> findByPropertysAndPage(Class<EmailInfo> entityClass, final String[] names, final Object[] values, final int offset,
			final int length) {
		final StringBuilder queryBuilder = new StringBuilder("from " + entityClass.getSimpleName() + " as model where 1=1");
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			if (values[i] != null) {
				if (values[i] instanceof Collection && !Collections3.isEmpty((Collection<?>) values[i])) {
					queryBuilder.append(" and model." + name + " in (:" + name + ")");
				} else {
					queryBuilder.append(" and model." + name + "=:" + name);
				}
			}
		}
		return getHibernateTemplate().execute(new HibernateCallback<List<EmailInfo>>() {
			public List<EmailInfo> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(queryBuilder.toString());
				for (int i = 0; i < names.length; i++) {
					String name = names[i];
					if (values[i] != null) {
						if (values[i] instanceof Collection && !Collections3.isEmpty((Collection<?>) values[i])) {
							query.setParameterList(name, (Collection<Object>) values[i]);
						} else {
							query.setParameter(name, values[i]);
						}
					}
				}
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return ((List<EmailInfo>) query.list());
			}
		});
	}
	
	/**
	 * 查找邮箱的数量
	 */
	public long findCountByPropertys(final String[] names, final Object[] values) {
		try {
			final StringBuilder queryBuilder =new StringBuilder( "select count(model) from EmailInfo as model where 1=1");
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				if (values[i] != null) {
					if (values[i] instanceof Collection && !Collections3.isEmpty((Collection<?>) values[i])) {
						queryBuilder.append(" and model." + name + " in (:" + name + ")");
					} else {
						queryBuilder.append(" and model." + name + "=:" + name);
					}
				}
			}
			
			return getHibernateTemplate().execute(new HibernateCallback<Long>() {
				public Long doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(queryBuilder.toString());
					for (int i = 0; i < names.length; i++) {
						String name = names[i];
						if (values[i] != null) {
							if (values[i] instanceof Collection && !Collections3.isEmpty((Collection<?>) values[i])) {
								query.setParameterList(name, (Collection<Object>) values[i]);
							} else {
								query.setParameter(name, values[i]);
							}
						}
					}
					return (Long)query.iterate().next();
				}
			});
		} catch (RuntimeException re) {
			log.error("get all count EmailInfo failure", re);
			throw re;
		}
	}
}
