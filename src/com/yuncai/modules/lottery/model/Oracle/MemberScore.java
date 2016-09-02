package com.yuncai.modules.lottery.model.Oracle;

public class MemberScore extends AbstractMemberScore implements java.io.Serializable {


	// Constructors

	/** default constructor */
	public MemberScore() {
	}

	/** full constructor */
	public MemberScore(Integer memberId, String account, Integer ableScore, Integer heapScore) {
		super(memberId, account, ableScore, heapScore);
	}
}
