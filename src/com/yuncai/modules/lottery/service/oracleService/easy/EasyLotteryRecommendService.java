package com.yuncai.modules.lottery.service.oracleService.easy;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.EasyLotteryRecommend;
import com.yuncai.modules.lottery.service.BaseService;

public interface EasyLotteryRecommendService extends BaseService<EasyLotteryRecommend, Integer> {
	/**查找推荐的足球比赛
	 * @return
	 */
	public List<Object[]> findRecommendFtInformation();
	
	/**查找推荐的篮球比赛
	 * @return
	 */
	public List<Object[]> findRecommendBkInformation();
	
	/**根据彩种查找pos的最小 值
	 * @param gtype
	 * @return
	 */
	public Integer findMinPos(int gtype);
	
	
}
