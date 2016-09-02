package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtNearRecordDao;
import com.yuncai.modules.lottery.model.Oracle.FtNearRecord;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtNearRecordService;

public class FtNearRecordServiceImpl extends BaseServiceImpl<FtNearRecord, Integer> implements FtNearRecordService {

	private FtNearRecordDao ftNearRecordDao;

	public void setFtNearRecordDao(FtNearRecordDao ftNearRecordDao) {
		this.ftNearRecordDao = ftNearRecordDao;
	}

	@Override
	public List<FtNearRecord> findFtNearRecordsByMids(String mids) {
		return ftNearRecordDao.findFtNearRecordsByMids(mids);
	}

}
