package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.member.GiftDatailLineDAO;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.GiftDatailLineService;

public class GiftDatailLineServiceImpl extends BaseServiceImpl<GiftDatailLine, Integer> implements GiftDatailLineService {

	private GiftDatailLineDAO giftDatailLineDAO;

	public void setGiftDatailLineDAO(GiftDatailLineDAO giftDatailLineDAO) {
		this.giftDatailLineDAO = giftDatailLineDAO;
	}

	@Override
	public List<GiftDatailLine> findByAccountList(final String account, final String topNumber) {
		
		return this.giftDatailLineDAO.findByAccountList(account, topNumber);
	}
	
	
	@Override
	public List<Object[]> giftMenu(final String account){
		return this.giftDatailLineDAO.giftMenu(account);
	}
}
