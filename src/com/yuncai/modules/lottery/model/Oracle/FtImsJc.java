package com.yuncai.modules.lottery.model.Oracle;

/**
 * 足球即时比分-竞彩
 */

@SuppressWarnings("serial")
public class FtImsJc implements java.io.Serializable {

	// Fields

	private int id; 
    private String round; 
    private String matchDate; 
    private String status; 
    private String mbRank; 
    private String mbYellowcard; 
    private String mbRedcard; 
    private String mbInball; 
    private String tgInball; 
    private String tgRank; 
    private String tgYellowcard; 
    private String tgRedcard; 
    private String hinball; 
    private int matchId;
    private int zsid;
    private String ymr;
    private String ypk;
    private String ygr;
    private String ytype;
    private String simDate;
    
	// Constructors

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMbRank() {
		return mbRank;
	}

	public void setMbRank(String mbRank) {
		this.mbRank = mbRank;
	}

	public String getMbYellowcard() {
		return mbYellowcard;
	}

	public void setMbYellowcard(String mbYellowcard) {
		this.mbYellowcard = mbYellowcard;
	}

	public String getMbRedcard() {
		return mbRedcard;
	}

	public void setMbRedcard(String mbRedcard) {
		this.mbRedcard = mbRedcard;
	}

	public String getMbInball() {
		return mbInball;
	}

	public void setMbInball(String mbInball) {
		this.mbInball = mbInball;
	}

	public String getTgInball() {
		return tgInball;
	}

	public void setTgInball(String tgInball) {
		this.tgInball = tgInball;
	}

	public String getTgRank() {
		return tgRank;
	}

	public void setTgRank(String tgRank) {
		this.tgRank = tgRank;
	}

	public String getTgYellowcard() {
		return tgYellowcard;
	}

	public void setTgYellowcard(String tgYellowcard) {
		this.tgYellowcard = tgYellowcard;
	}

	public String getTgRedcard() {
		return tgRedcard;
	}

	public void setTgRedcard(String tgRedcard) {
		this.tgRedcard = tgRedcard;
	}
	
	public String getHinball() {
		return hinball;
	}

	public void setHinball(String hinball) {
		this.hinball = hinball;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
	
	public int getZsid() {
		return zsid;
	}

	public void setZsid(int zsid) {
		this.zsid = zsid;
	}
	
	
	public String getYmr() {
		return ymr;
	}

	public void setYmr(String ymr) {
		this.ymr = ymr;
	}

	public String getYpk() {
		return ypk;
	}

	public void setYpk(String ypk) {
		this.ypk = ypk;
	}

	public String getYgr() {
		return ygr;
	}

	public void setYgr(String ygr) {
		this.ygr = ygr;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getYtype() {
		return ytype;
	}

	public void setYtype(String ytype) {
		this.ytype = ytype;
	}
	
	public String getSimDate() {
		return simDate;
	}

	public void setSimDate(String simDate) {
		this.simDate = simDate;
	}

	public FtImsJc(String round, String matchDate, String status, String mbRank, String mbYellowcard, String mbRedcard,
			String mbInball, String tgInball, String tgRank, String tgYellowcard, String tgRedcard, String hinball,
			int matchId,int zsid,String ymr,String ypk,String ygr,String ytype,String simDate) {
		super();
		this.round = round;
		this.matchDate = matchDate;
		this.status = status;
		this.mbRank = mbRank;
		this.mbYellowcard = mbYellowcard;
		this.mbRedcard = mbRedcard;
		this.mbInball = mbInball;
		this.tgInball = tgInball;
		this.tgRank = tgRank;
		this.tgYellowcard = tgYellowcard;
		this.tgRedcard = tgRedcard;
		this.hinball = hinball;
		this.matchId = matchId;
		this.zsid=zsid;
		this.ymr=ymr;
		this.ypk=ypk;
		this.ygr=ygr;
		this.ytype=ytype;
		this.simDate=simDate;
	}

	public FtImsJc() {
		super();
	}
	
}