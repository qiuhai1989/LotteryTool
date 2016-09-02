package com.yuncai.modules.lottery.service.oracleService.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.FtNearRecord;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * 竞彩足球-主客队近期战绩业务接口
 * @author blackworm
 *
 */
public interface FtNearRecordService extends BaseService<FtNearRecord, Integer> {
	
	public List<FtNearRecord> findFtNearRecordsByMids(String mids);
	
}
