package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**半全场
 * FtBqcAward entity. @author MyEclipse Persistence Tools
 */

public class FtBqcAward extends FtAward {

	// Fields

	private Double winWinAward;
	private Double winDrawAward;
	private Double winLoseAward;
	private Double drawWinAward;
	private Double drawDrawAward;
	private Double drawLoseAward;
	private Double loseWinAward;
	private Double loseDrawAward;
	private Double loseLoseAward;
	
	private Integer isTrue;
	// Constructors

	/** default constructor */
	public FtBqcAward() {
	}

	public Double getWinWinAward() {
		return winWinAward;
	}

	public void setWinWinAward(Double winWinAward) {
		this.winWinAward = winWinAward;
	}

	public Double getWinDrawAward() {
		return winDrawAward;
	}

	public void setWinDrawAward(Double winDrawAward) {
		this.winDrawAward = winDrawAward;
	}

	public Double getWinLoseAward() {
		return winLoseAward;
	}

	public void setWinLoseAward(Double winLoseAward) {
		this.winLoseAward = winLoseAward;
	}

	public Double getDrawWinAward() {
		return drawWinAward;
	}

	public void setDrawWinAward(Double drawWinAward) {
		this.drawWinAward = drawWinAward;
	}

	public Double getDrawDrawAward() {
		return drawDrawAward;
	}

	public void setDrawDrawAward(Double drawDrawAward) {
		this.drawDrawAward = drawDrawAward;
	}

	public Double getDrawLoseAward() {
		return drawLoseAward;
	}

	public void setDrawLoseAward(Double drawLoseAward) {
		this.drawLoseAward = drawLoseAward;
	}

	public Double getLoseWinAward() {
		return loseWinAward;
	}

	public void setLoseWinAward(Double loseWinAward) {
		this.loseWinAward = loseWinAward;
	}

	public Double getLoseDrawAward() {
		return loseDrawAward;
	}

	public void setLoseDrawAward(Double loseDrawAward) {
		this.loseDrawAward = loseDrawAward;
	}

	public Double getLoseLoseAward() {
		return loseLoseAward;
	}

	public void setLoseLoseAward(Double loseLoseAward) {
		this.loseLoseAward = loseLoseAward;
	}

	public FtBqcAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double winWinAward, Double winDrawAward, Double winLoseAward,
			Double drawWinAward, Double drawDrawAward, Double drawLoseAward,
			Double loseWinAward, Double loseDrawAward, Double loseLoseAward) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.winWinAward = winWinAward;
		this.winDrawAward = winDrawAward;
		this.winLoseAward = winLoseAward;
		this.drawWinAward = drawWinAward;
		this.drawDrawAward = drawDrawAward;
		this.drawLoseAward = drawLoseAward;
		this.loseWinAward = loseWinAward;
		this.loseDrawAward = loseDrawAward;
		this.loseLoseAward = loseLoseAward;
	}
	
	public void setFtBqcAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double winWinAward, Double winDrawAward, Double winLoseAward,
			Double drawWinAward, Double drawDrawAward, Double drawLoseAward,
			Double loseWinAward, Double loseDrawAward, Double loseLoseAward) {
		this.setMid(mid);
		this.setInttime(inttime);
		this.setLineid(lineid);
		this.setMatchtime(matchtime);
		this.setPassmode(passmode);
		this.setLastupdatetime(lastupdatetime);
		this.winWinAward = winWinAward;
		this.winDrawAward = winDrawAward;
		this.winLoseAward = winLoseAward;
		this.drawWinAward = drawWinAward;
		this.drawDrawAward = drawDrawAward;
		this.drawLoseAward = drawLoseAward;
		this.loseWinAward = loseWinAward;
		this.loseDrawAward = loseDrawAward;
		this.loseLoseAward = loseLoseAward;
	}

	public FtBqcAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,Double winWinAward, Double winDrawAward, Double winLoseAward, Double drawWinAward, Double drawDrawAward, Double drawLoseAward,
			Double loseWinAward, Double loseDrawAward, Double loseLoseAward, Integer isTrue) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.winWinAward = winWinAward;
		this.winDrawAward = winDrawAward;
		this.winLoseAward = winLoseAward;
		this.drawWinAward = drawWinAward;
		this.drawDrawAward = drawDrawAward;
		this.drawLoseAward = drawLoseAward;
		this.loseWinAward = loseWinAward;
		this.loseDrawAward = loseDrawAward;
		this.loseLoseAward = loseLoseAward;
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

	public Integer getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Integer isTrue) {
		this.isTrue = isTrue;
	}
}