package com.yuncai.modules.lottery.service.oracleService.fbmatch;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.yuncai.modules.lottery.model.Oracle.FtMatch;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * 对阵赛事业务接口
 * @author blackworm
 *
 */
public interface FtMatchService extends BaseService<FtMatch, Integer> {
	
	public List<FtMatch> findFtMatchs(String date);
	
	public List<FtMatch> findByDetachedCriteria(DetachedCriteria criteria);
	
	//找出赛事集合
	List<FtMatch> findMatchListBetweenIntTimes(Integer beginIntTIme, Integer endIntTime);
	
	/** 根据status拿到对阵* */
	List<FtMatch> findMatchByStatus(Integer status);
	
	/**获取当前足球比赛对阵信息
	 * @return
	 */
	List<Object[]>findCurrentMatch();
	/**获取在售某一天的比赛
	 * initTime 客户端指定的时间
	 * initTime2 默认时间
	 * @return
	 */
	List<Object[]>findCurrentMatch(Integer initTime,Integer initTime2);
	
	/**查找可以投注的比赛日期
	 * @return
	 */
	public String findMatchKey();
}
