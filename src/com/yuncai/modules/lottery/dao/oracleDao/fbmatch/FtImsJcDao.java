package com.yuncai.modules.lottery.dao.oracleDao.fbmatch;

import java.util.List;
import java.util.Map;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.FtImsJc;

/**
 * 足球即时比分-竞彩DAO接口
 * @author blackworm
 *
 */
public interface FtImsJcDao extends GenericDao<FtImsJc, Integer>{

	public List<Object[]> findFtImsJc(String expect);
	
	public List<Object[]> findFtIms(String expect);
	
	public List<Object[]> findFtIms10(final String startDate,final String endDate);
	
	public List<Object[]> findFtImsByIds(String jids);
	
	/**
	 * 根据mid查询mid和jid
	 * @param mids  12608,12608,12606
	 * @return
	 */
	public List<Map<String,Integer>> findMidAndJid(String mids);
	
	public List<Map<String, Object>> findRank(final String mids);
	/**查找及时比分赛果
	 * @param sqlId
	 * @return
	 */
	public String findMatchJcResult(final Integer sqlId);
}
