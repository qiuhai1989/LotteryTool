package com.yuncai.modules.lottery.dao.sqlDao.user;

import java.util.List;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.Platform;


public interface PlatformDAO extends GenericDao<Platform,Integer>{
	public List<Platform> findAllPlatform();
}
