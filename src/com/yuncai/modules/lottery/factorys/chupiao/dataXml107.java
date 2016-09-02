package com.yuncai.modules.lottery.factorys.chupiao;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml107  extends baseSplitXML 
{

	private String result; 
	private String statuscode ;
	protected int totalCount=0;
	private String palmid;
	private String prizemoney;
	private String tax;
	private String wintype;
	
	private Logger log = Logger.getLogger(dataXml107.class); 
 
	public dataXml107(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	
	 private void setValue(String nodeName,String value)
	 { 
		  
	    	if (nodeName.equals("palmid"))
		    {
	    		palmid = value ; 
		    }
	    	else
		    	if (nodeName.equals("tax"))
			    {
		    		tax = value ; 
			    }
		    
	    	else
		    if (nodeName.equals("prizemoney"))
		       {
		    	prizemoney = value ;
			  }
		    else
			    if (nodeName.equals("wintype"))
			       {
			    	wintype = value.trim() ;
				  }
			    else
					    if (nodeName.equals("id"))
					       {
					    	ticketId = value ;
						  }
	 } 
	 
    protected   void printNodeAttributes(Node node, String indent,String node_name)
    {
    	 String remark1="prizeticket";
    	 //System.out.println("node.getNodeType()=="+node.getNodeType()+";node_name"+node_name);
		 if ("prizetickets".equals(node_name) && node.getNodeName()!=null && remark1.equals(node.getNodeName()))
		 {			  
			 writeMsg("--getAttributesCount=="+node.getAttributes().getLength());
			 String nodeValue = "";
			 String nodeName = "";

			 for(int index = 0; index < node.getAttributes().getLength(); index ++)
			 {
				 Node nd = node.getAttributes().item(index);
				 nodeValue = nd.getNodeValue();
				 nodeName = nd.getNodeName();
				 writeMsg(nodeName+"="+nodeValue);
				 setValue(nodeName,nodeValue);
			 } 
			 
			   String sql=" update  "+getDB_preName()+"sys_intf105Result";
			   sql += " set wintype='"+wintype+"'";
			   
			   //N: 未中奖   ; B : 大奖   ; S : 小奖   ; W：未派奖   ; D：订单不存在

             if ("S".equals(wintype) || "B".equals(wintype)) //中奖
             {
          	   sql += " ,palmid107='"+palmid+"',prizemoney="+prizemoney+",tax="+tax;
             }				   
			   String where=" where gameId='"+gameId+"' and ticketId='"+ticketId+"'" ;
			   if (issue!=null && !"".equals(issue.trim()))
			   {
				   where += " and issue='"+issue+"'";  
			   }
			 
			   int count = SaveDb(sql+where);
			   writeMsg(" UpdateDb="+count); 
		 } 
			 else
				 super.printNodeAttributes(node, indent,node_name);
		 totalCount++;	 
    }


    protected String getFileName()
    {
    	 if (ticketId.indexOf("/")>=0)
 		{
 		  String ticketId_list[]=ticketId.split("/");
 		  return  "d:\\data\\caipiao\\"+gameId+"_"+transcode+"_"+prefix+ticketId_list[0]+"_"+ticketId_list.length+"_"+System.currentTimeMillis()+".txt";
 		}
         else
       return "d:\\data\\caipiao\\"+gameId+"_"+transcode+"_"+prefix+ticketId+"_"+System.currentTimeMillis()+".txt";	 
    }

    protected  String  doPost(String url)
    {
        String strXML="<queryprizes  gameid='"+gameId+"' issue='"+issue+"'>";//112
        if (ticketId.indexOf("/")>=0)
		{
		  String ticketId_list[]=ticketId.split("/");
		   for(int i=0;i<ticketId_list.length;i++)
		   {
			 //System.out.println("ticketId_list["+i+"]"+ticketId_list[i]);
			   strXML +="<queryprize id='"+ticketId_list[i]+"'/>";
		   }
		}
        else
        {
        	strXML +="<queryprize id='"+this.ticketId+"'/>";
        }
		strXML +="</queryprizes>";
	    doPost(url,strXML);
	    return statuscode;
    }
    
}
