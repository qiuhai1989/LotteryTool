package com.yuncai.modules.lottery.model.Oracle;

/**
 *  竞彩篮球-即时比分-赛事实体类(500.com)
 * @author blackworm
 *
 */
public class BkImsMatch500 {
	
	private int id;
	//schedules:['','未开始','第1节','第2节','中场','第3节','第4节','加时1','加时2','加时3','加时4','已结束','改期','节间休息']
	private int status;	   //赛事状态：对应上面数组下标
	private int matchId;    //254534
	private int hid;        //46951
	private String startDate;  //2014-01-09
	private String shortTime;  //08:00
	private int atype; 	   //类型，1.竞彩 3.其他
	private String sln;		   //简体联赛名称
	private String tln;		   //繁体联赛名称
	private String lc; 		   //#FF0000
	private String mt; 		   //常规赛
	private int num1;	   //1
	private int leagueId;   //联赛id
	private int tgId;		//客队id
	private String stgName;     //简体客队名称
	private String ttgName;     //繁体客队名称
	private int tgRankPos;	   // 客队排名位置：2->西
	private String tgRank;	   //客队排名：8
	private int mbId;		//主队id
	private String smbName;     //简体主队名称
	private String tmbName;     //繁体主队名称
	private int mbRankPos;	   //客队排名位置：1->东
	private String mbRank;	   //客队排名：1
	
	private int num5;	   //1
	private String matchNo;	   //周三301
	private int num6;	   //1
	private String shortTgName;//客队名称缩写形式
	private String shortMbName;//主队名称缩写形式
	
	private int wid; 		//竞彩篮球未知id，7916
	
	
	private String tgs1;	//客队第1节比分
	private String tgs2;	//客队第2节比分
	private String tgs3;	//客队第3节比分
	private String tgs4;	//客队第4节比分
	private String mbs1;	//主队第1节比分
	private String mbs2;	//主队第2节比分
	private String mbs3;	//主队第3节比分
	private String mbs4;	//主队第4节比分
	private String otherScore;	//00:16.1
	private String wordLive; 	//文字直播：莱莫尔 在对手投篮时犯规 (4 次犯规) (2 次罚球)
	
	private int wleagueId;
	private int wtgId;
	private int wmbId;
	
