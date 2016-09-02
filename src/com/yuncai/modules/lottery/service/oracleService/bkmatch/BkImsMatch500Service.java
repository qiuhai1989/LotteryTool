package com.yuncai.modules.lottery.service.oracleService.bkmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.BkImsMatch500;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * 竞彩篮球-即时比分-赛事业务接口
 * @author blackworm
 *
 */
public interface BkImsMatch500Service extends BaseService<BkImsMatch500, Integer> {
	
	public List<BkImsMatch500> findBkImsMatch500s(String expect);
	
	public List<BkImsMatch500> findBkImsMatch500s(String expect,int status);
	
	/**
	 * 查询竞彩赛事
	 * @param expect
	 * @return
	 */
	public List<BkImsMatch500> findBkImsJcMatchs(String expect);
	
	public List<BkImsMatch500> findBkImsJcMatchsByMids(String mids);
	
}
