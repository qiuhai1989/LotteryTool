package com.yuncai.modules.lottery.dao.sqlDao.impl.apk;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.admin.search.ApkPicSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkPicDAO;
import com.yuncai.modules.lottery.model.Sql.ApkPic;

public class ApkPicDaoImpl extends GenericDaoImpl<ApkPic, Integer> implements ApkPicDAO{

//	private static final Log log = LogFactory.getLog(GenericDaoImpl.class);

	/***
	 * 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> find(final ApkPicSearch search,final int offset, final int length) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = getSql(search, "model.*")+" order by model.updtime desc";
							
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;
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
	public int getCount(final ApkPicSearch search) {
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
	
	private String getSql(ApkPicSearch search,String str){
		
		String sql  = "select "+str+" from T_ApkPci model where 1=1";
		if(DBHelper.isNoNull(search)){
			if(search.getJumpType()>0){
				sql += " and model.Jump_Type = "+search.getJumpType();
			}
			if(search.getBandpos()>0){
				sql += " and model.Band_pos = "+search.getBandpos();
			}
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMaxCode() {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String sql  = "select max(ap.Code) from T_ApkPci ap";
				
				Query query = session.createSQLQuery(sql);

				return query.list().get(0);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findByCode(final int code) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String sql  = "select ap.Id from T_ApkPci ap where ap.Code="+code;
				
				Query query = session.createSQLQuery(sql);
				return query.list().size();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findById(final int id) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String sql  = "select ap.Code from T_ApkPci ap where ap.Id="+id;
				
				Query query = session.createSQLQuery(sql);
				return query.list().get(0);
			}
		});
	}

}
