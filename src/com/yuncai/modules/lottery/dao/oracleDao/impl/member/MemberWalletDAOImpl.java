package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberWalletDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.util.*;
public class MemberWalletDAOImpl extends GenericDaoImpl<MemberWallet, Integer> implements MemberWalletDAO{
	private static final Log log = LogFactory.getLog(MemberWalletDAOImpl.class);

	public void attachClean(MemberWallet instance) {
		log.debug("attaching clean MemberWallet instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public MemberWallet findByMemberIdAndType(final Integer memberId, final WalletType walletType) {
		return (MemberWallet) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hsql = new StringBuffer();
				hsql.append("from MemberWallet as model where model.memberId = :memberId and model.walletType = :walletType");
				Query query = session.createQuery(hsql.toString());
				query.setParameter("memberId", memberId);
				query.setParameter("walletType", walletType);
				query.setMaxResults(1);
				return query.list().get(0);
			}
		});
	}

	/**
	 * 按照会员ID和钱包类型找到相应的钱包帐号
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MemberWallet findByMemberIdAndWalletTypeForUpdate(final Integer memberId, final WalletType walletType) {
		getHibernateTemplate().evict(this.findByMemberIdAndType(memberId,walletType));
		return (MemberWallet) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from MemberWallet as model where model.memberId = :memberId and model.walletType = :walletType";
				Query query = session.createQuery(hql);
				query.setLockMode("model", LockMode.UPGRADE);
				query.setParameter("memberId", memberId);
				query.setParameter("walletType", walletType);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cailele.lottery.dao.impl.member.MemberWalletDAO#save(com.cailele.lottery.entity.member.MemberWallet)
	 */
	public void saves(MemberWallet transientInstance) {
		log.debug("saving MemberWallet instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(MemberWallet transientInstance)
	{
		log.debug("updating MemberWallet instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

}
