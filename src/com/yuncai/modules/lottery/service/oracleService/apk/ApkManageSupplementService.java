package com.yuncai.modules.lottery.service.oracleService.apk;


import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.ApkManageSupplement;
import com.yuncai.modules.lottery.service.BaseService;

public interface ApkManageSupplementService extends BaseService<ApkManageSupplement, Integer>{

	public List<ApkManageSupplement> findAll();

	

	
}
