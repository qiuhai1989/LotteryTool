package com.yuncai.core.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
	public static void out(Object o) {
		System.out.println(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + o.toString());
	}

	public static void out(Exception e) {
		System.out.println(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + e.getMessage());
	}

	public static void out(int i) {
		System.out.println(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + i);
	}

	public static void out(long i) {
		System.out.println(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + i);
	}

	public static void out(float i) {
		System.out.println(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + i);
	}

	public static void out(double i) {
		System.out.println(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + i);
	}

	public static void out(boolean i) {
		System.out.println(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + i);
	}

	public static void main(String[] arg) {
		int count = 100000;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			out("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
		long phase1 = System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
		long phase2 = System.currentTimeMillis() - startTime;
		System.out.println("phase1:" + phase1 + " phase2:" + phase2);

	}
}
