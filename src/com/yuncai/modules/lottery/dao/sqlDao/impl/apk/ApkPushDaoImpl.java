package com.yuncai.modules.lottery.dao.sqlDao.impl.apk;

import java.sql.SQLException;
import java.util.List;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkPushDAO;
import com.yuncai.modules.lottery.model.Sql.ApkPush;

public class ApkPushDaoImpl extends GenericDaoImpl<ApkPush, Integer> implements ApkPushDAO{

//	private static final Log log = LogFactory.getLog(GenericDaoImpl.class);

	/***
	 * 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApkPush> find(final int offset, final int length,final Search search) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = getSql(search, "model.*");
				sql += " order by model.pushTime desc";			
				Query query = session.createSQLQuery(sql).addEntity(ApkPush.class);
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
		
		String sql  = "select "+str+" from T_ApkPush model where 1=1";
		if(DBHelper.isNoNull(search)){
			if(DBHelper.isNoNull(search.getNoticeNo())){
				sql += " and model.pushNo = "+search.getNoticeNo();
			}
			if(DBHelper.isNoNull(search.getName())){
				sql += " and model.pushName like '%"+search.getName()+"%'";
			}
			if(DBHelper.isNoNull(search.getPushAd())){
				sql += " and model.pushAd like '%"+search.getPushAd()+"%'";
			}
			if(DBHelper.isNoNull(search.getContent())){
				sql += " and model.pushContent like '%"+search.getContent()+"%'";
			}
			if(DBHelper.isNoNull(search.getStartDate())){
				sql += " and model.pushTime >= '"+search.getStartDate()+"' ";
			}
			if(DBHelper.isNoNull(search.getEndDate())){
				sql += " and model.pushTime <= '"+search.getEndDate()+"'";
			}
			if(search.getType()>=0){
				sql += " and model.type = "+search.getType();
			}
			if(search.getSendGroup()>=0){
				sql += " and model.sendGroup = "+search.getSendGroup();
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
				
				String sql  = "select max(ap.pushNo) from T_ApkPush ap";
				
				Query query = session.createSQLQuery(sql);

				return query.list().get(0);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApkPush> findAll(final String now,final String str,final String user,final String agentId) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = "select * from T_ApkPush where pushTime>='"+now+"' and isValid=1";
				if(DBHelper.isNoNull(agentId)&&!DBHelper.isNoNull(user))
					sql += " and (receiveUser like '%"+agentId+"%' or receiveUser like '')";
				if(DBHelper.isNoNull(str))
					sql += " and id not in "+str;
				if(DBHelper.isNoNull(user))
					sql += " and (receiveUser like '%"+user+"%' or receiveUser like '%"+agentId+"%' or receiveUser like '')";
				sql += " order by pushNo asc";
				Query query = session.createSQLQuery(sql).addEntity(ApkPush.class);
				return query.list();
			}
		});
	}

}
