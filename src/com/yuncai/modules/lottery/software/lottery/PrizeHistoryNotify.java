package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.bean.vo.PrizeLevelInfoBean;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class PrizeHistoryNotify extends BaseAction implements SoftwareProcess {
	 
	protected SoftwareService softwareService; 
	protected  IsusesService sqlIsusesService;
	
	private Map<String, PrizeHistoryNotify>map;
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		
		String version = null;
		String message="组装节点不存在";
		SoftwareProcess softwareProcess = null;
		try {
			
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
//			version="9";
			softwareProcess = getPraticableVersion(map, version); 
			return  softwareProcess.execute(bodyEle, agentId);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code="";
				if(message!=null){
					code=message;
				}else{
					code="查询处理中或许存在异常";
				}
				return PackageXml("002", code, "9005", agentId);
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
	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}
	 /**获取从指定版本往前可用版本
	 * @param version
	 * @return
	 */
	public SoftwareProcess getPraticableVersion(Map<String, PrizeHistoryNotify>map, String version){
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
	public Map<String, PrizeHistoryNotify> getMap() {
		return map;
	}
	public void setMap(Map<String, PrizeHistoryNotify> map) {
		this.map = map;
	}
	
	
	
}
