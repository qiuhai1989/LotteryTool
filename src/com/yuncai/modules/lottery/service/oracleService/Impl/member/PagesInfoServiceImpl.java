package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.PagesInfoSearch;
import com.yuncai.modules.lottery.dao.oracleDao.member.PagesInfoDAO;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.PagesInfoService;

public class PagesInfoServiceImpl extends BaseServiceImpl<PagesInfo, Integer> implements PagesInfoService {
	private PagesInfoDAO pagesInfoDAO;

	public List<PagesInfo> findBySearch(PagesInfoSearch search,int offset, int length) {
		return pagesInfoDAO.findBySearch(search,offset,length);
	}

	public int findCountBySearch(PagesInfoSearch search){
		return pagesInfoDAO.findCountBySearch(search);
	}
	
	public void setPagesInfoDAO(PagesInfoDAO pagesInfoDAO) {
		this.pagesInfoDAO = pagesInfoDAO;
	}
}
