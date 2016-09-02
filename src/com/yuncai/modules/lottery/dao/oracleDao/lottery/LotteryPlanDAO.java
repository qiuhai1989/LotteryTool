package com.yuncai.modules.lottery.dao.oracleDao.lottery;

import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanSearch;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;


import java.util.*;

public interface LotteryPlanDAO extends GenericDao<LotteryPlan, Integer> {
	
	public List findBySearch(final PlanSearch search, final int offset, final int length);

	public int getCountBySearch(final PlanSearch search);

	// 统计方案金额总计
	public List getSumBySearch(final PlanSearch search);
	
	/**
	 * 根据方案彩种、出票状态、期数找出方案
	 * 
	 * @param lotteryType
	 * @param PlanTicketStatus
	 * @param terms
	 * @return
	 */
	public List findByLotteryTypeAndTermsAndPlanTicketStatus(LotteryType lotteryType, PlanTicketStatus planTicketStatus, String term);


	/**
	 * 找到没有出票的方案
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public List findUnPrintPlanByLotteryTypeAndTerm(LotteryType lotteryType, String term);

	
	/**
	 * 找到未成功的合买方案
	 * 
	 * @param lotteryType
	 * @param planType
	 * @param term
	 * @return
	 */
	public List findHmPlanForFailure(LotteryType lotteryType,PlanType planType ,SelectType  st, String term);

	/**
	 * 根据方案彩种、状态找出方案
	 * 
	 * @param lotteryType
	 * @param planStatus
	 * @param terms
	 * @return
	 */
	
	
	public List findByLotteryTypeAndPlanStatus(LotteryType lotteryType, PlanStatus planStatus);

	/**
	 * 根据方案彩种、可出票状态找出方案
	 * 
	 * @param lotteryType
	 * @param isAbleTicketing
	 * @return
	 */
	public List findByIsAbleTicketingAndPlanTickctStatus(LotteryType lotteryType, int isAbleTicketing, PlanTicketStatus planTicketStatus);

	/**
	 * 根据方案彩种、可出票状态查找方案
	 * 
	 * @param lotteryType
	 * @param planTicketStatus
	 * @param terms
	 * @return
	 */
	public List findByLotteryTypeAndPlanTicketStatus(LotteryType lotteryType, PlanTicketStatus planTicketStatus);

	/**
	 * 按彩种和期数找出已经出票的方案
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public List findPrintedPlan(LotteryType lotteryType, String term);

	/**
	 * 按彩种和期数找出失败的方案
	 * 
	 * @param lotteryType
	 * @param term
	 * @return
	 */
	public List findFailurePlan(LotteryType lotteryType, String term);
	

	/**
	 * 查找已经出票&未派奖的定单
	 * 
	 * @param planNo
	 * @return
	 */
	public List<LotteryPlan> findByTicketOutAndNotReturnPrized(final LotteryType lotteryType, final String term);
	/**
	 * 查找已经出票&未开奖的定单
	 * 
	 * @param planNo
	 * @return
	 */
	public List<LotteryPlan> findByTicketOutAndNotOpenPrized(final LotteryType lotteryType, final String term) ;
	/**
	 * 查找已经出票
	 * 
	 * @param planNo
	 * @return
	 */
	public List<LotteryPlan> findByTicketOut(final LotteryType lotteryType, final String term);
	/**
	 * 根据定单编号查询定单详细内容
	 * 
	 * @param planNo
	 * @return
	 */
	public LotteryPlan findLotteryPlanByPlanNoForUpdate(final Integer planNo);
	
	public LotteryPlan findByIdForUpdate(java.lang.Integer id);

	
	
	/**根据彩票种类查找合买方案列表
	 * @param lotteryType 彩种类
	 * @param readName 发起人用户名
	 * @param offset 页数
	 * @param pageSize 每页记录数
	 *  @param planStatus 方案状态
	 *  @param order  0降序 1升序
	 *   @param key 0方案进度1方案金额
	 * @return
	 */
	public List<LotteryPlan> findLotteryPlans(LotteryType lotteryType,String readName,int offset,int pageSize,PlanStatus planStatus,PlanType planType,int order,int key);

	
	
	/**
	 * 获取每期认购的最大份数;
	 */
	public Integer findBuyTermMaxCount(final LotteryType lotteryType,final String term,final String account);


	
	/**查找合买或代购在售方案
	 * @param lotteryType
	 * @param readName
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<LotteryPlan>findLotteryPlans(LotteryType lotteryType,String readName,int offset,int pageSize,PlanType planType);
	

	
	/**查找合买在售列表
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @param planType
	 * @param listPlanNo
	 * @param planNo
	 * @param order
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public List<HmShowBean> findHmShowBeans(String username,Date startDate,Date endDate,PlanType planType,List<Integer>listPlanNo,int planNo,int order,int offset,int pageSize );
	
	/**查找指定状态 方案列表
	 * @param username
	 * @param startDate
	 * @param endDate
	 * @param planType
	 * @param listPlanNo
	 * @param planNo
	 * @param order
	 * @param offset
	 * @param pageSJize
	 * @param planStatus
	 * @return
	 */
	public List<HmShowBean> findHmShowBeans(String username,Date startDate,Date endDate,PlanType planType,List<Integer>listPlanNo,int planNo,int order,int offset,int pageSJize ,PlanStatus planStatus);
	
	
	
	/**查找中奖最大方案
	 * @param lotteryType
	 * @param pretaxPrize
	 * @param beginTime
	 * @param endTime
	 * @param winStatus
	 * @param account
	 * @return
	 */
	public LotteryPlan findMaxWinPrizePlan(LotteryType lotteryType,Integer pretaxPrize,Date beginTime,Date endTime,WinStatus winStatus,String account);

	/**查询中奖率
	 * @param lotteryList  彩种列表
	 * @param bedinTime  开始时间
	 * @param endTime  截止时间
	 * @return
	 */
	public Object[] winPercent(List<LotteryType> lotteryList,Date bedinTime,Date endTime);
	
	/**获取轻松购彩某一类型当天购买人数
	 * @param easyType
	 * @return
	 */
	public Integer getEasyBuyCountByEasyType(Integer easyType);
}
