package com.yuncai.modules.lottery.bean.vo;

public class BBInnerPassRate {
	private String matchId;
	private String matchNumber;//周一001
	private String matchDate;//2013-8-6 0:29:00
	private String matchDate1;//周一
	private String mid;//2135 
	private String intTime;//2013-8-6
	private String lineId;
	private String url;//推广链接
	private String mRank;//东10
	private String gRank;//东14
//	private String date;//2013-8-6
	private String game;//NBA
	private String mainTeam;//克利夫兰骑士
	private String guestTeam;//纽约尼克斯
	private String stopSellTime;//2013-8-5 23:55:00
	private String rangScore;//让分-基数
	private String rsfLoseRate;//让分-负(让分主负)
	private String rsfWinRate;//让分-胜(让分主胜)
	private String sfWinRate;//胜负-胜(主胜)
	private String sfLoseRate;//胜负-负(主负)

	private String sfcMbRate1_5;//胜分差-1-5主胜
	private String sfcMbRate6_10;
	private String sfcMbRate11_15;
	private String sfcMbRate16_20;
	private String sfcMbRate21_25;
	private String sfcMbRate26;
	private String sfcTgRate1_5;//胜分差-1-5客胜
	private String sfcTgRate6_10;
	private String sfcTgRate11_15;
	private String sfcTgRate16_20;
	private String sfcTgRate21_25;
	private String sfcTgRate26;
	private String dxfBigRate;	//大小分-大
	private String dxfSmallRate;	//大小分-小
	private String dxScore;	//大小分-基数
	
	private String sfRefData;//胜负数据组合
	private String rsfRefData;//让分胜负数据组合
	private String sfcRefData;//胜分差数据组合
	private String dxfRefData;//大小分数据组合
	private String htRefData;//混投数据组合	
	
	private String isSF;//胜负玩法是否可投
	private String isRFSF;//让分胜负玩法是否可投
	private String isSFC;//胜分差是否可投
	private String isDX;//大小球是否可投
	
