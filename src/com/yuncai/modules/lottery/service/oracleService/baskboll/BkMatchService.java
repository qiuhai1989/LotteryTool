package com.yuncai.modules.lottery.service.oracleService.baskboll;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.yuncai.modules.lottery.model.Oracle.BkMatch;
import com.yuncai.modules.lottery.service.BaseService;


public interface BkMatchService extends BaseService<BkMatch, Integer>{
	public List<BkMatch> findFtMatchs(String date);
	
	public List<BkMatch> findByDetachedCriteria(DetachedCriteria criteria);
	
	//找出赛事集合
	List<BkMatch> findMatchListBetweenIntTimes(Integer beginIntTIme, Integer endIntTime);
	
	/** 根据status拿到对阵* */
	List<BkMatch> findMatchByStatus(Integer status);
	
	public List<BkMatch> findMatchsByNoStart();
	
	/**根据id集合查找对应比赛及赔率
	 * @param ids
	 * @return
	 */
	public List<Object[]> findMatchAndPassRate(List<Integer> ids);
	
	/**根据intTime集合查找对应比赛及赔率
	 * @param ids
	 * @return
	 */
	public List<Object[]> findMatchAndPassRateByIntTimes(List<Integer> intTimes);
	
	/**根据lineId intTime 查找对应比赛及赔率
	 * @param ids
	 * @return
	 */
	public List<Object[]> findMatchAndPassRate(List<Integer> intTimes,List<String>lineIds);
	
}

