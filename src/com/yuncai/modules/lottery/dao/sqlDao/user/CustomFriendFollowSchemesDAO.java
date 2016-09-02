package com.yuncai.modules.lottery.dao.sqlDao.user;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.CustomFriendFollowSchemes;

public interface CustomFriendFollowSchemesDAO extends GenericDao<CustomFriendFollowSchemes, Integer>  {
	//根据用户名查询出是否存在定制人员
	 public List findUserBySchemes( final String userName,final int lotteryType);
}
