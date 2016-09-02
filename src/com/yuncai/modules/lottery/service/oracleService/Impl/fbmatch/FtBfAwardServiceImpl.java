package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtBfAwardDao;
import com.yuncai.modules.lottery.model.Oracle.FtBfAward;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtBfAwardService;

public class FtBfAwardServiceImpl extends BaseServiceImpl<FtBfAward, Integer> implements FtBfAwardService {
	
	@SuppressWarnings("unused")
	private FtBfAwardDao ftBfAwardDao;
	
	
	public void setFtBfAwardDao(FtBfAwardDao ftBfAwardDao) {
		this.ftBfAwardDao = ftBfAwardDao;
	}

}
