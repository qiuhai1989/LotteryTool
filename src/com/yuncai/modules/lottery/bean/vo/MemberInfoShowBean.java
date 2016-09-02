package com.yuncai.modules.lottery.bean.vo;

public class MemberInfoShowBean {

	private String account;
	private String name;
	private String certNo;
	private String email;
	private String phone;
	private String bankCard;
	private  String address;
	private String part;
	private String bankPart;
	
	
	
	
	public MemberInfoShowBean(String account, String name, String certNo, String email, String phone, String bankCard, String bankPart,String part) {
		super();
		this.account = account;
		this.name = name;
		this.certNo = certNo;
		this.email = email;
		this.phone = phone;
		this.bankCard = bankCard;
		this.bankPart = bankPart;
		if(bankPart!=null){
			String[] strs = bankPart.split("\\|");
//			this.address = strs[0]+strs[1];
			this.address=bankPart;
			this.part=strs[2];
		}
		if(part!=null){
			this.part = part;
		}
		
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getBankPart() {
		return bankPart;
	}
	public void setBankPart(String bankPart) {
		this.bankPart = bankPart;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return account+"---"+name+"---"+certNo+"---"+email+"---"+phone+"---"+bankCard+"---"+address+"---"+bankPart;
//	}
	
	
	
	
	
}
