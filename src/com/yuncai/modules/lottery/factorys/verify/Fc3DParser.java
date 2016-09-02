package com.yuncai.modules.lottery.factorys.verify;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

/**福彩3D解析器
 * @author Administrator
 *
 */
public class Fc3DParser implements ParserBuilder{

	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		ContentCheck contentCheck = new ContentCheck();
		LogUtil.out(content);
		int amountTest = 0;
		int type = 0;
		int playType = 0;
		String endContent="";
		String[] contentDiv = content.split("\\%");
		int mutiple = Integer.parseInt(contentDiv[1]); //倍数
		String [] contentPlay=contentDiv[0].split("\\:");
		playType=Integer.parseInt(contentPlay[0]);
		endContent=contentPlay[1];
		
		if(playType== PlayType.ZXHZ.getValue() || playType == PlayType.ZSHZ.getValue()){
			endContent=endContent.replaceAll("\r\n", "|");
		}
		String[] contentGroup = endContent.split("\\\n");
		
		for(String str : contentGroup){
			String verifyContext=str;
			if (playType == PlayType.ZX.getValue() || playType == PlayType.ZXFS.getValue()) {
				verifyContext=ChangeContext(verifyContext);
				String[] area = verifyContext.split("\\|");
				int zhushu = 1;
				for (int i = 0; i < area.length; i++) {
					zhushu *= area[i].split("\\,").length;
				}
				amountTest += zhushu;
			}else if (playType == PlayType.ZS.getValue()) {
				amountTest += 2 * combination(verifyContext.length(), 2);
			}else if (playType == PlayType.ZL.getValue()) {
				amountTest += combination(verifyContext.length(), 3);
			}else if (playType == PlayType.ZXHZ.getValue()) {
				String[] area = verifyContext.split("\\|");
				for (int i = 0; i < area.length; i++) {
					amountTest += zxhz(Integer.parseInt(area[i]));
				}
			}else if (playType == PlayType.ZSHZ.getValue()) {
				String[] area = verifyContext.split("\\|");
				for (int i = 0; i < area.length; i++) {
					amountTest += z3hz(Integer.parseInt(area[i]));
				}
			}else if (playType == PlayType.ZLHZ.getValue()) {
				String[] area = verifyContext.split("\\|");
				for (int i = 0; i < area.length; i++) {
					amountTest += z6hz(Integer.parseInt(area[i]));
				}
			} 
		}
		if (amountTest * 2 * mutiple != amount || amount <= 0) {
			//LogUtil.out("type:" + type);
			//LogUtil.out("传入金额：" + amount);
			//LogUtil.out("计算金额：" + amountTest * 2 * mutiple);
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
		//System.out.println(sb.delete(0, 1).toString());
		return sb.delete(0, 1).toString();
		
	}
	public String ChangeContextZX(String context){
		StringBuffer sb=new StringBuffer();
		sb.append(context.replaceAll("", ",")).delete(0, 1).deleteCharAt(sb.length()-1);
		return sb.toString();
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

	public int zxhz(int n) {
		int z = 0;
		if (n > 14)
			n = 27 - n;

		for (int i = 0; i <= 9; i++)
			for (int j = 0; j <= 9; j++)
				for (int k = 0; k <= 9; k++) {
					if (i + j + k == n)
						z++;
				}
		return z;
	}

	public int z3hz(int n) {
		int zu = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = i; j < 10; j++) {
				for (int k = j; k < 10; k++) {
					if (i + j + k == n) {
						if ((i == j && i == k) || i != j && i != k && j != k) {
							zu = zu;
						} else {
							zu++;
						}
					}
				}
			}
		}
		return zu;
	}

	public int z6hz(int n) {
		int zu = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = i + 1; j < 9; j++) {

				for (int k = j + 1; k < 10; k++) {
					if (i + j + k == n)
						zu++;
				}

			}
		}
		return zu;
	}
	
	public int zxKd(int value){
		if(value == 0){
			return 10;
		}
		if(value == 1){
			return 54;
		}
		if(value == 2){
			return 96;
		}
		if(value == 3){
			return 126;
		}
		if(value == 4){
			return 144;
		}
		if(value == 5){
			return 150;
		}
		if(value == 6){
			return 144;
		}
		if(value == 7){
			return 126;
		}
		if(value == 8){
			return 96;
		}
		if(value == 9){
			return 54;
		}
		return 0;
	}
}
