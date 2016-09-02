package com.yuncai.modules.lottery.dao.oracleDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.BkSpRateDao;
import com.yuncai.modules.lottery.model.Oracle.BkSpRate;

public class BkSpRateDaoImpl extends HibernateDaoSupport implements BkSpRateDao{

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findCurrentPassRate() {
		// TODO Auto-generated method stub
		
		return (List<Object[]>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = session.createQuery("select m ,b from BkSpRate as m , BkMatch as b where m.mid=b.mid and b.nosaleTime >= sysdate order by b.matchDate,b.matchNo");
//				Query query  = session.createQuery("from BkSpRate where 1=1");
				return query.list();
			}
			
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public String findMatchKey() {		// TODO Auto-generated method stub
		return (String)this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer();
				String sql = "select distinct b.int_time from t_bk_match b ,T_BK_SP_RATE m where m.mid=b.mid and  b.nosale_time>=sysdate and m.PASS_MODE=1 and b.status=0 ";
				Query query = session.createSQLQuery(sql);
				List<Object>list = query.list();
				for(Object obj:list){
					sbf.append(obj.toString()+",");
				}
				if(sbf.length()>0){
					return sbf.toString().substring(0, sbf.toString().length()-1);
				}else{
					return null;
				}
				
			}
			
		});}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findCurrentPassRate(final Integer time1,final Integer time2) {
		// TODO Auto-generated method stub
		
		return (List<Object[]>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = session.createQuery("select m ,b from BkSpRate as m , BkMatch as b   where m.mid=b.mid   and b.nosaleTime >= sysdate and b.intTime= :time  and m.passMode=1 and b.status=0  order by b.matchDate,b.matchNo");
				  if(time1!=null){
					  query.setParameter("time", time1);
				  }else{
					  query.setParameter("time", time2);
				  }
				return query.list();
			}
			
			
		});
	}

}
