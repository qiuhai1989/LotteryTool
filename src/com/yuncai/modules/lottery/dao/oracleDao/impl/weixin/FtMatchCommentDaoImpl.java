package com.yuncai.modules.lottery.dao.oracleDao.impl.weixin;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.oracleDao.weixin.FtMatchCommentDao;
import com.yuncai.modules.lottery.model.Oracle.weixin.CommentBySearch;
import com.yuncai.modules.lottery.model.Oracle.weixin.FtMatchComment;
import com.yuncai.modules.lottery.model.Oracle.weixin.WeiXinNews;

public class FtMatchCommentDaoImpl extends GenericDaoImpl<FtMatchComment, Integer> implements FtMatchCommentDao{

	@Override
	public int deleteByNewsId(int newsId) {
		String hql="delete FtMatchComment fc where fc.newsId=?";
		return super.getHibernateTemplate().bulkUpdate(hql,newsId);
	}

	@Override
	public List<WeiXinNews> findComment(final int mid) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<WeiXinNews>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<WeiXinNews> doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql="select {w.*} from t_ft_match_comment fc,t_ft_match m,w_news w where w.id=fc.news_id and fc.mid=m.mid and m.mid=?";
				return session.createSQLQuery(sql).addEntity("w",WeiXinNews.class).setInteger(0, mid).list();
			}
		});
	}

	@Override
	public List<Object> checkHaveComment(final String mids) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<Object>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select distinct mid from T_FT_MATCH_COMMENT t where mid in("+mids+")";
				List<Object> list=session.createSQLQuery(sql).list();
				//List<Object> midList=new ArrayList<Object>();
				/*for (Object[] o : list) {
					midList.add(o[0]);
				}*/
				return  list;
			}
		});
	}

	@Override
	public List<CommentBySearch> findComment(final String mids) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<CommentBySearch>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<CommentBySearch> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql="select new com.yuncai.modules.lottery.model.Oracle.weixin.CommentBySearch(c.mid,n.content) from FtMatchComment c,WeiXinNews n where c.newsId=n.id and c.mid in("+mids+") order by mid asc";
				List<CommentBySearch> comments=session.createQuery(hql).list();
				return  comments;
			}
		});
	}

}
