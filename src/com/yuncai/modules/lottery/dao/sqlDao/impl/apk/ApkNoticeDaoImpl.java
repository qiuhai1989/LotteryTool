package com.yuncai.modules.lottery.dao.sqlDao.impl.apk;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkNoticeDAO;
import com.yuncai.modules.lottery.model.Sql.ApkNotice;

public class ApkNoticeDaoImpl extends GenericDaoImpl<ApkNotice, Integer> implements ApkNoticeDAO{


	/***
	 * 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApkNotice> find(final int offset, final int length,final Search search) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = getSql(search, "model.*");
				sql += " order by model.noticeNo asc";			
				Query query = session.createSQLQuery(sql).addEntity(ApkNotice.class);
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
		
		String sql  = "select "+str+" from T_ApkNotice model where 1=1";
		if(DBHelper.isNoNull(search)){
			if(DBHelper.isNoNull(search.getNoticeNo())){
				sql += " and model.noticeNo = "+search.getNoticeNo();
			}
			if(DBHelper.isNoNull(search.getName())){
				sql += " and model.noticeName like '%"+search.getName()+"%'";
			}
			if(DBHelper.isNoNull(search.getContent())){
				sql += " and model.noticeContent like '%"+search.getContent()+"%'";
			}
			if(DBHelper.isNoNull(search.getStartDate())){
				sql += " and model.issueTime >= '"+search.getStartDate()+"' ";
			}
			if(DBHelper.isNoNull(search.getEndDate())){
				sql += " and model.issueTime <= '"+search.getEndDate()+"'";
			}
			if(DBHelper.isNoNull(search.getStartDate1())){
				sql += " and model.endTime >= '"+search.getStartDate1()+"'";
			}
			if(DBHelper.isNoNull(search.getEndDate1())){
				sql += " and model.endTime <= '"+search.getEndDate1()+"'";
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
				
				String sql  = "select max(an.noticeNo) from T_ApkNotice an";
				
				Query query = session.createSQLQuery(sql);

				return query.list().get(0);
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ApkNotice> findAll(final String time,final String str){
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = "select * from T_ApkNotice where issueTime<='"+time+"' and endTime>='"+time+"' and isValid=1";
				if(DBHelper.isNoNull(str))
					sql += "and noticeNo not in "+str;
				Query query = session.createSQLQuery(sql).addEntity(ApkNotice.class);
				return query.list();
			}
		});
	}
}
