package com.yuncai.modules.lottery.factorys.verify;

import com.mysql.jdbc.log.Log;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;

/**
 * 解析的content格式为：玩法:选号/n玩法/n选号%倍数 
 * @author lm
 *
 */
public class Gd11x5Parser implements ParserBuilder{

	@Override
	public ContentCheck checkPlan(String content, double amount) throws Exception {
		ContentCheck result = new ContentCheck();
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
		int f=0;
		int couttest=0;
		for(String str : contentGroup){
			 f++;
			String verifyContext=str;
			if (playType == PlayType.R1_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=verifyContext.split("\\,").length;
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.R2_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 2);
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.R3_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 3);
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.R4_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 4);
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.R5_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 5);
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.R6_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 6);
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.R7_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 7);
				amountTest += count;
				couttest=count;
				
			} else if (playType == PlayType.R8_BZ.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 8);
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.QE_ZX.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				String[] area = verifyContext.split("\\|");
				int counter = 0;

				if (area.length != 2 || area[0].equals("") || area[1].equals("")) {
					counter = 0;
				} else {
					String[] code1 = area[0].split(",");
					String[] code2 = area[1].split(",");

					for (int t = 0; t < code1.length; t++) {
						for (int j = 0; j < code2.length; j++) {
							if (!code1[t].equals(code2[j]))
								counter++;
						}
					}
				}
				amountTest += counter;
				couttest=counter;
			} else if (playType == PlayType.QS_ZX.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				String[] area =verifyContext.split("\\|");
				int counter = 0;

				if (area.length != 3 || area[0].equals("") || area[1].equals("") || area[2].equals("")) {
					counter = 0;
				} else {
					String[] code1 = area[0].split(",");
					String[] code2 = area[1].split(",");
					String[] code3 = area[2].split(",");
					for (int t = 0; t < code1.length; t++) {
						for (int j = 0; j < code2.length; j++) {
							for (int k = 0; k < code3.length; k++) {
								if (code1[t].equals(code2[j]) || code1[t].equals(code3[k]) || code2[j].equals(code3[k]))
									;
								else
									counter++;
							}
						}
					}
				}
				amountTest += counter;
				couttest=counter;
			} else if (playType == PlayType.QE_ZHUX.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 2);
				amountTest += count;
				couttest=count;
			} else if (playType == PlayType.QS_ZHUX.getValue()) {
				//验证内容
				verifyContext=ChangeContextRX(verifyContext);
				result=resultCountext(f,verifyContext,playType);
				if(!result.isPass()){
					return result;
				}
				int count=combination(verifyContext.split("\\,").length, 3);
				amountTest += count;
				couttest=count;
			} else {// 胆拖
				verifyContext=get11X5Dt(verifyContext);
				int count=getzhushu_11x5_dt(playType, verifyContext);
				amountTest += count;
				couttest=count;
			}
			if(couttest >10000){
				result.setPass(false);
				result.setMessage("第"+f+"注单张彩票金额不能超过2万元！");
				return result;
			}
			couttest=0;
		}

		if (amountTest * 2 * mutiple != amount || amount <= 0) {
			LogUtil.out("type:" + type);
			LogUtil.out("传入金额：" + amount);
			LogUtil.out("计算金额：" + amountTest * 2 * mutiple);
			result.setPass(false);
			result.setMessage("传输金额错误");
		} else {
			result.setPass(true);
			result.setMultiple(mutiple);
			result.setIsAttribute("1");
			result.setContent(endContent);
			result.setCount(amountTest);
		}
	
		result.setPlayType(PlayType.getItem(playType));
		
		return result;
	}
	
	/* 直选格式检查 */
	public boolean zhixuanCheck(String[] str, boolean isQiansan) throws Exception {
		boolean flag = true;
		String[] first = str[0].split(","); // 第一位的号码
		String[] second = str[1].split(","); // 第二位的号码
		String strName;
		if (isQiansan)
			strName = "前三";
		else
			strName = "前二";
		for (int i = 0; i < second.length; i++) { // 检查第一位和第二位的号码是否重复
			if (isExit(first, second[i])) {
				flag = false;
				throw new Exception(strName + "直选格式错误");
			}
		}
		LogUtil.out("-----------");
		if (isQiansan) {
			String[] third = str[2].split(","); // 第三位的号码
			for (int i = 0; i < third.length; i++) {
				if (isExit(second, third[i])) { // 检查第二位和第三位的号码是否重复
					flag = false;
					throw new Exception(strName + "直选格式错误");
				}
			}
		}
		return flag;
	}

	// 胆拖函数
	public int getzhushu_11x5_dt(int playType, String str) throws Exception // 胆拖投注
	// 时时乐、3d根据类型计算注数
	{

		int sum = 0;
		if (playType >= PlayType.R2_DT.getValue() && playType <= PlayType.QS_ZHUX_DT.getValue()) {

			String[] t = str.split("\\#");
			String[] danCode = t[0].split(",");
			String[] tuoCode = t[1].split(",");
			if (playType == PlayType.R2_DT.getValue()) { // 任选二胆拖
				if (danCode.length == 0 || danCode.length > 1) // 检查胆码的个数是否合格
					throw new Exception("任选二胆格式错误");
				if (danCode.length + tuoCode.length < 3)
					throw new Exception("任选二胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选二胆格式错误");
				}
				sum += combination(tuoCode.length, 2 - danCode.length);
			}
			if (playType == PlayType.R3_DT.getValue()) { // 任选三胆拖
				if (danCode.length == 0 || danCode.length > 2)
					throw new Exception("任选三胆格式错误");
				if (danCode.length + tuoCode.length < 4)
					throw new Exception("任选三胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选三胆格式错误");
				}
				sum += combination(tuoCode.length, 3 - danCode.length);
			}
			if (playType == PlayType.R4_DT.getValue()) { // 任选四胆拖
				if (danCode.length == 0 || danCode.length > 3)
					throw new Exception("任选四胆格式错误");
				if (danCode.length + tuoCode.length < 5)
					throw new Exception("任选四胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选四胆格式错误");
				}
				sum += combination(tuoCode.length, 4 - danCode.length);
			}
			if (playType == PlayType.R5_DT.getValue()) {// 任选五胆拖
				if (danCode.length == 0 || danCode.length > 4)
					throw new Exception("任选五胆格式错误");
				if (danCode.length + tuoCode.length < 6)
					throw new Exception("任选五胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选五胆格式错误");
				}
				sum += combination(tuoCode.length, 5 - danCode.length);
			}

			if (playType == PlayType.R6_DT.getValue()) {// 任选六胆拖
				if (danCode.length == 0 || danCode.length > 5)
					throw new Exception("任选六胆格式错误");
				if (danCode.length + tuoCode.length < 7)
					throw new Exception("任选六胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选六胆格式错误");
				}
				sum += combination(tuoCode.length, 6 - danCode.length);
			}
			if (playType == PlayType.R7_DT.getValue()) {// 任选7胆拖
				if (danCode.length == 0 || danCode.length > 6)
					throw new Exception("任选七胆格式错误");
				if (danCode.length + tuoCode.length < 8)
					throw new Exception("任选七胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选七胆格式错误");
				}
				sum += combination(tuoCode.length, 7 - danCode.length);
			}
			if (playType == PlayType.R8_DT.getValue()) {// 任选8胆拖
				if (danCode.length == 0 || danCode.length > 7)
					throw new Exception("任选八胆格式错误");
				if (danCode.length + tuoCode.length < 9)
					throw new Exception("任选八胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选八胆格式错误");
				}
				sum += combination(tuoCode.length, 8 - danCode.length);
			}
			
			//2011-7-12 add 
			if (playType == PlayType.QE_ZHUX_DT.getValue()) { // 前二组选胆拖
				if (danCode.length == 0 || danCode.length > 1) // 检查胆码的个数是否合格
					throw new Exception("前二组选胆格式错误");
				if (danCode.length + tuoCode.length < 3)
					throw new Exception("前二组选胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("前二组选胆格式错误");
				}
				sum += combination(tuoCode.length, 2 - danCode.length);
			}
			
			if (playType == PlayType.QS_ZHUX_DT.getValue()) { // 前三组选胆拖
				if (danCode.length == 0 || danCode.length > 2)
					throw new Exception("任选三胆格式错误");
				if (danCode.length + tuoCode.length < 4)
					throw new Exception("任选三胆格式错误");
				for (int i = 0; i < danCode.length; i++) {
					if (isExit(tuoCode, danCode[i])) // 检查胆码和拖码是否有重复的号码
						throw new Exception("任选三胆格式错误");
				}
				sum += combination(tuoCode.length, 3 - danCode.length);
			}
		}
		return sum;
	}

	public boolean isExit(String[] str, String lan) // 判断lan串是否在数组str中
	{
		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i].equals(lan))
				return true;
		}
		return false;
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
	
	public ContentCheck resultCountext(int i,String lines,int playType){
		ContentCheck  result=new ContentCheck();
		if (playType == PlayType.R1_BZ.getValue()||playType == PlayType.R2_BZ.getValue()||playType == PlayType.R3_BZ.getValue()
				|| playType == PlayType.R4_BZ.getValue() ||playType == PlayType.R5_BZ.getValue() ||playType == PlayType.R6_BZ.getValue()||playType == PlayType.R7_BZ.getValue()
				||playType == PlayType.R8_BZ.getValue() || playType == PlayType.QE_ZHUX.getValue() || playType == PlayType.QS_ZHUX.getValue()) {
			String[] area=lines.split("\\,");
			result=ParserLine(area,i,1);
			if(!result.isPass()){
				return result;
			}
		}else if (playType == PlayType.QE_ZX.getValue() || playType == PlayType.QS_ZX.getValue()) {
			String[] area=lines.split("\\|");
			result=ParserZXTd(area,i,1);
			if(!result.isPass()){
				return result;
			}
		}
		result.setPass(true);
		return result;
	}
	
	public ContentCheck ParserLine(String[] lines,int i,int type){
		ContentCheck result=new ContentCheck();
		for(int j=0; j<lines.length;j++ ){
			int code = 0;
			String numLen=lines[j].toString();
			if(numLen.length()!=2){
				result.setPass(false);
				result.setMessage("组装格式错误!");
				return result;
			}
			try {
				code = Integer.parseInt(lines[j]);
			} catch (Exception e) {
				e.printStackTrace();
				result.setPass(false);
				result.setMessage("号码必须为数字");
				return result;
			}
			if(type==1){
				if (code < 1 || code > 11) {// ,大于1小于33
					result.setPass(false);
					result.setMessage("第" +  i + "注的第" + (j + 1) + "个号码必须大于等于1并小于等于11");
					return result;
				}
			}
		}
		result.setPass(true);
		return result;
	}
	public ContentCheck ParserZXTd(String[] lines,int i,int type){
		ContentCheck result=new ContentCheck();
			for(int j=0; j<lines.length;j++ ){
				String[] codes=lines[j].split("\\,");
				for(int n=0;n<codes.length;n++){
					int code = 0;
					String numLen=codes[n].toString();
					if(numLen.length()!=2){
						result.setPass(false);
						result.setMessage("组装格式错误!");
						return result;
					}
					try {
						code = Integer.parseInt(codes[n]);
					} catch (Exception e) {
						e.printStackTrace();
						result.setPass(false);
						result.setMessage("号码必须为数字");
						return result;
					}
					if(type==1){
						if (code <1 || code > 11) {// ,大于1小于33
							result.setPass(false);
							result.setMessage("第" +  i + "注的第" + (j + 1) + "个号码必须大于等于1并小于等于11");
							return result;
						}
					}
				}
				
			
		}
		result.setPass(true);
		return result;
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
	//直选
	public static String ChangeContextRX(String content){
		StringBuffer sb=new StringBuffer();
		sb.append(content.trim().replaceAll(" ", ","));
		return sb.toString();
	}
	
	//胆拖
	public static String get11X5Dt(String content){
		String[] strContent=content.split(",");
		String dat=strContent[0].trim().replaceAll(" ", ",");
		String tan=strContent[1].trim().replaceAll(" ", ",");
		String con=dat+"#"+tan;
		return con;
	}
	
	
	public static void main(String[] args) {
		String str="01 , 02 03 04";
		String temp=get11X5Dt(str);
		System.out.println(temp);
	}
	
}
