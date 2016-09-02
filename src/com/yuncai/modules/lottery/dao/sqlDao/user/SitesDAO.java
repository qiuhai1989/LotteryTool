package com.yuncai.modules.lottery.dao.sqlDao.user;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.Sites;

public interface SitesDAO extends GenericDao<Sites, Integer>{

	//获取信息
	public Sites getSitesInfo();
}
