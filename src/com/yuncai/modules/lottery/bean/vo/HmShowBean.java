package com.yuncai.modules.lottery.bean.vo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.BallCodeConverter;
import com.yuncai.core.tools.DateTools;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberFollowingType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanOrderStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanTicketStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PublicStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WinStatus;
import com.yuncai.modules.lottery.sporttery.OptionItem;

import com.yuncai.modules.lottery.sporttery.model.FootBallBetContentTurbid;

import com.yuncai.modules.lottery.sporttery.model.SportteryBetContentModelTurbid;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.MatchItem;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;

import com.yuncai.modules.lottery.sporttery.support.football.FootBallMatchTurbidItem;

public class HmShowBean {
	private String belongAccount; // 发起人 
	private LotteryType lotteryType; // 彩种
	private PlayType playType; // 方案玩法
	private String term; // 彩期
	private int part; // 认购的份数
	private Integer planNo; // 方案编号
	private PlanStatus planStatus; // 方案状态
	private int orderNo; // 定单编号
	private int amount; // 方案数金额'
	private String content; // 方案内容
	private WinStatus winStatus; // 方案开奖状态
	private PlanOrderStatus planOrderStatus; // 定单状态
	private double posttaxPrize; // 每个订单税后奖金
	private Date createDateTime; // 购买时间
	private Date dealDateTime; // 截止时间
	private BuyType buyType; // 购买类型
	private int isUploadContent;
	private SelectType selectType;
//	private Integer publicStatus = 1;//1公开，2不公开，3开奖后公开
	private PublicStatus publicStatus;
	private int soldPart; // 售出份数
	private int reservePart; // 保底份数
	
	private Integer model; //扩展字段，用来放相关编号
	private PlanTicketStatus planTicketStatus;

	private PlanType plantype;  //购买类型
	private MemberFollowingType followingType;  //
	
	
	private String winNum;//开奖号码
	
	// 倍数
	private Integer multiple;
	// 提成
	private int commision;
	
	private int totalAmount;//方案总金额
	
	private String title;
	
	private String descript;
	
	private String nickName;
	
	private Integer participates; 
	
	private Integer totalPart;//方案总数量
	
	private Double pretaxPrize;//方案税前奖金
	
	private Date lateDateTime; // 截止时间
	
	public HmShowBean(String belongAccount, LotteryType lotteryType,
			PlayType playType, String term, int part, Integer planNo,
			PlanStatus planStatus, int orderNo, int amount, String content,
			WinStatus winStatus, PlanOrderStatus planOrderStatus,
			double posttaxPrize, Date createDateTime, Date dealDateTime,
			BuyType buyType, int isUploadContent, SelectType selectType,
			PublicStatus publicStatus, int soldPart, int reservePart, Integer model,
			PlanTicketStatus planTicketStatus, PlanType plantype,
			MemberFollowingType followingType) {
		super();
		this.belongAccount = belongAccount;
		this.lotteryType = lotteryType;
		this.playType = playType;
		this.term = term;
		this.part = part;
		this.planNo = planNo;
		this.planStatus = planStatus;
		this.orderNo = orderNo;
		this.amount = amount;
		this.content = content;
		this.winStatus = winStatus;
		this.planOrderStatus = planOrderStatus;
		this.posttaxPrize = posttaxPrize;
		this.createDateTime = createDateTime;
		this.dealDateTime = dealDateTime;
		this.buyType = buyType;
		this.isUploadContent = isUploadContent;
		this.selectType = selectType;
		this.publicStatus = publicStatus;
		this.soldPart = soldPart;
		this.reservePart = reservePart;
		this.model = model;
		this.planTicketStatus = planTicketStatus;
		this.plantype = plantype;
		this.followingType = followingType;
	}

	
	
	
	
