package com.yuncai.modules.lottery.dao.oracleDao.impl.member;



import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.EmailNoticeTypeDAO;
import com.yuncai.modules.lottery.model.Oracle.EmailNoticeType;

/**
 * TODO
 * @author gx
 * Dec 24, 2013 4:19:38 PM
 */
public class EmailNoticeTypeDAOImpl extends GenericDaoImpl<EmailNoticeType,Integer> implements EmailNoticeTypeDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailNoticeType> findAll() {
		return (List<EmailNoticeType>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery("from EmailNoticeType order by value");
				return query.list();
			}
		});
	}
}
