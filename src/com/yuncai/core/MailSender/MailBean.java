package com.yuncai.core.MailSender;

public class MailBean {
	private String serverHost; //服务器名称
	private String userName; //用户名
	private String passWrod; //密码
	private String toAddress;
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWrod() {
		return passWrod;
	}
	public void setPassWrod(String passWrod) {
		this.passWrod = passWrod;
	}
}
