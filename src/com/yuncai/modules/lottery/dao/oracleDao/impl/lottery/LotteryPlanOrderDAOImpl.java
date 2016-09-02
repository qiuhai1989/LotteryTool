package com.yuncai.modules.lottery.dao.oracleDao.impl.lottery;

import java.sql.SQLException;
import java.util.*;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanOrderDAO;

import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

public class LotteryPlanOrderDAOImpl extends GenericDaoImpl<LotteryPlanOrder, Integer> implements LotteryPlanOrderDAO {
	private static final Log log = LogFactory.getLog(LotteryPlanOrderDAOImpl.class);

	/**
	 * 根据定单编号查询成功的跟单列表（做钱包操作的，按账号排序,防止死锁）
	 * 
	 * @param planNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findSuccessByPlanNo(final Integer planNo) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {

				StringBuffer hsql = new StringBuffer();
				hsql.append("from LotteryPlanOrder as model where 1=1  and model.planNo = :planNo and model.status = :status ");
				hsql.append(" order by model.account");
				Query query = session.createQuery(hsql.toString());
				query.setParameter("planNo", planNo);
				query.setParameter("status", PlanOrderStatus.PAYED);
				List list = new ArrayList();
				list = query.list();
				return list;

			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public LotteryPlanOrder findSearchList(final Integer planNo, final Integer orderNo, final String account) {
		log.debug("getting LotteryPlan instance with id: " + orderNo);
		getHibernateTemplate().flush();
		getHibernateTemplate().evict(this.find(LotteryPlanOrder.class, orderNo));
		return (LotteryPlanOrder) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from LotteryPlanOrder as model where model.planNo = :planNo and orderNo = :orderNo and account = :account ";

				Query query = session.createQuery(hql);
				query.setLockMode("model", LockMode.UPGRADE);
				query.setParameter("planNo", planNo);
				query.setParameter("orderNo", orderNo);
				query.setParameter("account", account);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});

	}

	@Override
	public List<String> findInPeople(Integer planNo) {
		// TODO Auto-generated method stub
		String hql = "select distinct model.account from LotteryPlanOrder as model where model.planNo =" + planNo;
		List<String> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<HmShowBean> findHmShowBeans(String username, Date startDate, Date endDate, PlanType planType, List<Integer> listPlanNo, int planNo,
			int order, int offset, int pageSize) {
		// TODO Auto-generated method stub

		return this.findHmShowBeans(username, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize, PlanStatus.getItem(2));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HmShowBean> findHmShowBeans(final String username, final Date startDate, final Date endDate, final PlanType planType,
			final List<Integer> listPlanNo, final int planNo, final int order, final int offset, final int pageSize, final PlanStatus planStatus) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer(
						"select new com.yuncai.modules.lottery.bean.vo.HmShowBean(p.account, p.lotteryType, p.playType, p.term, o.part,p.planNo, p.planStatus, o.orderNo, o.amount, p.content, p.winStatus, o.status, o.posttaxPrize, o.createDateTime, p.dealDateTime, o.buyType, p.isUploadContent, p.selectType, p.publicStatus, p.soldPart, p.reservePart, 0, p.planTicketStatus, p.planType, o.followingType)from LotteryPlanOrder o , LotteryPlan p where o.planNo = p.planNo ");
				if (planType.getValue() != -1) {
					sbf.append(" and p.planType=:planType ");
				}
				if (username != null && !"".equals(username)) {
					sbf.append(" and p.account=:account ");
				}
				// planNo单据号存在再能结合order使用,order=0往上，order=1往下
				if (planNo != 0) {
					if (order == 0) {
						sbf.append(" and p.planNo <:planNo ");
					}
					if (order == 1) {
						sbf.append(" and p.planNo >:planNo ");
					}
				}
				if (listPlanNo != null && listPlanNo.size() > 0) {
					sbf.append(" and p.planNo in (:listPlanNo) ");
				}

				sbf.append(" and o.createDateTime between :startDate and :endDate");
				// 向上翻页数据由近到远 需倒叙输出
				if (planNo != 0 && order == 0) {
					sbf.append(" order by p.planNo desc ");
				}
				//LogUtil.out(sbf.toString());
				Query query = session.createQuery(sbf.toString());
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				if (planType.getValue() != -1) {
					query.setParameter("planType", planType);
				}
				if (username != null && !"".equals(username)) {
					query.setParameter("account", username);
				}
				if (planNo != 0) {
					query.setParameter("planNo", planNo);
				}
				if (listPlanNo != null && listPlanNo.size() > 0) {
					query.setParameterList("listPlanNo", listPlanNo);
				}
				query.setFirstResult((offset - 1)*pageSize);
				query.setMaxResults(pageSize);
				return query.list();
			}

		});
	}

	@Override
	public List<HmShowBean> findHmShowBeans(String account, Date startDate, Date endDate, PlanType planType, List<Integer> listPlanNo, int planNo,
			int order, int offset, int pageSize, String userName,final LotteryType lotteryType) {
		// TODO Auto-generated method stub

		return findHmShowBeans(account, startDate, endDate, planType, listPlanNo, planNo, order, offset, pageSize, userName,lotteryType, PlanStatus.getItem(-1));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HmShowBean> findHmShowBeans(final String account, final Date startDate, final Date endDate, final PlanType planType,
			final List<Integer> listPlanNo, final int planNo, final int order, final int offset, final int pageSize, final String userName,final LotteryType lotteryType,
			final PlanStatus planStatus) {
		return (List) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer(
						"select new com.yuncai.modules.lottery.bean.vo.HmShowBean(p.account, p.lotteryType, p.playType, p.term, o.part,p.planNo, p.planStatus, o.orderNo, o.amount, p.content, p.winStatus, o.status, o.posttaxPrize, o.createDateTime, p.dealDateTime, o.buyType, p.isUploadContent, p.selectType, p.publicStatus, p.soldPart, p.reservePart, 0, p.planTicketStatus, p.planType, o.followingType)from LotteryPlanOrder o , LotteryPlan p where o.planNo = p.planNo ");
				if (planType.getValue() != -1) {
					sbf.append(" and p.planType=:planType ");
				}
				if (account != null && !"".equals(account)) {
					sbf.append(" and p.account=:account ");
				}
				if (userName != null && !"".equals(userName)) {
					sbf.append(" and o.account =:userName ");
				}
				//如果为72或73特殊处理				
				if(lotteryType.getValue()>0&&lotteryType.getValue()!=72&&lotteryType.getValue()>0&&lotteryType.getValue()!=73){
					sbf.append(" and p.lotteryType = :lotteryType");
				}else if(lotteryType.getValue()==72||lotteryType.getValue()==73){
					sbf.append( "  and p.lotteryType in(:alist)" );
				}
				
				// planNo单据号存在再能结合order使用,order=0往上，order=1往下
				if (planNo != 0) {
					if(order==-1){
						sbf.append("and o.orderNo =:planNo");
					}
					
					if (order == 0) {
						sbf.append(" and o.orderNo >:planNo ");
					}
					if (order == 1) {
						sbf.append(" and o.orderNo <:planNo  ");
					}
				}
				if (listPlanNo != null && listPlanNo.size() > 0) {
					sbf.append(" and o.orderNo in (:listPlanNo) ");
				}

				if(planStatus.getValue()>0){
					sbf.append(" and  p.planStatus =:planStatus");
				}
				
				sbf.append(" and o.createDateTime between :startDate and :endDate");
				// 向上翻页数据由近到远 需倒叙输出
				if (planNo != 0 && order == 0) {
					sbf.append(" order by o.orderNo desc ");
				}else {
					sbf.append(" order by o.orderNo desc ");
				}
//				LogUtil.out(sbf.toString());
				Query query = session.createQuery(sbf.toString());
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				if (lotteryType.getValue()>0&&lotteryType.getValue()!=72&&lotteryType.getValue()>0&&lotteryType.getValue()!=73) {
					query.setParameter("lotteryType", lotteryType);
				}else if(lotteryType.getValue()==72||lotteryType.getValue()==73){
					if(lotteryType.getValue()==72){
						query.setParameterList("alist", LotteryType.JCZQList);
					}else if(lotteryType.getValue()==73){
						query.setParameterList("alist", LotteryType.JCLQList);
					}
					
					
				}
				
				
				if (planType.getValue() != -1) {
					query.setParameter("planType", planType);
				}
				if (account != null && !"".equals(account)) {
					query.setParameter("account", account);
				}
				if (planNo != 0) {
					query.setParameter("planNo", planNo);
				}
				if (listPlanNo != null && listPlanNo.size() > 0) {
					query.setParameterList("listPlanNo", listPlanNo);
				}
				if (userName != null && !"".equals(userName)) {
					query.setParameter("userName", userName);
				}
				
				if(planStatus.getValue()>0){
					query.setParameter("planStatus", planStatus);
				}
				query.setFirstResult(0);
				query.setMaxResults(pageSize);
				return query.list();
			}

		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LotteryPlanOrder> findHMListByPlanNoAndBuyType(final int planNo,final BuyType buyType) {
		// TODO Auto-generated method stub
		
		return (List<LotteryPlanOrder>)this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer("from LotteryPlanOrder as model where model.planNo =:planNo ");
				if(buyType!=null){
					sbf.append(" and model.buyType = :buyType ");
				}
				sbf.append(" order by model.createDateTime desc");
				Query query = session.createQuery(sbf.toString());
				query.setParameter("planNo", planNo);
				if(buyType!=null){
					query.setParameter("buyType", buyType);
				}
				
				return query.list();
			}
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public HmShowBean getHmShowBeanByPlanOrderNo(final String planOrderNo) {
		// TODO Auto-generated method stub
		
		
		return (HmShowBean)this.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select new com.yuncai.modules.lottery.bean.vo.HmShowBean(p.account, p.lotteryType, p.playType, p.term, o.part,p.planNo, p.planStatus, o.orderNo, o.amount, p.content, p.winStatus, o.status,o.posttaxPrize, o.createDateTime, p.dealDateTime, o.buyType, p.isUploadContent, p.selectType, p.publicStatus, p.soldPart, p.reservePart, 0, p.planTicketStatus, p.planType, o.followingType, '0', p.multiple,p.commision, p.amount, p.title, p.planDesc,p.nickName,p.part,p.pretaxPrize,p.lateDateTime)from LotteryPlanOrder o , LotteryPlan p where o.planNo = p.planNo and o.orderNo=:orderNo ";
				
				Query query = session.createQuery(hql);
				
				query.setParameter("orderNo", Integer.parseInt(planOrderNo) );
				
				
				return query.uniqueResult();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findHMListNickNameByPlanNoAndBuyType(final int planNo,final BuyType buyType) {
		// TODO Auto-generated method stub
		
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
//				String hql = "select model ,mem.nickName from LotteryPlanOrder as model" +
//						" ,Member as mem where model.account = mem.account  and " +
//						" model.planNo =:planNo and model.buyType = :buyType order by " +
//						"model.createDateTime ";
				StringBuffer sbf = new StringBuffer("select model ,mem.nickName from LotteryPlanOrder as model ,Member as mem where model.account = mem.account and model.planNo =:planNo ");
				if(buyType!=null){
					sbf.append(" and model.buyType = :buyType ");
				}
				sbf.append("order by  model.createDateTime");
				Query query = session.createQuery(sbf.toString());
				query.setParameter("planNo", planNo);
				if(buyType!=null){
					query.setParameter("buyType", buyType);
				}
				
				return query.list();
			}
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HmShowBean> findTransationFlow(final String account,final Date startDate,final Date endDate,final PlanType planType,final List<Integer> listPlanNo,final int planNo,
			final int order,final int offset,final int pageSize,final String userName,final LotteryType lotteryType,final List<LotteryType> refuseList) {
		// TODO Auto-generated method stub
		

		return (List) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer(
						"select new com.yuncai.modules.lottery.bean.vo.HmShowBean(p.account, p.lotteryType, p.playType, p.term, o.part,p.planNo, p.planStatus, o.orderNo, o.amount, p.content, p.winStatus, o.status, o.posttaxPrize, o.createDateTime, p.dealDateTime, o.buyType, p.isUploadContent, p.selectType, p.publicStatus, p.soldPart, p.reservePart, 0, p.planTicketStatus, p.planType, o.followingType)from LotteryPlanOrder o , LotteryPlan p where o.planNo = p.planNo ");
				if (planType.getValue() != -1) {
					sbf.append(" and p.planType=:planType ");
				}
				if (account != null && !"".equals(account)) {
					sbf.append(" and p.account=:account ");
				}
				if (userName != null && !"".equals(userName)) {
					sbf.append(" and o.account =:userName ");
				}
				//如果为72或73特殊处理				
				if(lotteryType.getValue()>0&&lotteryType.getValue()!=72&&lotteryType.getValue()>0&&lotteryType.getValue()!=73){
					sbf.append(" and p.lotteryType = :lotteryType");
				}else if(lotteryType.getValue()==72||lotteryType.getValue()==73){
					sbf.append( "  and p.lotteryType in(:alist)" );
				}
				
				if(refuseList!=null){
					//最好改成not exists 形式
//					sbf.append( "  and p.lotteryType not in(:rList)" );
					sbf.append(" and not exists (select  1  from LotteryPlan  where lotteryType =p.lotteryType and p.lotteryType in(:rList))");
					
				}
				
				
				// planNo单据号存在再能结合order使用,order=0往上，order=1往下
				if (planNo != 0) {
					if(order==-1){
						sbf.append("and o.orderNo =:planNo");
					}
					
					if (order == 0) {
						sbf.append(" and o.orderNo >:planNo ");
					}
					if (order == 1) {
						sbf.append(" and o.orderNo <:planNo  ");
					}
				}
				if (listPlanNo != null && listPlanNo.size() > 0) {
					sbf.append(" and o.orderNo in (:listPlanNo) ");
				}

//				if(planStatus.getValue()>0){
//					sbf.append(" and  p.planStatus =:planStatus");
//				}
				
				sbf.append(" and o.createDateTime between :startDate and :endDate");
				// 向上翻页数据由近到远 需倒叙输出
				if (planNo != 0 && order == 0) {
					sbf.append(" order by o.orderNo desc ");
				}else {
					sbf.append(" order by o.orderNo desc ");
				}
//				LogUtil.out(sbf.toString());
				Query query = session.createQuery(sbf.toString());
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				if (lotteryType.getValue()>0&&lotteryType.getValue()!=72&&lotteryType.getValue()>0&&lotteryType.getValue()!=73) {
					query.setParameter("lotteryType", lotteryType);
				}else if(lotteryType.getValue()==72||lotteryType.getValue()==73){
					if(lotteryType.getValue()==72){
						query.setParameterList("alist", LotteryType.JCZQList);
					}else if(lotteryType.getValue()==73){
						query.setParameterList("alist", LotteryType.JCLQList);
					}
					
					
				}
				
				
				if (planType.getValue() != -1) {
					query.setParameter("planType", planType);
				}
				if (account != null && !"".equals(account)) {
					query.setParameter("account", account);
				}
				if (planNo != 0) {
					query.setParameter("planNo", planNo);
				}
				if (listPlanNo != null && listPlanNo.size() > 0) {
					query.setParameterList("listPlanNo", listPlanNo);
				}
				if (userName != null && !"".equals(userName)) {
					query.setParameter("userName", userName);
				}
				if(refuseList!=null){
					query.setParameterList("rList", refuseList);
					
				}
//				if(planStatus.getValue()>0){
//					query.setParameter("planStatus", planStatus);
//				}
				query.setFirstResult(0);
				query.setMaxResults(pageSize);
				return query.list();
			}

		});
	}

}
