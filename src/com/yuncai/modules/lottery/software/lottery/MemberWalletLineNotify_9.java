package com.yuncai.modules.lottery.software.lottery;

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
import com.yuncai.core.util.Dates;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberScore;
import com.yuncai.modules.lottery.model.Oracle.MemberWalletLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;

public class MemberWalletLineNotify_9 extends MemberWalletLineNotify {
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
//		Date date = null;
		Integer pageNo = null;
		Date startDate = null;
		Date endDate = null;
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
			pageNo = StringTools.isNullOrBlank(bodyEle.getChildText("pageNo"))?1:Integer.parseInt(bodyEle.getChildText("pageNo"));

			startDate = Dates.format(bodyEle.getChildText("beginDate"), "yyyy-MM-dd");
			endDate = Dates.format(bodyEle.getChildText("endDate"), "yyyy-MM-dd");
			//因为 oracle betweent  ... and...函数的特殊性 含头不含尾
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, +1);
			endDate = c.getTime();
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
			
			//LogUtil.out("查询钱.....");
			List<MemberWalletLine> list = memberWalletLineService.findByAccountAndDate(account, startDate, endDate, pageSize, null, pageNo);
			//总收入
//			Double sumIn = memberWalletLineService.countAmount(account,startDate,endDate,WalletTransType.incomeList);
//			
//			
//			if(sumIn==null){
//				sumIn = 0.0;
//			}
			Double sumIn = 0.0;
			//总支出
//			Double sumOut = memberWalletLineService.countAmount(account,startDate,endDate,WalletTransType.outTypeList);
//			if(sumOut==null){
//				sumOut=0.0;
//			}
			Double sumOut = 0.0;
			//中奖收入
//			Double winIn = memberWalletLineService.countAmount(account,startDate,endDate,WalletTransType.prizeTypeList);
//			if(winIn==null){
//				winIn=0.0;
//			}
			Double winIn = 0.0;
			
			List<Object[]>objs = memberWalletLineService.listMoney(account, startDate, endDate);
			for(Object[] obj: objs){
				//model.amount ,model.transType
				Double amount = (Double) obj[0];
				WalletTransType type = (WalletTransType) obj[1];
				Integer selectStatus = (Integer)obj[2];
				if(WalletTransType.outTypeList.contains(type)){
					if(type.equals(WalletTransType.FREEZE)&&(selectStatus==null||selectStatus!=0)){
						continue;
					}
					sumOut+=amount;
				}
				if(WalletTransType.incomeList.contains(type)){
					if(type.equals(WalletTransType.UN_FREEZE)&&(selectStatus==null||selectStatus!=0)){
						continue;
					}
					sumIn+=amount;
				}	
				if(WalletTransType.prizeTypeList.contains(type)){
					winIn+=amount;
				}
				
			}
			
			
			//开始组装xml
			responseCodeEle.setText("0");  //上传成功
			responseMessage.setText("查询成功");
			revenue.setText(doRatio(sumIn));
			expend.setText(doRatio(sumOut));
			win.setText(doRatio(winIn));
			
			for(MemberWalletLine mWalletLine:list){
				Element row = new Element("row");
				Element lineNoEle = new Element("lineNo");
//				LogUtil.out(mWalletLine.getWalletLineNo());
				lineNoEle.setText(mWalletLine.getWalletLineNo()+"");
				Element transTypeEle = new Element("transType");
				transTypeEle.setText(mWalletLine.getRemark());
				Element amount = new Element("amount");
				amount.setText(Double.toString(mWalletLine.getAmount()));
				Element transDate = new Element("transDate");
				transDate.setText(Dates.format(mWalletLine.getCreateDateTime(), "yyyy-MM-dd HH:mm:ss"));
				Element summary  = new Element("summary");
				summary.setText(mWalletLine.getRemark());

				Element income  = new Element("income");
				
				Element pay  = new Element("pay");
				//如果属于收入类型
				if(WalletTransType.incomeList.contains(mWalletLine.getTransType())){
					income.setText(doRatio(mWalletLine.getAmount()));
				}
				if(WalletTransType.outTypeList.contains(mWalletLine.getTransType())){
					pay.setText(doRatio(mWalletLine.getAmount()));
				}
				//余额
				Element balance  = new Element("balance");
				balance.setText(doRatio(mWalletLine.getAbleBalance()));
				
				
				row.addContent(lineNoEle);
				row.addContent(transTypeEle);
				row.addContent(amount);
				row.addContent(transDate);
				row.addContent(summary);
				row.addContent(income);
				row.addContent(pay);
				row.addContent(balance);
				rows.addContent(row);
				//应开客户端发人员需要 如果只有一条记录 拼接成两条 且第二条记录为空
				if(list.size()==1){
					Element row2 = new Element("row");
					row2.setText("");
					Element lineNoEle2 = new Element("lineNo");
					lineNoEle2.setText("");
					Element transTypeEle2 = new Element("transType");
					transTypeEle2.setText("");
					Element amount2 = new Element("amount");
					amount2.setText("");
					Element transDate2 = new Element("transDate");
					transDate2.setText("");
					Element summary2  = new Element("summary");
					summary2.setText("");
					Element income2  = new Element("income");
					income2.setText("");
					Element pay2  = new Element("pay");
					pay2.setText("");
					//余额
					Element balance2  = new Element("balance");
					balance.setText(Double.toString(mWalletLine.getAbleBalance()));
					row2.addContent(lineNoEle2);
					row2.addContent(transTypeEle2);
					row2.addContent(amount2);
					row2.addContent(transDate2);
					row2.addContent(summary2);
					row2.addContent(income2);
					row2.addContent(pay2);
					row2.addContent(balance2);
					rows.addContent(row2);
					
				}
			}
			
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			details.addContent(revenue);
			details.addContent(expend);
			details.addContent(win);
			
			//应客户端开发人员需要 如果没记录就直接不显示这个
			if(list.size()!=0){
				details.addContent(rows);
			}
			
			
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
