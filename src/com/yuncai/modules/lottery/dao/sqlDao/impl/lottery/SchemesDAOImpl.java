package com.yuncai.modules.lottery.dao.sqlDao.impl.lottery;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.SchemesDAO;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Sql.BuyDetails;
import com.yuncai.modules.lottery.model.Sql.Schemes;

public class SchemesDAOImpl extends GenericDaoImpl<Schemes, Integer> implements SchemesDAO{

	/**
	 * 根据定单编号查询定单详细内容
	 * 
	 * @param planNo
	 * @return
	 */
	@Override
	public Schemes findSchemeByPlanNOForUpdate(final Integer planNo) {
		getHibernateTemplate().flush();
		getHibernateTemplate().evict(this.find(Schemes.class, planNo));
		return (Schemes)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "from Schemes as model where model.ycSchemeNumber = :planNo";
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

    

}
