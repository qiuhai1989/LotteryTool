package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * Match entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Match implements java.io.Serializable {

	// Fields

	private Integer id;
	//联赛类型颜色
	private String gameColor;
	//联赛名称
	private String game; 
	//周日001
	private String matchNumber; 
	//比赛时间
	private Date matchDate;
	//主队
	private String mainTeam;
	//客队
	private String guestTeam;
	//主队让球
	private String mainLoseBall;
	//截至销售时间
	private Date stopSellingTime;
	//胜平负结果
	private String spfresult;
	//胜平负赔率
	private String spfbonus;
	//半全场结果
	private String bqcresult;
	//半全场赔率
	private String bqcbonus;
	//总进球结果
	private String zjqsresult;
	//总进球赔率
	private String zjqsbonus;
	//比分结果
	private String zqbfresult;
	//比分赔率
	private String zqbfbonus;
	//半场结果
	private String firstHalfResult;
	//比赛结果
	private String result;
	//是否开奖
	private Boolean isOpened;
	//与oracle数据库关联ID
	private Integer mid;
	//是否兑奖
	private Integer status;
	
	private Integer atype;
	
	private String RQSPFResult;
	

	// Constructors

	/** default constructor */
	public Match() {
	}

	/** full constructor */
	public Match(String gameColor, String game, String matchNumber,
			Date matchDate, String mainTeam, String guestTeam,
			String mainLoseBall, Date stopSellingTime, String spfresult,
			String spfbonus, String bqcresult, String bqcbonus,
			String zjqsresult, String zjqsbonus, String zqbfresult,
			String zqbfbonus, String firstHalfResult, String result,
			Boolean isOpened,Integer mid,Integer status) {
		this.gameColor = gameColor;
		this.game = game;
		this.matchNumber = matchNumber;
		this.matchDate = matchDate;
		this.mainTeam = mainTeam;
		this.guestTeam = guestTeam;
		this.mainLoseBall = mainLoseBall;
		this.stopSellingTime = stopSellingTime;
		this.spfresult = spfresult;
		this.spfbonus = spfbonus;
		this.bqcresult = bqcresult;
		this.bqcbonus = bqcbonus;
		this.zjqsresult = zjqsresult;
		this.zjqsbonus = zjqsbonus;
		this.zqbfresult = zqbfresult;
		this.zqbfbonus = zqbfbonus;
		this.firstHalfResult = firstHalfResult;
		this.result = result;
		this.isOpened = isOpened;
		this.mid=mid;
		this.status=status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGameColor() {
		return this.gameColor;
	}

	public void setGameColor(String gameColor) {
		this.gameColor = gameColor;
	}

	public String getGame() {
		return this.game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getMatchNumber() {
		return this.matchNumber;
	}

	public void setMatchNumber(String matchNumber) {
		this.matchNumber = matchNumber;
	}

	public Date getMatchDate() {
		return this.matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public String getMainTeam() {
		return this.mainTeam;
	}

	public void setMainTeam(String mainTeam) {
		this.mainTeam = mainTeam;
	}

	public String getGuestTeam() {
		return this.guestTeam;
	}

	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}

	public String getMainLoseBall() {
		return this.mainLoseBall;
	}

	public void setMainLoseBall(String mainLoseBall) {
		this.mainLoseBall = mainLoseBall;
	}

	public Date getStopSellingTime() {
		return this.stopSellingTime;
	}

	public void setStopSellingTime(Date stopSellingTime) {
		this.stopSellingTime = stopSellingTime;
	}

	public String getSpfresult() {
		return this.spfresult;
	}

	public void setSpfresult(String spfresult) {
		this.spfresult = spfresult;
	}

	public String getSpfbonus() {
		return this.spfbonus;
	}

	public void setSpfbonus(String spfbonus) {
		this.spfbonus = spfbonus;
	}

	public String getBqcresult() {
		return this.bqcresult;
	}

	public void setBqcresult(String bqcresult) {
		this.bqcresult = bqcresult;
	}

	public String getBqcbonus() {
		return this.bqcbonus;
	}

	public void setBqcbonus(String bqcbonus) {
		this.bqcbonus = bqcbonus;
	}

	public String getZjqsresult() {
		return this.zjqsresult;
	}

	public void setZjqsresult(String zjqsresult) {
		this.zjqsresult = zjqsresult;
	}

	public String getZjqsbonus() {
		return this.zjqsbonus;
	}

	public void setZjqsbonus(String zjqsbonus) {
		this.zjqsbonus = zjqsbonus;
	}

	public String getZqbfresult() {
		return this.zqbfresult;
	}

	public void setZqbfresult(String zqbfresult) {
		this.zqbfresult = zqbfresult;
	}

	public String getZqbfbonus() {
		return this.zqbfbonus;
	}

	public void setZqbfbonus(String zqbfbonus) {
		this.zqbfbonus = zqbfbonus;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFirstHalfResult() {
		return this.firstHalfResult;
	}

	public void setFirstHalfResult(String firstHalfResult) {
		this.firstHalfResult = firstHalfResult;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getIsOpened() {
		return this.isOpened;
	}

	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}
	
	public Integer getAtype() {
		return atype;
	}

	public void setAtype(Integer atype) {
		this.atype = atype;
	}

	
	
	public String getRQSPFResult() {
		return RQSPFResult;
	}

	public void setRQSPFResult(String result) {
		RQSPFResult = result;
	}

	/**
	 * 该构造函数用于竞彩官网计算器数据存储封装
	 */
	public Match(Integer mid, String game, String gameColor,String mainTeam, String guestTeam,
		Date matchDate, Date stopSellingTime,String matchNumber,String mainLoseBall,Integer status,Integer atype) {
		super();
		this.mid = mid;
		this.game = game;
		this.gameColor = gameColor;
		this.mainTeam = mainTeam;
		this.guestTeam = guestTeam;
		this.matchDate = matchDate;
		this.stopSellingTime = stopSellingTime;
		this.matchNumber = matchNumber;
		this.mainLoseBall=mainLoseBall;
		this.status=status;
		this.atype=atype;
	}
	
}