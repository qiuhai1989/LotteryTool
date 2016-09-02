package com.yuncai.modules.lottery.dao.oracleDao.fbmatch;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.FtImsSfc;

/**
 * 足球即时比分-胜负彩DAO接口
 * @author blackworm
 *
 */
public interface FtImsSfcDao extends GenericDao<FtImsSfc, Integer>{

	public List<FtImsSfc> findFtImsSfc(String expect,int atype);
}
