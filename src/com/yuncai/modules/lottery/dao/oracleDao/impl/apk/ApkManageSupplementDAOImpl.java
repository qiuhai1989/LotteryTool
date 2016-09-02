package com.yuncai.modules.lottery.dao.oracleDao.impl.apk;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.apk.ApkManageSupplementDAO;
import com.yuncai.modules.lottery.model.Oracle.ApkManageSupplement;

public class ApkManageSupplementDAOImpl extends GenericDaoImpl<ApkManageSupplement, Integer> implements ApkManageSupplementDAO{
	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 11:05:58 AM
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApkManageSupplement> findAll() {
		return (List<ApkManageSupplement>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("from ApkManageSupplement");
				return query.list();
			}
		});
	}
}
