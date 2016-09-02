package com.yuncai.modules.lottery.model.Sql;

public class LotteryTypeProps {
	
	// Fields
	// 彩种
	private Integer id;
	private Integer lotteryType;
	// 每包数量
	private Integer countInBatch;
	// 打包等待时间
	private Integer maxWaitTime;
	// 单式提前截止时间
	private Integer dsAheadTime;
	// 复式提前截止时间
	private Integer fsAheadTime;
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Integer getCountInBatch() {
		return countInBatch;
	}
	public void setCountInBatch(Integer countInBatch) {
		this.countInBatch = countInBatch;
	}
	public Integer getMaxWaitTime() {
		return maxWaitTime;
	}
	public void setMaxWaitTime(Integer maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	public Integer getDsAheadTime() {
		return dsAheadTime;
	}
	public void setDsAheadTime(Integer dsAheadTime) {
		this.dsAheadTime = dsAheadTime;
	}
	public Integer getFsAheadTime() {
		return fsAheadTime;
	}
	public void setFsAheadTime(Integer fsAheadTime) {
		this.fsAheadTime = fsAheadTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	// Constructors

	

}
