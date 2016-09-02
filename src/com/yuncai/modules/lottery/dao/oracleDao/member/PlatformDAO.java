package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.Platform;

public interface PlatformDAO extends GenericDao<Platform,Integer>{
	public List<Platform> findAllPlatform();
}
