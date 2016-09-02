package com.yuncai.modules.lottery.model.Oracle;

import java.util.Date;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketProvider;
import com.yuncai.modules.lottery.model.Oracle.toolType.TicketStatus;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;

public class AbstractTicket implements java.io.Serializable{
	private Integer id;
	//方案ID
	private Integer planNo;
	//内容
	private String content;
	//期数
	private String term;
	//彩种
	private LotteryType lotteryType;
	//金额
	private Integer amount;
	//创建时间
	private Date createDateTime;
	//送票时间
	private Date sendDateTime;
	//打印时间
	private Date printDateTime;
	//送票截止时间
	private Date dealDateTime;
	//倍数
	private Integer multiple; 
	//玩法
	private PlayType playType;
	//是否中奖
	private Integer isBingo;
	//中奖金额
	private Double bingoAmount;
	//中奖内容
	private String bingoContent;
	//状态
	private TicketStatus status;
	//是否追加
	private String addAttribute;
	//账户
	private String account;
	//渠道ID
	private String outId;
	private Integer noInBatch;
	private String batchNo;
	private Integer isConvert;
	private Date convertDateTime;
	private String serialNo;
	//渠道送票
	private TicketProvider provider;
	//竞彩票口sp值
	private String SP;
	//备份拆票内容
	private String boZhongContent;

	// Constructors

	public TicketProvider getProvider() {
		return provider;
	}

	public void setProvider(TicketProvider provider) {
		this.provider = provider;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/** default constructor */
	public AbstractTicket() {
	}

	/** minimal constructor */
	public AbstractTicket(Integer planNo, String term, Date createDateTime, Date dealDateTime, Integer multiple, PlayType playType,
			TicketStatus status, String account) {
		this.planNo = planNo;
		this.term = term;
		this.createDateTime = createDateTime;
		this.dealDateTime = dealDateTime;
		this.multiple = multiple;
		this.playType = playType;
		this.status = status;
		this.account = account;
	}

	/** full constructor */
	public AbstractTicket(Integer planNo, String content, String term, LotteryType lotteryType, Integer amount, Date createDateTime,
			Date sendDateTime, Date printDateTime, Date dealDateTime, Integer multiple, PlayType playType, Integer isBingo, Double bingoAmount,
			String bingoContent, TicketStatus status, String addAttribute, String account, String outId, Integer noInBatch, String batchNo,
			Integer isConvert, Date convertDateTime) {
		this.planNo = planNo;
		this.content = content;
		this.term = term;
		this.lotteryType = lotteryType;
		this.amount = amount;
		this.createDateTime = createDateTime;
		this.sendDateTime = sendDateTime;
		this.printDateTime = printDateTime;
		this.dealDateTime = dealDateTime;
		this.multiple = multiple;
		this.playType = playType;
		this.isBingo = isBingo;
		this.bingoAmount = bingoAmount;
		this.bingoContent = bingoContent;
		this.status = status;
		this.addAttribute = addAttribute;
		this.account = account;
		this.outId = outId;
		this.noInBatch = noInBatch;
		this.batchNo = batchNo;
		this.isConvert = isConvert;
		this.convertDateTime = convertDateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlanNo() {
		return planNo;
	}

	public void setPlanNo(Integer planNo) {
		this.planNo = planNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getSendDateTime() {
		return sendDateTime;
	}

	public void setSendDateTime(Date sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	public Date getPrintDateTime() {
		return printDateTime;
	}

	public void setPrintDateTime(Date printDateTime) {
		this.printDateTime = printDateTime;
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

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public Integer getIsBingo() {
		return isBingo;
	}

	public void setIsBingo(Integer isBingo) {
		this.isBingo = isBingo;
	}

	public Double getBingoAmount() {
		return bingoAmount;
	}

	public void setBingoAmount(Double bingoAmount) {
		this.bingoAmount = bingoAmount;
	}

	public String getBingoContent() {
		return bingoContent;
	}

	public void setBingoContent(String bingoContent) {
		this.bingoContent = bingoContent;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public String getAddAttribute() {
		return addAttribute;
	}

	public void setAddAttribute(String addAttribute) {
		this.addAttribute = addAttribute;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public Integer getNoInBatch() {
		return noInBatch;
	}

	public void setNoInBatch(Integer noInBatch) {
		this.noInBatch = noInBatch;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getIsConvert() {
		return isConvert;
	}

	public void setIsConvert(Integer isConvert) {
		this.isConvert = isConvert;
	}

	public Date getConvertDateTime() {
		return convertDateTime;
	}

	public void setConvertDateTime(Date convertDateTime) {
		this.convertDateTime = convertDateTime;
	}

	public String getSP() {
		return SP;
	}

	public void setSP(String sP) {
		SP = sP;
	}

	public String getBoZhongContent() {
		return boZhongContent;
	}

	public void setBoZhongContent(String boZhongContent) {
		this.boZhongContent = boZhongContent;
	}

}
