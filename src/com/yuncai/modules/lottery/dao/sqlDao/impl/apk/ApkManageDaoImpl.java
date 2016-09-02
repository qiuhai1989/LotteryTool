package com.yuncai.modules.lottery.dao.sqlDao.impl.apk;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkManageDAO;
import com.yuncai.modules.lottery.model.Sql.ApkManage;

public class ApkManageDaoImpl extends GenericDaoImpl<ApkManage, Integer> implements ApkManageDAO{


	/***
	 * 对象集合
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> find(final int offset, final int length,final int packageId,final Search search) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = getSql(search, "model.*,c.real_name as cname,p.name as pname",packageId)+" order by model.updVersion desc";
							
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;
				query.setFirstResult(offset);
				query.setMaxResults(length);

				return query.list();
			}
		});
	}

	/***
	 * 对象集合长度
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getCount(final Search search,final int packageId) {
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String sql  =getSql(search, "count(*)",packageId);
				Query query = session.createSQLQuery(sql);

				return query.list().get(0);
			}
		});	
	}
	
	private String getSql(Search search,String str,int packageId){
		
		String sql  = "select "+str+" from T_ApkManage model,T_Channel c,T_Platform as p where 1=1 and c.id = model.packageId and c.platformID=p.id and c.ISHIDE=0";
		if(packageId > 0){
			sql += " and model.packageId = "+packageId;
		}
		if(DBHelper.isNoNull(search)){
			if(DBHelper.isNoNull(search.getUpdVersion())){
				sql += " and model.updVersion like '"+search.getUpdVersion()+"'";
			}
			if(search.getPlatformId()>0){
				sql += " and p.id = "+search.getPlatformId();
			}
		}
		
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaxVersion(final int packageId) {
		return (String) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				String sql  = "select max(updVersion) from T_ApkManage where packageId = "+packageId;
				Query query = session.createSQLQuery(sql);

				return query.list().get(0);
			}
		});	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findUpdVersion(final int platformId,final int packageId) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql  = "select distinct updVersion from T_ApkManage,T_Channel where T_Channel.id = T_ApkManage.packageId";
				if(packageId>0)
					sql += " and T_ApkManage.packageId = "+packageId;
				if(platformId>0)
					sql += " and T_Channel.platformID = "+platformId;
				sql+= " order by updVersion desc";
				Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);;

				return query.list();
			}
		});
	}
	
	//查询当前渠道最新版本号说明
	@SuppressWarnings("unchecked")
	@Override
	public String findLastBak(final Integer channelid,final String maxVersion) {
		return (String) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql  = "select info from T_ApkManage where packageId = " +channelid+ " and updVersion='"+maxVersion+"' order by versionNameCode desc";
				Query query = session.createSQLQuery(sql);
				return query.list().get(0);
			}
		});	
	}
}
