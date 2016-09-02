package com.yuncai.modules.lottery.service.sqlService.apk;

import java.util.List;
import java.util.Map;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.model.Sql.ApkManage;
import com.yuncai.modules.lottery.service.BaseService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public interface ApkManageService extends BaseService<ApkManage, Integer>{
	
	/***
	 * 对象集合
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Map<String, Object>> find(int offset,int length,int packageId,Search search);
	
	/***
	 * 对象集合长度
	 * @return
	 */
	public int getCount(Search search,int packageId);
	
	public String getMaxVersion(int packageId);

	public List<Map<String, String>> findUpdVersion(int platformId,int channelId);
	
	public String findLastBak(Integer packageId, String maxVersion);
	
}
