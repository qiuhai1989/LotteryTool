package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtChampionDao;
import com.yuncai.modules.lottery.model.Oracle.FtChampion;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtChampionService;

public class FtChampionServiceImpl extends BaseServiceImpl<FtChampion, Integer> implements FtChampionService {

	FtChampionDao  ftChampionDao;

	public void setFtChampionDao(FtChampionDao ftChampionDao) {
		this.ftChampionDao = ftChampionDao;
	}
	

}
