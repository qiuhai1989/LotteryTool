package com.yuncai.modules.timer.ticket;

import java.util.List;

import com.yuncai.core.MailSender.EmailMailManager;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.system.SystemErrorManager;

public class JianPiaoThread extends Thread {
	private LotteryPlanService lotteryPlanService;
	private List<Integer> lotteryTypeList;
	// 默认为10秒的休息时间
	private long sleepTime = 10000;
	private boolean isRun = true;

	@SuppressWarnings("unchecked")
	public void run() {
		while (true) {
			try {
				sleep(sleepTime);
				LogUtil.out("开始检票...");

				for (Integer lotteryType : lotteryTypeList) {
					// LogUtil.out("开始检票:"+LotteryType.getItem(lotteryType).getName()+"...");
					List<LotteryPlan> lotteryList = lotteryPlanService.findCurrentLotteryPlan(LotteryType.getItem(lotteryType),PlanTicketStatus.TICKETING);
					LogUtil.out("开始检票:" + LotteryType.getItem(lotteryType).getName() + " 出票中方案数:" + (lotteryList == null ? 0 : lotteryList.size()));
					if (lotteryList != null) {
						for (LotteryPlan lotteryPlan : lotteryList) {
							try {
								this.lotteryPlanService.jianPiao(lotteryPlan);
							} catch (Exception e) {
								e.printStackTrace();
								// 检票票异常，发警告
								SystemErrorManager.addError(ErrorType.检票, lotteryPlan.getLotteryType(), lotteryPlan.getTerm(), ""
										+ lotteryPlan.getPlanNo(), "" + lotteryPlan.getAccount(), lotteryPlan.getAmount().doubleValue(), "第 "
										+ lotteryPlan.getTerm() + " 期,方案号：" + lotteryPlan.getPlanNo() + " 金额：" + lotteryPlan.getAmount() + "，检票失败！"
										+ e.getMessage(), e);
								
								//发送email
								EmailMailManager.sendMail(lotteryPlan.getLotteryType().getName()+"第 " + lotteryPlan.getTerm() + " 期"+ErrorType.检票.getName()+"失败", "第 "
										+ lotteryPlan.getTerm() + " 期,方案号：" + lotteryPlan.getPlanNo() + " 金额：" + lotteryPlan.getAmount() + "，检票失败！"
										+ e.getMessage());
						
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				try {
					sleep(sleepTime);
				} catch (Exception se) {
				}
			}
		}
	}
	public LotteryPlanService getLotteryPlanService() {
		return lotteryPlanService;
	}
	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}
	public List<Integer> getLotteryTypeList() {
		return lotteryTypeList;
	}
	public void setLotteryTypeList(List<Integer> lotteryTypeList) {
		this.lotteryTypeList = lotteryTypeList;
	}
	public long getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	public boolean isRun() {
		return isRun;
	}
	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

}
