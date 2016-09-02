package com.yuncai.modules.lottery.model.Oracle;

public class AbstractIsusesMange implements java.io.Serializable{

	/**
	 * 彩种管理
	 * @author gx
	 * Dec 13, 2013 3:44:38 PM
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int isShow;//前端显示1；0不显示
	private int isBetting;//投注1 ，0不投注
	private int sorder;//顺序
	private String name;//彩种名称
	private String address;//彩种入口地址
	private String msg;//彩种信息
	private String content;//彩种广告语
	private String alertMsg;//关闭投注时的弹窗信息
	private String updateTime;//修改时间
	private int platformID;//平台id
	private String logo;
	private int isusesID;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	public int getIsBetting() {
		return isBetting;
	}
	public void setIsBetting(int isBetting) {
		this.isBetting = isBetting;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getPlatformID() {
		return platformID;
	}
	public void setPlatformID(int platformID) {
		this.platformID = platformID;
	}
	public int getSorder() {
		return sorder;
	}
	public void setSorder(int sorder) {
		this.sorder = sorder;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getIsusesID() {
		return isusesID;
	}
	public void setIsusesID(int isusesID) {
		this.isusesID = isusesID;
	}
	public String getAlertMsg() {
		return alertMsg;
	}
	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}
	
	
}
