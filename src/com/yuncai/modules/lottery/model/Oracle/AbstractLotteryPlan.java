package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PublicStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;

public class AbstractLotteryPlan implements java.io.Serializable {
	// Fields
	// 方案编号
	private Integer planNo;
	// 彩种
	private LotteryType lotteryType;
	// 彩期
	private String term;
	// 方案类型
	private PlanType planType;
	// 选择类型
	private SelectType selectType;
	// 会员id
	private Integer memberId;
	// 会员帐号
	private String account;
	//会员昵称
	private String nickName;
	// 发起时间
	private Date createDateTime;
	// 金额
	private Integer amount;
	// 每份金额
	private Integer perAmount;
	// 份数
	private Integer part;
	// 已售份数
	private Integer soldPart;
	// 发起人认购份数
	private Integer founderPart;
	// 保底份数
	private Integer reservePart;
	// 截止处理时间
	private Date dealDateTime;
	// 倍数
	private Integer multiple;
	// 状态
	private PlanStatus planStatus;
	// 打票时间
	private Date printTicketDateTime;
	// 中奖状态
	private WinStatus winStatus;
	// 税前奖金
	private Double pretaxPrize;
	// 税后奖金
	private Double posttaxPrize;
	// 中奖描述内容 奖级^中奖注数^中奖金额 prize1^1^1000#prize2^0^0#prize3^0^0
	private String prizeContent;
	// 查看次数
	private Integer clicks;
	
	
	// 方案描述
	private String planDesc;
	// 内容
	private String content;
	// 是否已经上传方案
	private Integer isUploadContent;
	// 中奖后获得的经验值
	private Integer experience;
	// 是否超级发单人
	private Integer isSuperMan;
	// 玩法
	private PlayType playType;
	// 辅助字段 大乐透的是否追加
	private String addAttribute;
	// 是否置顶
	private Integer isTop;
	// 模型 来源方 其中套餐标记此单来源于哪个套餐，存放套餐的planNo
	private Integer model;
	// md5验证
	private String verifyMd5;
	// 公开类型
	private PublicStatus publicStatus;

	private int commision;
	// 被跟单次数
	private Integer bookCount;
	// 购买类型
	private GenType genType;
	// 是否可处理(出票)
	private Integer isAbleTicketing;
	// 出票状态
	private PlanTicketStatus planTicketStatus;
	//活动加奖金额
	private Double addPrize;
	//开奖时间
	private Date openResultTime;
	//合买促销图标
	private Integer planHmOg;
	// Constructors
	
	private String  automaticType; //自动合买标识//AUTOMATIC_TYPE;

	private String channel;//渠道ID
	
	private String title;//方案标题

	private Integer easyType;//轻松购彩标志 0否1是
	
	//处理方案公开最后截止时间;
	private Date lateDateTime;
	
	public Date getLateDateTime() {
		return lateDateTime;
	}

	public void setLateDateTime(Date lateDateTime) {
		this.lateDateTime = lateDateTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/** default constructor */
	public AbstractLotteryPlan() {
	}

	/** minimal constructor */
	public AbstractLotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, Integer amount, Integer perAmount, Integer part, Integer soldPart, Integer founderPart, Integer reservePart,
			Date dealDateTime, Integer multiple, PlanStatus planStatus, WinStatus winStatus, Integer clicks, String content, Integer isUploadContent,
			PlayType playType, String verifyMd5, Integer isAbleTicketing, PlanTicketStatus planTicketStatus) {
		this.planNo = planNo;
		this.lotteryType = lotteryType;
		this.term = term;
		this.planType = planType;
		this.selectType = selectType;
		this.memberId = memberId;
		this.account = account;
		this.amount = amount;
		this.perAmount = perAmount;
		this.part = part;
		this.soldPart = soldPart;
		this.founderPart = founderPart;
		this.reservePart = reservePart;
		this.dealDateTime = dealDateTime;
		this.multiple = multiple;
		this.planStatus = planStatus;
		this.winStatus = winStatus;
		this.clicks = clicks;
		this.content = content;
		this.isUploadContent = isUploadContent;
		this.playType = playType;
		this.verifyMd5 = verifyMd5;
		this.isAbleTicketing = isAbleTicketing;
		this.planTicketStatus = planTicketStatus;
	}

