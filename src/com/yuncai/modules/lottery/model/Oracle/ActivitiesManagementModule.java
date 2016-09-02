package com.yuncai.modules.lottery.model.Oracle;

/**
 * 活动模块管理实体类
 * @author gx
 * Apr 9, 2014 5:52:42 PM
 */
public class ActivitiesManagementModule {
	private int id;
	private int sorde;//位置
	private int module;//模块位置 初始化 1=个人中心 2=购彩大厅
	private int version;//内部版本号
	private int type;//类型 
	private int effective;//是否有效 默认0无效 不传送给客服端
	private int isHide;//是否隐藏 默认0不隐藏
	private String updcode;//更新标识，更新时间转换成唯一的字符串
	private String name;//活动名称
	private String logo;//logo的url
	private String msg;//活动信息（只针对购彩大厅）
	private String ad;//广告语（只针对购彩大厅）
	private String channel;//多选，隔开 channel202,channel201
	private String packageName;//模块包名
	private String address;//模块地址
	private String psize;//包大小 1.01M
	private String pno;//活动编号
	
/************************************华丽的分割线************************************/	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSorde() {
		return sorde;
	}
	public void setSorde(int sorde) {
		this.sorde = sorde;
	}
	public int getModule() {
		return module;
	}
	public void setModule(int module) {
		this.module = module;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getEffective() {
		return effective;
	}
	public void setEffective(int effective) {
		this.effective = effective;
	}
	public int getIsHide() {
		return isHide;
	}
	public void setIsHide(int isHide) {
		this.isHide = isHide;
	}
	public String getUpdcode() {
		return updcode;
	}
	public void setUpdcode(String updcode) {
		this.updcode = updcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPsize() {
		return psize;
	}
	public void setPsize(String psize) {
		this.psize = psize;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	
	
	
}
