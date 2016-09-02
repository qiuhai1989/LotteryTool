package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Sql.ChaseTaskDetails;
import com.yuncai.modules.lottery.service.sqlService.lottery.ChaseTaskDetailsService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class ChaseTaskDetailNotify extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService;
	private ChaseTaskDetailsService sqlChaseTaskDetailsService;
	private IsusesService sqlIsusesService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		
		Integer chaseTaskId = null;
		String userName = null;
		
		String message = "组装节点错误!";
		
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		Element resps = new Element("resps");
		//----------------------------------------------------
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			chaseTaskId = Integer.parseInt(bodyEle.getChildText("chaseTaskId"))  ;
			userName = bodyEle.getChildText("userName");
			
			message = null;
			
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if (memberSession == null) {
				return PackageXml("2000", "请登录!", "9300", agentId);
			}

			if (!userName.equals(memberSession.getAccount())) {
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			
			
			List<Object[]> objs = sqlChaseTaskDetailsService.findByChaseTaskIDForDeList2(chaseTaskId);
			
			List<String>names = new ArrayList<String>();
			
			List<ChaseTaskDetails> detList = new ArrayList<ChaseTaskDetails>();
			
			for(Iterator iterator=objs.iterator() ;iterator.hasNext();){
				Object[]obj = (Object[])iterator.next();
				detList.add((ChaseTaskDetails) obj[0]);
				names.add((String) obj[1]);
			}
			
			if(detList==null||detList.size()==0){
				message = " 找不到相应的记录";
				return PackageXml("4010", message, "9410", agentId);
			}
			
			for(int i=0;i<detList.size();i++){
				
				ChaseTaskDetails chaseTask = detList.get(i);
				
				Element resp = new Element("resp");
				Element chaseTaskIdEle = new Element("chaseTaskId");
				chaseTaskIdEle.setText(Integer.toString(chaseTask.getId()));
				Element term = new Element("term");
//				Isuses isuses = sqlIsusesService.findByProperty("id", chaseTask.getIsuseId()).get(0);
				term.setText(names.get(i)+"期");
				Element content = new Element("content");
//				LogUtil.out(chaseTask.getLotteryNumber());
				content.setText(contentChange(chaseTask.getLotteryNumber(), "\n")  );
				Element multiple = new Element("multiple");
				multiple.setText( Integer.toString(chaseTask.getMultiple()) );
				Element amount = new Element("amount");
				amount.setText(Double.toString(chaseTask.getMoney()));
				Element status = new Element("status");
				if(chaseTask.isExecuted()){
					status.setText("2");
				}
				if(chaseTask.getQuashStatus()>0&&chaseTask.isExecuted()==false){
					status.setText("3");
				}
				
				if(!chaseTask.isExecuted()&&chaseTask.getQuashStatus()==0){
					status.setText("1");
				}
				
				resp.addContent(chaseTaskIdEle);
				resp.addContent(term);
				resp.addContent(content);
				resp.addContent(multiple);
				resp.addContent(amount);
				resp.addContent(status);
				
				resps.addContent(resp);
				
			}
			responseCodeEle.setText("0");
			responseMessage.setText("查询成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(resps);
			
			return softwareService.toPackage(myBody, "9410", agentId);
			
		}catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("4010", code, "9410", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return null;
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


	
	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}


	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}


	public void setSqlChaseTaskDetailsService(ChaseTaskDetailsService sqlChaseTaskDetailsService) {
		this.sqlChaseTaskDetailsService = sqlChaseTaskDetailsService;
	}
	
	private  String contentChange(String content,String prefix){
		content = content.replaceAll("\r\n", "\n");
		String [] strs = content.split(prefix);
		StringBuffer sbf = new StringBuffer();
		for(String str:strs){
			sbf.append(str+"#");
		}
		String demo = sbf.toString();
		return demo.substring(0,demo.length()-1);
		
	}
	
}
