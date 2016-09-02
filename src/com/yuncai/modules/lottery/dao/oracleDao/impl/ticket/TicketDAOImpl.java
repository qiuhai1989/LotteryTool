package com.yuncai.modules.lottery.dao.oracleDao.impl.ticket;

import java.util.List;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.ticket.TicketDAO;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Ticket;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketProvider;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;
import com.yuncai.modules.lottery.model.Sql.Isuses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class TicketDAOImpl extends GenericDaoImpl<Ticket, Integer> implements TicketDAO{
	private static final Log log = LogFactory.getLog(TicketDAOImpl.class);
	@Override
	public List findByPlanNo(Object planNo) {
		// TODO Auto-generated method stub
		return findByProperty(PLAN_NO, planNo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cailele.lottery.dao.impl.ticket.TicketDAO#findByProperty(java.lang
	 *      .String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Ticket instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Ticket as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/***************************************************************************
	 * 
	 * 
	 * 根据菜种和打票地点的id找到未分配到打票地点的票
	 * 
	 **************************************************************************/
	@SuppressWarnings("unchecked")
	public List<Ticket> findByStatusAndlotteryType(final String lotteryType, final TicketStatus status, final Isuses nonceTerm) {
		if (nonceTerm == null) {
			LogUtil.out("lotteryType:" + lotteryType + " have not term,can`t find the ticket!");
			return new ArrayList<Ticket>();
		}
		String queryString = "from Ticket as model where  model.status = ? and model.lotteryType = ?  and model.term=?";
		return getHibernateTemplate().find(queryString,
				new Object[] { status, LotteryType.getItem(Integer.parseInt(lotteryType)), nonceTerm.getName() });
	}
	
	/**
	 * 送票后更新ticket 的状态 和送票时间
	 * 
	 * @param ticketIds
	 */
	@SuppressWarnings("unchecked")
	public void updateStatusForDeliver(final Integer[] ticketIds, final TicketProvider provider) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "update Ticket set provider = :provider, status = 3,sendDateTime=sysdate where id in (:ids)";
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ticketIds);
				query.setParameter("provider", provider);
				query.executeUpdate();
				return null;
			}
		});
	}
	

	/**
	 * 送票后更新ticket 的状态 和送票时间，出票时间
	 * 
	 * @param ticketIds
	 */
	@SuppressWarnings("unchecked")
	public void updateStatusForDeliverSuccess(final Integer[] ticketIds, final TicketProvider provider) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "update Ticket set provider = :provider, status = 4,sendDateTime=sysdate,printDateTime=sysdate where id in (:ids)";
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ticketIds);
				query.setParameter("provider", provider);
				query.executeUpdate();
				return null;
			}
		});
	}

	
	@SuppressWarnings("unchecked")
	public void updateFailure(final Integer id) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "update Ticket set status = 5 where id=:id ";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				query.executeUpdate();
				return null;
			}
		});
	}
	
	/**
	 *  未出票作废更改状态
	 */
	@SuppressWarnings("unchecked")
	public void updateByAbortUnPrint(final Integer planNo) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "update Ticket set status = 5 where planNo=:planNo and status <> 4";
				Query query = session.createQuery(hql);
				query.setParameter("planNo", planNo);
				query.executeUpdate();
				return null;
			}
		});
	}
	
	/**
	 * 查找出当前方案的所有票
	 */
	public List<Ticket> findTicketByLotteryPlan(LotteryPlan lotteryPlan) {
		String hql = "from Ticket as model where model.lotteryType=? and model.term=? and model.planNo=? order by model.printDateTime desc";
		return getHibernateTemplate().find(hql, new Object[] { lotteryPlan.getLotteryType(), lotteryPlan.getTerm(), lotteryPlan.getPlanNo(), });
	}

}
