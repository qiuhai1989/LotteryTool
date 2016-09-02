package com.yuncai.modules.lottery.dao.sqlDao.impl.apk;

import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkPackageDAO;
import com.yuncai.modules.lottery.model.Sql.ApkPackage;

public class ApkPackageDaoImpl extends GenericDaoImpl<ApkPackage, Integer> implements ApkPackageDAO{

	private static final Log log = LogFactory.getLog(GenericDaoImpl.class);

	/***
	 * 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApkPackage> find(final int offset, final int length,final Search search) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = getSql(search, "model.*");
							
				Query query = session.createSQLQuery(sql).addEntity(ApkPackage.class);
				query.setFirstResult(offset);
				query.setMaxResults(length);

				return query.list();
			}
		});
	}

	/***
	 * 对象集合长度
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getCount(final Search search) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String sql  =getSql(search, "count(*)");
				Query query = session.createSQLQuery(sql);

				return query.list().get(0);
			}
		});	
	}
	
	private String getSql(Search search,String str){
		
		String sql  = "select "+str+" from T_ApkPackage model where 1=1";
		if(DBHelper.isNoNull(search)){
			if(DBHelper.isNoNull(search.getName())){
				sql += " and model.PackageName like '%"+search.getName()+"%'";
			}
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApkPackage> find() {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = "select * from T_ApkPackage";			
				Query query = session.createSQLQuery(sql).addEntity(ApkPackage.class);
				return query.list();
			}
		});
	}
}
