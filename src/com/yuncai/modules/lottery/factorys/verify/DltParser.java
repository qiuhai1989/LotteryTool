package com.yuncai.modules.lottery.factorys.verify;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import java.util.HashMap;

/**
 * 解析的content格式为：玩法:选号/n玩法/n选号%倍数-追加 
 * 
 * @author lm
 * 
 */

public class DltParser implements ParserBuilder{

	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		String[] ts = content.split("\\-");
		String add = ts[1];
		int needAddCount = 0;
		content = ts[0];
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
		String[] contentGroup = endContent.split("\\\n");
		for(String str : contentGroup){
			 f++;
			String verifyContext=str;
			if (playType == PlayType.DS.getValue()) {
				String[] lines = verifyContext.split("\\^");
				//验证内容
				contentCheck=resultCountext(f,dltFS(lines[0]),playType);
				if(!contentCheck.isPass()){
					return contentCheck;
				}
				amountTest += lines.length;
				counttest=lines.length;
				if (add.equals("0")) {
					needAddCount += lines.length;
				}
				for (int i = 0; i < lines.length; i++) {
					String term=dltFS(lines[i]);
					if (term.length() != 20) {
						amountTest = -99999999;
					}
				}
			}else if(playType==PlayType.FS.getValue()){
				//验证内容
				verifyContext=dltFS(verifyContext);
				contentCheck=resultCountext(f,verifyContext,playType);
				if(!contentCheck.isPass()){
					return contentCheck;
				}
				
				int count = combination(verifyContext.split("\\|")[0].split("\\,").length, 5) * combination(verifyContext.split("\\|")[1].split("\\,").length, 2);
				amountTest += count;
				if (add.equals("0")) {
					needAddCount += count;
				}
                counttest=count;
			}else if(playType==PlayType.DT.getValue()){
				//验证内容
				verifyContext=dltDT(verifyContext);
				contentCheck=resultCountext(f,verifyContext,playType);
				if(!contentCheck.isPass()){
					return contentCheck;
				}
				String[] redAndBlue = verifyContext.split("\\|");
				String red = redAndBlue[0].trim();
				String blue = redAndBlue[1].trim();
				String dan = red.split("\\#")[0].trim();
				String tuo = red.split("\\#")[1].trim();
				
				String[] dan2 = new String[] {};
				if (blue.length() > 0)
					dan2 = blue.trim().split("\\,");
				
				int danCount = 0;
				if (!"".equals(dan)) {
					danCount = dan.split("\\,").length;
				}
				int tuoCount = tuo.split("\\,").length;

				int blueDanCount = 0;
				
				int blueTuoCount = dan2.length;
				int lendan=blueTuoCount-2;
				int count = combination(tuoCount, 5 - danCount) * combination(blueTuoCount, blueTuoCount-lendan);
				
				amountTest += count;
				if (add.equals("0")) {
					needAddCount += count;
				}
                counttest=count;
			}
			
			if(counttest >10000){
				contentCheck.setPass(false);
				contentCheck.setMessage("第"+f+"注单张彩票金额不能超过2万元！");
				return contentCheck;
			}
			counttest=0;
			
		}
		if (amountTest * 2 * mutiple + needAddCount * mutiple != amount || amount <= 0) {
			LogUtil.out("type:" + type);
			LogUtil.out("传入金额：" + amount);
			LogUtil.out("计算金额：" + amountTest * 2 * mutiple + needAddCount * mutiple);
			contentCheck.setPass(false);
			contentCheck.setMessage("传入金额错误");
		} else {
			contentCheck.setPass(true);
			contentCheck.setMultiple(mutiple);
			contentCheck.setIsAttribute(add);
			contentCheck.setContent(endContent);
			contentCheck.setCount(amountTest);
		}
		

