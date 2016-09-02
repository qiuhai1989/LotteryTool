package com.yuncai.modules.lottery.model.Oracle;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiuhai
 * 用户分享记录
 *
 */
public class UserShareRecord implements Serializable {

	private Integer id ;
	
	private String account;
	
	private String shareNo;//分享编号
	
	private String shareName;// 分享名称
	
	private Date saveTime;

	
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public UserShareRecord() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShareNo() {
		return shareNo;
	}

	public void setShareNo(String shareNo) {
		this.shareNo = shareNo;
	}

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	
	
	
	
	
}
