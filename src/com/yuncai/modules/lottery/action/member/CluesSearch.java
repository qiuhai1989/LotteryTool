package com.yuncai.modules.lottery.action.member;

public class CluesSearch {
	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 10:57:19 AM
	 */
	private String startDate;//开始时间
	private String endDate;//结束时间
	private int type;
	private String name;// 名称
	private String content;// 内容
	private String location;// w位置
	
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
