package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuncai.modules.lottery.bean.search.ActivitiesManagementModuleSearch;
import com.yuncai.modules.lottery.dao.oracleDao.member.ActivitiesManagementModuleDAO;
import com.yuncai.modules.lottery.model.Oracle.ActivitiesManagementModule;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.ActivitiesManagementModuleService;
public class ActivitiesManagementModuleServiceImpl extends BaseServiceImpl<ActivitiesManagementModule, Integer> implements  ActivitiesManagementModuleService {

	@Autowired
	private ActivitiesManagementModuleDAO ammDAO;
	
	public List<ActivitiesManagementModule> findBySearch(ActivitiesManagementModuleSearch search, int offset,int length) {
		return ammDAO.findBySearch(search, offset, length);
	}

	public ActivitiesManagementModuleDAO getAmmDAO() {
		return ammDAO;
	}

	public void setAmmDAO(ActivitiesManagementModuleDAO ammDAO) {
		this.ammDAO = ammDAO;
	}

	@Override
	public int getCountBySearch(ActivitiesManagementModuleSearch search) {
		return ammDAO.getCountBySearch(search);
	}

	@Override
	public List<ActivitiesManagementModule> findEffective(String channelRealName) {
		return ammDAO.findEffective(channelRealName);
	}
	


}
