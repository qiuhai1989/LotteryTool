package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.DrawingStatus;

public class MemberDrawing extends AbstractMemberDrawing implements java.io.Serializable{
	// Constructors
	private Member member;

	/** default constructor */
	public MemberDrawing() {
	}

	/** minimal constructor */
	public MemberDrawing(String id) {
		super(id);
	}

	/** full constructor */
	public MemberDrawing(String id, String bank, String partBank, String bankCard, Float amount, String account, DrawingStatus status,
			Date createDateTime,Double formalitiesFees) {
		super(id, bank, partBank, bankCard, amount, account, status, createDateTime,formalitiesFees);
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
