package com.yuncai.modules.lottery.service.oracleService.member;

import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.EmailInfo;
import com.yuncai.modules.lottery.service.BaseService;

public interface EmailInfoService extends BaseService<EmailInfo, Integer>{
	// 按照分页查找所有邮箱
	public List<EmailInfo> findAllEmailByPage(Integer offset,Integer length);
	// 获取所有邮箱数量
	public long findAllCount();
	
	// 按照参数和分页查找邮箱
	public List<EmailInfo> findByPropertysAndPage(String[] names,Object[] values,int offset, int length) ;
	// 按照参数和分页查找邮箱数量
	public long findCountByPropertys(String names[],Object values[]);
}
