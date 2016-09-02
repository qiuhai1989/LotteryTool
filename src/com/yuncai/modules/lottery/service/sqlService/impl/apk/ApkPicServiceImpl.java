package com.yuncai.modules.lottery.service.sqlService.impl.apk;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuncai.modules.lottery.action.admin.search.ApkPicSearch;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkPicDAO;
import com.yuncai.modules.lottery.model.Sql.ApkPic;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPicService;

/***
 * 广告图管理
 * @author gx
 *
 */
public class ApkPicServiceImpl extends BaseServiceImpl<ApkPic, Integer> implements ApkPicService {

	@Autowired
	private ApkPicDAO apkPicDAO;
	public void setApkPicDAO(ApkPicDAO apkPicDAO) {
		this.apkPicDAO = apkPicDAO;
	}

	@Override
	public List<Map<String, Object>> find(ApkPicSearch search,int offset, int length) {	
		return apkPicDAO.find(search,offset, length);
	}

	@Override
	public int getCount(ApkPicSearch search) {
		return apkPicDAO.getCount(search);
	}

	@Override
	public int findByCode(int code) {
		return apkPicDAO.findByCode(code);
	}

	@Override
	public int findById(int id) {
		return apkPicDAO.findById(id);
	}

	@Override
	public int getMaxCode() {
		return apkPicDAO.getMaxCode();
	}


	
}
