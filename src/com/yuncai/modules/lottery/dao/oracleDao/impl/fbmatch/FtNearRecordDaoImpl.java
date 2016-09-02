package com.yuncai.modules.lottery.dao.oracleDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtNearRecordDao;
import com.yuncai.modules.lottery.model.Oracle.FtNearRecord;

public class FtNearRecordDaoImpl extends HibernateDaoSupport implements FtNearRecordDao {

	@Override
	public List<FtNearRecord> findFtNearRecordsByMids(final String mids) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<FtNearRecord>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<FtNearRecord> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select {nr.*} from t_ft_near_record nr where nr.mid in("+mids+") order by nr.mid desc";
				return session.createSQLQuery(sql).addEntity("nr",FtNearRecord.class).list();
			}
		});
	}

}
