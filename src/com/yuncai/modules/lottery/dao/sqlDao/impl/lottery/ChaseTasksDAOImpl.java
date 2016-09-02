package com.yuncai.modules.lottery.dao.sqlDao.impl.lottery;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.And;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.ChaseTasksDAO;

import com.yuncai.modules.lottery.model.Sql.ChaseTasks;

import oracle.net.aso.q;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;


public class ChaseTasksDAOImpl extends GenericDaoImpl<ChaseTasks, Integer> implements ChaseTasksDAO {
	private static final Log log = LogFactory.getLog(ChaseTasksDAOImpl.class);
	/**
	 * 防重
	 */
	@Override
	public ChaseTasks findByIdForUpdate(Integer id) {
		log.debug("getting LotteryPlan instance with id: " + id);
		try {
			getHibernateTemplate().flush();
			getHibernateTemplate().evict(this.find(ChaseTasks.class, id));
			ChaseTasks instance = (ChaseTasks) getHibernateTemplate().get(ChaseTasks.class, id, LockMode.UPGRADE);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<ChaseTasks> findRecordForAccount(final Integer userId,final Integer lotteryId,final Date startDate,final Date endDate) {
		// TODO Auto-generated method stub
		
		return (List<ChaseTasks>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer();
				sbf.append("from ChaseTasks as model where 1=1 ");
				
				sbf.append(" and model.userId = :userId");
				
				if(lotteryId>0){
					sbf.append(" and model.lotteryId = :lotteryId");
				}
				
				sbf.append(" and model.dateTime between :begin and :end ");
				
				
				
				Query query = session.createQuery(sbf.toString());
				query.setParameter("userId", userId);
				if(lotteryId>0){
					query.setParameter("lotteryId", lotteryId);
				}
				query.setParameter("begin", startDate);
				query.setParameter("end", endDate);
				
				return query.list();
			}
			
		});
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<ChaseTasks> findRecordForList(final Integer userId,final Integer lotteryId,final Date startDate,final Date endDate,final Integer taskId,final Integer order,
			final	Integer pageSize,final Short status) {
		// TODO Auto-generated method stub
		
		return (List<ChaseTasks>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer();
				sbf.append("from ChaseTasks as model where 1=1 ");
				
				sbf.append(" and model.userId = :userId");
				
				if(lotteryId>0){
					sbf.append(" and model.lotteryId = :lotteryId");
				}
				
				sbf.append(" and model.dateTime between :begin and :end ");
				
				if(status>=0){
					sbf.append(" and model.quashStatus = :quashStatus ");
				}
				
				//翻页查询
				if(taskId!=0&&order!=-1){
					if(order==0){
						sbf.append(" and model.id < :taskId order by model.id ");
					}
					if(order==1){
						sbf.append(" and model.id >= :taskId order by model.id desc");
					}
				}
				//默认显示
				if(taskId==0){
					sbf.append( "  order by model.id desc");
				}
				
				
				Query query = session.createQuery(sbf.toString());
				query.setParameter("userId", userId);
				if(lotteryId>0){
					query.setParameter("lotteryId", lotteryId);
				}
				query.setParameter("begin", startDate);
				query.setParameter("end", endDate);
				if(status>=0){
					query.setParameter("quashStatus", status);
				}
				if(taskId!=0&&order!=-1){
					query.setParameter("taskId", taskId);
				}
				query.setFirstResult(0);
				query.setMaxResults(pageSize);
				
				return query.list();
			}
			
		});
	}

}
