package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.PlatformDAO;
import com.yuncai.modules.lottery.model.Oracle.Platform;

public class PlatformDAOImpl extends GenericDaoImpl<Platform,Integer> implements PlatformDAO{
	
	/**
	 * 查到所有平台信息
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Platform> findAllPlatform(){
		return (List<Platform>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hsql = new StringBuffer();
				hsql.append("from Platform  model where 1=1");
				Query query = session.createQuery(hsql.toString());
				//query.setParameter("id", id);
				//query.setParameter("quashStatus", (short)0);
				List<Platform> list = new ArrayList<Platform>();
				list = query.list();
				return list;
			}
		});
	}
}
