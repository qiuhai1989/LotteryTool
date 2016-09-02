package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * SchemesNumber entity. @author MyEclipse Persistence Tools
 */

public class SchemesNumber implements java.io.Serializable {

	private Integer id;
	//创建时间
	private Date dateTime;
	//方案ID
	private Integer schemeId;
	//方案金额
	private Double money;
	//倍数
	private Integer multiple;
	//内容
	private String lotteryNumber;
	//封装竞彩数据内容
	private String lotteryNumberNew;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public Integer getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public String getLotteryNumber() {
		return lotteryNumber;
	}
	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
	public String getLotteryNumberNew() {
		return lotteryNumberNew;
	}
	public void setLotteryNumberNew(String lotteryNumberNew) {
		this.lotteryNumberNew = lotteryNumberNew;
	}
	public SchemesNumber() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SchemesNumber(Date dateTime, Integer schemeId, Double money, Integer multiple, String lotteryNumber, String lotteryNumberNew) {
		super();
		this.dateTime = dateTime;
		this.schemeId = schemeId;
		this.money = money;
		this.multiple = multiple;
		this.lotteryNumber = lotteryNumber;
		this.lotteryNumberNew = lotteryNumberNew;
	}

	
}