package com.yuncai.modules.lottery.model.Sql;

public class Users {
	private int id;
	//用户名
	private String name;
	//真实姓名
	private String realityName;
	//pwd
	private String password;
	private String mobile;
	//等级
	private String level;
	private String certNo;//身份证号码
	private String email;
	
	private String bankName;
	
	private String alipayName;//支付宝账号
	
	private String bandCardNumber;//银行卡号
	
	private Double balance;//账户余额
	
	private Double freeze;//冻结余额
	
	private  Integer siteID;
	
	private Boolean isEmailValided;
	
	private Boolean isMobileValided;
	
	private Integer buySendSms;
	
	private String phoneNo;
	
	private Boolean isPhoneBinding;
	
	private String nickName; //昵称
	
	private String mobilBelongingTo;//号码归属地
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Boolean getIsPhoneBinding() {
		return isPhoneBinding;
	}
	public void setIsPhoneBinding(Boolean isPhoneBinding) {
		this.isPhoneBinding = isPhoneBinding;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getBuySendSms() {
		return buySendSms;
	}
	public void setBuySendSms(Integer buySendSms) {
		this.buySendSms = buySendSms;
	}
	public Boolean getIsEmailValided() {
		return isEmailValided;
	}
	public Boolean getIsMobileValided() {
		return isMobileValided;
	}
	public void setIsEmailValided(Boolean isEmailValided) {
		this.isEmailValided = isEmailValided;
	}
	public void setIsMobileValided(Boolean isMobileValided) {
		this.isMobileValided = isMobileValided;
	}
	public Integer getSiteID() {
		return siteID;
	}
	public void setSiteID(Integer siteID) {
		this.siteID = siteID;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getFreeze() {
		return freeze;
	}
	public void setFreeze(Double freeze) {
		this.freeze = freeze;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAlipayName() {
		return alipayName;
	}
	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}
	public String getBandCardNumber() {
		return bandCardNumber;
	}
	public void setBandCardNumber(String bandCardNumber) {
		this.bandCardNumber = bandCardNumber;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealityName() {
		return realityName;
	}
	public void setRealityName(String realityName) {
		this.realityName = realityName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMobilBelongingTo() {
		return mobilBelongingTo;
	}
	public void setMobilBelongingTo(String mobilBelongingTo) {
		this.mobilBelongingTo = mobilBelongingTo;
	}

}
