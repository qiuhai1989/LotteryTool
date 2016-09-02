package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtSpfAwardDao;
import com.yuncai.modules.lottery.model.Oracle.FtSpfAward;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtSpfAwardService;

public class FtSpfAwardServiceImpl extends BaseServiceImpl<FtSpfAward, Integer> implements FtSpfAwardService {
	
	@SuppressWarnings("unused")
	private FtSpfAwardDao ftSpfAwardDao;
	
	
	public void setFtSpfAwardDao(FtSpfAwardDao ftSpfAwardDao) {
		this.ftSpfAwardDao = ftSpfAwardDao;
	}

}
