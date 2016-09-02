package com.yuncai.modules.lottery.sporttery.model;

import java.util.Date;

/**
 * FtSpfAward entity. @author MyEclipse Persistence Tools
 */

public class FtSpfjAward extends FtjAward implements java.io.Serializable {

	// Fields

	private Double homewinaward;
	private Double guestwinaward;
	private Double drawaward;
	private Double nrhomewinaward;
	private Double nrguestwinaward;
	private Double nrdrawaward;
	private String homechange;
	private String drawchange;
	private String guestchange;
	// Constructors

	
	/** default constructor */
	public FtSpfjAward() {
	}
	
	public Double getHomewinaward() {
		return homewinaward;
	}

	public void setHomewinaward(Double homewinaward) {
		this.homewinaward = homewinaward;
	}

	public Double getGuestwinaward() {
		return guestwinaward;
	}

	public void setGuestwinaward(Double guestwinaward) {
		this.guestwinaward = guestwinaward;
	}

	public Double getDrawaward() {
		return drawaward;
	}

	public void setDrawaward(Double drawaward) {
		this.drawaward = drawaward;
	}

	public String getHomechange() {
		return homechange;
	}

	public void setHomechange(String homechange) {
		this.homechange = homechange;
	}

	public String getDrawchange() {
		return drawchange;
	}

	public void setDrawchange(String drawchange) {
		this.drawchange = drawchange;
	}

	public String getGuestchange() {
		return guestchange;
	}

	public void setGuestchange(String guestchange) {
		this.guestchange = guestchange;
	}
	
	public Double getNrhomewinaward() {
		return nrhomewinaward;
	}

	public void setNrhomewinaward(Double nrhomewinaward) {
		this.nrhomewinaward = nrhomewinaward;
	}

	public Double getNrguestwinaward() {
		return nrguestwinaward;
	}

	public void setNrguestwinaward(Double nrguestwinaward) {
		this.nrguestwinaward = nrguestwinaward;
	}

	public Double getNrdrawaward() {
		return nrdrawaward;
	}

	public void setNrdrawaward(Double nrdrawaward) {
		this.nrdrawaward = nrdrawaward;
	}

	public FtSpfjAward(Integer id,Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double homewinaward,Double drawaward, Double guestwinaward, 
			Double nrhomewinaward,Double nrdrawaward, Double nrguestwinaward, 
			String homechange, String drawchange, String guestchange) {
		super(id,mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.homewinaward = homewinaward;
		this.drawaward = drawaward;
		this.guestwinaward = guestwinaward;
		this.nrhomewinaward = nrhomewinaward;
		this.nrdrawaward = nrdrawaward;
		this.nrguestwinaward = nrguestwinaward;
		this.homechange = homechange;
		this.drawchange = drawchange;
		this.guestchange = guestchange;
	}

	/**
	 * 用于抓取回来的数据更新封装
	 * @param mid
	 * @param inttime
	 * @param lineid
	 * @param matchtime
	 * @param passmode
	 * @param lastupdatetime
	 * @param homewinaward
	 * @param guestwinaward
	 * @param drawaward
	 * @param homechange
	 * @param drawchange
	 * @param guestchange
	 */
	public void setFtSpfAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double homewinaward, Double drawaward,Double guestwinaward, 
			Double nrhomewinaward,Double nrdrawaward, Double nrguestwinaward, 
			String homechange, String drawchange, String guestchange) {
		this.setMid(mid);
		this.setInttime(inttime);
		this.setLineid(lineid);
		this.setMatchtime(matchtime);
		this.setPassmode(passmode);
		this.setLastupdatetime(lastupdatetime);
		this.homewinaward = homewinaward;
		this.drawaward = drawaward;
		this.guestwinaward = guestwinaward;
		this.nrhomewinaward = nrhomewinaward;
		this.nrdrawaward = nrdrawaward;
		this.nrguestwinaward = nrguestwinaward;
		this.homechange = homechange;
		this.drawchange = drawchange;
		this.guestchange = guestchange;
	}
	
	
	
}