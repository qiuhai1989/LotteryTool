package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.VipGiftDetailSearch;
import com.yuncai.modules.lottery.model.Oracle.VipGiftDetail;
import com.yuncai.modules.lottery.service.BaseService;

public interface VipGiftDetailService extends BaseService<VipGiftDetail,Integer>{
	// 获取红包菜单
	public List<Object[]> giftMenu(String account);
	
	// 获取送出红包菜单
	public List<Object[]> vipGiftMenu(String account);
	
	public List<VipGiftDetail> findAllBySearch(VipGiftDetailSearch search);
}
