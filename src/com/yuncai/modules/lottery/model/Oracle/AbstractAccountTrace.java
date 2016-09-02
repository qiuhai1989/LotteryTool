package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class AbstractAccountTrace implements java.io.Serializable{
	private Integer id;
	private String account;
	private Integer root;
	private String ip;
	private String channel;
	private Integer lotteryType;
	private String pageId;
	private String sessionId;
	private Date createDateTime;
	private String terminalId;// 终端机身唯一码或者网卡MAC地址
	private String systemVersion;//终端系统版本号
	private String model;//终端品牌和型号
	private String softwareVersion;//客户端内部版本号（code）
	private String browser;//浏览器和版本号
	private String operator;//用户使用网络的运营商名称
	
	public AbstractAccountTrace() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbstractAccountTrace(Integer id, String account, Integer root, String ip, String channel, Integer lotteryType, String pageId,
			String sessionId, Date createDateTime, String terminalId, String systemVersion, String model, String softwareVersion, String browser,
			String operator) {
		super();
		this.id = id;
		this.account = account;
		this.root = root;
		this.ip = ip;
		this.channel = channel;
		this.lotteryType = lotteryType;
		this.pageId = pageId;
		this.sessionId = sessionId;
		this.createDateTime = createDateTime;
		this.terminalId = terminalId;
		this.systemVersion = systemVersion;
		this.model = model;
		this.softwareVersion = softwareVersion;
		this.browser = browser;
		this.operator = operator;
	}

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

	public Integer getRoot() {
		return root;
	}

	public void setRoot(Integer root) {
		this.root = root;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
