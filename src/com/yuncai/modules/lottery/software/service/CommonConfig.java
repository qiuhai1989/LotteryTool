package com.yuncai.modules.lottery.software.service;

public class CommonConfig {
	private String agentId;  //商务维一编号
	private String key;      //加密串key
	private String sellerId;  //代理账号
	private String status;  //判断0投注时登录1否  是否带密码投注
	private String isGzip;  //0判断是1与null否   是否带压缩
	private String isVersion;  //0是1否null     是否带版本
	
	private String gentypeIn; //购彩类型
	
	private String resultPath;  //投注是否返回路径 0是,1否
	
	
	public String getResultPath() {
		return resultPath;
	}
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}
	public String getGentypeIn() {
		return gentypeIn;
	}
	public void setGentypeIn(String gentypeIn) {
		this.gentypeIn = gentypeIn;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsGzip() {
		return isGzip;
	}
	public void setIsGzip(String isGzip) {
		this.isGzip = isGzip;
	}
	public String getIsVersion() {
		return isVersion;
	}
	public void setIsVersion(String isVersion) {
		this.isVersion = isVersion;
	}

}
