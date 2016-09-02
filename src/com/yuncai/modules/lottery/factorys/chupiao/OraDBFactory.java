package com.yuncai.modules.lottery.factorys.chupiao;

import java.sql.Connection;

 
public class OraDBFactory {
    
    /** 数据库连接对象*/
    private OraDBConnection dbConn =null;// new OraDBConnection();
    
    /**
     * 获取数据库连接对象实例
     * 
     * @return
     */
    public OraDBConnection getDBConnectionInstance(){
        /** 如果为null就创建一个新的实例化对象且返回*/
        if(dbConn==null){
            dbConn = new OraDBConnection();
            return dbConn;
        }
        /** 如果不为null就直接返回当前的实例化对象*/
        else{
            return dbConn;
        }
    }
    
    /** 关闭数据库连接*/
    public void closeConnection(Connection conn){
        /** 如果为null就创建一个新的实例化对象*/
        if(dbConn!=null){
        	dbConn.closeConnection();
        	dbConn=null ;
        }
        
    }
    
}