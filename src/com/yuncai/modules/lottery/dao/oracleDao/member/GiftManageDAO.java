package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.GiftManage;
import com.yuncai.modules.lottery.model.Oracle.toolType.GiftManageSearch;

public interface GiftManageDAO extends GenericDao<GiftManage, Integer>{
	public abstract List<GiftManage> findBySearch(final GiftManageSearch search, final int offset, final int length);
	public abstract int getCountBySearch(final GiftManageSearch search);
	
	//根据活动查询是否存在这个活动
	public abstract List<GiftManage> findByChannelList(final String channel,final int  is_vail);
	//渠道； 赠送方式1充值，0直接赠送；  赠送类型 2注册，1充值； 当前时间
	public abstract List<GiftManage> findGift(String channel,int is_vail,int type);
}