	/** full constructor */
	public AbstractLotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, Date createDateTime, Integer amount, Integer perAmount, Integer part, Integer soldPart, Integer founderPart,
			Integer reservePart, Date dealDateTime, Integer multiple, PlanStatus planStatus, Date printTicketDateTime, WinStatus winStatus,
			Double pretaxPrize, Double posttaxPrize, String prizeContent, Integer clicks, String planDesc, String content, Integer isUploadContent,
			Integer experience, Integer isSuperMan, PlayType playType, String addAttribute, Integer isTop, Integer model, String verifyMd5,
			Integer bookCount, GenType genType, Integer isAbleTicketing, PlanTicketStatus planTicketStatus) {
		this.planNo = planNo;
		this.lotteryType = lotteryType;
		this.term = term;
		this.planType = planType;
		this.selectType = selectType;
		this.memberId = memberId;
		this.account = account;
		this.createDateTime = createDateTime;
		this.amount = amount;
		this.perAmount = perAmount;
		this.part = part;
		this.soldPart = soldPart;
		this.founderPart = founderPart;
		this.reservePart = reservePart;
		this.dealDateTime = dealDateTime;
		this.multiple = multiple;
		this.planStatus = planStatus;
		this.printTicketDateTime = printTicketDateTime;
		this.winStatus = winStatus;
		this.pretaxPrize = pretaxPrize;
		this.posttaxPrize = posttaxPrize;
		this.prizeContent = prizeContent;
		this.clicks = clicks;
		this.planDesc = planDesc;
		this.content = content;
		this.isUploadContent = isUploadContent;
		this.experience = experience;
		this.isSuperMan = isSuperMan;
		this.playType = playType;
		this.addAttribute = addAttribute;
		this.isTop = isTop;
		this.model = model;
		this.verifyMd5 = verifyMd5;
		this.bookCount = bookCount;
		this.genType = genType;
		this.isAbleTicketing = isAbleTicketing;
		this.planTicketStatus = planTicketStatus;
	}

	public AbstractLotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, Date createDateTime, Integer amount, Integer perAmount, Integer part, Integer soldPart, Integer founderPart,
			Integer reservePart, Date dealDateTime, Integer multiple, PlanStatus planStatus, Date printTicketDateTime, WinStatus winStatus,
			Double pretaxPrize, Double posttaxPrize, String prizeContent, Integer clicks, String planDesc, String content, Integer isUploadContent,
			Integer experience, Integer isSuperMan, PlayType playType, String addAttribute, Integer isTop, Integer model, String verifyMd5,
			Integer bookCount, GenType genType, Integer isAbleTicketing, PlanTicketStatus planTicketStatus,String channel) {
		this.planNo = planNo;
		this.lotteryType = lotteryType;
		this.term = term;
		this.planType = planType;
		this.selectType = selectType;
		this.memberId = memberId;
		this.account = account;
		this.createDateTime = createDateTime;
		this.amount = amount;
		this.perAmount = perAmount;
		this.part = part;
		this.soldPart = soldPart;
		this.founderPart = founderPart;
		this.reservePart = reservePart;
		this.dealDateTime = dealDateTime;
		this.multiple = multiple;
		this.planStatus = planStatus;
		this.printTicketDateTime = printTicketDateTime;
		this.winStatus = winStatus;
		this.pretaxPrize = pretaxPrize;
		this.posttaxPrize = posttaxPrize;
		this.prizeContent = prizeContent;
		this.clicks = clicks;
		this.planDesc = planDesc;
		this.content = content;
		this.isUploadContent = isUploadContent;
		this.experience = experience;
		this.isSuperMan = isSuperMan;
		this.playType = playType;
		this.addAttribute = addAttribute;
		this.isTop = isTop;
		this.model = model;
		this.verifyMd5 = verifyMd5;
		this.bookCount = bookCount;
		this.genType = genType;
		this.isAbleTicketing = isAbleTicketing;
		this.planTicketStatus = planTicketStatus;
		this.channel = channel;
	}	
	
	

	public AbstractLotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, Date createDateTime, Integer amount, Integer perAmount, Integer part, Integer soldPart, Integer founderPart,
			Integer reservePart, Date dealDateTime, Integer multiple, PlanStatus planStatus, Date printTicketDateTime, WinStatus winStatus,
			Double pretaxPrize, Double posttaxPrize, String prizeContent, Integer clicks, String planDesc, String content, Integer isUploadContent,
			Integer experience, Integer isSuperMan, PlayType playType, String addAttribute, Integer isTop, Integer model, String verifyMd5,
			PublicStatus publicStatus, int commision, Integer bookCount, GenType genType, Integer isAbleTicketing, PlanTicketStatus planTicketStatus,
			Double addPrize, Date openResultTime, Integer planHmOg, String automaticType, String channel, String title) {
		super();
		this.planNo = planNo;
		this.lotteryType = lotteryType;
		this.term = term;
		this.planType = planType;
		this.selectType = selectType;
		this.memberId = memberId;
		this.account = account;
		this.createDateTime = createDateTime;
		this.amount = amount;
		this.perAmount = perAmount;
		this.part = part;
		this.soldPart = soldPart;
		this.founderPart = founderPart;
		this.reservePart = reservePart;
		this.dealDateTime = dealDateTime;
		this.multiple = multiple;
		this.planStatus = planStatus;
		this.printTicketDateTime = printTicketDateTime;
		this.winStatus = winStatus;
		this.pretaxPrize = pretaxPrize;
		this.posttaxPrize = posttaxPrize;
		this.prizeContent = prizeContent;
		this.clicks = clicks;
		this.planDesc = planDesc;
		this.content = content;
		this.isUploadContent = isUploadContent;
		this.experience = experience;
		this.isSuperMan = isSuperMan;
		this.playType = playType;
		this.addAttribute = addAttribute;
		this.isTop = isTop;
		this.model = model;
		this.verifyMd5 = verifyMd5;
		this.publicStatus = publicStatus;
		this.commision = commision;
		this.bookCount = bookCount;
		this.genType = genType;
		this.isAbleTicketing = isAbleTicketing;
		this.planTicketStatus = planTicketStatus;
		this.addPrize = addPrize;
		this.openResultTime = openResultTime;
		this.planHmOg = planHmOg;
		this.automaticType = automaticType;
		this.channel = channel;
		this.title = title;
	}

	/**
	 * new full
	 * @param planNo
	 * @param lotteryType
	 * @param term
	 * @param planType
	 * @param selectType
	 * @param memberId
	 * @param account
	 * @param nickName
	 * @param createDateTime
	 * @param amount
	 * @param perAmount
	 * @param part
	 * @param soldPart
	 * @param founderPart
	 * @param reservePart
	 * @param dealDateTime
	 * @param multiple
	 * @param planStatus
	 * @param printTicketDateTime
	 * @param winStatus
	 * @param pretaxPrize
	 * @param posttaxPrize
	 * @param prizeContent
	 * @param clicks
	 * @param planDesc
	 * @param content
	 * @param isUploadContent
	 * @param experience
	 * @param isSuperMan
	 * @param playType
	 * @param addAttribute
	 * @param isTop
	 * @param model
	 * @param verifyMd5
	 * @param publicStatus
	 * @param commision
	 * @param bookCount
	 * @param genType
	 * @param isAbleTicketing
	 * @param planTicketStatus
	 * @param addPrize
	 * @param openResultTime
	 * @param planHmOg
	 * @param automaticType
	 * @param channel
	 * @param title
	 * @param easyType
	 */
	public AbstractLotteryPlan(Integer planNo, LotteryType lotteryType, String term, PlanType planType, SelectType selectType, Integer memberId,
			String account, String nickName, Date createDateTime, Integer amount, Integer perAmount, Integer part, Integer soldPart,
			Integer founderPart, Integer reservePart, Date dealDateTime, Integer multiple, PlanStatus planStatus, Date printTicketDateTime,
			WinStatus winStatus, Double pretaxPrize, Double posttaxPrize, String prizeContent, Integer clicks, String planDesc, String content,
			Integer isUploadContent, Integer experience, Integer isSuperMan, PlayType playType, String addAttribute, Integer isTop, Integer model,
			String verifyMd5, PublicStatus publicStatus, int commision, Integer bookCount, GenType genType, Integer isAbleTicketing,
			PlanTicketStatus planTicketStatus, Double addPrize, Date openResultTime, Integer planHmOg, String automaticType, String channel,
			String title, Integer easyType) {
		super();
		this.planNo = planNo;
		this.lotteryType = lotteryType;
		this.term = term;
		this.planType = planType;
		this.selectType = selectType;
		this.memberId = memberId;
		this.account = account;
		this.nickName = nickName;
		this.createDateTime = createDateTime;
		this.amount = amount;
		this.perAmount = perAmount;
		this.part = part;
		this.soldPart = soldPart;
		this.founderPart = founderPart;
		this.reservePart = reservePart;
		this.dealDateTime = dealDateTime;
		this.multiple = multiple;
		this.planStatus = planStatus;
		this.printTicketDateTime = printTicketDateTime;
		this.winStatus = winStatus;
		this.pretaxPrize = pretaxPrize;
		this.posttaxPrize = posttaxPrize;
		this.prizeContent = prizeContent;
		this.clicks = clicks;
		this.planDesc = planDesc;
		this.content = content;
		this.isUploadContent = isUploadContent;
		this.experience = experience;
		this.isSuperMan = isSuperMan;
		this.playType = playType;
		this.addAttribute = addAttribute;
		this.isTop = isTop;
		this.model = model;
		this.verifyMd5 = verifyMd5;
		this.publicStatus = publicStatus;
		this.commision = commision;
		this.bookCount = bookCount;
		this.genType = genType;
		this.isAbleTicketing = isAbleTicketing;
		this.planTicketStatus = planTicketStatus;
		this.addPrize = addPrize;
		this.openResultTime = openResultTime;
		this.planHmOg = planHmOg;
		this.automaticType = automaticType;
		this.channel = channel;
		this.title = title;
		this.easyType = easyType;
	}

	public Integer getPlanNo() {
		return planNo;
	}

	public void setPlanNo(Integer planNo) {
		this.planNo = planNo;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public PlanType getPlanType() {
		return planType;
	}

	public void setPlanType(PlanType planType) {
		this.planType = planType;
	}

	public SelectType getSelectType() {
		return selectType;
	}

	public void setSelectType(SelectType selectType) {
		this.selectType = selectType;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPerAmount() {
		return perAmount;
	}

	public void setPerAmount(Integer perAmount) {
		this.perAmount = perAmount;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public Integer getSoldPart() {
		return soldPart;
	}

	public void setSoldPart(Integer soldPart) {
		this.soldPart = soldPart;
	}

	public Integer getFounderPart() {
		return founderPart;
	}

	public void setFounderPart(Integer founderPart) {
		this.founderPart = founderPart;
	}

	public Integer getReservePart() {
		return reservePart;
	}

	public void setReservePart(Integer reservePart) {
		this.reservePart = reservePart;
	}

	public Date getDealDateTime() {
		return dealDateTime;
	}

	public void setDealDateTime(Date dealDateTime) {
		this.dealDateTime = dealDateTime;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public PlanStatus getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(PlanStatus planStatus) {
		this.planStatus = planStatus;
	}

	public Date getPrintTicketDateTime() {
		return printTicketDateTime;
	}

	public void setPrintTicketDateTime(Date printTicketDateTime) {
		this.printTicketDateTime = printTicketDateTime;
	}

	public WinStatus getWinStatus() {
		return winStatus;
	}

	public void setWinStatus(WinStatus winStatus) {
		this.winStatus = winStatus;
	}

	public Double getPretaxPrize() {
		return pretaxPrize;
	}

	public void setPretaxPrize(Double pretaxPrize) {
		this.pretaxPrize = pretaxPrize;
	}

	public Double getPosttaxPrize() {
		return posttaxPrize;
	}

	public void setPosttaxPrize(Double posttaxPrize) {
		this.posttaxPrize = posttaxPrize;
	}

	public String getPrizeContent() {
		return prizeContent;
	}

	public void setPrizeContent(String prizeContent) {
		this.prizeContent = prizeContent;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public String getPlanDesc() {
		return planDesc;
	}

	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsUploadContent() {
		return isUploadContent;
	}

	public void setIsUploadContent(Integer isUploadContent) {
		this.isUploadContent = isUploadContent;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public Integer getIsSuperMan() {
		return isSuperMan;
	}

	public void setIsSuperMan(Integer isSuperMan) {
		this.isSuperMan = isSuperMan;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public String getAddAttribute() {
		return addAttribute;
	}

	public void setAddAttribute(String addAttribute) {
		this.addAttribute = addAttribute;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public String getVerifyMd5() {
		return verifyMd5;
	}

	public void setVerifyMd5(String verifyMd5) {
		this.verifyMd5 = verifyMd5;
	}

	public PublicStatus getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(PublicStatus publicStatus) {
		this.publicStatus = publicStatus;
	}

	public int getCommision() {
		return commision;
	}

	public void setCommision(int commision) {
		this.commision = commision;
	}

	public Integer getBookCount() {
		return bookCount;
	}

	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	public GenType getGenType() {
		return genType;
	}

	public void setGenType(GenType genType) {
		this.genType = genType;
	}

	public Integer getIsAbleTicketing() {
		return isAbleTicketing;
	}

	public void setIsAbleTicketing(Integer isAbleTicketing) {
		this.isAbleTicketing = isAbleTicketing;
	}

	public PlanTicketStatus getPlanTicketStatus() {
		return planTicketStatus;
	}

	public void setPlanTicketStatus(PlanTicketStatus planTicketStatus) {
		this.planTicketStatus = planTicketStatus;
	}

	public Double getAddPrize() {
		return addPrize;
	}

	public void setAddPrize(Double addPrize) {
		this.addPrize = addPrize;
	}

	public Date getOpenResultTime() {
		return openResultTime;
	}

	public void setOpenResultTime(Date openResultTime) {
		this.openResultTime = openResultTime;
	}

	public Integer getPlanHmOg() {
		return planHmOg;
	}

	public void setPlanHmOg(Integer planHmOg) {
		this.planHmOg = planHmOg;
	}

	public String getAutomaticType() {
		return automaticType;
	}

	public void setAutomaticType(String automaticType) {
		this.automaticType = automaticType;
	}

	public String getNickName() {
		
		//针对用户名中包含手机号码的 进行屏蔽掉中间si'wei数字
		
		nickName = Strings.encryptionStrPhone(nickName);
		
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getEasyType() {
		return easyType;
	}

	public void setEasyType(Integer easyType) {
		this.easyType = easyType;
	}

	

}
