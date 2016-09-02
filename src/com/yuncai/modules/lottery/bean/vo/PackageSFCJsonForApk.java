package com.yuncai.modules.lottery.bean.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.model.Sql.IsuseForSFC;

public class PackageSFCJsonForApk {
	private String error;
	private String msg;
	private String serverTime;
	private List<Isuse> dtMatch = new ArrayList<Isuse>();

//内部类
	class Isuse{
		private String no;
		private String hostTeam;
		private String questTeam;
		private String datetime;
		private String s;
		private String p;
		private String f;
		private String game;
		private String hostTeamRank;
		private String guestTeamRank;
		
		public String getGame() {
			return game;
		}
		public void setGame(String game) {
			this.game = game;
		}
		public String getHostTeamRank() {
			return hostTeamRank;
		}
		public void setHostTeamRank(String hostTeamRank) {
			this.hostTeamRank = hostTeamRank;
		}
		public String getGuestTeamRank() {
			return guestTeamRank;
		}
		public void setGuestTeamRank(String guestTeamRank) {
			this.guestTeamRank = guestTeamRank;
		}
		public String getNo() {
			return no;
		}
		public void setNo(String no) {
			this.no = no;
		}
		public String getHostTeam() {
			return hostTeam;
		}
		public void setHostTeam(String hostTeam) {
			this.hostTeam = hostTeam;
		}
		public String getQuestTeam() {
			return questTeam;
		}
		public void setQuestTeam(String questTeam) {
			this.questTeam = questTeam;
		}
		public String getDatetime() {
			return datetime;
		}
		public void setDatetime(String datetime) {
			this.datetime = datetime;
		}
		public String getS() {
			return s;
		}
		public void setS(String s) {
			this.s = s;
		}
		public String getP() {
			return p;
		}
		public void setP(String p) {
			this.p = p;
		}
		public String getF() {
			return f;
		}
		public void setF(String f) {
			this.f = f;
		}
		
		
	}

	public void initDtMatch(List<IsuseForSFC> lists) {
//		List<Isuse> isuses = new ArrayList<Isuse>();
		for(IsuseForSFC iForSFC:lists){
			Isuse isuse = new Isuse();
			isuse.setNo(Integer.toString(iForSFC.getNo()) );
			isuse.setHostTeam(iForSFC.getHostTeam());
			isuse.setQuestTeam(iForSFC.getGuestTeam());
			isuse.setDatetime(DateTools.dateToString(iForSFC.getDateTime()));
			isuse.setS(Double.toString(iForSFC.getWin()));
			isuse.setP(Double.toString(iForSFC.getDraw()));
			isuse.setF(Double.toString(iForSFC.getLost()));
			isuse.setGame(iForSFC.getGame());
			isuse.setHostTeamRank(iForSFC.getHostTeamRank());
			isuse.setGuestTeamRank(iForSFC.getGuestTeamRank());
//			isuses.add(isuse);
			dtMatch.add(isuse);
		}
		
		
	}
	/**针对客户端9版本老足彩对阵主客队 乱序问题
	 * @param lists
	 */
	public void initDtMatch2(List<IsuseForSFC> lists) {
//		List<Isuse> isuses = new ArrayList<Isuse>();
		for(IsuseForSFC iForSFC:lists){
			Isuse isuse = new Isuse();
			isuse.setNo(Integer.toString(iForSFC.getNo()) );
/*			isuse.setHostTeam(iForSFC.getHostTeam());
			isuse.setQuestTeam();*/
			isuse.setHostTeam(iForSFC.getGuestTeam());
			isuse.setQuestTeam(iForSFC.getHostTeam());
			isuse.setDatetime(DateTools.dateToString(iForSFC.getDateTime()));
			isuse.setS(Double.toString(iForSFC.getWin()));
			isuse.setP(Double.toString(iForSFC.getDraw()));
			isuse.setF(Double.toString(iForSFC.getLost()));
			isuse.setGame(iForSFC.getGame());
			isuse.setHostTeamRank(iForSFC.getHostTeamRank());
			isuse.setGuestTeamRank(iForSFC.getGuestTeamRank());
//			isuses.add(isuse);
			dtMatch.add(isuse);
		}
		
		
	}

	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getServerTime() {
		return serverTime;
	}


	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}


	public List<Isuse> getDtMatch() {
		return dtMatch;
	}


	public void setDtMatch(List<Isuse> dtMatch) {
		this.dtMatch = dtMatch;
	}




	
}
