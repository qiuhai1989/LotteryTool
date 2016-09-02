package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.GiftDatailLineDAO;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;

public class GiftDatailLineDAOImpl extends GenericDaoImpl<GiftDatailLine, Integer> implements GiftDatailLineDAO {
	private static final Log log = LogFactory.getLog(GiftDatailLineDAOImpl.class);
	/*
	 * 
	 * //根据用户，字头查询是否存在记录(non-Javadoc)
	 * @see com.yuncai.modules.lottery.dao.oracleDao.member.GiftDatailLineDAO#findByAccountList(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<GiftDatailLine> findByAccountList(final String account,final String topNumber) {
		return (List<GiftDatailLine>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql=" from GiftDatailLine as model where 1=1 ";
				if(DBHelper.isNoNull(account)){
					hql += " and model.account = '" + account +"'";
				}
				if(DBHelper.isNoNull(topNumber)){
					hql +=" and model.giftId  like '%" +topNumber+"%'";
				}
				Query query=session.createQuery(hql);
				
				return query.list();
			}
		});
		
	}
	
	// 获取红包菜单
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> giftMenu(final String account){
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select count(amount) as giftCount,amount from GiftDatailLine as model where model.account= :account group by model.amount order by model.amount "  ;
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				return query.list();
			}
			
		});
	}

}
