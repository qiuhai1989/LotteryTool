package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.member.EmailInfoDAO;
import com.yuncai.modules.lottery.model.Oracle.EmailInfo;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.EmailInfoService;

public class EmailInfoServiceImpl extends BaseServiceImpl<EmailInfo, Integer> implements EmailInfoService{
	private EmailInfoDAO emailInfoDAO;
	
	// 按照分页查找所有邮箱
	public List<EmailInfo> findAllEmailByPage(Integer offset,Integer length){
		return emailInfoDAO.findAllEmailByPage(getEntityClass(), offset, length);
	}
	// 获取所有邮箱数量
	public long findAllCount(){
		return emailInfoDAO.findAllCount();
	}
	
	// 按照分页查找所有邮箱
	public List<EmailInfo> findByPropertysAndPage(String[] names, Object[] values, int offset, int length){
		return emailInfoDAO.findByPropertysAndPage(getEntityClass(),names,values, offset, length);
	}
	
	// 按照参数和分页查找邮箱数量
	public long findCountByPropertys(String names[],Object values[]){
		return emailInfoDAO.findCountByPropertys(names,values);
	}
	public void setEmailInfoDAO(EmailInfoDAO emailInfoDAO) {
		this.emailInfoDAO = emailInfoDAO;
	}
	
	
}
