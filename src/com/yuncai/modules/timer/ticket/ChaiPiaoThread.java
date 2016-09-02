package com.yuncai.modules.timer.ticket;

import com.yuncai.core.MailSender.EmailMailManager;
import com.yuncai.core.MailSender.MailBean;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.ticket.TicketService;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesService;
import com.yuncai.modules.lottery.system.SystemErrorManager;

import java.util.*;
public class ChaiPiaoThread extends Thread{
	private TicketService ticketService;
	private LotteryPlanService lotteryPlanService;
	private SchemesService sqlSchemesService;
	private List<Integer> lotteryTypeList;
	// 默认为10秒的休息时间
	private long sleepTime = 10000;
	private boolean isRun = true;
	


	@SuppressWarnings("unchecked")
	public void run() {
		LogUtil.out("开始拆票");
		while (isRun) {
			try {
				//LogUtil.out("开始拆票");
				for (Integer lotteryType : lotteryTypeList) {
				
					// 新的拆票条件 ,必须是已上传的 isUploadContent = 0
					List<LotteryPlan> lotteryPlanList = lotteryPlanService.findByIsAbleTicketingAndPlanTickctStatus(LotteryType.getItem(lotteryType),
							CommonStatus.YES.getValue(), PlanTicketStatus.NO_PROCESS);

					for (LotteryPlan lotteryPlan : lotteryPlanList) {
						try {
							ticketService.chaipiao(lotteryPlan);
							
							//模拟开sql配合流程开奖-----
//							Schemes buyPlan=this.sqlSchemesService.findSchemeByPlanNOForUpdate(lotteryPlan.getPlanNo());
//							buyPlan.setBuyed(true);
//							buyPlan.setPrintOutDateTime(new Date());
//							this.sqlSchemesService.saveOrUpdate(buyPlan);
						} catch (Exception e) {
							e.printStackTrace();
							//拆票异常，发警告
							SystemErrorManager.addError(ErrorType.拆票, lotteryPlan.getLotteryType(), lotteryPlan.getTerm(), ""+lotteryPlan.getPlanNo(), ""+ lotteryPlan.getAccount(), lotteryPlan.getAmount().doubleValue()
									,"第 " + lotteryPlan.getTerm() + " 期,方案号："+lotteryPlan.getPlanNo()+" 金额："+lotteryPlan.getAmount()+"，拆票失败！" + e.getMessage() 
									,e);
							//拆票失败更改状态
							LotteryPlan ticketFail=this.lotteryPlanService.find(lotteryPlan.getPlanNo());
							if(ticketFail!=null){
								ticketFail.setPlanTicketStatus(PlanTicketStatus.TICKET_TO_FAIL);
								this.lotteryPlanService.saveOrUpdate(ticketFail);
							}
							//发送email
							EmailMailManager.sendMail(lotteryPlan.getLotteryType().getName()+"第 " + lotteryPlan.getTerm() + " 期"+ErrorType.拆票.getName()+"失败", "第 " + lotteryPlan.getTerm() + " 期,方案号："+lotteryPlan.getPlanNo()+" 金额："+lotteryPlan.getAmount()+"，拆票失败！" + e.getMessage());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setLotteryTypeList(List<Integer> lotteryTypeList) {
		this.lotteryTypeList = lotteryTypeList;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setSqlSchemesService(SchemesService sqlSchemesService) {
		this.sqlSchemesService = sqlSchemesService;
	}




}
