package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**比分
 * FtBfAward entity. @author MyEclipse Persistence Tools
 */

public class FtBfAward extends FtAward implements java.io.Serializable {

	// Fields

	private Double s10award;
	private Double s20award;
	private Double s21award;
	private Double s30award;
	private Double s31award;
	private Double s32award;
	private Double s40award;
	private Double s41award;
	private Double s42award;
	private Double s50award;
	private Double s51award;
	private Double s52award;
	private Double SWinAward;
	private Double s00award;
	private Double s11award;
	private Double s22award;
	private Double s33award;
	private Double SDrawAward;
	private Double s01award;
	private Double s02award;
	private Double s12award;
	private Double s03award;
	private Double s13award;
	private Double s23award;
	private Double s04award;
	private Double s14award;
	private Double s24award;
	private Double s05award;
	private Double s15award;
	private Double s25award;
	private Double SLoseAward;
	
	private Integer isTrue;
	// Constructors

	/** default constructor */
	public FtBfAward() {
	}

	public Double getS10award() {
		return s10award;
	}

	public void setS10award(Double s10award) {
		this.s10award = s10award;
	}

	public Double getS20award() {
		return s20award;
	}

	public void setS20award(Double s20award) {
		this.s20award = s20award;
	}

	public Double getS21award() {
		return s21award;
	}

	public void setS21award(Double s21award) {
		this.s21award = s21award;
	}

	public Double getS30award() {
		return s30award;
	}

	public void setS30award(Double s30award) {
		this.s30award = s30award;
	}

	public Double getS31award() {
		return s31award;
	}

	public void setS31award(Double s31award) {
		this.s31award = s31award;
	}

	public Double getS32award() {
		return s32award;
	}

	public void setS32award(Double s32award) {
		this.s32award = s32award;
	}

	public Double getS40award() {
		return s40award;
	}

	public void setS40award(Double s40award) {
		this.s40award = s40award;
	}

	public Double getS41award() {
		return s41award;
	}

	public void setS41award(Double s41award) {
		this.s41award = s41award;
	}

	public Double getS42award() {
		return s42award;
	}

	public void setS42award(Double s42award) {
		this.s42award = s42award;
	}

	public Double getS50award() {
		return s50award;
	}

	public void setS50award(Double s50award) {
		this.s50award = s50award;
	}

	public Double getS51award() {
		return s51award;
	}

	public void setS51award(Double s51award) {
		this.s51award = s51award;
	}

	public Double getS52award() {
		return s52award;
	}

	public void setS52award(Double s52award) {
		this.s52award = s52award;
	}

	public Double getSWinAward() {
		return SWinAward;
	}

	public void setSWinAward(Double sWinAward) {
		SWinAward = sWinAward;
	}

	public Double getS00award() {
		return s00award;
	}

	public void setS00award(Double s00award) {
		this.s00award = s00award;
	}

	public Double getS11award() {
		return s11award;
	}

	public void setS11award(Double s11award) {
		this.s11award = s11award;
	}

	public Double getS22award() {
		return s22award;
	}

	public void setS22award(Double s22award) {
		this.s22award = s22award;
	}

	public Double getS33award() {
		return s33award;
	}

	public void setS33award(Double s33award) {
		this.s33award = s33award;
	}

	public Double getSDrawAward() {
		return SDrawAward;
	}

	public void setSDrawAward(Double sDrawAward) {
		SDrawAward = sDrawAward;
	}

	public Double getS01award() {
		return s01award;
	}

	public void setS01award(Double s01award) {
		this.s01award = s01award;
	}

	public Double getS02award() {
		return s02award;
	}

	public void setS02award(Double s02award) {
		this.s02award = s02award;
	}

	public Double getS12award() {
		return s12award;
	}

	public void setS12award(Double s12award) {
		this.s12award = s12award;
	}

	public Double getS03award() {
		return s03award;
	}

	public void setS03award(Double s03award) {
		this.s03award = s03award;
	}

	public Double getS13award() {
		return s13award;
	}

	public void setS13award(Double s13award) {
		this.s13award = s13award;
	}

	public Double getS23award() {
		return s23award;
	}

	public void setS23award(Double s23award) {
		this.s23award = s23award;
	}

	public Double getS04award() {
		return s04award;
	}

	public void setS04award(Double s04award) {
		this.s04award = s04award;
	}

	public Double getS14award() {
		return s14award;
	}

	public void setS14award(Double s14award) {
		this.s14award = s14award;
	}

	public Double getS24award() {
		return s24award;
	}

	public void setS24award(Double s24award) {
		this.s24award = s24award;
	}

	public Double getS05award() {
		return s05award;
	}

	public void setS05award(Double s05award) {
		this.s05award = s05award;
	}

	public Double getS15award() {
		return s15award;
	}

	public void setS15award(Double s15award) {
		this.s15award = s15award;
	}

	public Double getS25award() {
		return s25award;
	}

	public void setS25award(Double s25award) {
		this.s25award = s25award;
	}

	public Double getSLoseAward() {
		return SLoseAward;
	}

