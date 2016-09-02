package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtBqcAwardDao;
import com.yuncai.modules.lottery.model.Oracle.FtBqcAward;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtBqcAwardService;

public class FtBqcAwardServiceImpl extends BaseServiceImpl<FtBqcAward, Integer> implements FtBqcAwardService {
	
	@SuppressWarnings("unused")
	private FtBqcAwardDao ftBqcAwardDao;
	
	
	public void setFtBqcAwardDao(FtBqcAwardDao ftBqcAwardDao) {
		this.ftBqcAwardDao = ftBqcAwardDao;
	}

}
