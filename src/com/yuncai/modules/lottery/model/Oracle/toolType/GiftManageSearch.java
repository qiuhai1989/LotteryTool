package com.yuncai.modules.lottery.model.Oracle.toolType;


import java.util.Date;

import com.yuncai.core.tools.DateTools;

/***
 * 红包查询条件
 * @author TYH
 *
 */
public class GiftManageSearch {

	private int platformId = -1;//平台
	private int channelId = -1;//渠道
	private String giftName = "";//名称
	private String startDate;//开始时间
	private String endDate;//结束时间
	
	/******************/
	public int getPlatformId() {
		return platformId;
	}
	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate1() {
		if (this.startDate != null && !"".equals(startDate)) {
			return DateTools.StringToDate(startDate + " 00:00:00");
		}
		return null;
	}

	public Date getEndDate1() {
		if (this.endDate != null && !"".equals(endDate)) {
			return DateTools.StringToDate(endDate + " 23:59:59");
		}
		return null;
	}
}
	
