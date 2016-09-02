package com.yuncai.core.tools;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
	public static String format="Q56GtyNkop97H334TtyturfgErvvv98a";
	public static String encode(String inStr) {
		inStr=inStr+format;
		return encode(inStr, "UTF-8").toUpperCase();
	}

	public static String encode(String inStr, String enco) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			LogUtil.out(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		try {
			byteArray = inStr.getBytes(enco);
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
	
	public static String md5Hex(String s)
	{
		return DigestUtils.md5Hex(s); 
	}
	
	


	static public void main(String[] ar) {
//		LogUtil
//				.out(MD5
//						.encode("lotteryType=5&openCode=0,0,3,0,1,3,3,3,1,1,3,3,3,3&term=10049&openTime=2010-05-17 11:00:00&info=prize1^411^39670#prize2^9683^721#prize3^49592^301&arrange=切  沃,罗  马,2010-05-16 21:00,0:0,0:0|锡耶纳,国  米,2010-05-16 21:00,0:0,0:0|巴  里,佛罗伦,2010-05-16 21:00,0:0,0:0|亚特兰,巴勒莫,2010-05-16 21:00,0:0,0:0|卡利亚,博洛尼,2010-05-16 21:00,0:0,0:0|卡塔尼,热那亚,2010-05-16 21:00,0:0,0:0|桑普多,那不勒,2010-05-16 21:00,0:0,0:0|帕尔玛,利沃诺,2010-05-16 21:00,0:0,0:0|奥萨苏,萨雷斯,2010-05-17 01:00,0:0,0:0|马拉加,皇  马,2010-05-17 01:00,0:0,0:0|瓦伦西,特内里,2010-05-17 01:00,0:0,0:0|巴  萨,瓦拉多,2010-05-17 01:00,0:0,0:0|桑坦德,希  洪,2010-05-17 01:00,0:0,0:0|卡尔马,辛  堡,2010-05-16 23:59,0:0,0:0|adsfjlkjasdoiufe"));
//		LogUtil.out(MD5.encode("小号"));
//		LogUtil.out(MD5.encode("小号", "utf-8"));
		
		LogUtil.out(MD5.md5Hex("小号"));
		LogUtil.out(MD5.encode("小号", "utf-8"));

	}
}
