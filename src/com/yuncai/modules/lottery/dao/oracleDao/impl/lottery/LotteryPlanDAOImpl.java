package com.yuncai.modules.lottery.dao.oracleDao.impl.lottery;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.lottery.LotteryPlanDAO;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanSearch;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;

public class LotteryPlanDAOImpl extends GenericDaoImpl<LotteryPlan, Integer> implements LotteryPlanDAO {

	private static final Log log = LogFactory.getLog(LotteryPlanDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List findBySearch(final PlanSearch search, final int offset, final int length) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from LotteryPlan as model where 1=1 ";

				if (search.getAccount() != null && !"".equals(search.getAccount())) {
					hql += " and model.account = :account";
				}
				if (search.getTerm() != null && !"".equals(search.getTerm())) {
					hql += " and model.term = :term";
				}
				if (search.getPlanNo() != 0) {
					hql += " and model.planNo = :planNo";
				}
				if (search.getLotteryType() != -1) {
					hql += " and model.lotteryType = :lotteryType";
				}
				if (search.getPlanStatus() != -1) {
					hql += " and model.planStatus = :planStatus";
				}
				if (search.getWinStatus() != -1) {
					hql += " and model.winStatus = :winStatus";
				}
				if (search.getPlayType() != -1) {
					hql += " and model.playType = :playType";
				}
				if (search.getAmount() != -1) {
					if (search.getAmount() == 1) {
						hql += " and model.amount < :amount";
					} else if (search.getAmount() == 2 || search.getAmount() == 3 || search.getAmount() == 4 || search.getAmount() == 5) {
						hql += " and model.amount >= :amountBegin and model.amount <= :amountEnd";
					} else if (search.getAmount() == 6) {
						hql += " and model.amount > :amount";
					}
				}
				if (search.getPosttaxPrize() != -1) {
					if (search.getPosttaxPrize() == 1 || search.getPosttaxPrize() == 4) {
						hql += " and model.posttaxPrize < :posttaxPrize";
					} else if (search.getPosttaxPrize() == 2 || search.getPosttaxPrize() == 3) {
						hql += " and model.posttaxPrize > :posttaxPrize";
					} else if (search.getPosttaxPrize() == 5 || search.getPosttaxPrize() == 6) {
						hql += " and model.posttaxPrize >= :posttaxPrizeBegin and model.posttaxPrize <= :posttaxPrizeEnd";
					}
				}
				if (search.getIsRealBuy() != -1) {
					if (search.getIsRealBuy() == 1) {
						hql += " and model.account not in (" + search.getRealAccount() + ")";
					} else if (search.getIsRealBuy() == 2) {
						hql += " and model.account in (" + search.getRealAccount() + ")";
					}
				}
				if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
					hql += " and model.createDateTime >= :startDate";
				}
				if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
					hql += " and model.createDateTime <= :endDate";
				}
				if (search.getGenTypeSJ() != -1) {
					hql += " and model.genType = :genTypesj";
				}
				if (search.getPlanType() != -1) {
					hql += " and model.planType = :planType";
				}
				hql += " order by model.planNo desc ";

				//LogUtil.out(hql);
				Query query = session.createQuery(hql);

				if (search.getAccount() != null && !"".equals(search.getAccount())) {
					query.setParameter("account", search.getAccount());
				}
				if (search.getTerm() != null && !"".equals(search.getTerm())) {
					query.setParameter("term", search.getTerm());
				}
				if (search.getPlanNo() != 0) {
					query.setParameter("planNo", search.getPlanNo());
				}
				if (search.getLotteryType() != -1) {
					query.setParameter("lotteryType", LotteryType.getItem(search.getLotteryType()));
				}
				if (search.getPlanStatus() != -1) {
					query.setParameter("planStatus", PlanStatus.getItem(search.getPlanStatus()));
				}
				if (search.getWinStatus() != -1) {
					query.setParameter("winStatus", WinStatus.getItem(search.getWinStatus()));
				}
				if (search.getPlayType() != -1) {
					query.setParameter("playType", PlayType.getItem(search.getPlayType()));
				}
				if (search.getAmount() != -1) {
					if (search.getAmount() == 1) {
						query.setParameter("amount", 100);
					} else if (search.getAmount() == 2) {
						query.setParameter("amountBegin", 100);
						query.setParameter("amountEnd", 500);
					} else if (search.getAmount() == 3) {
						query.setParameter("amountBegin", 501);
						query.setParameter("amountEnd", 1000);
					} else if (search.getAmount() == 4) {
						query.setParameter("amountBegin", 1001);
						query.setParameter("amountEnd", 5000);
					} else if (search.getAmount() == 5) {
						query.setParameter("amountBegin", 5001);
						query.setParameter("amountEnd", 10000);
					} else if (search.getAmount() == 6) {
						query.setParameter("amount", 10001);
					}
				}
				if (search.getPosttaxPrize() != -1) {
					if (search.getPosttaxPrize() == 1 || search.getPosttaxPrize() == 2) {
						query.setParameter("posttaxPrize", Double.valueOf(10000));
					} else if (search.getPosttaxPrize() == 3) {
						query.setParameter("posttaxPrize", Double.valueOf(50000));
					} else if (search.getPosttaxPrize() == 4) {
						query.setParameter("posttaxPrize", Double.valueOf(1000));
					} else if (search.getPosttaxPrize() == 5) {
						query.setParameter("posttaxPrizeBegin", Double.valueOf(1000));
						query.setParameter("posttaxPrizeEnd", Double.valueOf(5000));
					} else if (search.getPosttaxPrize() == 6) {
						query.setParameter("posttaxPrizeBegin", Double.valueOf(5001));
						query.setParameter("posttaxPrizeEnd", Double.valueOf(9999));
					}
				}
				if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
					query.setParameter("startDate", search.getStartDate1());
				}
				if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
					query.setParameter("endDate", search.getEndDate1());
				}
				if (search.getGenTypeSJ() != -1) {
					query.setParameter("genTypesj", GenType.getItem(search.getGenTypeSJ()));
				}
				if (search.getPlanType() != -1) {
					query.setParameter("planType", PlanType.getItem(search.getPlanType()));
				}
				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int getCountBySearch(final PlanSearch search) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select count(model.planNo) from LotteryPlan as model where 1=1 ";

				if (search.getAccount() != null && !"".equals(search.getAccount())) {
					hql += " and model.account = :account";
				}
				if (search.getTerm() != null && !"".equals(search.getTerm())) {
					hql += " and model.term = :term";
				}
				if (search.getPlanNo() != 0) {
					hql += " and model.planNo = :planNo";
				}
				if (search.getLotteryType() != -1) {
					hql += " and model.lotteryType = :lotteryType";
				}
				if (search.getPlanStatus() != -1) {
					hql += " and model.planStatus = :planStatus";
				}
				if (search.getWinStatus() != -1) {
					hql += " and model.winStatus = :winStatus";
				}
				if (search.getPlayType() != -1) {
					hql += " and model.playType = :playType";
				}
				if (search.getAmount() != -1) {
					if (search.getAmount() == 1) {
						hql += " and model.amount < :amount";
					} else if (search.getAmount() == 2 || search.getAmount() == 3 || search.getAmount() == 4 || search.getAmount() == 5) {
						hql += " and model.amount >= :amountBegin and model.amount <= :amountEnd";
					} else if (search.getAmount() == 6) {
						hql += " and model.amount > :amount";
					}
				}
				if (search.getPosttaxPrize() != -1) {
					if (search.getPosttaxPrize() == 1 || search.getPosttaxPrize() == 4) {
						hql += " and model.posttaxPrize < :posttaxPrize";
					} else if (search.getPosttaxPrize() == 2 || search.getPosttaxPrize() == 3) {
						hql += " and model.posttaxPrize > :posttaxPrize";
					} else if (search.getPosttaxPrize() == 5 || search.getPosttaxPrize() == 6) {
						hql += " and model.posttaxPrize >= :posttaxPrizeBegin and model.posttaxPrize <= :posttaxPrizeEnd";
					}
				}
				if (search.getIsRealBuy() != -1) {
					if (search.getIsRealBuy() == 1) {
						hql += " and model.account not in (" + search.getRealAccount() + ")";
					} else if (search.getIsRealBuy() == 2) {
						hql += " and model.account in (" + search.getRealAccount() + ")";
					}
				}
				if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
					hql += " and model.createDateTime >= :startDate";
				}
				if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
					hql += " and model.createDateTime <= :endDate";
				}
				if (search.getGenTypeSJ() != -1) {
					hql += " and model.genType = :genTypesj";
				}
				if (search.getPlanType() != -1) {
					hql += " and model.planType = :planType";
				}

				//LogUtil.out(hql);
				Query query = session.createQuery(hql);

				if (search.getAccount() != null && !"".equals(search.getAccount())) {
					query.setParameter("account", search.getAccount());
				}
				if (search.getTerm() != null && !"".equals(search.getTerm())) {
					query.setParameter("term", search.getTerm());
				}
				if (search.getPlanNo() != 0) {
					query.setParameter("planNo", search.getPlanNo());
				}
				if (search.getLotteryType() != -1) {
					query.setParameter("lotteryType", LotteryType.getItem(search.getLotteryType()));
				}
				if (search.getPlanStatus() != -1) {
					query.setParameter("planStatus", PlanStatus.getItem(search.getPlanStatus()));
				}
				if (search.getWinStatus() != -1) {
					query.setParameter("winStatus", WinStatus.getItem(search.getWinStatus()));
				}
				if (search.getPlayType() != -1) {
					query.setParameter("playType", PlayType.getItem(search.getPlayType()));
				}
				if (search.getAmount() != -1) {
					if (search.getAmount() == 1) {
						query.setParameter("amount", 100);
					} else if (search.getAmount() == 2) {
						query.setParameter("amountBegin", 100);
						query.setParameter("amountEnd", 500);
					} else if (search.getAmount() == 3) {
						query.setParameter("amountBegin", 501);
						query.setParameter("amountEnd", 1000);
					} else if (search.getAmount() == 4) {
						query.setParameter("amountBegin", 1001);
						query.setParameter("amountEnd", 5000);
					} else if (search.getAmount() == 5) {
						query.setParameter("amountBegin", 5001);
						query.setParameter("amountEnd", 10000);
					} else if (search.getAmount() == 6) {
						query.setParameter("amount", 10001);
					}
				}
				if (search.getPosttaxPrize() != -1) {
					if (search.getPosttaxPrize() == 1 || search.getPosttaxPrize() == 2) {
						query.setParameter("posttaxPrize", Double.valueOf(10000));
					} else if (search.getPosttaxPrize() == 3) {
						query.setParameter("posttaxPrize", Double.valueOf(50000));
					} else if (search.getPosttaxPrize() == 4) {
						query.setParameter("posttaxPrize", Double.valueOf(1000));
					} else if (search.getPosttaxPrize() == 5) {
						query.setParameter("posttaxPrizeBegin", Double.valueOf(1000));
						query.setParameter("posttaxPrizeEnd", Double.valueOf(5000));
					} else if (search.getPosttaxPrize() == 6) {
						query.setParameter("posttaxPrizeBegin", Double.valueOf(5001));
						query.setParameter("posttaxPrizeEnd", Double.valueOf(9999));
					}
				}
				if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
					query.setParameter("startDate", search.getStartDate1());
				}
				if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
					query.setParameter("endDate", search.getEndDate1());
				}
				if (search.getGenTypeSJ() != -1) {
					query.setParameter("genTypesj", GenType.getItem(search.getGenTypeSJ()));
				}
				if (search.getPlanType() != -1) {
					query.setParameter("planType", PlanType.getItem(search.getPlanType()));
				}
				return ((Long) query.iterate().next()).intValue();
			}
		});
	}

	// 统计方案金额总计
	@SuppressWarnings("unchecked")
	public List getSumBySearch(final PlanSearch search) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select sum(model.amount),sum(model.posttaxPrize) from LotteryPlan as model where 1=1 ";

				if (search.getAccount() != null && !"".equals(search.getAccount())) {
					hql += " and model.account = :account";
				}
				if (search.getTerm() != null && !"".equals(search.getTerm())) {
					hql += " and model.term = :term";
				}
				if (search.getPlanNo() != 0) {
					hql += " and model.planNo = :planNo";
				}
				if (search.getLotteryType() != -1) {
					hql += " and model.lotteryType = :lotteryType";
				}
				if (search.getPlanStatus() != -1) {
					hql += " and model.planStatus = :planStatus";
				}
				if (search.getWinStatus() != -1) {
					hql += " and model.winStatus = :winStatus";
				}
				if (search.getPlayType() != -1) {
					hql += " and model.playType = :playType";
				}
				if (search.getAmount() != -1) {
					if (search.getAmount() == 1) {
						hql += " and model.amount < :amount";
					} else if (search.getAmount() == 2 || search.getAmount() == 3 || search.getAmount() == 4 || search.getAmount() == 5) {
						hql += " and model.amount >= :amountBegin and model.amount <= :amountEnd";
					} else if (search.getAmount() == 6) {
						hql += " and model.amount > :amount";
					}
				}
				if (search.getPosttaxPrize() != -1) {
					if (search.getPosttaxPrize() == 1 || search.getPosttaxPrize() == 4) {
						hql += " and model.posttaxPrize < :posttaxPrize";
					} else if (search.getPosttaxPrize() == 2 || search.getPosttaxPrize() == 3) {
						hql += " and model.posttaxPrize > :posttaxPrize";
					} else if (search.getPosttaxPrize() == 5 || search.getPosttaxPrize() == 6) {
						hql += " and model.posttaxPrize >= :posttaxPrizeBegin and model.posttaxPrize <= :posttaxPrizeEnd";
					}
				}
				if (search.getIsRealBuy() != -1) {
					if (search.getIsRealBuy() == 1) {
						hql += " and model.account not in (" + search.getRealAccount() + ")";
					} else if (search.getIsRealBuy() == 2) {
						hql += " and model.account in (" + search.getRealAccount() + ")";
					}
				}
				if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
					hql += " and model.createDateTime >= :startDate";
				}
				if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
					hql += " and model.createDateTime <= :endDate";
				}
				if (search.getGenTypeSJ() != -1) {
					hql += " and model.genType = :genTypesj";
				}
				if (search.getPlanType() != -1) {
					hql += " and model.planType = :planType";
				}
				//LogUtil.out(hql);
				Query query = session.createQuery(hql);

				if (search.getAccount() != null && !"".equals(search.getAccount())) {
					query.setParameter("account", search.getAccount());
				}
				if (search.getTerm() != null && !"".equals(search.getTerm())) {
					query.setParameter("term", search.getTerm());
				}
				if (search.getPlanNo() != 0) {
					query.setParameter("planNo", search.getPlanNo());
				}
				if (search.getLotteryType() != -1) {
					query.setParameter("lotteryType", LotteryType.getItem(search.getLotteryType()));
				}
				if (search.getPlanStatus() != -1) {
					query.setParameter("planStatus", PlanStatus.getItem(search.getPlanStatus()));
				}
				if (search.getWinStatus() != -1) {
					query.setParameter("winStatus", WinStatus.getItem(search.getWinStatus()));
				}
				if (search.getPlayType() != -1) {
					query.setParameter("playType", PlayType.getItem(search.getPlayType()));
				}
				if (search.getAmount() != -1) {
					if (search.getAmount() == 1) {
						query.setParameter("amount", 100);
					} else if (search.getAmount() == 2) {
						query.setParameter("amountBegin", 100);
						query.setParameter("amountEnd", 500);
					} else if (search.getAmount() == 3) {
						query.setParameter("amountBegin", 501);
						query.setParameter("amountEnd", 1000);
					} else if (search.getAmount() == 4) {
						query.setParameter("amountBegin", 1001);
						query.setParameter("amountEnd", 5000);
					} else if (search.getAmount() == 5) {
						query.setParameter("amountBegin", 5001);
						query.setParameter("amountEnd", 10000);
					} else if (search.getAmount() == 6) {
						query.setParameter("amount", 10001);
					}
				}
				if (search.getPosttaxPrize() != -1) {
					if (search.getPosttaxPrize() == 1 || search.getPosttaxPrize() == 2) {
						query.setParameter("posttaxPrize", Double.valueOf(10000));
					} else if (search.getPosttaxPrize() == 3) {
						query.setParameter("posttaxPrize", Double.valueOf(50000));
					} else if (search.getPosttaxPrize() == 4) {
						query.setParameter("posttaxPrize", Double.valueOf(1000));
					} else if (search.getPosttaxPrize() == 5) {
						query.setParameter("posttaxPrizeBegin", Double.valueOf(1000));
						query.setParameter("posttaxPrizeEnd", Double.valueOf(5000));
					} else if (search.getPosttaxPrize() == 6) {
						query.setParameter("posttaxPrizeBegin", Double.valueOf(5001));
						query.setParameter("posttaxPrizeEnd", Double.valueOf(9999));
					}
				}
				if (search.getStartDate() != null && !"".equals(search.getStartDate())) {
					query.setParameter("startDate", search.getStartDate1());
				}
				if (search.getEndDate() != null && !"".equals(search.getEndDate())) {
					query.setParameter("endDate", search.getEndDate1());
				}
				if (search.getGenTypeSJ() != -1) {
					query.setParameter("genTypesj", GenType.getItem(search.getGenTypeSJ()));
				}
				if (search.getPlanType() != -1) {
					query.setParameter("planType", PlanType.getItem(search.getPlanType()));
				}
				return query.list();
			}
		});
	}

	/**
	 * 找到没有出票的方案
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public List findUnPrintPlanByLotteryTypeAndTerm(LotteryType lotteryType, String term) {
		log.debug("findUnPrintPlanByLotteryTypeAndTerm instance by lotteryType : " + lotteryType + ", and term: " + term);
		try {
			String queryString = "from LotteryPlan as model where model.lotteryType = ? and model.term = ? and model.planTicketStatus = "
					+ PlanTicketStatus.TICKETING.getValue();
			return getHibernateTemplate().find(queryString, new Object[] { lotteryType, term });
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

	/**
	 * 根据方案彩种, 可出票状态找到方案
	 * 
	 * @param lotteryType
	 * @param planStatus
	 * @param terms
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByIsAbleTicketingAndPlanTickctStatus(LotteryType lotteryType, int isAbleTicketing, PlanTicketStatus planTicketStatus) {
		log.debug("finding LotteryPlan instance by lotteryType : " + lotteryType + ", and isAbleTicketing: " + isAbleTicketing);
		try {
			String queryString = "from LotteryPlan as model where model.lotteryType = ? and model.isAbleTicketing = ? and model.planTicketStatus = ? and model.isUploadContent = ?";
			return getHibernateTemplate().find(queryString, new Object[] { lotteryType, isAbleTicketing, planTicketStatus, CommonStatus.YES.getValue() });
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

	/**
	 * 根据方案彩种、状态找出方案
	 * 
	 * @param lotteryType
	 * @param planStatus
	 * @param terms
	 * @return
	 */
	public List findByLotteryTypeAndPlanStatus(LotteryType lotteryType, PlanStatus planStatus) {
		log.debug("finding LotteryPlan instance by lotteryType : " + lotteryType + ", and planStatus: " + planStatus);
		try {
			String queryString = "from LotteryPlan as model where model.lotteryType = ? and model.planStatus = ? and model.isUploadContent = ?";
			return getHibernateTemplate().find(queryString, new Object[] { lotteryType, planStatus, CommonStatus.YES.getValue() });
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

	/**
	 * 根据方案彩种、可出票状态查找方案
	 * 
	 * @param lotteryType
	 * @param planTicketStatus
	 * @param terms
	 * @return
	 */
	public List findByLotteryTypeAndPlanTicketStatus(LotteryType lotteryType, PlanTicketStatus planTicketStatus) {
		log.debug("finding LotteryPlan instance by lotteryType : " + lotteryType + ", and planTicketStatus: " + planTicketStatus);
		try {
			String queryString = "from LotteryPlan as model where model.lotteryType = ? and model.planTicketStatus = ? and model.isUploadContent = ?";
			return getHibernateTemplate().find(queryString, new Object[] { lotteryType, planTicketStatus, CommonStatus.YES.getValue() });
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

	/**
	 * 查找已经出票
	 * 
	 * @param planNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryPlan> findByTicketOut(final LotteryType lotteryType, final String term) {
		return (List<LotteryPlan>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from LotteryPlan as model where model.lotteryType = :lotteryType and model.term = :term and model.planStatus = 4";
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					hql += " and model.createDateTime >= :ct";
				}
				Query query = session.createQuery(hql);
				query.setParameter("lotteryType", lotteryType);
				query.setParameter("term", term);
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -5);
					query.setParameter("ct", cal.getTime());
				}
				List list = query.list();
				return list;
			}
		});
	}

	/**
	 * 查找已经出票&没开奖的定单
	 * 
	 * @param planNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryPlan> findByTicketOutAndNotOpenPrized(final LotteryType lotteryType, final String term) {
		return (List<LotteryPlan>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from LotteryPlan as model where model.lotteryType = :lotteryType and model.term = :term and model.planStatus = 4 "
						+ "and model.winStatus = 1";
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					hql += " and model.printTicketDateTime >= :ct";
				}
				Query query = session.createQuery(hql);
				query.setParameter("lotteryType", lotteryType);
				query.setParameter("term", term);
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -30);
					//LogUtil.out("时间:" + DateTools.dateToString(cal.getTime(), "yyyy-MM-dd"));
					query.setParameter("ct", cal.getTime());
				}
				List list = query.list();
				return list;
			}
		});
	}

	/**
	 * 查找已经出票&没派奖的定单
	 * 
	 * @param planNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryPlan> findByTicketOutAndNotReturnPrized(final LotteryType lotteryType, final String term) {
		return (List<LotteryPlan>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from LotteryPlan as model where model.lotteryType = :lotteryType and model.term = :term and model.planStatus = 4 and model.winStatus <> 4";
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					hql += " and model.printTicketDateTime >= :ct";
				}
				Query query = session.createQuery(hql);
				query.setParameter("lotteryType", lotteryType);
				query.setParameter("term", term);
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -15);
					query.setParameter("ct", cal.getTime());
				}
				List list = query.list();
				return list;
			}
		});
	}

	/**
	 * 按彩种和期数找出失败的方案 部分出票不算失败方案
	 * 
	 * @param lotteryType
	 * @param terms
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findFailurePlan(final LotteryType lotteryType, final String term) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from LotteryPlan as model where model.lotteryType = :lotteryType and model.term = :term "
						+ " and (model.planStatus = 6 or model.planStatus = 9) and (model.winStatus = " + WinStatus.NOT_RESULT.getValue()
						+ " or (model.winStatus = " + WinStatus.AWARD.getValue() + " and pretaxPrize = 0))";
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					hql += " and model.createDateTime >= :ct";
				}
				Query query = session.createQuery(hql);
				query.setParameter("lotteryType", lotteryType);
				query.setParameter("term", term);
				if (LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -5);
					query.setParameter("ct", cal.getTime());
				}
				List list = query.list();
				return list;
			}
		});
	}

	/**
	 * 找到招募中的合买方案
	 * 
	 * @param lotteryType
	 * @param planType
	 * @param term
	 * @return
	 */
	public List findHmPlanForFailure(LotteryType lotteryType, PlanType planType, SelectType st, String term) {
		try {
			String queryString = "from LotteryPlan as model where model.planStatus = " + PlanStatus.RECRUITING.getValue() + " and model.term = '"
					+ term + "'";
			if (lotteryType.getValue() > -1)
				queryString += " and model.lotteryType = " + lotteryType.getValue();
			if (planType.getValue() > -1)
				queryString += " and model.planType = " + planType.getValue();
			if (st.getValue() > -1)
				queryString += "  and model.selectType = " + st.getValue();

			// LogUtil.out("hql:------------------"+queryString.toString());
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

	/**
	 * 根据定单编号查询定单详细内容
	 * 
	 * @param planNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LotteryPlan findLotteryPlanByPlanNoForUpdate(final Integer planNo) {
		getHibernateTemplate().flush();
		getHibernateTemplate().evict(this.find(LotteryPlan.class, planNo));
		return (LotteryPlan) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from LotteryPlan as model where model.planNo = :planNo";
				Query query = session.createQuery(hql);
				query.setLockMode("model", LockMode.UPGRADE);
				query.setParameter("planNo", planNo);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	/**
	 * 按彩种和期数找出已经出票的方案
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public List findPrintedPlan(LotteryType lotteryType, String term) {
		return super.getHibernateTemplate().find("from LotteryPlan where lotteryType = ? and term = ? and (planStatus = 4 or planStatus = 8)",
				new Object[] { lotteryType, term });
	}

	public LotteryPlan findByIdForUpdate(java.lang.Integer id) {
		log.debug("getting LotteryPlan instance with id: " + id);
		try {
			getHibernateTemplate().flush();
			getHibernateTemplate().evict(this.find(LotteryPlan.class, id));
			LotteryPlan instance = (LotteryPlan) getHibernateTemplate().get(LotteryPlan.class, id, LockMode.UPGRADE);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * 根据方案彩种、出票状态、期数找出方案
	 * 
	 * @param lotteryType
	 * @param PlanTicketStatus
	 * @param terms
	 * @return
	 */
	public List findByLotteryTypeAndTermsAndPlanTicketStatus(LotteryType lotteryType, PlanTicketStatus planTicketStatus, String term) {

		log.debug("finding LotteryPlan instance by lotteryType : " + lotteryType + ", and planTicketStatus: " + planTicketStatus);
		try {
			String queryString = "from LotteryPlan as model where model.lotteryType = ? and model.planTicketStatus = ? and model.term = ? ";
			return getHibernateTemplate().find(queryString, new Object[] { lotteryType, planTicketStatus, term });
		} catch (RuntimeException re) {
			log.error("find  failed", re);
			throw re;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<LotteryPlan> findLotteryPlans(final LotteryType lotteryType, final String readName, final int offset, final int pageSize,
			final PlanStatus planStatus, final PlanType planType,final int order,final int key) {
		return (List)getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer hql2 = new StringBuffer("select model,model.soldPart*1.0/model.part,model2.nickName,model2.card from LotteryPlan as model,Member as model2 where model.account = model2.account ");
				//加上截止时间判断超过处理截止时间不予显示
				hql2.append(" and model.dealDateTime > sysdate  ");
				
				
				if (lotteryType!=null&&lotteryType.getValue() != 0 && lotteryType.getValue() > 0&&(lotteryType.getValue()!=72&&lotteryType.getValue()!=73)) {
					hql2.append("and model.lotteryType=:lotteryType ");
				}else if(lotteryType!=null&&lotteryType.getValue() != 0 && lotteryType.getValue() > 0&&(lotteryType.getValue()==72||lotteryType.getValue()==73)){
					hql2.append("and model.lotteryType in (:alist) ");
				}
				if (readName != null && !"".equals(readName)) {
					hql2.append("and model.nickName like :account ");
				}
				if (planStatus != null) {
					hql2.append("and model.planStatus=:planStatus ");
				}
				if (planType != null) {
					hql2.append("and model.planType=:planType ");
				}
				if(key==0){
					hql2.append(" order by (model.soldPart*1.0/model.part ) ");
				}
				if(key==1){
					hql2.append(" order by model.amount ");
				}				
				if(order==0){
					hql2.append(" desc ");
				}
				if(order==1){
					
				}
				
//				LogUtil.out(hql2.toString());
				Query query = session.createQuery(hql2.toString());
				
				if (lotteryType!=null&&lotteryType.getValue() != 0 && lotteryType.getValue() > 0&&(lotteryType.getValue()!=72&&lotteryType.getValue()!=73)) {
					query.setParameter("lotteryType", lotteryType);
				}else if(lotteryType!=null&&lotteryType.getValue() != 0 && lotteryType.getValue() > 0&&(lotteryType.getValue()==72||lotteryType.getValue()==73)){
					
					if(lotteryType.getValue()==72){
						query.setParameterList("alist", LotteryType.JCZQList);
					}else{
						query.setParameterList("alist", LotteryType.JCLQList);
					}
					
				}				
				
				if (readName != null && !"".equals(readName)) {
					query.setParameter("account", "%"+readName+"%");
				}
				if (planStatus != null) {
					query.setParameter("planStatus", planStatus);
				}else{
					//如果为空默认显示招募中的方案
					query.setParameter("planStatus", PlanStatus.RECRUITING);
				}
				if (planType != null) {
					query.setParameter("planType", planType);
				}
				
				query.setFirstResult((offset - 1)*pageSize);
				query.setMaxResults(pageSize);
				List<Object[]>list = query.list();
				Iterator<Object[]> iterator = 
					list.iterator();
				List list2 = new ArrayList();
				//修改plan 昵称来源
				while(iterator.hasNext()){
					Object[] objs = iterator.next();
//					LogUtil.out(objs[2]);
					list2.add(objs[0]);
					LotteryPlan l = ((LotteryPlan)objs[0]);
					l.setNickName((String) objs[2]);
					l.setCard( (objs[3]==null||objs[3].equals(""))?1: (Integer)objs[3]);
				}
				return list2;
			}

		});
	}

	@Override
	public List<LotteryPlan> findLotteryPlans(final LotteryType lotteryType, final String readName, final int offset, final int pageSize,
			PlanType planType) {

		return this.findLotteryPlans(lotteryType, readName, offset, pageSize, PlanStatus.getItem(2), planType,1,0);
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
		return (List) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer(
						"select new com.yuncai.modules.lottery.bean.vo.HmShowBean(p.account, p.lotteryType, p.playType, p.term, p.part,p.planNo, p.planStatus, o.orderNo, p.amount, p.content, p.winStatus, o.status, p.posttaxPrize, p.createDateTime, p.dealDateTime, o.buyType, p.isUploadContent, p.selectType, p.publicStatus, p.soldPart, p.reservePart, 0, p.planTicketStatus, p.planType, o.followingType)from LotteryPlanOrder o , LotteryPlan p where o.planNo = p.planNo ");
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

	/**
	 * 获取每期认购的最大份数;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer findBuyTermMaxCount(final LotteryType lotteryType,final String term,final String account) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select count(model.planNo) from LotteryPlan as model where 1=1 ";
				if(lotteryType.getValue()>0){
					hql +=" and model.lotteryType = :lotteryType ";
				}
				if(term!=null && !"".equals(term)){
					if(LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)){
						hql +=" and model.createDateTime >= :startTime ";
					}else{
						hql +=" and model.term = :term ";
					}
				}
				if(account!=null && !"".equals(account)){
					hql +=" and model.account = :account ";
				}
				
				Query query = session.createQuery(hql);
				if(lotteryType.getValue()>0){
					query.setParameter("lotteryType", lotteryType);
				}
				
				if(term!=null && !"".equals(term)){
					if(LotteryType.JCZQList.contains(lotteryType) || LotteryType.JCLQList.contains(lotteryType)){
						String DateStr=DateTools.dateToString(new Date(), "yyyy-MM-dd");
						query.setParameter("startTime",DateTools.StringToDate(DateStr, "yyyy-MM-dd"));
						//query.setParameter("endTime",DateTools.StringToDate(DateStr, "yyyy-MM-dd"));
					}else{
						query.setParameter("term", term);
					}
				}
				
				if(account!=null && !"".equals(account)){
					query.setParameter("account", account);
				}
				
				return ((Long) query.iterate().next()).intValue();
			}
		});
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public LotteryPlan findMaxWinPrizePlan(final LotteryType lotteryType,final Integer pretaxPrize,final Date beginTime,final Date endTime,final WinStatus winStatus,
			final	String account) {
		// TODO Auto-generated method stub
		
		return (LotteryPlan)getHibernateTemplate().execute(new HibernateCallback() {

			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer("from LotteryPlan as model where 1=1 ");
				if(lotteryType!=null&&lotteryType.getValue()!=-1){
					sbf.append(" and model.lotteryType = :lotteryType");
				}
				if(pretaxPrize!=null&&pretaxPrize>0){
					sbf.append(" and model.pretaxPrize >= :pretaxPrize");
				}
				if(winStatus!=null && winStatus.getValue()!= -1){
					sbf.append(" and model.winStatus = :winStatus");
				}
				if(account!=null){
					sbf.append(" and model.account = :account");
				}
				//LogUtil.out(beginTime);
				//LogUtil.out(endTime);
				//model.openResultTime 有空值导致运算结果为空
				sbf.append(" and model.openResultTime between  :beginTime and :endTime order by model.pretaxPrize desc");
				//LogUtil.out(sbf.toString());
				Query query = session.createQuery(sbf.toString());
				
				if(lotteryType!=null&&lotteryType.getValue()!=-1){
					query.setParameter("lotteryType", lotteryType);
				}
				if(pretaxPrize!=null&&pretaxPrize>0){
					query.setParameter("pretaxPrize", pretaxPrize);
				}
				if(winStatus!=null && winStatus.getValue()!= -1){
					query.setParameter("winStatus", winStatus);
				}
				if(account!=null){
					query.setParameter("account", account);
				}				
				query.setDate("beginTime", beginTime);
				query.setDate("endTime", endTime);
				
				return query.list().get(0);
			}
		});
	}
	/**
	 * 查询中奖率
	 * 
	 * @param lotteryList
	 *            彩种列表
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            截止时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] winPercent(final List<LotteryType> lotteryList, final Date beginTime, final Date endTime) {
		return (Object[]) this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer sbf = new StringBuffer(
						"select sum(model.amount) as totalAmount,sum(model.pretaxPrize) as totalPrize,sum(model.pretaxPrize)/sum(model.amount) as winPercent from LotteryPlan as model where 1=1 ");
				if (lotteryList != null && !lotteryList.isEmpty()) {
					sbf.append(" and model.lotteryType in (");
					int i =0;
					for (LotteryType lotteryType : lotteryList) {
						if(i==0){
							sbf.append(lotteryType.getValue());
						}else{
							sbf.append(","+lotteryType.getValue());
						}
						i+=1;
					}
					sbf.append(")");
				}	
				sbf.append(" and model.planStatus = "+ PlanStatus.TICKET_OUT.getValue() +" and model.planTicketStatus = "+ PlanTicketStatus.TICKET_FINISH.getValue() +" and model.winStatus in ("+ WinStatus.NOT_AWARD.getValue() +","+ WinStatus.AWARD.getValue() +","+ WinStatus.SEND_AWARD.getValue() +")");
				sbf.append(" and (model.openResultTime between  :beginTime and :endTime) ");
				Query query = session.createQuery(sbf.toString());
				query.setParameter("beginTime", beginTime);
				query.setParameter("endTime", endTime);
				//LogUtil.out(query.getQueryString());
				return query.iterate().next();
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public Integer getEasyBuyCountByEasyType (final Integer easyType) {
		// TODO Auto-generated method stub
		
		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql="select count(t.plan_no) from t_lottery_plan t where t.create_date_time " +
						"between trunc(sysdate)+1/86400  and trunc(sysdate)+1-1/86400 and t.easy_type=:easyType";
				Query query = session.createSQLQuery(sql);
				query.setParameter("easyType", easyType);
				return ((BigDecimal) query.uniqueResult()).intValue();
				//return ((Long)query.iterate().next()).intValue();
			}
			
			
		});
	}
}
