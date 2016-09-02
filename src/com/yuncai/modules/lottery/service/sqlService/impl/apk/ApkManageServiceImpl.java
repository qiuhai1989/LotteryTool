package com.yuncai.modules.lottery.service.sqlService.impl.apk;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkManageDAO;
import com.yuncai.modules.lottery.model.Sql.ApkManage;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkManageService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public class ApkManageServiceImpl extends BaseServiceImpl<ApkManage, Integer> implements ApkManageService {

	@Autowired
	private ApkManageDAO apkManageDAO;
	public void setApkManageDAO(ApkManageDAO apkManageDAO) {
		this.apkManageDAO = apkManageDAO;
	}

	@Override
	public List<Map<String, Object>> find(int offset, int length,int packageId,Search search) {	
		return apkManageDAO.find(offset, length, packageId, search);
	}

	@Override
	public int getCount(Search search,int packageId) {
		return apkManageDAO.getCount(search,packageId);
	}

	@Override
	public String getMaxVersion(int packageId) {
		return apkManageDAO.getMaxVersion(packageId);
	}

	@Override
	public List<Map<String, String>> findUpdVersion(int platformId,int channelId) {
		return apkManageDAO.findUpdVersion(platformId,channelId);
	}
	
	@Override
	public String findLastBak(Integer channelid,String maxVersion) {
		return apkManageDAO.findLastBak(channelid,maxVersion);
	}
	
}
