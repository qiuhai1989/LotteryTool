package com.yuncai.modules.lottery.dao.sqlDao.lottery;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.ChaseTasks;

public interface ChaseTasksDAO extends GenericDao<ChaseTasks, Integer>{
	//防重
	public ChaseTasks findByIdForUpdate(Integer id);
	

	/**查找某个用户的追号记录 
	 * @param userId
	 * @param lotteryId
	 * @param startDate
	 * @param endDate
	 * @param taskId
	 * @param order
	 * @param pageSize
	 * @param status 方案状态
	 * @return
	 */
	public List<ChaseTasks> findRecordForList(Integer userId,Integer lotteryId,Date startDate,Date endDate,
			Integer taskId,Integer order,Integer pageSize,Short status);
	
	
	public List<ChaseTasks> findRecordForAccount(Integer userId,Integer lotteryId,Date startDate,Date endDate);
	
}
