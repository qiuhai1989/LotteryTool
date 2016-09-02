package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.GiftManage;
import com.yuncai.modules.lottery.model.Oracle.toolType.GiftManageSearch;
import com.yuncai.modules.lottery.service.BaseService;

public interface GiftManageService extends BaseService<GiftManage, Integer>{
	public abstract List<GiftManage> findBySearch(final GiftManageSearch search, final int offset, final int length);
	public abstract int getCountBySearch(final GiftManageSearch search);
	
	//根据活动查询是否存在这个活动
	public abstract List<GiftManage> findByChannelList(final String channel,final int  is_vail);
	//渠道； 赠送方式1充值，0直接赠送；  赠送类型 2注册，1充值； 当前时间
	public abstract List<GiftManage> findGift(String channel,int is_vail,int type);
}
