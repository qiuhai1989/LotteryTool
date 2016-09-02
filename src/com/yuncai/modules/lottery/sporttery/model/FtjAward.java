package com.yuncai.modules.lottery.sporttery.model;

import java.util.Date;

/**
 * 足球sp值父类
 */

public class FtjAward implements java.io.Serializable {

	/** ID值* */
	private Integer id;
	/** 对阵ID* */
	private Integer mid;
	/** 比赛时间的整形类型* */
	private Integer inttime;
	/** 比赛时间的lineId* */
	private String lineid;
	/** 比赛时间* */
	private String matchtime;
	/** 过关方式的下标* */
	private Integer passmode;
	
	private Date lastupdatetime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getInttime() {
		return inttime;
	}

	public void setInttime(Integer inttime) {
		this.inttime = inttime;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}

	public String getMatchtime() {
		return matchtime;
	}

	public void setMatchtime(String matchtime) {
		this.matchtime = matchtime;
	}

	public Integer getPassmode() {
		return passmode;
	}

	public void setPassmode(Integer passmode) {
		this.passmode = passmode;
	}

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	/** default constructor */
	public FtjAward() {
	}

	public FtjAward(Integer id,Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime) {
		super();
		this.id=id;
		this.mid = mid;
		this.inttime = inttime;
		this.lineid = lineid;
		this.matchtime = matchtime;
		this.passmode = passmode;
		this.lastupdatetime = lastupdatetime;
	}
	
}