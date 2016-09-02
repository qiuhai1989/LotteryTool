package com.yuncai.modules.lottery.model.Oracle;

/***
 * 客户端版本管理补充
 * @author gx
 *
 */
public class ApkManageSupplement implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String qrCode;//二维码
	private String testQrCode;//测试二维码
	private String url;//路径
	private String bak;//说明
	private int channelid;//渠道id
	private String packageid;//渠道名称channel201
	
	/*****************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getTestQrCode() {
		return testQrCode;
	}
	public void setTestQrCode(String testQrCode) {
		this.testQrCode = testQrCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	public int getChannelid() {
		return channelid;
	}
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}
	public String getPackageid() {
		return packageid;
	}
	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}
	
}
