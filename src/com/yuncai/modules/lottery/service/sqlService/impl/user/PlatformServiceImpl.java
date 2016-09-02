package com.yuncai.modules.lottery.service.sqlService.impl.user;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.user.PlatformDAO;
import com.yuncai.modules.lottery.model.Sql.Platform;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.user.PlatformService;

public class PlatformServiceImpl extends BaseServiceImpl<Platform,Integer> implements PlatformService {
	
	private PlatformDAO sqlPlatformDAO;
	
	public List<Platform> findAllPlatform(){
		return sqlPlatformDAO.findAllPlatform();
	}

	public PlatformDAO getSqlPlatformDAO() {
		return sqlPlatformDAO;
	}

	public void setSqlPlatformDAO(PlatformDAO sqlPlatformDAO) {
		this.sqlPlatformDAO = sqlPlatformDAO;
	}
	
}
