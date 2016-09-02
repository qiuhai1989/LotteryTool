package com.yuncai.modules.lottery.dao.oracleDao.impl.worldcup;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.worldcup.WorldCupTeamDao;
import com.yuncai.modules.lottery.model.Oracle.WorldCupTeam;


public class WorldCupTeamDaoImpl extends GenericDaoImpl<WorldCupTeam, Integer> implements WorldCupTeamDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<WorldCupTeam> findTeams() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().executeFind(new HibernateCallback<List<WorldCupTeam>>(){

			@Override
			public List<WorldCupTeam> doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql ="select * from t_ft_champion a where a.competition_type=1 and a.sale_status<=1 order by  to_number(a.team_num)";
				Query query = session.createSQLQuery(sql).addEntity(WorldCupTeam.class);
				return query.list();
			}
			
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WorldCupTeam> findTeamsByIds(final  List<String> ids) {
		// TODO Auto-generated method stub
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String hql = " from WorldCupTeam wct where wct.teamID in (:ids)  order by wct.teamNum";
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				
				return query.list();
			}
			
		});
	}

}
