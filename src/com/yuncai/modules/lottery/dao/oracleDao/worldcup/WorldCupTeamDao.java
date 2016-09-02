package com.yuncai.modules.lottery.dao.oracleDao.worldcup;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.WorldCupTeam;

public interface WorldCupTeamDao {
	public List<WorldCupTeam> findTeams();
	public List<WorldCupTeam> findTeamsByIds(List<String>ids);
}
