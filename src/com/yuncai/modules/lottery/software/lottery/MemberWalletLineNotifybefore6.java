package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletLineService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class MemberWalletLineNotifybefore6 extends MemberWalletLineNotify {
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// TODO Auto-generated method stub
		String account = null;
		Date time = null;
		Integer transType = null;
		Integer lineNo =null;
		Integer order = null;
		Integer pageSize = null;
		Date date = null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		
		
		Element details = new Element("details");
		Element revenue = new Element("revenue");
		Element expend = new Element("expend");
		Element win = new Element("win");
		Element rows = new Element("rows");
		

		
		// -------------------------------------------------
		String node = "组装节点不存在";
		try {	
			account = bodyEle.getChildText("account");
			time =  DateTools.StringToDate(bodyEle.getChildText("time"),"yyyy-MM");
			transType = Integer.parseInt( bodyEle.getChildText("transType")) ;
			lineNo = StringTools.isNullOrBlank(bodyEle.getChildText("lineNo"))?0:Integer.parseInt(bodyEle.getChildText("lineNo"));
			order = StringTools.isNullOrBlank(bodyEle.getChildText("order"))?0:Integer.parseInt(bodyEle.getChildText("order"));
			pageSize = StringTools.isNullOrBlank(bodyEle.getChildText("pageSize"))?0:Integer.parseInt(bodyEle.getChildText("pageSize"));
			date = StringTools.isNullOrBlank(bodyEle.getChildText("date"))?null:DateTools.StringToDate(bodyEle.getChildText("date"));
			
			node=null;
			//验证登陆
			Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
			  return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if(!account.equals(memberSession.getAccount())){
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			Member member=new Member();
			// 判断用户是否存在
			if (account != null && !"".equals(account)) {
				List<MemberScore> list = memberScoreService.findByProperty(
						"account", account);
				List<Member> listm=memberService.findByProperty("account", account);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8007",
							agentId);
				}
				member=listm.get(0);
			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8007", agentId);
			}
			
			
			Date startDate = null;
			Date endDate = null;
			// 根据给定的date对象获取到Caledar对象后获取到
			Calendar c = Calendar.getInstance();
			c.setTime(time);
			c.set(Calendar.DAY_OF_MONTH, 1);
			startDate = c.getTime();
			// 该月最后一天
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			c.add(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.MILLISECOND, -1);
			endDate = c.getTime();


			
			List<WalletTransType> transTypes = null;
			switch (transType) {
			case 0:
				transTypes = null;
				break;
			case 1:
				transTypes = WalletTransType.incomeList;
				break;
			case 2:
				transTypes = WalletTransType.outTypeList;
				break;				
			}
			
			List<MemberWalletLine> list = memberWalletLineService.findByAccountAndDate(account,startDate,endDate,order,pageSize,date, transTypes);
			//总收入
			Double sumIn = memberWalletLineService.countAmount(account,startDate,endDate,WalletTransType.incomeList);
			if(sumIn==null){
				sumIn = 0.0;
			}
			//总支出
			Double sumOut = memberWalletLineService.countAmount(account,startDate,endDate,WalletTransType.outTypeList);
			if(sumOut==null){
				sumOut=0.0;
			}
			//中奖收入
			Double winIn = memberWalletLineService.countAmount(account,startDate,endDate,WalletTransType.prizeTypeList);
			if(winIn==null){
				winIn=0.0;
			}
			
			
			//开始组装xml
			responseCodeEle.setText("0");  //上传成功
			responseMessage.setText("查询成功");
			revenue.setText(Double.toString(sumIn));
			expend.setText(Double.toString(sumOut));
			win.setText(Double.toString(winIn));
			
			for(MemberWalletLine mWalletLine:list){
				Element row = new Element("row");
				Element lineNoEle = new Element("lineNo");
//				LogUtil.out(mWalletLine.getWalletLineNo());
				lineNoEle.setText(mWalletLine.getWalletLineNo()+"");
				Element transTypeEle = new Element("transType");
				transTypeEle.setText(mWalletLine.getTransType().getName());
				Element amount = new Element("amount");
				amount.setText(Double.toString(mWalletLine.getAmount()));
				Element transDate = new Element("transDate");
				transDate.setText(DateTools.dateToString(mWalletLine.getCreateDateTime()));
				Element summary  = new Element("summary");
				summary.setText(mWalletLine.getRemark());

				Element income  = new Element("income");
				
				Element pay  = new Element("pay");
				//如果属于收入类型
				if(WalletTransType.incomeList.contains(mWalletLine.getTransType())){
					income.setText(Double.toString(mWalletLine.getAmount()));
				}
				if(WalletTransType.outTypeList.contains(mWalletLine.getTransType())){
					pay.setText(Double.toString(mWalletLine.getAmount()));
				}
				//余额
				Element balance  = new Element("balance");
				balance.setText(Double.toString(mWalletLine.getAbleBalance()));
				
				
				row.addContent(lineNoEle);
				row.addContent(transTypeEle);
				row.addContent(amount);
				row.addContent(transDate);
				row.addContent(summary);
				
				rows.addContent(row);
				
			}
			
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			details.addContent(revenue);
			details.addContent(expend);
			details.addContent(win);
			details.addContent(rows);
			
			myBody.addContent(details);
			String str = softwareService.toPackage(myBody, "9048", agentId);
			return str;
		}catch (Exception e) {
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
}
