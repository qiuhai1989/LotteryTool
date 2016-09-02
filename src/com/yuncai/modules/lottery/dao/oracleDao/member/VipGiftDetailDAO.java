package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.VipGiftDetailSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.VipGiftDetail;

public interface VipGiftDetailDAO extends GenericDao<VipGiftDetail,Integer>{
	// 获取红包菜单
	public List<Object[]> giftMenu(final String account);
	
	// 获取送出红包菜单
	public List<Object[]> vipGiftMenu(final String account);
	
	public List<VipGiftDetail> findAllBySearch(final VipGiftDetailSearch search);
}
