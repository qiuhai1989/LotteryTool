package com.yuncai.modules.lottery.factorys.chupiao;

import java.text.DecimalFormat;

public class chkMem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DecimalFormat df1  = new DecimalFormat("0");
	    System.out.println("freeMemory=="+df1.format(Runtime.getRuntime().freeMemory()/ 1000.0)+" K");
	    System.out.println("totalMemory=="+df1.format(Runtime.getRuntime().totalMemory()/ 1000.0)+" K");
		DecimalFormat df2  = new DecimalFormat("0.00");
	    double c = (double) Runtime.getRuntime().freeMemory()/(double) Runtime.getRuntime().totalMemory();
	    String rate=df2.format(c * 100);	    
	    System.out.println("rate=="+rate);	    
	}

}
