package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawing;
import com.yuncai.modules.lottery.model.Oracle.MemberDrawingLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.DrawingStatus;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberDrawingService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

import sun.net.www.content.audio.basic;

public class MemberDrawingNotify extends BaseAction implements SoftwareProcess {
	private MemberDrawingService memberDrawingService;
	private SoftwareService softwareService;
	private MemberDrawingLineService memberDrawingLineService;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String account = null;// 操作员用户名
		String drawingId = null;// 提款单号
		Integer status = null;// 操作类型
		String remark=null;
		Double formalitiesFees = 0.0;// 手续费
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		// 获取请过的body
		Element drawing = bodyEle.getChild("drawing");
		String node = "组装节点不存在,或参数不正确";
		try {
			account=drawing.getChildText("account");
			drawingId = drawing.getChildText("drawingId");
			status = Integer.parseInt(drawing.getChildText("status"));
			formalitiesFees = drawing.getChildText("formalitiesFees")==null?0.0:Double.parseDouble(drawing.getChildText("formalitiesFees"));
			remark=drawing.getChildText("remark")==null?"":drawing.getChildText("remark");
			node = null;

			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!account.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}

			// 查找提款单
			List<MemberDrawing> list = memberDrawingService.findByProperty("id", drawingId);
			if (list == null || list.isEmpty()) {
				super.errorMessage = "此提款单不存在!";
				return PackageXml("002", super.errorMessage, "8011", agentId);
			}

			// 更新提款单
			MemberDrawing memberDrawing = list.get(0);
			memberDrawing.setStatus(DrawingStatus.getItem(status));
			memberDrawing.setFormalitiesFees(formalitiesFees);
			memberDrawing.setDealDateTime(new Date());
			//如果状态为已汇，更新出时间
			if (status.equals(0))
				memberDrawing.setSendDateTime(new Date());

			try {
				memberDrawingService.update(memberDrawing);
			} catch (Exception e) {
				e.printStackTrace();
				return PackageXml("1", e.getMessage(), "9300", agentId);
			}
			
			MemberDrawingLine memberDrawingLine=new MemberDrawingLine();
			memberDrawingLine.setAccount(account);
			memberDrawingLine.setCreateDateTime(new Date());
			memberDrawingLine.setDrawingId(drawingId);
			memberDrawingLine.setRemark(remark);
			memberDrawingLine.setStatus(DrawingStatus.getItem(status));
			
			try {
				memberDrawingLineService.save(memberDrawingLine);
			} catch (Exception e) {
				e.printStackTrace();
				return PackageXml("1", e.getMessage(), "9300", agentId);
			}
			
			// 封装xml
			{
				responseCodeEle.setText("0");
				responseMessage.setText("操作成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				return softwareService.toPackage(myBody, "8011", agentId);
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

	public void setMemberDrawingService(MemberDrawingService memberDrawingService) {
		this.memberDrawingService = memberDrawingService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setMemberDrawingLineService(MemberDrawingLineService memberDrawingLineService) {
		this.memberDrawingLineService = memberDrawingLineService;
	}
	
	
}
