package com.yuncai.modules.lottery.service.oracleService.member;

import com.yuncai.modules.lottery.model.Oracle.MemberInfo;
import com.yuncai.modules.lottery.service.BaseService;

public interface MemberInfoService extends BaseService<MemberInfo, Integer> {
	public	Integer update(MemberInfo memberInfo, Long ct, String ip, String referer, String key,String channel,String verison);
	
}
