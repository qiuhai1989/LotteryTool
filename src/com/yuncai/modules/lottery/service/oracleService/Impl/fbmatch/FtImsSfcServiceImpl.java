package com.yuncai.modules.lottery.service.oracleService.Impl.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.fbmatch.FtImsSfcDao;
import com.yuncai.modules.lottery.model.Oracle.FtImsSfc;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.fbmatch.FtImsSfcService;

public class FtImsSfcServiceImpl extends BaseServiceImpl<FtImsSfc, Integer> implements FtImsSfcService {

	private FtImsSfcDao ftImsSfcDao;

	public void setFtImsSfcDao(FtImsSfcDao ftImsSfcDao) {
		this.ftImsSfcDao = ftImsSfcDao;
	}

	@Override
	public List<FtImsSfc> findFtImsSfc(String expect, Integer atype) {
		return ftImsSfcDao.findFtImsSfc(expect, atype);
	}


}
