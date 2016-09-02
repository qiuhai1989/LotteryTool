package com.yuncai.modules.lottery.service.oracleService.Impl.bkmatch;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.bkmatch.BkImsMatch500Dao;
import com.yuncai.modules.lottery.model.Oracle.BkImsMatch500;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.bkmatch.BkImsMatch500Service;

public class BkImsMatch500ServiceImpl extends BaseServiceImpl<BkImsMatch500, Integer> implements BkImsMatch500Service {

	private BkImsMatch500Dao bkImsMatch500Dao;

	public void setBkImsMatch500Dao(BkImsMatch500Dao bkImsMatch500Dao) {
		this.bkImsMatch500Dao = bkImsMatch500Dao;
	}

	@Override
	public List<BkImsMatch500> findBkImsMatch500s(String expect) {
		return bkImsMatch500Dao.findBkImsMatch500s(expect);
	}

	@Override
	public List<BkImsMatch500> findBkImsMatch500s(String expect, int status) {
		return bkImsMatch500Dao.findBkImsMatch500s(expect, status);
	}

	@Override
	public List<BkImsMatch500> findBkImsJcMatchs(String expect) {
		return bkImsMatch500Dao.findBkImsJcMatchs(expect);
	}

	@Override
	public List<BkImsMatch500> findBkImsJcMatchsByMids(String mids) {
		return bkImsMatch500Dao.findBkImsJcMatchsByMids(mids);
	}

}
