package com.yuncai.modules.lottery.model.Oracle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.system.ErrorGrade;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorRemark;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorStatus;
import com.yuncai.modules.lottery.model.Oracle.system.ErrorType;


import net.sf.json.JSONArray;


public class SystemError extends AbstractSystemError {
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public SystemError() {
	}
	
	/** full constructor */
	public SystemError(Integer id, ErrorType type, ErrorGrade grade, String content, ErrorStatus status, Date createDateTime, String createBy,
			Date updateDateTime, String updateBy, String remark) {
		super(id, type, grade, content, status, createDateTime, createBy, updateDateTime, updateBy, remark);
	}
	public Collection<ErrorRemark> getRemarkList(){
		if(this.getRemark()!=null&&!"".equals(this.getRemark())){
			return JSONArray.toCollection(JSONArray.fromObject(this.getRemark()),ErrorRemark.class);
		}
		return new ArrayList<ErrorRemark>();
	}

}
