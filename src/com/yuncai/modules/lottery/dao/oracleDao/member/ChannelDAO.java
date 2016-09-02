package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.Channel;

public interface ChannelDAO extends GenericDao<Channel,Integer> {
	// 按分页查找所有渠道
	public List<Channel> findAllChannelByPage(Class<Channel> entityClass,final int offset,final int length);
	// 按分页和参数查找渠道
	public List<Channel> findByPropertysAndPage(Class<Channel> entityClass, final String[] names, final Object[] values, final int offset,
			final int length);
	// 获取渠道数量
	public long findAllCountByPropertys(final String[] names, final Object[] values);
	// 获取所有渠道数量
	public long findAllCount();
}
