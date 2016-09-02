package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.PagesInfoSearch;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;
import com.yuncai.modules.lottery.service.BaseService;

public interface PagesInfoService extends BaseService<PagesInfo, Integer> {
	public List<PagesInfo> findBySearch(PagesInfoSearch search,int offset, int length);
	public int findCountBySearch(PagesInfoSearch search);
}
