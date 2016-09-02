package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.service.BaseService;

public interface GiftDatailLineService extends BaseService<GiftDatailLine, Integer> {
	//根据用户，字头查询是否存在记录
	public abstract List<GiftDatailLine> findByAccountList(final String account,final String topNumber);
	// 获取红包菜单
	public List<Object[]> giftMenu(final String account);
}
