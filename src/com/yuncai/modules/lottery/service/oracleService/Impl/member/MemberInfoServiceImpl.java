package com.yuncai.modules.lottery.service.oracleService.Impl.member;

import java.util.Date;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberInfoDAO;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.model.Oracle.MemberInfo;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.service.BaseServiceImpl;
import com.yuncai.modules.lottery.service.oracleService.member.MemberInfoService;

public class MemberInfoServiceImpl extends BaseServiceImpl<MemberInfo, Integer> implements MemberInfoService {	
	private MemberInfoDAO memberInfoDAO;
	private IpSeeker ipSeeker;
	private MemberOperLineDAO memberOperLineDao;
	
	public Integer update(MemberInfo memberInfo, Long ct, String ip, String referer, String key,String channel,String version)
	{	

		Integer operLineNo=null;
//		LogUtil.out("开始绑定信息");
		this.memberInfoDAO.update(memberInfo);
//		LogUtil.out("绑定成功");
		
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(memberInfo.getAccount());
		//operLine.setSourceId(memberInfo.getSourceId());
		operLine.setCreateDateTime(new Date());
		operLine.setReferer(referer);
		operLine.setUrl("");
		operLine.setIp(ip);
		String country = ipSeeker.getCountry(ip);
		String area = ipSeeker.getArea(ip);
		String fromPlace = country + area;

		operLine.setFromPlace(fromPlace);

		operLine.setTerminalId(ct);
		operLine.setOperType(MemberOperType.BINDBANKCARD);
		operLine.setStatus(OperLineStatus.SUCCESS);
		operLine.setChannel(channel);
		operLine.setExtendInfo(key);
		operLine.setVersion(version);
		memberOperLineDao.save(operLine);
		operLineNo = operLine.getOperLineNo();
		
		return operLineNo;
		
	}

	
	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}
	
	public void setMemberInfoDAO(MemberInfoDAO memberInfoDAO) {
		this.memberInfoDAO = memberInfoDAO;
	}



	
	
}
