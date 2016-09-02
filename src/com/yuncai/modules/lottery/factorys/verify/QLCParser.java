package com.yuncai.modules.lottery.factorys.verify;

import java.util.HashMap;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

/**七乐彩验证器
 * @author Administrator
 *
 */
public class QLCParser implements ParserBuilder{

	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		LogUtil.out("DltParser content="+content);
		//3:01 02 03 04 , 05 06 07 08 09%1
		ContentCheck contentCheck = new ContentCheck();
		int amountTest = 0;
		int type = 0;
		int playType = 0;
		int f=0;
		int counttest=0;  //单注数
		String endContent="";
		String[] contentDiv = content.split("\\%");
		int mutiple = Integer.parseInt(contentDiv[1]); //倍数
		String [] contentPlay=contentDiv[0].split("\\:");
		playType=Integer.parseInt(contentPlay[0]);
		endContent=contentPlay[1];
		//01 02 03 04 , 05 06 07 08 09
		String[] contentGroup = endContent.split("\\\n");
		for(String str : contentGroup){
			 f++;
			String verifyContext=str;
			if (playType == PlayType.DS.getValue()) {
				String[] lines = verifyContext.split("\\^");
				//验证内容
				contentCheck=resultCountext(f,qlcFS(lines[0]),playType);
				if(!contentCheck.isPass()){
					return contentCheck;
				}
				amountTest += lines.length;
				counttest=lines.length;
				for (int i = 0; i < lines.length; i++) {
					String term=qlcFS(lines[i]);
					if (term.length() != 20) {
						amountTest = -99999999;
					}
				}
			}else if(playType==PlayType.FS.getValue()){
				//验证内容
				// 05 06 07 08 09 10 11 12 
				verifyContext=qlcFS(verifyContext);
				//05,06,07,08,09,10,11,12
				contentCheck=resultCountext(f,verifyContext,playType);
				if(!contentCheck.isPass()){
					return contentCheck;
				}
				
				int count = combination(verifyContext.split("\\,").length, 7)  ;
				amountTest += count;
                counttest=count;
			}else if(playType==PlayType.DT.getValue()){
				//验证内容
				verifyContext=qlcDT(verifyContext);
				////04 05 06#08 09 10
				//01,02,03,04#05,06,07,08,09
				contentCheck=resultCountext(f,verifyContext,playType);
				if(!contentCheck.isPass()){
					return contentCheck;
				}
//				String[] redAndBlue = verifyContext.split("\\|");
//				//[01,02,03,04#05,06,07,08,09, 07,08,09,10]
//				String red = redAndBlue[0].trim();
//				//01,02,03,04#05,06,07,08,09
//				String blue = redAndBlue[1].trim();
				//07,08,09,10 
				String dan = verifyContext.split("\\#")[0].trim();
				//01,02,03,04 胆
				String tuo = verifyContext.split("\\#")[1].trim();
				//05,06,07,08,09 拖码
				String[] dan2 = new String[] {};
				int danCount = 0;
				if (!"".equals(dan)) {
					danCount = dan.split("\\,").length;
				}
				int tuoCount = tuo.split("\\,").length;

				
				int count = combination(tuoCount,7 - danCount) ;
				
				amountTest += count;
                counttest=count;
			}
			
			if(counttest >10000){
				contentCheck.setPass(false);
				contentCheck.setMessage("第"+f+"注单张彩票金额不能超过2万元！");
				return contentCheck;
			}
			counttest=0;
			
		}
		if (amountTest * 2 * mutiple  != amount || amount <= 0) {
			LogUtil.out("type:" + type);
			LogUtil.out("传入金额：" + amount);
			LogUtil.out("计算金额：" + amountTest * 2 * mutiple );
			contentCheck.setPass(false);
			contentCheck.setMessage("传入金额错误");
		} else {
			contentCheck.setPass(true);
			contentCheck.setMultiple(mutiple);
//			contentCheck.setIsAttribute(add);
			contentCheck.setContent(endContent);
			contentCheck.setCount(amountTest);
		}
		

