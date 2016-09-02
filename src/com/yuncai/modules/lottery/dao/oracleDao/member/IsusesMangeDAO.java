package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.action.lottery.IsusesMangeSearch;
import com.yuncai.modules.lottery.bean.vo.member.IsusesMangeVo;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.IsusesMange;

/**
 * TODO
 * @author gx
 * Dec 13, 2013 5:25:32 PM
 */
public interface IsusesMangeDAO extends GenericDao<IsusesMange, Integer>{
	public abstract List<IsusesMangeVo> findBySearch(final IsusesMangeSearch search,final int offset,final int length,int platformID);

	public abstract int getCountBySearch(final IsusesMangeSearch search,final int platformID);
	
	public abstract List<IsusesMange> findByPlatformID_isShow(int isShow, int platformID);

	public abstract List<IsusesMange> findByChannelID_isShow(int i, int channelID);
}
