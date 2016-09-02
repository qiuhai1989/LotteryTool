package com.yuncai.modules.lottery.service.sqlService.lottery;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.ChaseTaskDetails;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.vo.ChaseTasksBean;
import com.yuncai.modules.lottery.service.BaseService;

public interface ChaseTaskDetailsService extends BaseService<ChaseTaskDetails, Integer>{
	
	/**
	 * 根据方案号找出追号任务
	 * @param planNo
	 * @return
	 */
	public ChaseTasksBean findBuyPlanNO(String planNo);
	/**
	 * 根据彩种ID、期数找出追号任务集合
	 * @param lotteryId
	 * @param term
	 * @return
	 */
	public List<ChaseTasksBean> findByTermList(Integer lotteryId,String term);
	/**
	 * 根据追号ID找出详情
	 * @param chaseNo
	 * @return
	 */
	public List<ChaseTasksBean> findByChaseNoTermList(Integer chaseNo);
	
	/**
	 * 根据ID找到方案 防重
	 * @param id
	 * @return
	 */
	public ChaseTaskDetails findByIdForUpdate(Integer id);
	
	public List<ChaseTaskDetails> findByChaseTaskIDForDeList(Integer id);
	
	public List<Object[]> findByChaseTaskIDForDeList2(Integer id);


}
