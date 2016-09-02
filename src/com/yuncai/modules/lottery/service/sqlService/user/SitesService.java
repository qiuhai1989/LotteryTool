package com.yuncai.modules.lottery.service.sqlService.user;

import com.yuncai.modules.lottery.model.Sql.Sites;
import com.yuncai.modules.lottery.service.BaseService;

public interface SitesService extends BaseService<Sites, Integer>{
	//获取信息
	public Sites getSitesInfo();

}
