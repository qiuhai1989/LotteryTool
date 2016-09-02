package com.yuncai.modules.lottery.factorys.chupiao;

 

import java.sql.Connection;
import java.sql.DriverManager;
 
public class OraDBConnection {
    
    /** Oracle数据库连接URL*/
    private final static String DB_URL = "jdbc:oracle:thin:@192.168.0.1:1521:FootballData";
    
    /** Oracle数据库连接驱动*/
    private final static String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    
    /** 数据库用户名*/
    private final static String DB_USERNAME = "clweb_fb_user";
    
    /** 数据库密码*/
    private final static String DB_PASSWORD = "LOTTERY";
    
     
    
    private static Connection conn=null;
    
    public OraDBConnection() 
    {    	
    	getConnection();
    }
    
    public Connection getConn()
    {
      return this.conn;	
    }
    
    private void getConnection(){
        /** 声明Connection连接对象*/
        
        try{
          if (conn==null)
          {
            /** 使用Class.forName()方法自动创建这个驱动程序的实例且自动调用DriverManager来注册它*/
            Class.forName(DB_DRIVER);
            /** 通过DriverManager的getConnection()方法获取数据库连接*/
            conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
          }          
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
   
    public void closeConnection( ){
        try{
            if(conn!=null){ 
                if(!conn.isClosed()){
                    conn.close();
                }
              conn=null ;  
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}