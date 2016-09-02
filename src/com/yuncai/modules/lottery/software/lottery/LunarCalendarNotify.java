package com.yuncai.modules.lottery.software.lottery;

import java.util.Date;

import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LunarCalendar;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class LunarCalendarNotify extends BaseAction implements SoftwareProcess {
	private SoftwareService softwareService; 
	@Override
	public String execute(Element bodyEle, String agentId) {
		Date date = null;
		//----------------新建xml包体--------------------
	    @SuppressWarnings("unused")
		Element myBody=new Element("body");
	    Element responseCodeEle=new Element("responseCode");
	    Element responseMessage=new Element("responseMessage");
	    Element dateEle = new Element("date");
	    //-------------------------------------------------
		Element dateInfo = bodyEle.getChild("date");
	    
	    String node="组装节点不存在";
	    
	    String lunarInfo = null;
	    try {
	    	date = dateInfo.getText()==null||"".equals(dateInfo.getText())?null:DateTools.StringToDate(dateInfo.getText());
	    	if(date==null){
	    		 
	    		 lunarInfo = new LunarCalendar().toString2();
	    	}else {
	    		 lunarInfo = new LunarCalendar(date).toString2();
			}
	    	
	    	node = null;
	    	
	    	dateEle.setText(lunarInfo);
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(dateEle);
			return softwareService.toPackage(myBody, "9007",agentId);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			try {
				String message="";
				String status="200";
				if(node!=null)
					message=node;
				else{
					message=e.getMessage();
					status="1";
				}
				
				return PackageXml(status, message, "9300", agentId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		}
	    
		return null;
	}
	
	//组装错误信息
	public String PackageXml(String CodeEle,String message,String type,String agentId) throws Exception{
		//----------------新建包体--------------------
		 String returnContent="";
		 Element myBody=new Element("body");
		 Element responseCodeEle=new Element("responseCode");
		 Element responseMessage=new Element("responseMessage");
		 responseCodeEle.setText(CodeEle); 
		 responseMessage.setText(message);
		 myBody.addContent(responseCodeEle);
		 myBody.addContent(responseMessage);
		 returnContent= this.softwareService.toPackage(myBody, type, agentId);
		 return returnContent;
		 
	}
	
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

}
