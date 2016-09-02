package com.yuncai.modules.lottery.software.lottery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.AccountTrace;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;
import com.yuncai.modules.lottery.service.oracleService.member.AccountTraceService;
import com.yuncai.modules.lottery.service.oracleService.member.PagesInfoService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;

public class AccountTraceNotify extends BaseAction implements SoftwareProcess {

	private AccountTraceService accountTraceService;
	private PagesInfoService pagesInfoService;
	private IpSeeker ipSeeker;
	
	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		String pageId = "-1";// 页面ID
		// -------------------------------------------------
		// 获取请过的body
		Element trace = bodyEle.getChild("trace");

		try {
			// channel = super.channelID;
			//
			pageId = trace.getChildText("pageId");
			// if(pageId.equals("")){
			// return null;
			// }
			// PagesInfo pagesInfo = pagesInfoService.findByProperty("pageId",
			// pageId).get(0);
			//
			// if(pagesInfo == null){
			// return null;
			// }
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			// String ip = super.remoteIp;
			//
			// // String sessionId = (String)
			// request.getSession().getAttribute(Constant.MEMBER_TRACE_SESSION);
			// // if (sessionId == null || sessionId.equals("")) {
			// // // 获取随机数
			// // sessionId = sdf.format(new Date()) + generateString(8);
			// //
			// request.getSession().setAttribute(Constant.MEMBER_TRACE_SESSION,
			// sessionId);
			// // }
			//
			// Member member = (Member)
			// request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			// if (member != null) {
			// account = member.getAccount();
			// }
			//
			// AccountTrace accountTrace = new AccountTrace();
			// accountTrace.setAccount(account);
			// accountTrace.setChannel(channel);
			// accountTrace.setCreateDateTime(new Date());
			// accountTrace.setIp(ip);
			// accountTrace.setLotteryType(pagesInfo.getLotteryType().getValue());
			// accountTrace.setPageId(pageId);
			// accountTrace.setRoot(pagesInfo.getIsRoot());
			// accountTrace.setSessionId(request.getSession().getId());
			//
			// accountTraceService.save(accountTrace);
			//

			if (pageId != null && !pageId.equals("")) {
				String phoneNo;// 机身码
				// String sim;// SIM卡
				String model;// 手机机型
				String systemVersion;// 系统版本
				String channel;// 渠道ID
				String softWareversion;// 软件版本

				phoneNo = StringTools.isNullOrBlank(bodyEle.getChildText("phoneNo")) ? null : bodyEle.getChildText("phoneNo");
				// sim = StringTools.isNullOrBlank(bodyEle.getChildText("sim"))
				// ? null : bodyEle.getChildText("sim");
				model = StringTools.isNullOrBlank(bodyEle.getChildText("model")) ? null : bodyEle.getChildText("model");
				systemVersion = StringTools.isNullOrBlank(bodyEle.getChildText("systemVersion")) ? null : bodyEle.getChildText("systemVersion");
				channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ? null : bodyEle.getChildText("channel");
				softWareversion = StringTools.isNullOrBlank(bodyEle.getChildText("version")) ? null : bodyEle.getChildText("version");
				try {
					PagesInfo pagesInfo = pagesInfoService.findByProperty("pageId", pageId).get(0);

					AccountTrace accountTrace = new AccountTrace();
					accountTrace.setBrowser("");
					accountTrace.setChannel(super.channelID);
					accountTrace.setCreateDateTime(new Date());
					accountTrace.setIp(super.remoteIp);
					accountTrace.setPageId(pageId);
					accountTrace.setLotteryType(pagesInfo.getLotteryType().getValue());
					accountTrace.setRoot(pagesInfo.getIsRoot());
					accountTrace.setOperator(ipSeeker.getCountry(super.remoteIp) + ipSeeker.getArea(super.remoteIp));
					accountTrace.setSessionId(request.getSession().getId());
					accountTrace.setSystemVersion(systemVersion);
					accountTrace.setModel(model);
					accountTrace.setSoftwareVersion(softWareversion);
					accountTrace.setTerminalId(phoneNo);
					// accountTrace.setAccount("");
					Member member = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
					if (member != null) {
						String account = member.getAccount();
						accountTrace.setAccount(account);
					}
					accountTraceService.save(accountTrace);
					// System.out.println(accountTrace.getPageId());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	// public String generateString(int length) // 参数为返回随机数的长度
	// {
	// String allChar =
	// "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	// StringBuffer sb = new StringBuffer();
	// Random random = new Random();
	// for (int i = 0; i < length; i++) {
	// sb.append(allChar.charAt(random.nextInt(allChar.length())));
	// }
	// return sb.toString();
	// }

	public AccountTraceService getAccountTraceService() {
		return accountTraceService;
	}

	public void setAccountTraceService(AccountTraceService accountTraceService) {
		this.accountTraceService = accountTraceService;
	}

	public void setPagesInfoService(PagesInfoService pagesInfoService) {
		this.pagesInfoService = pagesInfoService;
	}

	public void setIpSeeker(IpSeeker ipSeeker) {
		this.ipSeeker = ipSeeker;
	}

}
