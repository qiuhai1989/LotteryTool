package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.action.member.CluesSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.Clues;

public interface CluesDAO extends GenericDao<Clues, Integer>{

	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 11:06:38 AM
	 */
	public abstract List<Clues> findBySearch(final CluesSearch search, final int i, final int pageSize);

	public abstract int getCountBySearch(final CluesSearch search);

}
