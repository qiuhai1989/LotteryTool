package com.yuncai.modules.lottery.service.sqlService.user;

import java.util.List;
import com.yuncai.modules.lottery.model.Sql.Channel;
import com.yuncai.modules.lottery.service.BaseService;

public interface ChannelService  extends BaseService<Channel,Integer> {
	
	//根据渠道ID获取对象集合
	public List<Channel> findByPlatformId(int platformId);

	public List<Channel> findByIds(String string);
}
