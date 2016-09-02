package com.yuncai.modules.lottery.model.Sql;

public class UserPropose implements java.io.Serializable{

	private Integer id;
	//用户账号
	private String account;
	//用户名(用户选填)
	private String userName;
	//用户邮箱(用户选填)
	private String mail;
	//用户手机号(用户选填)
	private String mobile;
	//用户建议
	private String propose;
	private String model; 
	
	private String createDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPropose() {
		return propose;
	}
	public void setPropose(String propose) {
		this.propose = propose;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
