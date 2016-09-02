package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

/**总进球
 * FtJqsAward entity. @author MyEclipse Persistence Tools
 */

public class FtJqsAward extends FtAward implements java.io.Serializable {

	// Fields

	private Double s0Award;
	private Double s1Award;
	private Double s2Award;
	private Double s3Award;
	private Double s4Award;
	private Double s5Award;
	private Double s6Award;
	private Double s7Award;
	private Integer isTrue;
	// Constructors

	/** default constructor */
	public FtJqsAward() {
	}

	public Double getS0Award() {
		return s0Award;
	}

	public void setS0Award(Double s0Award) {
		this.s0Award = s0Award;
	}

	public Double getS1Award() {
		return s1Award;
	}

	public void setS1Award(Double s1Award) {
		this.s1Award = s1Award;
	}

	public Double getS2Award() {
		return s2Award;
	}

	public void setS2Award(Double s2Award) {
		this.s2Award = s2Award;
	}

	public Double getS3Award() {
		return s3Award;
	}

	public void setS3Award(Double s3Award) {
		this.s3Award = s3Award;
	}

	public Double getS4Award() {
		return s4Award;
	}

	public void setS4Award(Double s4Award) {
		this.s4Award = s4Award;
	}

	public Double getS5Award() {
		return s5Award;
	}

	public void setS5Award(Double s5Award) {
		this.s5Award = s5Award;
	}

	public Double getS6Award() {
		return s6Award;
	}

	public void setS6Award(Double s6Award) {
		this.s6Award = s6Award;
	}

	public Double getS7Award() {
		return s7Award;
	}

	public void setS7Award(Double s7Award) {
		this.s7Award = s7Award;
	}

	public FtJqsAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double s0Award, Double s1Award, Double s2Award, Double s3Award,
			Double s4Award, Double s5Award, Double s6Award, Double s7Award) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		this.s0Award = s0Award;
		this.s1Award = s1Award;
		this.s2Award = s2Award;
		this.s3Award = s3Award;
		this.s4Award = s4Award;
		this.s5Award = s5Award;
		this.s6Award = s6Award;
		this.s7Award = s7Award;
	}
	
	public void setFtJqsAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,
			Double s0Award, Double s1Award, Double s2Award, Double s3Award,
			Double s4Award, Double s5Award, Double s6Award, Double s7Award) {
		this.setMid(mid);
		this.setInttime(inttime);
		this.setLineid(lineid);
		this.setMatchtime(matchtime);
		this.setPassmode(passmode);
		this.setLastupdatetime(lastupdatetime);
		this.s0Award = s0Award;
		this.s1Award = s1Award;
		this.s2Award = s2Award;
		this.s3Award = s3Award;
		this.s4Award = s4Award;
		this.s5Award = s5Award;
		this.s6Award = s6Award;
		this.s7Award = s7Award;
	}

	public FtJqsAward(Integer mid, Integer inttime, String lineid,
			String matchtime, Integer passmode, Date lastupdatetime,Double award, Double award2, Double award3, Double award4, Double award5, Double award6, Double award7, Double award8,
			Integer isTrue) {
		super(mid, inttime, lineid, matchtime, passmode, lastupdatetime);
		s0Award = award;
		s1Award = award2;
		s2Award = award3;
		s3Award = award4;
		s4Award = award5;
		s5Award = award6;
		s6Award = award7;
		s7Award = award8;
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