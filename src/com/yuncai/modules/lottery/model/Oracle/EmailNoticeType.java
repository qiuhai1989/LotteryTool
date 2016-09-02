package com.yuncai.modules.lottery.model.Oracle;

public class EmailNoticeType implements java.io.Serializable{

	/**
	 * 系统邮件通知：类型管理
	 * @author gx
	 * Dec 24, 2014 11:43:56 AM
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int value;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
