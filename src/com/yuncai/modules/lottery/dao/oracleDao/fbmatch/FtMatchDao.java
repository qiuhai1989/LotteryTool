package com.yuncai.modules.lottery.dao.oracleDao.fbmatch;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.yuncai.modules.lottery.model.Oracle.FtMatch;

/**
 * 对阵赛事DAO接口
 * @author blackworm
 *
 */
public interface FtMatchDao {
	
	public List<FtMatch> findFtMatchs(String date);
	
	//找出赛事集合
	List<FtMatch> findMatchListBetweenIntTimes(final DetachedCriteria detachedCriteria);
	
	List<FtMatch> findByDetachedCriteria(DetachedCriteria criteria);
	/**后去当前足球比赛对阵信息
	 * @return
	 */
	List<Object[]>findCurrentMatch();
	/**查找可以投注的比赛日期
	 * @return
	 */
	public String findMatchKey();
	/**获取在售某一天的比赛
	 * initTime2 如果initTime为空 则使用作为默认值
	 * @return
	 */
	List<Object[]>findCurrentMatch(Integer initTime,Integer initTime2);
}
