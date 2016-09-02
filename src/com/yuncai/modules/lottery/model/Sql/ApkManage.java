package com.yuncai.modules.lottery.model.Sql;

/***
 * 客户端版本管理
 * @author TYH
 *
 */
public class ApkManage implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Integer packageId;//渠道ID
	private String apkName;//名称
	private String packageName;//包名
	private String apkSize;//apk大小
	private String apkPath;//apk路径,也可以说是内网下载地址
	private String netPath;//外网下载地址
	private Integer downType;//下载方式：0内网  1外网
	private String curVersion;//当前版本
	private String updVersion;//更新版本
	private String info;//简介
	private Integer isValid = 0;//客户端显示
	private Integer isForce = 0;//是不是强制更新
	private String addTime;//添加时间
	private String updTime;//修改时间
	private Integer versionCode = 0;//版本更新号
	private String versionName;//版本名称
	private Integer versionNameCode = 0;//子版本号
	
	public ApkManage() {
		super();
	}
	
	/*****************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getPackageId() {
		return packageId;
	}
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	public String getApkSize() {
		return apkSize;
	}
	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}
	public String getApkPath() {
		return apkPath;
	}
	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
	public String getNetPath() {
		return netPath;
	}
	public void setNetPath(String netPath) {
		this.netPath = netPath;
	}
	public Integer getDownType() {
		return downType;
	}
	public void setDownType(Integer downType) {
		this.downType = downType;
	}
	public String getCurVersion() {
		return curVersion;
	}
	public void setCurVersion(String curVersion) {
		this.curVersion = curVersion;
	}
	public String getUpdVersion() {
		return updVersion;
	}
	public void setUpdVersion(String updVersion) {
		this.updVersion = updVersion;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Integer getIsForce() {
		return isForce;
	}
	public void setIsForce(Integer isForce) {
		this.isForce = isForce;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Integer getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public Integer getVersionNameCode() {
		return versionNameCode;
	}

	public void setVersionNameCode(Integer versionNameCode) {
		this.versionNameCode = versionNameCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
}
