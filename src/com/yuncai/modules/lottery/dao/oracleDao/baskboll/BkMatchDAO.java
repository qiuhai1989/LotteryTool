package com.yuncai.modules.lottery.dao.oracleDao.baskboll;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.yuncai.modules.lottery.model.Oracle.BkMatch;

public interface BkMatchDAO  {

	public List<BkMatch> findFtMatchs(String date);
	
	//找出赛事集合
	public List<BkMatch> findMatchListBetweenIntTimes(final DetachedCriteria detachedCriteria);
	
	public List<BkMatch> findByDetachedCriteria(final DetachedCriteria criteria);
	/**根据id集合查找对应比赛及赔率
	 * @param ids
	 * @return
	 */
	public List<Object[]> findMatchAndPassRate(List<Integer> ids);
	/**根据lineId intTime 查找对应比赛及赔率
	 * @param ids
	 * @return
	 */
	public List<Object[]> findMatchAndPassRate(List<Integer> intTimes,List<String>lineIds);
	/**根据intTime集合查找对应比赛及赔率
	 * @param ids
	 * @return
	 */
	public List<Object[]> findMatchAndPassRateByIntTimes(List<Integer> intTimes);
}

