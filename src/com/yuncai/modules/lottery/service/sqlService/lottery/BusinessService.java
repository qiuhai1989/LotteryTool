package com.yuncai.modules.lottery.service.sqlService.lottery;

import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Sql.Users;

public interface BusinessService {
	
	/**
	 * 参与合买 针对sql自动跟单参与合买处理接口
	 * @param use //用户
	 * @param planNO //订单
	 * @param share  //份数
	 * @param isAutoFollowScheme //是否是自动跟单的购买记录
	 * @param fromClient 方案来自网页或手机，1表示来自网页  2表示来自手机应用
	 * @return
	 * lm 2013-05-30
	 */
	public String buySchemesJoin(Users use,String planNO,int share,boolean isAutoFollowScheme,int fromClient,double amount,PlanStatus planStatus)throws ServiceException, Exception ;

	/*
	 * 追号
	 */
	public String buySchemesChase(Users use);
	
	/**
	 * 详情
	 * @param user
	 * @param uploadeFileContent 是否上存内容
	 * @param schemeNumber 方案ID
	 * @param title       标题
	 * @param playTypeId  玩法ID
	 * @param isuseId     彩期ID
	 * @param multiple    倍数
	 * @param money      总金额
	 * @param share      总份数
	 * @param secrecyLevel 是否公开
	 * @param quashStatus  撤单状态:0 未撤单 1 用户撤单 2 系统撤单
	 * @param AtTopStatus   置顶状态 0 无 1 申请置顶状态 2 已置顶状态，满员或者撤单后会自动设置为 0
	 * @param BuyedShare    认购份数
	 * @param Schedule       已买份数百份比
	 * @param ReSchedule     已买份数百份比（当买的份数等于，或者大于份数，就为0）
	 * @param LotteryNumber  投注内容
	 * @param fromClient     来自那里 
	 * @param ycSchemeNumber  oralceId 
	 * @param gentype        内型
	 * isPurchasing   认购时，当买的份数等于总份数，为1
	 * SchemeBonusScale //佣金比例
	 * @return
	 */
	public Integer makeSchemes(Users user,String uploadeFileContent,String schemeNumber,String title,Integer playTypeId,Integer isuseId
			,Integer multiple,Double money,Integer share,Short secrecyLevel,Short quashStatus,Short AtTopStatus
			,Integer BuyedShare,Double Schedule,Double ReSchedule,String LotteryNumber,Integer fromClient,
			Integer ycSchemeNumber,GenType gentype,boolean IsPurchasing,Double SchemeBonusScale,Double assureMoney,String lotteryNumberNew,String channel
			,String planDec);
	
	/**
	 * 购买详情
	 * @param user
	 * @param planNo
	 * @param share
	 * @param amount
	 * @param fromClient
	 * @param isAutofollowScheme   是否自动跟单
	 * @return
	 */
	public Integer makeBuyDetailes(Users user,Integer planNo,int share,double amount,int fromClient,boolean isAutofollowScheme);
	
	/**
	 * 更新方案编号
	 * @param planNo
	 * @param schemeNumber
	 * @return
	 */
	public String currentSchemes(Integer planNo,String schemeNumber);
	
	/**
	 * 冻结资金返款
	 * @param planNo
	 * @param amount
	 * @return
	 */
	public String buyFreezeToAble(String planNo,Short quashStatus) throws Exception,ServiceException;
	
	/**
	 * 方案备注
	 * @param schemesId
	 * @param money
	 * @param multiple
	 * @param lotteryNumber
	 * @param lotteryNumberNew
	 * @return
	 */
	public Integer makeSchemesNumber(Integer schemesId,Double money,Integer multiple,String lotteryNumber,String lotteryNumberNew);
}
