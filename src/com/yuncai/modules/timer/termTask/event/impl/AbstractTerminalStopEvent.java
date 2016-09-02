package com.yuncai.modules.timer.termTask.event.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yuncai.core.MailSender.EmailMailManager;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.timer.termTask.event.Event;

public class AbstractTerminalStopEvent extends AbstractEvent{
	static final String EVENT_NAME="终端截止事件";

	private LotteryPlanService lotteryPlanService;
	
	@Override
	public Map execute(Map request) {
		LotteryType lotteryType = (LotteryType) request.get(LOTTERY_TYPE);
		Isuses term = (Isuses) request.get(NOW_TERM);
		Isuses nextTerm = (Isuses) request.get(NEXT_TERM);
		List refLotteryTypeList = (List)request.get(Event.RefLotteryTypeList);
		
		LogUtil.out("*********" + lotteryType.getName() + " " + term.getName() + " 终端截止事件启动*********");
		
		//待处理彩种列表
		List<Integer> ltList = new ArrayList<Integer>();
		ltList.add(lotteryType.getValue());
		//如果相关彩种list不为null, 则加入到待处理彩种列表中
		if(refLotteryTypeList != null) ltList.addAll(refLotteryTypeList);
		
		//开始处理
		for(Integer lt : ltList){
			// 开始执行单式合买结束事件
			//planBusiness.hmStopProcess(LotteryType.getItem(lt), term.getTerm(), SelectType.UPLOAD);

			List unPrintPlanList = lotteryPlanService.findUnPrintPlanByLotteryTypeAndTerm(LotteryType.getItem(lt), term.getName());
			LogUtil.out(LotteryType.getItem(lt).getName() + "找到没有出票的方案， 数量为:" + unPrintPlanList.size());

			// //-----------------------------先对出票中的进行一次检票，防止检票10s的空档期--------------------
			for (int i = 0; i < unPrintPlanList.size(); i++) {
				try {
					LotteryPlan plan = (LotteryPlan) unPrintPlanList.get(i);
					this.lotteryPlanService.jianPiao(plan);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// ---------------------------------处理退单--------------------------------------
			// 重查未出票定单
			unPrintPlanList = lotteryPlanService.findUnPrintPlanByLotteryTypeAndTerm(LotteryType.getItem(lt), term.getName());
			LogUtil.out(LotteryType.getItem(lt).getName() + "重查未出票定单，找到没有出票的方案，数量为:" + unPrintPlanList.size());
			for (int i = 0; i < unPrintPlanList.size(); i++) {
				LotteryPlan plan = (LotteryPlan) unPrintPlanList.get(i);
				try {
					lotteryPlanService.abortUnPrintPlan(plan);
				} catch (Exception e) {
					e.printStackTrace();
					String message = LotteryType.getItem(lt).getName() + "处理未出票退款异常，方案编号:" + plan.getPlanNo();
					//发送email
					EmailMailManager.sendMail(plan.getLotteryType().getName()+"第 " + plan.getTerm() + " 期处理未出票退款异常", message);
				}
			}
		}
		
		//重新设置事件运行环境
		request.put(NOW_TERM, nextTerm);
		return request;
	}

	@Override
	public String getEventName() {
		return EVENT_NAME;
	}

	@Override
	public long getScheduledTime(Map request) {
		Isuses term = (Isuses) request.get(NOW_TERM);
		return term.getTerminalEndDateTime().getTime();
		
	}

}
