package com.yuncai.modules.lottery.dao.sqlDao.impl.user;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.user.UsersDAO;
import com.yuncai.modules.lottery.model.Sql.SensitiveKeyWords;
import com.yuncai.modules.lottery.model.Sql.Users;

public class UsersDAOImpl extends GenericDaoImpl<Users, Integer> implements UsersDAO{

	@SuppressWarnings("unchecked")
	@Override
	public Users findUsersByAccount(final String account){
		return (Users) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql="select * from Users  model where model.name=:name";
				Query query=session.createSQLQuery(hql).addEntity(Users.class);
				query.setParameter("name", account);
				List list=query.list();
				return list.get(0);
			}
		});
	}

	@Override
	public Integer findUserByAuthority(final String account, final String password, final int competenceId1, final int competenceId2, final int groupId1, final int groupId2) {
		return super.getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select * from T_Users where ID in(select id from T_Users u,T_CompetencesOfUsers c where u.ID=c.UserID and c.CompetenceID in (?,?) and u.Name=? and u.Password=?) or ID in(select id from T_Users u,T_UserInGroups g where u.ID=g.UserID and g.GroupID in (?,?) and u.Name=? and u.Password=?)";
				SQLQuery query=session.createSQLQuery(sql);
				query.setInteger(0, competenceId1);
				query.setInteger(1, competenceId2);
				query.setString(2, account);
				query.setString(3, password);
				query.setInteger(4, groupId1);
				query.setInteger(5, groupId2);
				query.setString(6, account);
				query.setString(7, password);
				List list=query.list();
				/*return list.size()>0 ? (Users)list.get(0) : null;*/
				return list.size();
			}
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public Integer findMaxId() {
		// TODO Auto-generated method stub
		
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = " select max(model.id) from Users as model";
				Query query = session.createQuery(hql);
				
				return query.uniqueResult();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Users findbyId(final Integer id) {
		return (Users) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql="from Users as model where model.id=:id";
				Query query=session.createQuery(hql);
				query.setParameter("id", id);
				List list=query.list();
				return list.get(0);
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SensitiveKeyWords> findSensitiveKeyWords() {
		// TODO Auto-generated method stub
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public List<SensitiveKeyWords> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = session.createSQLQuery("SELECT * FROM [T_Sensitivekeywords]").addEntity(SensitiveKeyWords.class);
				return query.list();
			}
			
		});
	}
}
