package com.yuncai.modules.lottery.dao.oracleDao.member;


import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Oracle.EmailNoticeType;

/**
 * TODO
 * @author gx
 * Dec 24, 2013 4:19:38 PM
 */
public interface EmailNoticeTypeDAO extends GenericDao<EmailNoticeType,Integer>{

	public List<EmailNoticeType> findAll();
}
