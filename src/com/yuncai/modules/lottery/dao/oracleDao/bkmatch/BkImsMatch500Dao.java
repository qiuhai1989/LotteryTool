package com.yuncai.modules.lottery.dao.oracleDao.bkmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.BkImsMatch500;


/**
 * 竞彩篮球-即时比分-赛事DAO接口
 * @author blackworm
 *
 */
public interface BkImsMatch500Dao {
	
	public List<BkImsMatch500> findBkImsMatch500s(String expect);
	
	public List<BkImsMatch500> findBkImsMatch500s(String expect,int status);
	
	/**
	 * 查询竞彩赛事
	 * @param expect
	 * @return
	 */
	public List<BkImsMatch500> findBkImsJcMatchs(String expect);
	
	/**
	 * 根据mid列表查询竞彩赛事
	 * @param mids
	 * @return
	 */
	public List<BkImsMatch500> findBkImsJcMatchsByMids(String mids);
	
}
