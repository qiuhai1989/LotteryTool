package com.yuncai.modules.lottery.dao.sqlDao.impl.lottery;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.buyDetailesDAO;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Sql.BuyDetails;

public class buyDetailesDAOImpl extends GenericDaoImpl<BuyDetails, Integer> implements buyDetailesDAO{
	@Override
	public List findSuccessByPlanNo(final Integer planNo) {
	
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer hsql = new StringBuffer();
				hsql.append("from BuyDetails  model where 1=1  and model.schemeId = :id and model.quashStatus = :quashStatus");
				Query query = session.createQuery(hsql.toString());
				query.setParameter("id", planNo);
				query.setParameter("quashStatus", (short)0);
				List list = new ArrayList();
				list = query.list();
				return list;

			}
		});
	}
}
