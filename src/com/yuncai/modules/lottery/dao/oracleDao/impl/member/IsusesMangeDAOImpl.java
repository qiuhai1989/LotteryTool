package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.lottery.IsusesMangeSearch;
import com.yuncai.modules.lottery.bean.vo.member.IsusesMangeVo;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.IsusesMangeDAO;
import com.yuncai.modules.lottery.model.Oracle.IsusesMange;

/**
 * TODO
 * @author gx
 * Dec 13, 2013 5:27:46 PM
 */
public class IsusesMangeDAOImpl extends GenericDaoImpl<IsusesMange,Integer> implements IsusesMangeDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<IsusesMangeVo> findBySearch(final IsusesMangeSearch search, final int offset, final int length, final int platformID) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				String str = "new com.yuncai.modules.lottery.bean.vo.member.IsusesMangeVo(im,p)";
				String hql = getHqlBySearch(search, str,platformID);
				hql += " order by im.sorder";
				Query query = session.createQuery(hql);
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public int getCountBySearch(final IsusesMangeSearch search,final int platformID) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String str = "count(im.id)";
				String hql = getHqlBySearch(search, str,platformID);
				
				Query query = session.createQuery(hql);

				return ((Long) query.iterate().next()).intValue();
			}
		});
	}
	
	private String getHqlBySearch(IsusesMangeSearch search,String str,int platformID){
		String hql = "select "+str+" from IsusesMange as im,Platform as p where im.platformID=p.id";
		hql +=" and p.id = " + platformID;
		if(DBHelper.isNoNull(search)){
			if(DBHelper.isNoNull(search.getName()))
				hql +=" and im.name like '%"+ search.getName() +"%'";
		}
		return hql;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IsusesMange> findByPlatformID_isShow(final int isShow,final int platformID) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from IsusesMange where platformID="+platformID+" and isShow="+isShow;
				hql += " order by sorder";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IsusesMange> findByChannelID_isShow(final int i, final int channelID) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from IsusesMange where channelID="+channelID+" and isShow="+i;
				hql += " order by sorder";
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
}
