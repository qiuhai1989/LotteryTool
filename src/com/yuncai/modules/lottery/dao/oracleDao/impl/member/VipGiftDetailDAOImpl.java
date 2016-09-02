package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.bean.search.VipGiftDetailSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.VipGiftDetailDAO;

import com.yuncai.modules.lottery.model.Oracle.VipGiftDetail;

public class VipGiftDetailDAOImpl extends GenericDaoImpl<VipGiftDetail,Integer> implements VipGiftDetailDAO{
	
	// 获取红包菜单
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> giftMenu(final String account){
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select count(amount) as giftCount,amount from VipGiftDatail as model where model.account= :account group by model.amount order by model.amount "  ;
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				return query.list();
			}
			
		});
	}
	
	// 获取送出红包菜单
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> vipGiftMenu(final String account){
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select count(amount) as giftCount,amount from VipGiftDatail as model where model.vipAccount= :account group by model.amount order by model.amount "  ;
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				return query.list();
			}
			
		});
	}
	
	/**
	 * 查询操作hql
	 * @author WZL
	 * @param VipGiftDetailSearch
	 * @param str
	 * @return
	 */
	private String getHqlBySearch(VipGiftDetailSearch search,String str){
		
		String hql  = "from VipGiftDetail model where 1=1";
		
		if(DBHelper.isNoNull(search)){			
			if(DBHelper.isNoNull(search.getAccount())){
				hql += " and model.account = '"+search.getAccount()+"'";
			}
			if(DBHelper.isNoNull(search.getGiftId())){
				hql += " and model.id > "+ search.getGiftId() +"";
			}
			if(DBHelper.isNoNull(search.getVipAccount())){
				hql += " and model.vipAccount = '"+search.getVipAccount()+"'";
			}
		}
		return hql;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<VipGiftDetail> findAllBySearch(final VipGiftDetailSearch search) {
		return (List<VipGiftDetail>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {				
				String hql = getHqlBySearch(search, "VipGiftDetail");
				hql += " order by model.id desc";
				Query query = session.createQuery(hql);
				
				List<VipGiftDetail> list= new ArrayList<VipGiftDetail>();
				list = query.list();
				return list;
			}
		});
	}
}
