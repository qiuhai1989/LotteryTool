package com.yuncai.modules.lottery.service.sqlService.user;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.CustomFriendFollowSchemes;
import com.yuncai.modules.lottery.service.BaseService;

public interface CustomFriendFollowSchemesService extends BaseService<CustomFriendFollowSchemes, Integer>{

	//根据用户名查询出是否存在定制人员
	public List findUserBySchemes(String userName,int lotteryType);
}
