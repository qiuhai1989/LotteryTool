package com.yuncai.modules.lottery.dao.sqlDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.yuncai.modules.lottery.dao.sqlDao.fbmatch.PassRateDao;
import com.yuncai.modules.lottery.model.Sql.PassRate;

public class PassRateDaoImpl extends HibernateDaoSupport implements PassRateDao {

	@Override
	public PassRate findPassRate(final String matchDate, final String matchNo) {
		return super.getHibernateTemplate().execute(new HibernateCallback<PassRate>() {

			@SuppressWarnings("unchecked")
			@Override
			public PassRate doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql="select {r.*} from T_passRate r where CONVERT(varchar(20),MatchDate,23)=? and matchNumber=?";
				List<PassRate> list=session.createSQLQuery(sql).addEntity("r",PassRate.class).setString(0, matchDate).setString(1, matchNo).list();
				return list.size()>0?list.get(0):null;
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PassRate> findCurrentPassRate() {
		// TODO Auto-generated method stub
		return (List<PassRate>)getHibernateTemplate().executeFind(new HibernateCallback() {

			public List<PassRate> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = session.createSQLQuery("SELECT * FROM [T_PassRate] a inner join (select ID,mid,league_id,mb_id,tg_id from T_Match) b on a.MatchID = b.ID  where IsHhad = 1 and StopSellTime > GETDATE() order by [Day], MatchNumber").addEntity(PassRate.class);
				return query.list();
			}
		});
	}
	
}
