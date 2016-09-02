package com.yuncai.modules.lottery.software.lottery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.ChaseTaskDetails;
import com.yuncai.modules.lottery.model.Sql.ChaseTasks;
import com.yuncai.modules.lottery.model.Sql.Users;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTaskDetailsService;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTasksService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ChaseTaskNotify extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService;

	private ChaseTasksService sqlChaseTasksService;
	
	private ChaseTaskDetailsService sqlChaseTaskDetailsService;

	private UsersService sqlUsersService;
	
	private MemberService memberService;

	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Integer lotteryType = null;
		String account = null;
		Short status = null;
		Date date = null;
		Integer planNo = null;
		Integer order = null;
		Integer pageSize = null;

		String message = "组装节点错误!";
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		Element resps = new Element("resps");
		//----------------------------------------------------
		try {
			Element req = bodyEle.getChild("req");
			lotteryType = StringTools.isNullOrBlank(req.getChildText("lotteryType"))?-1:Integer.parseInt(req.getChildText("lotteryType"));
			account = req.getChildText("account");
			status =  StringTools.isNullOrBlank(req.getChildText("status")) ?0:Short.parseShort(req.getChildText("status"));
			date = DateTools.StringToDate(req.getChildText("date"), "yyyy-MM");
			planNo = StringTools.isNullOrBlank(req.getChildText("planNo"))?0: Integer.parseInt(req.getChildText("planNo"));
			order =  Integer.parseInt(req.getChildText("order"));
			pageSize =  StringTools.isNullOrBlank(req.getChildText("pageSize"))?15 : Integer.parseInt(req.getChildText("pageSize"));

			
			// 验证登陆
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if (!account.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}

			// 判断用户是否存在
			if (account != null && !"".equals(account)) {
				List<Member> list = memberService.findByProperty("account", account);
				if (list == null || list.isEmpty()) {
					super.errorMessage = "此用户不存在!";
					return PackageXml("002", super.errorMessage, "8005", agentId);
				}

			} else {
				super.errorMessage = "用户名不能为空!";
				return PackageXml("002", super.errorMessage, "8005", agentId);
			}
			
			
			
			message = null;
			// 待改进每次都需要根据accout 查找sql t_users
			Users users = sqlUsersService.findByProperty("name", account).get(0);

			if (users == null) {
				message = "找不到响应记录";
				return PackageXml("4009", message, "9049", agentId);
			}
			
			Date startDate = null;
			Date endDate = null;
			// 根据给定的date对象获取到Caledar对象后获取到
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, 1);
			startDate = c.getTime();
			// 该月最后一天
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			c.add(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.MILLISECOND, -1);
			endDate = c.getTime();
			
			
			List<ChaseTasks> list = sqlChaseTasksService.findRecordForList(users.getId(), lotteryType, startDate, endDate, planNo, order, pageSize, status);
		
			if(list==null||list.isEmpty()){
				message = " 找不到相应的记录";
				return PackageXml("4009", message, "9049", agentId);
			}else {
				message = null;
			}
			
			
			
			
			
			for(ChaseTasks cTasks:list){
				//查找某个方案下的追号详情列表
				List<ChaseTaskDetails> detaiList = sqlChaseTaskDetailsService.findByProperty("chaseTaskId", cTasks.getId());
				
				int sum = detaiList.size();
				int finished = 0;
				int canceled = 0;
				Double amount = 0d;
				for(ChaseTaskDetails details:detaiList){
					amount+= details.getMoney();
					if(details.isExecuted()){
						finished++;
					}else if (details.getQuashStatus()==1) {
						canceled++;
					}
					cTasks.setSum(sum);
					cTasks.setFinished(finished);
					cTasks.setCanceled(canceled);
					
				}
				
				cTasks.setStatus(Short.toString(cTasks.getQuashStatus()));
				
				cTasks.setAmount(amount);
				
				Element resp = new Element("resp");
				Element chaseTaskId = new Element("chaseTaskId");
				chaseTaskId.setText(Integer.toString(cTasks.getId()));
				Element startTime = new Element("startTime");
				startTime.setText(DateTools.dateToString(cTasks.getDateTime()) );
				Element lotteryTypeEle = new Element("lotteryType");
				lotteryTypeEle.setText(LotteryType.getItem(cTasks.getLotteryId()).getName());
				Element lotteryId = new Element("lotteryId");
				lotteryId.setText(Integer.toString(cTasks.getLotteryId()));
				Element amountEle = new Element("amount");
				amountEle.setText(Double.toString(cTasks.getAmount()));
				Element total = new Element("total");
				total.setText(Integer.toString(cTasks.getSum()));
				Element finishedEle = new Element("finished");
				finishedEle.setText(Integer.toString(cTasks.getFinished()));
				Element canceledEle = new Element("canceled");
				canceledEle.setText(Integer.toString(cTasks.getCanceled()));
				Element statusEle = new Element("status");
				statusEle.setText(cTasks.getStatus());
				
				resp.addContent(chaseTaskId);
				resp.addContent(startTime);
				resp.addContent(lotteryTypeEle);
				resp.addContent(lotteryId);
				resp.addContent(amountEle);
				resp.addContent(total);
				resp.addContent(finishedEle);
				resp.addContent(canceledEle);
				resp.addContent(statusEle);
				resps.addContent(resp);
			}
			responseCodeEle.setText("0");
			responseMessage.setText("查询成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(resps);
			
			return softwareService.toPackage(myBody, "9009", agentId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("4009", code, "9049", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setSqlChaseTasksService(ChaseTasksService sqlChaseTasksService) {
		this.sqlChaseTasksService = sqlChaseTasksService;
	}

	public void setSqlChaseTaskDetailsService(ChaseTaskDetailsService sqlChaseTaskDetailsService) {
		this.sqlChaseTaskDetailsService = sqlChaseTaskDetailsService;
	}

	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}
	
	
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	// 组装错误信息
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

}
