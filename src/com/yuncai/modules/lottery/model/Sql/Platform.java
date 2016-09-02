package com.yuncai.modules.lottery.model.Sql;

public class Platform  implements java.io.Serializable {

	private Integer id;
	private String name;//平台名
	private String agentId;//代理商id
	private String key;//验证码
	
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
