package com.yuncai.modules.lottery.service.sqlService.impl.user;

import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.user.ChannelDAO;
import com.yuncai.modules.lottery.model.Sql.Channel;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.user.ChannelService;


public class ChannelServiceImpl  extends BaseServiceImpl<Channel,Integer> implements ChannelService {
	private ChannelDAO sqlChannelDAO;
	
	public void setSqlChannelDAO(ChannelDAO sqlChannelDAO) {
		this.sqlChannelDAO = sqlChannelDAO;
	}

	@Override
	public List<Channel> findByPlatformId(int platformId) {
		return sqlChannelDAO.findByPlatformId(platformId);
	}

	@Override
	public List<Channel> findByIds(String string) {
		return sqlChannelDAO.findByIds(string);
	}
	
	
}
