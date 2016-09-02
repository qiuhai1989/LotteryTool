package com.yuncai.modules.lottery.dao.oracleDao.impl.system;

import java.util.List;

import com.yuncai.modules.lottery.action.system.SystemErrorSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.system.SystemErrorDAO;
import com.yuncai.modules.lottery.model.Oracle.SystemError;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorGrade;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorStatus;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class SystemErrorDAOImpl extends GenericDaoImpl<SystemError, Integer> implements SystemErrorDAO{

	private static final Log log = LogFactory.getLog(SystemErrorDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public Integer countBySearch(final SystemErrorSearch errorSearch){
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select count(model.id) from SystemError as model where 1=1 ";
				String hqlPost = "";
				Query query = genQuery(session, hql, hqlPost, errorSearch);
				return ((Long) query.iterate().next()).intValue();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<SystemError> findBySearch(final SystemErrorSearch errorSearch,final Integer offset,final Integer length){
		return (List<SystemError>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql = "select model from SystemError as model where 1=1 ";
				String hqlPost = " order by model.createDateTime desc";
				Query query = genQuery(session, hql, hqlPost, errorSearch);
				if(offset!=null&&length!=null){
					query.setFirstResult(offset);
					query.setMaxResults(length);
				}
				return query.list();
			}
		});
	}
	private final Query genQuery(Session session, String hql, String postHql, SystemErrorSearch search){
		if (search.getKeyWords() != null && !"".equals(search.getKeyWords())) {
			hql += " and model.keyWords = :keyWords";
		}
		if (search.getStatus() != null && search.getStatus().getValue() != ErrorStatus.ALL.getValue() ) {
			hql += " and model.status = :status";
		}
		if (search.getGrade() != null && search.getGrade().getValue() != ErrorGrade.ALL.getValue()) {
			hql += " and model.grade = :grade";
		}
		if (search.getCreateStartDate() != null) {
			hql += " and model.createDateTime > :createStartDate";
		}
		if (search.getCreateEndDate() != null) {
			hql += " and model.createDateTime < :createEndDate";
		}
		if(search.getType()!=null && search.getType().getValue()!= ErrorType.ALL.getValue()){
			hql += " and model.type = :errorType ";
		}
		if(search.getLotteryType()!=null && search.getLotteryType().getValue()!= LotteryType.NENO.getValue()){
			hql += " and model.lotteryType = :lotteryType ";
		}
		hql += postHql;
		//LogUtil.out(hql);
		Query query = session.createQuery(hql);
		if (search.getKeyWords() != null && !"".equals(search.getKeyWords())) {
			query.setParameter("keyWords", search.getKeyWords());
		}
		if (search.getStatus() != null && search.getStatus().getValue() != ErrorStatus.ALL.getValue()  ) {
			query.setParameter("status", search.getStatus());
		}
		if (search.getGrade() != null && search.getGrade().getValue() != ErrorGrade.ALL.getValue()) {
			query.setParameter("grade", search.getGrade());
		}
		if (search.getCreateStartDate() != null ) {
			query.setParameter("createStartDate", search.getCreateStartDate());
		}
		if (search.getCreateEndDate() != null ) {
			query.setParameter("createEndDate", search.getCreateEndDate());
		}
		if(search.getType()!=null && search.getType().getValue()!= ErrorType.ALL.getValue()){
			query.setParameter("errorType", search.getType());
		}
		if(search.getLotteryType()!=null && search.getLotteryType().getValue()!= LotteryType.NENO.getValue()){
			query.setParameter("lotteryType", search.getLotteryType());
		}
		return query;
	}

}
