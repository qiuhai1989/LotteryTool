package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.BkSpRateDao;
import com.yuncai.modules.lottery.model.Oracle.BkSpRate;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.BkSpRateService;

public class BkSpRateServiceImpl extends BaseServiceImpl<BkSpRate, Integer> implements BkSpRateService {
	private BkSpRateDao bkSpRateDao;
	
	public void setBkSpRateDao(BkSpRateDao bkSpRateDao) {
		this.bkSpRateDao = bkSpRateDao;
	}

	@Override
	public List<Object[]> findCurrentPassRate() {
		// TODO Auto-generated method stub
		return this.bkSpRateDao.findCurrentPassRate();
	}

	@Override
	public String findMatchKey() {
		// TODO Auto-generated method stub
		return bkSpRateDao.findMatchKey();
	}

	@Override
	public List<Object[]> findCurrentPassRate(Integer time1, Integer time2) {
		// TODO Auto-generated method stub
		return this.bkSpRateDao.findCurrentPassRate(time1, time2);
	}

}
