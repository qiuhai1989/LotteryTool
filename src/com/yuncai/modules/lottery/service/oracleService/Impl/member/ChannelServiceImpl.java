package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.member.ChannelDAO;
import com.yuncai.modules.lottery.model.Oracle.Channel;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.ChannelService;

public class ChannelServiceImpl  extends BaseServiceImpl<Channel,Integer> implements ChannelService {
	private ChannelDAO channelDAO;

	// 按照分页查找所有新闻
	public List<Channel> findAllChannelByPage(Integer offset,Integer length){
		return channelDAO.findAllChannelByPage(getEntityClass(), offset, length);
	}
	// 按分页和参数查找新闻
	public List<Channel> findByPropertysAndPage(String[] names, Object[] values, Integer offset,Integer length){
		return channelDAO.findByPropertysAndPage(getEntityClass(), names, values, offset, length);
	}
	// 获取新闻数量
	public long findAllCountByPropertys( String[] names, Object[] values){
		return channelDAO.findAllCountByPropertys(names, values);
	}
	// 获取所有新闻数量
	public long findAllCount(){
		return channelDAO.findAllCount();
	}
	
	
	
	
	public ChannelDAO getChannelDAO() {
		return channelDAO;
	}

	public void setChannelDAO(ChannelDAO channelDAO) {
		this.channelDAO = channelDAO;
	}
	
	
}
