package com.yuncai.modules.lottery.service.sqlService.impl.user;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.dao.sqlDao.user.SmsDAO;
import com.yuncai.modules.lottery.model.Sql.Sms;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.sqlService.user.SmsService;

public class SmsServiceImpl extends BaseServiceImpl<Sms,Integer> implements SmsService {
	private SmsDAO sqlSmsDAO;
	
	public List<Sms> findByUserAndDate(String User,Date startTime,Date endTime){
		return sqlSmsDAO.findByUserAndDate(User, startTime,endTime);
	}

	public void setSqlSmsDAO(SmsDAO sqlSmsDAO) {
		this.sqlSmsDAO = sqlSmsDAO;
	}
}
