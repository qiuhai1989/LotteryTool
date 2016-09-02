package com.yuncai.modules.lottery.factorys.chupiao;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml131  extends baseSplitXML {


	private Logger log = Logger.getLogger(dataXml131.class);
	
	protected String day;  //比赛日期（竞彩使用） 
	protected int played;  //场次
	protected String sp;  //场次的sp值
	private String statuscode ;
	 protected int totalCount=0;
	 
	public dataXml131(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	
	 private void setValue(String nodeName,String value)
	 {
		    if (nodeName.equals("day"))
		    { 
		    	day = value ;
		    } 
		    else
	    	if (nodeName.equals("played"))
		    {
	    		played =  new Integer(value).intValue() ; 
		    }
	    	else
		    if (nodeName.equals("sp"))
		       {
		    	 sp = value ;
			    }
	 }
	 
	protected  void printNodeAttributes(Node node, String indent,String node_name)
    {
		 //~issueinfos=ELEMENT_NODE=issueinfo
		 String remark1="spInfo";
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

		   String sql="insert into "+ getDB_preName()+"sys_intf131";
		   sql+="("+getColumn_ID()+"gameid,issue,played,dayc,sp,msg)";
		   String value=" values("+getColumn_ID_value("SEQ_caipiao131.Nextval,")+"'"+gameId+"','"+issue+"',"+played+","+getDate(day)+",'"+sp+"','";
		   value = value.replaceAll("'null'","null");
		   value = value.replaceAll("''","null");
		 //value +=xmlData+"')"; // xmldata太长，超出了字段容量
		   value +="')";
		   int count = SaveDb(sql+value);
		   if (count==1) {totalCount++;}
		   statuscode = String.valueOf(count);
		   writeMsg(" SaveDb="+count);                              
		 } else
			 super.printNodeAttributes(node, indent,node_name);
	 
    }



    protected  String  doPost(String url)
    {
        String strXML="<querygamesp gameid='"+gameId+"' issue='"+issue+"'  province=''/>";//131
	    doPost(url,strXML);
	    return statuscode;
    }
    
    
}
