package com.yuncai.modules.lottery.dao.oracleDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtImsSfcDao;
import com.yuncai.modules.lottery.model.Oracle.FtImsSfc;

public class FtImsSfcDaoImpl extends GenericDaoImpl<FtImsSfc, Integer> implements FtImsSfcDao{

	@SuppressWarnings("unchecked")
	public List<FtImsSfc> findFtImsSfc(final String expect,final int atype){
		return super.getHibernateTemplate().executeFind(new HibernateCallback<List<FtImsSfc>>() {

			@Override
			public List<FtImsSfc> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createCriteria(FtImsSfc.class).add(Restrictions.eq("expect", expect)).add(Restrictions.eq("atype", atype)).addOrder(Order.asc("cc")).list();
			}
			
		});
	}
}
