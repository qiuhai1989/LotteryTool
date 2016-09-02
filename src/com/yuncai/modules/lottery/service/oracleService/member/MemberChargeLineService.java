package com.yuncai.modules.lottery.service.oracleService.member;

import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.service.BaseService;

public interface MemberChargeLineService extends BaseService<MemberChargeLine, String> {
	public MemberChargeLine findByIdForUpdate(java.lang.String id);
	
	/*
	 * 处理充值
	 */
	public String charger(int walletType, Double amount, String extendInfo, String chargeNo, String responseInfo, String payedNo, String ip, Long ct,int chargeType,double formalitiesFees,String channel)throws Exception ;
	
	public void saveNumber(MemberChargeLine transientInstance);

}
