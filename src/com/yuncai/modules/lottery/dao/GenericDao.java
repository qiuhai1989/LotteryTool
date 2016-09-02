package com.yuncai.modules.lottery.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 通用数据库操作接口
 * @author lm
 *
 * @param <T> 待操作对象的类型
 * @param <PK>主键类型
 */
public interface GenericDao<T, PK extends Serializable> {
	public T find(Class<T> entityClass,PK id);

	public List<T> findByExamble(T instace);
	
	public List<T> findByProperty(Class<T> entityClass,String propertyName, Object value) ;
	
	public T findObjectByProperty(Class<T> entityClass,String propertyName, Object value) ;
	
	/**
	 * 多条件查询
	 * @param entityClass
	 * @param propertyNames   example: mid,id
	 * @param values
	 * @return object
	 */
	public T findObjectByProperty(Class<T> entityClass,String propertyNames,Object...values) ;
	
	public List<T> findByPropertys(Class<T> entityClass,String[] names,Object[] values) ;
	
	public Long countByProperty(Class<T> entityClass,String propertyName, Object value) ;
	
	public Long countByPropertys(Class<T> entityClass,String[] names,Object[] values) ;
	
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
	
	public T saveList(T t);
	
	public List<T> findByDetachedCriteria(final DetachedCriteria detachedCriteria);
	
}
