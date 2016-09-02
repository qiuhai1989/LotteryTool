package com.yuncai.modules.lottery.dao.sqlDao.impl.user;

import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.user.ChannelDAO;

import com.yuncai.modules.lottery.model.Sql.Channel;


public class ChannelDAOImpl extends  GenericDaoImpl<Channel,Integer> implements ChannelDAO {
	private static final Log log = LogFactory.getLog(ChannelDAOImpl.class);
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> findByPlatformId(final int platformId){
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql ="select model.* from T_Channel as model where model.ISHIDE=0";
				if(platformId > 0){
					sql += " and model.platformId = "+platformId;
				}
				sql +=" order by model.real_Name collate Chinese_PRC_CS_AS_KS_WS";
				Query query = session.createSQLQuery(sql).addEntity(Channel.class);


				return query.list();
			}
		});
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> findByIds(final String string) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql ="select model.* from T_Channel as model where model.name in "+string;
				Query query = session.createSQLQuery(sql).addEntity(Channel.class);


				return query.list();
			}
		});
	}
	
}
