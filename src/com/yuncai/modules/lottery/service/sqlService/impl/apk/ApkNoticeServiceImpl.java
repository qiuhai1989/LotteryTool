package com.yuncai.modules.lottery.service.sqlService.impl.apk;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkNoticeDAO;
import com.yuncai.modules.lottery.model.Sql.ApkNotice;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkNoticeService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public class ApkNoticeServiceImpl extends BaseServiceImpl<ApkNotice, Integer> implements ApkNoticeService {

	private ApkNoticeDAO apkNoticeDAO;
	public void setApkNoticeDAO(ApkNoticeDAO apkNoticeDAO) {
		this.apkNoticeDAO = apkNoticeDAO;
	}

	@Override
	public List<ApkNotice> find(int offset, int length,Search search) {	
		return apkNoticeDAO.find(offset, length,search);
	}

	@Override
	public int getCount(Search search) {
		return apkNoticeDAO.getCount(search);
	}

	@Override
	public int getMaxCode() {
		return apkNoticeDAO.getMaxCode();
	}

	@Override
	public List<ApkNotice> findAll(String time,String str) {
		return apkNoticeDAO.findAll(time,str);
	}


	
	
}
