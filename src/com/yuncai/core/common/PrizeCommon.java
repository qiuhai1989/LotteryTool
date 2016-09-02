package com.yuncai.core.common;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.yuncai.core.tools.FileTools;
import com.yuncai.modules.lottery.WebConstants;

public class PrizeCommon {
	static public List<PrizeBean> buildToList(int lotteryType, String prizeContent) {
		List<PrizeBean> ret = new ArrayList<PrizeBean>();
		String[] prizePiece = prizeContent.split("\\#");
		for (int i = 0; i < prizePiece.length; i++) {
			if (prizePiece[i].equals("")) {
				continue;
			}
			String[] areas = prizePiece[i].split("\\^");
			PrizeBean bean = new PrizeBean();
			bean.setLotteryType(lotteryType);
			bean.setPrizeName(areas[0]);
			bean.setCount(areas[1]);
			if (areas[2].indexOf("元") != -1) {
				areas[2] = areas[2].substring(0, areas[2].length() - 1);
			}
			bean.setAmount(areas[2]);
			ret.add(bean);
		}
		return ret;
	}
	
	static public List<PrizeBean> buildToListSfc14(int lotteryType, String prizeContent) {
		List<PrizeBean> ret = new ArrayList<PrizeBean>();
		String[] prizePiece = prizeContent.split("\\#");
		for (int i = 0; i < 3; i++) {
			if (prizePiece[i].equals("")) {
				continue;
			}
			String[] areas = prizePiece[i].split("\\^");
			PrizeBean bean = new PrizeBean();
			bean.setLotteryType(lotteryType);
			bean.setPrizeName(areas[0]);
			if(areas.length > 2){
				bean.setCount(areas[1]);
				if (areas[2].indexOf("元") != -1) {
				areas[2] = areas[2].substring(0, areas[2].length() - 1);
				}
				bean.setAmount(areas[2]);
			}
			ret.add(bean);
		}
		return ret;
	}
	
	static public List<PrizeBean> buildToListJq4(int lotteryType, String prizeContent) {
		List<PrizeBean> ret = new ArrayList<PrizeBean>();
			String[] areas = prizeContent.split("\\^");
			PrizeBean bean = new PrizeBean();
			bean.setLotteryType(lotteryType);
			bean.setPrizeName(areas[0]);
			if(areas.length > 2){
				bean.setCount(areas[1]);
				if (areas[2].indexOf("元") != -1) {
				areas[2] = areas[2].substring(0, areas[2].length() - 1);
				}
				bean.setAmount(areas[2]);
			}
			ret.add(bean);
		return ret;
	}
	
	public static String formatPrizeBeanList(List<PrizeBean> prizeBeanList){
		StringBuffer resultBuffer=new StringBuffer();
		
		for(PrizeBean bean:prizeBeanList){
			resultBuffer.append("#");
			resultBuffer.append(bean.getPrizeName());
			resultBuffer.append("^");
			resultBuffer.append(bean.getCount());
			resultBuffer.append("^");
			resultBuffer.append(bean.getAmount());
		}
		return resultBuffer.substring(1);
	}
	public static void main(String[] rags) {
		String str = "prize1^^500#prize2^666^600#prize3^^#prize4^^0";
		List<PrizeBean> list = buildToListSfc14(5, str);

		for(PrizeBean b : list){
			System.out.println(b.getPrizeName()+" "+b.getCount()+" "+b.getAmount());
		}
		String formatResult=formatPrizeBeanList(list);
		System.out.println(formatResult);
	}
	
