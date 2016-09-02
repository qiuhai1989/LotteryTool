package com.yuncai.core.tools;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MathUtil {

	/**
	 * @作者:andy
	 * @描述:求组合C(n,r)
	 * @param n
	 * @param r
	 * @return
	 */
	final static public int getCombinationCount(int n, int r) {
		if (r > n)
			return 0;
		if (r < 0 || n < 0)
			return 0;
		return getFactorial(n).divide(getFactorial(r), BigDecimal.ROUND_HALF_DOWN).divide(getFactorial((n - r)), BigDecimal.ROUND_HALF_DOWN)
				.intValue();
	}

	/**
	 * @作者:andy
	 * @描述:求n的阶乘
	 * @param n
	 * @return
	 */
	final static public BigDecimal getFactorial(int num) {
		BigDecimal sum = new BigDecimal(1.0);
		for (int i = 1; i <= num; i++) {
			BigDecimal a = new BigDecimal(new BigInteger(i + ""));
			sum = sum.multiply(a);
		}
		return sum;
	}

	/**
	 * @作者:andy
	 * @描述:随机取的01-36之间的数字
	 * @param n
	 *            取的个数
	 * @param area
	 *            区间
	 * @return
	 */
	final static public String getRandom(int area, int n) {
		Random rd1 = new Random();
		Set set = new HashSet();
		for (int i = 1; i < area; i++) {

			int num = rd1.nextInt(area);
			String strNum = "";
			if (num < 10) {
				if (num != 0) {
					strNum = "0" + num;
					set.add(strNum);
				}

			} else {
				strNum = "" + num;
				set.add(strNum);
			}
			if (set != null && set.size() >= n) {
				break;
			}
		}

		String str = "";// 返回拼的字符串
		Iterator it = set.iterator();
		int i = 0;
		while (it.hasNext()) {
			i++;
			if (i < n) {
				str += it.next() + ",";
			} else {
				str += it.next() + "";
			}

		}
		return str;
	}

	/**
	 * 累加数组之和
	 * 
	 * @param array
	 * @return 返回累加数组之和
	 */
	public final static int getArraySum(String[] array) {
		int sum = 0;
		for (String n : array) {
			sum += Integer.parseInt(n);
		}
		return sum;
	}

	/**
	 * 找出一个数组中的最大值和最小值之差
	 * 
	 * @param array
	 * @return 返回最大值和最小值之差
	 */
	public final static int getArrayDev(String[] array) {
		int max = Integer.parseInt(array[0]);
		int min = Integer.parseInt(array[0]);
		for (int i = 1; i < array.length; i++) {
			if (Integer.parseInt(array[i]) > max) {
				max = Integer.parseInt(array[i]);
			}
			if (Integer.parseInt(array[i]) < min) {
				min = Integer.parseInt(array[i]);
			}
		}
		return (max - min);
	}

	/**
	 * 找出一个数组中的连号数 比如： {01,02,03,04,05} 表示五连号 {01,02,03,04,08} 表示四连号
	 * {01,02,03,06,08} 表示三连号 {01,02,04,06,08} 表示二连号 {01,03,05,07,09} 表示一连号（无连号）
	 * {01,02,05,06,07} 表示一个二连号和一个三连号 {01,02,05,06,08} 表示两个二连号
	 * 
	 * @param array
	 * @return 返回连号数
	 */
	public final static HashMap<Integer, Integer> getArrayLinkCounts(String[] array) {
		Arrays.sort(array);// 排序
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int link = 0;

		for (int i = 0; i < array.length; i++) {
			if (((i + 1) < array.length) && (Integer.parseInt(array[i]) + 1 == Integer.parseInt(array[i + 1]))) {
				link++;
			} else {
				if (map.containsKey(link + 1)) {
					map.put((link + 1), (map.get(link + 1) + 1));
				} else {
					if (link != 0) {
						map.put((link + 1), 1);
					}
				}
				link = 0;
			}
		}

		if (map.isEmpty()) {
			map.put(1, array.length);
		}

		return map;
	}

	public static long gys(long a, long b) {
		if (a % b > Math.min(a, b) * 0.02)
			return gys(b, a % b);
		else
			return b;
	}

	public static long gbs(long a, long b) {
		long gys = gys(a, b);
		return a * b / gys;
	}
	
	public static List<Integer> getAvgMulList(List<Double> prizeList,Integer count){
		if(count==null||count.intValue()==0){
			count=prizeList.size()*10;
		}
		Double maxPrize = Collections.max(prizeList);
		List<Double> baseList = new ArrayList<Double>();
		Double baseSum = 0.0d;
		for (Double prize : prizeList) {
			Double base = maxPrize / prize;
			baseList.add(base);
			baseSum += base;
		}
		List<Integer> multList=new ArrayList<Integer>();
		for(int i=0,length=baseList.size();i<length;i++){
			Double base=baseList.get(i);
			multList.add(Long.valueOf(Math.round(base/baseSum*count)).intValue());
		}
		return multList;
	}
	/**
	 * 是否素数
	 * @param x
	 * @return
	 */
	public static boolean isPrime(int x) {
		if (x < 2)
			return false;
		for (int i = 2; i <= Math.sqrt(x); i++){
			if (x % i == 0){
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) {
		Double[] prizeArr=new Double[]{1.8,2.2,5.3};
		List<Double> prizeList=Arrays.asList(prizeArr);
		int betCount=100;
		Double maxPrize=Collections.max(prizeList);
		List<Double> baseList=new ArrayList<Double>();
		Double baseSum=0.0d;
		for(Double ticketPrize:prizeList){
			Double base=maxPrize/ticketPrize;
			baseList.add(base);
			baseSum+=base;
		}
		System.out.println(baseList);
		int count=0;
		for(Double base:baseList){
			base=base/baseSum;
		}
		
		System.out.println(baseList);
	}
}
