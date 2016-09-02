package com.yuncai.modules.timer.termTask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.sun.org.apache.bcel.internal.generic.IUSHR;
import com.yuncai.core.MailSender.EmailMailManager;
import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.model.Sql.LotteryTypeProps;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.service.sqlService.lottery.LotteryTypePropsService;
import com.yuncai.modules.lottery.system.SystemErrorManager;
import com.yuncai.modules.timer.termTask.event.Event;

public abstract class AbstractHandle extends Thread implements Handle{
	final String threadName = "彩期守护线程";
	LotteryType lotteryType;
	private int lotteryId;
	private LotteryTypePropsService  sqlLotteryTypePropsService;
	private IsusesService sqlIsusesService;
	//相关处理彩种列表（胜负彩，单场这类多彩种公用同一彩期的玩法使用）
	List<Integer> refLotteryTypeList;
	
	boolean isRun = true;

	Event hmStopEvent;

	Event termStopEvent;

	Event nextEvent;
	
	Event terminalStopEvent;

	// 事件运行的环境参数
	Map request;

	long beforeRunWaitTime = 1000;

	public void onEnd() {
		this.isRun = false;
	}

	public void run() {
		lotteryType = LotteryType.getItem(lotteryId);
		LogUtil.out("---------" + threadName + ":" + lotteryType.getName() + " 启动前开始休眠" + (beforeRunWaitTime / 1000) + "秒------");
		try {sleep(beforeRunWaitTime);} catch (Exception e) {}

		while (true) {
			
			// 初始化数据库的彩期及运行环境
			if (isRun) {
				try{
					onStart();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			try{
				while (isRun) {
					Isuses currentTerm = (Isuses)request.get(Event.NOW_TERM);

					//如果当前期为空，说明彩期数据异常，则退出运行循环，重新初始化
					if(currentTerm == null ){
						if( LotteryType.CTZQList.contains(lotteryType)){
							// 足彩,单场没有彩期可能是正常的，不抛错
							break;
						}else{
							throw new Exception("没有当前彩期");
						}
					}

					long runTime = nextEvent.getScheduledTime(request);
					long waitTime = runTime - System.currentTimeMillis();
					if (waitTime > 0) {
						try {
							LogUtil.out("---------" + threadName + ":" + lotteryType.getName() + " 第" + currentTerm.getName() + "将于" + waitTime / 1000 + "秒后("
									+ DateTools.dateToString(new Date(runTime), "MM-dd HH:mm:ss") + ")执行" + nextEvent.getEventName() + this.nextEvent);
							synchronized (this) {
								this.wait(waitTime);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					LogUtil.out("---------" + threadName + ":" + lotteryType.getName() + " 第" + currentTerm.getName() + "执行 " + nextEvent.getEventName() + nextEvent);
					request = nextEvent.execute(request);
					nextEvent = nextEvent.getNextEvent();
					System.out.println(nextEvent);
				}
			} catch (Exception e) {
				//发警告
				SystemErrorManager.addError(ErrorType.彩期守护, lotteryType, "", "", "", 0.0
						,"彩期守护线程出错！" + e.getMessage() + " ,将延迟1分钟重新加载"
						,e);
				LogUtil.out("---------" + threadName + ":" +  lotteryType.getName() + "期数守护线程中断 :" + e.getMessage() + " . 将延迟1分钟重新加载----");
				e.printStackTrace();
				
				//发送email
				EmailMailManager.sendMail(threadName+":"+lotteryType.getName()+ErrorType.彩期守护.getName()+"出错！", "彩期守护线程出错！" + e.getMessage() + " ,将延迟1分钟重新加载");
		
			}
			
			try {sleep(60 * 1000); } catch (Exception e) {e.printStackTrace();}
			
			if ( !isRun ) {
				// 被重新唤醒，是不运行状态，退出线程
				break;
			}
		}
		LogUtil.out("---------" + threadName + ":" + lotteryType.getName() + " 退出！");
	}
	
	abstract void initWorkFlow();
	

	@SuppressWarnings("unchecked")
	@Override
	public void onStart() {
		//LogUtil.out(threadName + ":" + lotteryType.getName() + " 开始初始化...");
		
		//初始化request
		request = new HashMap();

		LogUtil.out("-------" + threadName + ":" + lotteryType.getName() + hmStopEvent);
		LogUtil.out("-------" + threadName + ":" + lotteryType.getName() + termStopEvent);
		LogUtil.out("-------" + threadName + ":" + lotteryType.getName() + terminalStopEvent);
		// 设置事件处理流程
		initWorkFlow();
		
		// 查找玩法属性
		LotteryTypeProps props = sqlLotteryTypePropsService.findbuyLotteryType(lotteryType);
		
		// 根据时间获取当前期
		Calendar calendar = Calendar.getInstance();
		Isuses actualCurrentTerm = null;  //备注方案自己去写

		actualCurrentTerm = sqlIsusesService.findCurrentTermByDate(lotteryType, calendar.getTime());

		// 根据当前期找到上一期
		Isuses actualPreTerm = null;
		if(actualCurrentTerm !=null){
			actualPreTerm = sqlIsusesService.findPreByLotteryTypeAndTerm(lotteryType, actualCurrentTerm.getName());
		}
		//查找彩期已截止，但未派奖彩期 //方法自己写
//		List<Isuses> overdueAnduncompleteTerm =sqlIsusesService.findOverdueByDateAndUnReturnPrizeTerm(lotteryType);
//		if(overdueAnduncompleteTerm!=null){
//			for (Isuses tempCurrentTerm : overdueAnduncompleteTerm) {
//				request = new HashMap();
//				request.put(Event.LOTTERY_TYPE, lotteryType);
//				request.put(Event.RefLotteryTypeList,refLotteryTypeList);
//				request.put(Event.PROPS, props);
//				request.put(Event.NOW_TERM, tempCurrentTerm);
//				// 提示事件处理，本次为修复操作（开奖事件将不做数据分析和彩期静态化等操作）
//				request.put(Event.IS_REPAIR, true);
//
//				LogUtil.out("---------" + threadName + ":" + lotteryType.getName() + " " + tempCurrentTerm.getName() + " 开始补漏操作*********");
//				//合买，彩期stop事件次序
//				hmStopEvent.execute(request);
//				termStopEvent.execute(request);
//				//关闭修复标记
//				request.put(Event.IS_REPAIR, false);
//			}
//		}

		
		request = new HashMap();
		request.put(Event.LOTTERY_TYPE, lotteryType);
		request.put(Event.RefLotteryTypeList,refLotteryTypeList);
		request.put(Event.PROPS, props);
		
		request.put(Event.NOW_TERM, actualCurrentTerm);
		request.put(Event.NEXT_TERM, null);
		nextEvent = hmStopEvent;
		
		// 设置当前期标志
		if(actualCurrentTerm != null){
			// 可能因为事件补漏操作或者彩期状态本来已经混乱 ,所以根据时间初始化彩期状态
			// 清除所有当前期
			sqlIsusesService.initCurrentFlag(lotteryType);
			LogUtil.out("---------" + threadName + ":" + lotteryType.getName() + " 当前期为：" + actualCurrentTerm.getName());
			actualCurrentTerm.setState(LotteryTermStatus.OPEN);
			actualCurrentTerm.setCurrentIsuses(CommonStatus.YES.getValue());
			sqlIsusesService.saveOrUpdate(actualCurrentTerm);
			
		}

		//LogUtil.out("---------" + threadName + ":" + lotteryType.getName() + " 初始化完成！");
		return;
		
		
	}




	public LotteryType getLotteryType() {
		return lotteryType;
	}




	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}




	public boolean isRun() {
		return isRun;
	}




	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}




	public Event getHmStopEvent() {
		return hmStopEvent;
	}




	public void setHmStopEvent(Event hmStopEvent) {
		this.hmStopEvent = hmStopEvent;
	}




	public Event getTermStopEvent() {
		return termStopEvent;
	}




	public void setTermStopEvent(Event termStopEvent) {
		this.termStopEvent = termStopEvent;
	}




	public Event getNextEvent() {
		return nextEvent;
	}




	public void setNextEvent(Event nextEvent) {
		this.nextEvent = nextEvent;
	}




	public long getBeforeRunWaitTime() {
		return beforeRunWaitTime;
	}




	public void setBeforeRunWaitTime(long beforeRunWaitTime) {
		this.beforeRunWaitTime = beforeRunWaitTime;
	}




	public List<Integer> getRefLotteryTypeList() {
		return refLotteryTypeList;
	}

	public void setSqlLotteryTypePropsService(LotteryTypePropsService sqlLotteryTypePropsService) {
		this.sqlLotteryTypePropsService = sqlLotteryTypePropsService;
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Event getTerminalStopEvent() {
		return terminalStopEvent;
	}

	public void setTerminalStopEvent(Event terminalStopEvent) {
		this.terminalStopEvent = terminalStopEvent;
	}



}
