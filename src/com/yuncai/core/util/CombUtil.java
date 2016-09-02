package com.yuncai.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 高效组合算法
 * 
 * @author ZhouHaiBin
 * 
 */
public class CombUtil {

	/**
	 * 计算组合数
	 * 
	 * @param r
	 *            取出元素个数
	 * @param n
	 *            可选个数
	 * @return 组合数
	 */
	public static int comb(int n, int m) {
		if(n<m){
			return 0;
		}
		int p=1;
		int a=n-m<m?n-m:m;
		int b=n-m>m?n-m:m;
		for(int i=1; i<=a; i++)
			p+=p*b/i;
		return p;
	}

	/**
	 * 进制转换
	 * 
	 * @param val
	 *            十进制的值
	 * @param radix
	 *            要转换的进制
	 * @return
	 */
	public static List<Integer> toRadix(int val, int radix) {
		if (val <= 0 || radix <= 0) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		while (val >= radix) {
			int a = val % radix;
			list.add(a);
			val = val / radix;
		}
		list.add(val);
		return list;
	}

	/**
	 * 高效的组合算法
	 * 
	 * @param n
	 *            可选的数目
	 * @param m
	 *            取的数目
	 * @param callBack
	 *            每取出一个组合时的回调函数
	 */
	public static void comb(int n, int m, CombCallBack callBack) {
		if (m > n) {
			return;
		}

		boolean[] bs = new boolean[n];
		for (int i = 0; i < m; i++) {
			bs[i] = true;
		}
		if (m == n) {
			callBack.callback(bs, m);
			return;
		}
		for (int i = m; i < n; i++) {
			bs[i] = false;
		}
		if (m == 0) {
			callBack.callback(bs, m);
			return;
		}

		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		// 首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			callBack.callback(bs, m);

			// 找到第一个10组合，然后变成01
			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == true && bs[i + 1] == false) {
					bs[i] = false;
					bs[i + 1] = true;
					pos = i;
					break;
				}
			}

			// 将左边的1全部移动到数组的最左边
			for (int i = 0; i < pos; i++) {
				if (bs[i] == true) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = true;
				} else {
					bs[i] = false;
				}
			}

			// 检查是否所有的1都移动到了最右边
			for (int i = n - m; i < n; i++) {
				if (bs[i] == false) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		callBack.callback(bs, m);
	}
	public interface CombCallBack {

		/**
		 * 组合算法的回调函数
		 * 
		 * @param comb
		 *            选中的数组，数组元素的值为真表示原数组相同下标的元素被选中
		 * @param m
		 *            选中的元素数目
		 */
		void callback(boolean[] comb, int m);
	}
}

