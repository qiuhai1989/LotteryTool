package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.action.member.CluesSearch;
import com.yuncai.modules.lottery.dao.oracleDao.member.CluesDAO;
import com.yuncai.modules.lottery.model.Oracle.Clues;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.CluesService;

public class CluesServiceImpl extends BaseServiceImpl<Clues, Integer> implements CluesService {
	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 11:02:37 AM
	 */
	private CluesDAO cluesDao;
	
	public void setCluesDao(CluesDAO cluesDao) {
		this.cluesDao = cluesDao;
	}
	
	
	@Override
	public List<Clues> findBySearch(CluesSearch search, int i, int pageSize) {
		return this.cluesDao.findBySearch(search,i,pageSize);
	}


	@Override
	public int getCountBySearch(CluesSearch search) {
		return this.cluesDao.getCountBySearch(search);
	}
	
}