	private String jcDate; //竞彩日期
	private int mid; 		//竞彩官网mid
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
	public int getHid() {
		return hid;
	}
	public void setHid(int hid) {
		this.hid = hid;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getShortTime() {
		return shortTime;
	}
	public void setShortTime(String shortTime) {
		this.shortTime = shortTime;
	}
	public int getAtype() {
		return atype;
	}
	public void setAtype(int atype) {
		this.atype = atype;
	}
	public String getSln() {
		return sln;
	}
	public void setSln(String sln) {
		this.sln = sln;
	}
	public String getTln() {
		return tln;
	}
	public void setTln(String tln) {
		this.tln = tln;
	}
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	public String getMt() {
		return mt;
	}
	public void setMt(String mt) {
		this.mt = mt;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}
	public int getTgId() {
		return tgId;
	}
	public void setTgId(int tgId) {
		this.tgId = tgId;
	}
	public String getStgName() {
		return stgName;
	}
	public void setStgName(String stgName) {
		this.stgName = stgName;
	}
	public String getTtgName() {
		return ttgName;
	}
	public void setTtgName(String ttgName) {
		this.ttgName = ttgName;
	}
	public int getTgRankPos() {
		return tgRankPos;
	}
	public void setTgRankPos(int tgRankPos) {
		this.tgRankPos = tgRankPos;
	}
	public String getTgRank() {
		return tgRank;
	}
	public void setTgRank(String tgRank) {
		this.tgRank = tgRank;
	}
	public int getMbId() {
		return mbId;
	}
	public void setMbId(int mbId) {
		this.mbId = mbId;
	}
	public String getSmbName() {
		return smbName;
	}
	public void setSmbName(String smbName) {
		this.smbName = smbName;
	}
	public String getTmbName() {
		return tmbName;
	}
	public void setTmbName(String tmbName) {
		this.tmbName = tmbName;
	}
	public int getMbRankPos() {
		return mbRankPos;
	}
	public void setMbRankPos(int mbRankPos) {
		this.mbRankPos = mbRankPos;
	}
	public String getMbRank() {
		return mbRank;
	}
	public void setMbRank(String mbRank) {
		this.mbRank = mbRank;
	}
	public int getNum5() {
		return num5;
	}
	public void setNum5(int num5) {
		this.num5 = num5;
	}
	public String getMatchNo() {
		return matchNo;
	}
	public void setMatchNo(String matchNo) {
		this.matchNo = matchNo;
	}
	public int getNum6() {
		return num6;
	}
	public void setNum6(int num6) {
		this.num6 = num6;
	}
	public String getShortTgName() {
		return shortTgName;
	}
	public void setShortTgName(String shortTgName) {
		this.shortTgName = shortTgName;
	}
	public String getShortMbName() {
		return shortMbName;
	}
	public void setShortMbName(String shortMbName) {
		this.shortMbName = shortMbName;
	}
	public int getWid() {
		return wid;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public String getTgs1() {
		return tgs1;
	}
	public void setTgs1(String tgs1) {
		this.tgs1 = tgs1;
	}
	public String getTgs2() {
		return tgs2;
	}
	public void setTgs2(String tgs2) {
		this.tgs2 = tgs2;
	}
	public String getTgs3() {
		return tgs3;
	}
	public void setTgs3(String tgs3) {
		this.tgs3 = tgs3;
	}
	public String getTgs4() {
		return tgs4;
	}
	public void setTgs4(String tgs4) {
		this.tgs4 = tgs4;
	}
	public String getMbs1() {
		return mbs1;
	}
	public void setMbs1(String mbs1) {
		this.mbs1 = mbs1;
	}
	public String getMbs2() {
		return mbs2;
	}
	public void setMbs2(String mbs2) {
		this.mbs2 = mbs2;
	}
	public String getMbs3() {
		return mbs3;
	}
	public void setMbs3(String mbs3) {
		this.mbs3 = mbs3;
	}
	public String getMbs4() {
		return mbs4;
	}
	public void setMbs4(String mbs4) {
		this.mbs4 = mbs4;
	}
	public String getOtherScore() {
		return otherScore;
	}
	public void setOtherScore(String otherScore) {
		this.otherScore = otherScore;
	}
	public String getWordLive() {
		return wordLive;
	}
	public void setWordLive(String wordLive) {
		this.wordLive = wordLive;
	}
	public int getWleagueId() {
		return wleagueId;
	}
	public void setWleagueId(int wleagueId) {
		this.wleagueId = wleagueId;
	}
	public int getWtgId() {
		return wtgId;
	}
	public void setWtgId(int wtgId) {
		this.wtgId = wtgId;
	}
	public int getWmbId() {
		return wmbId;
	}
	public void setWmbId(int wmbId) {
		this.wmbId = wmbId;
	}
	public String getJcDate() {
		return jcDate;
	}
	public void setJcDate(String jcDate) {
		this.jcDate = jcDate;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public BkImsMatch500() {
		super();
	}
	
	public BkImsMatch500(int status, int matchId, int hid, String startDate,
			String shortTime, int atype, String sln, String tln, String lc,
			String mt, int num1, int leagueId, int tgId, String stgName,
			String ttgName, int tgRankPos, String tgRank, int mbId,
			String smbName, String tmbName, int mbRankPos, String mbRank,
			int num5, String matchNo, int num6, String shortTgName,
			String shortMbName, int wid, String tgs1, String tgs2, String tgs3,
			String tgs4, String mbs1, String mbs2, String mbs3, String mbs4,
			String otherScore, String wordLive, int wleagueId, int wtgId,
			int wmbId,String jcDate,int mid) {
		super();
		this.status = status;
		this.matchId = matchId;
		this.hid = hid;
		this.startDate = startDate;
		this.shortTime = shortTime;
		this.atype = atype;
		this.sln = sln;
		this.tln = tln;
		this.lc = lc;
		this.mt = mt;
		this.num1 = num1;
		this.leagueId = leagueId;
		this.tgId = tgId;
		this.stgName = stgName;
		this.ttgName = ttgName;
		this.tgRankPos = tgRankPos;
		this.tgRank = tgRank;
		this.mbId = mbId;
		this.smbName = smbName;
		this.tmbName = tmbName;
		this.mbRankPos = mbRankPos;
		this.mbRank = mbRank;
		this.num5 = num5;
		this.matchNo = matchNo;
		this.num6 = num6;
		this.shortTgName = shortTgName;
		this.shortMbName = shortMbName;
		this.wid = wid;
		this.tgs1 = tgs1;
		this.tgs2 = tgs2;
		this.tgs3 = tgs3;
		this.tgs4 = tgs4;
		this.mbs1 = mbs1;
		this.mbs2 = mbs2;
		this.mbs3 = mbs3;
		this.mbs4 = mbs4;
		this.otherScore = otherScore;
		this.wordLive = wordLive;
		this.wleagueId = wleagueId;
		this.wtgId = wtgId;
		this.wmbId = wmbId;
		this.jcDate=jcDate;
		this.mid=mid;
	}
	@Override
	public String toString() {
		return "BkImsMatch500 [id=" + id + ", status=" + status + ", matchId="
				+ matchId + ", hid=" + hid + ", startDate=" + startDate
				+ ", shortTime=" + shortTime + ", atype=" + atype + ", sln="
				+ sln + ", tln=" + tln + ", lc=" + lc + ", mt=" + mt
				+ ", num1=" + num1 + ", leagueId=" + leagueId + ", tgId="
				+ tgId + ", stgName=" + stgName + ", ttgName=" + ttgName
				+ ", tgRankPos=" + tgRankPos + ", tgRank=" + tgRank + ", mbId="
				+ mbId + ", smbName=" + smbName + ", tmbName=" + tmbName
				+ ", mbRankPos=" + mbRankPos + ", mbRank=" + mbRank + ", num5="
				+ num5 + ", matchNo=" + matchNo + ", num6=" + num6
				+ ", shortTgName=" + shortTgName + ", shortMbName="
				+ shortMbName + ", wid=" + wid + ", tgs1=" + tgs1 + ", tgs2="
				+ tgs2 + ", tgs3=" + tgs3 + ", tgs4=" + tgs4 + ", mbs1=" + mbs1
				+ ", mbs2=" + mbs2 + ", mbs3=" + mbs3 + ", mbs4=" + mbs4
				+ ", otherScore=" + otherScore + ", wordLive=" + wordLive
				+ ", wleagueId=" + wleagueId + ", wtgId=" + wtgId + ", wmbId="
				+ wmbId + ", jcDate=" + jcDate + ", mid=" + mid + "]";
	}
	
}
