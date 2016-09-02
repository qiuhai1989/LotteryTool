package com.yuncai.modules.lottery.model.Oracle;

import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import java.util.*;

public class MemberOperLine extends AbstractMemberOperLine implements java.io.Serializable{
	// Constructors

	/** default constructor */
	public MemberOperLine() {
	}

	/** minimal constructor */
	public MemberOperLine(Integer operLineNo, Long terminalId, String account, MemberOperType operType, String ip, OperLineStatus status) {
		super(operLineNo, terminalId, account, operType, ip, status);
	}

	/** full constructor */
	public MemberOperLine(Integer operLineNo, Long terminalId, String account, MemberOperType operType, Date createDateTime, String ip,
			OperLineStatus status, String extendInfo, Integer sourceId, String referer, String url) {
		super(operLineNo, terminalId, account, operType, createDateTime, ip, status, extendInfo, sourceId, referer, url);
	}
	public MemberOperLine(Integer operLineNo, Long terminalId, String account, MemberOperType operType, Date createDateTime, String ip,
			OperLineStatus status, String extendInfo, Integer sourceId, String referer, String url, String phoneNo, String sim,String model, String systemVersion, String channel,String version) {
		super(operLineNo, terminalId, account, operType, createDateTime, ip, status, extendInfo, sourceId, referer, url, phoneNo, sim, model, systemVersion, channel,version);
	}
	
	
	public String getShortInfo() {
		String extendInfo = this.getExtendInfo();
		if (extendInfo == null || "".equals(extendInfo)) {
			return "";
		}

		if (extendInfo.length() > 35) {
			return extendInfo.substring(0, 35) + "...";
		} else {
			return extendInfo;
		}
	}


}
