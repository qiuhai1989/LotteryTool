package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.member.PlatformDAO;
import com.yuncai.modules.lottery.model.Oracle.Platform;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.PlatformService;

public class PlatformServiceImpl extends BaseServiceImpl<Platform,Integer> implements PlatformService {
	
	private PlatformDAO platformDAO;
	
	public List<Platform> findAllPlatform(){
		return platformDAO.findAllPlatform();
	}

	public PlatformDAO getPlatformDAO() {
		return platformDAO;
	}

	public void setPlatformDAO(PlatformDAO platformDAO) {
		this.platformDAO = platformDAO;
	}
	
}
