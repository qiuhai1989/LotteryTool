package com.yuncai.modules.lottery.business.lottery;

import java.util.HashMap;
import java.util.List;

import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;

public interface PlanBusiness {
	/**
	 * 不包括已开奖方案
	 */
	public final static Boolean UNINCLUDE_OPENEDPLAN=false;
	/**
	 * 包括已开奖未派奖方案
	 */
	public final static Boolean INCLUDE_OPENEDPLAN=true;
	
	/**
	 * 派奖
	 * @param planNo
	 * @param amount //税前
	 * @param amountPer//税后  
	 * lm 2013-06-04
	 */
	public void returnPrize(String planNo, String amount,String amountPer);
	
	/**
	 * 拆行追号
	 * @param lotteryType //彩种
	 * @param term   //当期开奖期数
	 */
	public void executeChase(LotteryType lotteryType,String term)throws ServiceException, Exception;

	
	// 未派奖的方案进行开奖
	public List<LotteryPlan> checkBingo(HashMap<String, String> openResultMap,Boolean isOpenAgain);


	
	/**
	 * @param lotteryType //彩种
	 * @param term   //当期开奖期数
	 * @param st 投注方式
	 */
	public void hmStopProcess(LotteryType lt, String term, SelectType st);

}
