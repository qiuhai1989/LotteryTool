package com.yuncai.modules.lottery.service.oracleService.worldcup;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.WorldCupTeam;
import com.yuncai.modules.lottery.service.BaseService;

public interface WorldCupTeamService extends BaseService<WorldCupTeam, Integer> {

	public List<WorldCupTeam> findTeams();
	
	public List<WorldCupTeam> findTeamsByIds(List<String>ids);
	
}
