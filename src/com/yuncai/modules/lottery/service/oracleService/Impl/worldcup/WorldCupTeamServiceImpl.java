package com.yuncai.modules.lottery.service.oracleService.Impl.worldcup;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.worldcup.WorldCupTeamDao;
import com.yuncai.modules.lottery.model.Oracle.WorldCupTeam;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.worldcup.WorldCupTeamService;


public class WorldCupTeamServiceImpl extends BaseServiceImpl<WorldCupTeam, Integer> implements WorldCupTeamService{

	private WorldCupTeamDao worldCupTeamDao;
	
	@Override
	public List<WorldCupTeam> findTeams() {
		// TODO Auto-generated method stub
		return worldCupTeamDao.findTeams();
	}

	public void setWorldCupTeamDao(WorldCupTeamDao worldCupTeamDao) {
		this.worldCupTeamDao = worldCupTeamDao;
	}

	@Override
	public List<WorldCupTeam> findTeamsByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return this.worldCupTeamDao.findTeamsByIds(ids);
	}

}
