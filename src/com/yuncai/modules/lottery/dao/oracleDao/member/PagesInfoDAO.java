package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.PagesInfoSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;

public interface PagesInfoDAO extends GenericDao<PagesInfo,Integer>{
	public List<PagesInfo> findBySearch(PagesInfoSearch search,int offset,int length);
	public int findCountBySearch(PagesInfoSearch search);
}
