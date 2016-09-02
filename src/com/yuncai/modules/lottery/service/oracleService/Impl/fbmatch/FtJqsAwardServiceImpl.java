package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtJqsAwardDao;
import com.yuncai.modules.lottery.model.Oracle.FtJqsAward;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtJqsAwardService;

public class FtJqsAwardServiceImpl extends BaseServiceImpl<FtJqsAward, Integer> implements FtJqsAwardService {
	
	@SuppressWarnings("unused")
	private FtJqsAwardDao ftJqsAwardDao;
	
	
	public void setFtJqsAwardDao(FtJqsAwardDao ftJqsAwardDao) {
		this.ftJqsAwardDao = ftJqsAwardDao;
	}

}
