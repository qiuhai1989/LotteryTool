package com.yuncai.modules.lottery.software.service;
import org.jdom.Element;

public class HeadResposnseBean {
	
	private String messageId;
	private String agentId;
	private String digest;
	private String channelId;
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public void elementToObje(Element ele) {
		this.messageId = ele.getChild("messageId").getText();
		this.agentId = ele.getChild("agentId").getText();
		this.digest = ele.getChild("digest").getText();
		this.channelId=ele.getChild("channelId").getText();

	}

}
