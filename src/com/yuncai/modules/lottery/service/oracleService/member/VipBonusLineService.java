package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.VipBonusLineSearch;
import com.yuncai.modules.lottery.model.Oracle.VipBonusLine;
import com.yuncai.modules.lottery.service.BaseService;

public interface VipBonusLineService extends BaseService<VipBonusLine, Integer> {
	public List<VipBonusLine> findBySearch(final VipBonusLineSearch search, final int offset, final int length);
	public List<VipBonusLine> findAllBySearch(final VipBonusLineSearch search);
}
