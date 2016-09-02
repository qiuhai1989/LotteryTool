package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import com.yuncai.modules.lottery.dao.oracleDao.member.FollowingPlanInfoDAO;
import com.yuncai.modules.lottery.model.Oracle.FollowingPlanInfo;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.FollowingPlanInfoService;

public class FollowingPlanInfoServiceImpl extends BaseServiceImpl<FollowingPlanInfo, Integer> implements FollowingPlanInfoService{

	@SuppressWarnings("unused")
	private FollowingPlanInfoDAO followingPlanInfoDAO;

	public void setFollowingPlanInfoDAO(FollowingPlanInfoDAO followingPlanInfoDAO) {
		this.followingPlanInfoDAO = followingPlanInfoDAO;
	}
}
