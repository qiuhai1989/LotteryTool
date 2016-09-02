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
import com.yuncai.modules.lottery.dao.oracleDao.member.ChannelDAO;
import com.yuncai.modules.lottery.model.Oracle.Channel;

public class ChannelDAOImpl extends  GenericDaoImpl<Channel,Integer> implements ChannelDAO {
	private static final Log log = LogFactory.getLog(ChannelDAOImpl.class);
	/**
	 * 按分页查找所有新闻
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Channel> findAllChannelByPage(Class<Channel> entityClass,final int offset,final int length){
		final StringBuilder queryBuilder = new StringBuilder("from " + entityClass.getSimpleName() + " as model where 1=1 ");
		return getHibernateTemplate().execute(new HibernateCallback<List<Channel>>() {
			public List<Channel> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(queryBuilder.toString());
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return ((List<Channel>) query.list());
			}
		});
	}
	
	
	/**
	 * 按照分页查新闻
	 */
	public List<Channel> findByPropertysAndPage(Class<Channel> entityClass, final String[] names, final Object[] values, final int offset,
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
		//queryBuilder.append(" order by model.isTop desc, case when model.isHasImage=true then model.startTime else null end desc,model.isHasImage desc,model.isRed desc,model.dateTime desc");
		return getHibernateTemplate().execute(new HibernateCallback<List<Channel>>() {
			@SuppressWarnings("unchecked")
			public List<Channel> doInHibernate(Session session) throws HibernateException {
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
				return ((List<Channel>) query.list());
			}
		});
	}
	
	
	/**
	 * 查找新闻的数量
	 */
	public long findAllCountByPropertys(final String[] names, final Object[] values) {
		try {
			final StringBuilder queryBuilder =new StringBuilder( "select count(model) from Channel as model where 1=1");
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
			log.error("get all count Ticket failure", re);
			throw re;
		}
	}
	
	/**
	 * 查找所有频道的数量
	 */
	public long findAllCount() {
		try {
			final StringBuilder queryBuilder =new StringBuilder( "select count(model) from Channel as model where 1=1");
			
			return getHibernateTemplate().execute(new HibernateCallback<Long>() {
				public Long doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(queryBuilder.toString());
					return (Long)query.iterate().next();
				}
			});
		} catch (RuntimeException re) {
			log.error("get all count Ticket failure", re);
			throw re;
		}
	}
}
