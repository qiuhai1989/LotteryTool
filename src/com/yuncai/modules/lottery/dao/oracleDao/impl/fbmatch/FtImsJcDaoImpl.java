package com.yuncai.modules.lottery.dao.oracleDao.impl.fbmatch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtImsJcDao;
import com.yuncai.modules.lottery.model.Oracle.FtImsJc;

public class FtImsJcDaoImpl extends GenericDaoImpl<FtImsJc, Integer> implements FtImsJcDao{

	private String getSql(){
		return "select m.id match_id,m.mid,m.league_Id,m.mb_Id,m.tg_Id,m.league_Name," +
		"m.league_Color,m.mb_Name,m.tg_Name,m.start_time,m.match_no,m.int_time," +
		"m.line_id,m.rang_ball_num,j.id jid,j.round,j.sim_date,j.status,j.mb_rank," +
		"j.mb_yellowcard,j.mb_redcard,j.mb_inball,j.tg_inball,j.tg_rank,j.tg_yellowcard," +
		"j.tg_redcard,j.hinball,j.ytype,j.ymr,j.ypk,j.ygr,s.homewinaward,s.guestwinaward," +
		"s.drawaward,s.nrhomewinaward,s.nrguestwinaward,s.nrdrawaward,i.win_avg_rate,i.draw_avg_rate," +
		"i.lost_avg_rate,j.status jstatus from t_ft_ims_jc j,t_ft_match m,t_sporttery_football_spfaward s," +
		"t_sporttery_football_betinfo i where m.id=j.match_id and m.mid=s.mid and s.passmode=1" +
		" and m.mid=i.mid";
	}
	
	@Override
	public List<Object[]> findFtImsJc(final String expect) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select m.id match_id,m.mid,m.league_Id,m.mb_Id,m.tg_Id,m.league_Name," +
						"m.league_Color,m.mb_Name,m.tg_Name,m.start_time,m.match_no,m.int_time," +
						"m.line_id,m.rang_ball_num,j.id jid,j.round,j.sim_date,j.status,j.mb_rank," +
						"j.mb_yellowcard,j.mb_redcard,j.mb_inball,j.tg_inball,j.tg_rank,j.tg_yellowcard," +
						"j.tg_redcard,j.hinball,j.ytype,j.ymr,j.ypk,j.ygr,s.homewinaward,s.guestwinaward," +
						"s.drawaward,s.nrhomewinaward,s.nrguestwinaward,s.nrdrawaward,i.win_avg_rate,i.draw_avg_rate," +
						"i.lost_avg_rate,j.status jstatus from t_ft_ims_jc j,t_ft_match m,t_sporttery_football_spfaward s," +
						"t_sporttery_football_betinfo i where m.id=j.match_id and m.mid=s.mid and s.passmode=1" +
						" and m.mid=i.mid and m.match_date=? order by int_time,line_id";
				return session.createSQLQuery(sql).setString(0, expect).list();
			}
		});
	}

	@Override
	public List<Object[]> findFtIms(final String expect) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql=getSql()+" and to_char(m.start_time,'yyyy-MM-dd')=? order by int_time,line_id";
				return session.createSQLQuery(sql).setString(0, expect).list();
			}
		});
	}
	
	@Override
	public List<Object[]> findFtIms10(final String startDate,final String endDate) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql=getSql()+" and j.sim_date>=? and j.sim_date<=? order by int_time,line_id";
				return session.createSQLQuery(sql).setString(0, startDate).setString(1, endDate).list();
			}
		});
	}

	@Override
	public List<Object[]> findFtImsByIds(final String jids) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql=getSql()+" and j.id in("+jids.trim()+") order by int_time,line_id";
				return  session.createSQLQuery(sql).list();
			}
		});
	}

	@Override
	public List<Map<String, Integer>> findMidAndJid(final String mids) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Integer>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Map<String, Integer>> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select m.mid,j.id from t_ft_ims_jc j,t_ft_match m where m.id=j.match_id and m.mid in("+mids+")";
				List<Object[]> list=session.createSQLQuery(sql).list();
				List<Map<String, Integer>> mid_jids=new ArrayList<Map<String,Integer>>();
				for (Object[] o : list) {
					Map<String, Integer> map=new HashMap<String, Integer>();
					map.put("mid", Integer.valueOf(o[0].toString()));
					map.put("jid", Integer.valueOf(o[1].toString()));
					mid_jids.add(map);
				}
				return  mid_jids;
			}
		});
	}
	
	/**
	 * 
	 */
	@Override
	public List<Map<String, Object>> findRank(final String mids) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select m.mid,j.id,j.mb_rank,j.tg_rank from t_ft_ims_jc j,t_ft_match m where m.id=j.match_id and m.mid in("+mids+")";
				List<Object[]> list=session.createSQLQuery(sql).list();
				List<Map<String, Object>> mid_jids=new ArrayList<Map<String,Object>>();
				for (Object[] o : list) {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("mid", o[0]);
					map.put("jid", o[1]);
					map.put("mb_rank", o[2]);
					map.put("tg_rank", o[3]);
					mid_jids.add(map);
				}
				return  mid_jids;
			}
		});
	}

	@Override
	public String findMatchJcResult(final Integer sqlId) {
		// TODO Auto-generated method stub
		
		
		return super.getHibernateTemplate().execute(new HibernateCallback<String>(){
			@SuppressWarnings("unchecked")
			@Override
			public String doInHibernate(Session session) throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = " select j.mb_inball,j.tg_inball from  t_ft_ims_jc j,t_ft_match m where m.id=j.match_id and j.status in(4,10) and m.sql_id="+sqlId;
				List<Object[]>list = session.createSQLQuery(sql).list();
				
				String result = null;
				Object[]obj=(list.size()>0)?list.get(0):null;
				if(obj!=null&&obj[0]!=null&&obj[1]!=null){
					result=obj[0].toString()+":"+obj[1].toString();
				}
				return result;
			}
			
		});
	}
}
