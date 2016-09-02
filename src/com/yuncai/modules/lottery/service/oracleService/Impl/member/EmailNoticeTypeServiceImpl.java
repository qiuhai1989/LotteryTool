package com.yuncai.modules.lottery.service.oracleService.Impl.member;



import java.util.List;

import com.yuncai.modules.lottery.dao.oracleDao.member.EmailNoticeTypeDAO;
import com.yuncai.modules.lottery.model.Oracle.EmailNoticeType;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.EmailNoticeTypeService;

/**
 * TODO
 * @author gx
 * Dec 24, 2013 4:19:38 PM
 */
public class EmailNoticeTypeServiceImpl extends BaseServiceImpl<EmailNoticeType,Integer> implements EmailNoticeTypeService{
	private EmailNoticeTypeDAO emailNoticeTypeDAO;

	public EmailNoticeTypeDAO getEmailNoticeTypeDAO() {
		return emailNoticeTypeDAO;
	}

	public void setEmailNoticeTypeDAO(EmailNoticeTypeDAO emailNoticeTypeDAO) {
		this.emailNoticeTypeDAO = emailNoticeTypeDAO;
	}

	@Override
	public List<EmailNoticeType> findAll() {
		return emailNoticeTypeDAO.findAll();
	}
	
}
