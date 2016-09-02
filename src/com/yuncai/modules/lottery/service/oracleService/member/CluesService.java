package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.action.member.CluesSearch;
import com.yuncai.modules.lottery.model.Oracle.Clues;
import com.yuncai.modules.lottery.service.BaseService;

public interface CluesService extends BaseService<Clues, Integer>{
	/**
	 * TODO
	 * @author gx
	 * Dec 4, 2013 10:52:46 AM
	 */

	public List<Clues> findBySearch(CluesSearch search, int i, int pageSize);

	public int getCountBySearch(final CluesSearch search);
	
}
