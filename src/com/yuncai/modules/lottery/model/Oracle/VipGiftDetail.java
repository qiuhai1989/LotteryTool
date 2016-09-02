package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

public class VipGiftDetail extends AbstractVipGiftDetail implements java.io.Serializable {

	public VipGiftDetail() {
		super();
	}

	public VipGiftDetail(Integer id, String giftName, Integer platFormeId, String platformeName, Integer channelId, String channelName, Integer memberId, String account, Double amount, Date createTimeDate,
			Integer vipId, String vipAccount) {
		super(id, giftName, platFormeId, platformeName, channelId, channelName, memberId, account, amount, createTimeDate, vipId, vipAccount);
	}
}