	public static void buildePrizeStatistics(List<TotalPrizeBean> prizeBeanList, String fileName){
		
		try {
			SAXBuilder sb = new SAXBuilder();
			Reader reader = new StringReader("<?xml version=\"1.0\" encoding=\"GB2312\" ?><xml></xml>");

			Document returnDoc = sb.build(reader);
			Element rootElement = returnDoc.getRootElement();
			StringBuffer sbu = new StringBuffer();
			StringBuffer sbV3 = new StringBuffer();
			
			if(prizeBeanList==null||prizeBeanList.isEmpty()){
				sbu.append("<tr></tr>");
				sbV3.append("<tr></tr>");
			}else{
				for (int i = 0; i < prizeBeanList.size(); i++) {
					TotalPrizeBean prizeBean = (TotalPrizeBean) prizeBeanList.get(i);
					String account=prizeBean.getAccount();
					String tempAccount=account.substring(0,2);
					StringBuffer tempAccountBuf=new StringBuffer();
					for(int j=0,len=account.length();j<len-2;j++){
						tempAccountBuf.append("*");
					}
					tempAccount+=tempAccountBuf.toString();
					prizeBean.setAccount(tempAccount);
					Element row = new Element("row");
					row.setAttribute("account", tempAccount);
					row.setAttribute("prize", prizeBean.getPrize().toString());
					rootElement.addContent(row);
					if (i < 8) {
						if (i % 2 == 0) {
							sbu.append("<tr class=\"even\">");
						} else {
							sbu.append("<tr>");
						}
						sbu.append("<td>").append((i + 1)).append("</td>");
						sbu.append("<td>").append(prizeBean.getAccount()).append("</td>");
						sbu.append("<td>").append(prizeBean.getPrize()).append("</td>");
						sbu.append("</tr>");
					}
					if (i < 10) {
						String index = "";
						if (i == 9) {
							index = "10";
						} else {
							index = String.valueOf((i + 1));
						}
						sbV3.append("<tr>");
						//sbV3.append("<td >").append("<img src=\"/images/nh_num_" + index + ".gif\" />").append("</td>");
						sbV3.append("<td >").append("<i id=\"rankIco"+ index +"\" class=\"rank_ico\"></i>").append("</td>");
						sbV3.append("<td >").append(tempAccount).append("</td>");
						sbV3.append("<td>").append(prizeBean.getPrize()).append("</td>");
						sbV3.append("</tr>");
					}
				}
			}
			XMLOutputter xo = new XMLOutputter();

			Format format = xo.getFormat();
			format.setEncoding("GB2312");
			format.setLineSeparator("");
			xo.setFormat(format);

			String xmlContent = xo.outputString(returnDoc);
			FileTools.setFileContent(xmlContent, WebConstants.WEB_PATH + fileName + ".xml");
			FileTools.setFileContent(sbu.toString(), WebConstants.WEB_PATH + fileName + ".html");
			FileTools.setFileContent(sbV3.toString(), WebConstants.WEB_PATH + fileName + "_v3.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public static void buildePrizeStatistics20(List<TotalPrizeBean> prizeBeanList, String fileName){
		
		try {
			SAXBuilder sb = new SAXBuilder();
			Reader reader = new StringReader("<?xml version=\"1.0\" encoding=\"GB2312\" ?><xml></xml>");

			Document returnDoc = sb.build(reader);
			Element rootElement = returnDoc.getRootElement();
			StringBuffer sbu = new StringBuffer();
			StringBuffer sbV3 = new StringBuffer();
			StringBuffer phone = new StringBuffer();
			
			if(prizeBeanList==null||prizeBeanList.isEmpty()){
				sbu.append("<tr></tr>");
				sbV3.append("<tr></tr>");
				phone.append("<tr></tr>");
			}else{
				for (int i = 0; i < prizeBeanList.size(); i++) {
					TotalPrizeBean prizeBean = (TotalPrizeBean) prizeBeanList.get(i);
					String account=prizeBean.getAccount();
					String tempAccount=account.substring(0,2);
					StringBuffer tempAccountBuf=new StringBuffer();
					for(int j=0,len=account.length();j<len-2;j++){
						tempAccountBuf.append("*");
					}
					tempAccount+=tempAccountBuf.toString();
					//prizeBean.setAccount(tempAccount);
					Element row = new Element("row");
					row.setAttribute("account", account);
					row.setAttribute("prize", prizeBean.getPrize().toString());
					rootElement.addContent(row);
					if (i < 8) {
						if (i % 2 == 0) {
							sbu.append("<tr class=\"even\">");
						} else {
							sbu.append("<tr>");
						}
						sbu.append("<td>").append((i + 1)).append("</td>");
						sbu.append("<td>").append(prizeBean.getAccount()).append("</td>");
						sbu.append("<td>").append(prizeBean.getPrize()).append("</td>");
						sbu.append("</tr>");
					}
					if (i < 20) {
						sbV3.append("<tr>");
						if (i < 5 ) {
							sbV3.append("<td>").append("<span style=\"color:red;font-weight: bolder;\">"+ (i + 1)+"</span>");
						} else {
							sbV3.append("<td>").append("<span style=\"font-weight: bolder;\">"+(i + 1)+"</span>");
						}
						sbV3.append("</td>");
						sbV3.append("<td >").append(tempAccount).append("</td>");
						sbV3.append("<td>").append(prizeBean.getPrize()).append("</td>");
						sbV3.append("</tr>");
					}
					
					//手机用户
					if(i<15){
						phone.append("<tr>");
						if(i<3){
							phone.append("<td>").append("<label class='orange'>"+(i+1)+"</label>");
						}else{
							phone.append("<td>").append("<label>"+(i+1)+"</label>");
						}
						phone.append("</td>");
						phone.append("<td>").append(prizeBean.getAccount()).append("</td>");
						phone.append("<td><b class='money'>").append(prizeBean.getPrize()).append("</b></td>");
						phone.append("</tr>");
					}
				}
			}
			XMLOutputter xo = new XMLOutputter();

			Format format = xo.getFormat();
			format.setEncoding("GB2312");
			format.setLineSeparator("");
			xo.setFormat(format);

			String xmlContent = xo.outputString(returnDoc);
			FileTools.setFileContent(xmlContent, WebConstants.WEB_PATH + fileName + ".xml");
			FileTools.setFileContent(sbu.toString(), WebConstants.WEB_PATH + fileName + ".html");
			FileTools.setFileContent(sbV3.toString(), WebConstants.WEB_PATH + fileName + "_v3.html");
			FileTools.setFileContent(phone.toString(), WebConstants.WEB_PATH + fileName + ".html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
    

}
