package com.yuncai.modules.lottery.dao.sqlDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.List;

import oracle.net.aso.i;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.sqlDao.fbmatch.MatchDao;
import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.model.Sql.PassRate;


public class MatchDaoImpl extends HibernateDaoSupport implements MatchDao {

	@Override
	public Match findMatchById(final Integer id) {
		// TODO Auto-generated method stub
		return (Match)getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = session.createSQLQuery("select * from T_Match where ID=  ?").addEntity(Match.class).setInteger(0, id);
				return query.uniqueResult();
			}
		});
	}

	@Override
	public List<Match> findMatchList(final List<Integer> ids) {
		// TODO Auto-generated method stub
		
		return (List<Match>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = " from Match as model where model.id in (:ids)   order by model.matchDate  ";
//				LogUtil.out(hql);	
				Query query = session.createQuery(hql);
					   query.setParameterList("ids", ids); 
				return query.list();
			}
			
		});
	}

	@Override
	public List<Object[]> findMatchAndPassRate(final List<Integer> ids) {
		// TODO Auto-generated method stub
		
		return (List<Object[]>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				 String hql = "select math,pass  from Match as math ,PassRate as pass where math.id=pass.matchId and math.id in (:ids)   order by math.matchDate ";
				 Query query = session.createQuery(hql);
				 query.setParameterList("ids", ids);
				  
				return query.list();
			}
			
		});
	}
	
}
