package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.DrawingStatus;

public class AbstractMemberDrawingLine implements java.io.Serializable {
	// Fields
	// 编号
	private String id;
	// 提款单号
	private String drawingId;
	// 操作者用户名
	private String account;
	// 备注
	private String remark;
	// 操作时间
	private Date createDateTime;
	// 操作类型
	private DrawingStatus status;

	/** default constructor */
	public AbstractMemberDrawingLine() {
	}

	/** minimal constructor */
	public AbstractMemberDrawingLine(String id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractMemberDrawingLine(String id, String drawingId, String account, String remark, Date createDateTime, DrawingStatus status) {
		this.id=id;
		this.drawingId=drawingId;
		this.account=account;
		this.remark=remark;
		this.createDateTime=createDateTime;
		this.status=status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(String drawingId) {
		this.drawingId = drawingId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public DrawingStatus getStatus() {
		return status;
	}

	public void setStatus(DrawingStatus status) {
		this.status = status;
	}

}
