package com.yuncai.modules.lottery.service.sqlService.user;

import java.util.List;
import com.yuncai.modules.lottery.model.Sql.Platform;
import com.yuncai.modules.lottery.service.BaseService;

public interface PlatformService  extends BaseService<Platform,Integer>{
	 public List<Platform> findAllPlatform();
}
