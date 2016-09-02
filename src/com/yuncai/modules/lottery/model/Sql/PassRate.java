package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * 足球单关
 */

public class PassRate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String day;
	private String matchNumber;
	private Date matchDate;
	private String game;
	private String mainTeam;
	private String guestTeam;
	private Integer matchId;
	private Double win;//让球胜sp
	private String winDirection;
	private Double flat;//让球平sp
	private String flatDirection;
	private Double lose;//让球负sp
	private String loseDirection;
	private Double s10;
	private String s10direction;
	private Double s20;
	private String s20direction;
	private Double s21;
	private String s21direction;
	private Double s30;
	private String s30direction;
	private Double s31;
	private String s31direction;
	private Double s32;
	private String s32direction;
	private Double s40;
	private String s40direction;
	private Double s41;
	private String s41direction;
	private Double s42;
	private String s42direction;
	private Double s50;
	private String s50direction;
	private Double s51;
	private String s51direction;
	private Double s52;
	private String s52direction;
	private Double sother;
	private String sotherDirection;
	private Double p00;
	private String p00direction;
	private Double p11;
	private String p11direction;
	private Double p22;
	private String p22direction;
	private Double p33;
	private String p33direction;
	private Double pother;
	private String potherDirection;
	private Double f01;
	private String f01direction;
	private Double f02;
	private String f02direction;
	private Double f12;
	private String f12direction;
	private Double f03;
	private String f03direction;
	private Double f13;
	private String f13direction;
	private Double f23;
	private String f23direction;
	private Double f04;
	private String f04direction;
	private Double f14;
	private String f14direction;
	private Double f24;
	private String f24direction;
	private Double f05;
	private String f05direction;
	private Double f15;
	private String f15direction;
	private Double f25;
	private String f25direction;
	private Double fother;
	private String fotherDirection;
	private Double in0;
	private String in0direction;
	private Double in1;
	private String in1direction;
	private Double in2;
	private String in2direction;
	private Double in3;
	private String in3direction;
	private Double in4;
	private String in4direction;
	private Double in5;
	private String in5direction;
	private Double in6;
	private String in6direction;
	private Double in7;
	private String in7direction;
	private Double ss;
	private String ssdirection;
	private Double sp;
	private String spdirection;
	private Double sf;
	private String sfdirection;
	private Double ps;
	private String psdirection;
	private Double pp;
	private String ppdirection;
	private Double pf;
	private String pfdirection;
	private Double fs;
	private String fsdirection;
	private Double fp;
	private String fpdirection;
	private Double ff;
	private String ffdirection;
	private Integer mainLoseball; //主队让球数
	private Date stopSellTime;
	private String europeSsp; //平均胜赔率
	private String europePsp;//平均平赔率
	private String europeFsp;//平均负赔率
	private Integer inforMationId;
	private Integer inforMationMainTeamId;
	private Integer inforMationGuestTeamId;
	private Integer inforMationMatchTypeId;
	private String gameColor;
	private Integer snumberCount;
	private Integer pnumberCount;
	private Integer fnumberCount;
	private Boolean isHhad;
	private Boolean isCrs;
	private Boolean isTtg;
	private Boolean isHafu;
	private Boolean state;
	private Integer mid;
	private String winPercent;//不让球胜投注比例
	private String drawPercent;//不让球平投注比例
	private String lostPercent;//不让球负投注比例
	private Integer isSpf;  //是否有不让球胜平负 1.有 0.没有
	private Double spfWin;//胜平负 胜sp
	private String spfWinDirection;
	private Double spfFlat;//胜平负 平sp
	private String spfFlatDirection;
	private Double spfLose;//胜平负 负sp
	private String spfLoseDirection;
	private String rwinPercent;
	private String rdrawPercent;
	private String rlostPercent;
	// Constructors

	/** default constructor */
	public PassRate() {
	}

	/** minimal constructor */
	public PassRate(Integer id) {
		this.id = id;
	}
	
	/** full constructor */
	public PassRate(Integer id, String day, String matchNumber,
			Date matchDate, String game, String mainTeam,
			String guestTeam, Integer matchId, Double win, String winDirection,
			Double flat, String flatDirection, Double lose,
			String loseDirection, Double s10, String s10direction, Double s20,
			String s20direction, Double s21, String s21direction, Double s30,
			String s30direction, Double s31, String s31direction, Double s32,
			String s32direction, Double s40, String s40direction, Double s41,
			String s41direction, Double s42, String s42direction, Double s50,
			String s50direction, Double s51, String s51direction, Double s52,
			String s52direction, Double sother, String sotherDirection,
			Double p00, String p00direction, Double p11, String p11direction,
			Double p22, String p22direction, Double p33, String p33direction,
			Double pother, String potherDirection, Double f01,
			String f01direction, Double f02, String f02direction, Double f12,
			String f12direction, Double f03, String f03direction, Double f13,
			String f13direction, Double f23, String f23direction, Double f04,
			String f04direction, Double f14, String f14direction, Double f24,
			String f24direction, Double f05, String f05direction, Double f15,
			String f15direction, Double f25, String f25direction,
			Double fother, String fotherDirection, Double in0,
			String in0direction, Double in1, String in1direction, Double in2,
			String in2direction, Double in3, String in3direction, Double in4,
			String in4direction, Double in5, String in5direction, Double in6,
			String in6direction, Double in7, String in7direction, Double ss,
			String ssdirection, Double sp, String spdirection, Double sf,
			String sfdirection, Double ps, String psdirection, Double pp,
			String ppdirection, Double pf, String pfdirection, Double fs,
			String fsdirection, Double fp, String fpdirection, Double ff,
			String ffdirection, Integer mainLoseball, Date stopSellTime,
			String europeSsp, String europePsp, String europeFsp,
			Integer inforMationId, Integer inforMationMainTeamId,
			Integer inforMationGuestTeamId, Integer inforMationMatchTypeId,
			String gameColor, Integer snumberCount, Integer pnumberCount,
			Integer fnumberCount, Boolean isHhad, Boolean isCrs, Boolean isTtg,
			Boolean isHafu, Boolean state,Double spfWin, Double spfFlat, Double spfLose,Integer isSpf) {
		this.id = id;
		this.day = day;
		this.matchNumber = matchNumber;
		this.matchDate = matchDate;
		this.game = game;
		this.mainTeam = mainTeam;
		this.guestTeam = guestTeam;
		this.matchId = matchId;
		this.win = win;
		this.winDirection = winDirection;
		this.flat = flat;
		this.flatDirection = flatDirection;
		this.lose = lose;
		this.loseDirection = loseDirection;
		this.s10 = s10;
		this.s10direction = s10direction;
		this.s20 = s20;
		this.s20direction = s20direction;
		this.s21 = s21;
		this.s21direction = s21direction;
		this.s30 = s30;
		this.s30direction = s30direction;
		this.s31 = s31;
		this.s31direction = s31direction;
		this.s32 = s32;
		this.s32direction = s32direction;
		this.s40 = s40;
		this.s40direction = s40direction;
		this.s41 = s41;
		this.s41direction = s41direction;
		this.s42 = s42;
		this.s42direction = s42direction;
		this.s50 = s50;
		this.s50direction = s50direction;
		this.s51 = s51;
		this.s51direction = s51direction;
		this.s52 = s52;
		this.s52direction = s52direction;
		this.sother = sother;
		this.sotherDirection = sotherDirection;
		this.p00 = p00;
		this.p00direction = p00direction;
		this.p11 = p11;
		this.p11direction = p11direction;
		this.p22 = p22;
		this.p22direction = p22direction;
		this.p33 = p33;
		this.p33direction = p33direction;
		this.pother = pother;
		this.potherDirection = potherDirection;
		this.f01 = f01;
		this.f01direction = f01direction;
		this.f02 = f02;
		this.f02direction = f02direction;
		this.f12 = f12;
		this.f12direction = f12direction;
		this.f03 = f03;
		this.f03direction = f03direction;
		this.f13 = f13;
		this.f13direction = f13direction;
		this.f23 = f23;
		this.f23direction = f23direction;
		this.f04 = f04;
		this.f04direction = f04direction;
		this.f14 = f14;
		this.f14direction = f14direction;
		this.f24 = f24;
		this.f24direction = f24direction;
		this.f05 = f05;
		this.f05direction = f05direction;
		this.f15 = f15;
		this.f15direction = f15direction;
		this.f25 = f25;
		this.f25direction = f25direction;
		this.fother = fother;
		this.fotherDirection = fotherDirection;
		this.in0 = in0;
		this.in0direction = in0direction;
		this.in1 = in1;
		this.in1direction = in1direction;
		this.in2 = in2;
		this.in2direction = in2direction;
		this.in3 = in3;
		this.in3direction = in3direction;
		this.in4 = in4;
		this.in4direction = in4direction;
		this.in5 = in5;
		this.in5direction = in5direction;
		this.in6 = in6;
		this.in6direction = in6direction;
		this.in7 = in7;
		this.in7direction = in7direction;
		this.ss = ss;
		this.ssdirection = ssdirection;
		this.sp = sp;
		this.spdirection = spdirection;
		this.sf = sf;
		this.sfdirection = sfdirection;
		this.ps = ps;
		this.psdirection = psdirection;
		this.pp = pp;
		this.ppdirection = ppdirection;
		this.pf = pf;
		this.pfdirection = pfdirection;
		this.fs = fs;
		this.fsdirection = fsdirection;
		this.fp = fp;
		this.fpdirection = fpdirection;
		this.ff = ff;
		this.ffdirection = ffdirection;
		this.mainLoseball = mainLoseball;
		this.stopSellTime = stopSellTime;
		this.europeSsp = europeSsp;
		this.europePsp = europePsp;
		this.europeFsp = europeFsp;
		this.inforMationId = inforMationId;
		this.inforMationMainTeamId = inforMationMainTeamId;
		this.inforMationGuestTeamId = inforMationGuestTeamId;
		this.inforMationMatchTypeId = inforMationMatchTypeId;
		this.gameColor = gameColor;
		this.snumberCount = snumberCount;
		this.pnumberCount = pnumberCount;
		this.fnumberCount = fnumberCount;
		this.isHhad = isHhad;
		this.isCrs = isCrs;
		this.isTtg = isTtg;
		this.isHafu = isHafu;
		this.state = state;
		this.spfWin=spfWin;
		this.spfFlat=spfFlat;
		this.spfLose=spfLose;
		this.isSpf=isSpf;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDay() {
		return this.day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public String getGame() {
		return this.game;
	}

	public void setGame(String game) {
		this.game = game;
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

	public Integer getMatchId() {
		return this.matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Double getWin() {
		return this.win;
	}

	public void setWin(Double win) {
		this.win = win;
	}

	public String getWinDirection() {
		return this.winDirection;
	}

	public void setWinDirection(String winDirection) {
		this.winDirection = winDirection;
	}

	public Double getFlat() {
		return this.flat;
	}

	public void setFlat(Double flat) {
		this.flat = flat;
	}

	public String getFlatDirection() {
		return this.flatDirection;
	}

	public void setFlatDirection(String flatDirection) {
		this.flatDirection = flatDirection;
	}

	public Double getLose() {
		return this.lose;
	}

	public void setLose(Double lose) {
		this.lose = lose;
	}

	public String getLoseDirection() {
		return this.loseDirection;
	}

	public void setLoseDirection(String loseDirection) {
		this.loseDirection = loseDirection;
	}

	public Double getS10() {
		return this.s10;
	}

	public void setS10(Double s10) {
		this.s10 = s10;
	}

	public String getS10direction() {
		return this.s10direction;
	}

	public void setS10direction(String s10direction) {
		this.s10direction = s10direction;
	}

	public Double getS20() {
		return this.s20;
	}

	public void setS20(Double s20) {
		this.s20 = s20;
	}

	public String getS20direction() {
		return this.s20direction;
	}

	public void setS20direction(String s20direction) {
		this.s20direction = s20direction;
	}

	public Double getS21() {
		return this.s21;
	}

	public void setS21(Double s21) {
		this.s21 = s21;
	}

	public String getS21direction() {
		return this.s21direction;
	}

	public void setS21direction(String s21direction) {
		this.s21direction = s21direction;
	}

	public Double getS30() {
		return this.s30;
	}

	public void setS30(Double s30) {
		this.s30 = s30;
	}

	public String getS30direction() {
		return this.s30direction;
	}

	public void setS30direction(String s30direction) {
		this.s30direction = s30direction;
	}

	public Double getS31() {
		return this.s31;
	}

	public void setS31(Double s31) {
		this.s31 = s31;
	}

	public String getS31direction() {
		return this.s31direction;
	}

	public void setS31direction(String s31direction) {
		this.s31direction = s31direction;
	}

	public Double getS32() {
		return this.s32;
	}

	public void setS32(Double s32) {
		this.s32 = s32;
	}

	public String getS32direction() {
		return this.s32direction;
	}

	public void setS32direction(String s32direction) {
		this.s32direction = s32direction;
	}

	public Double getS40() {
		return this.s40;
	}

	public void setS40(Double s40) {
		this.s40 = s40;
	}

	public String getS40direction() {
		return this.s40direction;
	}

	public void setS40direction(String s40direction) {
		this.s40direction = s40direction;
	}

	public Double getS41() {
		return this.s41;
	}

	public void setS41(Double s41) {
		this.s41 = s41;
	}

	public String getS41direction() {
		return this.s41direction;
	}

	public void setS41direction(String s41direction) {
		this.s41direction = s41direction;
	}

	public Double getS42() {
		return this.s42;
	}

	public void setS42(Double s42) {
		this.s42 = s42;
	}

	public String getS42direction() {
		return this.s42direction;
	}

	public void setS42direction(String s42direction) {
		this.s42direction = s42direction;
	}

	public Double getS50() {
		return this.s50;
	}

	public void setS50(Double s50) {
		this.s50 = s50;
	}

	public String getS50direction() {
		return this.s50direction;
	}

	public void setS50direction(String s50direction) {
		this.s50direction = s50direction;
	}

	public Double getS51() {
		return this.s51;
	}

	public void setS51(Double s51) {
		this.s51 = s51;
	}

	public String getS51direction() {
		return this.s51direction;
	}

	public void setS51direction(String s51direction) {
		this.s51direction = s51direction;
	}

	public Double getS52() {
		return this.s52;
	}

	public void setS52(Double s52) {
		this.s52 = s52;
	}

	public String getS52direction() {
		return this.s52direction;
	}

	public void setS52direction(String s52direction) {
		this.s52direction = s52direction;
	}

	public Double getSother() {
		return this.sother;
	}

	public void setSother(Double sother) {
		this.sother = sother;
	}

	public String getSotherDirection() {
		return this.sotherDirection;
	}

	public void setSotherDirection(String sotherDirection) {
		this.sotherDirection = sotherDirection;
	}

	public Double getP00() {
		return this.p00;
	}

	public void setP00(Double p00) {
		this.p00 = p00;
	}

	public String getP00direction() {
		return this.p00direction;
	}

	public void setP00direction(String p00direction) {
		this.p00direction = p00direction;
	}

	public Double getP11() {
		return this.p11;
	}

	public void setP11(Double p11) {
		this.p11 = p11;
	}

	public String getP11direction() {
		return this.p11direction;
	}

	public void setP11direction(String p11direction) {
		this.p11direction = p11direction;
	}

	public Double getP22() {
		return this.p22;
	}

	public void setP22(Double p22) {
		this.p22 = p22;
	}

	public String getP22direction() {
		return this.p22direction;
	}

	public void setP22direction(String p22direction) {
		this.p22direction = p22direction;
	}

	public Double getP33() {
		return this.p33;
	}

	public void setP33(Double p33) {
		this.p33 = p33;
	}

	public String getP33direction() {
		return this.p33direction;
	}

	public void setP33direction(String p33direction) {
		this.p33direction = p33direction;
	}

	public Double getPother() {
		return this.pother;
	}

	public void setPother(Double pother) {
		this.pother = pother;
	}

	public String getPotherDirection() {
		return this.potherDirection;
	}

	public void setPotherDirection(String potherDirection) {
		this.potherDirection = potherDirection;
	}

	public Double getF01() {
		return this.f01;
	}

	public void setF01(Double f01) {
		this.f01 = f01;
	}

	public String getF01direction() {
		return this.f01direction;
	}

	public void setF01direction(String f01direction) {
		this.f01direction = f01direction;
	}

	public Double getF02() {
		return this.f02;
	}

	public void setF02(Double f02) {
		this.f02 = f02;
	}

	public String getF02direction() {
		return this.f02direction;
	}

	public void setF02direction(String f02direction) {
		this.f02direction = f02direction;
	}

	public Double getF12() {
		return this.f12;
	}

	public void setF12(Double f12) {
		this.f12 = f12;
	}

	public String getF12direction() {
		return this.f12direction;
	}

	public void setF12direction(String f12direction) {
		this.f12direction = f12direction;
	}

	public Double getF03() {
		return this.f03;
	}

	public void setF03(Double f03) {
		this.f03 = f03;
	}

	public String getF03direction() {
		return this.f03direction;
	}

	public void setF03direction(String f03direction) {
		this.f03direction = f03direction;
	}

	public Double getF13() {
		return this.f13;
	}

	public void setF13(Double f13) {
		this.f13 = f13;
	}

	public String getF13direction() {
		return this.f13direction;
	}

	public void setF13direction(String f13direction) {
		this.f13direction = f13direction;
	}

	public Double getF23() {
		return this.f23;
	}

	public void setF23(Double f23) {
		this.f23 = f23;
	}

	public String getF23direction() {
		return this.f23direction;
	}

	public void setF23direction(String f23direction) {
		this.f23direction = f23direction;
	}

	public Double getF04() {
		return this.f04;
	}

	public void setF04(Double f04) {
		this.f04 = f04;
	}

	public String getF04direction() {
		return this.f04direction;
	}

	public void setF04direction(String f04direction) {
		this.f04direction = f04direction;
	}

	public Double getF14() {
		return this.f14;
	}

	public void setF14(Double f14) {
		this.f14 = f14;
	}

	public String getF14direction() {
		return this.f14direction;
	}

	public void setF14direction(String f14direction) {
		this.f14direction = f14direction;
	}

	public Double getF24() {
		return this.f24;
	}

	public void setF24(Double f24) {
		this.f24 = f24;
	}

	public String getF24direction() {
		return this.f24direction;
	}

	public void setF24direction(String f24direction) {
		this.f24direction = f24direction;
	}

	public Double getF05() {
		return this.f05;
	}

	public void setF05(Double f05) {
		this.f05 = f05;
	}

	public String getF05direction() {
		return this.f05direction;
	}

	public void setF05direction(String f05direction) {
		this.f05direction = f05direction;
	}

	public Double getF15() {
		return this.f15;
	}

	public void setF15(Double f15) {
		this.f15 = f15;
	}

	public String getF15direction() {
		return this.f15direction;
	}

	public void setF15direction(String f15direction) {
		this.f15direction = f15direction;
	}

	public Double getF25() {
		return this.f25;
	}

	public void setF25(Double f25) {
		this.f25 = f25;
	}

	public String getF25direction() {
		return this.f25direction;
	}

	public void setF25direction(String f25direction) {
		this.f25direction = f25direction;
	}

	public Double getFother() {
		return this.fother;
	}

	public void setFother(Double fother) {
		this.fother = fother;
	}

	public String getFotherDirection() {
		return this.fotherDirection;
	}

	public void setFotherDirection(String fotherDirection) {
		this.fotherDirection = fotherDirection;
	}

	public Double getIn0() {
		return this.in0;
	}

	public void setIn0(Double in0) {
		this.in0 = in0;
	}

	public String getIn0direction() {
		return this.in0direction;
	}

	public void setIn0direction(String in0direction) {
		this.in0direction = in0direction;
	}

	public Double getIn1() {
		return this.in1;
	}

	public void setIn1(Double in1) {
		this.in1 = in1;
	}

	public String getIn1direction() {
		return this.in1direction;
	}

	public void setIn1direction(String in1direction) {
		this.in1direction = in1direction;
	}

	public Double getIn2() {
		return this.in2;
	}

	public void setIn2(Double in2) {
		this.in2 = in2;
	}

	public String getIn2direction() {
		return this.in2direction;
	}

	public void setIn2direction(String in2direction) {
		this.in2direction = in2direction;
	}

	public Double getIn3() {
		return this.in3;
	}

	public void setIn3(Double in3) {
		this.in3 = in3;
	}

	public String getIn3direction() {
		return this.in3direction;
	}

	public void setIn3direction(String in3direction) {
		this.in3direction = in3direction;
	}

	public Double getIn4() {
		return this.in4;
	}

	public void setIn4(Double in4) {
		this.in4 = in4;
	}

	public String getIn4direction() {
		return this.in4direction;
	}

	public void setIn4direction(String in4direction) {
		this.in4direction = in4direction;
	}

	public Double getIn5() {
		return this.in5;
	}

	public void setIn5(Double in5) {
		this.in5 = in5;
	}

	public String getIn5direction() {
		return this.in5direction;
	}

	public void setIn5direction(String in5direction) {
		this.in5direction = in5direction;
	}

	public Double getIn6() {
		return this.in6;
	}

	public void setIn6(Double in6) {
		this.in6 = in6;
	}

	public String getIn6direction() {
		return this.in6direction;
	}

	public void setIn6direction(String in6direction) {
		this.in6direction = in6direction;
	}

	public Double getIn7() {
		return this.in7;
	}

	public void setIn7(Double in7) {
		this.in7 = in7;
	}

	public String getIn7direction() {
		return this.in7direction;
	}

	public void setIn7direction(String in7direction) {
		this.in7direction = in7direction;
	}

	public Double getSs() {
		return this.ss;
	}

	public void setSs(Double ss) {
		this.ss = ss;
	}

	public String getSsdirection() {
		return this.ssdirection;
	}

	public void setSsdirection(String ssdirection) {
		this.ssdirection = ssdirection;
	}

	public Double getSp() {
		return this.sp;
	}

	public void setSp(Double sp) {
		this.sp = sp;
	}

	public String getSpdirection() {
		return this.spdirection;
	}

	public void setSpdirection(String spdirection) {
		this.spdirection = spdirection;
	}

	public Double getSf() {
		return this.sf;
	}

	public void setSf(Double sf) {
		this.sf = sf;
	}

	public String getSfdirection() {
		return this.sfdirection;
	}

	public void setSfdirection(String sfdirection) {
		this.sfdirection = sfdirection;
	}

	public Double getPs() {
		return this.ps;
	}

	public void setPs(Double ps) {
		this.ps = ps;
	}

	public String getPsdirection() {
		return this.psdirection;
	}

	public void setPsdirection(String psdirection) {
		this.psdirection = psdirection;
	}

	public Double getPp() {
		return this.pp;
	}

	public void setPp(Double pp) {
		this.pp = pp;
	}

	public String getPpdirection() {
		return this.ppdirection;
	}

	public void setPpdirection(String ppdirection) {
		this.ppdirection = ppdirection;
	}

	public Double getPf() {
		return this.pf;
	}

	public void setPf(Double pf) {
		this.pf = pf;
	}

	public String getPfdirection() {
		return this.pfdirection;
	}

	public void setPfdirection(String pfdirection) {
		this.pfdirection = pfdirection;
	}

	public Double getFs() {
		return this.fs;
	}

	public void setFs(Double fs) {
		this.fs = fs;
	}

	public String getFsdirection() {
		return this.fsdirection;
	}

	public void setFsdirection(String fsdirection) {
		this.fsdirection = fsdirection;
	}

	public Double getFp() {
		return this.fp;
	}

	public void setFp(Double fp) {
		this.fp = fp;
	}

	public String getFpdirection() {
		return this.fpdirection;
	}

	public void setFpdirection(String fpdirection) {
		this.fpdirection = fpdirection;
	}

	public Double getFf() {
		return this.ff;
	}

	public void setFf(Double ff) {
		this.ff = ff;
	}

	public String getFfdirection() {
		return this.ffdirection;
	}

	public void setFfdirection(String ffdirection) {
		this.ffdirection = ffdirection;
	}

	public Integer getMainLoseball() {
		return this.mainLoseball;
	}

	public void setMainLoseball(Integer mainLoseball) {
		this.mainLoseball = mainLoseball;
	}

	public Date getStopSellTime() {
		return this.stopSellTime;
	}

	public void setStopSellTime(Date stopSellTime) {
		this.stopSellTime = stopSellTime;
	}

	public String getEuropeSsp() {
		return this.europeSsp;
	}

	public void setEuropeSsp(String europeSsp) {
		this.europeSsp = europeSsp;
	}

	public String getEuropePsp() {
		return this.europePsp;
	}

	public void setEuropePsp(String europePsp) {
		this.europePsp = europePsp;
	}

	public String getEuropeFsp() {
		return this.europeFsp;
	}

	public void setEuropeFsp(String europeFsp) {
		this.europeFsp = europeFsp;
	}

	public Integer getInforMationId() {
		return this.inforMationId;
	}

	public void setInforMationId(Integer inforMationId) {
		this.inforMationId = inforMationId;
	}

	public Integer getInforMationMainTeamId() {
		return this.inforMationMainTeamId;
	}

	public void setInforMationMainTeamId(Integer inforMationMainTeamId) {
		this.inforMationMainTeamId = inforMationMainTeamId;
	}

	public Integer getInforMationGuestTeamId() {
		return this.inforMationGuestTeamId;
	}

	public void setInforMationGuestTeamId(Integer inforMationGuestTeamId) {
		this.inforMationGuestTeamId = inforMationGuestTeamId;
	}

	public Integer getInforMationMatchTypeId() {
		return this.inforMationMatchTypeId;
	}

	public void setInforMationMatchTypeId(Integer inforMationMatchTypeId) {
		this.inforMationMatchTypeId = inforMationMatchTypeId;
	}

	public String getGameColor() {
		return this.gameColor;
	}

	public void setGameColor(String gameColor) {
		this.gameColor = gameColor;
	}

	public Integer getSnumberCount() {
		return this.snumberCount;
	}

	public void setSnumberCount(Integer snumberCount) {
		this.snumberCount = snumberCount;
	}

	public Integer getPnumberCount() {
		return this.pnumberCount;
	}

	public void setPnumberCount(Integer pnumberCount) {
		this.pnumberCount = pnumberCount;
	}

	public Integer getFnumberCount() {
		return this.fnumberCount;
	}

	public void setFnumberCount(Integer fnumberCount) {
		this.fnumberCount = fnumberCount;
	}

	public Boolean getIsHhad() {
		return this.isHhad;
	}

	public void setIsHhad(Boolean isHhad) {
		this.isHhad = isHhad;
	}

	public Boolean getIsCrs() {
		return this.isCrs;
	}

	public void setIsCrs(Boolean isCrs) {
		this.isCrs = isCrs;
	}

	public Boolean getIsTtg() {
		return this.isTtg;
	}

	public void setIsTtg(Boolean isTtg) {
		this.isTtg = isTtg;
	}

	public Boolean getIsHafu() {
		return this.isHafu;
	}

	public void setIsHafu(Boolean isHafu) {
		this.isHafu = isHafu;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}
	
	
	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}
	
	public String getWinPercent() {
		return winPercent;
	}

	public void setWinPercent(String winPercent) {
		this.winPercent = winPercent;
	}

	public String getDrawPercent() {
		return drawPercent;
	}

	public void setDrawPercent(String drawPercent) {
		this.drawPercent = drawPercent;
	}

	public String getLostPercent() {
		return lostPercent;
	}

	public void setLostPercent(String lostPercent) {
		this.lostPercent = lostPercent;
	}

	
	public Integer getIsSpf() {
		return isSpf;
	}

	public void setIsSpf(Integer isSpf) {
		this.isSpf = isSpf;
	}

	public Double getSpfWin() {
		return spfWin;
	}

	public void setSpfWin(Double spfWin) {
		this.spfWin = spfWin;
	}

	public String getSpfWinDirection() {
		return spfWinDirection;
	}

	public void setSpfWinDirection(String spfWinDirection) {
		this.spfWinDirection = spfWinDirection;
	}

	public Double getSpfFlat() {
		return spfFlat;
	}

	public void setSpfFlat(Double spfFlat) {
		this.spfFlat = spfFlat;
	}

	public String getSpfFlatDirection() {
		return spfFlatDirection;
	}

	public void setSpfFlatDirection(String spfFlatDirection) {
		this.spfFlatDirection = spfFlatDirection;
	}

	public Double getSpfLose() {
		return spfLose;
	}

	public void setSpfLose(Double spfLose) {
		this.spfLose = spfLose;
	}

	public String getSpfLoseDirection() {
		return spfLoseDirection;
	}

	public void setSpfLoseDirection(String spfLoseDirection) {
		this.spfLoseDirection = spfLoseDirection;
	}
	
	
	public String getRwinPercent() {
		return rwinPercent;
	}

	public void setRwinPercent(String rwinPercent) {
		this.rwinPercent = rwinPercent;
	}

	public String getRdrawPercent() {
		return rdrawPercent;
	}

	public void setRdrawPercent(String rdrawPercent) {
		this.rdrawPercent = rdrawPercent;
	}

	public String getRlostPercent() {
		return rlostPercent;
	}

	public void setRlostPercent(String rlostPercent) {
		this.rlostPercent = rlostPercent;
	}
	

	/**
	 * 该构造函数用于保存或更新封装
	 * @param day
	 * @param matchNumber
	 * @param matchDate
	 * @param game
	 * @param mainTeam
	 * @param guestTeam
	 * @param matchId
	 * @param win
	 * @param flat
	 * @param lose
	 * @param s10
	 * @param s20
	 * @param s21
	 * @param s30
	 * @param s31
	 * @param s32
	 * @param s40
	 * @param s41
	 * @param s42
	 * @param s50
	 * @param s51
	 * @param s52
	 * @param sother
	 * @param p00
	 * @param p11
	 * @param p22
	 * @param p33
	 * @param pother
	 * @param f01
	 * @param f02
	 * @param f12
	 * @param f03
	 * @param f13
	 * @param f23
	 * @param f04
	 * @param f14
	 * @param f24
	 * @param f05
	 * @param f15
	 * @param f25
	 * @param fother
	 * @param in0
	 * @param in1
	 * @param in2
	 * @param in3
	 * @param in4
	 * @param in5
	 * @param in6
	 * @param in7
	 * @param ss
	 * @param sp
	 * @param sf
	 * @param ps
	 * @param pp
	 * @param pf
	 * @param fs
	 * @param fp
	 * @param ff
	 * @param mainLoseball
	 * @param stopSellTime
	 * @param europeSsp
	 * @param europePsp
	 * @param europeFsp
	 * @param gameColor
	 * @param isHhad
	 * @param isCrs
	 * @param isTtg
	 * @param isHafu
	 * @param state
	 */
	public PassRate(String day, String matchNumber, Date matchDate,
			String game, String mainTeam, String guestTeam, Integer matchId,
			Double win, Double flat, Double lose, Double s10, Double s20,
			Double s21, Double s30, Double s31, Double s32, Double s40,
			Double s41, Double s42, Double s50, Double s51, Double s52,
			Double sother, Double p00, Double p11, Double p22, Double p33,
			Double pother, Double f01, Double f02, Double f12, Double f03,
			Double f13, Double f23, Double f04, Double f14, Double f24,
			Double f05, Double f15, Double f25, Double fother, Double in0,
			Double in1, Double in2, Double in3, Double in4, Double in5,
			Double in6, Double in7, Double ss, Double sp, Double sf, Double ps,
			Double pp, Double pf, Double fs, Double fp, Double ff,
			Integer mainLoseball, Date stopSellTime, String europeSsp,
			String europePsp, String europeFsp, String gameColor,
			Boolean isHhad, Boolean isCrs, Boolean isTtg, Boolean isHafu,
			Boolean state,Integer mid,Double spfWin, Double spfFlat, Double spfLose,Integer isSpf) {
		super();
		this.day = day;
		this.matchNumber = matchNumber;
		this.matchDate = matchDate;
		this.game = game;
		this.mainTeam = mainTeam;
		this.guestTeam = guestTeam;
		this.matchId = matchId;
		this.win = win;
		this.flat = flat;
		this.lose = lose;
		this.s10 = s10;
		this.s20 = s20;
		this.s21 = s21;
		this.s30 = s30;
		this.s31 = s31;
		this.s32 = s32;
		this.s40 = s40;
		this.s41 = s41;
		this.s42 = s42;
		this.s50 = s50;
		this.s51 = s51;
		this.s52 = s52;
		this.sother = sother;
		this.p00 = p00;
		this.p11 = p11;
		this.p22 = p22;
		this.p33 = p33;
		this.pother = pother;
		this.f01 = f01;
		this.f02 = f02;
		this.f12 = f12;
		this.f03 = f03;
		this.f13 = f13;
		this.f23 = f23;
		this.f04 = f04;
		this.f14 = f14;
		this.f24 = f24;
		this.f05 = f05;
		this.f15 = f15;
		this.f25 = f25;
		this.fother = fother;
		this.in0 = in0;
		this.in1 = in1;
		this.in2 = in2;
		this.in3 = in3;
		this.in4 = in4;
		this.in5 = in5;
		this.in6 = in6;
		this.in7 = in7;
		this.ss = ss;
		this.sp = sp;
		this.sf = sf;
		this.ps = ps;
		this.pp = pp;
		this.pf = pf;
		this.fs = fs;
		this.fp = fp;
		this.ff = ff;
		this.mainLoseball = mainLoseball;
		this.stopSellTime = stopSellTime;
		this.europeSsp = europeSsp;
		this.europePsp = europePsp;
		this.europeFsp = europeFsp;
		this.gameColor = gameColor;
		this.isHhad = isHhad;
		this.isCrs = isCrs;
		this.isTtg = isTtg;
		this.isHafu = isHafu;
		this.state = state;
		this.mid=mid;
		this.spfWin=spfWin;
		this.spfFlat=spfFlat;
		this.spfLose=spfLose;
		this.isSpf=isSpf;
	}

	//更新SP值封装
	public void setPassRate(String day, String matchNumber, Date matchDate,
			String game, String mainTeam, String guestTeam, Integer matchId,
			Double win, Double flat, Double lose, Double s10, Double s20,
			Double s21, Double s30, Double s31, Double s32, Double s40,
			Double s41, Double s42, Double s50, Double s51, Double s52,
			Double sother, Double p00, Double p11, Double p22, Double p33,
			Double pother, Double f01, Double f02, Double f12, Double f03,
			Double f13, Double f23, Double f04, Double f14, Double f24,
			Double f05, Double f15, Double f25, Double fother, Double in0,
			Double in1, Double in2, Double in3, Double in4, Double in5,
			Double in6, Double in7, Double ss, Double sp, Double sf, Double ps,
			Double pp, Double pf, Double fs, Double fp, Double ff,
			Integer mainLoseball, Date stopSellTime, String gameColor,
			Boolean isHhad, Boolean isCrs, Boolean isTtg, Boolean isHafu,
			Boolean state,Integer mid,Double spfWin, Double spfFlat, Double spfLose,Integer isSpf) {
		this.day = day;
		this.matchNumber = matchNumber;
		this.matchDate = matchDate;
		this.game = game;
		this.mainTeam = mainTeam;
		this.guestTeam = guestTeam;
		this.matchId = matchId;
		this.win = win;
		this.flat = flat;
		this.lose = lose;
		this.s10 = s10;
		this.s20 = s20;
		this.s21 = s21;
		this.s30 = s30;
		this.s31 = s31;
		this.s32 = s32;
		this.s40 = s40;
		this.s41 = s41;
		this.s42 = s42;
		this.s50 = s50;
		this.s51 = s51;
		this.s52 = s52;
		this.sother = sother;
		this.p00 = p00;
		this.p11 = p11;
		this.p22 = p22;
		this.p33 = p33;
		this.pother = pother;
		this.f01 = f01;
		this.f02 = f02;
		this.f12 = f12;
		this.f03 = f03;
		this.f13 = f13;
		this.f23 = f23;
		this.f04 = f04;
		this.f14 = f14;
		this.f24 = f24;
		this.f05 = f05;
		this.f15 = f15;
		this.f25 = f25;
		this.fother = fother;
		this.in0 = in0;
		this.in1 = in1;
		this.in2 = in2;
		this.in3 = in3;
		this.in4 = in4;
		this.in5 = in5;
		this.in6 = in6;
		this.in7 = in7;
		this.ss = ss;
		this.sp = sp;
		this.sf = sf;
		this.ps = ps;
		this.pp = pp;
		this.pf = pf;
		this.fs = fs;
		this.fp = fp;
		this.ff = ff;
		this.mainLoseball = mainLoseball;
		this.stopSellTime = stopSellTime;
		this.gameColor = gameColor;
		this.isHhad = isHhad;
		this.isCrs = isCrs;
		this.isTtg = isTtg;
		this.isHafu = isHafu;
		this.state = state;
		this.mid=mid;
		this.spfWin=spfWin;
		this.spfFlat=spfFlat;
		this.spfLose=spfLose;
		this.isSpf=isSpf;
	}
	
	//更新平均赔率和投注比例封装
	public void setPercentAvgRate(String europeSsp, String europePsp, String europeFsp,
			String winPercent, String drawPercent, String lostPercent) {
		this.europeSsp = europeSsp;
		this.europePsp = europePsp;
		this.europeFsp = europeFsp;
		this.winPercent = winPercent;
		this.drawPercent = drawPercent;
		this.lostPercent = lostPercent;
	}
}