package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.action.lottery.IsusesMangeSearch;
import com.yuncai.modules.lottery.bean.vo.member.IsusesMangeVo;
import com.yuncai.modules.lottery.model.Oracle.IsusesMange;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * TODO
 * @author gx
 * Dec 13, 2013 4:19:38 PM
 */
public interface IsusesMangeService extends BaseService<IsusesMange,Integer>{

	public abstract List<IsusesMangeVo> findBySearch(final IsusesMangeSearch search,final int offset,final int length,int platformID);

	public abstract int getCountBySearch(final IsusesMangeSearch search,final int platformID);
	
	public abstract List<IsusesMange> findByPlatformID_isShow(int isShow, int platformID);

	public abstract List<IsusesMange> findByChannelID_isShow(int i, int channelID);
}
