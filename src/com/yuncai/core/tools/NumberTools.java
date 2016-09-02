package com.yuncai.core.tools;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class NumberTools {
	public static final String NUMBER_REG="^([-|\\+]?\\d+)(\\.\\d+)?$";
	/**
	 * 把数字转换成固定位数的字符串。不足的补0
	 * 
	 * @param number
	 * @param weishu
	 * @return
	 */
	public static String IntToString(Integer number, int weishu) {
		if (number == null) {
			number = new Integer(0);
		}
		StringBuffer result = new StringBuffer();
		String numberStr = number.toString();
		int temp = weishu - numberStr.length();
		if (temp < 0) {
			return "-1";
		} else {
			for (int i = 0; i < temp; i++) {
				result.append("0");
			}
		}
		result.append(number);
		return result.toString();
	}

	public static String doubleToString(Double number) {
		// 转换为1,000,000,000,000的形式输出
		NumberFormat formatter = new DecimalFormat("#,###,###,###,###");
		if (number != null)
			return formatter.format(number);
		else
			return "0";
	}
	public static String formatDouble(Double number,String format){
		NumberFormat formatter = new DecimalFormat(format);
		if (number != null)
			return formatter.format(number);
		else
			return "0";
	}
	public static String doubleToMoneyString(Double number) {
		// 转换为1,000,000,000,000的形式输出
		NumberFormat formatter = new DecimalFormat("###############.00");
		if (number != null)
			return formatter.format(number);
		else
			return "0";
	}

	public static Double formatDouble(Double number, int weishu) {
		int offset = 1;
		for (int i = 0; i < weishu; i++) {
			offset *= 10;
		}
		if (number != null)
			return (int) (number * offset) / (offset * 1.0d);
		else
			return 0d;
	}

	/*
	 * 数字转化为百分比格式
	 */
	public static String doubleToPercent(Double number, String fomatStr) {
		DecimalFormat format = null;
		format = (DecimalFormat) NumberFormat.getPercentInstance();
		format.applyPattern(fomatStr); // 0表示加的小数点,0.00表示两位小数点，
		if (number != null)
			return "" + format.format(number);
		else
			return "0%";
	}

	/*
	 * 数字转化为百分比格式
	 */
	public static String doubleToPercent(Double number) {
		return doubleToPercent(number, "0.00%");
	}

	public static int[] stringToNumArr(String source, String splitStr) {
		String[] tempArr = source.split(splitStr);
		int[] result = new int[tempArr.length];
		int len = tempArr.length;
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(tempArr[i]);
		}
		return result;
	}

	public static List queryListTypeConvert(List list) {
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			for (int j = 0; j < obj.length; j++) {
				// System.out.println(obj[j].getClass().getName());
				if (obj[j].getClass().getName().equals("java.math.BigDecimal")) {
					String s = String.valueOf(obj[j]);
					// System.out.println(s);
					if (s.contains("."))
						obj[j] = ((BigDecimal) obj[j]).doubleValue();
					else
						obj[j] = ((BigDecimal) obj[j]).intValue();
				}
			}
			list.set(i, obj);
		}
		return list;
	}

	public static double String2Double(String doubleStr, double defaultValue) {
		if (isNum(doubleStr)) {
			return Double.parseDouble(doubleStr);
		} else {
			return defaultValue;
		}
	}

	public static boolean isNum(String str){
		if(str==null){
			return false;
		}
		return str.replaceAll("\\s", "").matches(NUMBER_REG);
	}	
	public static void main(String[] args) {
		System.out.println(formatDouble(3.116, 2));
	}

}
