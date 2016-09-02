package com.yuncai.modules.timer.termTask.event.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.business.lottery.PlanBusiness;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.LotteryTypeProps;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.timer.termTask.event.Event;

/**
 * 合买截止事件抽象实现类
 * @author lm
 *
 */

public abstract class AbstractHmStopEvent extends AbstractEvent{
	private PlanBusiness planBusiness;
	static final String EVENT_NAME="合买截止事件";
	/**
	 * 合买截止改为只处理单式合买截至事件，复式合买放到彩期截止事件去处理
	 * @author lm
	 * @since 2013-06-18
	 */
	@SuppressWarnings("unchecked")
	public Map execute(Map request) {
		Isuses term=(Isuses)request.get(NOW_TERM);
		LotteryType lotteryType=(LotteryType)request.get(LOTTERY_TYPE);
		List refLotteryTypeList = (List)request.get(Event.RefLotteryTypeList);
		
		//待处理彩种列表
		List<Integer> ltList = new ArrayList<Integer>();
		ltList.add(lotteryType.getValue());
		//如果相关彩种list不为null, 则加入到待处理彩种列表中
		if(refLotteryTypeList != null) ltList.addAll(refLotteryTypeList);
		
		//开始处理
		for(Integer lt : ltList){
			// 开始执行单式合买结束事件
			this.planBusiness.hmStopProcess(lotteryType, term.getName(), SelectType.UPLOAD);
		}
		
		
		return request;
	
	}
	

	@SuppressWarnings("unchecked")
	public long getScheduledTime(Map request) {
		Isuses lotteryTerm = (Isuses) request.get(NOW_TERM);
		LotteryTypeProps props=(LotteryTypeProps) request.get(PROPS);
		if(props!=null){
			return lotteryTerm.getEndTime().getTime() - props.getDsAheadTime();
		}
		return lotteryTerm.getEndTime().getTime();
	}


	
	public PlanBusiness getPlanBusiness() {
		return planBusiness;
	}


	public void setPlanBusiness(PlanBusiness planBusiness) {
		this.planBusiness = planBusiness;
	}


	public String getEventName() {
		return EVENT_NAME;
	}

}
