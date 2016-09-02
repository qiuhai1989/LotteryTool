package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.VipBonusLineSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.VipBonusLine;

public interface VipBonusLineDAO extends GenericDao<VipBonusLine,Integer> {
	public List<VipBonusLine> findBySearch(final VipBonusLineSearch search, final int offset, final int length);
	public List<VipBonusLine> findAllBySearch(final VipBonusLineSearch search);
}
