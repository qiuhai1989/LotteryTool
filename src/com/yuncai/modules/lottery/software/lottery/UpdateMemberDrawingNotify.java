package com.yuncai.modules.lottery.software.lottery;

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
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawing;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.MemberWallet;
import com.yuncai.modules.lottery.model.Oracle.toolType.DrawingStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class UpdateMemberDrawingNotify extends BaseAction implements SoftwareProcess {

	private SoftwareService softwareService;
	private MemberDrawingService memberDrawingService;
	private MemberWalletService memberWalletService;
	private IpSeeker ipSeeker;
	private MemberOperLineService memberOperLineService;
	private MemberService memberService;

	@SuppressWarnings("unchecked")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String account = null;// 用户名
		String drawingId = null;// 提款订单id
		Integer status = null;// 订单状态
		Double formalitiesFees = null;// 手续费
		Integer operLineNO = null;// 操作流水号
		String remark = "无";// 备注
		String version=null;
		MemberDrawing memberDrawing = new MemberDrawing();
		MemberWallet memberWallet = new MemberWallet();
		Member member = new Member();

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element failureList = new Element("failureList");
		// -------------------------------------------------
		// 获取请过的body

		// 获取请过的body
		Element memberDrawingList = bodyEle.getChild("memberDrawingList");
		Element drawingList=memberDrawingList.getChild("drawingList");
		String node = "组装节点不存在";
		try {
			account = memberDrawingList.getChildText("account");
			List<Element> listxml = drawingList.getChildren("drawing");
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			 // 验证登陆
			 Member memberSession = (Member)
			 request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			 if (memberSession == null) {
			 return PackageXml("2000", "请登录!", "9300", agentId);
			 }
			
			 if (!account.equals(memberSession.getAccount())) {
			 return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			 }

			if (!listxml.isEmpty())
				for (Element ele : listxml) {

					Element drawingInfo = new Element("drawingInfo");
					Element drawingIdElement = new Element("drawingId");
					Element errorMessage = new Element("errorMessage");

					drawingId = ele.getChildText("drawingId");
					status = Integer.parseInt(ele.getChildText("status"));
					remark = ele.getChildText("remark") == null ? "无" : ele.getChildText("remark");
					formalitiesFees = ele.getChildText("formalitiesFees") == null ? 0.0 : Double.parseDouble(ele.getChildText("formalitiesFees"));

					memberDrawing = memberDrawingService.findObjectByProperty("id", drawingId);
					if (memberDrawing == null) {
						drawingIdElement.setText(drawingId);
						errorMessage.setText("对不起,提款单号不存在!");
						drawingInfo.addContent(drawingIdElement);
						drawingInfo.addContent(errorMessage);
						failureList.addContent(drawingInfo);
					}

					if (memberDrawing != null && !memberDrawing.getStatus().equals(DrawingStatus.getItem(status))) {

						// 已完成、已驳回、银行退单的订单不支持此操作
						if (memberDrawing.getStatus().equals(DrawingStatus.FAILURE) || memberDrawing.getStatus().equals(DrawingStatus.SUCCESS)|| memberDrawing.getStatus().equals(DrawingStatus.BANKRETURN)) {
							drawingIdElement.setText(drawingId);
							errorMessage.setText("对不起，该订单不支持此操作！");
							drawingInfo.addContent(drawingIdElement);
							drawingInfo.addContent(errorMessage);
							failureList.addContent(drawingInfo);
						} else {

							memberWallet = memberWalletService.findByAccount(memberDrawing.getAccount());
							member = memberService.findByAccount(memberDrawing.getAccount());

							Long ct = 0L;
							try {
								String _s = super.getSession().get("s").toString();
								ct = Long.parseLong(_s);
							} catch (Exception e) {
							}
							operLineNO = addOperLine(account, ct, -1, "", "", "", super.remoteIp, MemberOperType.ALL,version);

							try {
								// 操作钱包
								switch (status) {
								case 0:// 提款成功
									if (memberWallet.getFreezeBalance() < memberDrawing.getAmount()) {
										drawingIdElement.setText(drawingId);
										errorMessage.setText("对不起!冻结金额不足.");
										drawingInfo.addContent(drawingIdElement);
										drawingInfo.addContent(errorMessage);
										failureList.addContent(drawingInfo);
									} else {
										// 修改钱包
										memberWalletService.drawing(member, Double.parseDouble(memberDrawing.getAmount().toString()), -1, null, null,
												operLineNO, 1, null, remark + "，方案:" + drawingId);
										// 更新订单状态
										memberDrawing.setStatus(DrawingStatus.getItem(status));
										memberDrawing.setDealDateTime(new Date());
									}
									break;
								case 1:// 待审核
										// 更新订单状态
									memberDrawing.setStatus(DrawingStatus.getItem(status));
									memberDrawing.setDealDateTime(new Date());
									memberDrawingService.update(memberDrawing);
								case 2:// 已驳回
										// 订单为待审核和审核通过时，才能驳回
									if (memberDrawing.getStatus().equals(DrawingStatus.WAIT)
											|| memberDrawing.getStatus().equals(DrawingStatus.CHECKED)) {
										if (memberWallet.getFreezeBalance() < memberDrawing.getAmount()) {
											drawingIdElement.setText(drawingId);
											errorMessage.setText("对不起!冻结金额不足.");
											drawingInfo.addContent(drawingIdElement);
											drawingInfo.addContent(errorMessage);
											failureList.addContent(drawingInfo);
										} else {
											// 取消冻结
											memberWalletService.freezeToAble(member, 1, Double.parseDouble(memberDrawing.getAmount().toString()), -1,
													null, null, operLineNO, member.getSourceId(), remark + ",方案:" + drawingId,null,new Integer(0),0d);

											// 增加提款额度
											memberWalletService.increaseTakeCashQuota(member,
													Double.parseDouble(memberDrawing.getAmount().toString()), 1);
											// 更新订单状态
											memberDrawing.setStatus(DrawingStatus.getItem(status));
											memberDrawing.setDealDateTime(new Date());
											memberDrawingService.update(memberDrawing);
										}
									} else {
										drawingIdElement.setText(drawingId);
										errorMessage.setText("对不起，该订单不支持此操作！");
										drawingInfo.addContent(drawingIdElement);
										drawingInfo.addContent(errorMessage);
										failureList.addContent(drawingInfo);
									}
									break;
								case 3:// 审核通过 ，待汇款
										// 更新订单状态
									memberDrawing.setStatus(DrawingStatus.getItem(status));
									memberDrawing.setDealDateTime(new Date());
									memberDrawingService.update(memberDrawing);
									break;
								case 4:// 已汇出
										// 更新订单状态
									memberDrawing.setSendDateTime(new Date());
									memberDrawing.setStatus(DrawingStatus.getItem(status));
									memberDrawing.setDealDateTime(new Date());
									memberDrawingService.update(memberDrawing);
									break;
								case 5:// 银行退单
									if (memberWallet.getFreezeBalance() < memberDrawing.getAmount()) {
										drawingIdElement.setText(drawingId);
										errorMessage.setText("对不起!冻结金额不足.");
										drawingInfo.addContent(drawingIdElement);
										drawingInfo.addContent(errorMessage);
										failureList.addContent(drawingInfo);
									} else {
										// 取消冻结
										memberWalletService.freezeToAble(member, 1, Double.parseDouble(memberDrawing.getAmount().toString()), -1,
												null, null, operLineNO, member.getSourceId(), remark + ",方案:" + drawingId,null,new Integer(0),0d);

										// 增加提款额度
										memberWalletService
												.increaseTakeCashQuota(member, Double.parseDouble(memberDrawing.getAmount().toString()), 1);

										// 更新订单状态
										memberDrawing.setStatus(DrawingStatus.getItem(status));
										memberDrawing.setDealDateTime(new Date());
										memberDrawing.setFormalitiesFees(formalitiesFees);
										memberDrawingService.update(memberDrawing);
									}
									break;
								default:
									drawingIdElement.setText(drawingId);
									errorMessage.setText("无法识别订单状态。");
									drawingInfo.addContent(drawingIdElement);
									drawingInfo.addContent(errorMessage);
									failureList.addContent(drawingInfo);
									break;
								}

							} catch (Exception e) {
								drawingIdElement.setText(drawingId);
								errorMessage.setText(e.getMessage());
								drawingInfo.addContent(drawingIdElement);
								drawingInfo.addContent(errorMessage);
								failureList.addContent(drawingInfo);
							}
						}
					}
				}

			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText("操作成功");

				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(failureList);
				return softwareService.toPackage(myBody, "8012", agentId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String message = "";
				String errStatus = "200";
				message = node;
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
			MemberOperType operType,String version) {
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
			// memberOperLineDao.saveOrUpdate(operLine);
			memberOperLineService.save(operLine);
			return operLine.getOperLineNo();
		} catch (Exception e) {
			return null;
		}
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setMemberDrawingService(MemberDrawingService memberDrawingService) {
		this.memberDrawingService = memberDrawingService;
	}

	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

	public void setMemberOperLineService(MemberOperLineService memberOperLineService) {
		this.memberOperLineService = memberOperLineService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
