package com.yuncai.modules.lottery.service.sqlService.impl.apk;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.sqlDao.apk.ApkPushDAO;
import com.yuncai.modules.lottery.model.Sql.ApkPush;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPushService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public class ApkPushServiceImpl extends BaseServiceImpl<ApkPush, Integer> implements ApkPushService {

	private ApkPushDAO apkPushDAO;
	public void setApkPushDAO(ApkPushDAO apkPushDAO) {
		this.apkPushDAO = apkPushDAO;
	}

	@Override
	public List<ApkPush> find(int offset, int length,Search search) {	
		return apkPushDAO.find(offset, length,search);
	}

	@Override
	public int getCount(Search search) {
		return apkPushDAO.getCount(search);
	}
	
	@Override
	public int getMaxCode() {
		return apkPushDAO.getMaxCode();
	}

	@Override
	public List<ApkPush> findAll(String now,String substring,String receiveUser,String agentId) {
		return apkPushDAO.findAll(now,substring,receiveUser,agentId);
	}

}
