package com.yuncai.modules.lottery.service.sqlService.impl.lottery;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.lottery.ChaseTasksDAO;
import com.yuncai.modules.lottery.model.Sql.ChaseTasks;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTasksService;

public class ChaseTasksServiceImpl extends BaseServiceImpl<ChaseTasks, Integer> implements ChaseTasksService{
	
	private ChaseTasksDAO sqlChaseTasksDAO;

	public void setSqlChaseTasksDAO(ChaseTasksDAO sqlChaseTasksDAO) {
		this.sqlChaseTasksDAO = sqlChaseTasksDAO;
	}

	@Override
	public ChaseTasks findByIdForUpdate(Integer id) {
		
		return this.sqlChaseTasksDAO.findByIdForUpdate(id);
	}


	@Override
	public List<ChaseTasks> findRecordForAccount(Integer userId, Integer lotteryId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return this.sqlChaseTasksDAO.findRecordForAccount(userId, lotteryId, startDate, endDate);
	}

	@Override
	public List<ChaseTasks> findRecordForList(Integer userId, Integer lotteryId, Date startDate, Date endDate, Integer taskId, Integer order,
			Integer pageSize, Short status) {
		// TODO Auto-generated method stub
		
		return this.sqlChaseTasksDAO.findRecordForList(userId, lotteryId, startDate, endDate, taskId, order, pageSize, status);
	}


}
