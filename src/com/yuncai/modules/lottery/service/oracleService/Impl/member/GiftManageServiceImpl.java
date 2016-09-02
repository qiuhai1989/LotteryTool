package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import com.yuncai.modules.lottery.dao.oracleDao.member.GiftManageDAO;
import com.yuncai.modules.lottery.model.Oracle.GiftManage;
import com.yuncai.modules.lottery.model.Oracle.toolType.GiftManageSearch;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.GiftManageService;

public class GiftManageServiceImpl extends BaseServiceImpl<GiftManage, Integer> implements GiftManageService {

	@Autowired
	private GiftManageDAO giftManageDAO;
	public void setGiftManageDAO(GiftManageDAO giftManageDAO) {
		this.giftManageDAO = giftManageDAO;
	}

	
	public List<GiftManage> findBySearch(GiftManageSearch search, int offset,int length) {
		return giftManageDAO.findBySearch(search, offset, length);
	}

	
	public int getCountBySearch(GiftManageSearch search) {
		return giftManageDAO.getCountBySearch(search);
	}

	
	public List<GiftManage> findByChannelList(final String channel,final int is_vail) {
		
		return this.giftManageDAO.findByChannelList(channel, is_vail);
	}
	@Override
	public List<GiftManage> findGift(String channel, int is_vail, int type) {
		return this.giftManageDAO.findGift(channel,is_vail,type);
	}
}
