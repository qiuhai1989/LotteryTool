package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/***
 * 客户端push管理
 * @author gx
 *
 */
public class ApkPush implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int isValid;//客户端显示
	private String name;//PUSH名称（不需要传送）
	private String pushAd;//push广告语
	private int type;//连接类型：0站内  1站外 2文本
	private int pushContent;//站内:2 合买 3 充值 4 中奖单 5 彩种 6 钱包活动界面 站外:7 调用浏览器访问URL 文本:1 显示内容
	private String pushTime;//push时间
	private String pushSpace;//push间隔
	private int pushNum;//push次数
	private String updcode;//更新标识，更新时间转换成唯一的字符串
	private Date addtime;//添加时间
	private Date updtime;//更新时间
	private String bak;//备注
	private int sendGroup;//发送分组单项：0全部1渠道2用户
	private String receiveUser;//接收对象：按分组判断，0锁空1渠道多选分号隔开2用户登陆帐号
	private int pushNo;//公告编号，唯一
	private String address;
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
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPushAd() {
		return pushAd;
	}
	public void setPushAd(String pushAd) {
		this.pushAd = pushAd;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
	public String getPushSpace() {
		return pushSpace;
	}
	public void setPushSpace(String pushSpace) {
		this.pushSpace = pushSpace;
	}
	public int getPushNum() {
		return pushNum;
	}
	public void setPushNum(int pushNum) {
		this.pushNum = pushNum;
	}
	public String getUpdcode() {
		return updcode;
	}
	public void setUpdcode(String updcode) {
		this.updcode = updcode;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getUpdtime() {
		return updtime;
	}
	public void setUpdtime(Date updtime) {
		this.updtime = updtime;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public int getSendGroup() {
		return sendGroup;
	}
	public void setSendGroup(int sendGroup) {
		this.sendGroup = sendGroup;
	}
	public int getPushNo() {
		return pushNo;
	}
	public void setPushNo(int pushNo) {
		this.pushNo = pushNo;
	}
	public int getPushContent() {
		return pushContent;
	}
	public void setPushContent(int pushContent) {
		this.pushContent = pushContent;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
