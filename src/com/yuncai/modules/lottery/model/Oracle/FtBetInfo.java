package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;


/**投注比例
 * FtBetInfo entity. @author MyEclipse Persistence Tools
 */

public class FtBetInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mid;
	private String winPercent;
	private String drawPercent;
	private String lostPercent;
	private String winAvgRate;
	private String drawAvgRate;
	private String lostAvgRate;
	private Integer isFirst;
	private Date time;
	private String nwinPercent;
	private String ndrawPercent;
	private String nlostPercent;

	// Constructors

	/** default constructor */
	public FtBetInfo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getWinPercent() {
		return this.winPercent;
	}

	public void setWinPercent(String winPercent) {
		this.winPercent = winPercent;
	}

	public String getDrawPercent() {
		return this.drawPercent;
	}

	public void setDrawPercent(String drawPercent) {
		this.drawPercent = drawPercent;
	}

	public String getLostPercent() {
		return this.lostPercent;
	}

	public void setLostPercent(String lostPercent) {
		this.lostPercent = lostPercent;
	}

	public Integer getIsFirst() {
		return this.isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getWinAvgRate() {
		return winAvgRate;
	}

	public void setWinAvgRate(String winAvgRate) {
		this.winAvgRate = winAvgRate;
	}

	public String getDrawAvgRate() {
		return drawAvgRate;
	}

	public void setDrawAvgRate(String drawAvgRate) {
		this.drawAvgRate = drawAvgRate;
	}

	public String getLostAvgRate() {
		return lostAvgRate;
	}

	public void setLostAvgRate(String lostAvgRate) {
		this.lostAvgRate = lostAvgRate;
	}
	
	public String getNwinPercent() {
		return nwinPercent;
	}

	public void setNwinPercent(String nwinPercent) {
		this.nwinPercent = nwinPercent;
	}

	public String getNdrawPercent() {
		return ndrawPercent;
	}

	public void setNdrawPercent(String ndrawPercent) {
		this.ndrawPercent = ndrawPercent;
	}

	public String getNlostPercent() {
		return nlostPercent;
	}

	public void setNlostPercent(String nlostPercent) {
		this.nlostPercent = nlostPercent;
	}

	public FtBetInfo(Integer mid, String winPercent, String drawPercent,
			String lostPercent, String winAvgRate, String drawAvgRate,
			String lostAvgRate, Integer isFirst, Date time) {
		super();
		this.mid = mid;
		this.winPercent = winPercent;
		this.drawPercent = drawPercent;
		this.lostPercent = lostPercent;
		this.winAvgRate = winAvgRate;
		this.drawAvgRate = drawAvgRate;
		this.lostAvgRate = lostAvgRate;
		this.isFirst = isFirst;
		this.time = time;
	}
	
	public void setFtBetInfo(Integer mid, String winPercent, String drawPercent,
			String lostPercent, String winAvgRate, String drawAvgRate,
			String lostAvgRate, Integer isFirst, Date time) {
		this.mid = mid;
		this.winPercent = winPercent;
		this.drawPercent = drawPercent;
		this.lostPercent = lostPercent;
		this.winAvgRate = winAvgRate;
		this.drawAvgRate = drawAvgRate;
		this.lostAvgRate = lostAvgRate;
		this.isFirst = isFirst;
		this.time = time;
	}
}