	public void setSLoseAward(Double sLoseAward) {
		SLoseAward = sLoseAward;
	}

	public Integer getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Integer isTrue) {
		this.isTrue = isTrue;
	}

	public FtBfAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double s10award, Double s20award, Double s21award,
			Double s30award, Double s31award, Double s32award,
			Double s40award, Double s41award, Double s42award,
			Double s50award, Double s51award, Double s52award,
			Double sWinAward, Double s00award, Double s11award,
			Double s22award, Double s33award, Double sDrawAward,
			Double s01award, Double s02award, Double s12award,
			Double s03award, Double s13award, Double s23award,
			Double s04award, Double s14award, Double s24award,
			Double s05award, Double s15award, Double s25award,
			Double sLoseAward) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.s10award = s10award;
		this.s20award = s20award;
		this.s21award = s21award;
		this.s30award = s30award;
		this.s31award = s31award;
		this.s32award = s32award;
		this.s40award = s40award;
		this.s41award = s41award;
		this.s42award = s42award;
		this.s50award = s50award;
		this.s51award = s51award;
		this.s52award = s52award;
		SWinAward = sWinAward;
		this.s00award = s00award;
		this.s11award = s11award;
		this.s22award = s22award;
		this.s33award = s33award;
		SDrawAward = sDrawAward;
		this.s01award = s01award;
		this.s02award = s02award;
		this.s12award = s12award;
		this.s03award = s03award;
		this.s13award = s13award;
		this.s23award = s23award;
		this.s04award = s04award;
		this.s14award = s14award;
		this.s24award = s24award;
		this.s05award = s05award;
		this.s15award = s15award;
		this.s25award = s25award;
		SLoseAward = sLoseAward;
	}

	
	public void setFtBfAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double s10award, Double s20award, Double s21award,
			Double s30award, Double s31award, Double s32award,
			Double s40award, Double s41award, Double s42award,
			Double s50award, Double s51award, Double s52award,
			Double sWinAward, Double s00award, Double s11award,
			Double s22award, Double s33award, Double sDrawAward,
			Double s01award, Double s02award, Double s12award,
			Double s03award, Double s13award, Double s23award,
			Double s04award, Double s14award, Double s24award,
			Double s05award, Double s15award, Double s25award,
			Double sLoseAward) {
		this.setMid(mid);
		this.setInttime(inttime);
		this.setLineid(lineid);
		this.setMatchtime(matchtime);
		this.setPassmode(passmode);
		this.setLastupdatetime(lastupdatetime);
		this.s10award = s10award;
		this.s20award = s20award;
		this.s21award = s21award;
		this.s30award = s30award;
		this.s31award = s31award;
		this.s32award = s32award;
		this.s40award = s40award;
		this.s41award = s41award;
		this.s42award = s42award;
		this.s50award = s50award;
		this.s51award = s51award;
		this.s52award = s52award;
		SWinAward = sWinAward;
		this.s00award = s00award;
		this.s11award = s11award;
		this.s22award = s22award;
		this.s33award = s33award;
		SDrawAward = sDrawAward;
		this.s01award = s01award;
		this.s02award = s02award;
		this.s12award = s12award;
		this.s03award = s03award;
		this.s13award = s13award;
		this.s23award = s23award;
		this.s04award = s04award;
		this.s14award = s14award;
		this.s24award = s24award;
		this.s05award = s05award;
		this.s15award = s15award;
		this.s25award = s25award;
		SLoseAward = sLoseAward;
	}

	public FtBfAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,Double s10award, Double s20award, Double s21award, Double s30award, Double s31award, Double s32award, Double s40award,
			Double s41award, Double s42award, Double s50award, Double s51award, Double s52award, Double winAward, Double s00award, Double s11award,
			Double s22award, Double s33award, Double drawAward, Double s01award, Double s02award, Double s12award, Double s03award, Double s13award,
			Double s23award, Double s04award, Double s14award, Double s24award, Double s05award, Double s15award, Double s25award, Double loseAward,
			Integer isTrue) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.s10award = s10award;
		this.s20award = s20award;
		this.s21award = s21award;
		this.s30award = s30award;
		this.s31award = s31award;
		this.s32award = s32award;
		this.s40award = s40award;
		this.s41award = s41award;
		this.s42award = s42award;
		this.s50award = s50award;
		this.s51award = s51award;
		this.s52award = s52award;
		SWinAward = winAward;
		this.s00award = s00award;
		this.s11award = s11award;
		this.s22award = s22award;
		this.s33award = s33award;
		SDrawAward = drawAward;
		this.s01award = s01award;
		this.s02award = s02award;
		this.s12award = s12award;
		this.s03award = s03award;
		this.s13award = s13award;
		this.s23award = s23award;
		this.s04award = s04award;
		this.s14award = s14award;
		this.s24award = s24award;
		this.s05award = s05award;
		this.s15award = s15award;
		this.s25award = s25award;
		SLoseAward = loseAward;
		this.isTrue = isTrue;
	}

	@Override
	public Double[] getAwardArr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAwardArr(Double[] awardArr) {
		// TODO Auto-generated method stub
		
	}
}