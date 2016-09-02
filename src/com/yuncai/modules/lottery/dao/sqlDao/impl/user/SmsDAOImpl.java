package com.yuncai.modules.lottery.dao.sqlDao.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.user.SmsDAO;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Sql.Sms;

public class SmsDAOImpl extends GenericDaoImpl<Sms,Integer> implements SmsDAO{
	
	// 按用户名和日期查找已发送的短信
	@SuppressWarnings("unchecked")
	public List<Sms> findByUserAndDate(final String User,final Date startTime,final Date endTime){
		return (List<Sms>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {

				StringBuffer hsql = new StringBuffer();
				hsql.append("from Sms as model where 1=1 and model.isSent = 1 and model.from = :userName and model.dateTime > :startTime and model.dateTime < :endTime");
				hsql.append(" order by model.id");
				Query query = session.createQuery(hsql.toString());
				query.setParameter("userName", User);
				query.setParameter("startTime", startTime);
				query.setParameter("endTime", endTime);
				List<Sms> list = new ArrayList<Sms>();
				list = query.list();
				return list;
			}
		});
	}
}
