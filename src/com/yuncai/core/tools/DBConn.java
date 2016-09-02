package com.yuncai.core.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yuncai.DbConfigure;

public class DBConn {
	/**
	 * TODO
	 * @author gx
	 * Apr 3, 2014 5:56:11 PM
	 */
	
	public static Connection getConn(String db){
		String driverName="";
		String dbURL="";
		String userName = "";  //默认用户名
		String userPwd = ""; //密码
		if(db.equals("sqlServer")){
//			driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动
//			dbURL = "jdbc:sqlserver://192.168.0.1:1444; DatabaseName=do_caipiao";  //连接服务器和数据库test
//			userName = "sql08-luomin";
//			userPwd = "sxWkRNEfeV";
			driverName = DbConfigure.dB_DRIVER; 
			dbURL = DbConfigure.dB_URL; 
			userName = DbConfigure.dB_USERNAME; 
			userPwd = DbConfigure.dB_PASSWORD; 
		}else if(db.equals("Oracle")){
			driverName = DbConfigure.dB_ORACLEDRIVER; 
			dbURL = DbConfigure.dB_ORACLEURL; 
			userName = DbConfigure.dB_ORACLEUSERNAME; 
			userPwd = DbConfigure.dB_ORACLEPASSWORD; 
		}
		Connection dbConn=null;
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConn;
	}
	
	public static void main(String[] args) {
		Connection dbConn =DBConn.getConn("sqlServer");
		String sql = "select NoticeName from T_ApkNotice";
		PreparedStatement ps;
		try {
			ps = dbConn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				System.out.println(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
