package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.ActivitiesManagementModuleSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.ActivitiesManagementModule;

public interface ActivitiesManagementModuleDAO extends GenericDao<ActivitiesManagementModule, Integer>{
	public abstract List<ActivitiesManagementModule> findBySearch(final ActivitiesManagementModuleSearch search, final int offset, final int length);
	public abstract int getCountBySearch(final ActivitiesManagementModuleSearch search);
	public abstract List<ActivitiesManagementModule> findEffective(String channelRealName);
	
}
