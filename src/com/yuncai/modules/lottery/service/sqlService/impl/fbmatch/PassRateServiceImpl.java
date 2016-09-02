package com.yuncai.modules.lottery.service.sqlService.impl.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.fbmatch.PassRateDao;
import com.yuncai.modules.lottery.model.Sql.PassRate;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.fbmatch.PassRateService;

public class PassRateServiceImpl extends BaseServiceImpl<PassRate, Integer> implements PassRateService {

	private PassRateDao passRateDao;

	public void setPassRateDao(PassRateDao passRateDao) {
		this.passRateDao = passRateDao;
	}

	@Override
	public PassRate findPassRate(String matchDate, String matchNo) {
		return passRateDao.findPassRate(matchDate, matchNo);
	}

	@Override
	public List<PassRate> findCurrentPassRate() {
		// TODO Auto-generated method stub
		return passRateDao.findCurrentPassRate();
	}

}
