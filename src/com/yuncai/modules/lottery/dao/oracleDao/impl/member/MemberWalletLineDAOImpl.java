package com.yuncai.modules.lottery.dao.oracleDao.impl.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.member.WalletLineSearch;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberWalletLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;

public class MemberWalletLineDAOImpl extends GenericDaoImpl<MemberWalletLine, Integer> implements MemberWalletLineDAO {



	@Override
	@SuppressWarnings("unchecked")
	public Double countAmount(final String account,final Date startDate,final Date endDate,final List<WalletTransType> transTypes) {
		// TODO Auto-generated method stub
		return (Double)this.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = " select sum(model.amount) from MemberWalletLine  as model  where model.transType in (:transTypes)      and  model.account =:account and model.createDateTime  between :begin and :end ";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				query.setParameterList("transTypes", transTypes);
				query.setParameter("begin", startDate);
				query.setParameter("end", endDate);
				return query.uniqueResult();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MemberWalletLine> findByAccountAndDate(final String account,final Date begin,final Date end,final Integer order,final Integer pageSize,final Date date,
			final	List<WalletTransType> transTypes) {
		return (List)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer();
				sbf.append("from MemberWalletLine  as model where  model.account =:account  ");
				
				if(order==1&&date!=null){
				 sbf.append(" and model.createDateTime < :date");
				}
				
				if(transTypes!=null){
					sbf.append(" and model.transType in (:transTypes) ");
				}
				
				sbf.append(" and model.createDateTime  between :begin and :end order by model.walletLineNo desc");
				Query query = session.createQuery(sbf.toString());
				query.setParameter("account", account);
				if(order==1&&date!=null){
					 query.setParameter("date", date);
					}
				query.setParameter("begin", begin);
				query.setParameter("end", end);
				if(transTypes!=null){
					query.setParameterList("transTypes", transTypes);
				}
				query.setFirstResult(0);
				query.setMaxResults(pageSize);
				return query.list();
			}
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MemberWalletLine> findByAccountAndDate(final String account,final Date begin,final Date end,final Integer pageSize,
			final	List<WalletTransType> transTypes,final int pageNo) {
		return (List)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				StringBuffer sbf = new StringBuffer();
				sbf.append("from MemberWalletLine  as model where  model.account =:account  ");
//				
//				if(order==1&&date!=null){
//				 sbf.append(" and model.createDateTime < :date");
//				}
//				
				if(transTypes!=null){
					sbf.append(" and model.transType in (:transTypes) ");
				}else{
					//sbf.append("");
				//	sbf.append(" and (model.transType not in (:types) or (model.transType in (:types) and model.selectStatus=0)) ");
				}
				
				sbf.append(" and model.createDateTime  between :begin and :end order by model.walletLineNo desc");
				Query query = session.createQuery(sbf.toString());
				query.setParameter("account", account);
				query.setParameter("begin", begin);
				query.setParameter("end", end);
				if(transTypes!=null){
					query.setParameterList("transTypes", transTypes);
				}else {
//					List<WalletTransType>types = new ArrayList<WalletTransType>();
//					types.add(WalletTransType.getItem(31));
//					types.add(WalletTransType.getItem(32));
//					query.setParameterList("types", types);
				}
				query.setFirstResult((pageNo-1)*pageSize);
				query.setMaxResults(pageSize);
				return query.list();
			}
			
		});
	}

	/**
	 * 查询冻结方案
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findBySelectStatus(final WalletLineSearch search) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql="from MemberWalletLine m where 1=1 ";
				if (DBHelper.isNoNull(search)) {
					if (DBHelper.isNoNull(search.getPlanNo())) {
						hql +=" and m.planNo = "+search.getPlanNo();
					}
					if (DBHelper.isNoNull(search.getTransType())) {
						hql +=" and m.transType = "+search.getTransType();
					}
					if (DBHelper.isNoNull(search.getAccount())) {
						hql +=" and m.account = '"+search.getAccount()+"'";
					}
				}
				Query query = session.createQuery(hql);
				if(query.list().isEmpty()){
					return null;
				}else
					return query.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> listMoney(final String account,final Date startDate,final Date endDate) {
		// TODO Auto-generated method stub
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = " select model.amount ,model.transType,model.selectStatus from MemberWalletLine  as model  where model.transType in (:transTypes)      and  model.account =:account and model.createDateTime  between :begin and :end ";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				query.setParameter("begin", startDate);
				query.setParameter("end", endDate);
				List<WalletTransType>list = new ArrayList<WalletTransType>();
				list.addAll(WalletTransType.incomeList);
				list.addAll(WalletTransType.outTypeList);
				list.addAll(WalletTransType.prizeTypeList);
				query.setParameterList("transTypes", list);
				return query.list();
			}
			
		});
	}

}
