package com.yuncai.modules.lottery.service.sqlService.apk;

import java.util.List;

import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.model.Sql.ApkPackage;
import com.yuncai.modules.lottery.service.BaseService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
public interface ApkPackageService extends BaseService<ApkPackage, Integer>{

	/***
	 * 对象集合
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<ApkPackage> find(int offset,int length,Search search);
	
	/***
	 * 对象集合长度
	 * @return
	 */
	public int getCount(Search search);
	
	public List<ApkPackage> find();
}
