package com.yuncai.modules.lottery.dao.sqlDao.user;

import java.util.List;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.Channel;
public interface ChannelDAO extends GenericDao<Channel,Integer> {
	
	//根据渠道ID获取对象集合
	public List<Channel> findByPlatformId(int platformId);

	public List<Channel> findByIds(String string);
}
