package com.yuncai.modules.timer.term;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.yuncai.core.MailSender.EmailMailManager;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MatchStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Sql.LotteryTypeProps;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtMatchService;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.sqlService.lottery.LotteryTypePropsService;

public abstract class SportteryMatchStopThread1<X> extends Thread {
	private LotteryTypePropsService  sqlLotteryTypePropsService;
	LotteryPlanService  lotteryPlanService;
	private FtMatchService  ftMatchService;
	
	
	private boolean isRun = true;
	
	public void onEnd() {
		this.isRun = false;
	}
	
	private long process() throws Exception {
		long sleepMilli = -1;
		// 找到在售比赛，按照比赛时间顺序排序
		DetachedCriteria criteria = DetachedCriteria.forClass(FtMatch.class);
		criteria.add(Restrictions.eq("status", MatchStatus.ONSALE.ordinal()));
		criteria.addOrder(Order.asc("startTime"));  //开赛时间
		List<FtMatch> raceList=this.ftMatchService.findByDetachedCriteria(criteria);
		LotteryTypeProps props = this.sqlLotteryTypePropsService.findbuyLotteryType(LotteryType.JC_ZQ_SPF);
		int fsaheadTime = props.getFsAheadTime();
		//LogUtil.out("-- " + getLotteryName() + "，找到销售状态赛事数量为:" + raceList.size());

		// 遍历赛事,取得最早截止的时间
		if (raceList != null && raceList.size() > 0) {
			for (FtMatch race : raceList) {
				Date endTime = getEndSaleTime(race.getStartTime(), fsaheadTime);
				//LogUtil.out("时间判断>>>>>>>" + race.getTgName() + " 比赛时间:" + race.getStartTime().toString() + " 截止时间：" + endTime.toString());
				// 计算截止时间差
				long interval = endTime.getTime() - new Date().getTime();

				if (interval <= 0) {// 如果赛事截止
					race.setStatus(MatchStatus.SALEEND.ordinal());
					this.ftMatchService.saveOrUpdate(race);
					//暂时不用处理sql
					
					
				} else {
					sleepMilli = interval;
					// 比赛已按时间排序，这里找到第一个未截止的时间就行了
					break;
				}
			}


			// 开始处理招募中的合买单
			processHm();
			//生成首页竞彩足球彩期数据

		} else {
			throw new Exception("没有可销售的赛事，不能启动赛事停售线程!");
		}
		
		
		return sleepMilli;
	}
	private void processHm() {
		LogUtil.out("-- " + getLotteryName() + "开始自动处理合买");
		// TODO:查询优化
		// 查询当期所有招募中的订单
		List<LotteryPlan> hmList = getHmzmz();
		//LogUtil.out("-- " + getLotteryName() + "自动处理合买,找到招募中订单个数： " + hmList.size());
		for (LotteryPlan plan : hmList) {
			// 方案中保存的是最早那场截止时间，通过这个时间判断是否要进行退单或保底成功
			Date endTime = plan.getDealDateTime();
			if (new Date().getTime() < endTime.getTime()) {// 如果当前时间未超过网站截止时间,不处理
				//LogUtil.out(plan.getLotteryType().getName() + " 方案未过期不处理，方案编号:" + plan.getPlanNo());
				continue;
			}

			//LogUtil.out(plan.getLotteryType().getName() + " 处理合买结束事件，方案编号:" + plan.getPlanNo());

			// 通过是否可出票标志来判断是否退单
			if (plan.getIsAbleTicketing() == CommonStatus.YES.getValue()) {
				// 如果是可出票的方案，说明是 （认购+保底）>=90% 及其它需要保证出票的方案
				//LogUtil.out(getLotteryName() + "处理合买结束事件，方案编号:" + plan.getPlanNo() + "，保底成功............");

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
						String message = getLotteryName() + "处理自动保底失败，方案号:" + plan.getPlanNo();
						//发送邮件
						EmailMailManager.sendMail(message, e.getMessage());
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			} else {// 执行流单操作
				//LogUtil.out(getLotteryName() + "处理合买结束事件，方案编号:" + plan.getPlanNo() + "，保底失败，或都没有保底，上传方案的");
				try {
					lotteryPlanService.dealPlanBdFailure(plan,PlanStatus.ABORT);
				} catch (Exception e) {
					e.printStackTrace();
					try {
						String message = getLotteryName() + "处理流产单退款失败，方案号:" + plan.getPlanNo();
						//发送邮件
						EmailMailManager.sendMail(message, e.getMessage());
					} catch (Exception ee) {
						ee.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void run() {
		while (isRun) {
			try {
				// 处理过期对阵并计算睡觉时间
				long sleepTime = process();
				
				//！！！休眠时间最多不超过1小时，防止后抓取到的对阵先截止
				long maxSleepTime = 1*60*60*1000;
				if(sleepTime > maxSleepTime ){
					System.out.println("--"+ getLotteryName() +"停售事件处理成功，预计休眠:" + sleepTime / 1000 /60 + "分钟");
					sleepTime = maxSleepTime;
				}
				System.out.println("--"+ getLotteryName() +" 停售事件处理成功，实际将休眠:" + sleepTime / 1000 /60 + "分钟");

				/*
				 * /用同步来处理休眠，供以实现后触发控制 synchronized(this){ this.wait(sleepTime); } //
				 */
				sleep(sleepTime);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("--"+ getLotteryName() +" 停售事件处理异常，1分钟后重试");
				//发送邮件
				EmailMailManager.sendMail(getLotteryName()+" 停售事件处理异常！", e.getMessage());
				try {
					sleep(60 * 1000);
				} catch (Exception ee) {
				}
			}

		}
	}
	


	public abstract Date getEndSaleTime(Date matchTime, int aheadMilli) ;// 计算实际的截止时间


	// 取得招募中的订单
	public abstract List<LotteryPlan> getHmzmz();

	// 取得彩种名称
	public abstract String getLotteryName();


	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}
	
	public void setSqlLotteryTypePropsService(LotteryTypePropsService sqlLotteryTypePropsService) {
		this.sqlLotteryTypePropsService = sqlLotteryTypePropsService;
	}
	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setFtMatchService(FtMatchService ftMatchService) {
		this.ftMatchService = ftMatchService;
	}

}
