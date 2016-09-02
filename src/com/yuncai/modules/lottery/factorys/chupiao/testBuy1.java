package com.yuncai.modules.lottery.factorys.chupiao;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
 
public class testBuy1 {

	//static String issue = "" ;
	static String url="http://210.14.139.194:9082/sports_web/mainServlet";//main.cl";//  
	static int dbType=1;
	
	public static String md5Hex(String s)
	{
		return MD5.Md5(s);
		//return DigestUtils.md5Hex(s); 
	} 
	
	public  void doInterface105(String transCode,String gameId,String ticketId,String issue )
	{				 
		dataXml105 RpData = new dataXml105(dbType,transCode,gameId,issue,ticketId); 
	    String statuscode= RpData.doPost(url);
	    System.out.println("==处理结果=="+statuscode);		
	}
	
	
	 public  void doInterface104(int id,String ticket_id)
	    {
	      String sql =" select id,gameId,issue,played,groupid,ticketsnum,totalmoney,ticketid,multiple,moneys,playtype,addflag,ball,cretime";
	    	  	sql+=" ,realTicketID,realname,idcard,phone "; //
	    	  	
	      		sql+=" from "+getDB_preName(dbType)+"sys_intf104 where rtvalue=-1";
	      		if (id>0)
	      		{
	      			sql+=" and id="+id;
	      		}
	      		if (ticket_id!=null && !"".equals(ticket_id.trim()))
	      		{
	      			sql+=" and ticketid='"+ticket_id+"'";
	      		}
	            sql +=" order by cretime";//,gameId,issue";
	       	
	           caipiaoDbTools db = new caipiaoDbTools(dbType);
	      	 	List list=db.ExcuteSql(sql);
	      	 	db=null;
	      	  
			if (list!=null)
			{
				String transCode="104";
				
				System.out.println("==需要处理=="+list.size());
			 
			       for (int i = 0; i < list.size(); i++) {
			           Map map = (Map) list.get(i);
			           int index= MapUtils.getIntValue(map, "id",0);
			           String gameId= MapUtils.getString(map, "gameId");
			           String issue= MapUtils.getString(map, "issue","");
			           int played= MapUtils.getIntValue(map, "played",-1);
			           int ticketsnum= MapUtils.getIntValue(map, "ticketsnum",-1);
			           int totalmoney= MapUtils.getIntValue(map, "totalmoney",-1);
			           int multiple= MapUtils.getIntValue(map, "multiple",-1);
			           int moneys= MapUtils.getIntValue(map, "moneys",-1);
			           int addflag= MapUtils.getIntValue(map, "addflag",-1);
			           String ticketId= MapUtils.getString(map, "ticketid");
			           String playtype= MapUtils.getString(map, "playtype");
			           String ball= MapUtils.getString(map, "ball");
			           String userid= MapUtils.getString(map, "userid");
			           String realname= MapUtils.getString(map, "realname");
			           String idcard= MapUtils.getString(map, "idcard");
			           String phone= MapUtils.getString(map, "phone");
			           String province=MapUtils.getString(map, "province","");
			           
			           //String dayc= MapUtils.getString(map, "dayc","");			           
			           //if (!"".equals(dayc)){ //   dayc = dayc.substring(0,10); }
 				    
				      dataXml104 RpData = new dataXml104(dbType,transCode,gameId,issue,ticketId);				      
				      
				      String statuscode= RpData.doPost(url,index,ticketsnum,totalmoney,province ,userid,	idcard,	realname,	phone
				    	    		   ,ticketId,	multiple,	moneys,	playtype,	addflag,ball);
				      System.out.println("==处理结果=="+statuscode);				      
			   }
			       
			  System.out.println("==已经处理=="+list.size());     
			  list.clear();
			  list=null;
			}
			System.out.println("==线束==");
 	
  }
	 
	 
    public  static String getSubmitTime()
    {
        java.util.Calendar   c   =   java.util.Calendar.getInstance();
        java.text.DateFormat   df   =   new   java.text.SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(c.getTime());    	 
    }

    public static String getUTF8(String value)
    {
    String content=value; 
		try {
			content=java.net.URLEncoder.encode(content,"UTF-8");
		} catch (Exception e) {
		e.printStackTrace();
		}
	  return content;
    }
    
    public static String getGBK(String value)
    {
    String content=value; 
		try {
			content=java.net.URLDecoder.decode(content,"gb2312");
		} catch (Exception e) {
		e.printStackTrace();
		}
	  return content;
    }
    
    public static int getRandomNum()
    {
    	int num = new Random().nextInt(9999);
    	while (num==0 || String.valueOf(num).length()<4)
    	{
    		num = new Random().nextInt(9999);
    	}
    	return  num ;      
    }
    
