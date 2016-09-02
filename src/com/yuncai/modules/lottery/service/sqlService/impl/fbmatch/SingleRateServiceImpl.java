package com.yuncai.modules.lottery.service.sqlService.impl.fbmatch;

import com.yuncai.modules.lottery.dao.sqlDao.fbmatch.SingleRateDao;
import com.yuncai.modules.lottery.model.Sql.SingleRate;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.SingleRateService;

public class SingleRateServiceImpl extends BaseServiceImpl<SingleRate, Integer> implements SingleRateService {

	private SingleRateDao singleRateDao;

	public void setSingleRateDao(SingleRateDao singleRateDao) {
		this.singleRateDao = singleRateDao;
	}

	@Override
	public SingleRate findSingleRate(String matchDate, String matchNo) {
		return singleRateDao.findSingleRate(matchDate, matchNo);
	}

}
