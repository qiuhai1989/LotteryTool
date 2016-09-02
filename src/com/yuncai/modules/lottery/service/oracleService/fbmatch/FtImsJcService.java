package com.yuncai.modules.lottery.service.oracleService.fbmatch;

import java.util.List;
import java.util.Map;

import com.yuncai.modules.lottery.model.Oracle.FtImsJc;
import com.yuncai.modules.lottery.service.BaseService;


public interface FtImsJcService extends BaseService<FtImsJc, Integer> {
	
	public List<Object[]> findFtImsJc(String expect);
	
	public List<Object[]> findFtIms(String expect);
	
	public List<Object[]> findFtIms10(final String expect);
	
	public List<Object[]> findFtImsByIds(String jids);
	
	public List<Map<String,Integer>> findMidAndJid(String mids);
	
	public List<Map<String, Object>> findRank(final String mids);
	public String findMatchJcResult(final Integer sqlId);
	
}
