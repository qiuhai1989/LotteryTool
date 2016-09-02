package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.List;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.member.CluesSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.CluesDAO;
import com.yuncai.modules.lottery.model.Oracle.Clues;

public class CluesDAOImpl extends GenericDaoImpl<Clues, Integer> implements CluesDAO{
	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 11:05:58 AM
	 */
//	private static final Log log = LogFactory.getLog(CluesDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Clues> findBySearch(final CluesSearch search, final int offset, final int length) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql  = getHqlBySearch(search, "clues");
				
				Query query = session.createQuery(hql);

				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getCountBySearch(final CluesSearch search) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String hql  = getHqlBySearch(search, "count(clues.id)");
				
				Query query = session.createQuery(hql);

				return ((Long) query.iterate().next()).intValue();
			}
		});
	}
	
	private String getHqlBySearch(CluesSearch search,String str){
		String hql = "select "+str+" from Clues clues where 1=1";
		if (DBHelper.isNoNull(search)) {
			if(search.getType()==0){
				hql += " and clues.type =0";
			}
			if(search.getType()==1){
				hql += " and clues.type =1";
			}
			if(DBHelper.isNoNull(search.getName())){
				hql += " and clues.name like '%"+search.getName()+"%'";
			}
			if(DBHelper.isNoNull(search.getContent())){
				hql += " and clues.content like '%"+search.getContent()+"%'";
			}
			if(DBHelper.isNoNull(search.getLocation())){
				hql += " and clues.location like '%"+search.getLocation()+"%'";
			}
			if(DBHelper.isNoNull(search.getStartDate())){
				hql += " and clues.updateTime >= '"+search.getStartDate()+"'";
			}
			if(DBHelper.isNoNull(search.getEndDate())){
				hql += " and clues.updateTime <='"+search.getEndDate()+"' ";
			}
		}
		hql += " order by clues.id desc";
		return hql;

	}

}
