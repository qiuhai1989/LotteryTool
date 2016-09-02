package com.yuncai.modules.lottery.service.sqlService.impl.lottery;

import com.yuncai.modules.lottery.dao.sqlDao.lottery.SchemesNumberDAO;
import com.yuncai.modules.lottery.model.Sql.SchemesNumber;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.lottery.SchemesNumberService;

public class SchemesNumberServiceImpl extends BaseServiceImpl<SchemesNumber, Integer> implements SchemesNumberService{

	private SchemesNumberDAO sqlSchemesNumberDAO;

	public void setSqlSchemesNumberDAO(SchemesNumberDAO sqlSchemesNumberDAO) {
		this.sqlSchemesNumberDAO = sqlSchemesNumberDAO;
	}
}
