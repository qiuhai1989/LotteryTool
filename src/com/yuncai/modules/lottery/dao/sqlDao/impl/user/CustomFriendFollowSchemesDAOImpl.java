package com.yuncai.modules.lottery.dao.sqlDao.impl.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.yuncai.modules.lottery.dao.GenericDaoImpl;
import com.yuncai.modules.lottery.dao.sqlDao.user.CustomFriendFollowSchemesDAO;
import com.yuncai.modules.lottery.model.Sql.CustomFriendFollowSchemes;
import com.yuncai.modules.lottery.model.Sql.vo.CustomFriendBean;

import org.hibernate.Query;

public class CustomFriendFollowSchemesDAOImpl extends GenericDaoImpl<CustomFriendFollowSchemes, Integer> implements CustomFriendFollowSchemesDAO{

	//根据用户查询是否是红人，可以进行根单处理
	@SuppressWarnings("unchecked")
	public List findUserBySchemes(final String userName,final int lotteryType) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String hql="select u.ID,name,moduls.UserID,moduls.lotteryId,moduls.MoneyStart,moduls.BuyShareStart,moduls.BuyShareEnd ,moduls.MoneyEnd " +
						" from T_CustomFriendFollowSchemes as moduls,T_Users as u where u.ID=moduls.FollowUserID " +
						" and u.Name='"+userName+"' and (moduls.lotteryId="+lotteryType+" or  moduls.lotteryId = -1) " +
								" and  moduls.MoneyStart <= moduls.MoneyEnd AND moduls.BuyShareStart <= moduls.BuyShareEnd " +
								" AND moduls.MoneyStart >= 1 AND moduls.BuyShareStart >= 1 " +
								" order by moduls.DateTime";
				
				Query query=session.createSQLQuery(hql);
				List list = query.list();
				List redlist=new ArrayList();
				if(!list.isEmpty()){
					for(int i=0;i<list.size();i++){
						Object[] o=(Object[])list.get(i);
						CustomFriendBean bean=new CustomFriendBean();
						bean.setId(Integer.parseInt(o[0].toString()));
						bean.setUserName(o[1].toString());
						bean.setFollowUserID(Integer.parseInt(o[2].toString()));
						bean.setLotteryType(Integer.parseInt(o[3].toString()));
						bean.setMoneyStart(((BigDecimal)o[4]).doubleValue());
						bean.setBuyShareStart((Integer)o[5]);
						bean.setBuyShareEnd((Integer)o[6]);
						bean.setMoneyEnd(((BigDecimal)o[7]).doubleValue());
						redlist.add(bean);
						
					}
					return redlist;
				}else{
					return null;
				}
				
			}
		});
	}

}
