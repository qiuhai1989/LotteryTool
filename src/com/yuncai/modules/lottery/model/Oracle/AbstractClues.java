package com.yuncai.modules.lottery.model.Oracle;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yuncai.core.util.DBHelper;


public class AbstractClues implements java.io.Serializable{
	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 10:28:58 AM
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer effective=0;// 有效性,默认无效
	private Integer type;// 类型，0弹窗 1页面
	private String name;// 名称
	private String content;// 内容
	private String location;// w位置
	private Date updateTime;//修改时间
	private Integer code;
	private String note;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEffective() {
		return effective;
	}
	public void setEffective(Integer effective) {
		this.effective = effective;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTime(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sDateFormat.format(getUpdateTime()); 
		return DBHelper.formatTime(time);
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
