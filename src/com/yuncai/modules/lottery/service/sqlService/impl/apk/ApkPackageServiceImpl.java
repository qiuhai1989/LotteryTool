package com.yuncai.modules.lottery.service.sqlService.impl.apk;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkPackageDAO;
import com.yuncai.modules.lottery.model.Sql.ApkPackage;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPackageService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public class ApkPackageServiceImpl extends BaseServiceImpl<ApkPackage, Integer> implements ApkPackageService {

	private ApkPackageDAO apkPackageDAO;
	public void setApkPackageDAO(ApkPackageDAO apkPackageDAO) {
		this.apkPackageDAO = apkPackageDAO;
	}

	@Override
	public List<ApkPackage> find(int offset, int length,Search search) {	
		return apkPackageDAO.find(offset, length,search);
	}

	@Override
	public int getCount(Search search) {
		return apkPackageDAO.getCount(search);
	}

	@Override
	public List<ApkPackage> find() {
		return apkPackageDAO.find();
	}
	
	
}
