package com.yuncai.modules.timer.term;



import java.util.Date;
import java.util.List;

import com.yuncai.core.sporttery.TimeTools;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;


public class SportteryFootballMatchStopThread extends SportteryMatchStopThread1<FtMatch> {
	
	//取得彩种名称
	@Override
	public String getLotteryName() {
		return "竞彩足球";
	}
	
	
	// 取得招募中的订单
	@SuppressWarnings("unchecked")
	public List<LotteryPlan> getHmzmz() {
		String term = "001";
		List<LotteryPlan> hmList = lotteryPlanService.findHmPlanForFailure(LotteryType.JC_ZQ_BF, PlanType.HM, SelectType.ALL, term);
		hmList.addAll(lotteryPlanService.findHmPlanForFailure(LotteryType.JC_ZQ_BQC, PlanType.HM, SelectType.ALL, term));
		hmList.addAll(lotteryPlanService.findHmPlanForFailure(LotteryType.JC_ZQ_JQS, PlanType.HM, SelectType.ALL, term));
		hmList.addAll(lotteryPlanService.findHmPlanForFailure(LotteryType.JC_ZQ_SPF, PlanType.HM, SelectType.ALL, term));
		hmList.addAll(lotteryPlanService.findHmPlanForFailure(LotteryType.JC_ZQ_RQSPF, PlanType.HM, SelectType.ALL, term));

		return hmList;
	}
	
	public Class<FtMatch> getXClass(){
		return FtMatch.class;
	}
	
	public Date getEndSaleTime(Date matchTime, int aheadMilli) {// 计算实际的截止时间
		return TimeTools.getFbEndSaleTime(matchTime, aheadMilli);
	}


}
