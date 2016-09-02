package com.yuncai.modules.lottery.factorys.ticketBingo;


import java.util.Map;

import com.yuncai.modules.lottery.sporttery.OptionItem;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallMatchItem;
import com.yuncai.modules.lottery.sporttery.support.basketball.BasketBallTicketModel;

public class BasketBallBingoCheck extends SportteryBingoCheckTurbid<BasketBallTicketModel, BasketBallMatchItem> {

	@Override
	public Class<BasketBallTicketModel> getTClass() {
		return BasketBallTicketModel.class;
	}

	@Override
	public Class<BasketBallMatchItem> getXClass() {
		return BasketBallMatchItem.class;
	}

	@Override
	public OptionItem getPassOptionItem(String lotteryType, BasketBallMatchItem matchItem, Map<String, String> resultMap) {
		Double RF = matchItem.getRF();
		Double DXF = matchItem.getDXF();
		resultMap.put(CommonUtils.handicapKey, RF.toString());
		resultMap.put(CommonUtils.bigSmallKey, DXF.toString());
		return CommonUtils.getResultOptionItem(lotteryType, resultMap);
	}

}
