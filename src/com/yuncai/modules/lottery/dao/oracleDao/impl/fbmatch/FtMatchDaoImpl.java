package com.yuncai.modules.lottery.dao.oracleDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtMatchDao;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;


public class FtMatchDaoImpl extends GenericDaoImpl<FtMatch, Integer> implements FtMatchDao {

	private static final Log log = LogFactory.getLog(FtMatchDaoImpl.class);
	@Override
	public List<FtMatch> findFtMatchs(final String date) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<FtMatch>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<FtMatch> doInHibernate(Session session)
					throws HibernateException, SQLException {
				/*String sql="select {f.*} from t_ft_match f where Match_Date>?";
				return session.createSQLQuery(sql).addEntity("f",FtMatch.class).setString(0, date).list();*/
				return session.createCriteria(FtMatch.class).add(Restrictions.eq("matchDate", date)).addOrder(Order.asc("intTime")).addOrder(Order.asc("lineId")).list();
			}
		});
		
	}

	//获取赛事
	@SuppressWarnings("unchecked")
	public List<FtMatch> findMatchListBetweenIntTimes(final DetachedCriteria detachedCriteria) {
		return (List<FtMatch>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				org.hibernate.Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
			}

		});
	}
	
	@SuppressWarnings("unchecked")
	public List<FtMatch> findByDetachedCriteria(final DetachedCriteria detachedCriteria) {
		return (List<FtMatch>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
			}

		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findCurrentMatch() {
		// TODO Auto-generated method stub
		return (List<Object[]>)this.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {

			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException,
					SQLException {
				// TODO Auto-generated method stub
				  String hql = "select mt,bf,bqc,jqs,spf,bet,r  from FtMatch mt ,FtBfAward bf,FtBqcAward bqc,FtJqsAward jqs,FtSpfAward spf ,FtBetInfo bet ,FtNearRecord r where mt.mid=bf.mid and bf.mid = bqc.mid and bqc.mid = jqs.mid and jqs.mid = spf.mid and mt.mid=bet.mid and mt.mid=r.mid and bf.passmode=1 and bqc.passmode=1 and jqs.passmode=1 and spf.passmode=1 and mt.nosaleTime >sysdate and mt.status=0 order by mt.intTime, mt.matchNo";
				  Query query = session.createQuery(hql);
				  return query.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public String findMatchKey() {
		// TODO Auto-generated method stub
		return (String)this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer();
				String sql = "select distinct m.int_time from t_ft_match m where m.nosale_time>=sysdate and m.status=0";
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
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findCurrentMatch(final Integer initTime,final Integer initTime2) {
		// TODO Auto-generated method stub
		return (List<Object[]>)this.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {

			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException,
					SQLException {
				// TODO Auto-generated method stub
				  String hql = "select mt,bf,bqc,jqs,spf,bet,r  from FtMatch mt ,FtBfAward bf,FtBqcAward bqc,FtJqsAward jqs,FtSpfAward spf ,FtBetInfo bet,FtNearRecord r where mt.mid=bf.mid and bf.mid = bqc.mid and bqc.mid = jqs.mid and jqs.mid = spf.mid and mt.mid=bet.mid and mt.mid=r.mid  and bf.passmode=1 and bqc.passmode=1 and jqs.passmode=1 and spf.passmode=1 and mt.nosaleTime >sysdate  and mt.intTime= :time and mt.status=0 order by mt.intTime, mt.matchNo";
//				  StringBuffer sbf = new StringBuffer("select mt,bf,bqc,jqs,spf,bet  from FtMatch mt ,FtBfAward bf,FtBqcAward bqc,FtJqsAward jqs,FtSpfAward spf ,FtBetInfo bet where mt.mid=bf.mid and bf.mid = bqc.mid and bqc.mid = jqs.mid and jqs.mid = spf.mid and mt.mid=bet.mid and bf.passmode=1 and bqc.passmode=1 and jqs.passmode=1 and spf.passmode=1 and mt.nosaleTime >sysdate ");
//				  sbf.append(" and mt.intTime = :time  ");
//				  sbf.append("order by mt.intTime, mt.matchNo");
//				  LogUtil.out(sbf.toString());
				  Query query = session.createQuery(hql);
				  if(initTime!=null){
					  query.setParameter("time", initTime);
				  }else{
					  query.setParameter("time", initTime2);
				  }
				  return query.list();
			}
		});
	}
}
