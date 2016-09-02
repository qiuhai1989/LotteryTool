package com.yuncai.modules.lottery.service.oracleService.system;

import com.yuncai.modules.lottery.service.BaseService;
import com.yuncai.modules.lottery.action.system.SystemErrorSearch;
import com.yuncai.modules.lottery.model.Oracle.SystemError;
import java.util.*;

public interface SystemErrorService extends BaseService<SystemError, Integer> {
	public Integer countBySearch(final SystemErrorSearch errorSearch);
	
	public List<SystemError> findBySearch(final SystemErrorSearch errorSearch,final Integer offset,final Integer length);
	

}
