package com.yuncai.modules.lottery.action.admin;

import com.yuncai.core.util.DBUpload;

public class test {

	public static void main(String[] args) {
		String s = "D://My Documents/Workspaces2/.metadata/.me_tcat/webapps/ROOT";
		//System.out.println(DBUpload.getFileSize("/upload/apk/langlaile.apk", s));
		
		String a = "D:\\My Documents\\Workspaces2\\.metadata\\.me_tcat\\webapps\\ROOT";
		String b = "D:/My Documents/Workspaces2/.metadata/.me_tcat/webapps/ROOT//upload//";
		System.out.println(DBUpload.getFileSize("/upload/apk/langlaile.apk", a));
	}
}
