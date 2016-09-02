package com.yuncai.modules.lottery.software.service;

public class VersionBean {
	private int index;
	private String url;  
	private String test; //表示测试的版本
	private String instruction;// 说明
	void VersionBean(){
		index=0;
		url =null;
		test=null;
		instruction=null;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

}
