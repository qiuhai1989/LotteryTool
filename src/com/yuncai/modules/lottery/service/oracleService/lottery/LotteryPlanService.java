package com.yuncai.modules.lottery.service.oracleService.lottery;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanSearch;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.service.BaseService;

public interface LotteryPlanService  extends BaseService<LotteryPlan, Integer>{
	
	public List findBySearch(final PlanSearch search, final int offset, final int length);

	public int getCountBySearch(final PlanSearch search);

	// 统计方案金额总计
	public List getSumBySearch(final PlanSearch search);
	
	/**
	 * 撤销没有打票方案(包括合买和代购)
	 */
	public void abortUnPrintPlan(LotteryPlan lotteryPlan) throws Exception;

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
	
	//派奖
	public void returnPrize(String planNo, String amount,String pretaxPrize) throws Exception ;
	
	/**
	 * 合买方案自动补单，采用云彩用户账号
	 * 
	 * @param LotteryPlan
	 *            plan
	 * @return
	 */
	public void planHMAutoFill(LotteryPlan plan) throws Exception;
	
	
	/**
	 * 合买订单保底成功
	 */
	public void dealPlanBdSuccess(LotteryPlan lotteryPlan) throws Exception;
	
	
	/**
	 * 撤销合买失败的方案
	 */
	public void dealPlanBdFailure(LotteryPlan lotteryPlan,PlanStatus planStatus) throws Exception;

	
	/**
	 * 出票检控
	 * @param openResultMap
	 * @param lotteryPlan
	 * @return
	 * @throws Exception
	 */
	public boolean planCheckBingo(HashMap<String, String> openResultMap, LotteryPlan lotteryPlan) throws Exception;

	
	/**
	 * 检票
	 * 
	 * @param adminLotteryTermDAO 
	 * @throws Exception
	 */
	public void jianPiao(LotteryPlan lotteryPlan) throws Exception;
	
	/**根据彩票种类查找合买方案列表
	 * @param lotteryType 彩种类
	 * @param readName 发起人用户名
	 * @param offset 页数
	 * @param pageSize 每页记录数
	 * @param planStatus 方案状态
	 *@param order  0降序 1升序
	 *@param key 0方案进度1方案金额
	 * @return
	 */
	public List<LotteryPlan> findLotteryPlans(LotteryType lotteryType,String readName,int offset,int pageSize,PlanStatus planStatus,PlanType planType,int order,int key);
	
	/**查找合买在售方案
	 * @param lotteryType
	 * @param readName
	 * @param offset
	 * @param pageSize
	 * @param planType 1代购 2合买 此处用合买
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

	
	
	/**
	 * 
	 * 通过彩种查找当前期方案
	 * 
	 */
	public List findCurrentLotteryPlan(LotteryType lotteryType, PlanTicketStatus status);

	
	
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
