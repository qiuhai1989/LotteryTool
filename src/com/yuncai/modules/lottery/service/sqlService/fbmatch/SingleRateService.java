package com.yuncai.modules.lottery.service.sqlService.fbmatch;

import com.yuncai.modules.lottery.model.Sql.SingleRate;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * 足球单关业务接口
 * @author blackworm
 *
 */
public interface SingleRateService extends BaseService<SingleRate, Integer> {
	
	public SingleRate findSingleRate(String matchDate,String matchNo);
}
