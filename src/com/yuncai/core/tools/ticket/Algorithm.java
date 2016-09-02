package com.yuncai.core.tools.ticket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;


public class Algorithm {
	//
	public final static String FC_15_X_5 = "^((([0][1-9]|[1][0-5])\\s){4})((([0][1-9]|[1][0-5])){1})$";
	public final static String FB_SFP_14 = "^([310]{14})$";
	public final static String FB_SFP_9 = "^([310\\-]{14})$";
	public final static String FB_JQ_4 = "^([0123]{8})$";
	public final static String FB_BQC_6 = "^([310]{12})$";
	public final static String F_6 = "^([310]{12})$";
	public final static String F_4 = "^([3102]{8})$";

	/**
	 * 单式订单上传方案内容检查
	 * 
	 * @param content
	 * @param regular
	 * @return List
	 */
	public static List checkOut(String content, String regular) {
		// com.cailele.lottery.tools.LogUtil.out("--------"+content);
		List list = new ArrayList();
		Pattern p = Pattern.compile(regular, Pattern.MULTILINE);
		Matcher m = p.matcher(content);

		while (m.find()) {
			list.add(m.group());
		}
		return list;
	}

	/**
	 * 单式拆票(单色球格式的String均可)
	 * 
	 * 
	 */

	public static List ds(String content) {
		List strList = new ArrayList();
		content = content.split("!")[0];
		String str[] = content.split("\\" + FuHao.ZS);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			String dsStr;
			if (str[i].indexOf("%") < 0) {
				dsStr = str[i].split(String.valueOf(FuHao.WF))[0];
			} else {
				dsStr = str[i].split(String.valueOf(FuHao.WF))[1];
			}
			int num = i + 1;
			if (i != 0 && num % 5 == 0) {
				sb.append(dsStr);
				strList.add(sb.toString());
				sb.delete(0, sb.length());
			} else {
				if (i == str.length - 1) {
					sb.append(dsStr);
				} else {
					sb.append(dsStr + FuHao.ZS);
				}
			}
		}
		if (sb.length() > 0)
			strList.add(sb.toString());
		return strList;
	}

	/**
	 * 任选玩法胆拖拆票，全单式，一行一注
	 * 
	 * @param content
	 * @param codeCount
	 * @return
	 */
	public static List<String> rxDt(String content, int codeCount) {
		String danStr = content.substring(0, content.indexOf(FuHao.DT));
		String[] dan = danStr.split(FuHao.DH);
		String[] tuo = content.substring(content.indexOf(FuHao.DT) + 1).split(FuHao.DH);

		List<String> strList = new ArrayList<String>();
		int num = codeCount - dan.length;
		// 生成拖的组合排序
		List<String> tuoList = Algorithm.combine(tuo, tuo.length, num);
		// 拆票

		for (String str : tuoList) {
			StringBuffer sb = new StringBuffer();
			sb.append(danStr).append(FuHao.DH).append(str);
			String[] codes = sb.toString().split(FuHao.DH);
			// 排序
			maoPaoSort(codes);
			strList.add(StringTools.convertArrayToString(codes, FuHao.DH));
		}

		return strList;
	}

	// 数组冒泡排序
	public static void maoPaoSort(String[] x) {
		for (int i = 0; i < x.length; i++) {
			for (int j = i + 1; j < x.length; j++) {
				if (x[i].compareTo(x[j]) > 0) {
					String temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
			}
		}

	}

	/**
	 * 胆拖拆票(单色球胆拖格式)
	 */
	public static List dt(String content) {
		// 将单色球格式拆分成胆、拖、蓝球三个数组
		String[] redAndBlue = content.split("\\|");
		String danStr = redAndBlue[0].split("\\#")[0];
		String[] dan = new String[] {};
		if (danStr.length() > 0)
			dan = redAndBlue[0].split("\\#")[0].split("\\,");
		String[] tuo = redAndBlue[0].split("\\#")[1].split("\\,");
		String[] lq = redAndBlue[1].split("\\,");
		// 将胆拖玩法转换成单式玩法
		// String preStr=PlayType.DS.getValue()+String.valueOf(FuHao.WF);
		// String endStr=content.substring(content.indexOf(FuHao.BS));
		List<String> strList = new ArrayList();
		int ssqNum = 6;
		int num = 6 - dan.length;
		int count = 1;

		// 生成拖的组合排序
		List<String> tuoList = Algorithm.combine(tuo, tuo.length, num);

		// 拆票
		StringBuffer sb = new StringBuffer();
		for (String str : tuoList) {
			for (int i = 0; i < lq.length; i++) {
				if (count == 5) {
					if (danStr.length() > 0)
						sb.append(danStr).append(FuHao.DH);
					sb.append(str).append(FuHao.LQ).append(lq[i]);
					strList.add(sb.toString());
					sb.delete(0, sb.length());
					count = 1;
				} else {
					if (danStr.length() > 0)
						sb.append(danStr).append(FuHao.DH);
					sb.append(str).append(FuHao.LQ).append(lq[i]).append(FuHao.ZS);
					count++;
				}
			}
		}
		if (sb.length() > 0)
			strList.add(sb.toString().substring(0, sb.toString().length() - 1));
		return strList;
	}

	
	/**
	 * 大乐透托胆拆票
	 * @param content
	 * @return
	 */
	public static List dltDtL(String content) {
		// 将单色球格式拆分成前区胆、拖、后区胆拖 四个数组
		String[] redAndBlue = content.split("\\+");
		String danStr = redAndBlue[0].trim().split("\\,")[0];
		String danStr2 = redAndBlue[1].trim();
		String[] dan = new String[] {};
		if (danStr.length() > 0)
			dan = redAndBlue[0].trim().split("\\,")[0].trim().split(" ");
		String[] tuo = redAndBlue[0].trim().split("\\,")[1].trim().split(" ");
		String[] dan2 = new String[] {};
		if (danStr2.length() > 0)
			dan2 = redAndBlue[1].trim().split(" ");
		String[] tuo2 =dan2; //redAndBlue[1].trim().split("\\,")[1].trim().split(" ");

		// 将胆拖玩法转换成单式玩法
		List<String> strList = new ArrayList();
		int count = 1;
		
		danStr=danStr.trim().replaceAll(" ", "\\,");
		danStr2=danStr2.trim().replaceAll(" ", "\\,");

		// 生成前区拖的组合排序
		List<String> tuoList = Algorithm.combine(tuo, tuo.length, 5 - dan.length);
		int lendan=dan2.length-2;
		// 生成后区拖的组合排序
		List<String> tuoList2 = Algorithm.combine(tuo2, tuo2.length, dan2.length-lendan);

		// 拆票
		StringBuffer sb = new StringBuffer();
		for (String str : tuoList) {
			for (String str2 : tuoList2) {
				if (count == 5) {
					if (danStr.length() > 0)
						sb.append(danStr).append(FuHao.DH);
					sb.append(str).append(FuHao.LQ);
					if (danStr2.length() > 0)
						//sb.append(danStr2).append(FuHao.DH);
					sb.append(str2);

					strList.add(sb.toString());

					sb.delete(0, sb.length());
					count = 1;
				} else {
					if (danStr.length() > 0)
						sb.append(danStr).append(FuHao.DH);
					sb.append(str).append(FuHao.LQ);
					if (danStr2.length() > 0)
						//sb.append(danStr2).append(FuHao.DH);
					sb.append(str2).append(FuHao.ZS);
					count++;
				}
			}
		}
		if (sb.length() > 0)
			strList.add(sb.toString().substring(0, sb.toString().length() - 1));
		return strList;
	}

	/**
	 * 组合排序算法(回溯方法)
	 * 
	 * @param str
	 *            需要排序的字符串
	 * @param n
	 *            拖的个数
	 * @param m
	 *            拖中要选的个数(双色球则是6-胆的个数)
	 * @return
	 */
	public static List<String> combine(String str[], int n, int m) {
		return combineAppend(str, n, m, FuHao.DH);
	}
	
	public static List<String> combineL(String str[], int n, int m) {
		return combineAppend(str, n, m, FuHao.DHL);
	}

	/**
	 * 组合排序算法(回溯方法)
	 * 
	 * @param str
	 *            需要排序的字符串
	 * @param n
	 *            拖的个数
	 * @param m
	 *            拖中要选的个数(双色球则是6-胆的个数)
	 * @return
	 */
	public static List<String> combineWithSliptChat(String str[], int n, int m, String sliptChar) {
		return combineAppend(str, n, m, sliptChar);
	}

	/**
	 * 组合排序算法(回溯方法)
	 * 
	 * @param str
	 *            需要排序的字符串
	 * @param n
	 *            拖的个数 如果为-1，则默认为str的长度
	 * @param m
	 *            拖中要选的个数
	 * @return
	 */
	public static List<String> combineAppend(String str[], int n, int m, String composeCode) {
		if (n == -1) {
			n = str.length;
		}
		m = m > n ? n : m;
		List<String> list = new ArrayList();
		int[] order = new int[m + 1];
		for (int i = 0; i <= m; i++)
			order[i] = i - 1; // 注意这里order[0]=-1用来作为循环判断标识

		int count = 0;
		int k = m;
		boolean flag = true; // 标志找到一个有效组合
		while (order[0] == -1) {
			if (flag) // 输出符合要求的组合
			{
				StringBuffer sb = new StringBuffer();
				for (int j = 1; j <= m; j++) {
					count++;
					if (j != m) {
						sb.append(str[order[j]] + composeCode);
					} else {
						sb.append(str[order[j]]);
					}
				}
				flag = false;
				list.add(sb.toString());
			}
			order[k]++; // 在当前位置选择新的数字
			if (order[k] == n) // 当前位置已无数字可选，回溯
			{
				order[k--] = 0;
				continue;
			}
			if (k < m) // 更新当前位置的下一位置的数字
			{
				order[++k] = order[k - 1];
				continue;
			}
			if (k == m)
				flag = true;
		}
		return list;

	}

	/**
	 * 组合排序算法(回溯方法)
	 * 
	 * @param str
	 *            需要排序的字符串
	 * @param n
	 *            拖的个数 如果为-1，则默认为str的长度
	 * @param m
	 *            拖中要选的个数
	 * @return
	 */
	public static List<DcReturnBean> combineDc(String str[], int n, int m) {
		if (n == -1) {
			n = str.length;
		}
		m = m > n ? n : m;
		List<DcReturnBean> list = new ArrayList();
		int[] order = new int[m + 1];
		for (int i = 0; i <= m; i++)
			order[i] = i - 1; // 注意这里order[0]=-1用来作为循环判断标识

		int count = 0;
		int sum = 1;
		int k = m;
		boolean flag = true; // 标志找到一个有效组合
		while (order[0] == -1) {
			if (flag) // 输出符合要求的组合
			{
				StringBuffer sb = new StringBuffer();
				for (int j = 1; j <= m; j++) {
					count++;
					sum *= str[order[j]].split("\\,").length;
					String code = str[order[j]];
					if (code.charAt(0) == '(') {
						// 该位是个胆，应该把胆的标志去掉
						code = code.substring(1, code.length() - 1);
					}
					if (j != m) {
						sb.append(code).append(";");
					} else {
						sb.append(code);
					}
				}
				flag = false;
				DcReturnBean bean = new DcReturnBean();
				bean.setContent(sb.toString());
				bean.setCount(sum);
				sum = 1;
				list.add(bean);
			}
			order[k]++; // 在当前位置选择新的数字
			if (order[k] == n) // 当前位置已无数字可选，回溯
			{
				order[k--] = 0;
				continue;
			}
			if (k < m) // 更新当前位置的下一位置的数字
			{
				order[++k] = order[k - 1];
				continue;
			}
			if (k == m)
				flag = true;
		}
		return list;
	}

	/**
	 * 胆拖拆票(单场格式)
	 */
	public static List<DcReturnBean> combineDcDt(String[] dan, String[] tuo, String danString, int m) {
		/*
		 * //将单色球格式拆分成胆、拖、蓝球三个数组 String
		 * danStr=content.substring(content.indexOf(FuHao.WF)+1,content.indexOf(FuHao.DT));
		 * String
		 * []dan=content.substring(content.indexOf(FuHao.WF)+1,content.indexOf(FuHao.DT)).split(FuHao.DH);
		 * String
		 * []tuo=content.substring(content.indexOf(FuHao.DT)+1,content.indexOf(FuHao.LQ)).split(FuHao.DH);
		 * String
		 * []lq=content.substring(content.indexOf(FuHao.LQ)+1,content.lastIndexOf(FuHao.BS)).split(FuHao.DH);
		 */
		// 将胆拖玩法转换成单式玩法
		// String preStr=PlayType.DS.getValue()+String.valueOf(FuHao.WF);
		// String endStr=content.substring(content.indexOf(FuHao.BS));
		List<DcReturnBean> strList = new ArrayList<DcReturnBean>();
		int num = m - dan.length;

		// 生成拖的组合排序
		List<String> tuoList = Algorithm.combineWithSliptChat(tuo, tuo.length, num, ";");
		TreeSet<String> set = new TreeSet<String>(new DcCodeComparator());
		// 拆票
		StringBuffer sb = new StringBuffer();
		int sum = 1;
		for (String str : tuoList) {
			LogUtil.out(str);
			String[] tuos = str.split(";");
			for (int i = 0; i < dan.length; i++) {
				sum *= dan[i].split("\\,").length;
				set.add(dan[i]);
			}
			for (int i = 0; i < tuos.length; i++) {
				sum *= tuos[i].split("\\,").length;
				set.add(tuos[i]);
			}

			Iterator it = set.iterator();
			while (it.hasNext()) {
				sb.append(it.next()).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);

			DcReturnBean bean = new DcReturnBean();
			bean.setContent(sb.toString());
			bean.setCount(sum);
			sum = 1;
			sb = null;
			sb = new StringBuffer();
			set.clear();
			strList.add(bean);
			// strList.add(sb.toString());
		}

		return strList;
	}

	/**
	 * 
	 * 从m个数中生成n个数组合的总数
	 */
	public static int combination(int m, int n) {
		if (m < 0 || n < 0 || m < n)
			return -1;
		n = n < (m - n) ? n : m - n;
		if (n == 0)
			return 1;
		int result = m;
		for (int i = 2, j = result - 1; i <= n; i++, j--) {
			result = result * j / i;
		}
		return result;
	}

	/*	*//**
			 * 得到全排列标志位
			 * 
			 * @param m
			 *            在m个数中
			 * @param n
			 *            找n个选项
			 * @return
			 */
	/*
	 * public static List getPaiLieFlag(int m,int n){ StringBuffer initSb = new
	 * StringBuffer(); List list = new ArrayList(); for(int i=0;i<m;i++){ if(i<n){
	 * initSb.append(1); }else{ initSb.append(0); } } String init =
	 * initSb.toString(); com.cailele.lottery.tools.LogUtil.out(init);
	 * list.add(init);
	 * 
	 * while(init.indexOf("10") != -1){
	 * 
	 * if(init.charAt(0) == '0'){ init = init.replaceFirst("10", "01");
	 * if(init.indexOf("10") == -1){
	 * com.cailele.lottery.tools.LogUtil.out(init); list.add(init); break; }
	 * com.cailele.lottery.tools.LogUtil.out("----"+init); initSb = new
	 * StringBuffer(init); String appendChar = ""; while(initSb.charAt(0) ==
	 * '0'){ initSb.deleteCharAt(0); appendChar += "0"; }
	 * com.cailele.lottery.tools.LogUtil.out("----appendChar:"+appendChar);
	 * com.cailele.lottery.tools.LogUtil.out("----initSb.indexOf(0):"+initSb.indexOf("0"));
	 * com.cailele.lottery.tools.LogUtil.out("----appendChar.length():"+appendChar.length());
	 * //initSb.append(appendChar.toCharArray(), , appendChar.length());
	 * initSb.insert(initSb.indexOf("0"), appendChar.toCharArray()); init =
	 * initSb.toString(); }else{ init = init.replaceFirst("10", "01"); }
	 * 
	 * com.cailele.lottery.tools.LogUtil.out(init); list.add(init); } return
	 * list; }
	 */

	// public static List <String> combine(String str[], int n, int m){
	static public void main(String[] str) throws Exception {
		/*
		 * //java.util.h TreeSet<String> set = new TreeSet<String>(new
		 * DcCodeComparator()); set.add("2(1,3)"); set.add("12(1,3,0)");
		 * set.add("45(0)");
		 * 
		 * Iterator it = set.iterator();
		 * 
		 * while(it.hasNext()){
		 * com.cailele.lottery.tools.LogUtil.out(it.next()); }
		 * 
		 * set.clear();
		 * 
		 * while(it.hasNext()){
		 * com.cailele.lottery.tools.LogUtil.out(it.next()); }
		 */

		/*
		 * 
		 * public static List<DcReturnBean> combineDcDt(String[] dan, String[]
		 * tuo, String danString, int m) {
		 */

		/*
		 * List <DcReturnBean> list = Algorithm.combineDcDt(new
		 * String[]{"5(3)","12(1,3)"},new
		 * String[]{"15(3)","2(1,3)","9(0)"},"5(3);12(1,3)",3);
		 * 
		 * for(int i=0;i<list.size();i++){ DcReturnBean bean = list.get(i);
		 * com.cailele.lottery.tools.LogUtil.out(bean.getContent()+" ==
		 * "+bean.getCount()); }
		 */
		// *
		List list = Algorithm.rxDt("08#01,02,03,04,10", 2);
		for (int i = 0; i < list.size(); i++) {
			LogUtil.out(list.get(i));
		}
		// */
		/*
		 * List list = Algorithm.dltDt("#02,03,04,05,06,07|#08,09"); for(int
		 * i=0;i<list.size();i++){
		 * com.cailele.lottery.tools.LogUtil.out(list.get(i)); } //
		 */
	}


}
