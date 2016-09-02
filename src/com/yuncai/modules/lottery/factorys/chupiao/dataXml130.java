package com.yuncai.modules.lottery.factorys.chupiao;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml130  extends baseSplitXML {

		
		private String desc;//=   // 比赛描述
		private String disdate;//=20130607233000   //比赛开始时间
		private String day;//=2013-06-07   比赛日期(竞彩该字段有值 格式 yyyy-mm-dd)
		private String home;//=利比亚                   (主)
		private String homedesc;//=利比亚       (主)
		private String matchstate;//=0  //场次停售状态
		//竞彩使用 		0 正常，可以销售  		1 不可以销售
    
		private String name;//=世界杯预选赛 (比赛名称)
		private int played;//=001        //场次   
		private String remark;//=0
		private String single;//=0
		private String state;//=1      //状态
		//1为销售中		2为已结期		3为已兑奖		4.为已兑奖

		private String stopdate;//=20130607232100    //停售时间
		private String visit;//=民主刚果   (客)
		private String visitdesc;//=民主刚果 (客)
		private String statuscode ;
	    protected int totalCount=0;
	    
	private Logger log = Logger.getLogger(dataXml130.class);
	

	public dataXml130(int dbType ,String transcode,String gameId,String issue,String ticketId) {
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
		    if (nodeName.equals("matchstate"))
		    {
		    	matchstate = value ;
		    } 
		    else
		    if (nodeName.equals("name"))
		    {
		    	name = value ;
		    }  
			else
			    if (nodeName.equals("played"))
			    {
			    	played = new Integer(value).intValue() ;
			    }
			    else
				    if (nodeName.equals("remark"))
				    {
				    	remark = value ;
				    }
				    else
					    if (nodeName.equals("single"))
					    {
					    	single = value ;
					    }
					    else
						    if (nodeName.equals("state"))
						    {
						    	state = value ;
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
			 writeMsg("--getAttributesCount=="+node.getAttributes().getLength());
			 for(int index = 0; index < node.getAttributes().getLength(); index ++)
			 {
				 Node nd = node.getAttributes().item(index);
				 String nodeValue = nd.getNodeValue();
				 String nodeName = nd.getNodeName();
				 writeMsg(nodeName+"="+nodeValue);
				 setValue(nodeName,nodeValue);
			 }
			  String sql="insert into "+ getDB_preName()+"sys_intf130";
			  sql +="("+getColumn_ID()+"gameId,issue,dayc,descc,disdate,home,homedesc,matchstate,name,played,remark,single,statec,stopdate,visit,visitdesc,msg)";			  
			   
			   String value=" values("+getColumn_ID_value("SEQ_caipiao130.Nextval,")+"'"+gameId+"','"+issue+"',"+getDate(day)+",'"+desc+"',"+getDate(disdate)+",'"+home+"','"+homedesc+"','"+matchstate+"','"+name+"',"+played+",'";
			   value += remark+"','"+single+"','"+state+"',"+getDate(stopdate)+",'"+visit+"','"+visitdesc+"','";
			   value = value.replaceAll("'null'","null");
			   value = value.replaceAll("''","null");
			   //value +=xmlData+"')"; // xmldata太长，超出了字段容量
			   value +="')";
			   int count = SaveDb(sql+value);
			   if (count==1) {totalCount++;}
			   statuscode = String.valueOf(count);
			   writeMsg(" SaveDb="+count);
		 }
		 else
			 super.printNodeAttributes(node, indent,node_name);
//		  insert into [caipiao].[dbo].[sys_intf130](dayc,descc,disdate,home,homedesc,matchstate,name,played,remark,single,statec,stopdate,visit,visitdesc,cretime)
//		   values('2013-06-07','','2013-06-08 08:00:00','圣安东尼奥银星','圣安东尼奥银星','0','美国女子篮球联盟','303','0','0','1','2013-06-07 23:46:00','芝加哥天空','芝加哥天空',getdate()) 
    }


    protected  String  doPost(String url)
    {
        String strXML="<querygames gameid='"+gameId+"' issue='"+issue+"'  province=''/>";//130  OK
	    doPost(url,strXML);
	    return statuscode;
    }
    
}