    public  static String getSubmitId()
    {
        java.util.Calendar   c   =   java.util.Calendar.getInstance();
        //java.text.DateFormat   df   =   new   java.text.SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        java.text.DateFormat   df   =   new   java.text.SimpleDateFormat("yyyyMMdd-HHmmss");
        String s= String.valueOf(System.currentTimeMillis());
        //System.out.println(s);
        int n=getRandomNum();
        //System.out.println(n);
        s = s.substring(s.length()-6,s.length());
        return df.format(c.getTime())+"-"+n+"-"+s;    	 
    }
    
    public static void getInf101Data(String gameId,String issue)
    {    	
		dataXml101 d = new dataXml101(dbType,"101",gameId,issue,"");
		d.doPost(url);
		System.out.println("==结束,issue=="+d.issue+",starttime=="+d.starttime+",endtime=="+d.endtime+",status=="+d.status+",palmtime=="+d.palmtime+",prizetime=="+d.prizetime+"");
    }
    
    public static void getInf130Data(String gameId,String issue)
    {    	
    	dataXml130 d = new dataXml130(dbType,"130",gameId,issue,"");
		d.doPost(url);
		System.out.println("==结束,issue=="+d.issue+",totalCount=="+d.totalCount+"");
    }
    
    public static void getInf131Data(String gameId,String issue)
    {    	
    	dataXml131 d = new dataXml131(dbType,"131",gameId,issue,"");
		d.doPost(url);
		System.out.println("==结束,issue=="+d.issue+",totalCount=="+",day=="+d.day+",issue=="+d.played+",sp=="+d.sp);
    }
    
    public static void getInf132Data(String gameId,String issue,String dayc,String played)
    {    	// 竞彩、北单
    	dataXml132 d = new dataXml132(dbType,"132",gameId,issue,"");
    	//System.out.println("==结束["+gameId+","+issue+","+dayc+","+played+"]==");
		System.out.println(d.doPost(url,dayc,played));
    }

    
    public static void getInf112Data(String gameId,String issue)
    {   //  老体彩   	
    	dataXml112 d = new dataXml112(dbType,"112",gameId,issue,"");
		d.doPost(url);
		System.out.println("==结束["+gameId+","+issue+",totalCount=="+d.totalCount);
    }
    
    public static String getInf112DataStr(String gameId,String issue)
    {   //  老体彩   	
    	dataXml112 d = new dataXml112(dbType,"112",gameId,issue,"");
		d.doPost(url);
		System.out.println("==结束["+gameId+","+issue+",totalCount=="+d.totalCount);	
		return d.statuscode;
    }
    
    public static void getInf122Data(String gameId,String issue,String dayc,String played)
    {    //	 122文件下载地址获取接口   prizeinfo    兑奖文件gameResult  场购买明细  issueResult  期购买明细
    	dataXml122 d = new dataXml122(dbType,"122",gameId,issue,"");
		System.out.println(d.doPost(url,dayc,played));
    }
    
    public static void getInf134Data(String gameId,String issue,String dayc,String played)
    {    	// 返回竞彩游戏某场的单关奖金。
    	dataXml134 d = new dataXml134(dbType,"134",gameId,issue,"");
    	//System.out.println("==结束["+gameId+","+issue+","+dayc+","+played+"]==");
		System.out.println(d.doPost(url,dayc,played));
    }
    
    public static void get101_130_131()
    {  
    	 String[] allCode={"-单场",
    	 "SPF",
    	 "SXDS",
    	 "JQS",
    	 "BF",
    	 "BQC",
    	 //"SF",
    	 "-竞彩",
    	 //"BSKSF",
    	 "BSKRFSF",
    	 "BSKSFC",
    	 "BSKDXF",
    	 "FTSPF",
    	 "FTBF",
    	 "FTJQS",
    	 "FTBQC",
    	 "FTFH",
    	 "BSKFH",
    	 "-老体彩",
    	 "DLT",//摇号开奖
    	 "PL3",//摇号开奖
    	 "PL5",//摇号开奖
    	 "QXC",//摇号开奖
    	 "C522",//摇号开奖
    	 "SPF14",
    	 "SPF9",
    	 "CJQ4",
    	 "CBQC6"};
    	 String transcode="130"; //101
    	    	 
    	 baseSplitXML d = null;
    	 
    	 for (int i=0;i<allCode.length;i++)
    	 {    		 
    		 if (allCode[i].startsWith("-"))	continue;
    		String gameId=allCode[i];
    		transcode="101"; 
    		String issue = "" ;
    		d = new dataXml101(dbType,transcode,gameId,issue,"");
    		d.doPost(url);
    		issue = d.issue ;
 
    		transcode="130";
    		d = new dataXml130(dbType,transcode,gameId,issue,"");
    		d.doPost(url);
    		issue = d.issue ;
    		
    		transcode="131";
    		d = new dataXml131(dbType,transcode,gameId,issue,"");
    		d.doPost(url);
    		 
    		System.out.println("===="+i);
    	 }
    		System.out.println("==线束==");
    }
    
