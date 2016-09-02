package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.Platform;
import com.yuncai.modules.lottery.service.BaseService;

public interface PlatformService  extends BaseService<Platform,Integer>{
	 public List<Platform> findAllPlatform();
}
