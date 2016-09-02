package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.bean.search.VipBonusLineSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.VipBonusLineDAO;
import com.yuncai.modules.lottery.model.Oracle.VipBonusLine;

public class VipBonusLineDAOImpl extends GenericDaoImpl<VipBonusLine, Integer> implements VipBonusLineDAO{

	/**
	 * 查询操作hql
	 * @author TYH
	 * @param VipBonusLineSearch
	 * @param str
	 * @return
	 */
	private String getHqlBySearch(VipBonusLineSearch search,String str){
		
		String hql  = "from VipBonusLine model where 1=1";
		
		if(DBHelper.isNoNull(search)){
			
			if(DBHelper.isNoNull(search.getAccount())){
				hql += " and model.account = '"+search.getAccount()+"'";
			}
			if(DBHelper.isNoNull(search.getStartDate())){
				hql += " and model.ticketDate >= '"+ search.getStartDate() +"'";
			}
			if(DBHelper.isNoNull(search.getEndDate())){
				hql += " and model.ticketDate <= '"+ search.getEndDate() +"'";
			}		
			if(DBHelper.isNoNull(search.getStatus())){
				hql += " and model.status = "+search.getStatus().getValue();
			}
			if(DBHelper.isNoNull(search.getId())){
				hql += " and model.id > "+search.getId().toString();
			}
		}
		return hql;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<VipBonusLine> findBySearch(final VipBonusLineSearch search,final int offset,
			final int length) {
		 return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String hql = getHqlBySearch(search, "VipBonusLine");
				hql += " order by model.createDateTime desc , model.id desc ";
				Query query = session.createQuery(hql);
				
				if(DBHelper.isNoNull(search)){
					if(DBHelper.isNoNull(search.getStartDate())){
						query.setParameter("startDate", DateTools.dateToString(search.getStartDate1(), "yyyy-MM-dd"));
					}
					if(DBHelper.isNoNull(search.getEndDate())){
						query.setParameter("endDate", DateTools.dateToString(search.getEndDate1(), "yyyy-MM-dd"));
					}
				}
				query.setFirstResult(offset);
				query.setMaxResults(length);

				return query.list();
			}
		});
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<VipBonusLine> findAllBySearch(final VipBonusLineSearch search) {
		return (List<VipBonusLine>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {				
				String hql = getHqlBySearch(search, "VipBonusLine");
				hql += " order by model.ticketDate desc , model.id desc ";
				Query query = session.createQuery(hql);
//				
//				if(DBHelper.isNoNull(search)){
//					if(DBHelper.isNoNull(search.getStartDate())){
//						query.setParameter("startDate", search.getStartDate1());
//					}
//					if(DBHelper.isNoNull(search.getEndDate())){
//						query.setParameter("endDate", search.getEndDate1());
//					}
//				}
				List<VipBonusLine> list= new ArrayList<VipBonusLine>();
				list = query.list();
				return list;
			}
		});
	}
	
}
