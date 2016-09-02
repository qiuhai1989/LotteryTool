package com.yuncai.modules.lottery.dao.oracleDao.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.EmailInfo;

public interface EmailInfoDAO extends GenericDao<EmailInfo,Integer>{
	// 按分页查找所有邮箱
	public List<EmailInfo> findAllEmailByPage(Class<EmailInfo> entityClass,final int offset,final int length);
	// 获取所有邮箱数量
	public long findAllCount();
	
	// 按照参数和分页查找邮箱
	public List<EmailInfo> findByPropertysAndPage(Class<EmailInfo> entityClass,String[] names,Object[] values,int offset, int length) ;
	// 按照参数和分页查找邮箱数量
	public long findCountByPropertys(String[] names,Object[] values);
}