	public HmShowBean(String belongAccount, LotteryType lotteryType, PlayType playType, String term, int part, Integer planNo, PlanStatus planStatus,
			int orderNo, int amount, String content, WinStatus winStatus, PlanOrderStatus planOrderStatus, double posttaxPrize, Date createDateTime,
			Date dealDateTime, BuyType buyType, int isUploadContent, SelectType selectType, PublicStatus publicStatus, int soldPart, int reservePart,
			Integer model, PlanTicketStatus planTicketStatus, PlanType plantype, MemberFollowingType followingType, String winNum, Integer multiple,
			int commision, int totalAmount, String title, String descript) {
		super();
		this.belongAccount = belongAccount;
		this.lotteryType = lotteryType;
		this.playType = playType;
		this.term = term;
		this.part = part;
		this.planNo = planNo;
		this.planStatus = planStatus;
		this.orderNo = orderNo;
		this.amount = amount;
		this.content = content;
		this.winStatus = winStatus;
		this.planOrderStatus = planOrderStatus;
		this.posttaxPrize = posttaxPrize;
		this.createDateTime = createDateTime;
		this.dealDateTime = dealDateTime;
		this.buyType = buyType;
		this.isUploadContent = isUploadContent;
		this.selectType = selectType;
		this.publicStatus = publicStatus;
		this.soldPart = soldPart;
		this.reservePart = reservePart;
		this.model = model;
		this.planTicketStatus = planTicketStatus;
		this.plantype = plantype;
		this.followingType = followingType;
		this.winNum = winNum;
		this.multiple = multiple;
		this.commision = commision;
		this.totalAmount = totalAmount;
		this.title = title;
		this.descript = descript;
	}




	
	public HmShowBean(String belongAccount, LotteryType lotteryType, PlayType playType, String term, int part, Integer planNo, PlanStatus planStatus,
			int orderNo, int amount, String content, WinStatus winStatus, PlanOrderStatus planOrderStatus, double posttaxPrize, Date createDateTime,
			Date dealDateTime, BuyType buyType, int isUploadContent, SelectType selectType, PublicStatus publicStatus, int soldPart, int reservePart,
			Integer model, PlanTicketStatus planTicketStatus, PlanType plantype, MemberFollowingType followingType, String winNum, Integer multiple,
			int commision, int totalAmount, String title, String descript, String nickName,Integer totalPart,Double 	pretaxPrize) {
		super();
		this.belongAccount = belongAccount;
		this.lotteryType = lotteryType;
		this.playType = playType;
		this.term = term;
		this.part = part;
		this.planNo = planNo;
		this.planStatus = planStatus;
		this.orderNo = orderNo;
		this.amount = amount;
		this.content = content;
		this.winStatus = winStatus;
		this.planOrderStatus = planOrderStatus;
		this.posttaxPrize = posttaxPrize;
		this.createDateTime = createDateTime;
		this.dealDateTime = dealDateTime;
		this.buyType = buyType;
		this.isUploadContent = isUploadContent;
		this.selectType = selectType;
		this.publicStatus = publicStatus;
		this.soldPart = soldPart;
		this.reservePart = reservePart;
		this.model = model;
		this.planTicketStatus = planTicketStatus;
		this.plantype = plantype;
		this.followingType = followingType;
		this.winNum = winNum;
		this.multiple = multiple;
		this.commision = commision;
		this.totalAmount = totalAmount;
		this.title = title;
		this.descript = descript;
		this.nickName = nickName;
		this.totalPart = totalPart;
		this.pretaxPrize = pretaxPrize;
	}
	
	
	public HmShowBean(String belongAccount, LotteryType lotteryType, PlayType playType, String term, int part, Integer planNo, PlanStatus planStatus,
			int orderNo, int amount, String content, WinStatus winStatus, PlanOrderStatus planOrderStatus, double posttaxPrize, Date createDateTime,
			Date dealDateTime, BuyType buyType, int isUploadContent, SelectType selectType, PublicStatus publicStatus, int soldPart, int reservePart,
			Integer model, PlanTicketStatus planTicketStatus, PlanType plantype, MemberFollowingType followingType, String winNum, Integer multiple,
			int commision, int totalAmount, String title, String descript, String nickName,Integer totalPart,Double 	pretaxPrize,Date lateDateTime) {
		super();
		this.belongAccount = belongAccount;
		this.lotteryType = lotteryType;
		this.playType = playType;
		this.term = term;
		this.part = part;
		this.planNo = planNo;
		this.planStatus = planStatus;
		this.orderNo = orderNo;
		this.amount = amount;
		this.content = content;
		this.winStatus = winStatus;
		this.planOrderStatus = planOrderStatus;
		this.posttaxPrize = posttaxPrize;
		this.createDateTime = createDateTime;
		this.dealDateTime = dealDateTime;
		this.buyType = buyType;
		this.isUploadContent = isUploadContent;
		this.selectType = selectType;
		this.publicStatus = publicStatus;
		this.soldPart = soldPart;
		this.reservePart = reservePart;
		this.model = model;
		this.planTicketStatus = planTicketStatus;
		this.plantype = plantype;
		this.followingType = followingType;
		this.winNum = winNum;
		this.multiple = multiple;
		this.commision = commision;
		this.totalAmount = totalAmount;
		this.title = title;
		this.descript = descript;
		this.nickName = nickName;
		this.totalPart = totalPart;
		this.pretaxPrize = pretaxPrize;
		this.lateDateTime = lateDateTime;
	}
	
	
	


