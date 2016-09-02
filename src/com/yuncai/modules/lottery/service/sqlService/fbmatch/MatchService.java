package com.yuncai.modules.lottery.service.sqlService.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Sql.Match;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * 对阵赛事业务接口
 * @author blackworm
 *
 */
public interface MatchService extends BaseService<Match, Integer> {
	
	public List<Match> findMatchList(List<Integer> ids);
	public List<Object[]> findMatchAndPassRate(List<Integer> ids);
}
