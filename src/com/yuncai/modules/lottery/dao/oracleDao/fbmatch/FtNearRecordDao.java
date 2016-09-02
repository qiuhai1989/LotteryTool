package com.yuncai.modules.lottery.dao.oracleDao.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.FtNearRecord;

/**
 * 竞彩足球-主客队近期战绩DAO接口
 * @author blackworm
 *
 */
public interface FtNearRecordDao{

	public List<FtNearRecord> findFtNearRecordsByMids(String mids);
	
}
