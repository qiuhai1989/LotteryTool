package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;


/**
 * 竞彩篮球投注比例和平均赔率 entity. 
 * @author blackworm
 */

public class BkBetRateRatio implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mid;
	private String sfMainPercent;//胜负-主胜-投注比例
	private String sfGuestPercent;//胜负-客胜-投注比例
	private String rfsfMainPercent;//让分胜负-主胜-投注比例
	private String rfsfGuestPercent;//让分胜负-客胜-投注比例
	private String dxfdPercent;//大小分-大-投注比例
	private String dxfxPercent;//大小分-小-投注比例
	private String mainAvgRate;//主胜-平均赔率
	private String guestAvgRate;//客胜-平均赔率
	private Date fetchTime;//抓取时间

	// Constructors

	/** default constructor */
	public BkBetRateRatio() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getSfMainPercent() {
		return sfMainPercent;
	}

	public void setSfMainPercent(String sfMainPercent) {
		this.sfMainPercent = sfMainPercent;
	}

	public String getSfGuestPercent() {
		return sfGuestPercent;
	}

	public void setSfGuestPercent(String sfGuestPercent) {
		this.sfGuestPercent = sfGuestPercent;
	}

	public String getRfsfMainPercent() {
		return rfsfMainPercent;
	}

	public void setRfsfMainPercent(String rfsfMainPercent) {
		this.rfsfMainPercent = rfsfMainPercent;
	}

	public String getRfsfGuestPercent() {
		return rfsfGuestPercent;
	}

	public void setRfsfGuestPercent(String rfsfGuestPercent) {
		this.rfsfGuestPercent = rfsfGuestPercent;
	}

	public String getDxfdPercent() {
		return dxfdPercent;
	}

	public void setDxfdPercent(String dxfdPercent) {
		this.dxfdPercent = dxfdPercent;
	}

	public String getDxfxPercent() {
		return dxfxPercent;
	}

	public void setDxfxPercent(String dxfxPercent) {
		this.dxfxPercent = dxfxPercent;
	}

	public String getMainAvgRate() {
		return mainAvgRate;
	}

	public void setMainAvgRate(String mainAvgRate) {
		this.mainAvgRate = mainAvgRate;
	}

	public String getGuestAvgRate() {
		return guestAvgRate;
	}

	public void setGuestAvgRate(String guestAvgRate) {
		this.guestAvgRate = guestAvgRate;
	}

	public Date getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(Date fetchTime) {
		this.fetchTime = fetchTime;
	}

	public BkBetRateRatio(Integer mid, String sfMainPercent,
			String sfGuestPercent, String rfsfMainPercent,
			String rfsfGuestPercent, String dxfdPercent, String dxfxPercent,
			String mainAvgRate, String guestAvgRate, Date fetchTime) {
		super();
		this.mid = mid;
		this.sfMainPercent = sfMainPercent;
		this.sfGuestPercent = sfGuestPercent;
		this.rfsfMainPercent = rfsfMainPercent;
		this.rfsfGuestPercent = rfsfGuestPercent;
		this.dxfdPercent = dxfdPercent;
		this.dxfxPercent = dxfxPercent;
		this.mainAvgRate = mainAvgRate;
		this.guestAvgRate = guestAvgRate;
		this.fetchTime = fetchTime;
	}

	@Override
	public String toString() {
		return "BkBetRateRatio [id=" + id + ", mid=" + mid + ", sfMainPercent="
				+ sfMainPercent + ", sfGuestPercent=" + sfGuestPercent
				+ ", rfsfMainPercent=" + rfsfMainPercent
				+ ", rfsfGuestPercent=" + rfsfGuestPercent + ", dxfdPercent="
				+ dxfdPercent + ", dxfxPercent=" + dxfxPercent
				+ ", mainAvgRate=" + mainAvgRate + ", guestAvgRate="
				+ guestAvgRate + ", fetchTime=" + fetchTime + "]";
	}

}