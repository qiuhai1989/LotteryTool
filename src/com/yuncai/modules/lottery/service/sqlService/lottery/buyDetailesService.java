package com.yuncai.modules.lottery.service.sqlService.lottery;



import java.util.List;

import com.yuncai.modules.lottery.model.Sql.BuyDetails;
import com.yuncai.modules.lottery.model.Sql.Schemes;
import com.yuncai.modules.lottery.service.BaseService;

public interface buyDetailesService extends BaseService<BuyDetails, Integer> {
	/**
	 * 根据编号查询方案详情
	 * @param planNo
	 * @return
	 */
	public List<BuyDetails> findSuccessByPlanNo(final Integer planNo);
}
