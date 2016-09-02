package com.yuncai.modules.lottery.factorys.chupiao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 
import java.util.List;
import java.util.Map;
 

import org.apache.commons.collections.MapUtils; 
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;
 

public class caipiaoDbTools { 
 	
	private int dataType=0;
	private int error_count=0;
	private int conn_isClosed=0;
	static Connection ora_conn=null ;
	private Logger log = Logger.getLogger(caipiaoDbTools.class);
	
	public   caipiaoDbTools(int dataType)
	 {
		 this.dataType=dataType;
	 }
	 
	
	private  Connection getSQLconn()
	 {
		 Connection result_conn=null;
 		 String connUrl = "jdbc:sqlserver://211.155.23.227:4000;DatabaseName=caipiao;user=dopay1;password=dopay12345678";		 
		 String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try
         {
		 DbUtils.loadDriver(jdbcDriver);
		 result_conn = DriverManager.getConnection(connUrl);
         }
         catch (Exception e) {
				e.printStackTrace();
			}
         return   result_conn;
	 }
	 
	private Connection getORAconn()
	 {
		 Connection result_conn=null;
		    
		    String DB_URL = "jdbc:oracle:thin:@192.168.0.1:1521:FootballData";
		    DB_URL = "jdbc:oracle:thin:@113.64.125.29:1521:FootballData";
		    DB_URL = "jdbc:oracle:thin:@wayin.5166.info:1521:FootballData";
		    String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";		    
		    String DB_USERNAME = "clweb_fb_user";
		    String DB_PASSWORD = "LOTTERY";
         try
         {
        	  if (ora_conn==null)
        	  {
        	   Class.forName(DB_DRIVER); 
        	   result_conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        	   ora_conn = result_conn;
        	   int commit=0;
        	   if (ora_conn.getAutoCommit()) commit=1;
        	   log.info("ora_conn.getAutoCommit()=="+commit);
        	  }
        	  else
        	  {
        		  result_conn=ora_conn;
        	  }
         }
         catch (Exception e) {
				e.printStackTrace();
			}
         return   result_conn;
	 }
	 
	 public    Connection getConn(int dbType ) 
	 {
		 try 
		 {
			if (ora_conn!=null && ora_conn.isClosed())
			 {
				log.info("[ora_conn.isClosed()]=="+(++conn_isClosed));
				 ora_conn=null;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("[ora_conn.isClosed()]"+e.getMessage()); 
			//e.printStackTrace();
		}
        if (dbType==1)
        {
        	return getORAconn();
        }
        else
        	return getSQLconn();
	 }
	
	public int checkData(String sql,String key)
	{
			int result = 0;			
			List list=ExcuteSql(sql);
			if (list!=null)
			{
			       for (int i = 0; i < list.size(); i++) {
			           Map map = (Map) list.get(i); 
			  			result= MapUtils.getIntValue(map,key);
			  			break;
			       }
			}
			return result;
		}
		
	 public   List<Map> ExcuteSql(String sql)
	 {
	     List result=null;    
		 QueryRunner qr = new QueryRunner();
		 Connection conn=null;
		 try
		 {  
			 conn=getConn(dataType);
			 result= (List) qr.query(conn,sql,new MapListHandler());
		 }
			catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				if (conn!=null)
				{	
					freeConnection(conn);
				}
			}			
		return result; 		
     }
	 public   List<Map> ExcuteSql(Connection conn,String sql)
	 {
	     List result=null;    
		 QueryRunner qr = new QueryRunner();
		   try
     		 {
		       result= (List) qr.query(conn,sql,new MapListHandler());
	    	 }
			catch (Exception e) {
				e.printStackTrace();
			}
			
		return result; 		
     }
	 


	 public   List<Map> ExcuteSqlExt(int dbType,String sql)
	 {
	     List result=null;    
		 QueryRunner qr = new QueryRunner();
		 Connection conn=null;
		 try
		 {  
			 conn=getConn(dbType);
			 result= (List) qr.query(conn,sql,new MapListHandler());
		 }
			catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				if (conn!=null)
				{	
					//freeConnection(conn);
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DbUtils.closeQuietly(conn);
					conn=null;
				}
			}			
		return result; 		
     }
	 
	 
	 public Connection getConnection()
	 {
		 Connection conn=null;
		 try
		 {	
			 conn=getConn(dataType);
		 }
				catch (Exception e) {
					e.printStackTrace();
				}
		return conn; 
	 }
	
	 
	 public  int InsertRecord(String sql)
	 {
	     int result=-1;    
		 QueryRunner qr = new QueryRunner();
		 Connection conn=null;
		 try
		 {			 
			 conn=getConn(dataType);
			 result=qr.update(conn, sql) ;
		 }
			catch (Exception e) {
				//e.printStackTrace();
				error_count++;
				log.info("Error sql==["+sql+"]"+e.getMessage());
				log.info("error_count==["+error_count+"]");
				if (error_count<=2)
				{
					ora_conn=null;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					InsertRecord(sql);
				}
			}
			finally
			{
				if (conn!=null)
				{
					freeConnection(conn);
				}
			}			
		return result; 		
    }
	 
	 public  int InsertRecord(Connection conn,String sql)
	 {
	     int result=-1;    
		 QueryRunner qr = new QueryRunner();
		 
		  try
		   {			 
			 result=qr.update(conn, sql) ;
		   }
			catch (Exception e) {
				e.printStackTrace();
			}
		return result; 		
    }	 
	 
	 
	 public void freeConnection(Connection conn)
	 {
		 if (dataType==0)
		 {
			if (conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DbUtils.closeQuietly(conn);
				conn=null;
			}
		 }
	 }
 
}
