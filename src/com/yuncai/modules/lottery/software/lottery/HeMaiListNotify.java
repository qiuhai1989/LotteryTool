package com.yuncai.modules.lottery.software.lottery;

import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.yuncai.core.tools.ArithUtil;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.NumberTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.LotteryPlan;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.oracleService.lottery.LotteryPlanService;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class HeMaiListNotify extends SupNotify {

	protected LotteryPlanService lotteryPlanService;
//	protected SoftwareService softwareService;
	protected IsusesService sqlIsusesService;
	protected Map<String, HeMaiListNotify>map;
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		Element hmFound = bodyEle.getChild("hmFound");
		LotteryType lotteryType = null;
		String account = null;
		int offSet = 1;
		int pageSize = 10;
		int order=0;
		int key=0;
		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		//-------------------------------------------------
		String message="组装节点不存在";
		String version = null;
		SoftwareProcess softwareProcess = null;
		try {
			
			version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
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
				return PackageXml("002", code, "9116", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	 /**获取从指定版本往前可用版本
	 * @param version
	 * @return
	 */
	public SoftwareProcess getPraticableVersion(Map<String, HeMaiListNotify>map, String version){
		SoftwareProcess softwareProcess = null;
		if(version==null||!version.matches("[0-9]+")||Integer.parseInt(version.trim())<=7){
			softwareProcess = map.get("7");
		}else {
			softwareProcess = map.get(version);
			if(softwareProcess==null){
				softwareProcess = getPraticableVersion(map, Integer.toString(Integer.parseInt(version)-1) );
			}
		}
		return softwareProcess;
	 }
	
	public void setLotteryPlanService(LotteryPlanService lotteryPlanService) {
		this.lotteryPlanService = lotteryPlanService;
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}

	public void setSqlIsusesService(IsusesService sqlIsusesService) {
		this.sqlIsusesService = sqlIsusesService;
	}

	public void setMap(Map<String, HeMaiListNotify> map) {
		this.map = map;
	}

}
