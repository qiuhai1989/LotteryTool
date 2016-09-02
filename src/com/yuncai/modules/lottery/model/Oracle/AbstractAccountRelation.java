package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class AbstractAccountRelation implements java.io.Serializable {
	private Integer Id;
	private String account;//账号
	private String parentAccount;//父级账号
	private Integer levelId;//级别
	private Date creTime;//创建时间
	private String recommender;//推荐人编号
		
	public AbstractAccountRelation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AbstractAccountRelation(Integer id, String account, String parentAccount, Integer levelId, Date creTime) {
		super();
		Id = id;
		this.account = account;
		this.parentAccount = parentAccount;
		this.levelId = levelId;
		this.creTime = creTime;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getParentAccount() {
		return parentAccount;
	}
	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Date getCreTime() {
		return creTime;
	}
	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}
	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
}
