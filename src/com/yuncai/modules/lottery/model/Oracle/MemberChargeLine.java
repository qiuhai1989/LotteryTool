package com.yuncai.modules.lottery.model.Oracle;
import java.util.Date;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.RechargeScoreType;

public class MemberChargeLine extends AbstractMemberChargeLine implements java.io.Serializable{
	// Constructors

	/** default constructor */
	public MemberChargeLine() {
	}

	/** minimal constructor */
	public MemberChargeLine(String chargeNo) {
		super(chargeNo);
	}

	/** full constructor */
	public MemberChargeLine(String chargeNo, String account, Date createDateTime, Date successDateTime, Double amount, CommonStatus status,
			String payedNo, String requestInfo, String responseInfo, String bank, ChargeType chargeType, String bankCard,RechargeScoreType rechargeScoreType,double formalitiesFees) {
		super(chargeNo, account, createDateTime, successDateTime, amount, status, payedNo, requestInfo, responseInfo, bank, chargeType, bankCard, rechargeScoreType,formalitiesFees);
	}
}
