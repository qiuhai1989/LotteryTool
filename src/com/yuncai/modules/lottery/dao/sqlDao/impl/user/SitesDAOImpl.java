package com.yuncai.modules.lottery.dao.sqlDao.impl.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;

import com.yuncai.modules.lottery.dao.sqlDao.user.SitesDAO;
import com.yuncai.modules.lottery.model.Sql.Sites;

public class SitesDAOImpl extends GenericDaoImpl<Sites, Integer> implements SitesDAO {
	private static final Log log = LogFactory.getLog(SitesDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public Sites getSitesInfo() {
		return (Sites) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql="select *  from  T_Sites where 1=1 ";
				Query query = session.createSQLQuery(hql.toString()).addEntity(Sites.class);
				query.setMaxResults(1);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
		
	}

}
