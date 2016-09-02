package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;
import com.yuncai.modules.lottery.service.sqlService.lottery.IsusesService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class AfterCurrentTermListNotify extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService;
	private IsusesService sqlIsusesService;
	
	
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		HttpServletRequest request=ServletActionContext.getRequest();
		Element chaseTerm=bodyEle.getChild("chaseTerm");
		Integer lotteryId = null;
		Integer count = 100;
		String message="组装节点错误!";
		//-------------------新建xml包体--------------------------
		Element myBody=new Element("body");
		Element responseCodeEle =new Element("responseCode");  //返回值
		Element responseMessage=new Element("responseMessage");
		Element chaseTermEle=new Element("chaseTerm");
		
		
		//-------------------------------------------------
		
		try {
			lotteryId = StringTools.isNullOrBlank(chaseTerm.getChildText("lotteryType"))?null:Integer.parseInt(chaseTerm.getChildText("lotteryType"));
			if(lotteryId !=null &&LotteryType.getItem(lotteryId)==null){
				message = " 该彩种不存在";
				return PackageXml("3002", message, "9005", agentId);
			} 
			count = StringTools.isNullOrBlank(chaseTerm.getChildText("count"))?100:Integer.parseInt(chaseTerm.getChildTextTrim("count"));
			message=null;	
			
			List<Isuses> isuses = sqlIsusesService.findCurrentTermList(lotteryId);
			List<Isuses>list = sqlIsusesService.findAfterTerms(isuses.get(0).getLotteryId(), count, isuses.get(0).getName());
			
			if(list!=null&&list.size()!=0){
				for(Isuses isuses2:list){
					Element row = new Element("row");
					row.setAttribute("termId",Integer.toString(isuses2.getId()) );
					row.setAttribute("expect", isuses2.getName());
					row.setAttribute("starttime",DateTools.dateToString(isuses2.getStartTime()));
					row.setAttribute("endtime",  DateTools.dateToString(isuses2.getEndTime()));
					row.setAttribute("opentime", DateTools.dateToString(isuses2.getOpenDateTime()));
					chaseTermEle.addContent(row);
				}
				responseCodeEle.setText("0");
				responseMessage.setText("操作成功");
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(chaseTermEle);
				return softwareService.toPackage(myBody, "9046", agentId);
			}else {
				return PackageXml("002", "没有找到相应的记录", "9116", agentId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code="";
				if(message!=null){
					code=message;
				}else{
					code="查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9005", agentId);
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
}
