package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.bean.search.VipBonusLineSearch;
import com.yuncai.modules.lottery.dao.oracleDao.member.VipBonusLineDAO;
import com.yuncai.modules.lottery.model.Oracle.VipBonusLine;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.VipBonusLineService;

public class VipBonusLineServiceImpl extends BaseServiceImpl<VipBonusLine, Integer> implements VipBonusLineService{
	
	private VipBonusLineDAO vipBonusLineDAO;
	
	public List<VipBonusLine> findBySearch(final VipBonusLineSearch search, final int offset, final int length){
		return vipBonusLineDAO.findBySearch(search, offset, length);
	}
	public List<VipBonusLine> findAllBySearch(final VipBonusLineSearch search){
		return vipBonusLineDAO.findAllBySearch(search);
	}

	public void setVipBonusLineDAO(VipBonusLineDAO vipBonusLineDAO) {
		this.vipBonusLineDAO = vipBonusLineDAO;
	}
	
	
}
