package com.yuncai.modules.lottery.factorys.verify;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

/**
 * 解析的content格式为：玩法:选号/n玩法/n选号%倍数 
 * 
 * @author lm
 * 
 */
public class QxcParser implements ParserBuilder{

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
			//内容转换
			String verifyContext=str;
			verifyContext=ChangeContext(verifyContext);
			if (playType == PlayType.FS.getValue() || playType == PlayType.DS.getValue()) {
				String[] area = verifyContext.split("\\|");
				if (area.length != 7) {
					throw new Exception("号码格式有误");
				}
				int zhushu = 1;
				for (int i = 0; i < area.length; i++) {
					zhushu *= area[i].split("\\,").length;
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
			    			sb1.append(text.replaceAll("", ",")).delete(0, 1).deleteCharAt(sb1.length()-1);
			    			sb.append(FuHao.LQ).append(sb1);
			    		}else{
			    			sb.append(text.replaceAll("", "|")).deleteCharAt(sb.length()-1);
			    		}
			    	}
				}else{
					String temp=tem[0];
			    	sb.append(temp.replaceAll("", "|")).deleteCharAt(sb.length()-1);
				}
			}
			
		}else{
			sb.append(str.replaceAll("", "|")).deleteCharAt(sb.length()-1);
			
		}
		
		return sb.delete(0, 1).toString();
		
	}
	
	public String checkPlanBuyTogether() {

		return null;
	}
	
	public int combination(int m, int n)// m为下标，n为上标
	{
		if (m < 0 || n < 0 || m < n)
			return -1;
		// 数据不符合要求，返回错误信息
		n = n < (m - n) ? n : m - n;// C(m,n)=C(m,m-n)
		if (n == 0)
			return 1;
		int result = m;// C(m,1);
		for (int i = 2, j = result - 1; i <= n; i++, j--) {
			result = result * j / i;// 得到C(m,i)
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		QxcParser parser = new QxcParser();
		StringBuffer sb = new StringBuffer();
		sb.append("2:(12)345(678)13").append("\n").append("2345813");
		sb.append("%1");
		ContentCheck check = parser.checkPlan(sb.toString(), 12 + 2);

		LogUtil.out(check.isPass());
	}

}
