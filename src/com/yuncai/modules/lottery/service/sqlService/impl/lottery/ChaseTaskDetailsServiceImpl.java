package com.yuncai.modules.lottery.service.sqlService.impl.lottery;

import java.util.List;


import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;

import com.yuncai.modules.lottery.dao.sqlDao.lottery.ChaseTaskDetailsDAO;
import com.yuncai.modules.lottery.dao.sqlDao.lottery.ChaseTasksDAO;
import com.yuncai.modules.lottery.dao.sqlDao.user.UsersDAO;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChaseTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Sql.ChaseTaskDetails;
import com.yuncai.modules.lottery.model.Sql.ChaseTasks;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.model.Sql.vo.ChaseTasksBean;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTaskDetailsService;


public class ChaseTaskDetailsServiceImpl extends BaseServiceImpl<ChaseTaskDetails, Integer> implements ChaseTaskDetailsService{
	private ChaseTaskDetailsDAO sqlChaseTaskDetailsDAO;
	private ChaseTasksDAO sqlChaseTasksDAO;
	private UsersDAO sqlUsersDAO;
	private MemberService memberService;

	public void setSqlChaseTaskDetailsDAO(ChaseTaskDetailsDAO sqlChaseTaskDetailsDAO) {
		this.sqlChaseTaskDetailsDAO = sqlChaseTaskDetailsDAO;
	}

	@Override
	public ChaseTasksBean findBuyPlanNO(String planNo) {
		
		return this.sqlChaseTaskDetailsDAO.findBuyPlanNO(planNo);
	}

	@Override
	public List<ChaseTasksBean> findByChaseNoTermList(Integer chaseNo) {
		
		return this.sqlChaseTaskDetailsDAO.findByChaseNoTermList(chaseNo);
	}

	@Override
	public List<ChaseTasksBean> findByTermList(Integer lotteryId, String term) {
		
		return this.sqlChaseTaskDetailsDAO.findByTermList(lotteryId, term);
	}

	@Override
	public ChaseTaskDetails findByIdForUpdate(Integer id) {
	
		return this.sqlChaseTaskDetailsDAO.findByIdForUpdate(id);
	}
	
	

	public void setSqlChaseTasksDAO(ChaseTasksDAO sqlChaseTasksDAO) {
		this.sqlChaseTasksDAO = sqlChaseTasksDAO;
	}

	public void setSqlUsersDAO(UsersDAO sqlUsersDAO) {
		this.sqlUsersDAO = sqlUsersDAO;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public List<ChaseTaskDetails> findByChaseTaskIDForDeList(Integer id) {
		// TODO Auto-generated method stub
		return this.sqlChaseTaskDetailsDAO.findByChaseTaskIDForDeList(id);
	}

	@Override
	public List<Object[]> findByChaseTaskIDForDeList2(Integer id) {
		// TODO Auto-generated method stub
		return this.sqlChaseTaskDetailsDAO.findByChaseTaskIDForDeList2(id);
	}


	

}
