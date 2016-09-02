package com.yuncai.modules.lottery.model.Sql;

import java.util.Date;

/**
 * Sites entity. @author MyEclipse Persistence Tools
 */

public class Sites implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer parentId;
	private Integer ownerUserId;
	private String name;
	private String logoUrl;
	private String company;
	private String address;
	private String postCode;
	private String responsiblePerson;
	private String contactPerson;
	private String telephone;
	private String fax;
	private String mobile;
	private String email;
	private String qq;
	private String serviceTelephone;
	private String icpcert;
	private Short level;
	private Boolean on;
	private Double bonusScale;
	private Integer maxSubSites;
	private String useLotteryListRestrictions;
	private String useLotteryList;
	private String useLotteryListQuickBuy;
	private String optBettingStationName;
	private String optBettingStationNumber;
	private String optBettingStationAddress;
	private String optBettingStationTelephone;
	private String optBettingStationContactPreson;
	private String optEmailServerFrom;
	private String optEmailServerEmailServer;
	private String optEmailServerUserName;
	private String optEmailServerPassword;
	private String optIspHostName;
	private String optIspHostPort;
	private String optIspUserId;
	private String optIspUserPassword;
	private String optIspRegCode;
	private String optIspServiceNumber;
	private String optForumUrl;
	private Short optMobileCheckCharset;
	private Short optMobileCheckStringLength;
	private Short optSmspayType;
	private Double optSmsprice;
	private Boolean optIsUseCheckCode;
	private Short optCheckCodeCharset;
	private Boolean optIsWriteLog;
	private Double optInitiateSchemeBonusScale;
	private Double optInitiateSchemeMinBuyScale;
	private Double optInitiateSchemeMinBuyAndAssureScale;
	private Short optInitiateSchemeMaxNum;
	private Short optInitiateFollowSchemeMaxNum;
	private Short optQuashSchemeMaxNum;
	private Boolean optFullSchemeCanQuash;
	private Double optSchemeMinMoney;
	private Double optSchemeMaxMoney;
	private Short optFirstPageUnionBuyMaxRows;
	private Boolean optIsFirstPageUnionBuyWithAll;
	private Boolean optIsBuyValidPasswordAdv;
	private Short optMaxShowLotteryNumberRows;
	private Short optLotteryCountOfMenuBarRow;
	private Double optScoringOfSelfBuy;
	private Double optScoringOfCommendBuy;
	private Double optScoringExchangeRate;
	private Boolean optScoringStatusOn;
	private Short optSchemeChatRoomStopChatDaysAfterOpened;
	private Short optSchemeChatRoomMaxChatNumberOf;
	private Boolean optIsShowFloatAd;
	private Boolean optMemberSharingAlipayStatusOn;
	private Double optCpsBonusScale;
	private Boolean optCpsStatusOn;
	private Boolean optExpertsStatusOn;
	private String optPageTitle;
	private String optPageKeywords;
	private Short optDefaultFirstPageType;
	private Short optDefaultLotteryFirstPageType;
	private String optLotteryChannelPage;
	private Boolean optIsShowSmssubscriptionNavigate;
	private Boolean optIsShowChartNavigate;
	private Short optRoomStyle;
	private String optRoomLogoUrl;
	private Date optUpdateLotteryDateTime;
	private Double optInitiateSchemeLimitLowerScaleMoney;
	private Double optInitiateSchemeLimitLowerScale;
	private Double optInitiateSchemeLimitUpperScaleMoney;
	private Double optInitiateSchemeLimitUpperScale;
	private String optAbout;
	private String optRightFloatAdcontent;
	private String optContactUs;
	private String optUserRegisterAgreement;
	private String optSurrogateFaq;
	private String optOfficialAuthorization;
	private String optCompanyQualification;
	private String optExpertsNote;
	private String optSmssubscription;
	private String optLawAffirmsThat;
	private String optCpsPolicies;
	private String templateEmailRegister;
	private String templateEmailRegisterAdv;
	private String templateEmailForgetPassword;
	private String templateEmailUserEdit;
	private String templateEmailUserEditAdv;
	private String templateEmailInitiateScheme;
	private String templateEmailJoinScheme;
	private String templateEmailInitiateChaseTask;
	private String templateEmailExecChaseTaskDetail;
	private String templateEmailTryDistill;
	private String templateEmailDistillAccept;
	private String templateEmailDistillNoAccept;
	private String templateEmailQuash;
	private String templateEmailQuashScheme;
	private String templateEmailQuashChaseTaskDetail;
	private String templateEmailQuashChaseTask;
	private String templateEmailWin;
	private String templateEmailMobileValid;
	private String templateEmailMobileValided;
	private String templateStationSmsRegister;
	private String templateStationSmsRegisterAdv;
	private String templateStationSmsForgetPassword;
	private String templateStationSmsUserEdit;
	private String templateStationSmsUserEditAdv;
	private String templateStationSmsInitiateScheme;
	private String templateStationSmsJoinScheme;
	private String templateStationSmsInitiateChaseTask;
	private String templateStationSmsExecChaseTaskDetail;
	private String templateStationSmsTryDistill;
	private String templateStationSmsDistillAccept;
	private String templateStationSmsDistillNoAccept;
	private String templateStationSmsQuash;
	private String templateStationSmsQuashScheme;
	private String templateStationSmsQuashChaseTaskDetail;
	private String templateStationSmsQuashChaseTask;
	private String templateStationSmsWin;
	private String templateStationSmsMobileValid;
	private String templateStationSmsMobileValided;
	private String templateSmsRegister;
	private String templateSmsRegisterAdv;
	private String templateSmsForgetPassword;
	private String templateSmsUserEdit;
	private String templateSmsUserEditAdv;
	private String templateSmsInitiateScheme;
	private String templateSmsJoinScheme;
	private String templateSmsInitiateChaseTask;
	private String templateSmsExecChaseTaskDetail;
	private String templateSmsTryDistill;
	private String templateSmsDistillAccept;
	private String templateSmsDistillNoAccept;
	private String templateSmsQuash;
	private String templateSmsQuashScheme;
	private String templateSmsQuashChaseTaskDetail;
	private String templateSmsQuashChaseTask;
	private String templateSmsWin;
	private String templateSmsMobileValid;
	private String templateSmsMobileValided;
	private String optCpsregisterAgreement;
	private Double optPromotionMemberBonusScale;
	private Double optPromotionSiteBonusScale;
	private Boolean optPromotionStatusOn;
	private Short optFloatNotifiesTime;
	private Double optScoreCompendium;
	private Short optScorePrententType;
	private String templateEmailIntiateCustomChase;
	private String templateStationSmsIntiateCustomChase;
	private String templateSmsIntiateCustomChase;
	private String templateEmailCustomChaseWin;
	private String templateStationSmsCustomChaseWin;
	private String templateSmsCustomChaseWin;
	private Double theTopScheduleOfInitiateCanQuashScheme;  //撤销合买设计节点
	private Double scheduleOfAllCanNotQuashScheme;
	private String templateSmsSystemLoginUser;
	private String templateStationSmsSystemLoginUser;
	private String templateEmailSystemLoginUser;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(Integer ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getResponsiblePerson() {
		return responsiblePerson;
	}
	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getServiceTelephone() {
		return serviceTelephone;
	}
	public void setServiceTelephone(String serviceTelephone) {
		this.serviceTelephone = serviceTelephone;
	}
	public String getIcpcert() {
		return icpcert;
	}
	public void setIcpcert(String icpcert) {
		this.icpcert = icpcert;
	}
	public Short getLevel() {
		return level;
	}
	public void setLevel(Short level) {
		this.level = level;
	}
	public Boolean getOn() {
		return on;
	}
	public void setOn(Boolean on) {
		this.on = on;
	}
	public Double getBonusScale() {
		return bonusScale;
	}
	public void setBonusScale(Double bonusScale) {
		this.bonusScale = bonusScale;
	}
	public Integer getMaxSubSites() {
		return maxSubSites;
	}
	public void setMaxSubSites(Integer maxSubSites) {
		this.maxSubSites = maxSubSites;
	}
	public String getUseLotteryListRestrictions() {
		return useLotteryListRestrictions;
	}
	public void setUseLotteryListRestrictions(String useLotteryListRestrictions) {
		this.useLotteryListRestrictions = useLotteryListRestrictions;
	}
	public String getUseLotteryList() {
		return useLotteryList;
	}
	public void setUseLotteryList(String useLotteryList) {
		this.useLotteryList = useLotteryList;
	}
	public String getUseLotteryListQuickBuy() {
		return useLotteryListQuickBuy;
	}
	public void setUseLotteryListQuickBuy(String useLotteryListQuickBuy) {
		this.useLotteryListQuickBuy = useLotteryListQuickBuy;
	}
	public String getOptBettingStationName() {
		return optBettingStationName;
	}
	public void setOptBettingStationName(String optBettingStationName) {
		this.optBettingStationName = optBettingStationName;
	}
	public String getOptBettingStationNumber() {
		return optBettingStationNumber;
	}
	public void setOptBettingStationNumber(String optBettingStationNumber) {
		this.optBettingStationNumber = optBettingStationNumber;
	}
	public String getOptBettingStationAddress() {
		return optBettingStationAddress;
	}
	public void setOptBettingStationAddress(String optBettingStationAddress) {
		this.optBettingStationAddress = optBettingStationAddress;
	}
	public String getOptBettingStationTelephone() {
		return optBettingStationTelephone;
	}
	public void setOptBettingStationTelephone(String optBettingStationTelephone) {
		this.optBettingStationTelephone = optBettingStationTelephone;
	}
	public String getOptBettingStationContactPreson() {
		return optBettingStationContactPreson;
	}
	public void setOptBettingStationContactPreson(String optBettingStationContactPreson) {
		this.optBettingStationContactPreson = optBettingStationContactPreson;
	}
	public String getOptEmailServerFrom() {
		return optEmailServerFrom;
	}
	public void setOptEmailServerFrom(String optEmailServerFrom) {
		this.optEmailServerFrom = optEmailServerFrom;
	}
	public String getOptEmailServerEmailServer() {
		return optEmailServerEmailServer;
	}
	public void setOptEmailServerEmailServer(String optEmailServerEmailServer) {
		this.optEmailServerEmailServer = optEmailServerEmailServer;
	}
	public String getOptEmailServerUserName() {
		return optEmailServerUserName;
	}
	public void setOptEmailServerUserName(String optEmailServerUserName) {
		this.optEmailServerUserName = optEmailServerUserName;
	}
	public String getOptEmailServerPassword() {
		return optEmailServerPassword;
	}
	public void setOptEmailServerPassword(String optEmailServerPassword) {
		this.optEmailServerPassword = optEmailServerPassword;
	}
	public String getOptIspHostName() {
		return optIspHostName;
	}
	public void setOptIspHostName(String optIspHostName) {
		this.optIspHostName = optIspHostName;
	}
	public String getOptIspHostPort() {
		return optIspHostPort;
	}
	public void setOptIspHostPort(String optIspHostPort) {
		this.optIspHostPort = optIspHostPort;
	}
	public String getOptIspUserId() {
		return optIspUserId;
	}
	public void setOptIspUserId(String optIspUserId) {
		this.optIspUserId = optIspUserId;
	}
	public String getOptIspUserPassword() {
		return optIspUserPassword;
	}
	public void setOptIspUserPassword(String optIspUserPassword) {
		this.optIspUserPassword = optIspUserPassword;
	}
	public String getOptIspRegCode() {
		return optIspRegCode;
	}
	public void setOptIspRegCode(String optIspRegCode) {
		this.optIspRegCode = optIspRegCode;
	}
	public String getOptIspServiceNumber() {
		return optIspServiceNumber;
	}
	public void setOptIspServiceNumber(String optIspServiceNumber) {
		this.optIspServiceNumber = optIspServiceNumber;
	}
	public String getOptForumUrl() {
		return optForumUrl;
	}
	public void setOptForumUrl(String optForumUrl) {
		this.optForumUrl = optForumUrl;
	}
	public Short getOptMobileCheckCharset() {
		return optMobileCheckCharset;
	}
	public void setOptMobileCheckCharset(Short optMobileCheckCharset) {
		this.optMobileCheckCharset = optMobileCheckCharset;
	}
	public Short getOptMobileCheckStringLength() {
		return optMobileCheckStringLength;
	}
	public void setOptMobileCheckStringLength(Short optMobileCheckStringLength) {
		this.optMobileCheckStringLength = optMobileCheckStringLength;
	}
	public Short getOptSmspayType() {
		return optSmspayType;
	}
	public void setOptSmspayType(Short optSmspayType) {
		this.optSmspayType = optSmspayType;
	}
	public Double getOptSmsprice() {
		return optSmsprice;
	}
	public void setOptSmsprice(Double optSmsprice) {
		this.optSmsprice = optSmsprice;
	}
	public Boolean getOptIsUseCheckCode() {
		return optIsUseCheckCode;
	}
	public void setOptIsUseCheckCode(Boolean optIsUseCheckCode) {
		this.optIsUseCheckCode = optIsUseCheckCode;
	}
	public Short getOptCheckCodeCharset() {
		return optCheckCodeCharset;
	}
	public void setOptCheckCodeCharset(Short optCheckCodeCharset) {
		this.optCheckCodeCharset = optCheckCodeCharset;
	}
	public Boolean getOptIsWriteLog() {
		return optIsWriteLog;
	}
	public void setOptIsWriteLog(Boolean optIsWriteLog) {
		this.optIsWriteLog = optIsWriteLog;
	}
	public Double getOptInitiateSchemeBonusScale() {
		return optInitiateSchemeBonusScale;
	}
	public void setOptInitiateSchemeBonusScale(Double optInitiateSchemeBonusScale) {
		this.optInitiateSchemeBonusScale = optInitiateSchemeBonusScale;
	}
	public Double getOptInitiateSchemeMinBuyScale() {
		return optInitiateSchemeMinBuyScale;
	}
	public void setOptInitiateSchemeMinBuyScale(Double optInitiateSchemeMinBuyScale) {
		this.optInitiateSchemeMinBuyScale = optInitiateSchemeMinBuyScale;
	}
	public Double getOptInitiateSchemeMinBuyAndAssureScale() {
		return optInitiateSchemeMinBuyAndAssureScale;
	}
	public void setOptInitiateSchemeMinBuyAndAssureScale(Double optInitiateSchemeMinBuyAndAssureScale) {
		this.optInitiateSchemeMinBuyAndAssureScale = optInitiateSchemeMinBuyAndAssureScale;
	}
	public Short getOptInitiateSchemeMaxNum() {
		return optInitiateSchemeMaxNum;
	}
	public void setOptInitiateSchemeMaxNum(Short optInitiateSchemeMaxNum) {
		this.optInitiateSchemeMaxNum = optInitiateSchemeMaxNum;
	}
	public Short getOptInitiateFollowSchemeMaxNum() {
		return optInitiateFollowSchemeMaxNum;
	}
	public void setOptInitiateFollowSchemeMaxNum(Short optInitiateFollowSchemeMaxNum) {
		this.optInitiateFollowSchemeMaxNum = optInitiateFollowSchemeMaxNum;
	}
	public Short getOptQuashSchemeMaxNum() {
		return optQuashSchemeMaxNum;
	}
	public void setOptQuashSchemeMaxNum(Short optQuashSchemeMaxNum) {
		this.optQuashSchemeMaxNum = optQuashSchemeMaxNum;
	}
	public Boolean getOptFullSchemeCanQuash() {
		return optFullSchemeCanQuash;
	}
	public void setOptFullSchemeCanQuash(Boolean optFullSchemeCanQuash) {
		this.optFullSchemeCanQuash = optFullSchemeCanQuash;
	}
	public Double getOptSchemeMinMoney() {
		return optSchemeMinMoney;
	}
	public void setOptSchemeMinMoney(Double optSchemeMinMoney) {
		this.optSchemeMinMoney = optSchemeMinMoney;
	}
	public Double getOptSchemeMaxMoney() {
		return optSchemeMaxMoney;
	}
	public void setOptSchemeMaxMoney(Double optSchemeMaxMoney) {
		this.optSchemeMaxMoney = optSchemeMaxMoney;
	}
	public Short getOptFirstPageUnionBuyMaxRows() {
		return optFirstPageUnionBuyMaxRows;
	}
	public void setOptFirstPageUnionBuyMaxRows(Short optFirstPageUnionBuyMaxRows) {
		this.optFirstPageUnionBuyMaxRows = optFirstPageUnionBuyMaxRows;
	}
	public Boolean getOptIsFirstPageUnionBuyWithAll() {
		return optIsFirstPageUnionBuyWithAll;
	}
	public void setOptIsFirstPageUnionBuyWithAll(Boolean optIsFirstPageUnionBuyWithAll) {
		this.optIsFirstPageUnionBuyWithAll = optIsFirstPageUnionBuyWithAll;
	}
	public Boolean getOptIsBuyValidPasswordAdv() {
		return optIsBuyValidPasswordAdv;
	}
	public void setOptIsBuyValidPasswordAdv(Boolean optIsBuyValidPasswordAdv) {
		this.optIsBuyValidPasswordAdv = optIsBuyValidPasswordAdv;
	}
	public Short getOptMaxShowLotteryNumberRows() {
		return optMaxShowLotteryNumberRows;
	}
	public void setOptMaxShowLotteryNumberRows(Short optMaxShowLotteryNumberRows) {
		this.optMaxShowLotteryNumberRows = optMaxShowLotteryNumberRows;
	}
	public Short getOptLotteryCountOfMenuBarRow() {
		return optLotteryCountOfMenuBarRow;
	}
	public void setOptLotteryCountOfMenuBarRow(Short optLotteryCountOfMenuBarRow) {
		this.optLotteryCountOfMenuBarRow = optLotteryCountOfMenuBarRow;
	}
	public Double getOptScoringOfSelfBuy() {
		return optScoringOfSelfBuy;
	}
	public void setOptScoringOfSelfBuy(Double optScoringOfSelfBuy) {
		this.optScoringOfSelfBuy = optScoringOfSelfBuy;
	}
	public Double getOptScoringOfCommendBuy() {
		return optScoringOfCommendBuy;
	}
	public void setOptScoringOfCommendBuy(Double optScoringOfCommendBuy) {
		this.optScoringOfCommendBuy = optScoringOfCommendBuy;
	}
	public Double getOptScoringExchangeRate() {
		return optScoringExchangeRate;
	}
	public void setOptScoringExchangeRate(Double optScoringExchangeRate) {
		this.optScoringExchangeRate = optScoringExchangeRate;
	}
	public Boolean getOptScoringStatusOn() {
		return optScoringStatusOn;
	}
	public void setOptScoringStatusOn(Boolean optScoringStatusOn) {
		this.optScoringStatusOn = optScoringStatusOn;
	}
	public Short getOptSchemeChatRoomStopChatDaysAfterOpened() {
		return optSchemeChatRoomStopChatDaysAfterOpened;
	}
	public void setOptSchemeChatRoomStopChatDaysAfterOpened(Short optSchemeChatRoomStopChatDaysAfterOpened) {
		this.optSchemeChatRoomStopChatDaysAfterOpened = optSchemeChatRoomStopChatDaysAfterOpened;
	}
	public Short getOptSchemeChatRoomMaxChatNumberOf() {
		return optSchemeChatRoomMaxChatNumberOf;
	}
	public void setOptSchemeChatRoomMaxChatNumberOf(Short optSchemeChatRoomMaxChatNumberOf) {
		this.optSchemeChatRoomMaxChatNumberOf = optSchemeChatRoomMaxChatNumberOf;
	}
	public Boolean getOptIsShowFloatAd() {
		return optIsShowFloatAd;
	}
	public void setOptIsShowFloatAd(Boolean optIsShowFloatAd) {
		this.optIsShowFloatAd = optIsShowFloatAd;
	}
	public Boolean getOptMemberSharingAlipayStatusOn() {
		return optMemberSharingAlipayStatusOn;
	}
	public void setOptMemberSharingAlipayStatusOn(Boolean optMemberSharingAlipayStatusOn) {
		this.optMemberSharingAlipayStatusOn = optMemberSharingAlipayStatusOn;
	}
	public Double getOptCpsBonusScale() {
		return optCpsBonusScale;
	}
	public void setOptCpsBonusScale(Double optCpsBonusScale) {
		this.optCpsBonusScale = optCpsBonusScale;
	}
	public Boolean getOptCpsStatusOn() {
		return optCpsStatusOn;
	}
	public void setOptCpsStatusOn(Boolean optCpsStatusOn) {
		this.optCpsStatusOn = optCpsStatusOn;
	}
	public Boolean getOptExpertsStatusOn() {
		return optExpertsStatusOn;
	}
	public void setOptExpertsStatusOn(Boolean optExpertsStatusOn) {
		this.optExpertsStatusOn = optExpertsStatusOn;
	}
	public String getOptPageTitle() {
		return optPageTitle;
	}
	public void setOptPageTitle(String optPageTitle) {
		this.optPageTitle = optPageTitle;
	}
	public String getOptPageKeywords() {
		return optPageKeywords;
	}
	public void setOptPageKeywords(String optPageKeywords) {
		this.optPageKeywords = optPageKeywords;
	}
	public Short getOptDefaultFirstPageType() {
		return optDefaultFirstPageType;
	}
	public void setOptDefaultFirstPageType(Short optDefaultFirstPageType) {
		this.optDefaultFirstPageType = optDefaultFirstPageType;
	}
	public Short getOptDefaultLotteryFirstPageType() {
		return optDefaultLotteryFirstPageType;
	}
	public void setOptDefaultLotteryFirstPageType(Short optDefaultLotteryFirstPageType) {
		this.optDefaultLotteryFirstPageType = optDefaultLotteryFirstPageType;
	}
	public String getOptLotteryChannelPage() {
		return optLotteryChannelPage;
	}
	public void setOptLotteryChannelPage(String optLotteryChannelPage) {
		this.optLotteryChannelPage = optLotteryChannelPage;
	}
	public Boolean getOptIsShowSmssubscriptionNavigate() {
		return optIsShowSmssubscriptionNavigate;
	}
	public void setOptIsShowSmssubscriptionNavigate(Boolean optIsShowSmssubscriptionNavigate) {
		this.optIsShowSmssubscriptionNavigate = optIsShowSmssubscriptionNavigate;
	}
	public Boolean getOptIsShowChartNavigate() {
		return optIsShowChartNavigate;
	}
	public void setOptIsShowChartNavigate(Boolean optIsShowChartNavigate) {
		this.optIsShowChartNavigate = optIsShowChartNavigate;
	}
	public Short getOptRoomStyle() {
		return optRoomStyle;
	}
	public void setOptRoomStyle(Short optRoomStyle) {
		this.optRoomStyle = optRoomStyle;
	}
	public String getOptRoomLogoUrl() {
		return optRoomLogoUrl;
	}
	public void setOptRoomLogoUrl(String optRoomLogoUrl) {
		this.optRoomLogoUrl = optRoomLogoUrl;
	}
	public Date getOptUpdateLotteryDateTime() {
		return optUpdateLotteryDateTime;
	}
	public void setOptUpdateLotteryDateTime(Date optUpdateLotteryDateTime) {
		this.optUpdateLotteryDateTime = optUpdateLotteryDateTime;
	}
	public Double getOptInitiateSchemeLimitLowerScaleMoney() {
		return optInitiateSchemeLimitLowerScaleMoney;
	}
	public void setOptInitiateSchemeLimitLowerScaleMoney(Double optInitiateSchemeLimitLowerScaleMoney) {
		this.optInitiateSchemeLimitLowerScaleMoney = optInitiateSchemeLimitLowerScaleMoney;
	}
	public Double getOptInitiateSchemeLimitLowerScale() {
		return optInitiateSchemeLimitLowerScale;
	}
	public void setOptInitiateSchemeLimitLowerScale(Double optInitiateSchemeLimitLowerScale) {
		this.optInitiateSchemeLimitLowerScale = optInitiateSchemeLimitLowerScale;
	}
	public Double getOptInitiateSchemeLimitUpperScaleMoney() {
		return optInitiateSchemeLimitUpperScaleMoney;
	}
	public void setOptInitiateSchemeLimitUpperScaleMoney(Double optInitiateSchemeLimitUpperScaleMoney) {
		this.optInitiateSchemeLimitUpperScaleMoney = optInitiateSchemeLimitUpperScaleMoney;
	}
	public Double getOptInitiateSchemeLimitUpperScale() {
		return optInitiateSchemeLimitUpperScale;
	}
	public void setOptInitiateSchemeLimitUpperScale(Double optInitiateSchemeLimitUpperScale) {
		this.optInitiateSchemeLimitUpperScale = optInitiateSchemeLimitUpperScale;
	}
	public String getOptAbout() {
		return optAbout;
	}
	public void setOptAbout(String optAbout) {
		this.optAbout = optAbout;
	}
	public String getOptRightFloatAdcontent() {
		return optRightFloatAdcontent;
	}
	public void setOptRightFloatAdcontent(String optRightFloatAdcontent) {
		this.optRightFloatAdcontent = optRightFloatAdcontent;
	}
	public String getOptContactUs() {
		return optContactUs;
	}
	public void setOptContactUs(String optContactUs) {
		this.optContactUs = optContactUs;
	}
	public String getOptUserRegisterAgreement() {
		return optUserRegisterAgreement;
	}
	public void setOptUserRegisterAgreement(String optUserRegisterAgreement) {
		this.optUserRegisterAgreement = optUserRegisterAgreement;
	}
	public String getOptSurrogateFaq() {
		return optSurrogateFaq;
	}
	public void setOptSurrogateFaq(String optSurrogateFaq) {
		this.optSurrogateFaq = optSurrogateFaq;
	}
	public String getOptOfficialAuthorization() {
		return optOfficialAuthorization;
	}
	public void setOptOfficialAuthorization(String optOfficialAuthorization) {
		this.optOfficialAuthorization = optOfficialAuthorization;
	}
	public String getOptCompanyQualification() {
		return optCompanyQualification;
	}
	public void setOptCompanyQualification(String optCompanyQualification) {
		this.optCompanyQualification = optCompanyQualification;
	}
	public String getOptExpertsNote() {
		return optExpertsNote;
	}
	public void setOptExpertsNote(String optExpertsNote) {
		this.optExpertsNote = optExpertsNote;
	}
	public String getOptSmssubscription() {
		return optSmssubscription;
	}
	public void setOptSmssubscription(String optSmssubscription) {
		this.optSmssubscription = optSmssubscription;
	}
	public String getOptLawAffirmsThat() {
		return optLawAffirmsThat;
	}
	public void setOptLawAffirmsThat(String optLawAffirmsThat) {
		this.optLawAffirmsThat = optLawAffirmsThat;
	}
	public String getOptCpsPolicies() {
		return optCpsPolicies;
	}
	public void setOptCpsPolicies(String optCpsPolicies) {
		this.optCpsPolicies = optCpsPolicies;
	}
	public String getTemplateEmailRegister() {
		return templateEmailRegister;
	}
	public void setTemplateEmailRegister(String templateEmailRegister) {
		this.templateEmailRegister = templateEmailRegister;
	}
	public String getTemplateEmailRegisterAdv() {
		return templateEmailRegisterAdv;
	}
	public void setTemplateEmailRegisterAdv(String templateEmailRegisterAdv) {
		this.templateEmailRegisterAdv = templateEmailRegisterAdv;
	}
	public String getTemplateEmailForgetPassword() {
		return templateEmailForgetPassword;
	}
	public void setTemplateEmailForgetPassword(String templateEmailForgetPassword) {
		this.templateEmailForgetPassword = templateEmailForgetPassword;
	}
	public String getTemplateEmailUserEdit() {
		return templateEmailUserEdit;
	}
	public void setTemplateEmailUserEdit(String templateEmailUserEdit) {
		this.templateEmailUserEdit = templateEmailUserEdit;
	}
	public String getTemplateEmailUserEditAdv() {
		return templateEmailUserEditAdv;
	}
	public void setTemplateEmailUserEditAdv(String templateEmailUserEditAdv) {
		this.templateEmailUserEditAdv = templateEmailUserEditAdv;
	}
	public String getTemplateEmailInitiateScheme() {
		return templateEmailInitiateScheme;
	}
	public void setTemplateEmailInitiateScheme(String templateEmailInitiateScheme) {
		this.templateEmailInitiateScheme = templateEmailInitiateScheme;
	}
	public String getTemplateEmailJoinScheme() {
		return templateEmailJoinScheme;
	}
	public void setTemplateEmailJoinScheme(String templateEmailJoinScheme) {
		this.templateEmailJoinScheme = templateEmailJoinScheme;
	}
	public String getTemplateEmailInitiateChaseTask() {
		return templateEmailInitiateChaseTask;
	}
	public void setTemplateEmailInitiateChaseTask(String templateEmailInitiateChaseTask) {
		this.templateEmailInitiateChaseTask = templateEmailInitiateChaseTask;
	}
	public String getTemplateEmailExecChaseTaskDetail() {
		return templateEmailExecChaseTaskDetail;
	}
	public void setTemplateEmailExecChaseTaskDetail(String templateEmailExecChaseTaskDetail) {
		this.templateEmailExecChaseTaskDetail = templateEmailExecChaseTaskDetail;
	}
	public String getTemplateEmailTryDistill() {
		return templateEmailTryDistill;
	}
	public void setTemplateEmailTryDistill(String templateEmailTryDistill) {
		this.templateEmailTryDistill = templateEmailTryDistill;
	}
	public String getTemplateEmailDistillAccept() {
		return templateEmailDistillAccept;
	}
	public void setTemplateEmailDistillAccept(String templateEmailDistillAccept) {
		this.templateEmailDistillAccept = templateEmailDistillAccept;
	}
	public String getTemplateEmailDistillNoAccept() {
		return templateEmailDistillNoAccept;
	}
	public void setTemplateEmailDistillNoAccept(String templateEmailDistillNoAccept) {
		this.templateEmailDistillNoAccept = templateEmailDistillNoAccept;
	}
	public String getTemplateEmailQuash() {
		return templateEmailQuash;
	}
	public void setTemplateEmailQuash(String templateEmailQuash) {
		this.templateEmailQuash = templateEmailQuash;
	}
	public String getTemplateEmailQuashScheme() {
		return templateEmailQuashScheme;
	}
	public void setTemplateEmailQuashScheme(String templateEmailQuashScheme) {
		this.templateEmailQuashScheme = templateEmailQuashScheme;
	}
	public String getTemplateEmailQuashChaseTaskDetail() {
		return templateEmailQuashChaseTaskDetail;
	}
	public void setTemplateEmailQuashChaseTaskDetail(String templateEmailQuashChaseTaskDetail) {
		this.templateEmailQuashChaseTaskDetail = templateEmailQuashChaseTaskDetail;
	}
	public String getTemplateEmailQuashChaseTask() {
		return templateEmailQuashChaseTask;
	}
	public void setTemplateEmailQuashChaseTask(String templateEmailQuashChaseTask) {
		this.templateEmailQuashChaseTask = templateEmailQuashChaseTask;
	}
	public String getTemplateEmailWin() {
		return templateEmailWin;
	}
	public void setTemplateEmailWin(String templateEmailWin) {
		this.templateEmailWin = templateEmailWin;
	}
	public String getTemplateEmailMobileValid() {
		return templateEmailMobileValid;
	}
	public void setTemplateEmailMobileValid(String templateEmailMobileValid) {
		this.templateEmailMobileValid = templateEmailMobileValid;
	}
	public String getTemplateEmailMobileValided() {
		return templateEmailMobileValided;
	}
	public void setTemplateEmailMobileValided(String templateEmailMobileValided) {
		this.templateEmailMobileValided = templateEmailMobileValided;
	}
	public String getTemplateStationSmsRegister() {
		return templateStationSmsRegister;
	}
	public void setTemplateStationSmsRegister(String templateStationSmsRegister) {
		this.templateStationSmsRegister = templateStationSmsRegister;
	}
	public String getTemplateStationSmsRegisterAdv() {
		return templateStationSmsRegisterAdv;
	}
	public void setTemplateStationSmsRegisterAdv(String templateStationSmsRegisterAdv) {
		this.templateStationSmsRegisterAdv = templateStationSmsRegisterAdv;
	}
	public String getTemplateStationSmsForgetPassword() {
		return templateStationSmsForgetPassword;
	}
	public void setTemplateStationSmsForgetPassword(String templateStationSmsForgetPassword) {
		this.templateStationSmsForgetPassword = templateStationSmsForgetPassword;
	}
	public String getTemplateStationSmsUserEdit() {
		return templateStationSmsUserEdit;
	}
	public void setTemplateStationSmsUserEdit(String templateStationSmsUserEdit) {
		this.templateStationSmsUserEdit = templateStationSmsUserEdit;
	}
	public String getTemplateStationSmsUserEditAdv() {
		return templateStationSmsUserEditAdv;
	}
	public void setTemplateStationSmsUserEditAdv(String templateStationSmsUserEditAdv) {
		this.templateStationSmsUserEditAdv = templateStationSmsUserEditAdv;
	}
	public String getTemplateStationSmsInitiateScheme() {
		return templateStationSmsInitiateScheme;
	}
	public void setTemplateStationSmsInitiateScheme(String templateStationSmsInitiateScheme) {
		this.templateStationSmsInitiateScheme = templateStationSmsInitiateScheme;
	}
	public String getTemplateStationSmsJoinScheme() {
		return templateStationSmsJoinScheme;
	}
	public void setTemplateStationSmsJoinScheme(String templateStationSmsJoinScheme) {
		this.templateStationSmsJoinScheme = templateStationSmsJoinScheme;
	}
	public String getTemplateStationSmsInitiateChaseTask() {
		return templateStationSmsInitiateChaseTask;
	}
	public void setTemplateStationSmsInitiateChaseTask(String templateStationSmsInitiateChaseTask) {
		this.templateStationSmsInitiateChaseTask = templateStationSmsInitiateChaseTask;
	}
	public String getTemplateStationSmsExecChaseTaskDetail() {
		return templateStationSmsExecChaseTaskDetail;
	}
	public void setTemplateStationSmsExecChaseTaskDetail(String templateStationSmsExecChaseTaskDetail) {
		this.templateStationSmsExecChaseTaskDetail = templateStationSmsExecChaseTaskDetail;
	}
	public String getTemplateStationSmsTryDistill() {
		return templateStationSmsTryDistill;
	}
	public void setTemplateStationSmsTryDistill(String templateStationSmsTryDistill) {
		this.templateStationSmsTryDistill = templateStationSmsTryDistill;
	}
	public String getTemplateStationSmsDistillAccept() {
		return templateStationSmsDistillAccept;
	}
	public void setTemplateStationSmsDistillAccept(String templateStationSmsDistillAccept) {
		this.templateStationSmsDistillAccept = templateStationSmsDistillAccept;
	}
	public String getTemplateStationSmsDistillNoAccept() {
		return templateStationSmsDistillNoAccept;
	}
	public void setTemplateStationSmsDistillNoAccept(String templateStationSmsDistillNoAccept) {
		this.templateStationSmsDistillNoAccept = templateStationSmsDistillNoAccept;
	}
	public String getTemplateStationSmsQuash() {
		return templateStationSmsQuash;
	}
	public void setTemplateStationSmsQuash(String templateStationSmsQuash) {
		this.templateStationSmsQuash = templateStationSmsQuash;
	}
	public String getTemplateStationSmsQuashScheme() {
		return templateStationSmsQuashScheme;
	}
	public void setTemplateStationSmsQuashScheme(String templateStationSmsQuashScheme) {
		this.templateStationSmsQuashScheme = templateStationSmsQuashScheme;
	}
	public String getTemplateStationSmsQuashChaseTaskDetail() {
		return templateStationSmsQuashChaseTaskDetail;
	}
	public void setTemplateStationSmsQuashChaseTaskDetail(String templateStationSmsQuashChaseTaskDetail) {
		this.templateStationSmsQuashChaseTaskDetail = templateStationSmsQuashChaseTaskDetail;
	}
	public String getTemplateStationSmsQuashChaseTask() {
		return templateStationSmsQuashChaseTask;
	}
	public void setTemplateStationSmsQuashChaseTask(String templateStationSmsQuashChaseTask) {
		this.templateStationSmsQuashChaseTask = templateStationSmsQuashChaseTask;
	}
	public String getTemplateStationSmsWin() {
		return templateStationSmsWin;
	}
	public void setTemplateStationSmsWin(String templateStationSmsWin) {
		this.templateStationSmsWin = templateStationSmsWin;
	}
	public String getTemplateStationSmsMobileValid() {
		return templateStationSmsMobileValid;
	}
	public void setTemplateStationSmsMobileValid(String templateStationSmsMobileValid) {
		this.templateStationSmsMobileValid = templateStationSmsMobileValid;
	}
	public String getTemplateStationSmsMobileValided() {
		return templateStationSmsMobileValided;
	}
	public void setTemplateStationSmsMobileValided(String templateStationSmsMobileValided) {
		this.templateStationSmsMobileValided = templateStationSmsMobileValided;
	}
	public String getTemplateSmsRegister() {
		return templateSmsRegister;
	}
	public void setTemplateSmsRegister(String templateSmsRegister) {
		this.templateSmsRegister = templateSmsRegister;
	}
	public String getTemplateSmsRegisterAdv() {
		return templateSmsRegisterAdv;
	}
	public void setTemplateSmsRegisterAdv(String templateSmsRegisterAdv) {
		this.templateSmsRegisterAdv = templateSmsRegisterAdv;
	}
	public String getTemplateSmsForgetPassword() {
		return templateSmsForgetPassword;
	}
	public void setTemplateSmsForgetPassword(String templateSmsForgetPassword) {
		this.templateSmsForgetPassword = templateSmsForgetPassword;
	}
	public String getTemplateSmsUserEdit() {
		return templateSmsUserEdit;
	}
	public void setTemplateSmsUserEdit(String templateSmsUserEdit) {
		this.templateSmsUserEdit = templateSmsUserEdit;
	}
	public String getTemplateSmsUserEditAdv() {
		return templateSmsUserEditAdv;
	}
	public void setTemplateSmsUserEditAdv(String templateSmsUserEditAdv) {
		this.templateSmsUserEditAdv = templateSmsUserEditAdv;
	}
	public String getTemplateSmsInitiateScheme() {
		return templateSmsInitiateScheme;
	}
	public void setTemplateSmsInitiateScheme(String templateSmsInitiateScheme) {
		this.templateSmsInitiateScheme = templateSmsInitiateScheme;
	}
	public String getTemplateSmsJoinScheme() {
		return templateSmsJoinScheme;
	}
	public void setTemplateSmsJoinScheme(String templateSmsJoinScheme) {
		this.templateSmsJoinScheme = templateSmsJoinScheme;
	}
	public String getTemplateSmsInitiateChaseTask() {
		return templateSmsInitiateChaseTask;
	}
	public void setTemplateSmsInitiateChaseTask(String templateSmsInitiateChaseTask) {
		this.templateSmsInitiateChaseTask = templateSmsInitiateChaseTask;
	}
	public String getTemplateSmsExecChaseTaskDetail() {
		return templateSmsExecChaseTaskDetail;
	}
	public void setTemplateSmsExecChaseTaskDetail(String templateSmsExecChaseTaskDetail) {
		this.templateSmsExecChaseTaskDetail = templateSmsExecChaseTaskDetail;
	}
	public String getTemplateSmsTryDistill() {
		return templateSmsTryDistill;
	}
	public void setTemplateSmsTryDistill(String templateSmsTryDistill) {
		this.templateSmsTryDistill = templateSmsTryDistill;
	}
	public String getTemplateSmsDistillAccept() {
		return templateSmsDistillAccept;
	}
	public void setTemplateSmsDistillAccept(String templateSmsDistillAccept) {
		this.templateSmsDistillAccept = templateSmsDistillAccept;
	}
	public String getTemplateSmsDistillNoAccept() {
		return templateSmsDistillNoAccept;
	}
	public void setTemplateSmsDistillNoAccept(String templateSmsDistillNoAccept) {
		this.templateSmsDistillNoAccept = templateSmsDistillNoAccept;
	}
	public String getTemplateSmsQuash() {
		return templateSmsQuash;
	}
	public void setTemplateSmsQuash(String templateSmsQuash) {
		this.templateSmsQuash = templateSmsQuash;
	}
	public String getTemplateSmsQuashScheme() {
		return templateSmsQuashScheme;
	}
	public void setTemplateSmsQuashScheme(String templateSmsQuashScheme) {
		this.templateSmsQuashScheme = templateSmsQuashScheme;
	}
	public String getTemplateSmsQuashChaseTaskDetail() {
		return templateSmsQuashChaseTaskDetail;
	}
	public void setTemplateSmsQuashChaseTaskDetail(String templateSmsQuashChaseTaskDetail) {
		this.templateSmsQuashChaseTaskDetail = templateSmsQuashChaseTaskDetail;
	}
	public String getTemplateSmsQuashChaseTask() {
		return templateSmsQuashChaseTask;
	}
	public void setTemplateSmsQuashChaseTask(String templateSmsQuashChaseTask) {
		this.templateSmsQuashChaseTask = templateSmsQuashChaseTask;
	}
	public String getTemplateSmsWin() {
		return templateSmsWin;
	}
	public void setTemplateSmsWin(String templateSmsWin) {
		this.templateSmsWin = templateSmsWin;
	}
	public String getTemplateSmsMobileValid() {
		return templateSmsMobileValid;
	}
	public void setTemplateSmsMobileValid(String templateSmsMobileValid) {
		this.templateSmsMobileValid = templateSmsMobileValid;
	}
	public String getTemplateSmsMobileValided() {
		return templateSmsMobileValided;
	}
	public void setTemplateSmsMobileValided(String templateSmsMobileValided) {
		this.templateSmsMobileValided = templateSmsMobileValided;
	}
	public String getOptCpsregisterAgreement() {
		return optCpsregisterAgreement;
	}
	public void setOptCpsregisterAgreement(String optCpsregisterAgreement) {
		this.optCpsregisterAgreement = optCpsregisterAgreement;
	}
	public Double getOptPromotionMemberBonusScale() {
		return optPromotionMemberBonusScale;
	}
	public void setOptPromotionMemberBonusScale(Double optPromotionMemberBonusScale) {
		this.optPromotionMemberBonusScale = optPromotionMemberBonusScale;
	}
	public Double getOptPromotionSiteBonusScale() {
		return optPromotionSiteBonusScale;
	}
	public void setOptPromotionSiteBonusScale(Double optPromotionSiteBonusScale) {
		this.optPromotionSiteBonusScale = optPromotionSiteBonusScale;
	}
	public Boolean getOptPromotionStatusOn() {
		return optPromotionStatusOn;
	}
	public void setOptPromotionStatusOn(Boolean optPromotionStatusOn) {
		this.optPromotionStatusOn = optPromotionStatusOn;
	}
	public Short getOptFloatNotifiesTime() {
		return optFloatNotifiesTime;
	}
	public void setOptFloatNotifiesTime(Short optFloatNotifiesTime) {
		this.optFloatNotifiesTime = optFloatNotifiesTime;
	}
	public Double getOptScoreCompendium() {
		return optScoreCompendium;
	}
	public void setOptScoreCompendium(Double optScoreCompendium) {
		this.optScoreCompendium = optScoreCompendium;
	}
	public Short getOptScorePrententType() {
		return optScorePrententType;
	}
	public void setOptScorePrententType(Short optScorePrententType) {
		this.optScorePrententType = optScorePrententType;
	}
	public String getTemplateEmailIntiateCustomChase() {
		return templateEmailIntiateCustomChase;
	}
	public void setTemplateEmailIntiateCustomChase(String templateEmailIntiateCustomChase) {
		this.templateEmailIntiateCustomChase = templateEmailIntiateCustomChase;
	}
	public String getTemplateStationSmsIntiateCustomChase() {
		return templateStationSmsIntiateCustomChase;
	}
	public void setTemplateStationSmsIntiateCustomChase(String templateStationSmsIntiateCustomChase) {
		this.templateStationSmsIntiateCustomChase = templateStationSmsIntiateCustomChase;
	}
	public String getTemplateSmsIntiateCustomChase() {
		return templateSmsIntiateCustomChase;
	}
	public void setTemplateSmsIntiateCustomChase(String templateSmsIntiateCustomChase) {
		this.templateSmsIntiateCustomChase = templateSmsIntiateCustomChase;
	}
	public String getTemplateEmailCustomChaseWin() {
		return templateEmailCustomChaseWin;
	}
	public void setTemplateEmailCustomChaseWin(String templateEmailCustomChaseWin) {
		this.templateEmailCustomChaseWin = templateEmailCustomChaseWin;
	}
	public String getTemplateStationSmsCustomChaseWin() {
		return templateStationSmsCustomChaseWin;
	}
	public void setTemplateStationSmsCustomChaseWin(String templateStationSmsCustomChaseWin) {
		this.templateStationSmsCustomChaseWin = templateStationSmsCustomChaseWin;
	}
	public String getTemplateSmsCustomChaseWin() {
		return templateSmsCustomChaseWin;
	}
	public void setTemplateSmsCustomChaseWin(String templateSmsCustomChaseWin) {
		this.templateSmsCustomChaseWin = templateSmsCustomChaseWin;
	}
	public Double getTheTopScheduleOfInitiateCanQuashScheme() {
		return theTopScheduleOfInitiateCanQuashScheme;
	}
	public void setTheTopScheduleOfInitiateCanQuashScheme(Double theTopScheduleOfInitiateCanQuashScheme) {
		this.theTopScheduleOfInitiateCanQuashScheme = theTopScheduleOfInitiateCanQuashScheme;
	}
	public Double getScheduleOfAllCanNotQuashScheme() {
		return scheduleOfAllCanNotQuashScheme;
	}
	public void setScheduleOfAllCanNotQuashScheme(Double scheduleOfAllCanNotQuashScheme) {
		this.scheduleOfAllCanNotQuashScheme = scheduleOfAllCanNotQuashScheme;
	}
	public String getTemplateSmsSystemLoginUser() {
		return templateSmsSystemLoginUser;
	}
	public void setTemplateSmsSystemLoginUser(String templateSmsSystemLoginUser) {
		this.templateSmsSystemLoginUser = templateSmsSystemLoginUser;
	}
	public String getTemplateStationSmsSystemLoginUser() {
		return templateStationSmsSystemLoginUser;
	}
	public void setTemplateStationSmsSystemLoginUser(String templateStationSmsSystemLoginUser) {
		this.templateStationSmsSystemLoginUser = templateStationSmsSystemLoginUser;
	}
	public String getTemplateEmailSystemLoginUser() {
		return templateEmailSystemLoginUser;
	}
	public void setTemplateEmailSystemLoginUser(String templateEmailSystemLoginUser) {
		this.templateEmailSystemLoginUser = templateEmailSystemLoginUser;
	}
	public Sites(Integer parentId, Integer ownerUserId, String name, String logoUrl, String company, String address, String postCode,
			String responsiblePerson, String contactPerson, String telephone, String fax, String mobile, String email, String qq,
			String serviceTelephone, String icpcert, Short level, Boolean on, Double bonusScale, Integer maxSubSites,
			String useLotteryListRestrictions, String useLotteryList, String useLotteryListQuickBuy, String optBettingStationName,
			String optBettingStationNumber, String optBettingStationAddress, String optBettingStationTelephone,
			String optBettingStationContactPreson, String optEmailServerFrom, String optEmailServerEmailServer, String optEmailServerUserName,
			String optEmailServerPassword, String optIspHostName, String optIspHostPort, String optIspUserId, String optIspUserPassword,
			String optIspRegCode, String optIspServiceNumber, String optForumUrl, Short optMobileCheckCharset, Short optMobileCheckStringLength,
			Short optSmspayType, Double optSmsprice, Boolean optIsUseCheckCode, Short optCheckCodeCharset, Boolean optIsWriteLog,
			Double optInitiateSchemeBonusScale, Double optInitiateSchemeMinBuyScale, Double optInitiateSchemeMinBuyAndAssureScale,
			Short optInitiateSchemeMaxNum, Short optInitiateFollowSchemeMaxNum, Short optQuashSchemeMaxNum, Boolean optFullSchemeCanQuash,
			Double optSchemeMinMoney, Double optSchemeMaxMoney, Short optFirstPageUnionBuyMaxRows, Boolean optIsFirstPageUnionBuyWithAll,
			Boolean optIsBuyValidPasswordAdv, Short optMaxShowLotteryNumberRows, Short optLotteryCountOfMenuBarRow, Double optScoringOfSelfBuy,
			Double optScoringOfCommendBuy, Double optScoringExchangeRate, Boolean optScoringStatusOn, Short optSchemeChatRoomStopChatDaysAfterOpened,
			Short optSchemeChatRoomMaxChatNumberOf, Boolean optIsShowFloatAd, Boolean optMemberSharingAlipayStatusOn, Double optCpsBonusScale,
			Boolean optCpsStatusOn, Boolean optExpertsStatusOn, String optPageTitle, String optPageKeywords, Short optDefaultFirstPageType,
			Short optDefaultLotteryFirstPageType, String optLotteryChannelPage, Boolean optIsShowSmssubscriptionNavigate,
			Boolean optIsShowChartNavigate, Short optRoomStyle, String optRoomLogoUrl, Date optUpdateLotteryDateTime,
			Double optInitiateSchemeLimitLowerScaleMoney, Double optInitiateSchemeLimitLowerScale, Double optInitiateSchemeLimitUpperScaleMoney,
			Double optInitiateSchemeLimitUpperScale, String optAbout, String optRightFloatAdcontent, String optContactUs,
			String optUserRegisterAgreement, String optSurrogateFaq, String optOfficialAuthorization, String optCompanyQualification,
			String optExpertsNote, String optSmssubscription, String optLawAffirmsThat, String optCpsPolicies, String templateEmailRegister,
			String templateEmailRegisterAdv, String templateEmailForgetPassword, String templateEmailUserEdit, String templateEmailUserEditAdv,
			String templateEmailInitiateScheme, String templateEmailJoinScheme, String templateEmailInitiateChaseTask,
			String templateEmailExecChaseTaskDetail, String templateEmailTryDistill, String templateEmailDistillAccept,
			String templateEmailDistillNoAccept, String templateEmailQuash, String templateEmailQuashScheme,
			String templateEmailQuashChaseTaskDetail, String templateEmailQuashChaseTask, String templateEmailWin, String templateEmailMobileValid,
			String templateEmailMobileValided, String templateStationSmsRegister, String templateStationSmsRegisterAdv,
			String templateStationSmsForgetPassword, String templateStationSmsUserEdit, String templateStationSmsUserEditAdv,
			String templateStationSmsInitiateScheme, String templateStationSmsJoinScheme, String templateStationSmsInitiateChaseTask,
			String templateStationSmsExecChaseTaskDetail, String templateStationSmsTryDistill, String templateStationSmsDistillAccept,
			String templateStationSmsDistillNoAccept, String templateStationSmsQuash, String templateStationSmsQuashScheme,
			String templateStationSmsQuashChaseTaskDetail, String templateStationSmsQuashChaseTask, String templateStationSmsWin,
			String templateStationSmsMobileValid, String templateStationSmsMobileValided, String templateSmsRegister, String templateSmsRegisterAdv,
			String templateSmsForgetPassword, String templateSmsUserEdit, String templateSmsUserEditAdv, String templateSmsInitiateScheme,
			String templateSmsJoinScheme, String templateSmsInitiateChaseTask, String templateSmsExecChaseTaskDetail, String templateSmsTryDistill,
			String templateSmsDistillAccept, String templateSmsDistillNoAccept, String templateSmsQuash, String templateSmsQuashScheme,
			String templateSmsQuashChaseTaskDetail, String templateSmsQuashChaseTask, String templateSmsWin, String templateSmsMobileValid,
			String templateSmsMobileValided, String optCpsregisterAgreement, Double optPromotionMemberBonusScale, Double optPromotionSiteBonusScale,
			Boolean optPromotionStatusOn, Short optFloatNotifiesTime, Double optScoreCompendium, Short optScorePrententType,
			String templateEmailIntiateCustomChase, String templateStationSmsIntiateCustomChase, String templateSmsIntiateCustomChase,
			String templateEmailCustomChaseWin, String templateStationSmsCustomChaseWin, String templateSmsCustomChaseWin,
			Double theTopScheduleOfInitiateCanQuashScheme, Double scheduleOfAllCanNotQuashScheme, String templateSmsSystemLoginUser,
			String templateStationSmsSystemLoginUser, String templateEmailSystemLoginUser) {
		super();
		this.parentId = parentId;
		this.ownerUserId = ownerUserId;
		this.name = name;
		this.logoUrl = logoUrl;
		this.company = company;
		this.address = address;
		this.postCode = postCode;
		this.responsiblePerson = responsiblePerson;
		this.contactPerson = contactPerson;
		this.telephone = telephone;
		this.fax = fax;
		this.mobile = mobile;
		this.email = email;
		this.qq = qq;
		this.serviceTelephone = serviceTelephone;
		this.icpcert = icpcert;
		this.level = level;
		this.on = on;
		this.bonusScale = bonusScale;
		this.maxSubSites = maxSubSites;
		this.useLotteryListRestrictions = useLotteryListRestrictions;
		this.useLotteryList = useLotteryList;
		this.useLotteryListQuickBuy = useLotteryListQuickBuy;
		this.optBettingStationName = optBettingStationName;
		this.optBettingStationNumber = optBettingStationNumber;
		this.optBettingStationAddress = optBettingStationAddress;
		this.optBettingStationTelephone = optBettingStationTelephone;
		this.optBettingStationContactPreson = optBettingStationContactPreson;
		this.optEmailServerFrom = optEmailServerFrom;
		this.optEmailServerEmailServer = optEmailServerEmailServer;
		this.optEmailServerUserName = optEmailServerUserName;
		this.optEmailServerPassword = optEmailServerPassword;
		this.optIspHostName = optIspHostName;
		this.optIspHostPort = optIspHostPort;
		this.optIspUserId = optIspUserId;
		this.optIspUserPassword = optIspUserPassword;
		this.optIspRegCode = optIspRegCode;
		this.optIspServiceNumber = optIspServiceNumber;
		this.optForumUrl = optForumUrl;
		this.optMobileCheckCharset = optMobileCheckCharset;
		this.optMobileCheckStringLength = optMobileCheckStringLength;
		this.optSmspayType = optSmspayType;
		this.optSmsprice = optSmsprice;
		this.optIsUseCheckCode = optIsUseCheckCode;
		this.optCheckCodeCharset = optCheckCodeCharset;
		this.optIsWriteLog = optIsWriteLog;
		this.optInitiateSchemeBonusScale = optInitiateSchemeBonusScale;
		this.optInitiateSchemeMinBuyScale = optInitiateSchemeMinBuyScale;
		this.optInitiateSchemeMinBuyAndAssureScale = optInitiateSchemeMinBuyAndAssureScale;
		this.optInitiateSchemeMaxNum = optInitiateSchemeMaxNum;
		this.optInitiateFollowSchemeMaxNum = optInitiateFollowSchemeMaxNum;
		this.optQuashSchemeMaxNum = optQuashSchemeMaxNum;
		this.optFullSchemeCanQuash = optFullSchemeCanQuash;
		this.optSchemeMinMoney = optSchemeMinMoney;
		this.optSchemeMaxMoney = optSchemeMaxMoney;
		this.optFirstPageUnionBuyMaxRows = optFirstPageUnionBuyMaxRows;
		this.optIsFirstPageUnionBuyWithAll = optIsFirstPageUnionBuyWithAll;
		this.optIsBuyValidPasswordAdv = optIsBuyValidPasswordAdv;
		this.optMaxShowLotteryNumberRows = optMaxShowLotteryNumberRows;
		this.optLotteryCountOfMenuBarRow = optLotteryCountOfMenuBarRow;
		this.optScoringOfSelfBuy = optScoringOfSelfBuy;
		this.optScoringOfCommendBuy = optScoringOfCommendBuy;
		this.optScoringExchangeRate = optScoringExchangeRate;
		this.optScoringStatusOn = optScoringStatusOn;
		this.optSchemeChatRoomStopChatDaysAfterOpened = optSchemeChatRoomStopChatDaysAfterOpened;
		this.optSchemeChatRoomMaxChatNumberOf = optSchemeChatRoomMaxChatNumberOf;
		this.optIsShowFloatAd = optIsShowFloatAd;
		this.optMemberSharingAlipayStatusOn = optMemberSharingAlipayStatusOn;
		this.optCpsBonusScale = optCpsBonusScale;
		this.optCpsStatusOn = optCpsStatusOn;
		this.optExpertsStatusOn = optExpertsStatusOn;
		this.optPageTitle = optPageTitle;
		this.optPageKeywords = optPageKeywords;
		this.optDefaultFirstPageType = optDefaultFirstPageType;
		this.optDefaultLotteryFirstPageType = optDefaultLotteryFirstPageType;
		this.optLotteryChannelPage = optLotteryChannelPage;
		this.optIsShowSmssubscriptionNavigate = optIsShowSmssubscriptionNavigate;
		this.optIsShowChartNavigate = optIsShowChartNavigate;
		this.optRoomStyle = optRoomStyle;
		this.optRoomLogoUrl = optRoomLogoUrl;
		this.optUpdateLotteryDateTime = optUpdateLotteryDateTime;
		this.optInitiateSchemeLimitLowerScaleMoney = optInitiateSchemeLimitLowerScaleMoney;
		this.optInitiateSchemeLimitLowerScale = optInitiateSchemeLimitLowerScale;
		this.optInitiateSchemeLimitUpperScaleMoney = optInitiateSchemeLimitUpperScaleMoney;
		this.optInitiateSchemeLimitUpperScale = optInitiateSchemeLimitUpperScale;
		this.optAbout = optAbout;
		this.optRightFloatAdcontent = optRightFloatAdcontent;
		this.optContactUs = optContactUs;
		this.optUserRegisterAgreement = optUserRegisterAgreement;
		this.optSurrogateFaq = optSurrogateFaq;
		this.optOfficialAuthorization = optOfficialAuthorization;
		this.optCompanyQualification = optCompanyQualification;
		this.optExpertsNote = optExpertsNote;
		this.optSmssubscription = optSmssubscription;
		this.optLawAffirmsThat = optLawAffirmsThat;
		this.optCpsPolicies = optCpsPolicies;
		this.templateEmailRegister = templateEmailRegister;
		this.templateEmailRegisterAdv = templateEmailRegisterAdv;
		this.templateEmailForgetPassword = templateEmailForgetPassword;
		this.templateEmailUserEdit = templateEmailUserEdit;
		this.templateEmailUserEditAdv = templateEmailUserEditAdv;
		this.templateEmailInitiateScheme = templateEmailInitiateScheme;
		this.templateEmailJoinScheme = templateEmailJoinScheme;
		this.templateEmailInitiateChaseTask = templateEmailInitiateChaseTask;
		this.templateEmailExecChaseTaskDetail = templateEmailExecChaseTaskDetail;
		this.templateEmailTryDistill = templateEmailTryDistill;
		this.templateEmailDistillAccept = templateEmailDistillAccept;
		this.templateEmailDistillNoAccept = templateEmailDistillNoAccept;
		this.templateEmailQuash = templateEmailQuash;
		this.templateEmailQuashScheme = templateEmailQuashScheme;
		this.templateEmailQuashChaseTaskDetail = templateEmailQuashChaseTaskDetail;
		this.templateEmailQuashChaseTask = templateEmailQuashChaseTask;
		this.templateEmailWin = templateEmailWin;
		this.templateEmailMobileValid = templateEmailMobileValid;
		this.templateEmailMobileValided = templateEmailMobileValided;
		this.templateStationSmsRegister = templateStationSmsRegister;
		this.templateStationSmsRegisterAdv = templateStationSmsRegisterAdv;
		this.templateStationSmsForgetPassword = templateStationSmsForgetPassword;
		this.templateStationSmsUserEdit = templateStationSmsUserEdit;
		this.templateStationSmsUserEditAdv = templateStationSmsUserEditAdv;
		this.templateStationSmsInitiateScheme = templateStationSmsInitiateScheme;
		this.templateStationSmsJoinScheme = templateStationSmsJoinScheme;
		this.templateStationSmsInitiateChaseTask = templateStationSmsInitiateChaseTask;
		this.templateStationSmsExecChaseTaskDetail = templateStationSmsExecChaseTaskDetail;
		this.templateStationSmsTryDistill = templateStationSmsTryDistill;
		this.templateStationSmsDistillAccept = templateStationSmsDistillAccept;
		this.templateStationSmsDistillNoAccept = templateStationSmsDistillNoAccept;
		this.templateStationSmsQuash = templateStationSmsQuash;
		this.templateStationSmsQuashScheme = templateStationSmsQuashScheme;
		this.templateStationSmsQuashChaseTaskDetail = templateStationSmsQuashChaseTaskDetail;
		this.templateStationSmsQuashChaseTask = templateStationSmsQuashChaseTask;
		this.templateStationSmsWin = templateStationSmsWin;
		this.templateStationSmsMobileValid = templateStationSmsMobileValid;
		this.templateStationSmsMobileValided = templateStationSmsMobileValided;
		this.templateSmsRegister = templateSmsRegister;
		this.templateSmsRegisterAdv = templateSmsRegisterAdv;
		this.templateSmsForgetPassword = templateSmsForgetPassword;
		this.templateSmsUserEdit = templateSmsUserEdit;
		this.templateSmsUserEditAdv = templateSmsUserEditAdv;
		this.templateSmsInitiateScheme = templateSmsInitiateScheme;
		this.templateSmsJoinScheme = templateSmsJoinScheme;
		this.templateSmsInitiateChaseTask = templateSmsInitiateChaseTask;
		this.templateSmsExecChaseTaskDetail = templateSmsExecChaseTaskDetail;
		this.templateSmsTryDistill = templateSmsTryDistill;
		this.templateSmsDistillAccept = templateSmsDistillAccept;
		this.templateSmsDistillNoAccept = templateSmsDistillNoAccept;
		this.templateSmsQuash = templateSmsQuash;
		this.templateSmsQuashScheme = templateSmsQuashScheme;
		this.templateSmsQuashChaseTaskDetail = templateSmsQuashChaseTaskDetail;
		this.templateSmsQuashChaseTask = templateSmsQuashChaseTask;
		this.templateSmsWin = templateSmsWin;
		this.templateSmsMobileValid = templateSmsMobileValid;
		this.templateSmsMobileValided = templateSmsMobileValided;
		this.optCpsregisterAgreement = optCpsregisterAgreement;
		this.optPromotionMemberBonusScale = optPromotionMemberBonusScale;
		this.optPromotionSiteBonusScale = optPromotionSiteBonusScale;
		this.optPromotionStatusOn = optPromotionStatusOn;
		this.optFloatNotifiesTime = optFloatNotifiesTime;
		this.optScoreCompendium = optScoreCompendium;
		this.optScorePrententType = optScorePrententType;
		this.templateEmailIntiateCustomChase = templateEmailIntiateCustomChase;
		this.templateStationSmsIntiateCustomChase = templateStationSmsIntiateCustomChase;
		this.templateSmsIntiateCustomChase = templateSmsIntiateCustomChase;
		this.templateEmailCustomChaseWin = templateEmailCustomChaseWin;
		this.templateStationSmsCustomChaseWin = templateStationSmsCustomChaseWin;
		this.templateSmsCustomChaseWin = templateSmsCustomChaseWin;
		this.theTopScheduleOfInitiateCanQuashScheme = theTopScheduleOfInitiateCanQuashScheme;
		this.scheduleOfAllCanNotQuashScheme = scheduleOfAllCanNotQuashScheme;
		this.templateSmsSystemLoginUser = templateSmsSystemLoginUser;
		this.templateStationSmsSystemLoginUser = templateStationSmsSystemLoginUser;
		this.templateEmailSystemLoginUser = templateEmailSystemLoginUser;
	}
	public Sites() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}