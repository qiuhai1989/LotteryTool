package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.bean.search.ActivitiesManagementModuleSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.ActivitiesManagementModuleDAO;
import com.yuncai.modules.lottery.model.Oracle.ActivitiesManagementModule;

/**
 * 活动模块
 * 
 * @author gx Dec 13, 2013 4:19:38 PM
 */
@SuppressWarnings("unchecked")
public class ActivitiesManagementModuleDAOImpl extends GenericDaoImpl<ActivitiesManagementModule, Integer> implements ActivitiesManagementModuleDAO {

	@Override
	public List<ActivitiesManagementModule> findBySearch(final ActivitiesManagementModuleSearch search, final int offset, final int length) {
		return (List<ActivitiesManagementModule>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String sql = getSql(search, "amm.*");
				Query query = session.createSQLQuery(sql).addEntity(ActivitiesManagementModule.class);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}
		});
	}

	@Override
	public int getCountBySearch(final ActivitiesManagementModuleSearch search) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = getSql(search, "amm.ID");
				Query query = session.createSQLQuery(sql);
				return query.list().size();
			}
		});
	}

	private String getSql(ActivitiesManagementModuleSearch search, String str) {
		String sql = "select " + str + " from T_ACTIVITIES_MANAGEMENT_MODULE amm where 1=1";
		if (DBHelper.isNoNull(search)) {
			if (DBHelper.isNoNull(search.getIsHide()))
				sql += " and amm.ISHIDE=" + search.getIsHide();
			if (DBHelper.isNoNull(search.getName()))
				sql += " and amm.NAME like '%" + search.getName() + "%'";
		}
		sql += " order by amm.SORDE asc";
		return sql;
	}

	// 接口传送数据。根据渠道传送有效活动
	@Override
	public List<ActivitiesManagementModule> findEffective(final String channelRealName) {
		return (List<ActivitiesManagementModule>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String now = DBHelper.getNow();
				String sql = "select * from T_ACTIVITIES_MANAGEMENT_MODULE where 1=1 " + "and EFFECTIVE=1 and ISHIDE=0 and (CHANNEL like '%"
						+ channelRealName + "%' or CHANNEL='全部渠道')";
				sql +=" and NVL(stime,'2213-01-01 00:00:00')<='"+now+"' and NVL(etime,'"+now+"')>='"+now+"' order by SORDE asc";
				Query query = session.createSQLQuery(sql).addEntity(ActivitiesManagementModule.class);
				return query.list();
			}
		});
	}
}
