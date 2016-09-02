package com.yuncai.modules.lottery.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yuncai.core.util.Collections3;

/**
 * 通用数据库操作实现类
 * 
 * @author lm
 * 
 * @param <T>
 *            被持久化对象的类型
 * @param <PK>
 *            被持久化对象主键的类型
 */
@SuppressWarnings("unchecked")
public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {

	public GenericDaoImpl() {
	}

	public boolean contains(T t) {
		return getHibernateTemplate().contains(t);
	}

	public void delete(T t, LockMode lockMode) {
		getHibernateTemplate().delete(t, lockMode);
	}

	public void delete(T t) {
		getHibernateTemplate().delete(t);
	}

	public void deleteAll(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	public T find(Class<T> entityClass, PK id) {
		T load = (T) getHibernateTemplate().get(entityClass, id);
		return load;
	}

	public List<T> findByExamble(T instace) {
		return getHibernateTemplate().findByExample(instace);
	}

	public List<T> findByExamble(T instace, int firstResult, int maxResults) {
		return getHibernateTemplate().findByExample(instace, firstResult, maxResults);
	}

	public List<T> findByValues(final Class<T> entityClass, final String propertyName, final Collection<T> values) {
		if (values == null || values.size() == 0) {
			return new ArrayList<T>();
		} else {
			return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session session) throws HibernateException {
					String queryString = "from " + entityClass.getSimpleName() + " as model where ";
					Query query = null;
					if (values.size() < 5) {
						StringBuilder conditions = new StringBuilder();
						for (int i = 0, len = values.size(); i < len; i++) {
							conditions.append("or " + propertyName+ "=:"+propertyName+i+" ");
						}
						queryString += conditions.substring(2);
						query = session.createQuery(queryString);
						int i=0;
						for (Object value:values) {
							query.setParameter(propertyName+i, value);
							i++;
						}
					} else {
						queryString += " model." + propertyName + " in (:"+ propertyName + "s)";
						query = session.createQuery(queryString);
						query.setParameterList(propertyName + "s", values);
					}
					return ((List<T>) query.list());
				}
			});
		}
	}

	public List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		if (value instanceof Collection && !Collections3.isEmpty((Collection<?>) value)) {
			return findByValues(entityClass, propertyName, (Collection<T>) value);
		} else {
			String queryString = "from " + entityClass.getSimpleName() + " as model where model." + propertyName + "= ?";
			//LogUtil.out("hql---------------"+queryString);
			return getHibernateTemplate().find(queryString, value);
		}
	}
	
	public T findObjectByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
		List<T> list=null;
		if (value instanceof Collection && !Collections3.isEmpty((Collection<?>) value)) {
			list = findByValues(entityClass, propertyName, (Collection<T>) value);
		} else {
			list = super.getHibernateTemplate().find("from " + entityClass.getSimpleName() + " as model where model." + propertyName + "= ?",value);

		}
		return list.size()>0?list.get(0):null;
	}
	
	public T findObjectByProperty(final Class<T> entityClass, final String propertyNames, final Object... values) {
		StringBuffer hql=new StringBuffer();
		hql.append("from " + entityClass.getSimpleName() + " as model where ");
		String []proNames=propertyNames.split(",");
		for (int i = 0; i < proNames.length; i++) {
			if(i>0) hql.append(" and ");
			hql.append("model."+proNames[i]+"= ?");
		}
		List<T> list = super.getHibernateTemplate().find(hql.toString(),values);
		return list.size()>0?list.get(0):null;
	}
	
	public List<T> findByProperty(Class<T> entityClass, String propertyName, Object value, int firstResult, int maxResults) {
		String queryString = "from " + entityClass.getSimpleName() + " as model where model." + propertyName + "= ?";
		return getHibernateTemplate().find(queryString, value, firstResult, maxResults);
	}

	public List<T> findByPropertys(Class<T> entityClass, final String[] names, final Object[] values) {
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
		return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) throws HibernateException {
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
				return ((List<T>) query.list());
			}
		});
	}
	public Long countByProperty(Class<T> entityClass,String propertyName, Object value){
		if (value instanceof Collection && !Collections3.isEmpty((Collection<?>) value)) {
			return countByValues(entityClass, propertyName, (Collection<T>) value);
		} else {
			String queryString = "select count(*) from " + entityClass.getSimpleName() + " as model where model." + propertyName + "= ?";
			return ((Long)getHibernateTemplate().iterate(queryString, value).next());
		}
	}
	public Long countByValues(final Class<T> entityClass, final String propertyName, final Collection<T> values){
		if (Collections3.isEmpty(values)) {
			return 0l;
		} else {
			return getHibernateTemplate().execute(new HibernateCallback<Long>() {
				public Long doInHibernate(Session session) throws HibernateException {
					String queryString = "select count(*) from " + entityClass.getSimpleName() + " as model where ";
					Query query = null;
					if (values.size() < 5) {
						StringBuilder conditions = new StringBuilder();
						for (int i = 0, len = values.size(); i < len; i++) {
							conditions.append("or " + propertyName+ "=:"+propertyName+i+" ");
						}
						queryString += conditions.substring(2);
						query = session.createQuery(queryString);
						int i=0;
						for (Object value:values) {
							query.setParameter(propertyName+i, value);
							i++;
						}
					} else {
						queryString += " model." + propertyName + " in (:"+ propertyName + "s)";
						query = session.createQuery(queryString);
						query.setParameterList(propertyName + "s", values);
					}
					return ((Long) query.uniqueResult());
				}
			});
		}
	}
	
	public Long countByPropertys(final Class<T> entityClass,final String[] names,final Object[] values) {
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
				return ((Long) query.uniqueResult());
			}

		});
	}
	public void refresh(T t, LockMode lockMode) {
		getHibernateTemplate().refresh(t, lockMode);
	}

	public void refresh(T t) {
		getHibernateTemplate().refresh(t);
	}

	public PK save(T t) {
		return (PK) getHibernateTemplate().save(t);
	}

	public void update(T t, LockMode lockMode) {
		getHibernateTemplate().update(t, lockMode);
	}

	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	public void saveOrUpdate(T t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	public void saveOrUpdateAll(Collection<T> entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public void evict(T t) {
		getHibernateTemplate().evict(t);
	}

	@Override
	public List<T> find(Class<T> entityClass) {
		return super.getHibernateTemplate().loadAll(entityClass);
	}

	@Override
	public T saveList(T t) {
		return (T) getHibernateTemplate().save(t);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByDetachedCriteria(final DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
			}

		});
	}

}