package com.yuncai.modules.lottery.service.oracleService.Impl.system;

import java.util.List;

import com.yuncai.modules.lottery.action.system.SystemErrorSearch;
import com.yuncai.modules.lottery.dao.oracleDao.system.SystemErrorDAO;
import com.yuncai.modules.lottery.model.Oracle.SystemError;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.system.SystemErrorService;

public class SystemErrorServiceImpl extends BaseServiceImpl<SystemError, Integer> implements SystemErrorService{

	private SystemErrorDAO systemErrorDAO;

	public void setSystemErrorDAO(SystemErrorDAO systemErrorDAO) {
		this.systemErrorDAO = systemErrorDAO;
	}

	public Integer countBySearch(SystemErrorSearch errorSearch) {
		
		return this.systemErrorDAO.countBySearch(errorSearch);
	}

	public List<SystemError> findBySearch(SystemErrorSearch errorSearch, Integer offset, Integer length) {
		
		return this.systemErrorDAO.findBySearch(errorSearch, offset, length);
	}
}
