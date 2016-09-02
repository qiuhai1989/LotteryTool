package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.yuncai.core.common.PrizeBean;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.Strings;
import com.yuncai.modules.lottery.bean.vo.PrizeLevelInfoBean;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Sql.Isuses;

public class PrizeHistoryNotify11_0 extends PrizeHistoryNotify{

	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		Integer lotteryId= null;
		Integer term = null;//按彩期查询 默认为0查询所有
		Integer offSet = null;//设置页数 默认为1
		Integer order = null;//设置按彩期传进行查询上下级的数据了 默认为0向上，1向下
		Integer pageSize = null;//设置查询多少行 默认为15
		
		// ----------------新建xml包体--------------------
		@SuppressWarnings("unused")
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		//获取请求信息
		Element drawHistoryEle = bodyEle.getChild("drawHistory");
		String message="组装节点不存在";
		try {
			
			lotteryId = StringTools.isNullOrBlank(drawHistoryEle.getChildText("lotteryType"))?0:Integer.parseInt(drawHistoryEle.getChildText("lotteryType"));
			
			try {
				term = StringTools.isNullOrBlank(drawHistoryEle.getChildText("term"))?0:Integer.parseInt(drawHistoryEle.getChildText("term"));
			} catch (Exception e) {
				term=0;
			}
			try {
				offSet = StringTools.isNullOrBlank(drawHistoryEle.getChildText("offSet"))?1:Integer.parseInt(drawHistoryEle.getChildText("offSet"));
			} catch (Exception e) {
				offSet=1;
			}
			try {
				order = StringTools.isNullOrBlank(drawHistoryEle.getChildText("order"))?0:Integer.parseInt(drawHistoryEle.getChildText("order"));
			} catch (Exception e) {
				order=-1;
			}
			try {
				pageSize = StringTools.isNullOrBlank(drawHistoryEle.getChildText("pageSize"))?15:Integer.parseInt(drawHistoryEle.getChildText("pageSize"));
			} catch (Exception e) {
				pageSize=10;
			}
		    
			message=null;
		    //根据请求条件查找得到结果集
		    List<Isuses>isusesList = new ArrayList<Isuses>();
		    
		    isusesList = sqlIsusesService.FindPrizeHistory(lotteryId,Integer.toString(term) , offSet, order, pageSize); 
		    if(isusesList.size()==0){
		    	return PackageXml("002", "没有找到相应的记录", "9116", agentId);
				
		    }
		    
		    //遍历结果集构造响应xml

			responseCodeEle.setText("0");
			responseMessage.setText("查询成功");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			
		    for(Isuses isuses :isusesList){
				Element info=new Element("drawDetail");
			    Element termEle =new Element("term");
			    Element openDateTimeEle =new Element("openDateTime");
			    Element totalAmountEle =new Element("totalAmount");
			    Element resultEle =new Element("result");
			    Element prizeLevelInfo =new Element("prizeLevelInfo");
		    	termEle.setText(isuses.getName() );
		    	//开奖时间待设置
		    	openDateTimeEle.setText(DateTools.dateToString(isuses.getOpenDateTime()));
		    	resultEle.setText(isuses.getWinLotteryNumber());
		    	//
		    	totalAmountEle.setText(isuses.getTotalSales()>0?Integer.toString(isuses.getTotalSales()):"");
		    	//如果是大乐透只保留三等 加奖之前
		    	if(isuses.getLotteryId()==LotteryType.TCCJDLT.getValue()){
		    		String temp = isuses.getPrizeLevelInfo();
		    		try {
						temp =  temp.substring(0, Strings.getStringNum(temp, "#", 6));
//						System.out.println(temp);
						prizeLevelInfo.setText(temp);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
		    	}else if(isuses.getLotteryId()==LotteryType.GD11X5.getValue()){
		    		prizeLevelInfo.setText(isuses.reBuildForGD11X5());
		    	}
		    	else{
		    		prizeLevelInfo.setText(isuses.getPrizeLevelInfo());
		    	}
		    	
		    	
		    	
		    	//双色球，大乐透，七星彩等开奖结果数据格式不同，解析方式不同
		    	//  detail = "prize1^3^4684134#prize2^10^34233#prize3^30^5456" 双色球，大乐透，七星彩
		    	//直选^3^1000#组三^10^320#组六^30^160 3D
		    	//直选^3^1000#组三^10^320#组六^30^160 排列三
		    	//一等奖^3^100000#未设置^0^0#未设置^0^0 排列五，任9
		    	//一等奖^3^100000#二等奖^10^564655#未设置^0^0 胜负彩
		    		
			    	info.addContent(termEle);
			    	info.addContent(openDateTimeEle);
			    	info.addContent(resultEle);
			    	info.addContent(totalAmountEle);
			    	info.addContent(prizeLevelInfo);
		    	myBody.addContent(info);
		    }
	
		    return 	 softwareService.toPackage(myBody, "9050", agentId);
				
			
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

}
