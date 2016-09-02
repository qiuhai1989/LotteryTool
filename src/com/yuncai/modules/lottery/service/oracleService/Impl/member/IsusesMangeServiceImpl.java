package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;


import com.yuncai.modules.lottery.action.lottery.IsusesMangeSearch;
import com.yuncai.modules.lottery.bean.vo.member.IsusesMangeVo;
import com.yuncai.modules.lottery.dao.oracleDao.member.IsusesMangeDAO;
import com.yuncai.modules.lottery.model.Oracle.IsusesMange;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.IsusesMangeService;
/**
 * TODO
 * @author gx
 * Dec 13, 2013 5:22:36 PM
 */
public class IsusesMangeServiceImpl extends BaseServiceImpl<IsusesMange, Integer> implements IsusesMangeService{
	private IsusesMangeDAO isusesMangeDAO;
	
	
	public IsusesMangeDAO getIsusesMangeDAO() {
		return isusesMangeDAO;
	}


	public void setIsusesMangeDAO(IsusesMangeDAO isusesMangeDAO) {
		this.isusesMangeDAO = isusesMangeDAO;
	}


	@Override
	public List<IsusesMangeVo> findBySearch(final IsusesMangeSearch search, int offset, int length, int platformID) {
		return isusesMangeDAO.findBySearch(search,offset,length,platformID);
	}


	@Override
	public int getCountBySearch(final IsusesMangeSearch search ,final int platformID) {
		return isusesMangeDAO.getCountBySearch(search,platformID);
	}
	
	@Override
	public List<IsusesMange> findByPlatformID_isShow(int isShow, int platformID) {
		return isusesMangeDAO.findByPlatformID_isShow(isShow, platformID);
	}


	@Override
	public List<IsusesMange> findByChannelID_isShow(int i, int channelID) {
		return isusesMangeDAO.findByChannelID_isShow(i,channelID);
	}
	
}
