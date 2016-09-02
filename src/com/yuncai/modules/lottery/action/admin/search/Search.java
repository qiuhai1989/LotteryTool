package com.yuncai.modules.lottery.action.admin.search;

/**
 * 基本的公共查询条件
 * @author TYH
 *
 */
public class Search {

	private String name = "";//名称
	private String content = "";//内容
	private String startDate = "";//开始时间
	private String endDate = "";//结束时间
	private String startDate1 = "";//开始时间
	private String endDate1 = "";//结束时间
	private int pid = -1;
	private String updVersion;
	private int platformId;
	private int channelId;
	private Integer noticeNo;
	private int type=-1;
	private int sendGroup=-1;
	private String pushAd;
	/************************************/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getUpdVersion() {
		return updVersion;
	}
	public void setUpdVersion(String updVersion) {
		this.updVersion = updVersion;
	}
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
	public String getStartDate1() {
		return startDate1;
	}
	public void setStartDate1(String startDate1) {
		this.startDate1 = startDate1;
	}
	public String getEndDate1() {
		return endDate1;
	}
	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPushAd() {
		return pushAd;
	}
	public void setPushAd(String pushAd) {
		this.pushAd = pushAd;
	}
	public int getSendGroup() {
		return sendGroup;
	}
	public void setSendGroup(int sendGroup) {
		this.sendGroup = sendGroup;
	}
	public Integer getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(Integer noticeNo) {
		this.noticeNo = noticeNo;
	}
	
}
