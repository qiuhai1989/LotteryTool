package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.math.BigDecimal;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberChargeLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.orm.hibernate3.HibernateCallback;

public class MemberChargeLineDAOImpl extends GenericDaoImpl<MemberChargeLine, String> implements MemberChargeLineDAO{
	private static final Log log = LogFactory.getLog(MemberChargeLineDAOImpl.class);
	public MemberChargeLine findByIdForUpdate(java.lang.String id) {
		try {
			getHibernateTemplate().flush();
			getHibernateTemplate().evict(this.find(MemberChargeLine.class, id));
			MemberChargeLine instance = (MemberChargeLine) getHibernateTemplate().get(MemberChargeLine.class, id,
					LockMode.UPGRADE);

			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveNumber(final MemberChargeLine transientInstance) {
		log.debug("saving MemberChargeLine instance");
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {

					Long l = ((BigDecimal) session.createSQLQuery("SELECT s_charge.nextval FROM dual").list().get(0)).longValue();
					String id = DateTools.getNowDateYYYYMMDD() + StringTools.getStringFillZero(String.valueOf(l), 8);
					transientInstance.setChargeNo(id);
					session.save(transientInstance);
					return null;
				}
			});

		} catch (RuntimeException re) {
			throw re;
		}
	}


}
