package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.VipGiftDetailSearch;
import com.yuncai.modules.lottery.dao.oracleDao.member.VipGiftDetailDAO;
import com.yuncai.modules.lottery.model.Oracle.VipGiftDetail;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.VipGiftDetailService;

public class VipGiftDetailServiceImpl extends BaseServiceImpl<VipGiftDetail,Integer> implements VipGiftDetailService{
	
	private VipGiftDetailDAO vipGiftDetailDAO;
	
	// 获取红包菜单
	public List<Object[]> giftMenu(String account){
		return vipGiftDetailDAO.giftMenu(account);
	}
	
	// 获取送出红包菜单
	public List<Object[]> vipGiftMenu(String account){
		return vipGiftDetailDAO.vipGiftMenu(account);
	}
	
	public List<VipGiftDetail> findAllBySearch(VipGiftDetailSearch search){
		return vipGiftDetailDAO.findAllBySearch(search);
	}

	public void setVipGiftDetailDAO(VipGiftDetailDAO vipGiftDetailDAO) {
		this.vipGiftDetailDAO = vipGiftDetailDAO;
	}
	
	
}
