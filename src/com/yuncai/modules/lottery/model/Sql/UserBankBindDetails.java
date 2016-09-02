package com.yuncai.modules.lottery.model.Sql;

public class UserBankBindDetails {
	private Integer UserID;
	private Integer bankType;//银行类型
	private String bankName;//支行名称
	private String bankCardNumber;
	private String bankInProvinceName;
	private String bankInCityName;
	private String bankUserName;//持卡人姓名
	private String BankTypeName;//银行开户类型

	public Integer getUserID() {
		return UserID;
	}
	public void setUserID(Integer userID) {
		UserID = userID;
	}
	public Integer getBankType() {
		return bankType;
	}
	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCardNumber() {
		return bankCardNumber;
	}
	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}
	public String getBankInProvinceName() {
		return bankInProvinceName;
	}
	public void setBankInProvinceName(String bankInProvinceName) {
		this.bankInProvinceName = bankInProvinceName;
	}
	public String getBankInCityName() {
		return bankInCityName;
	}
	public void setBankInCityName(String bankInCityName) {
		this.bankInCityName = bankInCityName;
	}
	public String getBankUserName() {
		return bankUserName;
	}
	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}
	public String getBankTypeName() {
		return BankTypeName;
	}
	public void setBankTypeName(String bankTypeName) {
		BankTypeName = bankTypeName;
	}
	
	
	
}
