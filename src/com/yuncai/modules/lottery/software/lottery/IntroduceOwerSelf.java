package com.yuncai.modules.lottery.software.lottery;

import java.util.Map;

import org.jdom.Element;

import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class IntroduceOwerSelf extends BaseAction implements SoftwareProcess{
	protected SoftwareService softwareService; 
	protected Map<String, IntroduceOwerSelf>map;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
	    try {
	    	String version = null;
	    	
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			SoftwareProcess softwareProcess = null;
//			if(version==null){
//				softwareProcess = map.get("5");
//			}else {
//				softwareProcess = map.get(version);
//			}
//			if(softwareProcess==null){
//				softwareProcess = map.get("5");
//			}
			softwareProcess = getPraticableVersion(map, version); 
			return  softwareProcess.execute(bodyEle, agentId);
	    	

		} catch (Exception e) {
	    	e.printStackTrace();
			try {
				return PackageXml("3002", "组装节点不存在", "9001", agentId);
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
	 /**获取从指定版本往前可用版本
	 * @param version
	 * @return
	 */
	public SoftwareProcess getPraticableVersion(Map<String, IntroduceOwerSelf>map, String version){
		SoftwareProcess softwareProcess = null;
		if(version==null||!version.matches("[0-9]+")){
			softwareProcess = map.get("4");
		}else {
			softwareProcess = map.get(version);
			if(softwareProcess==null){
				softwareProcess = getPraticableVersion(map, Integer.toString(Integer.parseInt(version)-1) );
			}
		}
		return softwareProcess;
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
	public void setMap(Map<String, IntroduceOwerSelf> map) {
		this.map = map;
	}
	
	
}
