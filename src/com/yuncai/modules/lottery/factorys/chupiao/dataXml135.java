package com.yuncai.modules.lottery.factorys.chupiao;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml135  extends baseSplitXML {

		
		private String desc;//=   // 比赛描述
		private String disdate;//=20130607233000   //比赛开始时间
		private String day;//=2013-06-07   比赛日期(竞彩该字段有值 格式 yyyy-mm-dd)
		private String home;//=利比亚                   (主)
		private String homedesc;//=利比亚       (主) 
    
		private String game;//=世界杯预选赛 (比赛名称)
		private int played;//=001        //场次   
	
		private String stopdate;//=20130607232100    //停售时间
		private String visit;//=民主刚果   (客)
		private String visitdesc;//=民主刚果 (客)
		private String statuscode ;
	    protected int totalCount=0;
	     

	    
	private Logger log = Logger.getLogger(dataXml135.class);
	

	public dataXml135(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	
	 private void setValue(String nodeName,String value)
	 {
		    if (nodeName.equals("day"))
		    { 
		    	day = value ;
		    }//
		    else
		    	if (nodeName.equals("desc"))
			    { 
		    		desc = value ; 
			    }else
				    if (nodeName.equals("disdate"))
				    {
				    	if (value!=null)
				    	{
				    	   if (!"".equals(value.trim()))
				    		   value=SplitTime(value);
				    	   else
				    		  value="null";
				    	}
				    	disdate = value ;
				    }
		    else
	    	if (nodeName.equals("home"))
		    {
	    		home = value ; 
		    }else
		    if (nodeName.equals("homedesc"))
		    {
		    	homedesc = value ;
		    } 
		    else
		    if (nodeName.equals("game"))
		    {
		    	game = value ;
		    }  
			else
			    if (nodeName.equals("played"))
			    {
			    	played = new Integer(value).intValue() ;
			    }
		    else
			    if (nodeName.equals("stopdate"))
			    {
			    	if (value!=null)
			    	{
			    	   if (!"".equals(value.trim()))
			    		   value=SplitTime(value);
			    	   else
			    		  value="null";
			    	}
			    	stopdate = value ;
			    }else
				    if (nodeName.equals("visit"))
				    {
				    	visit = value ;
				    }
			    else
				    if (nodeName.equals("visitdesc"))
				    {
				    	visitdesc = value ;
				    }  
	 }
	 
	
	protected  void printNodeAttributes(Node node, String indent,String node_name)
    {
		 // ~games=ELEMENT_NODE=game
		 String remark1="game";
		 if (node.getNodeName()!=null && remark1.equals(node.getNodeName()))
		 {
			 super.printNodeAttributes(node, indent,node_name);
		 }
		 else
			 super.printNodeAttributes(node, indent,node_name);
//		  insert into [caipiao].[dbo].[sys_intf130](dayc,descc,disdate,home,homedesc,matchstate,name,played,remark,single,statec,stopdate,visit,visitdesc,cretime)
//		   values('2013-06-07','','2013-06-08 08:00:00','圣安东尼奥银星','圣安东尼奥银星','0','美国女子篮球联盟','303','0','0','1','2013-06-07 23:46:00','芝加哥天空','芝加哥天空',getdate()) 
    }


    protected  String  doPost(String url)
    {
        String strXML="<querygamess gameid='"+gameId+"'  province=''/>";//135
	    doPost(url,strXML);
	    return statuscode;
    }
    
}
