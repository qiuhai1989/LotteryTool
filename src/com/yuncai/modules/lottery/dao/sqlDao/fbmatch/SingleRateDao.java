package com.yuncai.modules.lottery.dao.sqlDao.fbmatch;

import com.yuncai.modules.lottery.model.Sql.SingleRate;

/**
 * 足球单关DAO接口
 * @author blackworm
 *
 */
public interface SingleRateDao {

	/**
	 * 根据赛事日期和赛事编号查找指定记录
	 * @param matchDate
	 * @param matchNo
	 * @return
	 */
	public SingleRate findSingleRate(String matchDate,String matchNo);
}
