package com.yuncai.core.tools;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringTools {
	
	/**
	 * 产生随机字符串
	 */
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
	private static Object initLock = new Object();

	private static Random randGenNumber = null;
	private static char[] numbersAndLettersNumber = null;
	private static Object initLockNumber = new Object();

	// 判断字符串是否空或null
	public static boolean isNullOrBlank(String s) {
		if (s == null || s.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static final String randomString(int length) {

		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(62)];
		}
		return new String(randBuffer);
	}

	public static final String randomNumberString(int length) {

		if (length < 1) {
			return null;
		}
		if (randGenNumber == null) {
			synchronized (initLockNumber) {
				if (randGenNumber == null) {
					randGenNumber = new Random();
					numbersAndLettersNumber = ("0123456789").toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLettersNumber[randGenNumber.nextInt(10)];
		}
		return new String(randBuffer);
	}

	final static public String getStringFillZero(String str, int length) {
		if (str.length() == length) {
			return str;
		}
		int fillLength = length - str.length();
		for (int i = 0; i < fillLength; i++) {
			str = "0" + str;
		}
		return str;
	}

	final static public boolean isValueIdTokenString(String tokenString, String split, String value) {
		String[] str = tokenString.split(split);
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @作者:andy
	 * @描述:解析http头里的参数值
	 * @param header
	 * @return
	 */
	final static public HashMap<String, String> getParametersByHttpHead(String header) {

		String data = header.substring(header.indexOf("?") + 1, header.indexOf(" HTTP"));
		return getParametersByContents(data);
	}

	/**
	 * @作者:andy
	 * @描述:解析 key=value&key=value的键值
	 * @param contents
	 * @return
	 */
	final static public HashMap<String, String> getParametersByContents(String contents) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] keyValues = contents.split("&");
		for (int i = 0; i < keyValues.length; i++) {
			String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
			String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * @作者:andy
	 * @描述:解析 key=value&key=value的键值
	 * @param contents
	 * @return
	 */
	final static public HashMap<String, String> getParametersByContents(String contents, String charset) {
		LogUtil.out(contents);
		HashMap<String, String> map = new HashMap<String, String>();
		String[] keyValues = contents.split("&");
		for (int i = 0; i < keyValues.length; i++) {
			String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
			String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);

			if (charset != null && !"".equals(charset)) {
				try {
					value = java.net.URLDecoder.decode(value, charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			map.put(key, value);
		}
		return map;
	}

	/**
	 * @作者:andy
	 * @描述:将map转成key=value&key=value
	 * @param contents
	 * @return
	 */
	final static public String getStringByHashMap(Map map) {
		StringBuffer sb = new StringBuffer();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = map.get(key);
			sb.append(key.toString()).append("=").append(value.toString()).append("&");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * @作者:wuxh
	 * @描述:将ActionContext 里而的Parameter map转成key=value&key=value
	 * @param contents
	 * @return
	 */
	final static public String getStringByActionHashMap(Map map) {
		StringBuffer sb = new StringBuffer();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = map.get(key);
			if (value instanceof String[]) {
				String[] values = (String[]) value;

				StringBuffer subSb = new StringBuffer();
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						subSb.append(values[i]).append("|");
					}
					if (subSb.length() > 0) {
						subSb.deleteCharAt(subSb.length() - 1);
					}
				}
				sb.append(key.toString()).append("=").append(subSb.toString()).append("&");
			}

		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * @作者:andy
	 * @描述:将map转成key=value&key=value
	 * @param contents
	 * @return
	 */
	final static public String getStringByHashMap(HashMap<String, String> map, String chartset) throws Exception {
		StringBuffer sb = new StringBuffer();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = map.get(key);
			sb.append(key).append("=").append(URLEncoder.encode(value, chartset)).append("&");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 把给定的字符串转换为Base64编码
	 * 
	 * @param source
	 *            需要进行转换的字符串
	 * @return 转换后的结果，如果source为null，则返回null
	 */
	public static String EncodeBase64(String source) {
		if (source == null)
			return null;
		return new String(org.apache.commons.codec.binary.Base64.encodeBase64(source.getBytes()));
	}

	/**
	 * 把base64编码的字符串进行解码并返回原字符串
	 * 
	 * @param source
	 *            需要进行解码的Base64编码的字符串
	 * @return 解码后的原字符串，如果错误则返回null
	 */
	public static String DecodeBase64(String source) {
		if (source == null)
			return null;
		try {
			return new String(new BASE64Decoder().decodeBuffer(source));
		} catch (Exception ex) {
			return null;
		}
	}

	public static String[] split(String str, char delim) {

		if (str == null)
			return null;
		if (str.trim().equals(""))
			return new String[0];

		int count = 1;
		char c[] = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == delim) {
				count++;
			}
		}

		String s[] = new String[count];
		int index = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == delim) {
				s[index] = sb.toString();
				sb = new StringBuffer();
				index++;
			} else {
				sb.append(c[i]);
			}
		}
		s[index] = sb.toString();
		return s;
	}

	static public String decodeForwardUrl(String forwardUrl) {
		forwardUrl = StringTools.DecodeBase64(forwardUrl);

		/**
		 * 以下的代码是为了让转向地址携带的参数能正常传递 如号码格式里面的 % 这些字符必须经过URLEncode才能被正常解析
		 */
		String pre = "";
		String bak = forwardUrl.substring(forwardUrl.indexOf("?") + 1, forwardUrl.length());
		String area[] = bak.split("\\&");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < area.length; i++) {
			LogUtil.out("area" + area[i]);
			String[] peace = area[i].split("\\=");
			if (peace.length == 2) {
				try {
					sb.append(peace[0]).append("=").append(URLEncoder.encode(peace[1], "GBK")).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else if (peace.length == 1) {
				LogUtil.out("Warning the empty parameter is:" + peace[0]);
				// sb.append(peace[0]).append("=").append("").append("&");
			}

		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
			pre = forwardUrl.substring(0, forwardUrl.indexOf("?") + 1);
		} else {
			// 后面没有参数，不用带 ?
			if (forwardUrl.indexOf('?') != -1) {
				pre = forwardUrl.substring(0, forwardUrl.indexOf("?"));
			}

		}

		forwardUrl = pre + sb.toString();
		return forwardUrl;
	}

	public boolean isNumber(String str) {
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将一个字符串转换为一个数组 例如将 0102030405 转换为 01,02,03,04,05的数组
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 返回一个数组
	 */
	public static String[] convertToStringArray(String str) {
		if (null == str || str.length() < 1)
			return new String[0];
		String[] codes = new String[str.length() / 2];
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i, i + 2);
			// com.cailele.lottery.tools.LogUtil.out(temp);
			codes[i / 2] = temp;
			i++;
		}
		return codes;
	}

	/**
	 * 将一个字符串转换为一个数组(单字符) 例如将 12345 转换为 1,2,3,4,5的数组
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 返回一个数组
	 */
	public static String[] convertToCharArray(String str) {
		if (null == str || str.length() < 1)
			return new String[0];
		String[] codes = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i, i + 1);
			// com.cailele.lottery.tools.LogUtil.out(temp);
			codes[i] = temp;

		}
		return codes;
	}

	/**
	 * 将一个数组转为带分隔符的串
	 */
	public static String convertArrayToString(Object[] arr, String splitStr) {
		String codes = "";
		for (Object a : arr) {
			codes += a.toString() + splitStr;
		}
		return codes.substring(0, codes.length() - 1);
	}

	public static String getSecStr(String src, int start, int end) {
		if (src == null)
			return "";
		StringBuffer des = new StringBuffer();
		for (int i = 0; i < src.length(); i++) {
			if (i >= start && i < end) {
				des.append("*");
			} else {
				des.append(src.charAt(i));
			}

		}
		return des.toString();
	}

	// 将16进制字符转为byte数数
	public static byte[] parseHexString(String src) {
		byte[] result = null;
		try {
			if (src.length() % 2 != 0)
				throw new Exception("串内容错误！");
			result = new byte[src.length() / 2];
			for (int i = 0; i < src.length() / 2; i++) {
				result[i] = (byte)Integer.parseInt(src.substring((i * 2), (i * 2 + 2)), 16);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	// 数据DES加密,java 默认mode = ECB ,key 为16进制字符
	public static String getDESEncrypt(String src, String key) throws Exception {
		// 转化key串为 byte[]
		byte[] k = StringTools.parseHexString(key);
		SecretKey deskey = new SecretKeySpec(k, "DES");
		// String Algorithm = "DES/CBC/PKCS5Padding"; //定义加密算法,可用
		// DES,DESede,Blowfish
		String Algorithm = "DES"; // java 默认mode = ECB
		Cipher c = Cipher.getInstance(Algorithm);
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] raw = c.doFinal(src.getBytes());
		// 对加密后的数据进行BASE64编码
		BASE64Encoder encoder = new BASE64Encoder();
		String encMsg = encoder.encode(raw);

		// System.out.println("加密后的数据：" + encMsg);
		return encMsg.replaceAll("\r", "").replaceAll("\n", "");
	}

	// 解析DES加密后的数据
	public static String getDESDecrypt(String encMsg, String key) throws Exception {
		if (encMsg == null || encMsg.length() <= 0) {
			throw new Exception("数据为空！");
		}
		// 对加密数据进行BASE64解码
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] raw = decoder.decodeBuffer(encMsg);
		// 转化key串为 byte[]
		byte[] k = StringTools.parseHexString(key);
		SecretKey deskey = new SecretKeySpec(k, "DES");

		String Algorithm = "DES"; // java 默认mode = ECB
		Cipher c = Cipher.getInstance(Algorithm);
		c.init(Cipher.DECRYPT_MODE, deskey);
		byte[] cipherByte = c.doFinal(raw);
		String srcMsg = new String(cipherByte);
		// System.out.println("解密后的数据：" + srcMsg);

		return srcMsg;
	}

	public static String getNumfromStr(String str) {
		Matcher m = Pattern.compile("[\\d.]+").matcher(str);
		m.find();
		String value = m.group();
		return value;
	}

	public static String getFloatfromStr(String str) {
		Matcher m = Pattern.compile("[\u4e00-\u9fa5]+").matcher(str);
		m.find();
		String value = m.group();
		return value;
	}

	static public void main(String[] args) throws Exception {
		System.out.println(getFloatfromStr("让分主负abc-8.5"));
		// try{
		// String str =
		// StringTools.DecodeBase64("ZnJlZXplPTEyNzMuMCZhY2NvdW50PXNzYyZhYmxlPTE0MDk2LjB8NmJkYzczYmZhZjFiN2Q3ZjVjZTA1NTU0Y2Y0NDRjN2M=");
		// com.cailele.lottery.tools.LogUtil.out(str);
		// String [] codes = str.split("\\|");
		// if(Constant.MD5(codes[0]+"nbcllnb").equals(codes[1])){
		// com.cailele.lottery.tools.LogUtil.out("success");
		// }
		// System.out.println(getSecStr("一二三四",1,1));
		//		
		// //des test
		// String key = "0102030405060708";
		// parseHexString(key);
		// String src = "1234567890";
		// System.out.println("源文:" + src);
		// String enc = getDESEncrypt(src,key);
		// System.out.println("加密文:" + enc);
		// String deSrc = getDESDecrypt(enc,key);
		// System.out.println("解密文:" + deSrc);
		//		
		// String xx =
		// "s8nZ1VkOAJNw7BS6l3KFUCr5n8mK0bokzBGT3vaTNTZJ+ayCoc6ERQr9EWh3J80hhAMSYOE9SvJpBa3+sxIqRjlIfi1IS8JEqD7G338altFU52spsvr5h4c30qldU9/ih59lAKVSxmvWp+ro6QhrMOy4lMu9WLox0NFMn4ZNOjjvRYgY5rmnZdL8TY5Gv6vxRIb8aK4xDLThrMLYSWOW4VUBrhDK43qR2ILG95VbzFiNNYtWyFEinU0mq8EJhWgjbI5jnqjOP5KiqR1Wv2lysuZmVcCy3zmDbHtq9d69d3FxPSxBnVxjaYOc7PPfQXxGDrd7JzgwOr95VxGAwQij1C7UJZQwCbjRslO7S066P2clgK1MsK5EIc7BdC6VwCQQ5VYkzzZKj87RkcVTraDlHlXJ3ZWMKE/e4+w9tGjVOhNRGy18cGGYzLmtZ0Rwr3rxUD2X8DHXT/4U21F2BQ7I9PH+XGpILQoPmvNtwi7YLAt2QBKA5nl1+A==";
		// //String xx = "123456789" ;
		// System.out.println("md5=" + Constant.MD5digest(xx));
		// System.out.println("md5=" + Constant.MD5(xx));
		//		
		// //0a41aedc2bf88601ddc1e76e0440f0ad
		//		
		// System.out.println("src=" + getDESDecrypt(xx,"010D030A05060B08"));
		// }
		// catch(Exception e)
		// {
		// e.printStackTrace();
		// }
		String[] strArr1 = new String[] { "3", "1" };
		String[] strArr2 = new String[] { "0", "3", "1" };
		String[] strArr3 = new String[] { "1", "0" };
		String[] strArr = new String[] { "3" };
		System.out.println(formatArr2Or(Arrays.asList(strArr1)));
	}

	/**
	 * [3,1,3,0,1]->3|1|3|0|1
	 * 
	 * @param strArr
	 * @return
	 */
	public static String formatArr2Or(Collection<String> strArr) {
		return formatArr(strArr, "|");
	}

	/**
	 * [3,1|0,3,0,1]->3,1|0,3,0,1
	 * 
	 * @param strArr
	 * @return
	 */
	public static String formatArr2And(Collection<String> strArr) {
		return formatArr(strArr, ",");
	}

	public static String formatArr(Collection<String> strArr, String separator) {
		if (strArr == null) {
			return null;
		}
		StringBuffer resultBuffer = new StringBuffer();
		for (String temp : strArr) {
			resultBuffer.append(separator).append(temp);
		}
		String result = (resultBuffer.length() < 1) ? "" : resultBuffer.substring(separator.length());
		return result;
	}


}
