package com.yuncai.modules.lottery.dao.sqlDao.apk;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.ApkPush;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public interface ApkPushDAO extends GenericDao<ApkPush, Integer>{
	
	/***
	 * 对象集合
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<ApkPush> find(int offset,int length,Search search);
	
	/***
	 * 对象集合长度
	 * @return
	 */
	public int getCount(Search search);
	
	public int getMaxCode();

	public List<ApkPush> findAll(String now,String str,String receiveUser,String agentId);

	
}
