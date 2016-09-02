package com.yuncai.modules.lottery.business.impl.lottery;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;

import com.yuncai.core.MailSender.EmailMailManager;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.business.lottery.PlanBusiness;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;

import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.vo.ChaseTasksBean;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTaskDetailsService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;

import com.yuncai.modules.lottery.system.SystemErrorManager;

public class PlanBusinessImpl implements PlanBusiness {
	private LotteryPlanService lotteryPlanService;
	private IsusesService sqlIsusesService;
	private ChaseTaskDetailsService sqlChaseTaskDetailsService;
	private TradeService tradeService;

	/**
	 * 开奖业务
	 * @param openResultMap
	 * @param isOpenAgain 是否重新开奖
	 * @return
	 */
	public List<LotteryPlan> checkBingo(HashMap<String, String> openResultMap,Boolean isOpenAgain) {
		List<LotteryPlan> bingoList = new ArrayList<LotteryPlan>();
		
		//获取参数
		String term = openResultMap.get("term");
		String lotteryType = openResultMap.get("lotteryType");
		
		Long startTime = System.currentTimeMillis();
		
		try {
			List<LotteryPlan> planList = null;
			if(isOpenAgain){
				//查找已经出票&奖金为0&没派奖的定单  
				planList = lotteryPlanService.findByTicketOutAndNotReturnPrized(LotteryType.getItem(Integer.valueOf(lotteryType)), term);
			}else{
				//查找已经出票&没开奖的定单  
				planList = lotteryPlanService.findByTicketOutAndNotOpenPrized(LotteryType.getItem(Integer.valueOf(lotteryType)), term);
			}
			LogUtil.out("-----------------彩种：" + lotteryType + " 进行开奖： " + openResultMap + "方案数：" + planList.size());
			
			for (int p = 0; p < planList.size(); p++) {
				LotteryPlan lotteryPlan = planList.get(p);
				LogUtil.out("-------对"+lotteryPlan.getPlanNo()+"方案进行开奖-----");
				try {
					if(Integer.parseInt(lotteryType) == LotteryType.JXSSC.getValue()) {//江西时时彩
						openResultMap.put("amount", lotteryPlan.getAmount()+"");
					}
					if(Integer.parseInt(lotteryType) == LotteryType.GD11X5.getValue() || Integer.parseInt(lotteryType) == LotteryType.SYYDJ.getValue()) {//广东11x5 || 11运夺金
						openResultMap.put("account", lotteryPlan.getAccount()+"");
					}
					
					//开奖
					if (lotteryPlanService.planCheckBingo(openResultMap, lotteryPlan)) {
						bingoList.add(lotteryPlan);
					}
				}catch (Exception e) {
					e.printStackTrace();
					LogUtil.out("定单：" + lotteryPlan.getPlanNo() + " 开奖失败.");
					LotteryType lotType=LotteryType.getItem(Integer.parseInt(lotteryType));
						SystemErrorManager.addError(ErrorType.开奖, lotteryPlan.getLotteryType(), lotteryPlan.getTerm(), ""+lotteryPlan.getPlanNo(), lotteryPlan.getAccount(), lotteryPlan.getAmount().doubleValue()
							,"第 " + lotteryPlan.getTerm() + " 期,方案：" + lotteryPlan.getPlanNo() + " 开奖失败！" + e.getMessage()
							,e);
					//发送邮件
					EmailMailManager.sendMail(lotteryPlan.getLotteryType().getName()+"第 " + lotteryPlan.getTerm() + " 期"+ErrorType.开奖.getName()+"失败！", "第 " + lotteryPlan.getTerm() + " 期,方案：" + lotteryPlan.getPlanNo() + " 开奖失败！" + e.getMessage());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			LotteryType lotType=LotteryType.getItem(Integer.parseInt(lotteryType));
				//保存开奖错误
				SystemErrorManager.addError(ErrorType.开奖,lotType , term, "", "", 0.0
						,"第 " + term + " 期, 开奖失败！" + e.getMessage()
						,e);
				//发送邮件
				EmailMailManager.sendMail(lotType.getName()+"第 " + term + " 期"+ErrorType.开奖.getName()+"失败！", "第 " + term + " 期, 开奖失败！" + e.getMessage());
		}
		
		Long endTime = System.currentTimeMillis();
		LogUtil.out("开奖处理--共花:" + (endTime - startTime) + "ms");
		
		return bingoList;
	}
	/**
	 * 派奖
	 * @param planNo
	 * @param amount //税前
	 * @param amountPer//税后  
	 * lm 2013-06-04
	 */
	@Override
	public void returnPrize(String planNo, String amount, String amountPer) {
		
		LotteryPlan plan=lotteryPlanService.find(Integer.valueOf(planNo));
		try {
			//派奖
			
			lotteryPlanService.returnPrize(planNo, amount, amountPer);
		}catch (Exception e) {
			e.printStackTrace();
			LogUtil.out("定单：" + planNo + " 派奖失败.");
			
			//保存派奖错误
			SystemErrorManager.addError(ErrorType.派奖, plan.getLotteryType(), plan.getTerm(), ""+plan.getPlanNo(), plan.getAccount(), Double.valueOf(amount)
					,"第 " + plan.getTerm() + " 期,方案：" + plan.getPlanNo() + " 奖金："+amount+" 派奖失败！" + e.getMessage()
					,e);
			//发送邮件
			EmailMailManager.sendMail(plan.getLotteryType().getName()+"第 " + plan.getTerm() + " 期"+ErrorType.派奖.getName()+"失败！", 
					"第"+plan.getTerm()+"期,方案："+plan.getPlanNo()+"奖金："+amount+" 派奖失败！" + e.getMessage());
			
		}
		
	}
	
	/**
	 * 拆行追号
	 * @param lotteryType //彩种
	 * @param term   //当期开奖期数
	 */
	public void executeChase(LotteryType lotteryType,String term)throws ServiceException, Exception{
		// 小盘追号&高频
		Isuses nextTerm=this.sqlIsusesService.findNextByLotteryTypeAndTerm(lotteryType, term);
		
		if(nextTerm==null){
			LogUtil.out("=================彩种:"+lotteryType.getName()+"不存在期号"+term+"的下一期");
			throw new ServiceException("1", "本次操作被中止:追号失败");
		}
		try {
			List<ChaseTasksBean> chaseTermList =this.sqlChaseTaskDetailsService.findByTermList(lotteryType.getValue(), nextTerm.getId().toString()) ;
			LogUtil.out(lotteryType.getName() + "期数切换，开始执行追号==>");
			if(chaseTermList!=null && !chaseTermList.isEmpty()){
				for (int i = 0; i < chaseTermList.size(); i++) {
					ChaseTasksBean chaseTerm = (ChaseTasksBean)chaseTermList.get(i);
					LogUtil.out(lotteryType.getName() + "期数切换，开始执行追号==>追号No:" + chaseTerm.getChaseTaskId());
					try {
						//检查是否符追号
						if(!chaseTerm.isExecuted()){
							if(chaseTerm.getQuashStatus()==0 && chaseTerm.getQuashStatusDetail()==0){
								this.tradeService.doChase(chaseTerm, nextTerm);
							}else{
								LogUtil.out(lotteryType.getName() + "追号任务已撤销==>追号No:" + chaseTerm.getChaseTaskId());
							}
						}else{
							LogUtil.out(lotteryType.getName() + "追号任务已执行==>追号No:" + chaseTerm.getChaseTaskId());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						try {
							String message = lotteryType.getName() + "追号出现异常，追号编号:" + chaseTerm.getChaseTaskId();
							//LogUtil.out(message);
							//发送邮件
							EmailMailManager.sendMail(lotteryType.getName()+ErrorType.追号.getName()+"失败！", 
									message+e.getMessage());
						} catch (Exception ee) {
							ee.printStackTrace();
						}
					}
				}
			}else{
				LogUtil.out(lotteryType.getName() + "没有找到要开始执行的追号==>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void hmStopProcess(LotteryType lt, String term, SelectType st){
		//System.out.println("调用合买事件"+"-----------*******-------------------");
		List<LotteryPlan> hmList=this.lotteryPlanService.findHmPlanForFailure(lt, PlanType.HM, st, term);
		//LogUtil.out("-- " + "自动处理合买,找到招募中订单个数： " + hmList.size());
		for (LotteryPlan plan : hmList) {
			//LogUtil.out(plan.getLotteryType().getName() + " 处理合买结束事件，方案编号:" + plan.getPlanNo());

			// 通过是否可出票标志来判断是否退单
			if (plan.getIsAbleTicketing() == CommonStatus.YES.getValue()) {
				// 如果是可出票的方案，说明是 （认购+保底）>=90% 及其它需要保证出票的方案
				//LogUtil.out("处理合买结束事件，方案编号:" + plan.getPlanNo() + "，保底成功............");

				try {
					// 如果（认购+保底）< 100%
					if (plan.getReservePart() + plan.getSoldPart() < plan.getPart()) {
						// 剩余份数，用云彩户参与合买，让单成功。
						lotteryPlanService.planHMAutoFill(plan);
					}
					// 执行保底成功操作
					lotteryPlanService.dealPlanBdSuccess(plan);

				} catch (Exception e) {
					e.printStackTrace();
					try {
						String message = "处理自动保底失败，方案号:" + plan.getPlanNo();
						//发送邮件
						EmailMailManager.sendMail(plan.getLotteryType().getName()+"第 " + plan.getTerm() + " 期", 
								message+e.getMessage());
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			} else {// 执行流单操作
				//LogUtil.out("处理合买结束事件，方案编号:" + plan.getPlanNo() + "，保底失败，或都没有保底，上传方案的");
				try {
					lotteryPlanService.dealPlanBdFailure(plan,PlanStatus.ABORT);
				} catch (Exception e) {
					e.printStackTrace();
					try {
						String message = "处理流产单退款失败，方案号:" + plan.getPlanNo();
						//发送邮件
						EmailMailManager.sendMail(plan.getLotteryType().getName()+"第 " + plan.getTerm() + " 期", 
								message+e.getMessage());
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			}
		}
	}


	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	public void setSqlChaseTaskDetailsService(ChaseTaskDetailsService sqlChaseTaskDetailsService) {
		this.sqlChaseTaskDetailsService = sqlChaseTaskDetailsService;
	}

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

}
