package com.yuncai.modules.lottery.service.oracleService.Impl.bkmatch;

import com.yuncai.modules.lottery.dao.oracleDao.bkmatch.BkBetRateRatioDao;
import com.yuncai.modules.lottery.model.Oracle.BkBetRateRatio;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkBetRateRatioService;

public class BkBetRateRatioServiceImpl extends BaseServiceImpl<BkBetRateRatio, Integer> implements BkBetRateRatioService {

	@SuppressWarnings("unused")
	private BkBetRateRatioDao bkBetRateRatioDao;

	public void setBkBetRateRatioDao(BkBetRateRatioDao bkBetRateRatioDao) {
		this.bkBetRateRatioDao = bkBetRateRatioDao;
	}

}