	public String getSfRefData() {
		return sfRefData;
	}
	public void setSfRefData(String sfRefData) {
		this.sfRefData = sfRefData;
	}
	public String getRsfRefData() {
		return rsfRefData;
	}
	public void setRsfRefData(String rsfRefData) {
		this.rsfRefData = rsfRefData;
	}
	public String getSfcRefData() {
		return sfcRefData;
	}
	public void setSfcRefData(String sfcRefData) {
		this.sfcRefData = sfcRefData;
	}
	public String getDxfRefData() {
		return dxfRefData;
	}
	public void setDxfRefData(String dxfRefData) {
		this.dxfRefData = dxfRefData;
	}
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getMatchNumber() {
		return matchNumber;
	}
	public void setMatchNumber(String matchNumber) {
		this.matchNumber = matchNumber;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public String getMatchDate1() {
		return matchDate1;
	}
	public void setMatchDate1(String matchDate1) {
		this.matchDate1 = matchDate1;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMRank() {
		return mRank;
	}
	public void setMRank(String rank) {
		mRank = rank;
	}
	public String getGRank() {
		return gRank;
	}
	public void setGRank(String rank) {
		gRank = rank;
	}
//	public String getDate() {
//		return date;
//	}
//	public void setDate(String date) {
//		this.date = date;
//	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
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
	public String getStopSellTime() {
		return stopSellTime;
	}
	public void setStopSellTime(String stopSellTime) {
		this.stopSellTime = stopSellTime;
	}
	public String getRangScore() {
		return rangScore;
	}
	public void setRangScore(String rangScore) {
		this.rangScore = rangScore;
	}
	public String getRsfLoseRate() {
		return rsfLoseRate;
	}
	public void setRsfLoseRate(String rsfLoseRate) {
		this.rsfLoseRate = rsfLoseRate;
	}
	public String getRsfWinRate() {
		return rsfWinRate;
	}
	public void setRsfWinRate(String rsfWinRate) {
		this.rsfWinRate = rsfWinRate;
	}
	public String getSfWinRate() {
		return sfWinRate;
	}
	public void setSfWinRate(String sfWinRate) {
		this.sfWinRate = sfWinRate;
	}
	public String getSfLoseRate() {
		return sfLoseRate;
	}
	public void setSfLoseRate(String sfLoseRate) {
		this.sfLoseRate = sfLoseRate;
	}
	public String getSfcMbRate1_5() {
		return sfcMbRate1_5;
	}
	public void setSfcMbRate1_5(String sfcMbRate1_5) {
		this.sfcMbRate1_5 = sfcMbRate1_5;
	}
	public String getSfcMbRate6_10() {
		return sfcMbRate6_10;
	}
	public void setSfcMbRate6_10(String sfcMbRate6_10) {
		this.sfcMbRate6_10 = sfcMbRate6_10;
	}
	public String getSfcMbRate11_15() {
		return sfcMbRate11_15;
	}
	public void setSfcMbRate11_15(String sfcMbRate11_15) {
		this.sfcMbRate11_15 = sfcMbRate11_15;
	}
	public String getSfcMbRate16_20() {
		return sfcMbRate16_20;
	}
	public void setSfcMbRate16_20(String sfcMbRate16_20) {
		this.sfcMbRate16_20 = sfcMbRate16_20;
	}
	public String getSfcMbRate21_25() {
		return sfcMbRate21_25;
	}
	public void setSfcMbRate21_25(String sfcMbRate21_25) {
		this.sfcMbRate21_25 = sfcMbRate21_25;
	}
	public String getSfcMbRate26() {
		return sfcMbRate26;
	}
	public void setSfcMbRate26(String sfcMbRate26) {
		this.sfcMbRate26 = sfcMbRate26;
	}
	public String getSfcTgRate1_5() {
		return sfcTgRate1_5;
	}
	public void setSfcTgRate1_5(String sfcTgRate1_5) {
		this.sfcTgRate1_5 = sfcTgRate1_5;
	}
	public String getSfcTgRate6_10() {
		return sfcTgRate6_10;
	}
	public void setSfcTgRate6_10(String sfcTgRate6_10) {
		this.sfcTgRate6_10 = sfcTgRate6_10;
	}
	public String getSfcTgRate11_15() {
		return sfcTgRate11_15;
	}
	public void setSfcTgRate11_15(String sfcTgRate11_15) {
		this.sfcTgRate11_15 = sfcTgRate11_15;
	}
	public String getSfcTgRate16_20() {
		return sfcTgRate16_20;
	}
	public void setSfcTgRate16_20(String sfcTgRate16_20) {
		this.sfcTgRate16_20 = sfcTgRate16_20;
	}
	public String getSfcTgRate21_25() {
		return sfcTgRate21_25;
	}
	public void setSfcTgRate21_25(String sfcTgRate21_25) {
		this.sfcTgRate21_25 = sfcTgRate21_25;
	}
	public String getSfcTgRate26() {
		return sfcTgRate26;
	}
	public void setSfcTgRate26(String sfcTgRate26) {
		this.sfcTgRate26 = sfcTgRate26;
	}
	public String getDxfBigRate() {
		return dxfBigRate;
	}
	public void setDxfBigRate(String dxfBigRate) {
		this.dxfBigRate = dxfBigRate;
	}
	public String getDxfSmallRate() {
		return dxfSmallRate;
	}
	public void setDxfSmallRate(String dxfSmallRate) {
		this.dxfSmallRate = dxfSmallRate;
	}
	public String getDxScore() {
		return dxScore;
	}
	public void setDxScore(String dxScore) {
		this.dxScore = dxScore;
	}
	public String getIntTime() {
		return intTime;
	}
	public void setIntTime(String intTime) {
		this.intTime = intTime;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getmRank() {
		return mRank;
	}
	public void setmRank(String mRank) {
		this.mRank = mRank;
	}
	public String getgRank() {
		return gRank;
	}
	public void setgRank(String gRank) {
		this.gRank = gRank;
	}
	public String getIsSF() {
		return isSF;
	}
	public void setIsSF(String isSF) {
		this.isSF = isSF;
	}
	public String getIsRFSF() {
		return isRFSF;
	}
	public void setIsRFSF(String isRFSF) {
		this.isRFSF = isRFSF;
	}
	public String getIsSFC() {
		return isSFC;
	}
	public void setIsSFC(String isSFC) {
		this.isSFC = isSFC;
	}
	public String getIsDX() {
		return isDX;
	}
	public void setIsDX(String isDX) {
		this.isDX = isDX;
	}
	public String getHtRefData() {
		return htRefData;
	}
	public void setHtRefData(String htRefData) {
		this.htRefData = htRefData;
	}
	
	
	
	
	
}
