package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;

public interface GiftDatailLineDAO extends GenericDao<GiftDatailLine, Integer>{
	
	//根据用户，字头查询是否存在记录
	public abstract List<GiftDatailLine> findByAccountList(final String account,final String topNumber);
	
	// 获取红包菜单
	public List<Object[]> giftMenu(final String account);
	
}
