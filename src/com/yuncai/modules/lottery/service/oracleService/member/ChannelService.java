package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.Channel;
import com.yuncai.modules.lottery.service.BaseService;

public interface ChannelService  extends BaseService<Channel,Integer> {
	// 按照分页查找所有渠道
	public List<Channel> findAllChannelByPage(Integer offset,Integer length);
	// 按分页和参数查找渠道
	public List<Channel> findByPropertysAndPage(String[] names, Object[] values, Integer offset,Integer length);
	// 获取渠道数量
	public long findAllCountByPropertys(final String[] names, final Object[] values);
	// 获取所有渠道数量
	public long findAllCount();
}
