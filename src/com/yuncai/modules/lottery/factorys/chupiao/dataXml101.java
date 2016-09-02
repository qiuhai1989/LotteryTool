package com.yuncai.modules.lottery.factorys.chupiao;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml101  extends baseSplitXML {


	private Logger log = Logger.getLogger(dataXml101.class);
	
	protected String starttime; //期次开始的时间	
	protected String endtime;   //期次结期的时间 
	
	protected String palmtime;   //平台接收订单的截至时间//体彩因为接单最小单位是场次，所以此信息为空
	protected String prizeball;  //开奖号码 //体彩期次应答中无开奖号码
	protected String prizetime;	 //期次的官方开奖时间  //竞彩因为没有期的开奖概念，所以此时间为空
	protected String status;//期状态信息
	//1为销售中 	//2为已结期  	//4 为已兑奖（兑奖是否完成以兑奖文件为准）

	protected String unionendtime; //合买订单接收截至时间 //此版暂不支持合买业务，此值为空
	protected String statuscode ;
	
	public dataXml101(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	
	 private void setValue(String nodeName,String value)
	 {
		    if (nodeName.equals("starttime"))
		    {
		    	if (value!=null)
		    	{
		    	   if (!"".equals(value.trim()))
		    		   value=SplitTime(value);
		    	   else
		    		  value="null";
		    	}
		    	starttime = value ;
		    }//
		    else
		    	if (nodeName.equals("endtime"))
			    {
		    		if (value!=null)
			    	{
			    	   if (!"".equals(value.trim()))
			    		   value=SplitTime(value);
			    	   else
			    		  value="null";
			    	}
		    		endtime = value ; 
			    }
		    else
	    	if (nodeName.equals("gameid"))
		    {
	    		gameId = value ; 
		    }else
		    if (nodeName.equals("issue"))
		    {
		    	if (value!=null && !"".equals(value.trim()))
		    	{
		    		issue = value ;
		    	}
		    	else issue="";
		    }
		    else
		    if (nodeName.equals("palmtime"))
		    {
		    	if (value!=null)
		    	{
		    	   if (!"".equals(value.trim()))
		    		   value=SplitTime(value);
		    	   else
		    		  value="null";
		    	}
		    	palmtime = value ;
		    }
		    else
		    if (nodeName.equals("prizeball"))
		    {
		    	prizeball = value ;
		    }
		    else
		    if (nodeName.equals("prizetime"))
		    {
		    	if (value!=null)
		    	{
		    	   if (!"".equals(value.trim()))
		    		   value=SplitTime(value);
		    	   else
		    		  value="null";
		    	}
		    	prizetime = value ;
		    }
		    else
		    if (nodeName.equals("status"))
		    {
		    	status = value ;
		    } 
		    else
			    if (nodeName.equals("unionendtime"))
			    {
			    	if (value!=null)
			    	{
			    	   if (!"".equals(value.trim()))
			    		   value=SplitTime(value);
			    	   else
			    		  value="null";
			    	}
			    	unionendtime = value ;
			    }
	 }
	 
	protected  void printNodeAttributes(Node node, String indent,String node_name)
    {
		 //~issueinfos=ELEMENT_NODE=issueinfo
		 String remark1="issueinfo";
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
		   String sql="insert into "+ getDB_preName()+"sys_intf101";
		   sql+="("+getColumn_ID()+"starttime,endtime,gameid,issue,palmtime,prizeball,prizetime,statusc,unionendtime,msg)";
		   String value=" values("+getColumn_ID_value("SEQ_caipiao101.Nextval,")+getDate(starttime)+","+getDate(endtime)+",'"+gameId+"','"+issue+"',"+getDate(palmtime)+",'"+prizeball+"',"+getDate(prizetime)+",'"+status+"',"+getDate(unionendtime)+",'";
		   value = value.replaceAll("'null'","null");
		   value = value.replaceAll("''","null");
		   value +=xmlData+"')";
		   int count = SaveDb(sql+value);
		   statuscode = String.valueOf(count);
		   writeMsg(" SaveDb="+count);
		   //insert into [caipiao].[dbo].[sys_intf101]
		   //(starttime,endtime,gameid,issue,palmtime,prizeball,prizetime,statusc,unionendtime,cretime)
		   //values(null,null,'2123','',null,'',null,'',null,getdate())                             
		 } else
			 super.printNodeAttributes(node, indent,node_name);
	 
    }
	

    protected  String  doPost(String url)
    {
        String strXML="<queryIssue gameid='"+gameId+"' issueno='"+issue+"'  province=''/>";//101   OK
	    doPost(url,strXML);
	    return statuscode;
    }

}
