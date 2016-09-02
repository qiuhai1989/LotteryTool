package com.yuncai.modules.lottery.dao.oracleDao.lottery;

import com.yuncai.modules.lottery.bean.vo.HmShowBean;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlanOrder;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;

import java.util.*;
public interface LotteryPlanOrderDAO extends GenericDao<LotteryPlanOrder, Integer> {
	/**
	 * 根据定单编号查询成功的跟单列表（做钱包操作的，按账号排序,防止死锁）
	 * 
	 * @param planNo
	 * @return
	 */
	public List<LotteryPlanOrder> findSuccessByPlanNo(final Integer planNo);
	public LotteryPlanOrder findSearchList(final Integer planNo,final Integer orderNo,final String account);

	/**查找某一方案参与者用户名
	 * @param planNo
	 * @return
	 */
	public List<String> findInPeople(final Integer planNo);
	
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
	
	/**交易流水
	 * @param account 发起人
	 * @param startDate
	 * @param endDate
	 * @param planType
	 * @param listPlanNo
	 * @param planNo
	 * @param order
	 * @param offset
	 * @param pageSize
	 * @param userName 用户名
	 * @return
	 */
	public List<HmShowBean> findHmShowBeans(String account,Date startDate,Date endDate,PlanType planType,List<Integer>listPlanNo,int planNo,int order,int offset,int pageSize,String userName ,final LotteryType lotteryType);
	
	/**交易流水
	 * @param account
	 * @param startDate
	 * @param endDate
	 * @param planType
	 * @param listPlanNo
	 * @param planNo
	 * @param order
	 * @param offset
	 * @param pageSize
	 * @param userName 用户名
	 * @param planStatus
	 * @return
	 */
	public List<HmShowBean> findHmShowBeans(String account,Date startDate,Date endDate,PlanType planType,List<Integer>listPlanNo,int planNo,int order,int offset,int pageSize,String userName,LotteryType lotteryType ,PlanStatus planStatus);
	
	/**获取参与合买的订单列表
	 * @param planNo
	 * @param buyType
	 * @return
	 */
	public List<LotteryPlanOrder> findHMListByPlanNoAndBuyType(int planNo,BuyType buyType);
	
	
	/**根据订单号获得相应的结果
	 * @param planOrderNo
	 * @return
	 */
	public HmShowBean getHmShowBeanByPlanOrderNo(String planOrderNo);
	
	
	/**获取参与合买的订单列表 及用户昵称
	 * @param planNo
	 * @param buyType
	 * @return
	 */
	public List<Object[]> findHMListNickNameByPlanNoAndBuyType(int planNo,BuyType buyType);
	
	/**交易流水版本 可以过滤部分不显示的彩种
	 * @param account 发起人
	 * @param startDate
	 * @param endDate
	 * @param planType
	 * @param listPlanNo
	 * @param planNo
	 * @param order
	 * @param offset
	 * @param pageSize
	 * @param userName 用户名
	 * @param refuseList不显示的彩种
	 * @return
	 */
	public List<HmShowBean> findTransationFlow(String account,Date startDate,Date endDate,PlanType planType,List<Integer>listPlanNo,int planNo,int order,int offset,int pageSize,String userName ,LotteryType lotteryType,List<LotteryType>refuseList);	
}
