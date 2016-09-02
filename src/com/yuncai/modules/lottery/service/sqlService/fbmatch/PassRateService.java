package com.yuncai.modules.lottery.service.sqlService.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.PassRate;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * 足球过关业务接口
 * @author blackworm
 *
 */
public interface PassRateService extends BaseService<PassRate, Integer> {
	
	public PassRate findPassRate(String matchDate,String matchNo);
	/**查找近三天在售可见赛事信息
	 * @return
	 */
	public List<PassRate>findCurrentPassRate();
}
