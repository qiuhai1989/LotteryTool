package com.yuncai.modules.lottery.model.Oracle;

/**
 * 足球即时比分-竞彩
 */

@SuppressWarnings("serial")
public class FtImsSfc implements java.io.Serializable {

	// Fields

	private int id; 
	private int cc;
	private String leagueName;
	private String color;
    private String round; 
    private String matchDate; 
    private String status; //状态：17未/4完/3正在开打/13延
    private String mbRank; 
    private String mbYellowcard; 
    private String mbRedcard; 
    private String mbInball; 
    private String tgInball; 
    private String tgRank; 
    private String tgYellowcard; 
    private String tgRedcard; 
    private String hinball; 
    private int zsid;
    private int leagueId;
    private int mbId;
    private int tgId;
    private int atype;
    private String owr; 
    private String odr; 
    private String olr; 
    private String ymr; 
    private String ypk; 
    private String ygr; 
    private String ytype; 
    private String mbName; 
    private String tgName; 
    private String expect;
    private String sid;
    private String startTime;
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

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	public int getMbId() {
		return mbId;
	}

	public void setMbId(int mbId) {
		this.mbId = mbId;
	}

	public int getTgId() {
		return tgId;
	}

	public void setTgId(int tgId) {
		this.tgId = tgId;
	}

	public int getAtype() {
		return atype;
	}

	public void setAtype(int atype) {
		this.atype = atype;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getZsid() {
		return zsid;
	}

	public void setZsid(int zsid) {
		this.zsid = zsid;
	}
	
	public String getOwr() {
		return owr;
	}

	public void setOwr(String owr) {
		this.owr = owr;
	}

	public String getOdr() {
		return odr;
	}

	public void setOdr(String odr) {
		this.odr = odr;
	}

	public String getOlr() {
		return olr;
	}

	public void setOlr(String olr) {
		this.olr = olr;
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

	public String getYtype() {
		return ytype;
	}

	public void setYtype(String ytype) {
		this.ytype = ytype;
	}

	public String getMbName() {
		return mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}
	
	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getSimDate() {
		return simDate;
	}

	public void setSimDate(String simDate) {
		this.simDate = simDate;
	}

	public FtImsSfc(int cc, String leagueName, String color, String round, String matchDate, String status, String mbRank, String mbYellowcard,
			String mbRedcard, String mbInball, String tgInball, String tgRank, String tgYellowcard, String tgRedcard, String hinball, int zsid,
			int atype,String ymr, String ypk, String ygr, String ytype,String expect,String simDate) {
		super();
		this.cc = cc;
		this.leagueName = leagueName;
		this.color = color;
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
		this.zsid = zsid;
		this.atype = atype;
		this.ymr = ymr;
		this.ypk = ypk;
		this.ygr = ygr;
		this.ytype = ytype;
		this.expect=expect;
		this.simDate=simDate;
	}

	public FtImsSfc() {
		super();
	}
	
}