    protected static String getDB_preName(int dbType)
    {        
    	String db_preName="caipiao.dbo.";
       if (dbType==0)
       {
       	return db_preName;
       }
       else return "";
    }
    
    public static void getAllData_132()
    {
      String sql =" select gameId,issue,played,dayc from "+getDB_preName(1)+"sys_intf130 ";//
           //sql +=" where cretime<'2013-06-12' ";
           sql +=" order by gameId,issue,convert(int,played)";
       	
           caipiaoDbTools db = new caipiaoDbTools(0);           
      	 List list=db.ExcuteSql(sql);
      	 db=null;
      	 dbType=1;
		if (list!=null)
		{
			System.out.println("==需要处理=="+list.size());
			String transcode="132";
		       for (int i = 0; i < list.size(); i++) {
		           Map map = (Map) list.get(i); 
		           String gameId= MapUtils.getString(map, "gameId");
		           String issue= MapUtils.getString(map, "issue","");
		           String dayc= MapUtils.getString(map, "dayc","");
		           int played= MapUtils.getIntValue(map, "played",-1);
		           if (!"".equals(dayc))
		           {
		        	   dayc = dayc.substring(0,10);
		           } 
		    		//String strXML="<querygameresult gameid='"+gameId+"' issue='"+issue+"' day='"+dayc+"' province='' played='"+played+"'/>";// //132
		    		//getIssue132(transcode,strXML,gameId,issue,played,dayc);
		           dataXml132 d =  new dataXml132(dbType,transcode,gameId,issue,"");		
		   		System.out.println("结果["+gameId+","+issue+","+dayc+","+played+"]==");
		   		String played_s="";
		   		if (played!=-1)
		   		{
		   			played_s = String.valueOf(played);
		   			while (played_s.length()<3)
		   			{
		   				played_s ="0"+played_s;		
		   			}
		   		}
		   		System.out.println(d.doPost(url,dayc,played_s));
		       }
		  System.out.println("==已经处理=="+list.size());     
		  list.clear();
		  list=null;
		}
		System.out.println("==线束==");
    }
    
    
    public static void addSPF104(String  userid,String issue,String ball,int ticketsnum)
    {
      try
	   {
    	String name="测试"+getRandomNum();
    	
    	String province=""; //	指定出票省份
    	
    	//		用户id		用户的真实姓名		身份证号码		电话号码
    	//String  userid="1103";
    	String realname=name,idcard="37010419801122999", phone="13800138000";
    	String sp="";
    	
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="SPF";   // 彩种编号
		//String issue="30702";	 //期次	 
		//int ticketsnum=1;    //彩票订单的总数量
		int totalmoney=2 * ticketsnum ;		//该次请求的彩票订单总额
		
		String playtype="2-1";  //玩法
		
		//倍数  ,是否追加  
		int multiple=1, addflag=0;
		int moneys=2;  //该张彩票的金额
		int count=0;
		//String ball="81:[3]/82:[1]";   //投注号码
		
//		String ticketId= getSubmitId(); //订单号（合作商的彩票交易流水号，唯一编号）

//		int count=f104.Save104(gameId, issue, ticketsnum, totalmoney, //彩种编号,期次,总数量,订单总额,
//				province, userid, idcard,name, phone, //出票省份,用户id,身份证号码,用户的真实姓名,	电话号码,
//				ticketId, multiple, moneys, playtype, addflag, ball, sp); //订单号,倍数,金额，玩法，是否追加，投注号码，sp
//--test1 07-17		
	//if (count==1)
	 
		
   		String strXML="<ticketorder gameid='"+gameId+"' ticketsnum='"+ticketsnum+"' totalmoney='"+totalmoney+"' province='"+province+"'>";
		strXML +="<user userid='"+userid+"' realname='"+getUTF8(realname)+"' idcard='"+idcard+"' phone='"+phone+"' />";
		strXML +="<tickets>";
		//--test1 07-17
	 for (int i=1;i<=ticketsnum;i++){
		  String ticketId= getSubmitId(); //订单号（合作商的彩票交易流水号，唯一编号）
		  count+=f104.Save104(gameId, issue, ticketsnum, totalmoney, //彩种编号,期次,总数量,订单总额,
				province, userid, idcard,name, phone, //出票省份,用户id,身份证号码,用户的真实姓名,	电话号码,
				ticketId, multiple, moneys, playtype, addflag, ball, sp); //订单号,倍数,金额，玩法，是否追加，投注号码，sp
		  f104.SaveIntf104Result(ticketId);
	 		      	
			strXML +="<ticket id='"+ticketId+"' multiple='"+multiple+"' issue='"+issue+"'";
			strXML +=" playtype='"+playtype+"' money='"+moneys+"' addflag='"+addflag+"'>";
			   			strXML +="<ball>"+ball+"</ball>";
	         strXML +="</ticket>";
	 } 
	//--test1 07-17		
	         strXML+="</tickets>";
    	strXML+="</ticketorder>";
    	f104.doPost(url,strXML);
	 
	//else
	 
	System.out.println("==处理结果==保存数据=="+count);
	 
// --test1 07-17	  	
  //t.doInterface104(-1,ticketId);//--test1 07-17	

	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    public static String getIssue101(String gameId)
    {
    	dataXml101 d = new dataXml101(dbType,"101",gameId,"","");
		d.doPost(url);
		return d.issue ;	
    }
    
    public   static String  getResult105(String transCode,String gameId,String ticketId,String issue )
	{				 
		dataXml105 RpData =  new dataXml105(dbType,transCode,gameId,issue,ticketId);
	    String statuscode= RpData.doPost(url);
	    System.out.println("==处理结果=="+statuscode);
	    return statuscode;
	}
    
    public static void addJQS104()
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='JQS' order by gameId,issue,convert(int,played)
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="JQS";
		String issue="30606";  //issue=getIssue101(gameId);// 获取最新issue期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		String ball="1:[3]/2:[1]";
		//msg="投注期次错误"   a1 //
		ball="27:[3]/28:[1]";  // a1  //stop日期必须大于当前时间，statec 必须=1购买状态
		ball="11:[3]/12:[1]"; 
		ball="13:[6]/14:[7]"; //最大数值=7
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        Thread.sleep(5000);
        String transCode="105";
		t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    
    public static void addSXDS104()
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='SXDS' order by gameId,issue,convert(int,played)
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="SXDS";
		String issue="30606";  //issue=getIssue101(gameId);// 获取最新issue期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		String ball="1:[3]/2:[1]";
		ball="11:[1]/12:[2]"; 
		
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        Thread.sleep(5000);
        String transCode="105";
		t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
   

    public static void addBF104()
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='BF' order by gameId,issue,convert(int,played)
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="BF";
		String issue="30606";  //issue=getIssue101(gameId);// 获取最新issue期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		String ball="1:[3]/2:[1]";
		ball="11:[1:0]/12:[C]";
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        Thread.sleep(5000);
        String transCode="105";
		t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    


    public static void addFTSPF104(String ball)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='FTSPF'  order by gameId,dayc,stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="FTSPF";
		String issue="";  //竞彩没有期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		//String ball="20130619|3|001|0;20130619|3|003|1";  
		//日期|周数|场次|注码
		//dayc|根据dayc所在的星期1=1...星期天=7|played|
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
		t.doInterface104(-1,ticketId);
        //Thread.sleep(5000);
        //String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }


    public static void addFTBF104(int userId_start,int userId_end,String ball1,String ball2)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='FTBF'  order by gameId,dayc,stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="FTBF";
		String issue="";  //竞彩没有期次		
		int ticketsnum=userId_end-userId_start + 1;
		int totalmoney=2*ticketsnum;		
		int count=0;
		int num=0;
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		String ball="20130619|3|002|09;20130619|3|003|09";  		 
		//日期|周数|场次|注码
		//dayc|根据dayc所在的星期1=1...星期天=7|played|
		
		//String ticketId= getSubmitId();				
		//f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);	
		//t.doInterface104(-1,ticketId);
   		String strXML="<ticketorder gameid='"+gameId+"' ticketsnum='"+ticketsnum+"' totalmoney='"+totalmoney+"' province='"+province+"'>";
		strXML +="<user userid='"+userid+"' realname='"+getUTF8(realname)+"' idcard='"+idcard+"' phone='"+phone+"' />";
		strXML +="<tickets>";
		//--test1 07-17
		//int userId_start=1177;//~ 1200;~768
		//int userId_end=1180; //~  1203;~792
		//addFTBF104(1177,1180,"");
	   for (int i=userId_start;i<=userId_end;i++)
	   {
		  if (++num % 2==0) {
			  ball= ball1;
		  } else { ball= ball2;}
		  userid = String.valueOf(i);
		  String ticketId= getSubmitId(); //订单号（合作商的彩票交易流水号，唯一编号）
		  count+=f104.Save104(gameId, issue, ticketsnum, totalmoney, //彩种编号,期次,总数量,订单总额,
				province, userid, idcard,name, phone, //出票省份,用户id,身份证号码,用户的真实姓名,	电话号码,
				ticketId, multiple, moneys, playtype, addflag, ball, sp); //订单号,倍数,金额，玩法，是否追加，投注号码，sp
		  f104.SaveIntf104Result(ticketId);
	 		      	
			strXML +="<ticket id='"+ticketId+"' multiple='"+multiple+"' issue='"+issue+"'";
			strXML +=" playtype='"+playtype+"' money='"+moneys+"' addflag='"+addflag+"'>";
			   			strXML +="<ball>"+ball+"</ball>";
	         strXML +="</ticket>";
	   }
	     strXML+="</tickets>";
    	strXML+="</ticketorder>";
    	f104.doPost(url,strXML);
    	System.out.println("===已经处理==="+count);
	  }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
   
    

    public static void addFTJQS104(String ball)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='FTJQS'  order by gameId,dayc,stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="FTJQS";
		String issue="";  //竞彩没有期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		//String ball="20130619|3|001|0;20130619|3|002|1";
		
		//日期|周数|场次|注码
		//dayc|根据dayc所在的星期1=1...星期天=7|played|
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        //Thread.sleep(5000);
       // String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }

    public static void addFTBQC104(String ball)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='FTBQC'  order by gameId,dayc,stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="FTBQC";
		String issue="";  //竞彩没有期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		//String ball="20130619|3|001|11;20130619|3|002|00";
		//日期|周数|场次|注码
		//dayc|根据dayc所在的星期1=1...星期天=7|played|
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        //Thread.sleep(5000);
       // String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }



    public static void addFTFH104(String ball) //足球混合过关
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='FTFH'  order by gameId,dayc,stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="FTFH";
		String issue="";  //竞彩没有期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="4-1";//注意事项
		int multiple=1, addflag=0,moneys=2;
		//String ball="FTSPF@20130521|2|001|0;FTJQS@20130521|2|002|0;FTBQC@20130521|2|003|00;FTBF@20130521|2|004|00";
		//日期|周数|场次|注码
		//dayc|根据dayc所在的星期1=1...星期天=7|played|
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
       // Thread.sleep(5000);
       // String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }

    
    public static void addFTBRQSPF104(String ball)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='BSKSF'  order by gameId,dayc,stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="FTBRQSPF";
		String issue="";  //竞彩没有期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="3-1";
		int multiple=1, addflag=0,moneys=2;
		//String ball="20130619|3|001|11;20130619|3|002|00";
		//ball="20130625|2|301|1;20130625|2|302|2";  
		//ball="20130712|5|301|1;20130712|5|302|2";
		//日期|周数|场次|注码
		//dayc|根据dayc所在的星期1=1...星期天=7|played|
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
       // Thread.sleep(5000);
        //String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    
    public static void addBSKSF104(String ball)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='BSKSF'  order by gameId,dayc,stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="BSKSF";
		String issue="";  //竞彩没有期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="2-1";
		int multiple=1, addflag=0,moneys=2;
		//String ball="20130619|3|001|11;20130619|3|002|00";
		//ball="20130625|2|301|1;20130625|2|302|2";  
		//ball="20130712|5|301|1;20130712|5|302|2";
		//日期|周数|场次|注码
		//dayc|根据dayc所在的星期1=1...星期天=7|played|
		
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
       // Thread.sleep(5000);
        //String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
     

    public static void addSPF14104()
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='SPF14'  order by  gameId,convert(int,played),stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="SPF14";
		String issue="13082";  //期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="1";  //注意
		
		int multiple=1, addflag=0,moneys=2;
		String ball="[1]/[3]/[1]/[0]/[1]/[0]/[3]/[3]/[3]/[0]/[3]/[1]/[3]/[1]";		
		//单场～  0:负		1:平		3:胜

		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        Thread.sleep(5000);
        String transCode="105";
		t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    


    public static void addSPF9104()
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='SPF9'  order by  gameId,convert(int,played),stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="SPF9";
		String issue="13082";  //期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="1";  //注意
		
		int multiple=1, addflag=0,moneys=2;
		String ball="[1]/[3]/[]/[0]/[1]/[0]/[3]/[]/[3]/[0]/[]/[]/[3]/[];[]/[]/[]/[0]/[1]/[0]/[3]/[1]/[3]/[0]/[]/[]/[]/[1]";
		ball="[1]/[3]/[]/[0]/[1]/[0]/[3]/[]/[3]/[0]/[]/[]/[3]/[]";
		//单场～  0:负		1:平		3:胜

		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        Thread.sleep(5000);
        String transCode="105";
		t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
     



    public static void addCJQ4104()
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='CJQ4'  order by  gameId,convert(int,played),stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="CJQ4";
		String issue="13090";  //期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="1";  //注意
		
		int multiple=1, addflag=0,moneys=2;
		String ball="[3]/[2]/[0]/[2]/[3]/[0]/[1]/[0]";

		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        Thread.sleep(5000);
        String transCode="105";
		t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
   
    
  
    
    public static void addCBQC6104(String issue,String playtype)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='CBQC6'  order by  gameId,convert(int,played),stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="CBQC6";
		//String issue="13089";  //期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		//String playtype="1";  //注意
		
		int multiple=1, addflag=0,moneys=2;
		String ball="[3]/[3]/[1]/[3]/[0]/[1]/[1]/[1]/[3]/[1]/[0]/[0]";

		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
       // Thread.sleep(5000);
       // String transCode="105";
	//	t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    
    
    public static void addQXC104(String issue)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='CBQC6'  order by  gameId,convert(int,played),stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="QXC";
		//String issue="13080";  //期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="1";  //注意
		
		int multiple=1, addflag=0,moneys=2;
		String ball="";
		ball="[5]/[8]/[2]/[4]/[6]/[5]/[8]";
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        
		//Thread.sleep(5000);
        //String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    

    public static void addPL3104(String issue)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='CBQC6'  order by  gameId,convert(int,played),stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="PL3";
		//String issue="13080";  //期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="1";  //注意
		
		int multiple=1, addflag=0,moneys=2;
		String ball="";
		ball="[1]/[9]/[3]";
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        
		//Thread.sleep(5000);
        //String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    

    public static void addPL5104(String issue)
    {
    	//select gameId,issue,played,dayc,statec,[matchstate],[stopdate] from [caipiao].[dbo].[sys_intf130_1]  
    	 // where cretime>'2013-06-18'   	AND gameid='CBQC6'  order by  gameId,convert(int,played),stopdate
      try
	   {
    	String name="测试"+getRandomNum();
    	String province="",userid="1199",realname=name,idcard="37010419801122999", phone="13800138000",sp="";
    	dataXml104 f104 = new dataXml104(dbType,"","","","");
    	testBuy1  t= new testBuy1();
    	
		String gameId="PL5";
		//String issue="13080";  //期次		
		int ticketsnum=1;
		int totalmoney=2;		
		
		String playtype="1";  //注意
		
		int multiple=1, addflag=0,moneys=2;
		String ball="";
		ball="[1]/[1]/[9]/[9]/[3]";
		String ticketId= getSubmitId();				
		f104.Save104(gameId, issue, ticketsnum, totalmoney, province, userid, idcard,name, phone, ticketId, multiple, moneys, playtype, addflag, ball, sp);
	
		t.doInterface104(-1,ticketId);
        
		//Thread.sleep(5000);
        //String transCode="105";
		//t.doInterface105(transCode,gameId,ticketId,issue);
	   }
	  
	  catch (Exception e) 
	    {
		  e.printStackTrace();
		}	
  }
    
    public static void query107(String gameId,String issue,String ticketId)
    {
    	dataXml107 d = new dataXml107(dbType,"107",gameId,issue,ticketId);
		d.doPost(url);
		System.out.println("==结束,issue=="+d.issue+",count=="+d.totalCount);  
    }
    
    public static void query135(String gameId)
    {
    	dataXml135 d = new dataXml135(dbType,"135",gameId,"","");
		d.doPost(url);
		System.out.println("==结束,issue=="+d.issue+",count=="+d.totalCount);  
    }
    
    
    public static void query105(String gameId,String issue,String ticketId)
    {    	
		dataXml105 RpData = new dataXml105(dbType,"105",gameId,issue,ticketId); 
	    String statuscode= RpData.doPost(url);
	    System.out.println("==处理结果=="+statuscode);	 	  
    }
    
	public static void main(String[] args) {
		 
		
		dbType=1;
		
		//getInf101Data("SPF","");   //返回彩种期次		
	//	getInf101Data("BSKSF","");   //返回彩种期次
		//getInf101Data("FTBF","");   //返回彩种期次
		
		//getInf101Data("QXC","");   //返回彩种期次
		//getInf101Data("PL3","");   //返回彩种期次
		getInf101Data("PL5","");   //返回彩种期次
		//getInf130Data("SPF","30707");    // 返回彩种比赛列表。    //	竞彩返回当前场次的信息。
		//getInf130Data("CBQC6","13089");    // 返回彩种比赛列表。    //	竞彩返回当前场次的信息。
		
		//getInf130Data("FTBQC","");    // 返回彩种比赛列表。    //	竞猜没有issue这个概念，返回当前场次的信息。
		//getInf130Data("BSKSF","");    // 返回彩种比赛列表。    //	竞猜没有issue这个概念，返回当前场次的信息。
		//getInf130Data("FTSPF",""); 
		//getInf130Data("FTBF","");		
		//getInf130Data("QXC","13077");   //数字彩，怎么有对阵呢。所以没有数据返回
		
		//getInf131Data("SPF","30608");  //返回彩种赔率是实时在变化，需要定时获取，建议1分钟获取一次。
		
		
		//竞彩没有期次
	//	getInf131Data("FTSPF","");  //返回彩种赔率是实时在变化，需要定时获取，建议1分钟获取一次。
		//getInf130Data("FTFH","");		
		
		////查询赛果数据
		
		int ncount=109; //==		///select * from   sys_intf130  where cretime>to_date('2013-05-28','yyyy-mm-dd')
		 // and issue='30607'		 //and gameid='SPF' order by cretime,played    ;
		
		for(int i=1;i<=ncount;i++)
		{
		 //getInf132Data("SPF","30607","",String.valueOf(i));  ////查询赛果数据
		}		
		 //getInf132Data("CJQ4","13090","","1");  ////查询赛果数据

		
		//getInf112Data("CJQ4","13090"); //7.29  开奖公告查询接口(112) 只有老体彩才有开奖公告
		//getInf112Data("CBQC6","13089"); //7.29  开奖公告查询接口(112)
		//getInf112Data("DLT","13084"); //7.29  开奖公告查询接口(112) 只有老体彩才有开奖公告
		 
		// getInf122Data("CJQ4","13090","","");  ////122文件下载地址获取接口   prizeinfo    兑奖文件gameResult  场购买明细  issueResult  期购买明细
		// getInf122Data("FTSPF","","2013-06-21","001");  ////122文件下载地址获取接口   prizeinfo    兑奖文件gameResult  场购买明细  issueResult  期购买明细

		
		//getInf134Data("FTSPF","","2013-06-21","001");  ////查询竞彩游戏某场的单关奖金返回 
		//getInf134Data("FTSPF","","2013-06-21","002");  ////查询竞彩游戏某场的单关奖金返回 
		//getInf134Data("FTSPF","","2013-06-21","003");  ////查询竞彩游戏某场的单关奖金返回
		//getInf134Data("FTSPF","","2013-06-23","003");  ////查询竞彩游戏某场的单关奖金返回
		//getInf134Data("FTSPF","","2013-06-24","003");  ////查询竞彩游戏某场的单关奖金返回
		
/*		
		getInf132Data("SPF","30607","","001");  ////查询赛果数据
		 
		 //竞彩没有期次
		getInf132Data("FTSPF","","2013-06-21","001");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-22","001");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-23","001");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-24","001");  ////查询赛果数据
		 
		getInf132Data("FTSPF","","2013-06-21","002");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-22","002");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-23","002");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-24","002");  ////查询赛果数据
		
		getInf132Data("FTSPF","","2013-06-21","003");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-22","003");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-23","003");  ////查询赛果数据
		getInf132Data("FTSPF","","2013-06-24","003");  ////查询赛果数据
		*/
		//投注期次错误::   指 期次 不是当前期次、有效的期次、投注号码里面的场次错误;
		//注码格式错误::   指 投注号码不正确
		
//--------北京单场------------
		
		//addSPF104("30706","81:[3]/82:[1]",2) ;
		//addSPF104("1103","30707","163:[3]/164:[1]",1) ; //2013-07-22
		//getResult105("105","SPF","20130625-164715-881-035350",""); //查出票结果
		//addJQS104() ;
		//addSXDS104();
		//addBF104();
//--------竞彩------------

		//addFTSPF104("20130619|3|001|0;20130619|3|003|1");
		   //   addFTSPF104("20130718|4|001|0;20130718|4|003|1");
//		addFTSPF104("20130722|1|002|0;20130722|1|004|1");  //2013-07-22
		//addFTBF104("20130718|4|001|09;20130718|4|003|09");//20130718
 	  //	addFTBF104(1103,1103,"20130719|5|011|09;20130719|5|012|09");//20130719
		 //addFTBF104(1199,1199,"20130719|5|002|09;20130719|5|003|09");//20130719
		//addFTBF104(1177,1180,"20130719|5|001|09;20130719|5|002|09","20130719|5|002|09;20130719|5|003|09");
		//addFTBF104(1200,1203,"");
		//addFTBF104(768,792,"20130719|5|001|09;20130719|5|002|09","20130719|5|002|09;20130719|5|003|09");
		
		//addFTJQS104("20130718|4|001|0;20130718|4|002|0");
		//addFTBQC104("20130718|4|001|00;20130718|4|003|00");
		 
		//addBSKSF104("20130718|4|301|1;20130718|4|302|2");  //2013-07-18
		//addFTFH104("FTSPF@20130718|4|001|0;FTJQS@20130718|4|002|0;FTBQC@20130718|4|003|00;FTBF@20130718|4|004|09");
		// addFTBRQSPF104("20130718|4|001|3;20130718|4|002|3;20130718|4|003|3");
		//
//--------老体彩------------		
		
		//addSPF14104();
		//addSPF9104();		
		//addCJQ4104();
		
		 //addCBQC6104("13089","1"); 
		
		//addQXC104("13082") ;
		//addPL3104("13186") ;
		//addPL5104("13186") ;
		
//---------------------------------
		
		//query105("QXC","13080","20130712-162336-420-416647") ;//查出票结果  // msg="交易中",需要对方查下
		//query105("QXC","13082","20130715-114445-491-885816");  //返回失败！
	//	query105("PL3","13186","20130712-163657-513-217180") ;//查出票结果
//		query105("PL3","13186","20130712-163658-333-218132") ;//查出票结果
		//query105("FTBF","","20130718-093737758-1726-457763") ;//查出票结果
		 //  query105("FTSPF","","20130718-101410849-8668-650854") ;//查出票结果
		//query105("SPF","","20130722-102658-9320-018023");
//---------------------------------		
		
		//query107("SPF","30702","20130703-111645-404-405446");  ////查中奖结果
		//query107("BSKSF","","20130625-175210-963-930062");
		//query107("SPF","30702","20130703-111645-404-405446/20130625-175210-963-930062");//必须是相同gameID才能合并一起查
		//query107("BSKSF","","20130712-154737-60-257917/20130712-154727-843-247930");

		
		//query135(""); //没有数据
		
		
	//dbType=1;
	//  get101_130_131() ;
		// getAllData_132();  //查询所有赛果数据			
	 

 }
	
	
	/* 

调试用：

select *  from [dbo].[sys_intf101] where cretime>'2013-07-3' and gameid='SPF' order by cretime  desc
select  * from [dbo].[sys_intf130] where cretime>'2013-07-3' and gameid='SPF' order by cretime  desc
select  * from [dbo].[sys_intf131] where cretime>'2013-07-3' order by cretime  desc

  select * from [dbo].[sys_intf104] where cretime>'2013-07-15' order by cretime  desc
select  * from [dbo].[sys_intf104Result] where cretime>'2013-07-15' order by cretime  desc
select  * from [dbo].[sys_intf105Result] where cretime>'2013-07-15' order by cretime  desc

//----------调试数据-----------
 * select * from  sys_intf104  where to_char(cretime,'yyyymmdd')='20130717'   order by cretime  desc 
;
select * from  sys_intf104Result  where to_char(cretime,'yyyymmdd')='20130717'   order by cretime  desc 
;
select * from  sys_intf105Result  where to_char(cretime,'yyyymmdd')='20130717'   order by cretime  desc

--update T_TICKET set send_date_time=null,status=0 WHERE ID=1199
-- update sys_intf104 set updtime=null,recresult=null WHERE ID=597

  -- to_DATE(cretime,'yyyy-mm-dd hh24:mi:ss') > to_date('2013-06-11 00:00:00','yyyy-mm-dd hh24:mi:ss')
--  select * from  sys_intf104 where  between  to_DATE('2013-06-11 00:00:00,'yyyy-mm-dd hh24:mi:ss') and to_date(sysdate,'yyyy-mm-dd hh24:mi:ss')
  -- order by cretime  desc
  String aa="<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"605\" partnerid=\"008603\" version=\"2.0\" time=\"20130717151233\"/><body><ticketresult id=\"20130717-151231078-4178-151086\" gameid=\"FTSPF\" palmid=\"130717151055102787\" multiple=\"1\" issue=\"\" playtype=\"2-1\" money=\"2\" statuscode=\"0002\" msg=\"交易成功\" orgserial=\"40-8055-557C8E0CF723\" orgcheckcode=\"8636046\" province=\"tj\" standby=\"{&quot;spInfo&quot;:&quot;3-001:[负=1.85^-1]/3-003:[平=3.65^-1]&quot;,&quot;maxBonus&quot;:&quot;13.51&quot;}\"/></body></msg>";
  aa="<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"605\" partnerid=\"008603\" version=\"2.0\" time=\"20130717165035\"/><body><ticketresult id=\"20130717-165004603-6301-004608\" gameid=\"FTSPF\" palmid=\"130717164829105307\" multiple=\"1\" issue=\"\" playtype=\"2-1\" money=\"2\" statuscode=\"0002\" msg=\"交易成功\" orgserial=\"6C-87D4-4F789228B37A\" orgcheckcode=\"8651046\" province=\"tj\" standby=\"{&quot;spInfo&quot;:&quot;3-001:[负=1.85^-1]/3-003:[平=3.65^-1]&quot;,&quot;maxBonus&quot;:&quot;13.51&quot;}\"/></body></msg>";
dataXml105 RpData = new dataXml105(1,"","","","");	
RpData.doAnXml(aa); 
RpData.saveLogFile ();	 
	*/ 
	 
	 
}
