package com.yuncai.modules.lottery.software.lottery;

import java.util.List;

import org.jdom.Element;

import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.service.sqlService.user.BankDetailService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class BankDetailNotify extends BaseAction implements SoftwareProcess{
	private BankDetailService bankDetailService;
	private SoftwareService softwareService; 
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		
		String type=null;
		String province=null;
		String city=null;
		String bank=null;
		
		String provinces =null;
		String citys =null;
		String bankTypes =null;
		String bankNames=null;
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		
		Element repEle = new Element("rep");
		// -------------------------------------------------
		String node = "组装节点不存在";
		Element req=bodyEle.getChild("req");
		try{
			
			type=StringTools.isNullOrBlank(req.getChildText("type"))?"1" :req.getChildText("type") ;
			province=StringTools.isNullOrBlank(req.getChildText("province"))?null :req.getChildText("province") ;
			city=StringTools.isNullOrBlank(req.getChildText("city"))?null :req.getChildText("city") ;
			bank=StringTools.isNullOrBlank(req.getChildText("bank"))?null :req.getChildText("bank") ;
			List<String>strs=null;
			List<String>strs2=null;
			switch (Integer.parseInt(type)) {
			//获取省份列表//获取银行类型列表
			case 1:
				strs=bankDetailService.getProvoinces();
				provinces=concatStrArray(strs);
				strs2 = bankDetailService.getBankTypeNames();
				bankTypes = concatStrArray(strs2);
				break;
			//获取某省份下城市列表	
			case 2:
				if(province!=null){
					strs = bankDetailService.getCityNames(province);
					citys=concatStrArray(strs);
				}else{
					return PackageXml("500", "请求格式错误 ", "9421", agentId);
				}
				break;
				//获取某地区支行名称
			case 3:
				if(province!=null&&city!=null&&bank!=null){
					strs=bankDetailService.getBankNames(province, city, bank);
					bankNames = concatStrArray(strs);
				}else{
					return PackageXml("500", "请求格式错误 ", "9421", agentId);
				}
				break;				
			default:
				 return PackageXml("500", "请求格式错误", "9421", agentId);
			
			}
			node=null;
			responseCodeEle.setText("0");
			responseMessage.setText("操作成功");
			
			if(Integer.parseInt(type)==1){
				repEle.setText((StringTools.isNullOrBlank(provinces)?"--":provinces)+"#"+(StringTools.isNullOrBlank(bankTypes)?"--":bankTypes));
			}else if(Integer.parseInt(type)==2){
				repEle.setText(StringTools.isNullOrBlank(citys)?"--":citys);
			}else{
				repEle.setText(StringTools.isNullOrBlank(bankNames)?"--":bankNames);
			}

			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			myBody.addContent(repEle);
			
			return softwareService.toPackage(myBody, "9421", agentId);
		}catch (Exception e) {
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
					
					return PackageXml(status, message, "9421", agentId);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		}
		return null;
	}

	public void setBankDetailService(BankDetailService bankDetailService) {
		this.bankDetailService = bankDetailService;
	}
	
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	
	public String concatStrArray(List<String> strs){
		String temp=null;
		if(strs==null||strs.size()==0){
			temp="";
			return temp;
		}
		temp = strs.toString();//eg [aaa, bbb, ccc, ddd]
		temp=temp.trim().substring(1, temp.length()-1);
		return temp;
		
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
