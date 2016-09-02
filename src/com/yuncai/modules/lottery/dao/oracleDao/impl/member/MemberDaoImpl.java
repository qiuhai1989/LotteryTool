package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yuncai.modules.lottery.action.member.MemberSearch;
import com.yuncai.modules.lottery.bean.vo.MemberInfoShowBean;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberDao;
import com.yuncai.modules.lottery.model.Oracle.Member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;


public class MemberDaoImpl extends GenericDaoImpl<Member, Integer> implements MemberDao {
	
	private static final Log log = LogFactory.getLog(MemberDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<Member> findBySearch(final MemberSearch memberSearch, final int offset, final int length) {
		return (List<Member>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				
				if((memberSearch.getAccount() == null || "".equals(memberSearch.getAccount()))
						&&(memberSearch.getName() == null || "".equals(memberSearch.getName()))
						&&(memberSearch.getCertNo() == null || "".equals(memberSearch.getCertNo()))
						&&(memberSearch.getEmail() == null || "".equals(memberSearch.getEmail()))
						&&(memberSearch.getMobile() == null || "".equals(memberSearch.getMobile()))		
				){
					//如果查询信息输入为空，则不显示查询结果
					return new ArrayList();
				}				
				
				String hql = "select model from Member as model where 1=1 ";
				if (memberSearch.getAccount() != null && !"".equals(memberSearch.getAccount())) {
					hql += " and model.account like '" + memberSearch.getAccount() + "' ";
				}
				if (memberSearch.getName() != null && !"".equals(memberSearch.getName())) {
					hql += " and model.name like '%" + memberSearch.getName().trim() + "%' ";
				}
				if (memberSearch.getCertNo() != null && !"".equals(memberSearch.getCertNo())) {
					hql += " and model.certNo like '%" + memberSearch.getCertNo().trim() + "%' ";
				}
				if (memberSearch.getEmail() != null && !"".equals(memberSearch.getEmail())) {
					hql += " and model.email like '%" + memberSearch.getEmail().trim() + "%' ";
				}
				if (memberSearch.getMobile() != null && !"".equals(memberSearch.getMobile())) {
					hql += " and model.mobile like '%" + memberSearch.getMobile().trim() + "%' ";
				}
				if (memberSearch.getStartDate() != null && !"".equals(memberSearch.getStartDate())) {
					hql += " and model.registerDateTime >= :startDate";
				}
				if (memberSearch.getEndDate() != null && !"".equals(memberSearch.getEndDate())) {
					hql += " and model.registerDateTime <= :endDate";
				}
				if (memberSearch.getProvider() != null && !"".equals(memberSearch.getProvider())) {
					hql += " and model.provider = '" + memberSearch.getProvider() + "'";
				}

				hql += " order by model.id desc";				
				
				//com.cailele.lottery.tools.LogUtil.out(hql);
				Query query = session.createQuery(hql);

				if (memberSearch.getStartDate() != null && !"".equals(memberSearch.getStartDate())) {
					query.setParameter("startDate", memberSearch.getStartDate1());
				}
				if (memberSearch.getEndDate() != null && !"".equals(memberSearch.getEndDate())) {
					query.setParameter("endDate", memberSearch.getEndDate1());
				}

				query.setFirstResult(offset);
				query.setMaxResults(length);
				return query.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	public int getCountBySearch(final MemberSearch memberSearch) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select count(model.id) from Member as model where 1=1 ";
				if (memberSearch.getAccount() != null && !"".equals(memberSearch.getAccount())) {
					hql += " and model.account like '%" + memberSearch.getAccount() + "%' ";
				}
				if (memberSearch.getName() != null && !"".equals(memberSearch.getName())) {
					hql += " and model.name like '%" + memberSearch.getName() + "%' ";
				}
				if (memberSearch.getCertNo() != null && !"".equals(memberSearch.getCertNo())) {
					hql += " and model.certNo like '%" + memberSearch.getCertNo() + "%' ";
				}
				if (memberSearch.getEmail() != null && !"".equals(memberSearch.getEmail())) {
					hql += " and model.email like '%" + memberSearch.getEmail() + "%' ";
				}
				if (memberSearch.getMobile() != null && !"".equals(memberSearch.getMobile())) {
					hql += " and model.mobile like '%" + memberSearch.getMobile() + "%' ";
				}

				if (memberSearch.getStartDate() != null && !"".equals(memberSearch.getStartDate())) {
					hql += " and model.registerDateTime >= :startDate";
				}
				if (memberSearch.getEndDate() != null && !"".equals(memberSearch.getEndDate())) {
					hql += " and model.registerDateTime <= :endDate";
				}
				if (memberSearch.getProvider() != null && !"".equals(memberSearch.getProvider())) {
					hql += " and model.provider = '" + memberSearch.getProvider() + "'";
				}

				Query query = session.createQuery(hql);

				if (memberSearch.getStartDate() != null && !"".equals(memberSearch.getStartDate())) {
					query.setParameter("startDate", memberSearch.getStartDate1());
				}
				if (memberSearch.getEndDate() != null && !"".equals(memberSearch.getEndDate())) {
					query.setParameter("endDate", memberSearch.getEndDate1());
				}

				return ((Long) query.iterate().next()).intValue();
			}
		});
	}

	/**
	 * 更新最后一次登录时间
	 * 
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	public void updateLastLogin(final Integer id) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {

				String hql = "update Member m set m.lastLoginDateTime = SYSDATE where m.id = :id";
				Query query = session.createQuery(hql);
				query.setParameter("id", id);
				query.executeUpdate();
				return null;
			}
		});
	}
	
	public void saves(Member transientInstance) {
		log.debug("saving Member instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(Member transientInstance)
	{
		log.debug("updating Member instance");
		try{
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cailele.lottery.dao.impl.member.MemberDAO#attachClean(com.cailele.lottery.entity.member.Member)
	 */
	public void attachClean(Member instance) {
		log.debug("attaching clean Member instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public MemberInfoShowBean findMemberInfoShowBeanByName(final String account) {
		// TODO Auto-generated method stub
		return (MemberInfoShowBean)this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public MemberInfoShowBean doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select new com.yuncai.modules.lottery.bean.vo.MemberInfoShowBean(m1.account, m1.name, m1.certNo, m1.email, m1.mobile, m2.bankCard, m2.bankPart,m2.bank) from  Member as m1 ,MemberInfo as m2 where m1.id=m2.memberId and m1.account=:account";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				MemberInfoShowBean bean = (MemberInfoShowBean) query.uniqueResult();
				
				return bean;
			}
			
		});
	}

}
