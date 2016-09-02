package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * Sms entity. @author MyEclipse Persistence Tools
 */

public class Sms implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer siteId;
	private Integer smsid;
	private String from;
	private String to;
	private Date dateTime;
	private String content;
	private Boolean isSent;
	// Constructors

	/** default constructor */
	public Sms() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSmsid() {
		return smsid;
	}

	public void setSmsid(Integer smsid) {
		this.smsid = smsid;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsSent() {
		return isSent;
	}

	public void setIsSent(Boolean isSent) {
		this.isSent = isSent;
	}

	public Sms(Integer siteId, Integer smsid, String from, String to, Date dateTime, String content, Boolean isSent) {
		super();
		this.siteId = siteId;
		this.smsid = smsid;
		this.from = from;
		this.to = to;
		this.dateTime = dateTime;
		this.content = content;
		this.isSent = isSent;
	}

	/** full constructor */

	// Property accessors
	
	
}