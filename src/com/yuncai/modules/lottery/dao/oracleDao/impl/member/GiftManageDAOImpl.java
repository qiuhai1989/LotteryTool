package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.util.DBHelper;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.GiftManageDAO;
import com.yuncai.modules.lottery.model.Oracle.GiftManage;
import com.yuncai.modules.lottery.model.Oracle.toolType.GiftManageSearch;

/****
 * 红包管理
 * @author TYH
 *
 */
public class GiftManageDAOImpl extends GenericDaoImpl<GiftManage, Integer> implements GiftManageDAO{
//	private static final Log log = LogFactory.getLog(GiftManageDAOImpl.class);

	/***
	 * 根据查询条件获取对象集合
	 */
	@SuppressWarnings("unchecked")
	public List<GiftManage> findBySearch(final GiftManageSearch search,final int offset,final int length) {
		return (List<GiftManage>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = getHqlBySearch(search, "model")+" order by model.startTime desc";	
				Query query = session.createQuery(hql);
				if(DBHelper.isNoNull(search)){
					if(DBHelper.isNoNull(search.getStartDate())){
						query.setParameter("startDate", search.getStartDate1());
					}
					if(DBHelper.isNoNull(search.getEndDate())){
						query.setParameter("endDate", search.getEndDate1());
					}
				}
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();	
			}
		});
	}

	/***
	 * 根据查询条件获取对象记录数
	 */
	@SuppressWarnings("unchecked")
	public int getCountBySearch(final GiftManageSearch search) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = getHqlBySearch(search, "count(model.id)");
				Query query = session.createQuery(hql);
				
				if(DBHelper.isNoNull(search)){
					if(DBHelper.isNoNull(search.getStartDate())){
						query.setParameter("startDate", search.getStartDate1());
					}
					if(DBHelper.isNoNull(search.getEndDate())){
						query.setParameter("endDate", search.getEndDate1());
					}
				}
				
				return ((Long) query.iterate().next()).intValue();
			}
		});
	}
	
	/**
	 * 根据查询条件获取sql语句
	 * @param search 查询条件
	 * @param str 查询内容
	 * @return sql语句
	 */
	private final String getHqlBySearch(final GiftManageSearch search,String str){
		String hql = "select "+str+" from GiftManage as model where 1=1";
		if(DBHelper.isNoNull(search)){
			if(DBHelper.isNoNull(search.getPlatformId())&&search.getPlatformId()>0){
				hql += " and model.platform.id = "+search.getPlatformId();
			}
			if(DBHelper.isNoNull(search.getChannelId())&&search.getChannelId()>0){
				hql += " and model.channel.id = "+search.getChannelId();
			}
			if(DBHelper.isNoNull(search.getGiftName())){
				hql += " and model.giftName like '%"+search.getGiftName()+"%'";
			}
			if(DBHelper.isNoNull(search.getStartDate())){
				hql += " and model.startTime >= :startDate";
			}
			if(DBHelper.isNoNull(search.getEndDate())){
				hql += " and model.endTime >= :endDate";
			}
		}
		return hql;
	}

	//根据活动查询是否存在这个活动
	@SuppressWarnings("unchecked")
	public List<GiftManage> findByChannelList(final String channel,final int is_vail) {
		return (List<GiftManage>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql =" from GiftManage as model where 1=1 ";
				if(DBHelper.isNoNull(channel)){
					hql +=" and model.channel.name = :channelName ";
				}
				if(DBHelper.isNoNull(is_vail)){
					//1充值，0直接赠送
					hql +=" and model.giftType = :giftType ";
				} 
				hql +=" and model.endTime >= sysdate and model.isValid= 1 ";
				Query query = session.createQuery(hql);
				query.setParameter("channelName", channel);
				query.setParameter("giftType", is_vail);
				
				return query.list();
			}
		});
		
	}
	
	//查询活动是否存在
	@SuppressWarnings("unchecked")
	public List<GiftManage> findGift(final String channel,final int is_vail,final int type) {
		return (List<GiftManage>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String d = DBHelper.getNow();
				Date date = DateTools.StringToDate(d);
				String hql =" from GiftManage as model where 1=1 ";
				if(DBHelper.isNoNull(channel))
					hql +=" and model.channel.name = '"+channel+"'";
				if(DBHelper.isNoNull(is_vail))//1充值，0直接赠送
					hql +=" and model.giftType = "+is_vail;
				if(type>0)//2注册，1充值
					hql +=" and model.type ="+type;
				
				hql +=" and model.isValid= 1";
				hql +=" and model.startTime <= :startDate";
				hql +=" and model.endTime >= :endDate";

				Query query = session.createQuery(hql);
				query.setParameter("startDate", date);
				query.setParameter("endDate", date);
				return query.list();
			}
		});
	}
}
