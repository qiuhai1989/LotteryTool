package com.yuncai.modules.lottery.dao.oracleDao.impl.card;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.card.CardDAO;
import com.yuncai.modules.lottery.model.Oracle.Card;
import com.yuncai.modules.lottery.model.Oracle.toolType.CardStatus;

public class CardDAOImpl extends GenericDaoImpl<Card, Integer> implements CardDAO{

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> cardPopulation(final Integer memberId,final CardStatus cardStatus) {
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select count(model.amount),model.amount   from Card as model where model.memberId =:memberId and model.status = :status  group by model.amount order by model.amount desc "  ;
				Query query = session.createQuery(hql);
				query.setParameter("memberId", memberId);
				query.setParameter("status", cardStatus);
				return query.list();
			}
			
		});
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> cardPopulationChannel(final String account) {
		// TODO Auto-generated method stub
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select count(amount) as cardCount,amount from Card as model where model.owner= :account and  model.isValid=1 group by model.amount order by model.amount "  ;
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				return query.list();
			}
			
		});
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> validCardList(final String account,final Double amount){
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select model.id,model.createDateTime,model.expireDateTime,2 as type, case when account is not null then 1 else 0 end as status from Card as model where (model.owner= :account and model.isValid=1) and model.amount=:amount  order by status,model.expireDateTime";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				query.setParameter("amount", amount);		
				return query.list();
			}
			
		});
		
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> personalCardMenu(final String account){
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select count(amount) as cardCount,amount from Card as model where (model.owner= :account and model.isValid=0) or model.account= :account group by model.amount order by model.amount "  ;
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				return query.list();
			}
			
		});
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> personalCardList(final String account,final Double amount){
		return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = "select model.id,model.createDateTime,model.expireDateTime,case when model.owner= :account then 0 when model.account= :account and (model.owner!= :account or model.owner is null) then 1 end as type,case when model.owner= :account and model.account is null then 0 when model.owner= :account and model.account is not null then 1 when  model.account= :account and (model.owner!= :account  or model.owner is null) and model.useDateTime is null then 0 when model.account= :account and (model.owner!= :account  or model.owner is null) and model.useDateTime is not null then 1 end as status from Card as model where ((model.owner= :account and model.isValid=0) or model.account= :account) and model.amount=:amount order by status,model.expireDateTime";
				Query query = session.createQuery(hql);
				query.setParameter("account", account);
				query.setParameter("amount", amount);
				//System.out.println(query.getQueryString());
				return query.list();
			}
			
		});
		
	}

		@Override
		@SuppressWarnings("unchecked")
		public List<Object[]> validCardMenu(final String account){
			return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					// TODO Auto-generated method stub
//					String hql = "select count(model.amount),model.amount   from Card as model where model.owner = :owner and model.isValid=1  group by model.amount order by model.createDateTime desc " ;
					String hql = "select count(model.amount),model.amount ,model.status  from Card as model where model.owner =:owner and model.isValid=1  group by model.amount ,model.status  "  ;
					//LogUtil.out(hql);
					Query query = session.createQuery(hql);
					query.setParameter("owner", account);

					return query.list();
				}
				
			});
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public List<Object[]> cardPopulation(final String owner) {
			return (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

				@Override
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					// TODO Auto-generated method stub
//					String hql = "select count(model.amount),model.amount   from Card as model where model.owner = :owner and model.isValid=1  group by model.amount order by model.createDateTime desc " ;
					String hql = "select count(model.amount),model.amount ,model.status  from Card as model where (model.owner =:owner or model.account =:owner ) and model.isValid=0  group by model.amount ,model.status  "  ;
					LogUtil.out(hql);
					Query query = session.createQuery(hql);
					query.setParameter("owner", owner);

					return query.list();
				}
				
			});
		}


}
