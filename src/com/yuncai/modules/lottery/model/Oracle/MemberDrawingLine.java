package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.DrawingStatus;

public class MemberDrawingLine extends AbstractMemberDrawingLine implements java.io.Serializable {
	private MemberDrawingLine memberDrawingLine;

	/** default constructor */
	public MemberDrawingLine() {
	}

	/** minimal constructor */
	public MemberDrawingLine(String id) {
		super(id);
	}

	/** full constructor */
	public MemberDrawingLine(String id, String drawingId, String account, String remark, Date createDateTime, DrawingStatus status) {
		super(id, drawingId, account, remark, createDateTime, status);
	}

	public MemberDrawingLine getMemberDrawingLine() {
		return memberDrawingLine;
	}

	public void setMemberDrawingLine(MemberDrawingLine memberDrawingLine) {
		this.memberDrawingLine = memberDrawingLine;
	}

}
