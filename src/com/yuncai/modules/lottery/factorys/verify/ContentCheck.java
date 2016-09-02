package com.yuncai.modules.lottery.factorys.verify;
import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

public class ContentCheck {
	public int multiple;

	public String isAttribute;

	public PlayType playType;

	public String content;

	private Date firstTime;
	
	private Date lateTime;

	public Date getLateTime() {
		return lateTime;
	}

	public void setLateTime(Date lateTime) {
		this.lateTime = lateTime;
	}

	// 是否通过
	private boolean isPass;
	
	private String message;
	private int count; //注数

	public String getIsAttribute() {
		return isAttribute;
	}

	public void setIsAttribute(String isAttribute) {
		this.isAttribute = isAttribute;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
