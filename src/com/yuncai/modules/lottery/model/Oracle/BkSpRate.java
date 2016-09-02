package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**
 * 竞彩篮球SP值实体类
 * @author blackworm
 */

@SuppressWarnings("serial")
public class BkSpRate implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mid;
	private Integer intTime;
	private String lineId;
	private String matchDate;
	private Integer passMode;
	private Date lastUpdateTime;
	//胜负
	private double sfloseRate; //负(主负)
	private double sfWinRate; //胜(主胜)
	
	//让分胜负
	private double rsfloseRate; //负(让分主负)
	private double rsfWinRate; //胜(让分主胜)
	
	//胜分差
	private double sfcTgRate1_5; //1-5客胜
	private double sfcMbRate1_5; //1-5主胜
	private double sfcTgRate6_10; //6-10客胜
	private double sfcMbRate6_10; //6-10主胜
	private double sfcTgRate11_15; //11-15客胜
	private double sfcMbRate11_15; //11-15主胜
	private double sfcTgRate16_20; //16-20客胜
	private double sfcMbRate16_20; //16-20主胜
	private double sfcTgRate21_25; //21-25客胜
	private double sfcMbRate21_25; //21-25主胜
	private double sfcTgRate26; //26+客胜
	private double sfcMbRate26; //26+主胜
	
	//大小分
	private double dxfBigRate; //大
	private double dxfSmallRate; //小
	

	private double rangScore; //让分基数
	private double dxScore; //大小分基数
	
	private Integer isSF;
	private Integer isRFSF;
	private Integer isSFC;
	private Integer isDX;
	
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
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public Integer getPassMode() {
		return passMode;
	}
	public void setPassMode(Integer passMode) {
		this.passMode = passMode;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public double getSfloseRate() {
		return sfloseRate;
	}
	public void setSfloseRate(double sfloseRate) {
		this.sfloseRate = sfloseRate;
	}
	public double getSfWinRate() {
		return sfWinRate;
	}
	public void setSfWinRate(double sfWinRate) {
		this.sfWinRate = sfWinRate;
	}
	public double getRsfloseRate() {
		return rsfloseRate;
	}
	public void setRsfloseRate(double rsfloseRate) {
		this.rsfloseRate = rsfloseRate;
	}
	public double getRsfWinRate() {
		return rsfWinRate;
	}
	public void setRsfWinRate(double rsfWinRate) {
		this.rsfWinRate = rsfWinRate;
	}
	public double getSfcTgRate1_5() {
		return sfcTgRate1_5;
	}
	public void setSfcTgRate1_5(double sfcTgRate1_5) {
		this.sfcTgRate1_5 = sfcTgRate1_5;
	}
	public double getSfcMbRate1_5() {
		return sfcMbRate1_5;
	}
	public void setSfcMbRate1_5(double sfcMbRate1_5) {
		this.sfcMbRate1_5 = sfcMbRate1_5;
	}
	public double getSfcTgRate6_10() {
		return sfcTgRate6_10;
	}
	public void setSfcTgRate6_10(double sfcTgRate6_10) {
		this.sfcTgRate6_10 = sfcTgRate6_10;
	}
	public double getSfcMbRate6_10() {
		return sfcMbRate6_10;
	}
	public void setSfcMbRate6_10(double sfcMbRate6_10) {
		this.sfcMbRate6_10 = sfcMbRate6_10;
	}
	public double getSfcTgRate11_15() {
		return sfcTgRate11_15;
	}
	public void setSfcTgRate11_15(double sfcTgRate11_15) {
		this.sfcTgRate11_15 = sfcTgRate11_15;
	}
	public double getSfcMbRate11_15() {
		return sfcMbRate11_15;
	}
	public void setSfcMbRate11_15(double sfcMbRate11_15) {
		this.sfcMbRate11_15 = sfcMbRate11_15;
	}
	public double getSfcTgRate16_20() {
		return sfcTgRate16_20;
	}
	public void setSfcTgRate16_20(double sfcTgRate16_20) {
		this.sfcTgRate16_20 = sfcTgRate16_20;
	}
	public double getSfcMbRate16_20() {
		return sfcMbRate16_20;
	}
	public void setSfcMbRate16_20(double sfcMbRate16_20) {
		this.sfcMbRate16_20 = sfcMbRate16_20;
	}
	public double getSfcTgRate21_25() {
		return sfcTgRate21_25;
	}
	public void setSfcTgRate21_25(double sfcTgRate21_25) {
		this.sfcTgRate21_25 = sfcTgRate21_25;
	}
	public double getSfcMbRate21_25() {
		return sfcMbRate21_25;
	}
	public void setSfcMbRate21_25(double sfcMbRate21_25) {
		this.sfcMbRate21_25 = sfcMbRate21_25;
	}
	public double getSfcTgRate26() {
		return sfcTgRate26;
	}
	public void setSfcTgRate26(double sfcTgRate26) {
		this.sfcTgRate26 = sfcTgRate26;
	}
	public double getSfcMbRate26() {
		return sfcMbRate26;
	}
	public void setSfcMbRate26(double sfcMbRate26) {
		this.sfcMbRate26 = sfcMbRate26;
	}
	public double getDxfBigRate() {
		return dxfBigRate;
	}
	public void setDxfBigRate(double dxfBigRate) {
		this.dxfBigRate = dxfBigRate;
	}
	public double getDxfSmallRate() {
		return dxfSmallRate;
	}
	public void setDxfSmallRate(double dxfSmallRate) {
		this.dxfSmallRate = dxfSmallRate;
	}
	public double getRangScore() {
		return rangScore;
	}
	public void setRangScore(double rangScore) {
		this.rangScore = rangScore;
	}
	public double getDxScore() {
		return dxScore;
	}
	public void setDxScore(double dxScore) {
		this.dxScore = dxScore;
	}
	public BkSpRate(Integer mid, Integer intTime, String lineId,
			String matchDate, Integer passMode, Date lastUpdateTime,
			double sfloseRate, double sfWinRate,
			double rsfloseRate, double rsfWinRate, double sfcTgRate1_5,
			double sfcMbRate1_5, double sfcTgRate6_10, double sfcMbRate6_10,
			double sfcTgRate11_15, double sfcMbRate11_15,
			double sfcTgRate16_20, double sfcMbRate16_20,
			double sfcTgRate21_25, double sfcMbRate21_25, double sfcTgRate26,
			double sfcMbRate26, double dxfBigRate,
			double dxfSmallRate, double rangScore, double dxScore) {
		super();
		this.mid = mid;
		this.intTime = intTime;
		this.lineId = lineId;
		this.matchDate = matchDate;
		this.passMode = passMode;
		this.lastUpdateTime = lastUpdateTime;
		this.sfloseRate = sfloseRate;
		this.sfWinRate = sfWinRate;
		this.rsfloseRate = rsfloseRate;
		this.rsfWinRate = rsfWinRate;
		this.sfcTgRate1_5 = sfcTgRate1_5;
		this.sfcMbRate1_5 = sfcMbRate1_5;
		this.sfcTgRate6_10 = sfcTgRate6_10;
		this.sfcMbRate6_10 = sfcMbRate6_10;
		this.sfcTgRate11_15 = sfcTgRate11_15;
		this.sfcMbRate11_15 = sfcMbRate11_15;
		this.sfcTgRate16_20 = sfcTgRate16_20;
		this.sfcMbRate16_20 = sfcMbRate16_20;
		this.sfcTgRate21_25 = sfcTgRate21_25;
		this.sfcMbRate21_25 = sfcMbRate21_25;
		this.sfcTgRate26 = sfcTgRate26;
		this.sfcMbRate26 = sfcMbRate26;
		this.dxfBigRate = dxfBigRate;
		this.dxfSmallRate = dxfSmallRate;
		this.rangScore = rangScore;
		this.dxScore = dxScore;
	}
	public BkSpRate() {
		super();
	}
	@Override
	public String toString() {
		return "BkSpRate [id=" + id + ", mid=" + mid + ", intTime=" + intTime
				+ ", lineId=" + lineId + ", matchDate=" + matchDate
				+ ", passMode=" + passMode + ", lastUpdateTime="
				+ lastUpdateTime + ", sfloseRate=" + sfloseRate
				+ ", sfWinRate=" + sfWinRate 
				+ ", rsfloseRate=" + rsfloseRate + ", rsfWinRate=" + rsfWinRate
				+ ", sfcTgRate1_5=" + sfcTgRate1_5 + ", sfcMbRate1_5="
				+ sfcMbRate1_5 + ", sfcTgRate6_10=" + sfcTgRate6_10
				+ ", sfcMbRate6_10=" + sfcMbRate6_10 + ", sfcTgRate11_15="
				+ sfcTgRate11_15 + ", sfcMbRate11_15=" + sfcMbRate11_15
				+ ", sfcTgRate16_20=" + sfcTgRate16_20 + ", sfcMbRate16_20="
				+ sfcMbRate16_20 + ", sfcTgRate21_25=" + sfcTgRate21_25
				+ ", sfcMbRate21_25=" + sfcMbRate21_25 + ", sfcTgRate26="
				+ sfcTgRate26 + ", sfcMbRate26=" + sfcMbRate26 + ", dxfBigRate=" + dxfBigRate + ", dxfSmallRate="
				+ dxfSmallRate + ", rangScore=" + rangScore + ", dxScore="
				+ dxScore + "]";
	}
	
	
	
	
	public BkSpRate(Integer id, Integer mid, Integer intTime, String lineId,
			String matchDate, Integer passMode, Date lastUpdateTime,
			double sfloseRate, double sfWinRate, double rsfloseRate,
			double rsfWinRate, double sfcTgRate1_5, double sfcMbRate1_5,
			double sfcTgRate6_10, double sfcMbRate6_10, double sfcTgRate11_15,
			double sfcMbRate11_15, double sfcTgRate16_20,
			double sfcMbRate16_20, double sfcTgRate21_25,
			double sfcMbRate21_25, double sfcTgRate26, double sfcMbRate26,
			double dxfBigRate, double dxfSmallRate, double rangScore,
			double dxScore, Integer isSF, Integer isRFSF, Integer isSFC,
			Integer isDX) {
		super();
		this.id = id;
		this.mid = mid;
		this.intTime = intTime;
		this.lineId = lineId;
		this.matchDate = matchDate;
		this.passMode = passMode;
		this.lastUpdateTime = lastUpdateTime;
		this.sfloseRate = sfloseRate;
		this.sfWinRate = sfWinRate;
		this.rsfloseRate = rsfloseRate;
		this.rsfWinRate = rsfWinRate;
		this.sfcTgRate1_5 = sfcTgRate1_5;
		this.sfcMbRate1_5 = sfcMbRate1_5;
		this.sfcTgRate6_10 = sfcTgRate6_10;
		this.sfcMbRate6_10 = sfcMbRate6_10;
		this.sfcTgRate11_15 = sfcTgRate11_15;
		this.sfcMbRate11_15 = sfcMbRate11_15;
		this.sfcTgRate16_20 = sfcTgRate16_20;
		this.sfcMbRate16_20 = sfcMbRate16_20;
		this.sfcTgRate21_25 = sfcTgRate21_25;
		this.sfcMbRate21_25 = sfcMbRate21_25;
		this.sfcTgRate26 = sfcTgRate26;
		this.sfcMbRate26 = sfcMbRate26;
		this.dxfBigRate = dxfBigRate;
		this.dxfSmallRate = dxfSmallRate;
		this.rangScore = rangScore;
		this.dxScore = dxScore;
		this.isSF = isSF;
		this.isRFSF = isRFSF;
		this.isSFC = isSFC;
		this.isDX = isDX;
	}
	public Integer getIsSF() {
		return isSF;
	}
	public void setIsSF(Integer isSF) {
		this.isSF = isSF;
	}
	public Integer getIsRFSF() {
		return isRFSF;
	}
	public void setIsRFSF(Integer isRFSF) {
		this.isRFSF = isRFSF;
	}
	public Integer getIsSFC() {
		return isSFC;
	}
	public void setIsSFC(Integer isSFC) {
		this.isSFC = isSFC;
	}
	public Integer getIsDX() {
		return isDX;
	}
	public void setIsDX(Integer isDX) {
		this.isDX = isDX;
	}
	
	
}