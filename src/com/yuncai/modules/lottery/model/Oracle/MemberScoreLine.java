package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.ScoreType;

public class MemberScoreLine extends AbstractMemberScoreLine implements java.io.Serializable {
	// Constructors

	/** default constructor */
	public MemberScoreLine() {
	}

	/** minimal constructor */
	public MemberScoreLine(Integer scoreLineNo, Integer memberId, String account, Integer value, ScoreType scoreType) {
		super(scoreLineNo, memberId, account, value, scoreType);
	}

	/** full constructor */
	public MemberScoreLine(Integer scoreLineNo, Integer memberId, String account, Integer value, ScoreType scoreType, Date createDateTime) {
		super(scoreLineNo, memberId, account, value, scoreType, createDateTime);
	}

}
