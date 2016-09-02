package com.yuncai.modules.lottery.service.oracleService.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.BkSpRate;
import com.yuncai.modules.lottery.model.Sql.PassRate;
import com.yuncai.modules.lottery.service.BaseService;

public interface BkSpRateService extends BaseService<BkSpRate, Integer>{
	/**查找近三天在售可见赛事信息
	 * @return
	 */
	public List<Object[]>findCurrentPassRate();
	/**查找可以投注的比赛日期
	 * @return
	 */
	public String findMatchKey();

	/**
	 * @param time1 指定查询那一天
	 * @param time2 如果time1为null 则使用time2作为默认值
	 * @return
	 */
	public List<Object[]>findCurrentPassRate(Integer time1,Integer time2);
}
