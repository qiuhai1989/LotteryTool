package com.yuncai.modules.lottery.factorys.chupiao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml104  extends baseSplitXML {

 
	private int played=1;  //场次
	private int ticketsnum ; //投注总注数
	private int totalmoney ;  //投注总金额
	private String province ;  //省份
	private String userid ;       //用户ID
	private String idcard ;        //身份ID
	private String realname ;      // 姓名
	private String phone ;          //电话
	//private String ticketId ;
	private int multiple ;			//倍数
	private int moneys ;            //该张彩票的金额
	private String playtype ;       //玩法
	private int addflag ;  //是否追加
	private String ball ;       //  所选号码
	private String sp ;
	private String palmid  ;//彩票平台序列号
	private String statuscode ;
	private String returnmsg ;
	private String errorCode="";
 
	private Logger log = Logger.getLogger(dataXml104.class); 
  
	
	public dataXml104(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	
	private void setData(int ticketsnum,int totalmoney,String province ,
	String userid,	String idcard,	String realname,	String phone,
	String ticketId,	int multiple,	int moneys,	String playtype,	int addflag,String ball,String sp )
	{
		this.ticketsnum=ticketsnum;
		this.totalmoney=totalmoney;
		this.province=province;
		this.userid=userid;
		this.idcard=idcard;
		this.realname=realname;
		this.phone=phone;
		this.ticketId=ticketId;
		this.multiple=multiple;
		this.moneys=moneys;
		this.playtype=playtype;
		this.addflag=addflag;
		this.ball=ball;
		this.sp=sp;
	}
	
	public  int Save104(String gameId,String issue,int ticketsnum,int totalmoney,String province ,
			String userid,	String idcard,	String realname,	String phone,
			String ticketId,	int multiple,	int moneys,	String playtype,	int addflag,String ball,String sp )
	{
		this.gameId= gameId;
		this.issue= issue;
		this.transcode="104";
		//this.ticketId=ticketId;
		setData(ticketsnum, totalmoney, province, userid, idcard,realname, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	    String sql="insert into  "+ getDB_preName()+"sys_intf104";
	   sql+="("+getColumn_ID()+"gameid,issue,ticketsnum,totalmoney,province ,realTicketID,	idcard,	realname,	phone";
	   sql +=",ticketId,	multiple,	moneys,	playtype,	addflag,ball,sp)";	   

	   String value=" values("+getColumn_ID_value("SEQ_caipiao104.Nextval,")+"'"+gameId+"','"+issue+"',"+ticketsnum+","+totalmoney+",'"+province+"','"+userid+"','"+idcard+"','"+realname+"','"+phone+"'";
	   value +=",'"+ticketId+"',"+multiple+","+moneys+",'"+playtype+"',"+addflag+",'"+ball+"','"+sp+"'";
	   value +=")";
	   prefix="SaveDB-";
	   writeMsg(sql);
	   writeMsg(value);
	   int count = SaveDb(sql+value);
	   writeMsg(" SaveDb=="+count);
	  return count ;
	}
	
 
    
	protected void printNodeValue(Node node,String indent,String node_name)
	{
		super.printNodeValue(node, indent, node_name);		
	}
	 
	
	 private void setValue(String nodeName,String value)
	 {
		    if (nodeName.equals("addflag"))
		    { 
		    	addflag = new Integer(value).intValue() ;
		    } 
		    else
	    	if (nodeName.equals("money"))
		    {
	    		moneys =  new Integer(value).intValue() ; 
		    }
	    	else
		    	if (nodeName.equals("multiple"))
			    {
		    		multiple =  new Integer(value).intValue() ; 
			    }
		    
	    	else
		    if (nodeName.equals("id"))
		       {
		    	ticketId = value ;
			  }
		    else
			    if (nodeName.equals("issue"))
			       {
			    	issue = value ;
				  }
			    else
				    if (nodeName.equals("msg"))
				       {
				    	returnmsg = value ;
					  } else
						    if (nodeName.equals("statuscode"))
						       {
						    	statuscode = value ;
							  }
						    else
							    if (nodeName.equals("palmid"))
							       {
							    	palmid = value ;
								  }
							    else
								    if (nodeName.equals("playtype"))
								       {
								    	playtype = value ;
									  }
								    else
									    if (nodeName.equals("errorCode"))
									       {
									    	errorCode = value ;
										  }
								    	
	 }
	  
    
    
    protected   void printNodeAttributes(Node node, String indent,String node_name)
    {
    	 //~tickets=ELEMENT_NODE=ticket
		 String remark1="ticket";
		 if (node.getNodeName()!=null && remark1.equals(node.getNodeName()))
		 {
			 writeMsg("--getAttributesCount=="+node.getAttributes().getLength());
			 for(int index = 0; index < node.getAttributes().getLength(); index ++)
			 {
				 Node nd = node.getAttributes().item(index);
				 String nodeValue = nd.getNodeValue();
				 String nodeName = nd.getNodeName();
				 writeMsg(nodeName+"="+nodeValue);
				 setValue(nodeName,nodeValue);
			 }
           if (issue==null){
        	   issue="";
           }
		   String sql=" update "+getDB_preName()+"sys_intf104Result";
		   sql += " set palmid104='"+palmid+"',statuscode104='"+statuscode+"',returnmsg104='"+returnmsg+"',updtime="+getDefaultDate()+",rtvalue=0";
			 sql +=",issue='"+issue+"'";
		   String where=" where ticketId='"+ticketId+"'";		   
		   int count = SaveDb(sql+where);
		   writeMsg(" Update sys_intf104Result="+count+";"+where);                 
    	//super.printNodeAttributes(node, indent,node_name); 
		   sql=" update "+getDB_preName()+"sys_intf104";
		   sql += " set updtime="+getDefaultDate()+",rtvalue=0";           // -->该记录已经请求投注
		   sql +=" ,recResult='"+statuscode+"'";                //投注状态结果
		   where=" where ticketId='"+ticketId+"'";		   
		   count = SaveDb(sql+where);
		   writeMsg("  Update sys_intf104="+count+";"+where);              
      }
		 else
			 super.printNodeAttributes(node, indent,node_name);
		  
			 
   }
    public   int SaveIntf104Result()
    {
    	String sql="insert into "+getDB_preName()+"sys_intf104Result";
    	 sql +="("+getColumn_ID()+"Pid,ticketId)";
 	    String value=" values("+getColumn_ID_value("SEQ_caipiao104Result.Nextval,")+Pid+",'"+ticketId+"')"; 
 	    return SaveDb(sql+value);	
    }
	 

    public   int SaveIntf104Result(String ticketId_1)
    {
    	String sql="insert into "+getDB_preName()+"sys_intf104Result";
    	 sql +="("+getColumn_ID()+"Pid,ticketId)";
 	    String value=" values("+getColumn_ID_value("SEQ_caipiao104Result.Nextval,")+Pid+",'"+ticketId_1+"')"; 
 	    return SaveDb(sql+value);	
    }
    
    protected  String   doPost(String url,int pid,int ticketsnum,
    		int totalmoney,String province ,String userid,	String idcard,	String realname,	String phone
    		   ,String ticketId,	int multiple,	int moneys,	String playtype,	int addflag,String ball)
 	{
    	statuscode ="-1";	
    	Pid=pid;
    	if (SaveIntf104Result()==1)
    	{
    		String strXML="<ticketorder gameid='"+gameId+"' ticketsnum='"+ticketsnum+"' totalmoney='"+totalmoney+"' province='"+province+"'>";
			strXML +="<user userid='"+userid+"' realname='"+getUTF8(realname)+"' idcard='"+idcard+"' phone='"+phone+"' />";
			strXML +="<tickets>";  
				strXML +="<ticket id='"+ticketId+"' multiple='"+multiple+"' issue='"+issue+"'";
				strXML +=" playtype='"+playtype+"' money='"+moneys+"' addflag='"+addflag+"'>";
				   			strXML +="<ball>"+ball+"</ball>";
		         strXML +="</ticket>";
		         strXML+="</tickets>";
        	strXML+="</ticketorder>";
          super.doPost(url,strXML);
    	}
    	return statuscode;
 	}
	 
}
