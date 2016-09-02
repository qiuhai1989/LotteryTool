package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * MatchBasketId entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class MatchBasket implements java.io.Serializable {

	// Fields
	
	private Integer id;
	//联赛类型颜色
	private String gameColor;
	//周日001
	private String matchNumber;
	//比赛时间
	private Date matchDate;
	//主队
	private String mainTeam;
	//客队
	private String guestTeam;
	//截至销售时间
	private Date stopSellingTime;
	
	private String winlosescore;
	private String givewinlosescore;
	private Double bigSmallscore;
	//大小分结果
	private String dxresult;
	//大小分赔率
	private String dxbonus;
	//让球胜平负结果
	private String rfsfresult;
	//让球胜平负赔率
	private String rfsfbonus;
	//胜负结果
	private String sfresult;
	//胜负赔率
	private String sfbonus;
	//胜分差结果
	private String sfcresult;
	//胜分差赔率
	private String sfcbonus;
	//比赛结果
	private String result;
	//否有结果（弃用该字段）
	private Boolean isResult;
	//是否已经开奖
	private Boolean isOpened;
	//联赛名称
	private String game;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGameColor() {
		return gameColor;
	}
	public void setGameColor(String gameColor) {
		this.gameColor = gameColor;
	}
	public String getMatchNumber() {
		return matchNumber;
	}
	public void setMatchNumber(String matchNumber) {
		this.matchNumber = matchNumber;
	}
	public Date getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	public String getMainTeam() {
		return mainTeam;
	}
	public void setMainTeam(String mainTeam) {
		this.mainTeam = mainTeam;
	}
	public String getGuestTeam() {
		return guestTeam;
	}
	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}
	public Date getStopSellingTime() {
		return stopSellingTime;
	}
	public void setStopSellingTime(Date stopSellingTime) {
		this.stopSellingTime = stopSellingTime;
	}
	public String getWinlosescore() {
		return winlosescore;
	}
	public void setWinlosescore(String winlosescore) {
		this.winlosescore = winlosescore;
	}
	public String getGivewinlosescore() {
		return givewinlosescore;
	}
	public void setGivewinlosescore(String givewinlosescore) {
		this.givewinlosescore = givewinlosescore;
	}
	public Double getBigSmallscore() {
		return bigSmallscore;
	}
	public void setBigSmallscore(Double bigSmallscore) {
		this.bigSmallscore = bigSmallscore;
	}
	public String getDxresult() {
		return dxresult;
	}
	public void setDxresult(String dxresult) {
		this.dxresult = dxresult;
	}
	public String getDxbonus() {
		return dxbonus;
	}
	public void setDxbonus(String dxbonus) {
		this.dxbonus = dxbonus;
	}
	public String getRfsfresult() {
		return rfsfresult;
	}
	public void setRfsfresult(String rfsfresult) {
		this.rfsfresult = rfsfresult;
	}
	public String getRfsfbonus() {
		return rfsfbonus;
	}
	public void setRfsfbonus(String rfsfbonus) {
		this.rfsfbonus = rfsfbonus;
	}
	public String getSfresult() {
		return sfresult;
	}
	public void setSfresult(String sfresult) {
		this.sfresult = sfresult;
	}
	public String getSfbonus() {
		return sfbonus;
	}
	public void setSfbonus(String sfbonus) {
		this.sfbonus = sfbonus;
	}
	public String getSfcresult() {
		return sfcresult;
	}
	public void setSfcresult(String sfcresult) {
		this.sfcresult = sfcresult;
	}
	public String getSfcbonus() {
		return sfcbonus;
	}
	public void setSfcbonus(String sfcbonus) {
		this.sfcbonus = sfcbonus;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Boolean getIsResult() {
		return isResult;
	}
	public void setIsResult(Boolean isResult) {
		this.isResult = isResult;
	}
	public Boolean getIsOpened() {
		return isOpened;
	}
	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public MatchBasket(String gameColor, String matchNumber, Date matchDate,
			String mainTeam, String guestTeam, Date stopSellingTime,
			String winlosescore, String givewinlosescore, Double bigSmallscore,
			String dxresult, String dxbonus, String rfsfresult,
			String rfsfbonus, String sfresult, String sfbonus,
			String sfcresult, String sfcbonus, String result, Boolean isResult,
			Boolean isOpened, String game) {
		super();
		this.gameColor = gameColor;
		this.matchNumber = matchNumber;
		this.matchDate = matchDate;
		this.mainTeam = mainTeam;
		this.guestTeam = guestTeam;
		this.stopSellingTime = stopSellingTime;
		this.winlosescore = winlosescore;
		this.givewinlosescore = givewinlosescore;
		this.bigSmallscore = bigSmallscore;
		this.dxresult = dxresult;
		this.dxbonus = dxbonus;
		this.rfsfresult = rfsfresult;
		this.rfsfbonus = rfsfbonus;
		this.sfresult = sfresult;
		this.sfbonus = sfbonus;
		this.sfcresult = sfcresult;
		this.sfcbonus = sfcbonus;
		this.result = result;
		this.isResult = isResult;
		this.isOpened = isOpened;
		this.game = game;
	}
	public MatchBasket() {
		super();
		// TODO Auto-generated constructor stub
	}

}