package com.yuncai;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * 配置类
 * @author gx
 *
 */
public class DbConfigure {

	public static String dB_URL;//数据库访问
	public static String dB_DRIVER;//数据库driver
	public static String dB_USERNAME; //用户名
	public static String dB_PASSWORD; //密码

	//oracle数据库
	public static String dB_ORACLEURL;//数据库访问
	public static String dB_ORACLEDRIVER;//数据库driver
	public static String dB_ORACLEUSERNAME; //用户名
	public static String dB_ORACLEPASSWORD; //密码
	
	
	
	static Logger log=Logger.getLogger(DbConfigure.class);
	static{
		try {
			init();
		} catch (IOException e) {
			log.error("加载配置文件出现错误！");
			e.printStackTrace();
		}
	}
	
	public static void init() throws IOException{
		Properties props = new Properties();
		InputStream is=DbConfigure.class.getResourceAsStream("/db_config.properties");
		props.load(is);
		dB_URL=props.getProperty("dB_URL");
		dB_DRIVER=props.getProperty("dB_DRIVER");
		dB_USERNAME=props.getProperty("dB_USERNAME");
		dB_PASSWORD=props.getProperty("dB_PASSWORD");
		
		dB_ORACLEURL=props.getProperty("dB_ORACLEURL");
		dB_ORACLEDRIVER=props.getProperty("dB_ORACLEDRIVER");
		dB_ORACLEUSERNAME=props.getProperty("dB_ORACLEUSERNAME");
		dB_ORACLEPASSWORD=props.getProperty("dB_ORACLEPASSWORD");
	}
}
