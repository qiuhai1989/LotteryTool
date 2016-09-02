package com.yuncai.modules.lottery.factorys.chupiao;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXmlAther  extends baseSplitXML {


	private Logger log = Logger.getLogger(dataXmlAther.class); 
	public dataXmlAther(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	
	protected void printNodeValue(Node node,String indent,String node_name)
	{
		super.printNodeValue(node, indent, node_name);
		 String s="";
	      short nodeType = node.getNodeType();
	      if (nodeType==Node.TEXT_NODE)
	      {
	    	  s ="~~来自字类~~"+ indent +"~"+ node_name + "=ELEMENT_NODE=" + node.getNodeValue();
          	  System.out.println(s);
          	  //
          	   /*
          		String day="";
        		int played=1;
          	 	String sql="insert into [caipiao].[dbo].[sys_intf132]";
			   	sql+="(gameid,issue,played,dayc,result,cretime,msg)";
			   	String value=" values('"+gameId+"','"+issue+"',"+played+",'"+day+"','"+node.getNodeValue()+"',getdate(),'";
			   	value = value.replaceAll("'null'","null");
			   	value = value.replaceAll("''","null");
			   	value +=xmlData+"')";
			   	int count = SaveDb(sql+value);
			   	writeMsg(" SaveDb="+count);
			  */         
	      }
	      
	}
	 
}
