package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.ActivitiesManagementModuleSearch;
import com.yuncai.modules.lottery.model.Oracle.ActivitiesManagementModule;
import com.yuncai.modules.lottery.service.BaseService;

public interface ActivitiesManagementModuleService extends BaseService<ActivitiesManagementModule, Integer>{
	public abstract int getCountBySearch(final ActivitiesManagementModuleSearch search);
	public abstract List<ActivitiesManagementModule> findBySearch(final ActivitiesManagementModuleSearch search, final int offset, final int length);
	
	//当前渠道下查询有效活动模块
	public abstract List<ActivitiesManagementModule> findEffective(String channelRealName);
}
