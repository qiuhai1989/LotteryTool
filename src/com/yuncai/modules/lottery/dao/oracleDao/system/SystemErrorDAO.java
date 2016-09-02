package com.yuncai.modules.lottery.dao.oracleDao.system;

import com.yuncai.modules.lottery.action.system.SystemErrorSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.SystemError;
import java.util.*;

public interface SystemErrorDAO extends GenericDao<SystemError, Integer> {
	
	public Integer countBySearch(final SystemErrorSearch errorSearch);
	
	public List<SystemError> findBySearch(final SystemErrorSearch errorSearch,final Integer offset,final Integer length);
	

}
