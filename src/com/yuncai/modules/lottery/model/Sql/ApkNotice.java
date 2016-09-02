package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/***
 * 客户端公告管理
 * @author gx
 *
 */
public class ApkNotice implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String noticeName;//公告名称
	private String noticeContent;//公告内容
	private Integer isValid;//客户端显示否
	private Date issueTime;//发布时间
	private Date endTime;//截止时间
	private int sendGroup;//发送分组单项：0全部1渠道2用户
	private String receiveUser;//接收对象：按分组判断，0锁空 1渠道多选分号隔开 2用户登陆帐号
	private int noticeNo;//公告编号，唯一
	private String updcode;//更新标识，更新时间转换成唯一的字符串
	private int type;//连接类型：0站内，1站外，2文本
	private int pushContent;//站内:2 合买 3 充值 4 中奖单 5 彩种 6 钱包活动界面 站外:7 调用浏览器访问URL 文本:1 显示内容
	private String address;//地址
	private String pageId;// 页面编号
	
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	/**************************华丽的分割线*****************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Date getIssueTime() {
		return issueTime;
	}
	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public int getSendGroup() {
		return sendGroup;
	}
	public void setSendGroup(int sendGroup) {
		this.sendGroup = sendGroup;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public String getUpdcode() {
		return updcode;
	}
	public void setUpdcode(String updcode) {
		this.updcode = updcode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPushContent() {
		return pushContent;
	}
	public void setPushContent(int pushContent) {
		this.pushContent = pushContent;
	}
	public int getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
