package com.yuncai.modules.lottery.dao.sqlDao.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.PassRate;

/**
 * 足球单关DAO接口
 * @author blackworm
 *
 */
public interface PassRateDao {

	public PassRate findPassRate(String matchDate,String matchNo);
	
	/**查找近三天在售可见赛事信息
	 * @return
	 */
	public List<PassRate>findCurrentPassRate();
	
}
