package com.yuncai.modules.lottery.service.sqlService.lottery;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.LotteryTypeProps;
import com.yuncai.modules.lottery.service.BaseService;

public interface LotteryTypePropsService extends BaseService<LotteryTypeProps, Integer>{
	
	public LotteryTypeProps findbuyLotteryType(LotteryType lotteryType);

}
