package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.admin.search.PagesInfoSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.PagesInfoDAO;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;

public class PagesInfoDAOImpl extends GenericDaoImpl<PagesInfo, Integer> implements PagesInfoDAO {
	private String getHqlBySearch(PagesInfoSearch search, String str) {
		String hql = "select " + str + " from PagesInfo as model where 1=1 ";
		if (DBHelper.isNoNull(search)) {
			if (DBHelper.isNoNull(search.getPageName())) {
				hql += " and model.pageName like '%" + search.getPageName() + "%'";
			}
			if (DBHelper.isNoNull(search.getPageId())) {
				hql += " and lower(model.pageId) like lower('%" + search.getPageId() + "%')";
			}
			if (DBHelper.isNoNull(search.getLotteryType()) && search.getLotteryType() > 0) {
				hql += " and model.lotteryType = " + search.getLotteryType();
			}
			if (DBHelper.isNoNull(search.getPageType()) && search.getPageType() > 0) {
				hql += " and model.pageType = " + search.getPageType();
			}
			if (DBHelper.isNoNull(search.getPlatform()) && !search.getPlatform().equals("-1")) {
				hql += " and model.platform = '" + search.getPlatform() +"'";
			}
		}
		return hql;
	}

	@SuppressWarnings("unchecked")
	public List<PagesInfo> findBySearch(final PagesInfoSearch search, final int offset, final int length) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = getHqlBySearch(search, "model");
				hql += " order by model.createDateTime desc";
				Query query = session.createQuery(hql);
				query.setFirstResult(offset);
				query.setMaxResults(length);

				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int findCountBySearch(final PagesInfoSearch search) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				String hql = getHqlBySearch(search, "count(model.id)");
				Query query = session.createQuery(hql);

				return ((Long) query.iterate().next()).intValue();
			}
		});
	}
}