		contentCheck.setPlayType(PlayType.getItem(playType));
		return contentCheck;
	}
	
	//把内容转换一下
	public static String dltFS(String content){
		String[] redAndBlue = content.split("\\+");
		String dan=redAndBlue[0].trim().replaceAll(" ", "\\,");
		String tan=redAndBlue[1].trim().replaceAll(" ", "\\,");
		return dan+FuHao.LQ+tan;
	}
	public static String dltDT(String content){
		//04 05 06 , 08 09 10 + 08 09 10   ||~3:07,09,03#08,16,17|05#01
		String[] redAndBlue = content.split("\\+");
		String[] danStr=redAndBlue[0].trim().split("\\,");
		String dan1=danStr[0].trim().replaceAll(" ", "\\,");
		String dan2=danStr[1].trim().replaceAll(" ", "\\,");
		String tan=redAndBlue[1].trim().replaceAll(" ", "\\,");
		return dan1+FuHao.DT+dan2+FuHao.LQ+tan;
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
		ContentCheck  result = new ContentCheck();
		String[] codes = lines.split("\\|");
		
		//格式判断
		if(codes.length != 2) {
			result.setPass(false);
			result.setMessage("第" + i  + "行号码格式错误!");
			return result;
		}
		
		//前区个数判断
		String[] pres = codes[0].replaceAll("\\#","\\,").split("\\,");//处理胆拖的分隔符#
		if(pres.length < 5){
			result.setPass(false);
			result.setMessage("第" + i  + "行前区号码个数不足!");
			return result;
		}
		
		//后区个数判断
		String[] poss = codes[1].replaceAll("\\#","\\,").split("\\,");//处理胆拖的分隔符#
		if(poss.length < 2){
			result.setPass(false);
			result.setMessage("第" + i  + "行后区号码个数不足!");
			return result;
		}
		
		
		//前区范围,数字,重复判断
		HashMap<Integer,Integer> redMap = new HashMap<Integer,Integer>();
		for(int k = 0; k < pres.length; k++) {
			try {
				if(pres[k].length() != 2) {
					result.setPass(false);
					result.setMessage("第" +  i + "行前区号码必须为两位格式,如:01");
					return result;
				}
				
				Integer code = Integer.parseInt(pres[k]);
				if (code < 1 || code > 35) {
					result.setPass(false);
					result.setMessage("第" +  i + "行前区号码必须大于等于1并小于等于35!");
					return result;
				}
				redMap.put(code, code);
			} catch (Exception e) {
				e.printStackTrace();
				result.setPass(false);
				result.setMessage("第" + i  + "行前区号码必须为数字!");
				return result;
			}
		}
		if(redMap.size() != pres.length) {
			result.setPass(false);
			result.setMessage("第" + i  + "行前区号码有重复!");
			return result;
		}
		
		//后区范围,数字,重复判断
		HashMap<Integer,Integer> blueMap = new HashMap<Integer,Integer>();
		for(int k = 0; k < poss.length; k++) {
			try {
				if(poss[k].length() != 2) {
					result.setPass(false);
					result.setMessage("第" +  i + "行后区号码必须为两位格式,如:01");
					return result;
				}
				
				Integer code = Integer.parseInt(poss[k]);
				if(code < 1 || code > 12){
					result.setPass(false);
					result.setMessage("第" +  i + "行后区号码必须大于等于1并小于等于12!");
					return result;
			    }
				blueMap.put(code, code);
			} catch (Exception e) {
				e.printStackTrace();
				result.setPass(false);
				result.setMessage("第" + i  + "行后区号码必须为数字!");
				return result;
			}
		}
		if(blueMap.size() != poss.length) {
			result.setPass(false);
			result.setMessage("第" + i  + "行后区有重复号码!");
			return result;
		}
		
		//胆拖玩法特别处理
		if(playType == PlayType.DT.getValue()){
			String[] preDantuo = codes[0].split("\\#");
			String[] posDantuo = codes[1].split("\\,");
			if(preDantuo.length != 2) {
				result.setPass(false);
				result.setMessage("第" + i  + "行胆拖格式错误!");
				return result;
			}
			
			//前区
			int preDanCount = preDantuo[0].split("\\,").length;
			int preTuoCount = preDantuo[1].split("\\,").length;
			if(preDanCount < 1 ||  preDanCount > 5){
				result.setPass(false);
				result.setMessage("第" + i + "行前区胆码个数必须大于等于1并小于等于5!");
				return result;
			}
			
			if(preTuoCount < 1){
				result.setPass(false);
				result.setMessage("第" + i + "行前区拖码个数必须大于等于1!");
				return result;
			}
			
			//后区
			//int posDanCount = posDantuo[0].split("\\,").length;
			int posTuoCount = posDantuo[1].split("\\,").length;
//			if(posDanCount != 1){
//				result.setPass(false);
//				result.setMessage("第" + i + "行后区胆码个数只能是1!");
//				return result;
//			}
			
			if(posTuoCount < 1){
				result.setPass(false);
				result.setMessage("第" + i + "行后区拖码个数必须大于等于1!");
				return result;
			}
		}
		
		result.setPass(true);
		return result;
	}
	
	public static void main(String[] args) throws Exception {
//		String str="04 05 06 , 08 09 10 + 08 09 10";
//		System.out.println(dltDT(str));
		
		DltParser parser = new DltParser();
		StringBuffer sb = new StringBuffer();
		//sb.append("2:04 07 17 20 33 + 05 06").append("\n").append("07 08 12 13 28 30 + 01 10 02");
		sb.append("3:01 02 03 04 , 05 06 07 08 09 + 07 08 09 10");
		sb.append("%1-1");
		ContentCheck check = parser.checkPlan(sb.toString(), 60);
		LogUtil.out(check.isPass());
		LogUtil.out(check.getContent());
		
	}

}
