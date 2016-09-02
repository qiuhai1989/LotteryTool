package com.yuncai.modules.lottery.dao.sqlDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.yuncai.modules.lottery.dao.sqlDao.fbmatch.SingleRateDao;
import com.yuncai.modules.lottery.model.Sql.SingleRate;

public class SingleRateDaoImpl extends HibernateDaoSupport implements SingleRateDao {

	@Override
	public SingleRate findSingleRate(final String matchDate, final String matchNo) {
		return super.getHibernateTemplate().execute(new HibernateCallback<SingleRate>() {

			@SuppressWarnings("unchecked")
			@Override
			public SingleRate doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql="select {r.*} from T_singleRate r where CONVERT(varchar(20),MatchDate,23)=? and matchNumber=?";
				List<SingleRate> list=session.createSQLQuery(sql).addEntity("r",SingleRate.class).setString(0, matchDate).setString(1, matchNo).list();
				return list.size()>0?list.get(0):null;
			}
		});
	}
	
}
