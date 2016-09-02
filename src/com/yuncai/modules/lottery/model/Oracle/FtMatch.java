package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**
 * FtMatch entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class FtMatch implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mid;
	private String leagueName;
	private String leagueColor;
	private String mbName;
	private String tgName;
	private Date startTime;
	private Date nosaleTime;
	private String matchDate;
	private String matchNo;
	private Integer intTime;
	private String lineId;
	private Integer rangBallNum;
	private Integer mbScoreHalf;
	private Integer tgScoreHalf;
	private Integer mbScoreAll;
	private Integer tgScoreAll;
	private Double wplGold;      //-1:无胜出投注   0:为空
	private Double inballGold;   //-1:无胜出投注   0:为空
	private Double scoreGold;	 //-1:无胜出投注   0:为空
	private Double haGold;       //-1:无胜出投注   0:为空
	private Integer status;
	private Date fetchTime;
	private Integer leagueId;
	private Integer mbId;
	private Integer tgId;
	private Integer atype;
	private Double nwplGold;      //-1:无胜出投注   0:为空
	private Integer mtid; //彩客mid
	
	private Integer sqlId;
	// Constructors

	/** default constructor */
	public FtMatch() {
	}

	/** full constructor */
	public FtMatch(Integer mid, String leagueName, String leagueColor,
			String mbName, String tgName, Date startTime,
			Date nosaleTime, String matchDate, String matchNo,
			Integer intTime, String lineId, Integer rangBallNum,
			Integer mbScoreHalf, Integer tgScoreHalf,
			Integer mbScoreAll, Integer tgScoreAll, Double wplGold,
			Double inballGold, Double scoreGold, Double haGold,
			Integer status, Date fetchTime) {
		this.mid = mid;
		this.leagueName = leagueName;
		this.leagueColor = leagueColor;
		this.mbName = mbName;
		this.tgName = tgName;
		this.startTime = startTime;
		this.nosaleTime = nosaleTime;
		this.matchDate = matchDate;
		this.matchNo = matchNo;
		this.intTime = intTime;
		this.lineId = lineId;
		this.rangBallNum = rangBallNum;
		this.mbScoreHalf = mbScoreHalf;
		this.tgScoreHalf = tgScoreHalf;
		this.mbScoreAll = mbScoreAll;
		this.tgScoreAll = tgScoreAll;
		this.wplGold = wplGold;
		this.inballGold = inballGold;
		this.scoreGold = scoreGold;
		this.haGold = haGold;
		this.status = status;
		this.fetchTime = fetchTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getLeagueName() {
		return this.leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getLeagueColor() {
		return this.leagueColor;
	}

	public void setLeagueColor(String leagueColor) {
		this.leagueColor = leagueColor;
	}

	public String getMbName() {
		return this.mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

	public String getTgName() {
		return this.tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getNosaleTime() {
		return this.nosaleTime;
	}

	public void setNosaleTime(Date nosaleTime) {
		this.nosaleTime = nosaleTime;
	}

	public String getMatchDate() {
		return this.matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchNo() {
		return this.matchNo;
	}

	public void setMatchNo(String matchNo) {
		this.matchNo = matchNo;
	}

	public Integer getIntTime() {
		return this.intTime;
	}

	public void setIntTime(Integer intTime) {
		this.intTime = intTime;
	}

	public String getLineId() {
		return this.lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Integer getRangBallNum() {
		return this.rangBallNum;
	}

	public void setRangBallNum(Integer rangBallNum) {
		this.rangBallNum = rangBallNum;
	}

	public Integer getMbScoreHalf() {
		return this.mbScoreHalf;
	}

	public void setMbScoreHalf(Integer mbScoreHalf) {
		this.mbScoreHalf = mbScoreHalf;
	}
	
	public Integer getTgScoreHalf() {
		return this.tgScoreHalf;
	}

	public void setTgScoreHalf(Integer tgScoreHalf) {
		this.tgScoreHalf = tgScoreHalf;
	}

	public Integer getMbScoreAll() {
		return this.mbScoreAll;
	}

	public void setMbScoreAll(Integer mbScoreAll) {
		this.mbScoreAll = mbScoreAll;
	}

	public Integer getTgScoreAll() {
		return this.tgScoreAll;
	}

	public void setTgScoreAll(Integer tgScoreAll) {
		this.tgScoreAll = tgScoreAll;
	}

	public Double getWplGold() {
		return this.wplGold;
	}

	public void setWplGold(Double wplGold) {
		this.wplGold = wplGold;
	}

	public Double getInballGold() {
		return this.inballGold;
	}

	public void setInballGold(Double inballGold) {
		this.inballGold = inballGold;
	}

	public Double getScoreGold() {
		return this.scoreGold;
	}

	public void setScoreGold(Double scoreGold) {
		this.scoreGold = scoreGold;
	}

	public Double getHaGold() {
		return this.haGold;
	}

	public void setHaGold(Double haGold) {
		this.haGold = haGold;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getFetchTime() {
		return this.fetchTime;
	}

	public void setFetchTime(Date fetchTime) {
		this.fetchTime = fetchTime;
	}
	
	public Integer getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Integer leagueId) {
		this.leagueId = leagueId;
	}

	public Integer getMbId() {
		return mbId;
	}

	public void setMbId(Integer mbId) {
		this.mbId = mbId;
	}

	public Integer getTgId() {
		return tgId;
	}

	public void setTgId(Integer tgId) {
		this.tgId = tgId;
	}
	
	public Integer getAtype() {
		return atype;
	}

	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	
	public Double getNwplGold() {
		return nwplGold;
	}

	public void setNwplGold(Double nwplGold) {
		this.nwplGold = nwplGold;
	}
	
	public Integer getMtid() {
		return mtid;
	}

	public void setMtid(Integer mtid) {
		this.mtid = mtid;
	}

	/**
	 * 结果
	 * @param mid
	 * @param matchDate
	 * @param mbScoreHalf
	 * @param tgScoreHalf
	 * @param mbScoreAll
	 * @param tgScoreAll
	 * @param status
	 */
	public void setResult(Integer mbScoreHalf,
			Integer tgScoreHalf, Integer mbScoreAll, Integer tgScoreAll,
			Integer status,Date fetchTime){
		this.mbScoreHalf = mbScoreHalf;
		this.tgScoreHalf = tgScoreHalf;
		this.mbScoreAll = mbScoreAll;
		this.tgScoreAll = tgScoreAll;
		this.status = status;
		this.fetchTime = fetchTime;
	}
	public void setResult(Integer mbScoreHalf,
			Integer tgScoreHalf, Integer mbScoreAll, Integer tgScoreAll,
			Date fetchTime){
		this.mbScoreHalf = mbScoreHalf;
		this.tgScoreHalf = tgScoreHalf;
		this.mbScoreAll = mbScoreAll;
		this.tgScoreAll = tgScoreAll;
		this.fetchTime = fetchTime;
	}
	
	/**
	 * 奖金
	 * @param wplGold
	 * @param inballGold
	 * @param scoreGold
	 * @param haGold
	 */
	public void setGold(Double wplGold, Double inballGold,
			Double scoreGold, Double haGold,Double nwplGold){
		this.wplGold = wplGold;
		this.inballGold = inballGold;
		this.scoreGold = scoreGold;
		this.haGold = haGold;
		this.nwplGold=nwplGold;
	}

	/**
	 * 该构造函数用于竞彩官网计算器数据存储封装
	 * @param mid
	 * @param leagueName
	 * @param leagueColor
	 * @param mbName
	 * @param tgName
	 * @param startTime
	 * @param nosaleTime
	 * @param matchDate
	 * @param matchNo
	 * @param intTime
	 * @param lineId
	 * @param rangBallNum
	 */
	public FtMatch(Integer mid, String leagueName, String leagueColor,
			String mbName, String tgName, Date startTime, Date nosaleTime,
			String matchDate, String matchNo, Integer intTime, String lineId,
			Integer rangBallNum,Integer leagueId,Integer mbId,Integer tgId,Integer atype) {
		super();
		this.mid = mid;
		this.leagueName = leagueName;
		this.leagueColor = leagueColor;
		this.mbName = mbName;
		this.tgName = tgName;
		this.startTime = startTime;
		this.nosaleTime = nosaleTime;
		this.matchDate = matchDate;
		this.matchNo = matchNo;
		this.intTime = intTime;
		this.lineId = lineId;
		this.rangBallNum = rangBallNum;
		this.leagueId=leagueId;
		this.mbId=mbId;
		this.tgId=tgId;
		this.atype=atype;
	}

	public FtMatch(Integer id, Integer mid, String leagueName, String leagueColor, String mbName, String tgName, Date startTime, Date nosaleTime,
			String matchDate, String matchNo, Integer intTime, String lineId, Integer rangBallNum, Integer mbScoreHalf, Integer tgScoreHalf,
			Integer mbScoreAll, Integer tgScoreAll, Double wplGold, Double inballGold, Double scoreGold, Double haGold, Integer status,
			Date fetchTime, Integer leagueId, Integer mbId, Integer tgId, Integer atype, Double nwplGold, Integer mtid, Integer sqlId) {
		super();
		this.id = id;
		this.mid = mid;
		this.leagueName = leagueName;
		this.leagueColor = leagueColor;
		this.mbName = mbName;
		this.tgName = tgName;
		this.startTime = startTime;
		this.nosaleTime = nosaleTime;
		this.matchDate = matchDate;
		this.matchNo = matchNo;
		this.intTime = intTime;
		this.lineId = lineId;
		this.rangBallNum = rangBallNum;
		this.mbScoreHalf = mbScoreHalf;
		this.tgScoreHalf = tgScoreHalf;
		this.mbScoreAll = mbScoreAll;
		this.tgScoreAll = tgScoreAll;
		this.wplGold = wplGold;
		this.inballGold = inballGold;
		this.scoreGold = scoreGold;
		this.haGold = haGold;
		this.status = status;
		this.fetchTime = fetchTime;
		this.leagueId = leagueId;
		this.mbId = mbId;
		this.tgId = tgId;
		this.atype = atype;
		this.nwplGold = nwplGold;
		this.mtid = mtid;
		this.sqlId = sqlId;
	}

	public Integer getSqlId() {
		return sqlId;
	}

	public void setSqlId(Integer sqlId) {
		this.sqlId = sqlId;
	}
	
	
}