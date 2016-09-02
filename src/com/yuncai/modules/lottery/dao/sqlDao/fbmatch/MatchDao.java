package com.yuncai.modules.lottery.dao.sqlDao.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.Match;

/**
 * 足球对阵赛事DAO接口
 * @author blackworm
 *
 */
public interface MatchDao {

	/**根据id获取赛事结果
	 * @param id
	 * @return
	 */
	public Match findMatchById(Integer id);
	public List<Match> findMatchList(List<Integer> ids);
	public List<Object[]> findMatchAndPassRate(List<Integer> ids);
}
