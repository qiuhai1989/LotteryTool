package com.yuncai.modules.lottery.dao.sqlDao.impl.lottery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;

import com.yuncai.modules.lottery.dao.sqlDao.lottery.IsusesDAO;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;

import java.sql.SQLException;
import java.util.*;

public class IsusesDAOImpl extends GenericDaoImpl<Isuses, Integer> implements IsusesDAO {
	private static final Log log = LogFactory.getLog(IsusesDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Isuses findByLotteryTypeAndTerm(final LotteryType lotType, final String term) {
		return (Isuses) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hsql = new StringBuffer();
				hsql.append("from  Isuses as  model where  1=1 and model.id = :name ");
				if (lotType.getValue() >= 0)
					hsql.append(" and model.lotteryId = :lotteryId");
				hsql.append(" order by model.name desc");

				Query query = session.createQuery(hsql.toString());// session.createSQLQuery(hsql.toString());
				if (lotType.getValue() >= 0) {

					if (LotteryType.JCZQList.contains(lotType)) {
						query.setParameter("lotteryId", 72);
					}else if(LotteryType.JCLQList.contains(lotType)){
						query.setParameter("lotteryId", 73);
					}else {
						query.setParameter("lotteryId", lotType.getValue());
					}

				}

				query.setParameter("name", Integer.parseInt(term));
				query.setMaxResults(1);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Isuses findByIsusesTerm(final LotteryType lotType, final String termID) {
		return (Isuses) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hsql = new StringBuffer();
				hsql.append("from  Isuses as  model where  1=1 and model.name = :name ");
				if (lotType.getValue() >= 0)
					hsql.append(" and model.lotteryId = :lotteryId");
				hsql.append(" order by model.name desc");

				Query query = session.createQuery(hsql.toString());// session.createSQLQuery(hsql.toString());
				
				if (lotType.getValue() >= 0) {

					if (LotteryType.JCZQList.contains(lotType)) {
						query.setParameter("lotteryId", 72);
					}else if(LotteryType.JCLQList.contains(lotType)){ 
						query.setParameter("lotteryId", 73);
					}else {
						query.setParameter("lotteryId", lotType.getValue());
					}

				}
				query.setParameter("name", termID);
				query.setMaxResults(1);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	/**
	 * 获取下一期的彩期
	 * 
	 * @param lottery
	 * @param term
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Isuses findNextByLotteryTypeAndTerm(final LotteryType lottery, final String term) {
		return (Isuses) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from Isuses as model where model.lotteryId = :lotteryType and model.name > :term order by model.name asc";
				Query query = session.createQuery(hql);
				query.setParameter("lotteryType", lottery.getValue());
				query.setParameter("term", term);
				query.setMaxResults(1);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Isuses findCurrentTermByDate(final LotteryType lotteryType, final Date nowDate) {
		// 改变查询方式取消按时查询改为按state 和currentIsuses查询

		return (Isuses) getHibernateTemplate().execute(new HibernateCallback() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
			 */
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from Isuses as model where model.lotteryId = :lotteryType "
						+ " and model.state= :state  order by model.endTime asc ";
				Query query = session.createQuery(hql);
				query.setParameter("state", LotteryTermStatus.OPEN);
				if (lotteryType.getValue() == LotteryType.ZCRJC.getValue()) {
					query.setParameter("lotteryType", LotteryType.ZCSFC.getValue());
				} else {
					query.setParameter("lotteryType", lotteryType.getValue());
				}

				query.setMaxResults(1);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}

		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Isuses findPreByLotteryTypeAndTerm(final LotteryType lotteryType, final String name) {
		return (Isuses) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
//				String hql = "from Isuses as model where model.lotteryId = :lotteryType and model.name < :term and model.isOpened=1   order by model.name desc";
				String sql = "select top 1 * from T_Isuses model where  model.LotteryID=? and model.Name< ? and model.IsOpened=1 order by model.name desc";
				
				Query query = session.createSQLQuery(sql).addEntity(Isuses.class);
				query.setParameter(0, lotteryType.getValue());
				query.setParameter(1, name);
				return query.uniqueResult();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Isuses> findOverdueByDateAndUnReturnPrizeTerm(final LotteryType lotteryType) {
		// TODO Auto-generated method stub

		return (List<Isuses>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from Isuses as model where model.lotteryId = :lotteryType and model.endTime< :endTime and model.endTime> :startTime "
						+ " and  model.state != -100 and model.state != 5 order by model.name asc ";
				Query query = session.createQuery(hql);
				int t = 15; // 默认的补漏的时间跨度
				// 计算开始结束时间 , 优化查询范围，只查找最近t天的已开奖彩期
				Calendar ca = Calendar.getInstance();
				Date endTime = ca.getTime();
				ca.set(Calendar.HOUR, 0);
				ca.add(Calendar.DAY_OF_MONTH, -t);
				Date startTime = ca.getTime();
				query.setParameter("startTime", startTime);
				query.setParameter("endTime", endTime);
				if (lotteryType.getValue() == LotteryType.ZCRJC.getValue()) {
					query.setParameter("lotteryType", LotteryType.ZCSFC.getValue());
				} else {
					query.setParameter("lotteryType", lotteryType.getValue());
				}
				List list = query.list();

				if (list != null && list.size() != 0) {
					return list;
				}
				return null;
			}
		});
	}
	
	// 查找前100期彩期信息
	@Override
	@SuppressWarnings("unchecked")
	public List<Isuses> findPre100OpenPrizeTerm(final LotteryType lotteryType) {
		return (List<Isuses>) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from Isuses as model where model.lotteryId = :lotteryType "
						+ " and isOpened = 1 order by model.name desc ";
				Query query = session.createQuery(hql);
				query.setParameter("lotteryType", lotteryType.getValue());
				query.setFirstResult(0);
				query.setMaxResults(100);
				List list = query.list();

				if (list != null && list.size() != 0) {
					return list;
				}
				return null;
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initCurrentFlag(final LotteryType lotteryType) {
		// TODO Auto-generated method stub

		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "update Isuses  set currentIsuses= :currentIsuses where lotteryId = :lotteryId ";
				Query query = session.createQuery(hql);
				query.setParameter("currentIsuses", CommonStatus.NO.getValue());
				query.setParameter("lotteryId", lotteryType.getValue());
				query.executeUpdate();
				return null;
			}

		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Isuses findPrintingTermByLotteryType(final LotteryType lotteryType) {
		return (Isuses) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				LotteryType lt = null;

				if (LotteryType.JCZQList.contains(lotteryType)) {
					lt = LotteryType.getItem(72);
				}else if(LotteryType.JCLQList.contains(lotteryType)){
					lt = LotteryType.getItem(73);
				}else {
					lt = lotteryType;
				}

				String hql = "from Isuses as model where model.lotteryId = :lotteryType and model.terminalEndDateTime > :nowdate order by model.name asc";
				Query query = session.createQuery(hql);
				query.setParameter("lotteryType", lt.getValue());
				query.setParameter("nowdate", new Date());
				query.setMaxResults(1);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Isuses> FindPrizeHistory(final Integer lotteryId, final String term, final Integer offset, final Integer order, final Integer pageSize) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer("from Isuses as model where 1=1  and model.state >=:state ");

				if(lotteryId>0){
					sbf.append(" and  model.lotteryId = :lotteryId");
				}
				
				// 查询当前期
				if (Integer.parseInt(term) != 0 && order == -1) {
					sbf.append(" and model.name = :term ");
				}
				// 翻页查询
				if (Integer.parseInt(term) != 0 && order != -1) {
					if (order == 1) {
						sbf.append(" and model.name <=:term order by model.name desc");
					}
					if (order == 0) {
						sbf.append(" and model.name >=:term  order by model.name ");
					}
				}
				// 查询所有
				if (Integer.parseInt(term) == 0 && order == -1) {
					sbf.append(" order by model.name desc");
				}
//				LogUtil.out(sbf.toString());
				Query query = session.createQuery(sbf.toString());
				
				if(lotteryId>0){
					query.setParameter("lotteryId", lotteryId);
				}
				
				// 设置查询条件为已经派奖
				// 测试改为5 正常为4
				query.setParameter("state", LotteryTermStatus.getItem(4));
				if (Integer.parseInt(term) != 0 && order == -1) {
					query.setParameter("term", term);
				}
				if (Integer.parseInt(term) != 0 && order != -1) {
					query.setParameter("term", term);

				}

				query.setFirstResult((offset - 1)*pageSize);
				query.setMaxResults(pageSize);

				return query.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Isuses> findCurrentTermList(final Integer lotteryType) {
		// TODO Auto-generated method stub
		
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer("from Isuses as model where model.currentIsuses=1 ");
				if(lotteryType!=null && lotteryType>0){
					sbf.append(" and model.lotteryId = :lotteryId");
				}
				sbf.append(" and model.state =:state ");
				Query query = session.createQuery(sbf.toString());
				if(lotteryType!=null && lotteryType!=-1){
					query.setParameter("lotteryId", lotteryType);
				}
				query.setParameter("state", LotteryTermStatus.OPEN);
				return query.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Isuses> findAfterTerms(final Integer lotteryId,final Integer count,final String currentTerm) {
		// TODO Auto-generated method stub
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
					String hql = " from Isuses as model where model.lotteryId = :lotteryId and model.name >=:currentTerm";
					Query query = session.createQuery(hql);
					query.setParameter("lotteryId", lotteryId);
					query.setParameter("currentTerm", currentTerm);
					query.setFirstResult(0);
					query.setMaxResults(count);
				return query.list();
			}
		
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Isuses> findCurrentTermList(final List<Integer> list) {
		// TODO Auto-generated method stub
		
		return (List)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer("from Isuses as model where model.currentIsuses=1 ");
				
				// 标志变量
				Boolean b = true;
				String ids = null;
				if(list.size()>0){
					ids = list.toString().replaceAll("\\[|\\]", "");
				}else {
					b=false;
				}
				if(b){
					
					sbf.append(" and model.lotteryId in (:ids)");
				}
				
//				ids = ids.substring(0, ids.length()-2);
				String sql = "select * from T_Isuses isu where isu.CurrentIsuses=1 and isu.LotteryID in ("+ids+");";
				Query query = session.createSQLQuery(sql).addEntity(Isuses.class);
				
				return query.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Isuses> findCurrentTermList2() {
		// TODO Auto-generated method stub
		
		
		
		return (List)this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "from Isuses as model where model.lotteryId in (:ids)";
				List<Integer>ids = new ArrayList<Integer>();
				ids.add(72);
				ids.add(73);
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				return query.list();
			}
			
		});
	}

}
