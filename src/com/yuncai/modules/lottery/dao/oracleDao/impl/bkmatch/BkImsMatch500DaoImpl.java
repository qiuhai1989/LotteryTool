package com.yuncai.modules.lottery.dao.oracleDao.impl.bkmatch;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.bkmatch.BkImsMatch500Dao;
import com.yuncai.modules.lottery.model.Oracle.BkImsMatch500;

public class BkImsMatch500DaoImpl extends GenericDaoImpl<BkImsMatch500, Integer> implements BkImsMatch500Dao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BkImsMatch500> findBkImsMatch500s(String expect) {
		return super.getHibernateTemplate().find(" from BkImsMatch500 m where m.startDate=? order by m.matchId asc",expect);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BkImsMatch500> findBkImsMatch500s(String expect, int status) {
		return super.getHibernateTemplate().find(" from BkImsMatch500 m where m.startDate=? and m.status=? order by m.matchId asc",expect,status);
	}

	@Override
	public List<BkImsMatch500> findBkImsJcMatchs(final String expect) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<BkImsMatch500>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<BkImsMatch500> doInHibernate(Session session)
					throws HibernateException, SQLException {
				//return session.createSQLQuery("select {bim.*} from t_bk_ims_match_500 bim,t_bk_match m where m.match_no=bim.match_no and m.match_date=bim.start_date and m.match_date=?").addEntity("bim", BkImsMatch500.class).setString(0, expect).list();
				return session.createSQLQuery("select {bim.*} from t_bk_ims_match_500 bim where bim.match_no like '周%' and bim.jc_date=? order by bim.match_no asc").addEntity("bim", BkImsMatch500.class).setString(0, expect).list();
			}
		});
	}

	@Override
	public List<BkImsMatch500> findBkImsJcMatchsByMids(final String mids) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<BkImsMatch500>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<BkImsMatch500> doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery("select {bim.*} from t_bk_ims_match_500 bim where bim.mid in("+mids+") order by bim.match_no asc").addEntity("bim", BkImsMatch500.class).list();
			}
		});
	}

}
