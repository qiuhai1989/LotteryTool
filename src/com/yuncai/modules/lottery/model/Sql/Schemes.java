package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;

/**
 * Schemes entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Schemes implements java.io.Serializable {

	// Fields

	private Integer id;
	//站点
	private Integer siteId;
	//生成时间
	private Date dateTime;
	//方案号
	private String schemeNumber;
	//方案标题
	private String title;
	//发起人ID
	private Integer initiateUserId;
	//期号id,对应表表 Isuses
	private Integer isuseId;
	//指向玩法类别 PlayTypes 表 ID
	private Integer playTypeId;
	//倍数
	private Integer multiple;
	//方案总金额
	private Double money;
	//保底金额
	private Double assureMoney;
	//总份数
	private Integer share;
	//0 不保密 1 到截止 2 到开奖 3 永远
	private Short secrecyLevel;
	//撤单状态:0 未撤单 1 用户撤单 2 系统撤单
	private Short quashStatus;
	//已出票
	private Boolean buyed;
	//出票员ID,对应于 UserID
	private Integer buyOperatorId;
	//出票方式(1 - 本地出票 2 - 电子票接口)
	private Short printOutType;
	//彩票标识码
	private String identifiers;
	//是否开奖了,如单场可以部分方案先开奖
	private Boolean isOpened;
	//开奖操作员ID
	private Integer openOperatorId;
	//中奖金额
	private Double winMoney;
	//中奖税后金额
	private Double winMoneyNoWithTax;
	//方案盈利时，发起人提取的佣金金额
	private Double initiateBonus;
	//置顶状态 0 无 1 申请置顶状态 2 已置顶状态，满员或者撤单后会自动设置为 0
	private Short atTopStatus;
	//
	private Boolean isCanChat;
	private Double preWinMoney;
	private Double preWinMoneyNoWithTax;
	//编辑后的中奖金额
	private Double editWinMoney;
	//编辑后的税后金额
	private Double editWinMoneyNoWithTax;
	//已购买份数
	private Integer buyedShare;
	//进度
	private Double schedule;
	//排序用的进度，满100后置为0，就排序到最后了
	private Double reSchedule;
	//方案是否计算奖金
	private Boolean isSchemeCalculatedBonus;
	//方案描述
	private String description;
	//方案内容
	private String lotteryNumber;
	//上传方案内容(弃用)
	private String uploadFileContent;
	//中奖描述
	private String winDescription;
	private String winImage;
	//更新时间
	private Date updateDatetime;
	//出票时间
	private Date printOutDateTime;
	private Short ot;
	private Short outTo;
	private Integer correlationSchemeId;
	private Double schemeBonusScale;
	//代购还是合买
	private Boolean isPurchasing;
	private Integer fromClient;
	private Integer ycSchemeNumber; //订单号yc
	private GenType changeType; //购买的渠道
	private String quashReason;
	private String winNumber;
	private Integer openWinState;
	private String lotteryNumberNew; //根据竞彩新增字段进行封装
	// Constructors
	private String channel;
	
	



	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getYcSchemeNumber() {
		return ycSchemeNumber;
	}

	public void setYcSchemeNumber(Integer ycSchemeNumber) {
		this.ycSchemeNumber = ycSchemeNumber;
	}

	/** default constructor */
	public Schemes() {
	}

	/** full constructor */
	public Schemes(Integer siteId, Date dateTime, String schemeNumber,
			String title, Integer initiateUserId, Integer isuseId,
			Integer playTypeId, Integer multiple, Double money,
			Double assureMoney, Integer share, Short secrecyLevel,
			Short quashStatus, Boolean buyed, Integer buyOperatorId,
			Short printOutType, String identifiers, Boolean isOpened,
			Integer openOperatorId, Double winMoney, Double winMoneyNoWithTax,
			Double initiateBonus, Short atTopStatus, Boolean isCanChat,
			Double preWinMoney, Double preWinMoneyNoWithTax,
			Double editWinMoney, Double editWinMoneyNoWithTax,
			Integer buyedShare, Double schedule, Double reSchedule,
			Boolean isSchemeCalculatedBonus, String description,
			String lotteryNumber, String uploadFileContent,
			String winDescription, String winImage, Date updateDatetime,
			Date printOutDateTime, Short ot, Short outTo,
			Integer correlationSchemeId, Double schemeBonusScale,
			Boolean isPurchasing, Integer fromClient) {
		this.siteId = siteId;
		this.dateTime = dateTime;
		this.schemeNumber = schemeNumber;
		this.title = title;
		this.initiateUserId = initiateUserId;
		this.isuseId = isuseId;
		this.playTypeId = playTypeId;
		this.multiple = multiple;
		this.money = money;
		this.assureMoney = assureMoney;
		this.share = share;
		this.secrecyLevel = secrecyLevel;
		this.quashStatus = quashStatus;
		this.buyed = buyed;
		this.buyOperatorId = buyOperatorId;
		this.printOutType = printOutType;
		this.identifiers = identifiers;
		this.isOpened = isOpened;
		this.openOperatorId = openOperatorId;
		this.winMoney = winMoney;
		this.winMoneyNoWithTax = winMoneyNoWithTax;
		this.initiateBonus = initiateBonus;
		this.atTopStatus = atTopStatus;
		this.isCanChat = isCanChat;
		this.preWinMoney = preWinMoney;
		this.preWinMoneyNoWithTax = preWinMoneyNoWithTax;
		this.editWinMoney = editWinMoney;
		this.editWinMoneyNoWithTax = editWinMoneyNoWithTax;
		this.buyedShare = buyedShare;
		this.schedule = schedule;
		this.reSchedule = reSchedule;
		this.isSchemeCalculatedBonus = isSchemeCalculatedBonus;
		this.description = description;
		this.lotteryNumber = lotteryNumber;
		this.uploadFileContent = uploadFileContent;
		this.winDescription = winDescription;
		this.winImage = winImage;
		this.updateDatetime = updateDatetime;
		this.printOutDateTime = printOutDateTime;
		this.ot = ot;
		this.outTo = outTo;
		this.correlationSchemeId = correlationSchemeId;
		this.schemeBonusScale = schemeBonusScale;
		this.isPurchasing = isPurchasing;
		this.fromClient = fromClient;
	}

	
	
	
	// Property accessors

	public Schemes(Integer id, Integer siteId, Date dateTime, String schemeNumber, String title, Integer initiateUserId, Integer isuseId,
			Integer playTypeId, Integer multiple, Double money, Double assureMoney, Integer share, Short secrecyLevel, Short quashStatus,
			Boolean buyed, Integer buyOperatorId, Short printOutType, String identifiers, Boolean isOpened, Integer openOperatorId, Double winMoney,
			Double winMoneyNoWithTax, Double initiateBonus, Short atTopStatus, Boolean isCanChat, Double preWinMoney, Double preWinMoneyNoWithTax,
			Double editWinMoney, Double editWinMoneyNoWithTax, Integer buyedShare, Double schedule, Double reSchedule,
			Boolean isSchemeCalculatedBonus, String description, String lotteryNumber, String uploadFileContent, String winDescription,
			String winImage, Date updateDatetime, Date printOutDateTime, Short ot, Short outTo, Integer correlationSchemeId, Double schemeBonusScale,
			Boolean isPurchasing, Integer fromClient, Integer ycSchemeNumber, GenType changeType, String quashReason, String winNumber,
			Integer openWinState, String lotteryNumberNew, String channel) {
		super();
		this.id = id;
		this.siteId = siteId;
		this.dateTime = dateTime;
		this.schemeNumber = schemeNumber;
		this.title = title;
		this.initiateUserId = initiateUserId;
		this.isuseId = isuseId;
		this.playTypeId = playTypeId;
		this.multiple = multiple;
		this.money = money;
		this.assureMoney = assureMoney;
		this.share = share;
		this.secrecyLevel = secrecyLevel;
		this.quashStatus = quashStatus;
		this.buyed = buyed;
		this.buyOperatorId = buyOperatorId;
		this.printOutType = printOutType;
		this.identifiers = identifiers;
		this.isOpened = isOpened;
		this.openOperatorId = openOperatorId;
		this.winMoney = winMoney;
		this.winMoneyNoWithTax = winMoneyNoWithTax;
		this.initiateBonus = initiateBonus;
		this.atTopStatus = atTopStatus;
		this.isCanChat = isCanChat;
		this.preWinMoney = preWinMoney;
		this.preWinMoneyNoWithTax = preWinMoneyNoWithTax;
		this.editWinMoney = editWinMoney;
		this.editWinMoneyNoWithTax = editWinMoneyNoWithTax;
		this.buyedShare = buyedShare;
		this.schedule = schedule;
		this.reSchedule = reSchedule;
		this.isSchemeCalculatedBonus = isSchemeCalculatedBonus;
		this.description = description;
		this.lotteryNumber = lotteryNumber;
		this.uploadFileContent = uploadFileContent;
		this.winDescription = winDescription;
		this.winImage = winImage;
		this.updateDatetime = updateDatetime;
		this.printOutDateTime = printOutDateTime;
		this.ot = ot;
		this.outTo = outTo;
		this.correlationSchemeId = correlationSchemeId;
		this.schemeBonusScale = schemeBonusScale;
		this.isPurchasing = isPurchasing;
		this.fromClient = fromClient;
		this.ycSchemeNumber = ycSchemeNumber;
		this.changeType = changeType;
		this.quashReason = quashReason;
		this.winNumber = winNumber;
		this.openWinState = openWinState;
		this.lotteryNumberNew = lotteryNumberNew;
		this.channel = channel;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getSchemeNumber() {
		return this.schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getInitiateUserId() {
		return this.initiateUserId;
	}

	public void setInitiateUserId(Integer initiateUserId) {
		this.initiateUserId = initiateUserId;
	}

	public Integer getIsuseId() {
		return this.isuseId;
	}

	public void setIsuseId(Integer isuseId) {
		this.isuseId = isuseId;
	}

	public Integer getPlayTypeId() {
		return this.playTypeId;
	}

	public void setPlayTypeId(Integer playTypeId) {
		this.playTypeId = playTypeId;
	}

	public Integer getMultiple() {
		return this.multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getAssureMoney() {
		return this.assureMoney;
	}

	public void setAssureMoney(Double assureMoney) {
		this.assureMoney = assureMoney;
	}

	public Integer getShare() {
		return this.share;
	}

	public void setShare(Integer share) {
		this.share = share;
	}

	public Short getSecrecyLevel() {
		return this.secrecyLevel;
	}

	public void setSecrecyLevel(Short secrecyLevel) {
		this.secrecyLevel = secrecyLevel;
	}

	public Short getQuashStatus() {
		return this.quashStatus;
	}

	public void setQuashStatus(Short quashStatus) {
		this.quashStatus = quashStatus;
	}

	public Boolean getBuyed() {
		return this.buyed;
	}

	public void setBuyed(Boolean buyed) {
		this.buyed = buyed;
	}

	public Integer getBuyOperatorId() {
		return this.buyOperatorId;
	}

	public void setBuyOperatorId(Integer buyOperatorId) {
		this.buyOperatorId = buyOperatorId;
	}

	public Short getPrintOutType() {
		return this.printOutType;
	}

	public void setPrintOutType(Short printOutType) {
		this.printOutType = printOutType;
	}

	public String getIdentifiers() {
		return this.identifiers;
	}

	public void setIdentifiers(String identifiers) {
		this.identifiers = identifiers;
	}

	public Boolean getIsOpened() {
		return this.isOpened;
	}

	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}

	public Integer getOpenOperatorId() {
		return this.openOperatorId;
	}

	public void setOpenOperatorId(Integer openOperatorId) {
		this.openOperatorId = openOperatorId;
	}

	public Double getWinMoney() {
		return this.winMoney;
	}

	public void setWinMoney(Double winMoney) {
		this.winMoney = winMoney;
	}

	public Double getWinMoneyNoWithTax() {
		return this.winMoneyNoWithTax;
	}

	public void setWinMoneyNoWithTax(Double winMoneyNoWithTax) {
		this.winMoneyNoWithTax = winMoneyNoWithTax;
	}

	public Double getInitiateBonus() {
		return this.initiateBonus;
	}

	public void setInitiateBonus(Double initiateBonus) {
		this.initiateBonus = initiateBonus;
	}

	public Short getAtTopStatus() {
		return this.atTopStatus;
	}

	public void setAtTopStatus(Short atTopStatus) {
		this.atTopStatus = atTopStatus;
	}

	public Boolean getIsCanChat() {
		return this.isCanChat;
	}

	public void setIsCanChat(Boolean isCanChat) {
		this.isCanChat = isCanChat;
	}

	public Double getPreWinMoney() {
		return this.preWinMoney;
	}

	public void setPreWinMoney(Double preWinMoney) {
		this.preWinMoney = preWinMoney;
	}

	public Double getPreWinMoneyNoWithTax() {
		return this.preWinMoneyNoWithTax;
	}

	public void setPreWinMoneyNoWithTax(Double preWinMoneyNoWithTax) {
		this.preWinMoneyNoWithTax = preWinMoneyNoWithTax;
	}

	public Double getEditWinMoney() {
		return this.editWinMoney;
	}

	public void setEditWinMoney(Double editWinMoney) {
		this.editWinMoney = editWinMoney;
	}

	public Double getEditWinMoneyNoWithTax() {
		return this.editWinMoneyNoWithTax;
	}

	public void setEditWinMoneyNoWithTax(Double editWinMoneyNoWithTax) {
		this.editWinMoneyNoWithTax = editWinMoneyNoWithTax;
	}

	public Integer getBuyedShare() {
		return this.buyedShare;
	}

	public void setBuyedShare(Integer buyedShare) {
		this.buyedShare = buyedShare;
	}

	public Double getSchedule() {
		return this.schedule;
	}

	public void setSchedule(Double schedule) {
		this.schedule = schedule;
	}

	public Double getReSchedule() {
		return this.reSchedule;
	}

	public void setReSchedule(Double reSchedule) {
		this.reSchedule = reSchedule;
	}

	public Boolean getIsSchemeCalculatedBonus() {
		return this.isSchemeCalculatedBonus;
	}

	public void setIsSchemeCalculatedBonus(Boolean isSchemeCalculatedBonus) {
		this.isSchemeCalculatedBonus = isSchemeCalculatedBonus;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLotteryNumber() {
		return this.lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getUploadFileContent() {
		return this.uploadFileContent;
	}

	public void setUploadFileContent(String uploadFileContent) {
		this.uploadFileContent = uploadFileContent;
	}

	public String getWinDescription() {
		return this.winDescription;
	}

	public void setWinDescription(String winDescription) {
		this.winDescription = winDescription;
	}

	public String getWinImage() {
		return this.winImage;
	}

	public void setWinImage(String winImage) {
		this.winImage = winImage;
	}

	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public Date getPrintOutDateTime() {
		return this.printOutDateTime;
	}

	public void setPrintOutDateTime(Date printOutDateTime) {
		this.printOutDateTime = printOutDateTime;
	}

	public Short getOt() {
		return this.ot;
	}

	public void setOt(Short ot) {
		this.ot = ot;
	}

	public Short getOutTo() {
		return this.outTo;
	}

	public void setOutTo(Short outTo) {
		this.outTo = outTo;
	}

	public Integer getCorrelationSchemeId() {
		return this.correlationSchemeId;
	}

	public void setCorrelationSchemeId(Integer correlationSchemeId) {
		this.correlationSchemeId = correlationSchemeId;
	}

	public Double getSchemeBonusScale() {
		return this.schemeBonusScale;
	}

	public void setSchemeBonusScale(Double schemeBonusScale) {
		this.schemeBonusScale = schemeBonusScale;
	}

	public Boolean getIsPurchasing() {
		return this.isPurchasing;
	}

	public void setIsPurchasing(Boolean isPurchasing) {
		this.isPurchasing = isPurchasing;
	}

	public Integer getFromClient() {
		return this.fromClient;
	}

	public void setFromClient(Integer fromClient) {
		this.fromClient = fromClient;
	}

	public GenType getChangeType() {
		return changeType;
	}

	public void setChangeType(GenType changeType) {
		this.changeType = changeType;
	}

	public String getQuashReason() {
		return quashReason;
	}

	public void setQuashReason(String quashReason) {
		this.quashReason = quashReason;
	}

	public String getWinNumber() {
		return winNumber;
	}

	public void setWinNumber(String winNumber) {
		this.winNumber = winNumber;
	}

	public Integer getOpenWinState() {
		return openWinState;
	}

	public void setOpenWinState(Integer openWinState) {
		this.openWinState = openWinState;
	}

	public String getLotteryNumberNew() {
		return lotteryNumberNew;
	}

	public void setLotteryNumberNew(String lotteryNumberNew) {
		this.lotteryNumberNew = lotteryNumberNew;
	}

}