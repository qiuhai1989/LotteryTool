package com.yuncai.modules.lottery.service.oracleService.Impl.apk;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.apk.ApkManageSupplementDAO;
import com.yuncai.modules.lottery.model.Oracle.ApkManageSupplement;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.apk.ApkManageSupplementService;

public class ApkManageSupplementServiceImpl extends BaseServiceImpl<ApkManageSupplement, Integer> implements ApkManageSupplementService {
	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 11:02:37 AM
	 */
	private ApkManageSupplementDAO apkManageSupplementDao;


	public ApkManageSupplementDAO getApkManageSupplementDao() {
		return apkManageSupplementDao;
	}


	public void setApkManageSupplementDao(ApkManageSupplementDAO apkManageSupplementDao) {
		this.apkManageSupplementDao = apkManageSupplementDao;
	}


	@Override
	public List<ApkManageSupplement> findAll() {
		return apkManageSupplementDao.findAll();
	}


	
}
