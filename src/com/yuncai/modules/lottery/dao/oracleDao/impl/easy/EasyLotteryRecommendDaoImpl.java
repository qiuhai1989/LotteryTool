package com.yuncai.modules.lottery.dao.oracleDao.impl.easy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.easy.EasyLotteryRecommendDao;
import com.yuncai.modules.lottery.model.Oracle.EasyLotteryRecommend;

public class EasyLotteryRecommendDaoImpl extends GenericDaoImpl<EasyLotteryRecommend, Integer> implements EasyLotteryRecommendDao {
	private static final Log log = LogFactory.getLog(EasyLotteryRecommendDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findRecommendFtInformation() {
		// TODO Auto-generated method stub
		return (List<Object[]>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select r,m,e from EasyLotteryRecommend r,FtMatch m ,EasyLotteryType e where m.nosaleTime >sysdate and m.status=0 and e.isShow=1 " +
						"and r.isShow=1 and r.gtype=72 and r.intTime=m.intTime and r.lineId=m.lineId and r.typeId=e.id  ";
//				String hql = "select r,m from EasyLotteryRecommend r,FtMatch m where m.nosaleTime >sysdate and m.status=0 ";
				Query query = session.createQuery(hql);
				return query.list();
			}
			
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findRecommendBkInformation() {
		// TODO Auto-generated method stub
		
		return (List<Object[]>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select r,m,e from EasyLotteryRecommend r,EasyLotteryType e,BkMatch m where m.nosaleTime >= sysdate and m.status=0 and e.isShow=1  " +
						"and r.isShow=1 and r.gtype=73 and r.intTime=m.intTime and r.lineId=m.lineId and r.typeId=e.id order by m.intTime, m.matchNo";
				Query query = session.createQuery(hql);
				
				
				return query.list();
			}
			
			
		});
	}
	@Override
	@SuppressWarnings("unchecked")
	public Integer findMinPos(final int gtype) {
		// TODO Auto-generated method stub
		return  Integer.valueOf(( this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = "select min(pos) from t_easy_lottery_type where gtype=?";
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, gtype);
				return query.list();
			}
			
			
		}).get(0)).toString()) ;
	}

}
