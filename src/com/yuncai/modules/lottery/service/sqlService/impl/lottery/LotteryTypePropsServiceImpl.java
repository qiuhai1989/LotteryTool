package com.yuncai.modules.lottery.service.sqlService.impl.lottery;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.lottery.LotteryTypePropsDAO;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.LotteryTypeProps;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.lottery.LotteryTypePropsService;

public class LotteryTypePropsServiceImpl extends BaseServiceImpl<LotteryTypeProps, Integer> implements LotteryTypePropsService{

	private LotteryTypePropsDAO sqlLotteryTypePropsDAO;

	public void setSqlLotteryTypePropsDAO(LotteryTypePropsDAO sqlLotteryTypePropsDAO) {
		this.sqlLotteryTypePropsDAO = sqlLotteryTypePropsDAO;
	}

	@Override
	public LotteryTypeProps findbuyLotteryType(LotteryType lotteryType) {
		List<LotteryTypeProps> list=this.sqlLotteryTypePropsDAO.findByProperty(LotteryTypeProps.class, "lotteryType", lotteryType.getValue());
		if (list == null || list.size() == 0) {
			return null;
		}
		LotteryTypeProps props=(LotteryTypeProps)list.get(0);
		return props;
	}
}
