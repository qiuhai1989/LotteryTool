package com.yuncai.modules.lottery.bean.vo;

import java.util.ArrayList;
import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.WorldCupTeam;

/**冠军玩法对阵
 * @author qiuhai
 *
 */
public class ChampionMatchAgainst {

	private List<WorldCupTeam> teams=new ArrayList<WorldCupTeam>();

	public List<WorldCupTeam> getTeams() {
		return teams;
	}

	public void setTeams(List<WorldCupTeam> teams) {
		this.teams = teams;
	}
	
}
