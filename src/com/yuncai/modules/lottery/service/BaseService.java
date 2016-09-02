package com.yuncai.modules.lottery.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;

public interface BaseService<T, PK extends Serializable> {

	public T find(PK id);

	public List<T> findByExamble(T instace);
	
	public List<T> findByProperty(String propertyName, Object value) ;
	
	public T findObjectByProperty(String propertyName, Object value) ;
	
	/**
	 * 多条件查询
	 * @param entityClass
	 * @param propertyNames   example: mid,id
	 * @param values
	 * @return object
	 */
	public T findObjectByProperty(String propertyNames, Object... values) ;
	
	public List<T> findByPropertys(String[] names,Object[] values) ;
	
	public Long countByProperty(String propertyName, Object value) ;
	
	public Long countByPropertys(String[] names,Object[] values) ;
	
	public boolean contains(T t);

	public void delete(T t, LockMode lockMode);

	public void delete(T t);

	public void deleteAll(Collection<T> entities);
	
	public PK save(T t);
	
	public void update(T t);
	
	public void update(T t, LockMode lockMode);
	
	public void saveOrUpdate(T t);
	
	public void saveOrUpdateAll(Collection<T> entities);
	
	public void refresh(T t);
	
	public void refresh(T t, LockMode lockMode);

	public void evict(T t);
	
	public List<T> find(Class<T> entityClass);
	
	public T saveList(T t) ;
}
