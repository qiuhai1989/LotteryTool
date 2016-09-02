package com.yuncai.modules.lottery.service.sqlService.user;

import java.util.Date;
import java.util.List;

import com.yuncai.modules.lottery.model.Sql.Sms;
import com.yuncai.modules.lottery.service.BaseService;

public interface SmsService extends BaseService<Sms,Integer> {
	public List<Sms> findByUserAndDate(String User,Date startTime,Date endTime);
}
