package com.yuncai.modules.lottery.service.oracleService.member;


import java.util.List;

import com.yuncai.modules.lottery.model.Oracle.EmailNoticeType;
import com.yuncai.modules.lottery.service.BaseService;

/**
 * TODO
 * @author gx
 * Dec 24, 2013 4:19:38 PM
 */
public interface EmailNoticeTypeService extends BaseService<EmailNoticeType,Integer>{

	public List<EmailNoticeType> findAll();
}
