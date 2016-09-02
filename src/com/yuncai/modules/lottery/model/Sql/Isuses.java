package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.yuncai.core.common.PrizeBean;
import com.yuncai.core.common.PrizeCommon;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

/**
 * Isuses entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Isuses implements java.io.Serializable {

	// Fields

	private Integer id;
	//彩种ID
	private Integer lotteryId;
	//彩期
	private String name;
	//开售时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//追号任务是否执行了 (这个暂时好像没用到)
	private Boolean chaseExecuted;
	//是否开奖
	private Boolean isOpened;
	//开奖号码
	private String winLotteryNumber;
	//开奖操作员ID
	private Integer openOperatorId;
	//彩期状态
	private LotteryTermStatus state;
	//更新时间
	private Date stateUpdateTime;
	//开奖公告
	private String openAffiche;
	//是否当前期
	private Integer currentIsuses;
	private Integer totalSales;//总售出份数
	private String prizeLevelInfo;//开奖详细
	// 开奖时间
	private Date openDateTime;
	
	// 终端截止时间
	private Date terminalEndDateTime;

	/** default constructor */
	public Isuses() {
	}

	/** minimal constructor */
	public Isuses(Integer currentIsuses) {
		this.currentIsuses = currentIsuses;
	}

	/** full constructor */
	public Isuses(Integer lotteryId, String name, Date startTime,
			Date endTime, Boolean chaseExecuted, Boolean isOpened,
			String winLotteryNumber, Integer openOperatorId, LotteryTermStatus state,
			Date stateUpdateTime, String openAffiche, Integer currentIsuses) {
		this.lotteryId = lotteryId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.chaseExecuted = chaseExecuted;
		this.isOpened = isOpened;
		this.winLotteryNumber = winLotteryNumber;
		this.openOperatorId = openOperatorId;
		this.state = state;
		this.stateUpdateTime = stateUpdateTime;
		this.openAffiche = openAffiche;
		this.currentIsuses = currentIsuses;
	}
	
	
	
	public Isuses(Integer id, Integer lotteryId, String name, Date startTime, Date endTime, Boolean chaseExecuted, Boolean isOpened,
			String winLotteryNumber, Integer openOperatorId, LotteryTermStatus state, Date stateUpdateTime, String openAffiche,
			Integer currentIsuses, Integer totalSales, String prizeLevelInfo, Date openResultTime) {
		super();
		this.id = id;
		this.lotteryId = lotteryId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.chaseExecuted = chaseExecuted;
		this.isOpened = isOpened;
		this.winLotteryNumber = winLotteryNumber;
		this.openOperatorId = openOperatorId;
		this.state = state;
		this.stateUpdateTime = stateUpdateTime;
		this.openAffiche = openAffiche;
		this.currentIsuses = currentIsuses;
		this.totalSales = totalSales;
		this.prizeLevelInfo = prizeLevelInfo;
		this.openDateTime=openResultTime;
	}

	// Property accessors


	public String getPrizeLevelInfo() {
		return prizeLevelInfo;
	}

	public Isuses(Integer id, Integer lotteryId, String name, Date startTime,
			Date endTime, Boolean chaseExecuted, Boolean isOpened,
			String winLotteryNumber, Integer openOperatorId,
			LotteryTermStatus state, Date stateUpdateTime, String openAffiche,
			Integer currentIsuses, Integer totalSales, String prizeLevelInfo) {
		super();
		this.id = id;
		this.lotteryId = lotteryId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.chaseExecuted = chaseExecuted;
		this.isOpened = isOpened;
		this.winLotteryNumber = winLotteryNumber;
		this.openOperatorId = openOperatorId;
		this.state = state;
		this.stateUpdateTime = stateUpdateTime;
		this.openAffiche = openAffiche;
		this.currentIsuses = currentIsuses;
		this.totalSales = totalSales;
		this.prizeLevelInfo = prizeLevelInfo;
	}

	public void setPrizeLevelInfo(String prizeLevelInfo) {
		this.prizeLevelInfo = prizeLevelInfo;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLotteryId() {
		return this.lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getChaseExecuted() {
		return this.chaseExecuted;
	}

	public void setChaseExecuted(Boolean chaseExecuted) {
		this.chaseExecuted = chaseExecuted;
	}

	public Boolean getIsOpened() {
		return this.isOpened;
	}

	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}

	public String getWinLotteryNumber() {
		return this.winLotteryNumber;
	}

	public void setWinLotteryNumber(String winLotteryNumber) {
		this.winLotteryNumber = winLotteryNumber;
	}

	public Integer getOpenOperatorId() {
		return this.openOperatorId;
	}

	public void setOpenOperatorId(Integer openOperatorId) {
		this.openOperatorId = openOperatorId;
	}

	

	public LotteryTermStatus getState() {
		return state;
	}

	public void setState(LotteryTermStatus state) {
		this.state = state;
	}

	public Date getStateUpdateTime() {
		return this.stateUpdateTime;
	}

	public void setStateUpdateTime(Date stateUpdateTime) {
		this.stateUpdateTime = stateUpdateTime;
	}

	public String getOpenAffiche() {
		return this.openAffiche;
	}

	public void setOpenAffiche(String openAffiche) {
		this.openAffiche = openAffiche;
	}

	public Integer getCurrentIsuses() {
		return this.currentIsuses;
	}

	public void setCurrentIsuses(Integer currentIsuses) {
		this.currentIsuses = currentIsuses;
	}

	public Integer getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Integer totalSales) {
		this.totalSales = totalSales;
	}

	public Date getOpenDateTime() {
		return openDateTime;
	}

	public void setOpenDateTime(Date openDateTime) {
		this.openDateTime = openDateTime;
	}

	public Date getTerminalEndDateTime() {
		return terminalEndDateTime;
	}

	public void setTerminalEndDateTime(Date terminalEndDateTime) {
		this.terminalEndDateTime = terminalEndDateTime;
	}
	
	public List<PrizeBean> getPrizeBeanList() {
		return PrizeCommon.buildToList(this.lotteryId, this.prizeLevelInfo);
	}
	
	public String reBuildForGD11X5(){
		String temp=this.prizeLevelInfo;
		if(this.getLotteryId()==LotteryType.GD11X5.getValue()){
			List<PrizeBean> beans = getPrizeBeanList();
		
			for(PrizeBean bean :beans){
				temp = temp.replace(bean.getPrizeName(), bean.getPrizeNameLabel());
			}
		}
		return temp;
	}

}