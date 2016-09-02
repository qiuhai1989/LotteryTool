package com.yuncai.core.tools;
import java.util.*;

public class MathUtils {
	/**
	 * 计算组合数
	 * 
	 * @param r
	 *            取出元素个数
	 * @param n
	 *            可选个数
	 * @return 组合数
	 */
	public static int comp(int r, int n) {
		long C = 1;
		for (int i = n - r + 1; i <= n; i++) {
			C = C * i;
		}
		for (int i = 2; i <= r; i++) {
			C = C / i;
		}
		return (int) C;
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
	public static void efficientComb(int n, int m, CombCallBack callBack) {
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

	/**
	 * 拆分单式对外公开方法
	 * 
	 * @param nums
	 * @param callBack
	 */
	public static void traversalComb(int[] nums, TraversalCallBack callBack) {
		traversalComb(nums, new int[nums.length], 0, callBack);
	}

	/**
	 * 递归拆分单式 内部算法
	 * 
	 * @param nums
	 *            复式个数 ，选项:[3|0|1,1|0]->复式个数[3,2]
	 * @param temps
	 *            初始值
	 * @param bit
	 *            标志位
	 * @param callBack
	 *            回调函数，对结果进行处理
	 */
	private static void traversalComb(int[] nums, int[] temps, int bit, TraversalCallBack callBack) {
		int len = nums.length;
		if (bit < len) {
			for (int i = 0; i < nums[bit]; ++i) {
				temps[bit] = i;
				traversalComb(nums, temps, bit + 1, callBack);
				temps[bit] = 0;
			}
		} else {
			callBack.callback(temps);
		}
	}
	
	public static List<String> permutationResult(int num1, int num2, int numLength) {
		return permutationResult(num1, num2, numLength, true);
	}
	
	public static List<String> permutationResult(int num1, int num2, int numLength, boolean doubleFlag) {
		List<String> numList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();

		for (int i = num1; i <= num2; i++) {
			if(doubleFlag) {
				numList.add(String.format("%02d", i));
			} else {
				numList.add(String.valueOf(i));
			}
		}

		sort(numList, new ArrayList(), resultList, numLength);

		return resultList;

	}
	
	public static List<String> permutationResult(List<String> numList, int numLength) {
		List<String> resultList = new ArrayList<String>();
		sort(numList, new ArrayList(), resultList, numLength);

		return resultList;
	}

	private static void sort(List datas, List target, List result, int num) {
		if (target.size() == num) {
			String s ="";
			for (Object obj : target) {
				s += obj + ",";
			}
			s = s.substring(0, s.length() - 1);
			result.add(s);
			return;
		}
		for (int i = 0; i < datas.size(); i++) {
			List newDatas = new ArrayList(datas);
			List newTarget = new ArrayList(target);
			newTarget.add(newDatas.get(i));
			newDatas.remove(i);
			sort(newDatas, newTarget, result, num);
		}
	}

	public static List<String> combinResult(int totalCount, int selectCount) {
		return combinResult(totalCount,selectCount,true);
	}
	
	public static List<String> combinResult(int totalCount, int selectCount,boolean doubleFlag) {

		final List<Integer> testNumList = new ArrayList<Integer>();
		for (int i = 1; i <= totalCount; i++) {
			testNumList.add(i);
		}

		return combinResult(testNumList,selectCount,doubleFlag);
	}
	
	public static List<String> combinResult(final List<Integer> dataList, int selectCount) {
		return  combinResult(dataList, selectCount, true);
	}
	
	public static List<String> combinResult(final List<Integer> dataList, int selectCount,boolean doubleFlag) {
		final List<int[]> list = new ArrayList<int[]>();

		efficientComb(dataList.size(), selectCount, new CombCallBack() {
			public void callback(boolean[] comb, int m) {
				int[] tempArr = new int[m];
				int pos = 0;
				for (int i = 0; i < comb.length; i++) {
					if (comb[i] == true) {
						tempArr[pos] = dataList.get(i);
						pos++;
						if (pos == m)
							break;
					}
				}
				list.add(tempArr);
			}
		});

		List<String> retList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for (int[] arr1 : list) {
			for (int b : arr1) {
				if(doubleFlag) {
					sb.append(String.format("%02d", b) + ",");
				} else {
					sb.append(b + ",");
				}
			}
			retList.add(sb.toString().substring(0, sb.toString().length() - 1));
			sb.setLength(0);
		}

		return retList;
	}

	public static void main(String[] args) {
		 
		String code = "01,02,03,04,05";
		String[] codes = code.split("\\,");
		final List<Integer> testNumList = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			testNumList.add(Integer.valueOf(codes[i]));
		}
		
		List<String> list = combinResult(testNumList,2);
		for(String n : list) {
			System.out.println(n);
		}
		System.out.println(list.size());
		
//		 final int[] arr = { 23, 23 ,23};
//		 final List<Long> count=new ArrayList<Long>();
//		 count.add(0l);
//		 traversalComb(arr, new TraversalCallBack() {
//		 public void callback(int[] indexArr) {
//			 boolean flag=true;
//			 Set<Integer> indexSet=new HashSet<Integer>();
//			 for(int index:indexArr){
//				 indexSet.add(index);
//			 }
//			 flag=indexSet.size()==indexArr.length;
//			 if(flag){
//				 LogUtil.out(Arrays.toString(indexArr));
//				 count.set(0, count.get(0)+1);
//			 }
//		 }
//		 });
//		 System.out.println(count.get(0));
//		String[] datas = new String[] { "a", "b", "c", "d" };
//		ArrayList al = new ArrayList();
//		sort(Arrays.asList(datas), new ArrayList(), al, 3);

		
//		ArrayList<String> al = (ArrayList<String>)permutationResult(1, 11, 2);
//		for (int i = 0; i < al.size(); i++)
//		System.out.println(al.get(i));

		// final List<Integer> testNumList=new ArrayList<Integer>();
		// for(int i=0;i<30;i++){
		// testNumList.add(i);
		// }
		// final List<int[]> list = new ArrayList<int[]>();
		//
		// efficientComb(testNumList.size(), 10, new CombCallBack() {
		// public void callback(boolean[] comb, int m) {
		// int[] tempArr = new int[m];
		// int pos = 0;
		// for (int i = 0; i < comb.length; i++) {
		// if (comb[i] == true) {
		// tempArr[pos] = testNumList.get(i);
		// pos++;
		// if (pos == m)
		// break;
		// }
		// }
		// // list.add(tempArr);
		// }
		//
		// });
		//
		// StringBuffer sb = new StringBuffer();
		// for (int[] arr1 : list) {
		// for (int b : arr1) {
		// sb.append(b);
		// }
		// System.out.println(sb);
		// sb.setLength(0);
		// }
	}

}
