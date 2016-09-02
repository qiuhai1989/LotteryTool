package com.yuncai.modules.lottery.dao.sqlDao.impl.lottery;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.ChaseTaskDetailsDAO;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Sql.ChaseTaskDetails;

import com.yuncai.modules.lottery.model.Sql.vo.ChaseTasksBean;

public class ChaseTaskDetailsDAOImpl extends GenericDaoImpl<ChaseTaskDetails, Integer> implements  ChaseTaskDetailsDAO{
	private static final Log log = LogFactory.getLog(ChaseTaskDetailsDAOImpl.class);

	/**
	 * 根据方案号找出追号任务
	 * @param planNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ChaseTasksBean findBuyPlanNO(final String planNo){
		return (ChaseTasksBean) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer sql=new StringBuffer();
				sql.append("select modul.UserID,modul.LotteryID,modul.QuashStatus,modul.StopWhenWinMoney,modul.FromClient," +
						"detail.ChaseTaskID,detail.IsuseID,detail.PlayTypeID,detail.Money,detail.Multiple,detail.QuashStatus," +
						"detail.Executed,detail.SchemeID,detail.SecrecyLevel,detail.LotteryNumber,detail.Share,detail.BuyedShare " +
						"from T_ChaseTasks as modul,T_ChaseTaskDetails as detail where detail.ChaseTaskID=modul.ID ");
				if(planNo!=null && !"".equals(planNo)){
					sql.append(" and detail.SchemeID = :schemeId ");
				}
				LogUtil.out("sql:"+sql.toString());
				Query query=session.createSQLQuery(sql.toString());
				if(planNo!=null && !"".equals(planNo)){
					query.setParameter("schemeId", Integer.parseInt(planNo));
				}
				query.setMaxResults(1);
				List list=query.list();
				ChaseTasksBean chase=new ChaseTasksBean();
				if(list !=null && list.size()!=0){
					for(int i=0;i<list.size();i++){
						Object[] o=(Object[])list.get(i);
						ChaseTasksBean bean=new ChaseTasksBean();
						bean.setUserId(((BigInteger)o[0]).intValue());
						bean.setLotteryId((Integer)o[1]);
						bean.setQuashStatus(Short.valueOf(o[2].toString()));
						bean.setStopWhenWinMoney(((BigDecimal)o[3]).doubleValue());
						//String firomClient=o[4].toString()==null?1:o[4].toString();
						bean.setFromClient((Integer)o[4]);
						bean.setChaseTaskId(((BigInteger)o[5]).intValue());
						bean.setIsuseId(((BigInteger)o[6]).intValue());
						bean.setPlayTypeId((Integer)o[7]);
						bean.setMoney(((BigDecimal)o[8]).doubleValue());
						bean.setMultiple((Integer)o[9]);
						bean.setQuashStatusDetail(Short.valueOf(o[10].toString()));
						bean.setExecuted((Boolean)o[11]);
						bean.setSchemeId(((BigInteger)o[12]).intValue());
						bean.setSecrecyLevel(Short.valueOf(o[13].toString()));
						//bean.setLotteryNumber(o[14].toString());
						bean.setShare((Integer)o[15]);
						bean.setBuyedShare((Integer)o[16]);
						chase=bean;
					}
					return chase;
					
			
				}
				return null;
			}
			});
	}
	/**
	 * 根据彩种ID、期数找出追号任务集合
	 * @param lotteryId
	 * @param term
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ChaseTasksBean> findByTermList(final Integer lotteryId,final String term){
		return (List<ChaseTasksBean>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				StringBuffer sql=new StringBuffer();
				sql.append("select modul.UserID,modul.LotteryID,modul.QuashStatus,modul.StopWhenWinMoney,modul.FromClient," +
						"detail.ChaseTaskID,detail.IsuseID,detail.PlayTypeID,detail.Money,detail.Multiple,detail.QuashStatus," +
						"detail.Executed,detail.SchemeID,detail.SecrecyLevel,detail.LotteryNumber,detail.Share,detail.BuyedShare,detail.ID  " +
						"from T_ChaseTasks as modul,T_ChaseTaskDetails as detail where detail.ChaseTaskID=modul.ID ");
				if(lotteryId!=-1 && lotteryId>0){
					sql.append(" and modul.LotteryID = :lotteryId ");
				}
				if(term!=null && !"".equals(term)){
					sql.append(" and detail.IsuseID = :term ");
				}
				LogUtil.out("sql:"+sql.toString());
				Query query=session.createSQLQuery(sql.toString());
				if(lotteryId!=-1 && lotteryId>0){
					query.setParameter("lotteryId", lotteryId);
				}
				
				if(term!=null && !"".equals(term)){
					query.setParameter("term", Integer.parseInt(term));
				}
				
				List list=query.list();
				List<ChaseTasksBean> chaseList=new ArrayList<ChaseTasksBean>();
				if(list !=null && list.size()!=0){
					for(int i=0;i<list.size();i++){
						Object[] o=(Object[])list.get(i);
						ChaseTasksBean bean=new ChaseTasksBean();
						bean.setUserId(((BigInteger)o[0]).intValue());
						bean.setLotteryId((Integer)o[1]);
						bean.setQuashStatus(Short.valueOf(o[2].toString()));
						bean.setStopWhenWinMoney(((BigDecimal)o[3]).doubleValue());
						//String firomClient=o[4].toString()==null?1:o[4].toString();
						bean.setFromClient((Integer)o[4]);
						bean.setChaseTaskId(((BigInteger)o[5]).intValue());
						bean.setIsuseId(((BigInteger)o[6]).intValue());
						bean.setPlayTypeId((Integer)o[7]);
						bean.setMoney(((BigDecimal)o[8]).doubleValue());
						bean.setMultiple((Integer)o[9]);
						bean.setQuashStatusDetail(Short.valueOf(o[10].toString()));
						bean.setExecuted((Boolean)o[11]);
						//bean.setSchemeId(((BigInteger)o[12]).intValue());
						bean.setSecrecyLevel(Short.valueOf(o[13].toString()));
						bean.setLotteryNumber(o[14].toString());
						bean.setShare((Integer)o[15]);
						bean.setBuyedShare((Integer)o[16]);
						bean.setDetailId(((BigInteger)o[17]).intValue());
						chaseList.add(bean);
					}
					return chaseList;
					
			
				}
				return null;
			}
		});
		
	}
	/**
	 * 根据追号ID找出详情
	 * @param chaseNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ChaseTasksBean> findByChaseNoTermList(final Integer chaseNo){
		return (List<ChaseTasksBean>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				StringBuffer sql=new StringBuffer();
				sql.append("select modul.UserID,modul.LotteryID,modul.QuashStatus,modul.StopWhenWinMoney,modul.FromClient," +
						"detail.ChaseTaskID,detail.IsuseID,detail.PlayTypeID,detail.Money,detail.Multiple,detail.QuashStatus," +
						"detail.Executed,detail.SchemeID,detail.SecrecyLevel,detail.LotteryNumber,detail.Share,detail.BuyedShare,detail.ID " +
						"from T_ChaseTasks as modul,T_ChaseTaskDetails as detail where detail.ChaseTaskID=modul.ID ");
				if(chaseNo!=-1 && chaseNo>0){
					sql.append(" and modul.ID = :chaseNo ");
				}
//				LogUtil.out("sql:"+sql.toString());
				Query query=session.createSQLQuery(sql.toString());
				if(chaseNo!=-1 && chaseNo>0){
					query.setParameter("chaseNo", chaseNo);
				}
				
				List list=query.list();
				List<ChaseTasksBean> chaseList=new ArrayList<ChaseTasksBean>();
				if(list !=null && list.size()!=0){
					for(int i=0;i<list.size();i++){
						Object[] o=(Object[])list.get(i);
						ChaseTasksBean bean=new ChaseTasksBean();
						bean.setUserId(((BigInteger)o[0]).intValue());
						bean.setLotteryId((Integer)o[1]);
						bean.setQuashStatus(Short.valueOf(o[2].toString()));
						bean.setStopWhenWinMoney(((BigDecimal)o[3]).doubleValue());
						//String firomClient=o[4].toString()==null?1:o[4].toString();
						bean.setFromClient((Integer)o[4]);
						bean.setChaseTaskId(((BigInteger)o[5]).intValue());
						bean.setIsuseId(((BigInteger)o[6]).intValue());
						bean.setPlayTypeId((Integer)o[7]);
						bean.setMoney(((BigDecimal)o[8]).doubleValue());
						bean.setMultiple((Integer)o[9]);
						bean.setQuashStatusDetail(Short.valueOf(o[10].toString()));
						bean.setExecuted((Boolean)o[11]);
						//bean.setSchemeId(((BigInteger)o[12]).intValue());
						bean.setSecrecyLevel(Short.valueOf(o[13].toString()));
						bean.setLotteryNumber(o[14].toString());
						bean.setShare((Integer)o[15]);
						bean.setBuyedShare((Integer)o[16]);
						bean.setDetailId(((BigInteger)o[17]).intValue());
						chaseList.add(bean);
					}
					return chaseList;
					
			
				}
				return null;
			}
		});
	}
	
	public ChaseTaskDetails findByIdForUpdate(Integer id){
		log.debug("getting LotteryPlan instance with id: " + id);
		try {
			getHibernateTemplate().flush();
			getHibernateTemplate().evict(this.find(ChaseTaskDetails.class, id));
			ChaseTaskDetails instance = (ChaseTaskDetails) getHibernateTemplate().get(ChaseTaskDetails.class, id, LockMode.UPGRADE);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<ChaseTaskDetails> findByChaseTaskIDForDeList(final Integer id) {
		// TODO Auto-generated method stub
		
		
		return (List<ChaseTaskDetails>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select model,isuses.name from ChaseTaskDetails as model,Isuses as isuses  where model.isuseId = isuses.id and model.chaseTaskId = :chaseTaskId order by isuses.name desc ";
				Query query = session.createQuery(hql);
				query.setParameter("chaseTaskId", id);
				List list = query.list();
				List<ChaseTaskDetails> detList = new ArrayList<ChaseTaskDetails>();
				for(Iterator iterator=list.iterator() ;iterator.hasNext();){
					Object[]obj = (Object[])iterator.next();
					detList.add((ChaseTaskDetails) obj[0]);
					System.out.println(obj[1]);
				}
				
				return detList;
			}
			
		});
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findByChaseTaskIDForDeList2(final Integer id) {
		// TODO Auto-generated method stub
		
		
		return (List<Object[]>)this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select model,isuses.name from ChaseTaskDetails as model,Isuses as isuses  where model.isuseId = isuses.id and model.chaseTaskId = :chaseTaskId order by isuses.name desc ";
				Query query = session.createQuery(hql);
				query.setParameter("chaseTaskId", id);
//				List list = query.list();
//				List<ChaseTaskDetails> detList = new ArrayList<ChaseTaskDetails>();
//				for(Iterator iterator=list.iterator() ;iterator.hasNext();){
//					Object[]obj = (Object[])iterator.next();
//					detList.add((ChaseTaskDetails) obj[0]);
//					System.out.println(obj[1]);
//				}
//				
				return query.list();
			}
			
		});
	}


}
