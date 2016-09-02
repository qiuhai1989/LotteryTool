package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class AccountTrace extends AbstractAccountTrace implements java.io.Serializable {

	public AccountTrace() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountTrace(Integer id, String account, Integer root, String ip, String channel, Integer lotteryType, String pageId, String sessionId,
			Date createDateTime, String terminalId, String systemVersion, String model, String softwareVersion, String browser, String operator) {
		super(id, account, root, ip, channel, lotteryType, pageId, sessionId, createDateTime, terminalId, systemVersion, model, softwareVersion, browser,
				operator);
		// TODO Auto-generated constructor stub
	}

}
