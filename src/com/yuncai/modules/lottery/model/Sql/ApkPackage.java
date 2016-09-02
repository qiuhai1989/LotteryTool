package com.yuncai.modules.lottery.model.Sql;

/***
 * 客户端渠道管理
 * @author TYH
 *
 */
public class ApkPackage implements java.io.Serializable{

	private int id;
	private String packageName;//渠道名称
	private String packageCode;//渠道编码
	private String bak;//备注
	private Integer isValid;//客户端显示否
	
	public ApkPackage() {
		super();
	}
	
	public ApkPackage(String packageName, String packageCode, String bak,
			Integer isValid) {
		super();
		this.packageName = packageName;
		this.packageCode = packageCode;
		this.bak = bak;
		this.isValid = isValid;
	}

	/*******************************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	
}
