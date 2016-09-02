package com.yuncai.modules.lottery.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;

import com.yuncai.modules.lottery.dao.GenericDao;

public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	protected Logger log = Logger.getLogger(BaseServiceImpl.class);
	protected Class<T> entityClass;
	private GenericDao<T, PK> genericDao;

	public void saveOrUpdate(T t) {
		genericDao.saveOrUpdate(t);
	}

	public T find(PK id) {
		return genericDao.find(getEntityClass(), id);
	}

	public boolean contains(T t) {
		return genericDao.contains(t);
	}

	public void delete(T t, LockMode lockMode) {
		genericDao.delete(t, lockMode);
	}

	public void delete(T t) {
		genericDao.delete(t);

	}

	public void deleteAll(Collection<T> entities) {
		genericDao.deleteAll(entities);

	}

	public List<T> findByExamble(T t) {
		return genericDao.findByExamble(t);
	}

	public List<T> findByProperty(String propertyName, Object value) {
		return genericDao.findByProperty(getEntityClass(), propertyName, value);
	}

	public T findObjectByProperty(String propertyName, Object value) {
		return genericDao.findObjectByProperty(getEntityClass(), propertyName, value);
	}
	
	public T findObjectByProperty(String propertyNames, Object... values) {
		return genericDao.findObjectByProperty(getEntityClass(), propertyNames, values);
	}
	
	public List<T> findByPropertys(String[] propertyNames, Object[] values) {
		return genericDao.findByPropertys(getEntityClass(), propertyNames, values);
	}

	public Long countByProperty(String propertyName, Object value) {
		return genericDao.countByProperty(getEntityClass(), propertyName, value);
	}

	public Long countByPropertys(String[] names, Object[] values) {
		return genericDao.countByPropertys(getEntityClass(), names, values);
	}

	public void refresh(T t, LockMode lockMode) {
		genericDao.refresh(t, lockMode);
	}

	public void refresh(T t) {
		genericDao.refresh(t);
	}

	public PK save(T t) {
		return genericDao.save(t);
	}

	public void saveOrUpdateAll(Collection<T> entities) {
		genericDao.saveOrUpdateAll(entities);
	}

	public void update(T t, LockMode lockMode) {
		genericDao.update(t, lockMode);
	}

	public void update(T t) {
		genericDao.update(t);
	}

	public void setGenericDao(GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	public void evict(T t) {
		this.genericDao.evict(t);
	}

	/**
	 * 获取被持久化对象类型的Class
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	@Override
	public List<T> find(Class<T> entityClass) {
		return genericDao.find(entityClass);
	}

	@Override
	public T saveList(T t) {
		return (T)this.genericDao.saveList(t);
	}

}