		contentCheck.setPlayType(PlayType.getItem(playType));
		return contentCheck;
	}
	
	//把内容转换一下
	public static String qlcFS(String content){
		//// 05 06 07 08 09 10 11 12 
		return content.trim().replaceAll(" ", "\\,");
	}
	public static String qlcDT(String content){
		//04 05 06 , 08 09 10 
		String[] redAndBlue = content.split("\\+");
		String[] danStr=redAndBlue[0].trim().split("\\,");
		String dan1=danStr[0].trim().replaceAll(" ", "\\,");
		String dan2=danStr[1].trim().replaceAll(" ", "\\,");
		return dan1+FuHao.DT+dan2;
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
	
	/**
	 * 基本玩法号码格式检查
	 * @param i         第几行
	 * @param lines     行内容
	 * @param playType  玩法类型
	 * @return 返回检查器
	 */
	public ContentCheck resultCountext(int i,String lines,int playType){
	////05,06,07,08,09,10,11,12
		ContentCheck  result = new ContentCheck();
		
		////04 05 06#08 09 10
		//前区个数判断
		String[] pres = lines.replaceAll("\\#","\\,").split("\\,");//处理胆拖的分隔符#
		//05,06,07,08,09,10,11,12
		if(pres.length < 7){
			result.setPass(false);
			result.setMessage("第" + i  + "行号码个数不足!");
			return result;
		}
		
		
		
		//,数字,重复判断
		HashMap<Integer,Integer> preMap = new HashMap<Integer,Integer>();
		for(int k = 0; k < pres.length; k++) {
			try {
				if(pres[k].length() != 2) {
					result.setPass(false);
					result.setMessage("第" +  i + "行前区号码必须为两位格式,如:01");
					return result;
				}
				
				Integer code = Integer.parseInt(pres[k]);
				if (code < 1 || code > 30) {
					result.setPass(false);
					result.setMessage("第" +  i + "行前区号码必须大于等于1并小于等于30!");
					return result;
				}
				preMap.put(code, code);
			} catch (Exception e) {
				e.printStackTrace();
				result.setPass(false);
				result.setMessage("第" + i  + "行前区号码必须为数字!");
				return result;
			}
		}
		if(preMap.size() != pres.length) {
			result.setPass(false);
			result.setMessage("第" + i  + "行前区号码有重复!");
			return result;
		}
		
		
		//胆拖玩法特别处理
		//01,02,03,04#05,06,07,08,09
		if(playType == PlayType.DT.getValue()){
			String[] preDantuo = lines.split("\\#");
			if(preDantuo.length != 2) {
				result.setPass(false);
				result.setMessage("第" + i  + "行胆拖格式错误!");
				return result;
			}
			
			//前区
			int preDanCount = preDantuo[0].split("\\,").length;
			int preTuoCount = preDantuo[1].split("\\,").length;
			if(preDanCount < 1 ||  preDanCount >6){
				result.setPass(false);
				result.setMessage("第" + i + "行胆码个数必须大于等于1并小于等于6!");
				return result;
			}
			
			if(preTuoCount < 1){
				result.setPass(false);
				result.setMessage("第" + i + "行前区拖码个数必须大于等于1!");
				return result;
			}
			
		}
		
		result.setPass(true);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
//		String str="04 05 06 , 08 09 10 + 08 09 10";
//		System.out.println(dltDT(str));
		
		QLCParser parser = new QLCParser();
		StringBuffer sb = new StringBuffer();
		//sb.append("2:04 07 17 20 33 + 05 06").append("\n").append("07 08 12 13 28 30 + 01 10 02");
//		sb.append("2:01 02 03 04 05 07 08 09").append("\n").append("04 05 12 15 16 18 22 24 26 27");
		sb.append("3:01 02 03 04 , 05 09 13 15 18");
//		sb.append("2:01 05 12 20 21 26 33+05 07 16 ");
//		sb.append("%1-1");
		sb.append("%3");
		ContentCheck check = parser.checkPlan(sb.toString(), 60);
		LogUtil.out(check.isPass());
//		LogUtil.out(check.getMessage());
		LogUtil.out(check.getContent());
		
		
	}

}
