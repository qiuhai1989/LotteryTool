package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryTermStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

public class AbstractLotteryTerm implements java.io.Serializable{
	// Fields
	// 无逻辑id
	private Integer id;
	// 彩种
	private LotteryType lotteryType;
	// 彩期
	private String term;
	// 外部彩期
	private String outTerm;
	// 开奖时间
	private Date openDateTime;
	// 开始时间
	private Date startDateTime;
	// 结束时间
	private Date endDateTime;
	// 终端截止时间
	private Date terminalEndDateTime;
	// 是否可用
	private CommonStatus isAble;
	// 是否当前期
	private CommonStatus isCurrentTerm;
	// 彩期状态
	private LotteryTermStatus status;
	// 开奖结果
	private String result;
	// 开奖描述 奖级^中奖注数^每注奖金
	private String resultDetail;
	// 奖池
	private String awardPool;
	// 投注额
	private String totalAmount;

	private CommonStatus isBooking;

	// Constructors

	/** default constructor */
	public AbstractLotteryTerm() {
	}

	/** minimal constructor */
	public AbstractLotteryTerm(Integer id, LotteryType lotteryType, String term, CommonStatus isAble, CommonStatus isCurrentTerm,
			LotteryTermStatus status) {
		this.id = id;
		this.lotteryType = lotteryType;
		this.term = term;
		this.isAble = isAble;
		this.isCurrentTerm = isCurrentTerm;
		this.status = status;
	}

	/** full constructor */
	public AbstractLotteryTerm(Integer id, LotteryType lotteryType, String term, Date openDateTime, Date startDateTime, Date endDateTime,
			Date terminalEndDateTime, CommonStatus isAble, CommonStatus isCurrentTerm, LotteryTermStatus status, String result, String resultDetail,
			String awardPool) {
		this.id = id;
		this.lotteryType = lotteryType;
		this.term = term;
		this.openDateTime = openDateTime;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.terminalEndDateTime = terminalEndDateTime;
		this.isAble = isAble;
		this.isCurrentTerm = isCurrentTerm;
		this.status = status;
		this.result = result;
		this.resultDetail = resultDetail;
		this.awardPool = awardPool;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getOutTerm() {
		return outTerm;
	}

	public void setOutTerm(String outTerm) {
		this.outTerm = outTerm;
	}

	public Date getOpenDateTime() {
		return openDateTime;
	}

	public void setOpenDateTime(Date openDateTime) {
		this.openDateTime = openDateTime;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Date getTerminalEndDateTime() {
		return terminalEndDateTime;
	}

	public void setTerminalEndDateTime(Date terminalEndDateTime) {
		this.terminalEndDateTime = terminalEndDateTime;
	}

	public CommonStatus getIsAble() {
		return isAble;
	}

	public void setIsAble(CommonStatus isAble) {
		this.isAble = isAble;
	}

	public CommonStatus getIsCurrentTerm() {
		return isCurrentTerm;
	}

	public void setIsCurrentTerm(CommonStatus isCurrentTerm) {
		this.isCurrentTerm = isCurrentTerm;
	}

	public LotteryTermStatus getStatus() {
		return status;
	}

	public void setStatus(LotteryTermStatus status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public String getAwardPool() {
		return awardPool;
	}

	public void setAwardPool(String awardPool) {
		this.awardPool = awardPool;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public CommonStatus getIsBooking() {
		return isBooking;
	}

	public void setIsBooking(CommonStatus isBooking) {
		this.isBooking = isBooking;
	}

}
