package com.yuncai.core.common;


import org.jdom.Element;

import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;

public class CheckBingoXml {
	
	//开奖奖金组装
	public static String getResultDetail(LotteryType type,Element winLists){
		
		//大乐透
		String resultDetail="";
		String prize1="";
		String prize2="";
		String prize3="";
		String prize4="";
		String prize5="";
		String prize6="";
		String prize7="";
		String prize8="";
		String prize9="";
		String prize10="";
		String prize11="";
		String prize12="";
		String prize13="";
		String prize14="";
		String prize15="";
		String prize16="";
		if(LotteryType.TCCJDLT.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			Element winLists2=winLists.getChild("winLists2");
			Element winLists3=winLists.getChild("winLists3");
			Element winLists4=winLists.getChild("winLists4");
			Element winLists5=winLists.getChild("winLists5");
			Element winLists6=winLists.getChild("winLists6");
			Element winLists7=winLists.getChild("winLists7");
			Element winLists8=winLists.getChild("winLists8");
			
			prize1=winLists1.getChildText("defaultMoney");
			prize2=winLists1.getChildText("addPrize");
			prize3=winLists2.getChildText("defaultMoney");
			prize4=winLists2.getChildText("addPrize");
			prize5=winLists3.getChildText("defaultMoney");
			prize6=winLists3.getChildText("addPrize");
			prize7=winLists4.getChildText("defaultMoney");
			prize8=winLists5.getChildText("defaultMoney");
			prize9=winLists6.getChildText("defaultMoney");
			prize10=winLists7.getChildText("defaultMoney");
			prize11=winLists8.getChildText("defaultMoney");
			prize12="0";
			prize13=winLists4.getChildText("addPrize");
			prize14=winLists5.getChildText("addPrize");
			prize15=winLists6.getChildText("addPrize");
			prize16=winLists7.getChildText("addPrize");
			resultDetail +="prize1^^"+prize1+"#prize2^^"+prize2+"#prize3^^"+prize3+"#prize4^^"+prize4+
			"#prize5^^"+prize5+"#prize6^^"+prize6+"#prize7^^"+prize7+"#prize8^^"+prize8+"#prize9^^"+prize9+
			"#prize10^^"+prize10+"#prize11^^"+prize11+"#prize12^^"+prize12+"#prize13^^"+prize13+"#prize14^^"+prize14+
			"#prize15^^"+prize15+"#prize16^^"+prize16;
		}else if(LotteryType.QXC.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			Element winLists2=winLists.getChild("winLists2");
			Element winLists3=winLists.getChild("winLists3");
			Element winLists4=winLists.getChild("winLists4");
			Element winLists5=winLists.getChild("winLists5");
			Element winLists6=winLists.getChild("winLists6");
			prize1=winLists1.getChildText("defaultMoney");
			prize2=winLists2.getChildText("defaultMoney");
			prize3=winLists3.getChildText("defaultMoney");
			prize4=winLists4.getChildText("defaultMoney");
			prize5=winLists5.getChildText("defaultMoney");
			prize6=winLists6.getChildText("defaultMoney");
			resultDetail +="prize1^^"+prize1+"#prize2^^"+prize2+"#prize3^^"+prize3+"#prize4^^"+prize4+
			"#prize5^^"+prize5+"#prize6^^"+prize6;
		}else if(LotteryType.SZPL3.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			Element winLists2=winLists.getChild("winLists2");
			Element winLists3=winLists.getChild("winLists3");
			prize1=winLists1.getChildText("defaultMoney");
			prize2=winLists2.getChildText("defaultMoney");
			prize3=winLists3.getChildText("defaultMoney");
			resultDetail +="prize1^^"+prize1+"#prize2^^"+prize2+"#prize3^^"+prize3;
		}else if(LotteryType.SZPL5.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			prize1=winLists1.getChildText("defaultMoney");
			resultDetail +="prize1^^"+prize1;
		}else if(LotteryType.ZCSFC.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			Element winLists2=winLists.getChild("winLists2");
			Element winLists3=winLists.getChild("winLists3");
			prize1=winLists1.getChildText("defaultMoney");
			prize2=winLists2.getChildText("defaultMoney");
			prize3="0";
			resultDetail +="prize1^^"+prize1+"#prize2^^"+prize2+"#prize3^^"+prize3;
		}else if(LotteryType.ZCRJC.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			prize1=winLists1.getChildText("defaultMoney");
			resultDetail +="prize1^^"+prize1;
		}else if(LotteryType.JQC.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			prize1=winLists1.getChildText("defaultMoney");
			resultDetail +="prize1^^"+prize1;
		}else if(LotteryType.LCBQC.getValue()==type.getValue()){
			Element winLists1=winLists.getChild("winLists1");
			prize1=winLists1.getChildText("defaultMoney");
			resultDetail +="prize1^^"+prize1;
		}
		
		
		return resultDetail;
		
	}

	public static String getResult(LotteryType type,String winNumber){
		String result="";
		StringBuffer str=new StringBuffer();
		if(LotteryType.TCCJDLT.getValue()==type.getValue()){
			result=(winNumber.trim().split("\\+")[0].trim()+" "+ winNumber.trim().split("\\+")[1].trim()).replaceAll(" ", ",").trim();
			
		}else if(LotteryType.QXC.getValue()==type.getValue()){
			str.append(winNumber.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(str.length()-1);
			result=str.toString();
		}else if(LotteryType.SZPL3.getValue()==type.getValue()){
			str.append(winNumber.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(str.length()-1);
			result=str.toString();
		}else if(LotteryType.SZPL5.getValue()==type.getValue()){
			str.append(winNumber.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(str.length()-1);
			result=str.toString();
		}else if(LotteryType.ZCSFC.getValue()==type.getValue()){
			str.append(winNumber.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(str.length()-1);
			result=str.toString();
		}else if(LotteryType.ZCRJC.getValue()==type.getValue()){
			str.append(winNumber.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(str.length()-1);
			result=str.toString();
		}else if(LotteryType.JQC.getValue()==type.getValue()){
			str.append(winNumber.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(str.length()-1);
			result=str.toString();
		}else if(LotteryType.LCBQC.getValue()==type.getValue()){
			str.append(winNumber.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(str.length()-1);
			result=str.toString();
		}
		return result;
	}
	public static void main(String[] args) {
		StringBuffer b=new StringBuffer();
		String winNumber="01 02 03 04 05 + 01 12";
		String result="";
		
		result=(winNumber.trim().split("\\+")[0].trim()+" "+ winNumber.trim().split("\\+")[1].trim()).replaceAll(" ", ",").trim();
		//b.append(result.trim().replaceAll("", "\\,")).deleteCharAt(0).deleteCharAt(b.length()-1);
		System.out.println(result);
	}
}
