package com.yuncai.core.tools.ticket;
import java.util.Comparator;
public class DcCodeComparator implements Comparator{
	public int compare(Object o1, Object o2) {
		String code1 = (String) o1;
		String code2 = (String) o2;
		Integer i1 = Integer.valueOf(code1.substring(0, code1.indexOf('(')));
		Integer i2 = Integer.valueOf(code2.substring(0, code2.indexOf('(')));
		return i1.compareTo(i2);
	}

}
