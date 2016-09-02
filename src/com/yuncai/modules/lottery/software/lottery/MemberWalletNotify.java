	package com.yuncai.modules.lottery.software.lottery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.core.util.DBHelper;
import com.yuncai.core.util.EmailUtil;
import com.yuncai.core.util.HttpUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.dao.oracleDao.member.MemberOperLineDAO;
import com.yuncai.modules.lottery.model.Oracle.EmailInfo;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawing;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawingLine;
import com.yuncai.modules.lottery.model.Oracle.MemberInfo;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.BuyType;
import com.yuncai.modules.lottery.model.Oracle.toolType.DrawingStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.EmailType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Sql.Sms;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.oracleService.member.EmailInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.EmailNoticeTypeService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.user.SmsService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberWalletNotify extends BaseAction implements SoftwareProcess {

	private MemberService memberService;
	private MemberWalletService memberWalletService;
	private SoftwareService softwareService;
	private LotteryPlanService lotteryPlanService;
	private IpSeeker ipSeeker;
	private MemberOperLineService memberOperLineService;
	private MemberDrawingService memberDrawingService;
	private MemberInfoService memberInfoService;
	private MemberDrawingLineService memberDrawingLineService;
	private MemberOperLineDAO memberOperLineDao;
	private MemberDrawingService membDrawingService;
	private EmailInfoService emailInfoService;
	private EmailNoticeTypeService emailNoticeTypeService;
	private SmsService sqlSmsService;
	public void setMemberOperLineDao(MemberOperLineDAO memberOperLineDao) {
		this.memberOperLineDao = memberOperLineDao;
	}

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String userName = null;// 用户名
		String amount = null;// 金额
		String type = null;// 操作类型
		Integer lotteryType = -1;// 彩种
		Integer planNO = null;// 方案编号
		Integer orderNO = null;// 方案编号
		Integer operLineNO = null;// 操作流水号
		Integer walletType = null;// 钱包类型
		String tradeNO = null; // 针对：提款、追号执行的单号
		String remark = "无";// 备注
		Integer tradeType = -1; // 交易参数

		String phoneNo;// 机身码
		String sim;// SIM卡
		String model;// 手机机型
		String systemVersion;// 系统版本

		String channel;// 渠道ID
		
		String realName ;//账号注册真实姓名
		String version=null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element memberDetail = new Element("memberDetail");
		Element ableBalance = new Element("ableBalance");
		Element freezeBalance = new Element("freezeBalance");
		Element takeCashQuota = new Element("takeCashQuota");
		Element ableScore = new Element("ableScore");
		// -------------------------------------------------
		LogUtil.out("平台："+agentId+",发送提款申请请求");
		// 获取请过的body
		Element wallet = bodyEle.getChild("MemberWallet");
		String node = "组装节点不存在";
		try {
			userName = wallet.getChildText("account");
			amount = wallet.getChildText("amount");
			type = wallet.getChildText("transType");
			// walletType = Integer.parseInt(wallet.getChildText("walletType"));
			try {
				planNO = wallet.getChildText("planNO") == null ? null : Integer.parseInt(wallet.getChildText("planNO"));
			} catch (Exception e) {
			}
			try {
				orderNO = wallet.getChildText("orderNO") == null ? null : Integer.parseInt(wallet.getChildText("orderNO"));
			} catch (Exception e) {
			}
			try {
				lotteryType = wallet.getChildText("lotteryType") == null ? -1 : Integer.parseInt(wallet.getChildText("lotteryType"));
			} catch (Exception e) {
			}
			try {
				tradeNO = wallet.getChildText("tradeNO");
			} catch (Exception e) {
			}
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			tradeType = Integer.parseInt(wallet.getChildText("tradeType"));

			remark = wallet.getChildText("remark") == null ? "无" : wallet.getChildText("remark");

			node = null;

			phoneNo = StringTools.isNullOrBlank(bodyEle.getChildText("phoneNo")) ? null : bodyEle.getChildText("phoneNo");
			sim = StringTools.isNullOrBlank(bodyEle.getChildText("sim")) ? null : bodyEle.getChildText("sim");
			model = StringTools.isNullOrBlank(bodyEle.getChildText("model")) ? null : bodyEle.getChildText("model");
			systemVersion = StringTools.isNullOrBlank(bodyEle.getChildText("systemVersion")) ? null : bodyEle.getChildText("systemVersion");
			channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ? null :bodyEle.getChildText("channel") ;

			realName = StringTools.isNullOrBlank(wallet.getChildText("realName"))?null:wallet.getChildText("realName");
//			certNo = StringTools.isNullOrBlank(wallet.getChildText("certNo"))?null:wallet.getChildText("certNo");
			
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}

			Member member = new Member();
			MemberWallet memberWallet = new MemberWallet();
			// 判断用户是否存在
			if (userName != null && !"".equals(userName)) {
				List<Member> list = memberService.findByProperty("account", userName);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8005", agentId);
				}

				member = list.get(0);
				memberWallet = memberWalletService.findByAccount(member.getAccount());
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}

			Long ct = 0L;
			try {
				try {
					String _s = super.getSession().get("s").toString();
					ct = Long.parseLong(_s);
				} catch (Exception e) {
				}

				if (amount != null && !("".equals(amount)) && Double.parseDouble(amount) > 0) {
					Integer i = (type == null || "".equals(type) ? 0 : Integer.parseInt(type));

					// 处理业务
					switch (i) {
					// case 1://充值
					// 获取操作记录编号
					// operLineNO= addOperLine(member.getAccount(), ct,
					// member.getSourceId(), "", "", "",
					// super.remoteIp,MemberOperType.ADD_MONEY);

					// //增加可用余额
					// memberWalletService.increaseAble(member,
					// Double.parseDouble(amount), lotteryType, planNO, orderNO,
					// operaLineNO, 1,WalletTransType.CHARGE.getValue(), 6636,
					// "充值");
					// //增加提款额度
					// memberWalletService.increaseTakeCashQuota(member,
					// Double.parseDouble(amount), 1);
					case 2:// 提款

						if(memberSession.getRecommender() == 1){
							return PackageXml("9001", "限制提款用户，请联系客服。", "9300", agentId);
						}
						
						// 获取操作记录编号
						operLineNO = addOperLine(member.getAccount(), ct, member.getSourceId(), "", "", "", super.remoteIp, MemberOperType.GET_MONEY,
								phoneNo, sim, model, systemVersion, super.channelID,version);
						
						if(realName.trim()==null&&!member.getName().equalsIgnoreCase(realName.trim())){
							super.errorMessage = "姓名与账户信息中不一致";
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}
//						if(certNo.trim()==null&&!member.getCertNo().equalsIgnoreCase(certNo.trim())){
//							super.errorMessage = "身份证信息与账户信息中不一致";
//							return PackageXml("002", super.errorMessage, "8005", agentId);
//						}
						//RECOMMENDER	N	NUMBER(11)	Y			是否限制提款：0否，1是
						if(member.getRecommender()!=0){
							super.errorMessage = "限制提款用户，请联系客服";
							System.out.println(super.errorMessage);
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}						
						
						if (memberWallet.getTakeCashQuota() < Double.parseDouble(amount)) {
							super.errorMessage = "对不起，提款金额不可超过提款额度！";
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}

						// 获取银行绑定信息
						MemberInfo memberInfo = memberInfoService.findByProperty("account", userName).get(0);
						if (memberInfo.getBank() == null || memberInfo.getBank().equals("")) {
							super.errorMessage = "对不起，该用户尚未绑定银行卡！";
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}

						// 创建提款记录
						MemberDrawing memberDrawing = new MemberDrawing();
						memberDrawing.setAccount(userName);
						memberDrawing.setAmount(Float.parseFloat(amount));
						memberDrawing.setBank(memberInfo.getBank());
						memberDrawing.setBankCard(memberInfo.getBankCard());
						memberDrawing.setCreateDateTime(new java.util.Date());
						memberDrawing.setPartBank(memberInfo.getBankPart());
						// 如果该用户不允许提款，则自动驳回
						if (member.getRecommender() == 0) {
							memberDrawing.setStatus(DrawingStatus.WAIT);
						} else if (member.getRecommender() == 1) {
							memberDrawing.setStatus(DrawingStatus.FAILURE);
						}
//						memberDrawing.setStatus(DrawingStatus.WAIT);
						memberDrawing.setFormalitiesFees(0.0);
						memberDrawing.setChannel(super.channelID);
						memberDrawingService.save(memberDrawing);
						
						
						// 创建提款流水
						MemberDrawingLine memberDrawingLine = new MemberDrawingLine();
						memberDrawingLine.setAccount(userName);
						memberDrawingLine.setCreateDateTime(new java.util.Date());
						memberDrawingLine.setDrawingId(memberDrawing.getId());
						memberDrawingLine.setRemark("申请提款");
						memberDrawingLine.setStatus(DrawingStatus.WAIT);
						memberDrawingLineService.save(memberDrawingLine);
						LogUtil.out("用户：" + member.getAccount()+"于："+memberDrawing.getCreateDateTime()+"发起提款申请(单号："+memberDrawing.getId());						
						
						// 如果该用户不允许提款，则添加驳回流水
						if (member.getRecommender() == 1) {
							// 创建提款流水
							MemberDrawingLine memberDrawingLine2 = new MemberDrawingLine();
							memberDrawingLine2.setAccount(userName);
							memberDrawingLine2.setCreateDateTime(new java.util.Date());
							memberDrawingLine2.setDrawingId(memberDrawing.getId());
							memberDrawingLine2.setRemark("已驳回");
							memberDrawingLine2.setStatus(DrawingStatus.FAILURE);
							memberDrawingLineService.save(memberDrawingLine2);
						} else{

							// 冻结金额
							memberWalletService.freeze(member, Double.parseDouble(amount), lotteryType, planNO, orderNO, operLineNO, 1,
									member.getSourceId(), "申请提款，方案:" + memberDrawing.getId(),null,new Integer(0));
							// 减少提款额度
							memberWalletService.increaseTakeCashQuota(member, -Double.parseDouble(amount), 1);

							
							LogUtil.out("准备发送提款审核通知邮件"+agentId);
							try {
								String names[] = new String[2];
								names[0] = "type";
								names[1] = "effective";

								Object values[] = new Object[2];
								if(emailNoticeTypeService.findByProperty("name", "提款审核").size()>0)
									values[0] = emailNoticeTypeService.findByProperty("name", "提款审核").get(0).getValue();
								values[1] = 1;
								List<EmailInfo> emailList = emailInfoService.findByPropertys(names, values);

								Object values2[] = new Object[2];
								values2[0] = -1;
								values2[1] = 1;
								List<EmailInfo> emailList2 = emailInfoService.findByPropertys(names, values2);
								emailList.addAll(emailList2);

								// 格式化当前时间
								DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
								String dateStr = df.format(memberDrawing.getCreateDateTime());

								String title = "处理用户提款(审核)通知";
								String content = ("申请时间：" + dateStr + "<br />提款流水号： " + memberDrawing.getId() + "<br />用户名：" + member.getAccount()
										+ "<br />昵称：" + member.getNickName() + "<br />提款金额：" + amount + " 元"+"<br/><br/><span style='color:red'>摘要："+ DrawingStatus.map.get(memberDrawing.getStatus().getValue()) +"</span>");
								String content1 = "值班经理，用户"+member.getName()+"于"+dateStr+"发起提款申请(单号："+memberDrawing.getId()+"),请尽快审核【云彩】";
								LogUtil.out("处理用户提款(审核)通知开始");
								for (EmailInfo email : emailList) {
									if (email.getEmail()==1){// 发送email
										if(DBHelper.isNoNull(email.getAddress())){
											EmailUtil.sendEmail(email.getAddress(), title, content, "text/html;charset=utf-8");
											LogUtil.out("处理用户提款(审核)发送email:"+email.getAddress());
										}
									}
									boolean tag=false;
									if(email.getSms()==1){// 发送短信
										if(DBHelper.isNoNull(email.getMobile())){
											tag = HttpUtil.SmsSend(email.getMobile(),content1,2);
											LogUtil.out("处理用户提款(审核)发送短信:"+email.getMobile());
										}
									}
									if(email.getMas()==1){// 发送mas
										if(DBHelper.isNoNull(email.getMobile())){
											tag = HttpUtil.SmsSend(email.getMobile(),content1,1);
											LogUtil.out("处理用户提款(审核)发送mas:"+email.getMobile());
										}
									}
									LogUtil.out("处理用户提款(审核)通知单个结束");
									if (tag) {
											Sms sms = new Sms();
											sms.setSiteId(1);
											sms.setSmsid(-1);
											sms.setIsSent(true);
											sms.setDateTime(new Date());
											sms.setTo(email.getMobile());
											sms.setContent(content);
											sqlSmsService.save(sms);
									}
								}
								LogUtil.out("处理用户提款(审核)通知全部结束");
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
							
							// 发送提款审核通知邮件
//							try {
//								String names[] = new String[2];
//								names[0] = "type";
//								names[1] = "enable";
//
//								Object values[] = new Object[2];
//								values[0] = EmailType.TKSH;
//								values[1] = true;
//								List<EmailInfo> emailList = emailInfoService.findByPropertys(names, values);
//
//								Object values2[] = new Object[2];
//								values2[0] = EmailType.ALL;
//								values2[1] = true;
//								List<EmailInfo> emailList2 = emailInfoService.findByPropertys(names, values2);
//								emailList.addAll(emailList2);
//
//								// 格式化当前时间
//								DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//								String dateStr = df.format(memberDrawing.getCreateDateTime());
//
//								String title = "处理用户提款(审核)通知";
//								String content = ("申请时间：" + dateStr + "<br />提款流水号： " + memberDrawing.getId() + "<br />用户名：" + member.getAccount()
//										+ "<br />昵称：" + member.getNickName() + "<br />提款金额：" + amount + " 元");
//
//								for (EmailInfo email : emailList) {
//									EmailUtil.sendEmail(email.getAddress(), title, content, "text/html;charset=utf-8");
//								}
//
//							} catch (Exception e) {
//
//							}
//
//						
//						}
						
						
						//新增用户操作
						
						MemberOperLine operLine = new MemberOperLine();
						operLine.setAccount(member.getAccount());
						LogUtil.out("account: " + member.getAccount());
						operLine.setTerminalId(ct);
						operLine.setSourceId(member.getSourceId());
						operLine.setExtendInfo("");
						operLine.setCreateDateTime(new Date());
						operLine.setReferer("");
						operLine.setUrl("");
						operLine.setIp("");
						operLine.setOperType(MemberOperType.GET_MONEY);
						operLine.setPhoneNo(phoneNo);
						operLine.setModel(model);
						operLine.setSim(sim);
						operLine.setSystemVersion(systemVersion);
						operLine.setChannel(super.channelID);
						operLine.setStatus(OperLineStatus.SUCCESS);
						
						memberOperLineService.save(operLine);
						

//						// 冻结金额
//						memberWalletService.freeze(member, Double.parseDouble(amount), lotteryType, planNO, orderNO, operLineNO, 1, member
//								.getSourceId(), "申请提款，方案:tradeNO:" + memberDrawing.getId());
//						// 减少提款额度
//						memberWalletService.increaseTakeCashQuota(member, -Double.parseDouble(amount), 1);
						break;
					case 3:// 提款成功
						MemberOperType operTypeIn = null;
						if (tradeType == 1) {
							operTypeIn = MemberOperType.GET_MONEY;
							MemberDrawing memberDrawing2 = memberDrawingService.findObjectByProperty("id", tradeNO);
							member = memberService.findByAccount(memberDrawing2.getAccount());
						}

						else if (tradeType == 2)
							operTypeIn = MemberOperType.CHASE_CANCEL;
						operLineNO = addOperLine(member.getAccount(), ct, member.getSourceId(), "", "", "", super.remoteIp, operTypeIn, phoneNo, sim,
								model, systemVersion, super.channelID,version);
						if (memberWallet.getFreezeBalance() < Double.parseDouble(amount)) {
							super.errorMessage = "对不起，冻结金额不足！";
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}

						if (tradeType == 1) {
							memberWalletService.drawing(member, Double.parseDouble(amount), lotteryType, planNO, orderNO, operLineNO, 1, null, remark
									+ "，方案:" + tradeNO);
						} else if (tradeType == 2) {
							memberWalletService.drawing(member, Double.parseDouble(amount), lotteryType, planNO, orderNO, operLineNO, 1, null, remark
									+ "，方案:" + tradeNO);
						}

						break;
					case 4:// 提款失败
						MemberOperType resuntTypeIN = null;
						if (tradeType == 1) {
							resuntTypeIN = MemberOperType.RETURN_MONEY;
							MemberDrawing memberDrawing2 = memberDrawingService.findObjectByProperty("id", tradeNO);
							member = memberService.findByAccount(memberDrawing2.getAccount());
						} else if (tradeType == 2)
							resuntTypeIN = MemberOperType.CHASE_CANCEL;
						operLineNO = addOperLine(member.getAccount(), ct, member.getSourceId(), "", "", "", super.remoteIp, resuntTypeIN, phoneNo,
								sim, model, systemVersion, super.channelID,version);
						if (memberWallet.getFreezeBalance() < Double.parseDouble(amount)) {
							super.errorMessage = "对不起，冻结金额不足！";
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}
						if (tradeType == 1) {
							// 取消冻结
							memberWalletService.freezeToAble(member, 1, Double.parseDouble(amount), lotteryType, planNO, orderNO, operLineNO, member
									.getSourceId(), remark + ",方案:" + tradeNO,null,new Integer(0),0d);
						} else if (tradeType == 2) {
							// 取消冻结
							
							Integer buyType=BuyType.BAODI.getValue();
							memberWalletService.freezeToAble(member, 1, Double.parseDouble(amount), lotteryType, planNO, orderNO, operLineNO, member
									.getSourceId(), remark + ",方案:" + tradeNO,buyType,new Integer(0),0d);
						}
						// 增加提款额度
						if (tradeType == 1)
							memberWalletService.increaseTakeCashQuota(member, Double.parseDouble(amount), 1);

						break;
					case 5:// 返奖
						operLineNO = addOperLine(member.getAccount(), ct, member.getSourceId(), "", "", "", super.remoteIp,
								MemberOperType.RETURN_PRIZE, phoneNo, sim, model, systemVersion, super.channelID,version);
						if (lotteryType == -1) {
							super.errorMessage = "对不起，彩种错误！";
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}
						if (planNO != null && lotteryPlanService.findByProperty("planNo", planNO) != null) {
							// 增加可用余额
							memberWalletService.increaseAble(member, Double.parseDouble(amount), lotteryType, planNO, orderNO, operLineNO, 1,
									WalletTransType.RETURN_PRIZE.getValue(), Integer.parseInt(agentId), "返奖");
							// 增加提款额度
							memberWalletService.increaseTakeCashQuota(member, Double.parseDouble(amount), 1);
						} else {
							super.errorMessage = "方案号错误或不存在！";
							return PackageXml("002", super.errorMessage, "8005", agentId);
						}
						break;
					default:
						super.errorMessage = "操作类型出错!";
						return PackageXml("002", super.errorMessage, "8005", agentId);
					}
				} else {
					super.errorMessage = "金额不能为空或小于0!";
					return PackageXml("002", super.errorMessage, "8005", agentId);
				}
			} catch (ServiceException en) {
				en.printStackTrace();
				return PackageXml(en.getErrorCode(), en.getErrorMesg(), "9300", agentId);
			} catch (Exception e) {
				e.printStackTrace();
				return PackageXml("1", e.getMessage(), "9300", agentId);
			}

			memberSession.setWallet(this.memberWalletService.findByAccount(memberSession.getAccount()));

			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText("操作成功");

				ableBalance.setText(memberSession.getWallet().getAbleBalance().toString());
				freezeBalance.setText(memberSession.getWallet().getFreezeBalance().toString());
				
				double cashQuota=memberSession.getWallet().getTakeCashQuota();
				int IntCashQuota=(int)cashQuota;
				takeCashQuota.setText(IntCashQuota+"");
				//takeCashQuota.setText(memberSession.getWallet().getTakeCashQuota().toString());
				ableScore.setText(memberSession.getScore().getAbleScore().toString());
				memberDetail.addContent(ableBalance);
				memberDetail.addContent(freezeBalance);
				memberDetail.addContent(takeCashQuota);
				memberDetail.addContent(ableScore);

				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(memberDetail);
				return softwareService.toPackage(myBody, "8005", agentId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String message = "";
				String errStatus = "200";
				if (node != null)
					message = node;
				else {
					message = e.getMessage();
					errStatus = "1";
				}

				return PackageXml(errStatus, message, "9300", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;

	}

	public String PackageXml(String CodeEle, String message, String type, String agentId) throws Exception {
		// ----------------新建包体--------------------
		String returnContent = "";
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		responseCodeEle.setText(CodeEle);
		responseMessage.setText(message);
		myBody.addContent(responseCodeEle);
		myBody.addContent(responseMessage);
		returnContent = this.softwareService.toPackage(myBody, type, agentId);
		return returnContent;
	}

	// 用户操作记录
	public Integer addOperLine(String account, Long ct, Integer sourceId, String extendInfo, String referer, String url, String ip,
			MemberOperType operType, String phoneNo, String sim, String model, String systemVersion, String channel,String version) {
		try {
			MemberOperLine operLine = new MemberOperLine();
			operLine.setAccount(account);
			LogUtil.out("account: " + account);
			operLine.setTerminalId(ct);
			operLine.setSourceId(sourceId);
			operLine.setExtendInfo(extendInfo + "");
			operLine.setCreateDateTime(new java.util.Date());
			operLine.setReferer("");
			operLine.setUrl("");
			operLine.setIp(ip);
			String country = ipSeeker.getCountry(ip);
			String area = ipSeeker.getArea(ip);
			String fromPlace = country + area;
			operLine.setFromPlace(fromPlace);
			operLine.setOperType(operType);
			operLine.setVersion(version);
			operLine.setStatus(OperLineStatus.SUCCESS);
			operLine.setChannel(channel);

			// 新增部分手机信息字段

			// memberOperLineDao.saveOrUpdate(operLine);
			memberOperLineService.save(operLine);
			return operLine.getOperLineNo();
		} catch (Exception e) {
			return null;
		}
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}

	public void setMemberDrawingService(MemberDrawingService memberDrawingService) {
		this.memberDrawingService = memberDrawingService;
	}

	public void setMemberInfoService(MemberInfoService memberInfoService) {
		this.memberInfoService = memberInfoService;
	}

	public void setMemberDrawingLineService(MemberDrawingLineService memberDrawingLineService) {
		this.memberDrawingLineService = memberDrawingLineService;
	}

	public void setEmailInfoService(EmailInfoService emailInfoService) {
		this.emailInfoService = emailInfoService;
	}

	public void setMembDrawingService(MemberDrawingService membDrawingService) {
		this.membDrawingService = membDrawingService;
	}

	public EmailNoticeTypeService getEmailNoticeTypeService() {
		return emailNoticeTypeService;
	}

	public void setEmailNoticeTypeService(EmailNoticeTypeService emailNoticeTypeService) {
		this.emailNoticeTypeService = emailNoticeTypeService;
	}

	public SmsService getSqlSmsService() {
		return sqlSmsService;
	}

	public void setSqlSmsService(SmsService sqlSmsService) {
		this.sqlSmsService = sqlSmsService;
	}

}
