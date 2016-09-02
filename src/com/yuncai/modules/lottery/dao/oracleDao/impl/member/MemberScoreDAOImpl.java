package com.yuncai.modules.lottery.dao.oracleDao.impl.member;



import java.sql.SQLException;
import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberScoreDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;


public class MemberScoreDAOImpl extends GenericDaoImpl<MemberScore, Integer> implements MemberScoreDAO {
	private static final Log log = LogFactory.getLog(MemberScoreDAOImpl.class);

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cailele.lottery.dao.impl.member.MemberScoreDAO#attachClean(com.cailele.lottery.entity.member.MemberScore)
	 */
	public void attachClean(MemberScore instance) {
		log.debug("attaching clean MemberScore instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public MemberScore findByMemberId(final Integer memberId){
		return (MemberScore) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hsql = new StringBuffer();
				hsql.append("from MemberScore as model where model.memberId = :memberId");
				Query query = session.createQuery(hsql.toString());
				query.setParameter("memberId", memberId);
				query.setMaxResults(1);
				return query.list().get(0);
			}
		});
	}
	
	/**
	 * 按照会员ID找到相应的积分账号
	 */
	@SuppressWarnings("unchecked")
	public MemberScore findByIdForUpdate(final Integer id) {
		getHibernateTemplate().flush();
		getHibernateTemplate().evict(this.find(MemberScore.class, id));
		return (MemberScore) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from MemberScore as model where model.memberId = :memberId";
				Query query = session.createQuery(hql);
				query.setLockMode("model", LockMode.UPGRADE);
				query.setParameter("memberId", id);
				List list = query.list();
				if (list != null && list.size() != 0) {
					return list.get(0);
				}
				return null;
			}
		});
	}
}
