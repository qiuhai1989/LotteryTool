package com.yuncai.modules.lottery.dao.oracleDao.apk;


import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.ApkManageSupplement;

public interface ApkManageSupplementDAO extends GenericDao<ApkManageSupplement, Integer>{

	public List<ApkManageSupplement> findAll();



}
