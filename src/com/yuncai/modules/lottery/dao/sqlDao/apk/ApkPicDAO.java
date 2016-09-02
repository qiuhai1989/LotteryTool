package com.yuncai.modules.lottery.dao.sqlDao.apk;

import java.util.List;
import java.util.Map;

import com.yuncai.modules.lottery.action.admin.search.ApkPicSearch;
import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.ApkPic;

/***
 * 广告图管理
 * @author gx
 *
 */
public interface ApkPicDAO extends GenericDao<ApkPic, Integer>{
	
	/***
	 * 对象集合
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Map<String, Object>> find(ApkPicSearch search,int offset,int length);
	
	/***
	 * 对象集合长度
	 * @return
	 */
	public int getCount(ApkPicSearch search);

	public int findByCode(int code);

	public int findById(int id);

	public int getMaxCode();
	
	
}
