package com.yuncai.modules.lottery.factorys.verify;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
/**
 * 解析的content格式为：玩法:选号/n玩法/n选号%倍数 
 * 
 * @author lm
 * 
 */
public class FbSfc14Parser implements ParserBuilder {

	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		ContentCheck contentCheck = new ContentCheck();
		int amountTest = 0;
		int type = 0;
		int playType = 0;
		String endContent="";
		String[] contentDiv = content.split("\\%");
		int mutiple = Integer.parseInt(contentDiv[1]); //倍数
		String [] contentPlay=contentDiv[0].split("\\:");
		playType=Integer.parseInt(contentPlay[0]);
		endContent=contentPlay[1];
		String[] contentGroup = endContent.split("\\\n");
		for(String str : contentGroup){
			String verifyContext=str;
			verifyContext=ChangeContext(verifyContext);
			if (playType == PlayType.DS.getValue()) {
				String[] lines = verifyContext.split("\\^");
				amountTest += lines.length;
				for (int i = 0; i < lines.length; i++) {
					if (lines[i].length() != 27) {
						return contentCheck;
					}

				}
			}else if (playType == PlayType.FS.getValue()) {
				String[] areas = verifyContext.split("\\,");
				int zhushu = 1;
				for (int i = 0; i < areas.length; i++) {
					zhushu = zhushu * areas[i].length();
				}
				amountTest += zhushu;
			}
		}
		if (amountTest * 2 * mutiple != amount || amount <= 0) {
			LogUtil.out("type:" + type);
			LogUtil.out("传入金额：" + amount);
			LogUtil.out("计算金额：" + amountTest * 2 * mutiple);
			contentCheck.setPass(false);
		} else {
			contentCheck.setPass(true);
			contentCheck.setMultiple(mutiple);
			contentCheck.setIsAttribute("1");
			contentCheck.setContent(endContent);
		}
		

		contentCheck.setPlayType(PlayType.getItem(playType));
		return contentCheck;
	}
	
	//把内容转换一下
	public static String ChangeContext(String content_){
		String str=content_;
		String[] content=str.split("\\(");
		StringBuffer sb=new StringBuffer();
		
		if(content.length>1){
			for(String t:content){
				String[] tem=t.split("\\)");
				if(t.indexOf(")")!=-1){
					for(int i=0;i<tem.length;i++){
			    		String text=tem[i];
			    		if(i==0){
			    			StringBuffer sb1=new StringBuffer();
			    			sb1.append(text.replaceAll("", ""));
			    			sb.append(",").append(sb1);
			    		}else{
			    			sb.append(text.replaceAll("", ",")).deleteCharAt(sb.length()-1);
			    		}
			    	}
				}else{
					String temp=tem[0];
			    	sb.append(temp.replaceAll("", ",")).deleteCharAt(sb.length()-1);
				}
			}
			
		}else{
			sb.append(str.replaceAll("", ",")).deleteCharAt(sb.length()-1);
			
		}
		//System.out.println(sb.delete(0, 1).toString());
		return sb.delete(0, 1).toString();
		
	}
	
	public static void main(String[] args) throws Exception {
		FbSfc14Parser parser = new FbSfc14Parser();
		StringBuffer sb = new StringBuffer();
		//sb.append("1:3,3,3,1,1,1,1,1,1,3,3,1,1,1");// 单式
		sb.append("2:(310)0(31)03(31)0130(31)131").append("\n").append("11133300013101");
		// sb.append("~2:3,3,3,31,31,31,31,31,31,31,31,31,31,31");//复式
		sb.append("%1");
		// +4096
		ContentCheck check = parser.checkPlan(sb.toString(), 48+2);
		LogUtil.out(check.isPass());
		LogUtil.out(check.getContent());
	}

}
