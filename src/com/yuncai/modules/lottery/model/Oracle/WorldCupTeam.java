package com.yuncai.modules.lottery.model.Oracle;

import java.io.Serializable;

public class WorldCupTeam implements Serializable{

	public static final String TEAMNUM="teamNum";
	
	private Integer id;
	private String teamNum;//编号
	private String teamID;//球队ID对应t_ft_team表team_id
	private String teamName;//队名
	private String saleStatus;//开售状态 0停售 1开售
	private double bouns;//奖金
	private String supportRate;//支持率
	private String championRate;//夺冠支持率
	private String competitionType;//比赛类型 0欧冠杯 1世界杯
	private String rank;//排名
	private String iconsrc;//队伍图片路径
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTeamNum() {
		return teamNum;
	}
	public void setTeamNum(String teamNum) {
		this.teamNum = teamNum;
	}
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	public String getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}
	public double getBouns() {
		return bouns;
	}
	public void setBouns(double bouns) {
		this.bouns = bouns;
	}
	public String getSupportRate() {
		return supportRate;
	}
	public void setSupportRate(String supportRate) {
		this.supportRate = supportRate;
	}
	public String getChampionRate() {
		return championRate;
	}
	public void setChampionRate(String championRate) {
		this.championRate = championRate;
	}
	public String getCompetitionType() {
		return competitionType;
	}
	public void setCompetitionType(String competitionType) {
		this.competitionType = competitionType;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getIconsrc() {
		return iconsrc;
	}
	public void setIconsrc(String iconsrc) {
		this.iconsrc = iconsrc;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
					
	
	
}
