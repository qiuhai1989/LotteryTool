package com.yuncai.modules.lottery.software.lottery;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Clues;
import com.yuncai.modules.lottery.service.oracleService.member.CluesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class CluesNotify extends BaseAction implements SoftwareProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CluesService cluesService;
	private SoftwareService softwareService;

	@SuppressWarnings("unused")
	public String execute(Element bodyEle, String agentId) {// 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = "";// 提示语编号
		String con = "";// 提示内容
		String snote="";
		String sname="";

		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		Element clues = new Element("clues");
		
		// -------------------------------------------------
		// 获取请过的body
		Element cl = bodyEle.getChild("cl");
		String node = "组装节点不存在";
		String sendType = cl.getChildText("sendType");
		Clues cc = new Clues();
		try {
			if (sendType.equals("0")) {
				String[] names = new String[2];
				names[0] = "effective";
				names[1] = "type";
				Object[] values = new Object[2];
				values[0] = 1;
				values[1] = 0;
				List<Clues> list = cluesService.findByPropertys(names, values);
				for (Clues cc1 : list) {
					Element clue = new Element("clue");
					Element cluesID = new Element("cluesID");
					Element content = new Element("content");
					Element note = new Element("note");
					Element name = new Element("name");
					cluesID.setText(""+cc1.getCode());
					content.setText(cc1.getContent());
					snote = cc1.getNote();
					if(DBHelper.isNoNull(snote))
						snote = CluesNotify.getUrl()+snote;
					note.setText(snote);
					name.setText(cc1.getName());
					clue.addContent(cluesID);
					clue.addContent(content);
					clue.addContent(note);
					clue.addContent(name);
					clues.addContent(clue);
				}
			} else if (sendType.equals("1")) {//请求时只有一条数据，为保持返回格式一致加一条空数据
				id = cl.getChildText("cluesID");
				if(!DBHelper.isNoNull(id)){
					return PackageXml("500", "未传送提示语编号", "9201", agentId);
				}
				cc = cluesService.findObjectByProperty("code",Integer.valueOf(id));
				if(!DBHelper.isNoNull(cc)){
					return PackageXml("500", "未找到提示语编号："+id, "9201", agentId);
				}
				for (int i=0;i<2;i++) {
					Element clue = new Element("clue");
					Element cluesID = new Element("cluesID");
					Element content = new Element("content");
					Element note = new Element("note");
					Element name = new Element("name");
					if(i==0){
						if(cc.getEffective()==1){
							con = cc.getContent();
							snote = cc.getNote();
							sname = cc.getName();
							if(DBHelper.isNoNull(snote))
								snote = CluesNotify.getUrl()+snote;
						}
						id = "" + cc.getCode();
					}else{
						con="";
						id="";
						snote="";
						sname="";
					}
					cluesID.setText(id);
					content.setText(con);
					note.setText(snote);
					name.setText(sname);
					clue.addContent(cluesID);
					clue.addContent(content);
					clue.addContent(note);
					clue.addContent(name);
					clues.addContent(clue);
				}
			}

			myBody.addContent(clues);

			responseCodeEle.setText("0");// 0 ok 1请填充信息
			responseMessage.setText("返回成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			return softwareService.toPackage(myBody, "9201", agentId);
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

	public static String getUrl(){
		String url = "http://www.dyjw.com";
		try {
				InetAddress addr = InetAddress.getLocalHost();
				String ip=addr.getHostAddress().toString();
				if(ip.equals("192.168.0.66")){
					url = "http://wayin.5166.info:81";
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		return url;
	}
	
	public CluesService getCluesService() {
		return cluesService;
	}

	public void setCluesService(CluesService cluesService) {
		this.cluesService = cluesService;
	}

	public SoftwareService getSoftwareService() {
		return softwareService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

}
