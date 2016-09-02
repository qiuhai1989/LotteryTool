package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;

public class AbstractMemberOperLine {
	// Fields
	// 操作流水编号
	private Integer operLineNo;
	// 会员id
	private Long terminalId;
	// 会员帐号
	private String account;
	// 操作类型
	private MemberOperType operType;
	
	//购买彩种
	private LotteryType lotteryType=LotteryType.getItem(-1);
	// 生成时间
	private Date createDateTime;
	// ip地址
	private String ip;
	// 流水状态
	private OperLineStatus status;
	// 扩展信息
	private String extendInfo;
	// 来源编号
	private Integer sourceId;
	// 来源的页面
	private String referer;
	// ip所解析的地区
	private String fromPlace;
	// 当前url
	private String url;

	private String phoneNo;//机身码
	private String sim;//SIM卡
	private  String model;//手机机型
	private String systemVersion;//系统版本
	private String channel ;//渠道ID
	
	private String version;//客户端版本号
	
	// Constructors

	/** default constructor */
	public AbstractMemberOperLine() {
	}

	/** minimal constructor */
	public AbstractMemberOperLine(Integer operLineNo, Long terminalId, String account, MemberOperType operType, String ip, OperLineStatus status) {
		this.operLineNo = operLineNo;
		this.terminalId = terminalId;
		this.account = account;
		this.operType = operType;
		this.ip = ip;
		this.status = status;
	}

	/** full constructor */
	public AbstractMemberOperLine(Integer operLineNo, Long terminalId, String account, MemberOperType operType, Date createDateTime, String ip,
			OperLineStatus status, String extendInfo, Integer sourceId, String referer, String url) {
		this.operLineNo = operLineNo;
		this.terminalId = terminalId;
		this.account = account;
		this.operType = operType;
		this.createDateTime = createDateTime;
		this.ip = ip;
		this.status = status;
		this.extendInfo = extendInfo;
		this.sourceId = sourceId;
		this.referer = referer;
		this.url = url;
	}

	
	
	public AbstractMemberOperLine(Integer operLineNo, Long terminalId, String account, MemberOperType operType, Date createDateTime, String ip,
			OperLineStatus status, String extendInfo, Integer sourceId, String referer,  String url, String phoneNo, String sim,
			String model, String systemVersion, String channel,String version) {
		super();
		this.operLineNo = operLineNo;
		this.terminalId = terminalId;
		this.account = account;
		this.operType = operType;
		this.createDateTime = createDateTime;
		this.ip = ip;
		this.status = status;
		this.extendInfo = extendInfo;
		this.sourceId = sourceId;
		this.referer = referer;
		this.url = url;
		this.phoneNo = phoneNo;
		this.sim = sim;
		this.model = model;
		this.systemVersion = systemVersion;
		this.channel = channel;
		this.version = version;
	}

	public Integer getOperLineNo() {
		return operLineNo;
	}

	public void setOperLineNo(Integer operLineNo) {
		this.operLineNo = operLineNo;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public MemberOperType getOperType() {
		return operType;
	}

	public void setOperType(MemberOperType operType) {
		this.operType = operType;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public OperLineStatus getStatus() {
		return status;
	}

	public void setStatus(OperLineStatus status) {
		this.status = status;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}



}
