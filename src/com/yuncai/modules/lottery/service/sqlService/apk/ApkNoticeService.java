package com.yuncai.modules.lottery.service.sqlService.apk;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.model.Sql.ApkNotice;
import com.yuncai.modules.lottery.service.BaseService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public interface ApkNoticeService extends BaseService<ApkNotice, Integer>{

	/***
	 * 对象集合
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<ApkNotice> find(int offset,int length,Search search);
	
	/***
	 * 对象集合长度
	 * @return
	 */
	public int getCount(Search search);

	public int getMaxCode();

	public List<ApkNotice> findAll(String time,String str);
}
