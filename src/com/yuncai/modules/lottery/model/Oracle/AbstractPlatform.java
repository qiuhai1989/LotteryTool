package com.yuncai.modules.lottery.model.Oracle;

public class AbstractPlatform implements java.io.Serializable {
	private Integer id;
	private String name;//平台名
	private String agentId;//代理商id
	private String key;//验证码
	
	
	public AbstractPlatform() {
	}
	
	public AbstractPlatform(Integer id,String name,String agentId,String key){
		this.id=id;
		this.name=name;
		this.agentId=agentId;
		this.key=key;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

}
