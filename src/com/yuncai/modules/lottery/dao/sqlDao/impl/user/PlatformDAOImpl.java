package com.yuncai.modules.lottery.dao.sqlDao.impl.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.user.PlatformDAO;
import com.yuncai.modules.lottery.model.Sql.Platform;

public class PlatformDAOImpl extends GenericDaoImpl<Platform,Integer> implements PlatformDAO{
	
	/**
	 * 查到所有平台信息
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Platform> findAllPlatform(){
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql ="select model.* from T_platform as model";
				Query query = session.createSQLQuery(sql).addEntity(Platform.class);
				return query.list();
			}
		});
	}
}
