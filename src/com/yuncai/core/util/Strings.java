package com.yuncai.core.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * 字符串工具类
 * 
 * @author ysh
 * 
 */
public class Strings {
	public static final String NUMBER_REG = "^([-|\\+]?\\d+)(\\.\\d+)?$";
	public static final String DEFAULT_CHARSET = "UTF-8";
	// 供MD5编码使用的盐;
	// SAL=MD5("BAIQiu");
	public static final String SAL = "05d66836f399ef8f6ff14ebb088543c6";

	public static boolean isNumeric(CharSequence chars) {
		if (chars == null) {
			return false;
		}
		return chars.toString().replaceAll("\\s", "").matches(NUMBER_REG);
	}

	public static List<String> getBetween(CharSequence chars, CharSequence prefix, CharSequence suffix) {
		Pattern PARENTHESES_REG = Pattern.compile(prefix + "(.+?)" + suffix);
		Matcher matcher = PARENTHESES_REG.matcher(chars);
		List<String> result = new ArrayList<String>();
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		return result;
	}

	public static boolean isEmpty(CharSequence chars) {
		if (chars == null) {
			return true;
		}
		return chars.length() == 0;
	}

	public static Map<String, String> analysisUrlParam(String url) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] urlInfo = url.split("[?]");
		if (urlInfo.length > 1) {
			String[] paramEntrys = urlInfo[1].split("[&]");
			for (String paramEntry : paramEntrys) {
				String[] keyAndValue = paramEntry.split("[=]");
				if (keyAndValue.length == 2) {
					paramMap.put(keyAndValue[0], keyAndValue[1]);
				}
			}
		}
		return paramMap;
	}

	public static String buildUrlParam(Map<String, Object> paramMap) {
		if (Collections3.isEmpty(paramMap)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			sb.append("&");
			sb.append(entry.getKey() + "=" + entry.getValue());
		}
		String s = sb.toString();
		if (s.startsWith("&")) {
			s = s.substring(1);
		}
		return s;
	}

	/**
	 * MD5编码（加盐）,此加盐算法允许在仅仅知道MD5密文下转换为加盐后的密文，方便用户导入的情况
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5WithSal(String inStr) {
		String fristMd5 = MD5(inStr);
		StringBuffer secondInStrBuff = new StringBuffer();
		secondInStrBuff.append(fristMd5.substring(16));
		secondInStrBuff.append(SAL.substring(16));
		System.out.println(secondInStrBuff);
		return MD5(secondInStrBuff.toString());
	}

	/**
	 * MD5编码(使用utf-8进行编码)
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5(String inStr) {
		return MD5(inStr, DEFAULT_CHARSET);
	}

	/**
	 * MD5编码
	 * 
	 * @param inStr
	 * @param chartSet
	 * @return
	 */
	public static String MD5(String inStr, String chartSet) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		try {
			byteArray = inStr.getBytes(chartSet);
		} catch (Exception e) {
			return "";
		}

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String EncodeBase64(String source) {
		if (source == null)
			return null;
		return new String(Base64.encodeBase64(source.getBytes()));
	}

	public static String DecodeBase64(String source) {
		if (source == null)
			return null;
		return new String(Base64.decodeBase64(source.getBytes()));
	}

	/**
	 * 针对String转换成int时可能为空处理
	 * 
	 * @param str
	 * @param num
	 *            默认值
	 */
	public static int toInteger(String str, int num) {
		if ("".equals(str) || str == null)
			return num;
		return Integer.valueOf(str);
	}

	/**
	 * 针对String转换成double时可能为空处理
	 * 
	 * @param str
	 * @param num
	 *            默认值
	 */
	/*
	 * public static double toDouble(String str,int num){ if("".equals(str) ||
	 * str==null) return num; return Double.valueOf(str); }
	 */

	/**
	 * 从指定字符串中提取 符合正则表达式的内容
	 * 
	 * @param str
	 * @param reg
	 * @return
	 */
	public static String getByReg(String str, String reg) {
		StringBuffer sbf = new StringBuffer();
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			sbf.append(matcher.group());
		}
		return sbf.toString();
	}

	/**
	 * 转换金额元为分
	 * 
	 * @param str
	 * @param reg
	 * @return
	 */
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
		// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	/**
	 * 
	 * 
	 * 一个字符串在另一个字符串中 第N次出现的位置 (如果是倒序则将indexOf改成lastIndexOf) 使用 : int num =
	 * getStringNum("aaasdfzsssasef", "s", 10); System.out.println(num);
	 * 
	 * @param sA
	 *            是第一个字符(大的字符串)
	 * @param sB
	 *            被包含的字符串
	 * @param num
	 *            sB在sA 中第几次出现 num 必须大于1
	 * @return sB 在sA 中第num次出现的位置
	 */
	public static int getStringNum(String sA, String sB, int num) {
		int weizhi = 0;
		for (int i = 1; i < num; i++) {
			if (weizhi == 0) {
				weizhi = sA.toString().indexOf(sB);
			}
			// System.out.println(sB+" 在"+sA+" 中第"+i+"次出现的位置"+(weizhi+1));
			weizhi = sA.indexOf(sB, weizhi + 1);
		}
		return weizhi ;
	}
	
	
	 /**屏蔽字符串中包含的的手机号码中间4位
	  * @param str
	  * @return 
	  */
	 public static String encryptionStrPhone(String str){
	  if(str==null){
	   return "";
	  }
	  String reg = "((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}";
	  Pattern p = Pattern.compile(reg);
	  Matcher matcher = p.matcher(str);
	 
	  while (matcher.find()) {
	   
	   String mStr = matcher.group();
	   int start=matcher.start();
	   int end =matcher.end() ;
	   
	   return str.substring(0, start)+encryptionPhone(mStr)+str.substring(end);
	  }
	  return str;
	 }

	 
	 /**转换手机号码中间四位
	  * @param phone
	  * @return
	  */
	 public static String encryptionPhone(String phone){
	  String regex = "((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}";
	 
	  if(phone==null){
	   return "";
	  }
	 
	  if(phone.matches(regex)){
	   phone = phone.substring(0, 3)+"****"+phone.substring(7);
	  }
	  return phone;
	 
	 }
}
