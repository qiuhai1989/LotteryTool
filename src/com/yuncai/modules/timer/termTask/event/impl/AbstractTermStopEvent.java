package com.yuncai.modules.timer.termTask.event.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuncai.core.MailSender.EmailMailManager;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.business.lottery.PlanBusiness;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.timer.termTask.event.Event;

/**
 * 彩期截止事件抽象实现类
 * @author lm
 *
 */

public abstract class AbstractTermStopEvent extends AbstractEvent {
	static final String EVENT_NAME="彩期停售事件";
	private PlanBusiness planBusiness;
	private IsusesService sqlIsusesService;
	@SuppressWarnings("unchecked")
	public Map execute(Map request) {
		
		LotteryType lotteryType = (LotteryType) request.get(Event.LOTTERY_TYPE);
		List refLotteryTypeList = (List)request.get(Event.RefLotteryTypeList);
	
		Isuses tempCurrentTerm = (Isuses) request.get(Event.NOW_TERM);
		Isuses nextTerm = null;
		if(tempCurrentTerm!=null){
			 nextTerm = sqlIsusesService.findNextByLotteryTypeAndTerm(lotteryType, tempCurrentTerm.getName());
		}
		
		if(nextTerm==null){
			try {
				String message = lotteryType.getName() + "彩期切换异常，没有找到" + nextTerm + "的下一期";
				//发送邮件
				EmailMailManager.sendMail(lotteryType.getName() + "彩期切换异常", message);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		
		//待处理彩种列表
		List<Integer> ltList = new ArrayList<Integer>();
		ltList.add(lotteryType.getValue());
		//如果相关彩种list不为null, 则加入到待处理彩种列表中
		if(refLotteryTypeList != null) ltList.addAll(refLotteryTypeList);
		
		//开始处理
		for(Integer lt : ltList){
			// 开始执行所有合买结束事
			planBusiness.hmStopProcess(LotteryType.getItem(lt), tempCurrentTerm.getName(), SelectType.ALL);
		}
		
		//当前期彩期状态调整
		tempCurrentTerm.setState(LotteryTermStatus.CLOSE);
		tempCurrentTerm.setStateUpdateTime(new Date());
		sqlIsusesService.saveOrUpdate(tempCurrentTerm);
		
		if(nextTerm!=null){
			//如果有下一期，才修改当前期标记
			tempCurrentTerm.setCurrentIsuses(CommonStatus.NO.getValue());
			sqlIsusesService.saveOrUpdate(tempCurrentTerm);
			
			//下一期彩期状态调整
			nextTerm = sqlIsusesService.find(nextTerm.getId());
			nextTerm.setCurrentIsuses(CommonStatus.YES.getValue());
			if(nextTerm.getState().getValue()< LotteryTermStatus.OPEN.getValue()){
				nextTerm.setState(LotteryTermStatus.OPEN);
			}
			nextTerm.setStateUpdateTime(new Date());
			this.sqlIsusesService.saveOrUpdate(nextTerm);
			
			//重新设置事件运行环境
			//request.put(NOW_TERM, nextTerm);
		}
		
		request.put(NEXT_TERM, nextTerm);
		return request;
	}
	
	public String getEventName() {
		return EVENT_NAME;
	}
	
	/**
	 * 将在当前期截止时执行
	 */
	@SuppressWarnings("unchecked")
	public long getScheduledTime(Map request) {
		Isuses term = (Isuses) request.get(NOW_TERM);
		return term.getEndTime().getTime();
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	public PlanBusiness getPlanBusiness() {
		return planBusiness;
	}

	public void setPlanBusiness(PlanBusiness planBusiness) {
		this.planBusiness = planBusiness;
	}


}
