package com.yuncai.modules.lottery.software.service;

public class XFBConfig {
	private String channelId ;//渠道id
	private String returnAddress ; //回调地址
	private String deductPercentAddress; //获取手续费率地址
	
	public String getDeductPercentAddress() {
		return deductPercentAddress;
	}
	public void setDeductPercentAddress(String deductPercentAddress) {
		this.deductPercentAddress = deductPercentAddress;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getReturnAddress() {
		return returnAddress;
	}
	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}
	

}
