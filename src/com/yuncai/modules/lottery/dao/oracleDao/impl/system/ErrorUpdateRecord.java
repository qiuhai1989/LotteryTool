package com.yuncai.modules.lottery.dao.oracleDao.impl.system;
import java.util.*;

public class ErrorUpdateRecord {
	/**
	 * 更新明细
	 */
	private String detail;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateDateTime;
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public ErrorUpdateRecord() {
		super();
	}
	public ErrorUpdateRecord(String detail, String updateBy, Date updateDateTime) {
		super();
		this.detail = detail;
		this.updateBy = updateBy;
		this.updateDateTime = updateDateTime;
	}

}
