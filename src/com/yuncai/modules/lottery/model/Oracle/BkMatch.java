package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**
 * 竞彩篮球赛事对阵实体类
 * @author blackworm
 */

@SuppressWarnings("serial")
public class BkMatch implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mid;
	private Integer leagueId;
	private Integer tgId;
	private Integer mbId;
	private String leagueName;
	private String leagueAliasName;//联赛别名
	private String leagueColor;
	private String tgName;
	private String tgAliasName;//客队别名
	private String mbName;
	private String mbAliasName;//主队别名
	private Date startTime;
	private Date nosaleTime;
	private String matchDate;
	private String matchNo;
	private Integer intTime;
	private String lineId;
	private Integer tgScore1;
	private Integer mbScore1;
	private Integer tgScore2;
	private Integer mbScore2;
	private Integer tgScore3;
	private Integer mbScore3;
	private Integer tgScore4;
	private Integer mbScore4;
	private Integer tgScore;
	private Integer mbScore;
	private Double sfGold;      //-1:无胜出投注   0:为空
	private Double rsfGold;   //-1:无胜出投注   0:为空
	private Double sfcGold;	 //-1:无胜出投注   0:为空
	private Double dxGold;       //-1:无胜出投注   0:为空
	private Integer status;
	private Date fetchTime;
	
	private Integer atype;
	private Integer mtid; //彩客mid
	
	private String dxResult;//大小分赛果
	
	private String rfsfResult;//让分胜负赛果
	
	private String sfResult;//胜负赛果
	
	private String sfcResult;//胜分差赛果
	
	private Integer sqlId;
	
	// Constructors

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

	public Integer getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Integer leagueId) {
		this.leagueId = leagueId;
	}

	public Integer getTgId() {
		return tgId;
	}

	public void setTgId(Integer tgId) {
		this.tgId = tgId;
	}

	public Integer getMbId() {
		return mbId;
	}

	public void setMbId(Integer mbId) {
		this.mbId = mbId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getLeagueAliasName() {
		return leagueAliasName;
	}

	public void setLeagueAliasName(String leagueAliasName) {
		this.leagueAliasName = leagueAliasName;
	}

	public String getLeagueColor() {
		return leagueColor;
	}

	public void setLeagueColor(String leagueColor) {
		this.leagueColor = leagueColor;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public String getTgAliasName() {
		return tgAliasName;
	}

	public void setTgAliasName(String tgAliasName) {
		this.tgAliasName = tgAliasName;
	}

	public String getMbName() {
		return mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

	public String getMbAliasName() {
		return mbAliasName;
	}

	public void setMbAliasName(String mbAliasName) {
		this.mbAliasName = mbAliasName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getNosaleTime() {
		return nosaleTime;
	}

	public void setNosaleTime(Date nosaleTime) {
		this.nosaleTime = nosaleTime;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchNo() {
		return matchNo;
	}

	public void setMatchNo(String matchNo) {
		this.matchNo = matchNo;
	}

	public Integer getIntTime() {
		return intTime;
	}

	public void setIntTime(Integer intTime) {
		this.intTime = intTime;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Integer getTgScore1() {
		return tgScore1;
	}

	public void setTgScore1(Integer tgScore1) {
		this.tgScore1 = tgScore1;
	}

	public Integer getMbScore1() {
		return mbScore1;
	}

	public void setMbScore1(Integer mbScore1) {
		this.mbScore1 = mbScore1;
	}

	public Integer getTgScore2() {
		return tgScore2;
	}

	public void setTgScore2(Integer tgScore2) {
		this.tgScore2 = tgScore2;
	}

	public Integer getMbScore2() {
		return mbScore2;
	}

	public void setMbScore2(Integer mbScore2) {
		this.mbScore2 = mbScore2;
	}

	public Integer getTgScore3() {
		return tgScore3;
	}

	public void setTgScore3(Integer tgScore3) {
		this.tgScore3 = tgScore3;
	}

	public Integer getMbScore3() {
		return mbScore3;
	}

	public void setMbScore3(Integer mbScore3) {
		this.mbScore3 = mbScore3;
	}

	public Integer getTgScore4() {
		return tgScore4;
	}

	public void setTgScore4(Integer tgScore4) {
		this.tgScore4 = tgScore4;
	}

	public Integer getMbScore4() {
		return mbScore4;
	}

	public void setMbScore4(Integer mbScore4) {
		this.mbScore4 = mbScore4;
	}

	public Integer getTgScore() {
		return tgScore;
	}

	public void setTgScore(Integer tgScore) {
		this.tgScore = tgScore;
	}

	public Integer getMbScore() {
		return mbScore;
	}

	public void setMbScore(Integer mbScore) {
		this.mbScore = mbScore;
	}

	public Double getSfGold() {
		return sfGold;
	}

	public void setSfGold(Double sfGold) {
		this.sfGold = sfGold;
	}

	public Double getRsfGold() {
		return rsfGold;
	}

	public void setRsfGold(Double rsfGold) {
		this.rsfGold = rsfGold;
	}

	public Double getSfcGold() {
		return sfcGold;
	}

	public void setSfcGold(Double sfcGold) {
		this.sfcGold = sfcGold;
	}

	public Double getDxGold() {
		return dxGold;
	}

	public void setDxGold(Double dxGold) {
		this.dxGold = dxGold;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(Date fetchTime) {
		this.fetchTime = fetchTime;
	}

	public Integer getAtype() {
		return atype;
	}

	public void setAtype(Integer atype) {
		this.atype = atype;
	}

	public Integer getMtid() {
		return mtid;
	}

	public void setMtid(Integer mtid) {
		this.mtid = mtid;
	}

	/** default constructor */
	public BkMatch() {
	}



	public BkMatch(Integer mid, Integer leagueId, Integer tgId, Integer mbId,
			String leagueName, String leagueAliasName, String leagueColor,
			String tgName, String tgAliasName, String mbName,
			String mbAliasName, Date startTime, Date nosaleTime,
			String matchDate, String matchNo, Integer intTime, String lineId,
			Integer tgScore1, Integer mbScore1, Integer tgScore2,
			Integer mbScore2, Integer tgScore3, Integer mbScore3,
			Integer tgScore4, Integer mbScore4, Integer tgScore,
			Integer mbScore, Double sfGold, Double rsfGold, Double sfcGold,
			Double dxGold, Integer status, Date fetchTime, Integer atype,
			Integer mtid) {
		super();
		this.mid = mid;
		this.leagueId = leagueId;
		this.tgId = tgId;
		this.mbId = mbId;
		this.leagueName = leagueName;
		this.leagueAliasName = leagueAliasName;
		this.leagueColor = leagueColor;
		this.tgName = tgName;
		this.tgAliasName = tgAliasName;
		this.mbName = mbName;
		this.mbAliasName = mbAliasName;
		this.startTime = startTime;
		this.nosaleTime = nosaleTime;
		this.matchDate = matchDate;
		this.matchNo = matchNo;
		this.intTime = intTime;
		this.lineId = lineId;
		this.tgScore1 = tgScore1;
		this.mbScore1 = mbScore1;
		this.tgScore2 = tgScore2;
		this.mbScore2 = mbScore2;
		this.tgScore3 = tgScore3;
		this.mbScore3 = mbScore3;
		this.tgScore4 = tgScore4;
		this.mbScore4 = mbScore4;
		this.tgScore = tgScore;
		this.mbScore = mbScore;
		this.sfGold = sfGold;
		this.rsfGold = rsfGold;
		this.sfcGold = sfcGold;
		this.dxGold = dxGold;
		this.status = status;
		this.fetchTime = fetchTime;
		this.atype = atype;
		this.mtid = mtid;
	}

	
	
	
	public BkMatch(Integer id, Integer mid, Integer leagueId, Integer tgId, Integer mbId, String leagueName, String leagueAliasName,
			String leagueColor, String tgName, String tgAliasName, String mbName, String mbAliasName, Date startTime, Date nosaleTime,
			String matchDate, String matchNo, Integer intTime, String lineId, Integer tgScore1, Integer mbScore1, Integer tgScore2, Integer mbScore2,
			Integer tgScore3, Integer mbScore3, Integer tgScore4, Integer mbScore4, Integer tgScore, Integer mbScore, Double sfGold, Double rsfGold,
			Double sfcGold, Double dxGold, Integer status, Date fetchTime, Integer atype, Integer mtid, String dxResult, String rfsfResult,
			String sfResult, String sfcResult,Integer sqlId) {
		super();
		this.id = id;
		this.mid = mid;
		this.leagueId = leagueId;
		this.tgId = tgId;
		this.mbId = mbId;
		this.leagueName = leagueName;
		this.leagueAliasName = leagueAliasName;
		this.leagueColor = leagueColor;
		this.tgName = tgName;
		this.tgAliasName = tgAliasName;
		this.mbName = mbName;
		this.mbAliasName = mbAliasName;
		this.startTime = startTime;
		this.nosaleTime = nosaleTime;
		this.matchDate = matchDate;
		this.matchNo = matchNo;
		this.intTime = intTime;
		this.lineId = lineId;
		this.tgScore1 = tgScore1;
		this.mbScore1 = mbScore1;
		this.tgScore2 = tgScore2;
		this.mbScore2 = mbScore2;
		this.tgScore3 = tgScore3;
		this.mbScore3 = mbScore3;
		this.tgScore4 = tgScore4;
		this.mbScore4 = mbScore4;
		this.tgScore = tgScore;
		this.mbScore = mbScore;
		this.sfGold = sfGold;
		this.rsfGold = rsfGold;
		this.sfcGold = sfcGold;
		this.dxGold = dxGold;
		this.status = status;
		this.fetchTime = fetchTime;
		this.atype = atype;
		this.mtid = mtid;
		this.dxResult = dxResult;
		this.rfsfResult = rfsfResult;
		this.sfResult = sfResult;
		this.sfcResult = sfcResult;
		this.sqlId=sqlId;
	}

	public String getDxResult() {
		return dxResult;
	}

	public void setDxResult(String dxResult) {
		this.dxResult = dxResult;
	}

	public String getRfsfResult() {
		return rfsfResult;
	}

	public void setRfsfResult(String rfsfResult) {
		this.rfsfResult = rfsfResult;
	}

	public String getSfResult() {
		return sfResult;
	}

	public void setSfResult(String sfResult) {
		this.sfResult = sfResult;
	}

	public String getSfcResult() {
		return sfcResult;
	}

	public void setSfcResult(String sfcResult) {
		this.sfcResult = sfcResult;
	}

	public Integer getSqlId() {
		return sqlId;
	}

	public void setSqlId(Integer sqlId) {
		this.sqlId = sqlId;
	}

	
}