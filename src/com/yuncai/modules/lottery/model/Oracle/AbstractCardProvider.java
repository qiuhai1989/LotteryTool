package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class AbstractCardProvider implements java.io.Serializable{

	private Integer id;
	
	private String name;
	
	private String deramk;
	
	private Date createDateTime;
	//绑定账户名
	private String account;
	//绑定账户id
	private Integer memberId;
	//是否返点
	private Integer isfd;
	//返点比例：0-100
	private Integer fdpercent;
	
	
	
	
	public AbstractCardProvider() {
		super();
	}
	public AbstractCardProvider(Integer id, String name, String deramk, Date createDateTime, String account, Integer memberId, Integer isfd,
			Integer fdpercent) {
		super();
		this.id = id;
		this.name = name;
		this.deramk = deramk;
		this.createDateTime = createDateTime;
		this.account = account;
		this.memberId = memberId;
		this.isfd = isfd;
		this.fdpercent = fdpercent;
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
	public String getDeramk() {
		return deramk;
	}
	public void setDeramk(String deramk) {
		this.deramk = deramk;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getIsfd() {
		return isfd;
	}
	public void setIsfd(Integer isfd) {
		this.isfd = isfd;
	}
	public Integer getFdpercent() {
		return fdpercent;
	}
	public void setFdpercent(Integer fdpercent) {
		this.fdpercent = fdpercent;
	}
	
	
	
	
}
