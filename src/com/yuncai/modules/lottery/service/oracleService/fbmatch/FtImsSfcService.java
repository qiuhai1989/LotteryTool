package com.yuncai.modules.lottery.service.oracleService.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.FtImsSfc;
import com.yuncai.modules.lottery.service.BaseService;


public interface FtImsSfcService extends BaseService<FtImsSfc, Integer> {
	
	public List<FtImsSfc> findFtImsSfc(String expect,Integer atype);
}
