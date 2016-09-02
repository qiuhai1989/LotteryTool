package com.yuncai.modules.lottery.service.sqlService.impl.user;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.user.CustomFriendFollowSchemesDAO;
import com.yuncai.modules.lottery.model.Sql.CustomFriendFollowSchemes;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.user.CustomFriendFollowSchemesService;

public class CustomFriendFollowSchemesServiceImpl extends BaseServiceImpl<CustomFriendFollowSchemes, Integer> implements CustomFriendFollowSchemesService{

	@SuppressWarnings("unused")
	private CustomFriendFollowSchemesDAO sqlCustomFriendFollowSchemesDAO;

	public void setSqlCustomFriendFollowSchemesDAO(CustomFriendFollowSchemesDAO sqlCustomFriendFollowSchemesDAO) {
		this.sqlCustomFriendFollowSchemesDAO = sqlCustomFriendFollowSchemesDAO;
	}

	public List findUserBySchemes(final String userName,final int lotteryType) {
		
		return this.sqlCustomFriendFollowSchemesDAO.findUserBySchemes(userName,lotteryType);
	}

	
}
