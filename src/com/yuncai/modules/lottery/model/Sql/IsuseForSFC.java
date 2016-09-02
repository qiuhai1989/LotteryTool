package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

public class IsuseForSFC {

	private Integer id;
	private Integer isusesId;
	private Integer no;
	private String hostTeam;
	private String hostTeamRank;
	private String guestTeam;
	private String guestTeamRank;
	private Date dateTime;
	private Double lost;
	private Double draw;
	private Double win;
	private String game;
	
	public String getHostTeamRank() {
		return hostTeamRank;
	}
	public void setHostTeamRank(String hostTeamRank) {
		this.hostTeamRank = hostTeamRank;
	}
	public String getGuestTeamRank() {
		return guestTeamRank;
	}
	public void setGuestTeamRank(String guestTeamRank) {
		this.guestTeamRank = guestTeamRank;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIsusesId() {
		return isusesId;
	}
	public void setIsusesId(Integer isusesId) {
		this.isusesId = isusesId;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getHostTeam() {
		return hostTeam;
	}
	public void setHostTeam(String hostTeam) {
		this.hostTeam = hostTeam;
	}
	public String getGuestTeam() {
		return guestTeam;
	}
	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public Double getLost() {
		return lost;
	}
	public void setLost(Double lost) {
		this.lost = lost;
	}
	public Double getDraw() {
		return draw;
	}
	public void setDraw(Double draw) {
		this.draw = draw;
	}
	public Double getWin() {
		return win;
	}
	public void setWin(Double win) {
		this.win = win;
	}
	
	
	
}
