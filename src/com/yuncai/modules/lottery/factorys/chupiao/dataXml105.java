package com.yuncai.modules.lottery.factorys.chupiao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
 

public class dataXml105  extends baseSplitXML {


	private int played=1; 
	private int ticketsnum ;
	private int totalmoney ;
	private String province ;
	private String orgcheckcode ;
	private String orgserial ;
	private String standby ; 
//	private String ticketId ;
	private int multiple ;
	private int moneys ;
	private String playtype ; 
	private String ball ;
	private String sp ;
	private String palmid  ;
	private String statuscode ;
	private String returnmsg ;
	private int isReWrite=-1;
	private StringBuffer msgList=null;
	private String standby_sp;
	
	private Logger log = Logger.getLogger(dataXml105.class); 
 
	public dataXml105(int dbType ,String transcode,String gameId,String issue,String ticketId) {
		super(dbType ,transcode,gameId, issue,ticketId);
	}
	  
	
	protected void printNodeValue(Node node,String indent,String node_name)
	{
		super.printNodeValue(node, indent, node_name);		
	}
	
	
	
	 private void setValue(String nodeName,String value)
	 { 
		  
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
									    if (nodeName.equals("orgcheckcode"))
									       {
									    	orgcheckcode = value ;
										  }		    
									    else
										    if (nodeName.equals("orgserial"))
										       {
										    	orgserial = value ;
											  }		
										    else
												    if (nodeName.equals("province"))
												       {
												    	province = value ;
													  }		
												    else
													    if (nodeName.equals("gameid"))
													       {
													    	gameId = value ;
														  } else
															    if (nodeName.equals("standby"))
															       {
															    	if ("".equals(standby) && !"".equals(value))
															    	{
															    		standby = value ;
															    	}
																  }	
	 }
	    
	 
    protected   void printNodeAttributes(Node node, String indent,String node_name)
    {
    	 //~tickets=ELEMENT_NODE=ticket
		 String remark1="ticketresult";
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
			 
		   String sql=" insert into  "+getDB_preName()+"sys_intf105Result";
		     sql += " ("+getColumn_ID()+"gameId ,issue ,ticketId ,orgcheckcode,orgserial,palmid105 ,statuscode105,returnmsg105,standbys,province,rectime ,recResult)";		     
		     sql +=" values("+getColumn_ID_value("SEQ_caipiao104.Nextval,")+"'"+gameId+"','"+issue+"','"+ticketId+"','"+orgcheckcode+"','"+orgserial+"','"+palmid+"','"+statuscode+"','"+returnmsg+"','"+standby+"','"+province+"'";
			sql+=" ,"+getDefaultDate()+",'"+statuscode+"')";		   
		   int count = SaveDb(sql);
		   writeMsg(" SaveDb="+count);                 
    	//  
		    sql=" update  "+getDB_preName()+"sys_intf104Result";
		     sql += " set recResult='"+statuscode+"', rectime="+getDefaultDate()+",rtvalue=1";
		   String where=" where ticketId='"+ticketId+"'";		   
		   count = SaveDb(sql+where);
		   writeMsg(" UpdateDb="+count);
		//  
		    sql=" update  "+getDB_preName()+"sys_intf104Result";           // 修改投注后的出票结果状态参数
		     sql += " set recResult='"+statuscode+"', rectime="+getDefaultDate()+",rtvalue=1";
		     where=" where ticketId='"+ticketId+"'";		   
		   count = SaveDb(sql+where);
		   writeMsg(" UpdateDb="+count);
      //--
		   standby_sp="";
		   if (!"".equals(standby))
		   {
			   log.info(" standby is not null["+ticketId+"]=="+standby);
			   splitStandby();//
		   }
		   else
		   {
			   log.info(" standby is null["+ticketId+"]");
		   }
		   sql=" update   "+getDB_preName()+"sys_intf104";      // // 该记录投注后的最终出票状态结果
		   sql += " set rectime="+getDefaultDate()+" ";           //  
		   sql +=" ,recResult='"+statuscode+"'";                //对方出票 结果
		   sql +=" ,sp='"+standby_sp+"'";                //出票时候的赔率
		   where=" where ticketId='"+ticketId+"'";		   
		   count = SaveDb(sql+where);
		   writeMsg("  Update sys_intf104="+count+";"+where);     
      }
		 else
			 super.printNodeAttributes(node, indent,node_name);
   }

    private void splitStandby()
    {
  	  //--    	    	  
	    standby_sp="";
	    String s_list[]= standby.split("/");
	       String s1=s_list[0];
	             String s1_list[]= s1.split(";");
	             standby_sp=s1_list[s1_list.length-1]; //第一个
	             log.info("s1_list["+(s1_list.length-1)+"]=="+standby_sp);
	       String s2=s_list[s_list.length-1]; //最后一个
             String s2_list[]= s2.split(";");
             log.info("s2_list[0]=="+s2_list[0]);
             log.info("s2_list["+(s2_list.length-1)+"]=="+s2_list[s2_list.length-1]);
             for (int i=1;i<=s_list.length-2;i++)  //分解中间
             {
            	 standby_sp+="/"+s_list[i];
             }
             standby_sp+="/"+s2_list[0] ;//最后一个赔率
             standby_sp+="/"+s2_list[s2_list.length-1] ;//理论最高中奖金额
             log.info("standby_sp=="+standby_sp);
	  //--	
    }
    
    protected  String  doPost(String url)
    {
        String strXML="<queryticket id='"+ticketId+"'  gameid='"+gameId+"' issue='"+issue+"'/>"; 
	    doPost(url,strXML);
	    return statuscode;
    }
    
    protected  void writeMsg(String outfn,String value)
    {
    	if (isReWrite==-1)
    	{
         super.writeMsg(outfn, value);
    	}
    	else
    	{
    		value+="\r\n";
    		msgList.append(value);	
    	}
    }
    
    protected String getFileName()
    {
    	 Errorfn="d:\\data\\caipiao\\Error_"+gameId+"_"+transcode+"_"+prefix+ticketId;
         return "d:\\data\\caipiao\\"+gameId+"_"+transcode+"_"+prefix+ticketId ;	 
    }
    
    public void saveLogFile ()
    {
       if (msgList!=null)
       {
   	    this.transcode="105";
		FileOutputStream Output = 	null;
		try 
	       {
			   String outfn=getFileName();
			   Output = new FileOutputStream(outfn, true);
	    	   //Output.write(msgList.toString().getBytes());
			   Output.write(msgList.toString().getBytes("UTF-8"));
	    	   //
	       } 
	       catch (Exception e) {
	    	   log.info("writeMsg Exception:=="+e.getMessage());
			}
	       finally
	       {
	    	  if (Output!=null){
	    	   try 
	    	    {
				   Output.close();
		    	   Output=null;
	    	   	} catch (Exception e) 
	    	   	 {
	    	   		log.error("writeMsg Exception11:=="+e.getMessage());
			     }
	    	  }
	       }
	       msgList=null ;  
       }
    }
    
    public   void doAnXml(String xml)
	    { 
    	    isReWrite=0;
    	    msgList= new StringBuffer();
    	    standby = ""; 
    	    if (xml.indexOf("standby=")>0)
    	    {
    	    	String s= xml.substring(xml.indexOf("standby=")+8,xml.length());
    	    	if (s.indexOf("}")>=0)
    	    	{
    	    	  //log.info("s=="+s);
    	    	  standby = s.substring(2,s.indexOf("}"));
    	    	  log.info("standby=="+standby);
    	    	  ////standby==&quot;spInfo&quot;:&quot;3-001:[负=1.85^-1]/3-003:[平=3.65^-1]&quot;,&quot;maxBonus&quot;:&quot;13.51&quot;
    	    	  ////standby=" &quot;spInfo&quot;:&quot;4-001:[4=5.5,5=10,6=21,7+=28]/4-002:[0=8.5,1=4.1,2=3.25,3=4]/4-003:[0=26,1=8.5,5=5.8,7+=8.5]/4-004:[0=11,1=4.2,7+=28]&quot;,&quot;maxBonus&quot;:&quot;77.45&quot;";
    	    	  standby = standby.replaceAll("&quot","");
    	    	  log.info("standby=="+standby);
    	    	  xml= xml.substring(0,xml.indexOf("standby=")+9);
    	    	  xml +="\"/></body></msg>";
    	    	  log.info("xml=="+xml);
    	    	}
    	    }
    	    try {
				xml = new String(xml.getBytes("UTF-8"),"GBK");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("doAnXml Exception:=="+e.getMessage());
			}
			 log.info("new String(xml)=="+xml);
    		 AnXml(xml);
	    }
    
}
