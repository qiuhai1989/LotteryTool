package com.yuncai.modules.lottery.dao.sqlDao.impl.user;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.user.BankDetailDAO;
import com.yuncai.modules.lottery.model.Sql.BankDetail;

public class BankDetailDAOImpl extends GenericDaoImpl<BankDetail, Integer> implements BankDetailDAO{
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBankNames(final String province,final String city,final String bank) {
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<String>>(){

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = "select BankName from t_bankdetails where ProvinceName=? and CityName=? and BankTypeName=? ";
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, province);
				query.setParameter(1, city);
				query.setParameter(2, bank);
				return query.list();
			}
			
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBankTypeNames() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<String>>(){

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = "select distinct BankTypeName from t_bankdetails";
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
			
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCityNames(final  String provinceName) {
		// TODO Auto-generated method stub
		//select distinct CityName from t_bankdetails where ProvinceName='辽宁省'
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<String>>(){

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = "select distinct CityName from t_bankdetails where ProvinceName=?";
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, provinceName);
				return query.list();
			}
			
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProvoinces() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<String>>(){

			@Override
			public List<String> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = "select distinct provincename from t_bankdetails";
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
			
		});
	}

}
