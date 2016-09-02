package com.yuncai.modules.lottery.factorys.chupiao;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml134  extends baseSplitXML 
{

	private String day="";//比赛日期（竞彩使用） 
	private String played=""; //场次
	private String result; 
	private String statuscode ;
	protected int totalCount=0;
	
	private Logger log = Logger.getLogger(dataXml134.class); 
 
	public dataXml134(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	
	protected void printNodeValue(Node node,String indent,String node_name)
	{
		super.printNodeValue(node, indent, node_name);
		 //String s="";
	      short nodeType = node.getNodeType();
	      if (nodeType==Node.TEXT_NODE)
	      {
	    	  // result ="printNodeValue~~"+ indent +"~"+ node_name + "=ELEMENT_NODE=" + node.getNodeValue();
	    	  result=node.getNodeValue(); 
          	 // System.out.println(s);
	    	  String sql="insert into  "+ getDB_preName()+"sys_intf132";
			   sql+="("+getColumn_ID()+"gameid,issue,played,dayc,result,msg)";
			   String value=" ";//
			   //String value=" values("+getColumn_ID_value("SEQ_caipiao132.Nextval,")+"'"+gameId+"','"+issue+"',"+played+","+getDate(day)+",'"+result+"','";
			   value = value.replaceAll("'null'","null");
			   value = value.replaceAll("''","null");
			   //value +=xmlData+"')"; // xmldata太长，超出了字段容量
			   value +="')";
			   int count = -2;//SaveDb(sql+value);
			   statuscode = result;//String.valueOf(count);
			   writeMsg(" SaveDb="+count);
			   totalCount++;
	      }
	}
	
	 
    protected   void printNodeAttributes(Node node, String indent,String node_name)
    {
    	 String remark1="msg";
    	 //System.out.println("node.getNodeType()=="+node.getNodeType()+";node_name"+node_name);
		 if ("body".equals(node_name) && node.getNodeName()!=null && remark1.equals(node.getNodeName()))
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
			 }
			 if ("未查询到数据".equals(nodeValue))
			 {
				 result="-1";	 
			 } else { result=nodeValue; }
			 
			 // System.out.println("nodeValue=="+nodeValue+" ; nodeName=="+nodeName);
			 String sql="insert into  "+ getDB_preName()+"sys_intf132";
			   sql+="("+getColumn_ID()+"gameid,issue,played,dayc,result,msg)";
			   String value=" ";//
			   //String value=" values("+getColumn_ID_value("SEQ_caipiao132.Nextval,")+"'"+gameId+"','"+issue+"',"+played+","+getDate(day)+",'"+result+"','";
			   value = value.replaceAll("'null'","null");
			   value = value.replaceAll("''","null");			   
			   //value +=xmlData+"')"; // xmldata太长，超出了字段容量
			   value +="')";
			   int count = -1;//SaveDb(sql+value);
			   statuscode = result;//String.valueOf(count);
			   writeMsg(" SaveDb="+count);         
		 } 
			 else
				 super.printNodeAttributes(node, indent,node_name);
		 totalCount++;	 
    }



    protected  String  doPost(String url,String dayc,String played)
    {      //7.24  竞彩游戏单关奖金返回  
    	this.played=played;
		this.day=dayc;
        String strXML="<querysinglebonus gameid='"+gameId+"' day='"+dayc+"' played='"+played+"' province='' />";//134
	    doPost(url,strXML);
	    return statuscode;
    }
    
}
