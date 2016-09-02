package com.yuncai.modules.lottery.service.oracleService.Impl.bkmatch;

import com.yuncai.modules.lottery.dao.oracleDao.bkmatch.BkMatchBet365Dao;
import com.yuncai.modules.lottery.model.Oracle.BkMatchBet365;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkMatchBet365Service;

public class BkMatchBet365ServiceImpl extends BaseServiceImpl<BkMatchBet365, Integer> implements BkMatchBet365Service {

	private BkMatchBet365Dao bkMatchBet365Dao;

	public void setBkMatchBet365Dao(BkMatchBet365Dao bkMatchBet365Dao) {
		this.bkMatchBet365Dao = bkMatchBet365Dao;
	}

}
