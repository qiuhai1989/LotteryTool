package com.yuncai.modules.lottery.dao.sqlDao.user;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.dao.GenericDao;
import com.yuncai.modules.lottery.model.Sql.Sms;

public interface SmsDAO extends GenericDao<Sms,Integer> {
	public List<Sms> findByUserAndDate(String User,Date nowDate,Date endTime);
}
