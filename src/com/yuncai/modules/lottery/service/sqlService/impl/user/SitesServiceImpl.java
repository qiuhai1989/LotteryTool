package com.yuncai.modules.lottery.service.sqlService.impl.user;

import com.yuncai.modules.lottery.dao.sqlDao.user.SitesDAO;
import com.yuncai.modules.lottery.model.Sql.Sites;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.user.SitesService;

public class SitesServiceImpl extends BaseServiceImpl<Sites, Integer> implements SitesService{

	private SitesDAO sqlSitesDAO;

	public void setSqlSitesDAO(SitesDAO sqlSitesDAO) {
		this.sqlSitesDAO = sqlSitesDAO;
	}

	@Override
	public Sites getSitesInfo() {
		return this.sqlSitesDAO.getSitesInfo();
	}
}