	public int getSoldPart() {
		return soldPart;
	}

	public void setSoldPart(int soldPart) {
		this.soldPart = soldPart;
	}

	public int getIsUploadContent() {
		return isUploadContent;
	}

	public void setIsUploadContent(int isUploadContent) {
		this.isUploadContent = isUploadContent;
	}

	public SelectType getSelectType() {
		return selectType;
	}

	public void setSelectType(SelectType selectType) {
		this.selectType = selectType;
	}

	public double getPosttaxPrize() {
		return posttaxPrize;
	}

	public void setPosttaxPrize(double posttaxPrize) {
		this.posttaxPrize = posttaxPrize;
	}

	public String getBelongAccount() {
		return belongAccount;
	}

	public void setBelongAccount(String belongAccount) {
		this.belongAccount = belongAccount;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public Integer getPlanNo() {
		return planNo;
	}

	public void setPlanNo(Integer planNo) {
		this.planNo = planNo;
	}

	public PlanStatus getPlanStatus() {
		return planStatus;
	}
	
	public String getPlanStatusName() {
		return planStatus.getName();
	}

	public void setPlanStatus(PlanStatus planStatus) {
		this.planStatus = planStatus;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public PlanOrderStatus getPlanOrderStatus() {
		return planOrderStatus;
	}

	public void setPlanOrderStatus(PlanOrderStatus planOrderStatus) {
		this.planOrderStatus = planOrderStatus;
	}

	public WinStatus getWinStatus() {
		return winStatus;
	}

	public void setWinStatus(WinStatus winStatus) {
		this.winStatus = winStatus;
	}

	public String getContent() {
		return content;
	}

	public String getContentList() {
		if (this.getIsUploadContent() == CommonStatus.YES.getValue()) {
			if (this.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
				return "<a href='" + this.getContent() + "' target='_blank'>下载文件</a>";
			} else {
				return LotteryPlan.genContentList(this.getLotteryType(), this.getContent(),this.getPlayType().getValue()+"");
			}
		} else {
			if (this.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
				return "暂未上传";
			} else {
				return "暂未选号";
			}
		}

	}
	public String getSoftwareList(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String host=request.getServerName();
		String path="http://"+host;
		if (this.getIsUploadContent() == CommonStatus.YES.getValue()) {
			if (this.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
				String countent=this.getContent();
				if(this.getLotteryType().getValue()>=7201 && this.getLotteryType().getValue() <=7207 && countent.startsWith("{")){
					String filepath=countent.split("filePath")[1].split(",")[0].split("\"")[2];
					return path+filepath;
				}else if(this.getLotteryType().getValue()>=7301 && this.getLotteryType().getValue() <=7306){
					if(countent.startsWith("{")){
						String filepath=countent.split("filePath")[1].split(",")[0].split("\"")[2];
						return path+filepath;
					}else{
						String comtent = null;
//						try {
//							comtent = FileTools.getFileContent(WebConstants.WEB_PATH + getContent());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						BasketBallBetFilterContent contentModel = CommonUtils.Object4Json(comtent, BasketBallBetFilterContent.class,
//								BasketBallMatchItem.class);
//						List<String> contentList = contentModel.getContentList();
//						String selectContent = "";
//	
//						for (String temp : contentList) {
//							selectContent += temp;
//							selectContent += "</br>";
//						}
//						return selectContent;
						return null;
					}
				}else{
					//return "<a href='" + path+this.getContent() + "' target='_blank'>下载文件</a>";
					return path+this.getContent();
				}
				
				
			} else {
				return LotteryPlan.genContentList(this.getLotteryType(), this.getContent(), this.getPlayType().getValue()+"");
			}
		} else {
			if (this.getSelectType().getValue() == SelectType.UPLOAD.getValue()) {
				return "暂未上传";
			} else {
				return "暂未选号";
			}
		}
		
	}

	public static void main(String[] args) {
		String countent="{\"conditions\":null,\"contentList\":[],\"filePath\":\"/dsUpFile/120113/BZU39qlirbldxevGrq.txt\",\"isWithBoutIndex\":false,\"matchItems\":[{\"DXF\":0,\"RF\":-11.5,\"intTime\":20120113,\"lineId\":302,\"options\":[],\"shedan\":false},{\"DXF\":0,\"RF\":-2.5,\"intTime\":20120113,\"lineId\":304,\"options\":[],\"shedan\":false},{\"DXF\":0,\"RF\":-7.5,\"intTime\":20120113,\"lineId\":307,\"options\":[],\"shedan\":false},{\"DXF\":0,\"RF\":-9.5,\"intTime\":20120113,\"lineId\":311,\"options\":[],\"shedan\":false}],\"multiple\":1,\"optionMapText\":\"3:3,0:0\",\"passMode\":1,\"passTypes\":[\"P3_1\"]}";
		String filepath=countent.split("filePath")[1].split(",")[0].split("\"")[2];
		System.out.println(filepath);
		//100!103#64,65,66;3:3,1:1,0:0_/dsUpFile/111020/WCVBzvkFsEhCS3yA4a.txt%2&true
		//String countent="{"conditions":null,"contentList":[],"filePath":"/dsUpFile/111027/jgsmdnB94MAUISJKjO.txt","isWithBoutIndex":true,"matchItems":[{"intTime":20111027,"lineId":4,"options":[],"shedan":false},{"intTime":20111027,"lineId":6,"options":[],"shedan":false}],"multiple":1,"optionMapText":"3:3,1:1,0:0","passMode":1,"passTypes":["P2_1"]}";
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public BuyType getBuyType() {
		return buyType;
	}

	public void setBuyType(BuyType buyType) {
		this.buyType = buyType;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public int getReservePart() {
		return reservePart;
	}

	public void setReservePart(int reservePart) {
		this.reservePart = reservePart;
	}

	public Date getDealDateTime() {
		return dealDateTime;
	}

	public void setDealDateTime(Date dealDateTime) {
		this.dealDateTime = dealDateTime;
	}
	
	public PublicStatus getPublicStatus() {
		return publicStatus;
	}
	
	public void setPublicStatus(PublicStatus publicStatus) {
		this.publicStatus = publicStatus;
	}

	public PlanType getPlantype() {
		return plantype;
	}

	public void setPlantype(PlanType plantype) {
		this.plantype = plantype;
	}

	public MemberFollowingType getFollowingType() {
		return followingType;
	}

	public void setFollowingType(MemberFollowingType followingType) {
		this.followingType = followingType;
	}

	public PlanTicketStatus getPlanTicketStatus() {
		return planTicketStatus;
	}

	public void setPlanTicketStatus(PlanTicketStatus planTicketStatus) {
		this.planTicketStatus = planTicketStatus;
	}

	public String getWinNum() {
		return winNum;
	}

	public void setWinNum(String winNum) {
		this.winNum = winNum;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public int getCommision() {
		return commision;
	}

	public void setCommision(int commision) {
		this.commision = commision;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}
	@SuppressWarnings("unchecked")
	public static String genContentList(LotteryType lt, String content,String playType) {

		StringBuffer sb = new StringBuffer("");
		if (LotteryType.JCLQList.contains(lt) || LotteryType.JCZQList.contains(lt)) {
			String selectValue = "";
			if (content != null && content.length() > 0) {
				SportteryBetContentModelTurbid comtent = null;
				if (LotteryType.JCLQList.contains(lt)) {
					
				} else {
					comtent = CommonUtils.Object4JsonTurbid(content, FootBallBetContentTurbid.class, FootBallMatchTurbidItem.class);
				}
				List<MatchItem> matchItemList = comtent.getMatchItems();

				if (comtent.getPassMode() == 0) {
					selectValue = "单关:";
				} else {
					selectValue = "过关:";
				}

				for (int i = 0; i < matchItemList.size(); i++) {
					MatchItem item = matchItemList.get(i);
					List<SportteryOption> optionList = item.getOptions();
					String weekStr = DateTools.getWeekStr(DateTools.StringToDate(item.getIntTime() + "", "yyyyMMdd"));
					selectValue += weekStr + item.getLineId() + "(";
					for (int j = 0; j < optionList.size(); j++) {
						SportteryOption option = optionList.get(j);
						//Integer optType = optionList.get(j).getType()==null||optionList.get(j).getType().equals("")?null: Integer.parseInt( optionList.get(j).getType());
						OptionItem optionItem = CommonUtils.getByPlayType(lt, option);
						selectValue += optionItem.getCommonText() + ",";
					}
					selectValue = selectValue.substring(0, selectValue.length() - 1);
					selectValue += ") ";
				}
			}
			return selectValue;
		} else {
			
			content = content.replaceAll("\r\n", "\n");
			
			String[] lines = content.split("\\\n");
			
			for (int i = 0; i < lines.length; i++) {
				sb.append("").append(PlayType.getItem(Integer.valueOf(playType)).getName()).append(":");
				String peace = lines[i];
				if(i!=lines.length-1){
					sb.append(BallCodeConverter.convert(lt.getValue(), Integer.valueOf(playType), peace)).append("<br>");
				}
				else{
					sb.append(BallCodeConverter.convert(lt.getValue(), Integer.valueOf(playType), peace));
				}
			}
			
		}
		sb.append("");
		return sb.toString();

	}





	public String getNickName() {
		return nickName;
	}





	public void setNickName(String nickName) {
		this.nickName = nickName;
	}





	public Integer getParticipates() {
		return participates;
	}





	public void setParticipates(Integer participates) {
		this.participates = participates;
	}





	public Integer getTotalPart() {
		return totalPart;
	}





	public void setTotalPart(Integer totalPart) {
		this.totalPart = totalPart;
	}





	public Double getPretaxPrize() {
		return pretaxPrize;
	}





	public void setPretaxPrize(Double pretaxPrize) {
		this.pretaxPrize = pretaxPrize;
	}





	public Date getLateDateTime() {
		return lateDateTime;
	}





	public void setLateDateTime(Date lateDateTime) {
		this.lateDateTime = lateDateTime;
	}


	
}
