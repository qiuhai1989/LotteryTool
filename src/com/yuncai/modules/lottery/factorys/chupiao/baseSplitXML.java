package com.yuncai.modules.lottery.factorys.chupiao;
  
import java.io.BufferedReader;
import java.io.ByteArrayInputStream; 
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.InputStream;
import java.io.InputStreamReader; 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; 
  
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

public class baseSplitXML{
	 
	private String fn ="";
	protected String Errorfn ="";
	protected String transcode; //接口编号： 101，130，104.。。。
	protected String gameId;   //彩种
	protected  String issue;  //期号
	protected String ticketId;  // 104投注时的订单号（唯一编号）
	protected String xmlData;   //接口时发送或接收的xml数据
	protected int Pid; 
	protected int dbType=0; //0:--> MSSQL  ; 1--> ORA
	
    protected String db_preName="caipiao.dbo.";
    protected String prefix="";

   public baseSplitXML(int dbType ,String transcode,String gameId,String issue,String ticketId) {
	   this.transcode=transcode;
		 this.gameId=gameId;
		 this.ticketId=ticketId;
		 this.issue= issue;
		 this.dbType=dbType;
	}
     
     protected String getDB_preName()
     {        
        if (dbType==0)
        {
        	return this.db_preName;
        }
        else return "";
     }
     
     protected String getColumn_ID()
     {        
        if (dbType==0)
        {
        	return "";
        }
        else return "id,";
     }
     
     protected String getDefaultDate()
     {        
        if (dbType==0)
        {
        	return "getdate()";
        }
        else return "sysdate";
     }
     
     
     protected String getColumn_ID_value(String s)
     {        
        if (dbType==0)
        {
        	return "";
        }
        else return s;
     }
     
     protected String getDate(String s)
     {
        if (s==null || (s!=null && "".equals(s.trim())) || (s!=null && "null".equals(s.trim())))
    	   return "null";
    	else   
    	 if (dbType==0)
         {
         	return "'"+s+"'";
         }
         else return "to_date('"+s+"','yyyy-mm-dd hh24:mi:ss')";
    	   
     }
     
     protected String getFileName()
     {
    	 Errorfn="d:\\data\\caipiao\\Error_"+gameId+"_"+transcode+"_"+prefix+ticketId;
         return "d:\\data\\caipiao\\"+gameId+"_"+transcode+"_"+prefix+ticketId+"_"+System.currentTimeMillis()+".txt";	 
     }
     
	 protected void doReadXMLData(String xmlData)
	 {
		 try {
	         this.xmlData=xmlData;
	         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	         factory.setValidating(false);
	         factory.setNamespaceAware(false);
	         factory.setIgnoringElementContentWhitespace(true);
	         DocumentBuilder builder = factory.newDocumentBuilder();
	         Document doc = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));
	         printNode(doc, "","");
	         
	         
	      } catch (Exception e)
	      { 
	    	  String s=getTime()+" doReadXMLData Exception=="+e.getMessage();
	    	  writeMsg(Errorfn,transcode+"_"+gameId);
	    	  writeMsg(Errorfn,xmlData);
	    	  writeMsg(Errorfn,s);
	          System.out.println(s);
	      }  
	 } 
	 
	 public  static String getTime()
	    {
	        java.util.Calendar   c   =   java.util.Calendar.getInstance();
	        java.text.DateFormat   df   =   new   java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        return df.format(c.getTime());    	 
	    }
	 
	 public  static String getSubmitTime()
	    {
	        java.util.Calendar   c   =   java.util.Calendar.getInstance();
	        java.text.DateFormat   df   =   new   java.text.SimpleDateFormat("yyyyMMddHHmmss");
	        return df.format(c.getTime());    	 
	    }
	 
	 public static String md5Hex(String s)
		{
			return MD5.Md5(s);
			//return DigestUtils.md5Hex(s); 
		}
	 
	  protected  void writeMsg(String value)
	  {
		  checkOutFileName();
		  writeMsg(fn,value);
	  }
	  
	  protected  void writeMsg(String outfn,String value)
		{
		 
			FileOutputStream Output = 	null;
			try 
		       {
				   value+="\r\n";
				   Output = new FileOutputStream(outfn, true);
		    	   Output.write(value.getBytes());
		       } 
		       catch (Exception e) {
		    	   System.out.println("writeMsg Exception:=="+e.getMessage());
				}
		       finally
		       {
		    	  if (Output!=null){
		    	   try 
		    	    {
					   Output.close();
			    	   Output=null;
		    	   	} catch (IOException e) 
		    	   	 {
		    	   		System.out.println("writeMsg Exception11:=="+e.getMessage());
				     }
		    	  }
		       }
		}
	  
      private void doprint(String s)
      {
    	//  System.out.println(s); 
      }
      
	  protected void printNodeValue(Node node,String indent,String node_name)
		 {
		      // print the DOM tree
			 String s="";
		      short nodeType = node.getNodeType();
		      switch(nodeType)
		      {
		         case Node.DOCUMENT_NODE:
		        	 	s = indent +"~"+ node_name + "=DOCUMENT_NODE=" + node.getNodeName();
		        	 	doprint(s);
		            break;
		            
		         case Node.ELEMENT_NODE:
		        	 	s = indent +"~"+ node_name + "=ELEMENT_NODE=" + node.getNodeName();
		        	 	doprint(s);
		            break;
		            
		         case Node.TEXT_NODE:
		            String nodeValue = node.getNodeValue();
		            if (nodeValue != null)
		               {
		            	s = indent +"~"+ node_name + "=TEXT_NODE=" + nodeValue;
		            	doprint(s);
		               }
		            break;
		            
		         case Node.ATTRIBUTE_NODE:
			            String nodeValue1 = node.getNodeValue();
			            if (nodeValue1 != null)
			               {
			            	s =node.getNodeName()+"=" + nodeValue1;
			            	doprint(s);
			               }
			            break;
			            
		         default:
		        	 String nodeValue2 = node.getNodeValue();
		            if (nodeValue2 != null)
		               {
		            	s = indent +"~"+ node_name + "=default=" + nodeValue2;
		            	doprint(s);
		               }
		            break;
		      }
		      if (!"".equals(s))
		      {
		    	  writeMsg(fn,s);
		      } 
     }
    	
     
	 protected   void printNode(Node node, String indent,String node_name)
	   {
		 printNodeValue(node,indent,node_name);         
		 if (node.getAttributes()!=null)
	      {
			 printNodeAttributes(node, indent,node_name);
			 /*
			 writeMsg(fn,"--getAttributesCount=="+node.getAttributes().getLength());
			 for(int index = 0; index < node.getAttributes().getLength(); index ++)	      
			 {
				 //System.out.println("~~getAttributes~~" + index);
				// writeMsg(fn,"--count["+(index)+"]--");
				 Node nd = node.getAttributes().item(index);
				 printNodeValue(nd,indent+" ",node.getNodeName());
			 }
			 */
	      }
		 else
		 {/*
			  System.out.println("node.getNodeValue()=="+node.getNodeValue());	    		  
			  System.out.println("node.getTextContent()=="+node.getTextContent());
		      System.out.println("node.getLocalName()=="+node.getLocalName());	    						  
		      System.out.println("node.getNamespaceURI()=="+node.getNamespaceURI());
		  */
		 }
	      
	      NodeList nl = node.getChildNodes();
	      //System.out.println("~~n1.length[" + nl.getLength()+"]"+node_name);
	      for(int index = 0; index < nl.getLength(); index ++)
	      {
	    	  //System.out.println("~~getChildNodes~~[" + (index+1)+"]"+node_name); 
	         printNode(nl.item(index), indent + "   ",node.getNodeName());
	      }
	   }
	  
	 protected  String SplitTime(String s)
	 {
		 // s="20130607090000";
		 String result = s.substring(0,4);
			s = s.substring(4,s.length());
			result +="-"+ s.substring(0,2);
			s = s.substring(2,s.length());
			result +="-"+ s.substring(0,2);
			s = s.substring(2,s.length());
			result +=" "+ s.substring(0,2);
			s = s.substring(2,s.length());
			result +=":"+ s.substring(0,2);
			s = s.substring(2,s.length());
			result +=":"+ s;
		 return result;
		 //result="2013-06-07 09:00:00";
	 }
	 
     protected   void printNodeAttributes(Node node, String indent,String node_name)
     {
		 {
			 writeMsg("--getAttributesCount=="+node.getAttributes().getLength());
			 for(int index = 0; index < node.getAttributes().getLength(); index ++)
			 {
				 Node nd = node.getAttributes().item(index);
				 String nodeValue = nd.getNodeValue();
				 String nodeName = nd.getNodeName();
				 writeMsg(nodeName+"="+nodeValue);
			 }
		 }
     }
     
     protected int SaveDb(String sql)
     {
    	 writeMsg(" SaveDb SQL=["+sql+"]");
    	 int result =0;
    	 caipiaoDbTools db = new caipiaoDbTools(dbType);
    	 result =db.InsertRecord(sql);
    	 db=null;
    	 return result;
     }
    

     protected   String getUTF8(String value)
     {
     String content=value; 
 		try {
 			content=java.net.URLEncoder.encode(content,"UTF-8");
 		} catch (Exception e) {
 		e.printStackTrace();
 		}
 	  return content;
     }

     protected  String  doPost(String url)
     {
       return "";	 
     }
     
     protected  void   doPost(String url,String xmlData1)
 	{
 		
 		String strXML="<?xml version=\"1.0\" encoding=\"utf-8\"?>";
 		strXML+="<msg>";
 		strXML+="<head transcode=\""+transcode+"\" partnerid=\"008603\" version=\"2.0\" time=\""+getSubmitTime()+"\" />";	//134,109合作商账户查询接口	
 			strXML+="<body>";
 				strXML+=xmlData1;
 				strXML+="</body>";
 					strXML+="</msg>";
 					
 		String  signKey_0 = "ldyt123456";		
 		String partnerid="008603";
 		
 		//数字签名，由交易代码transcode+交易数据msg+由掌中彩分配的交易密匙进行md5得到的字符串
 		String combineString=transcode+strXML+signKey_0;
 		
 		String key=md5Hex(combineString);
/*
 		System.out.println("transcode=="+transcode); 
 		System.out.println("partnerid="+partnerid);
 		System.out.println("msg=="+strXML);
 		System.out.println("signKey_0=="+signKey_0);
 		System.out.println("combineString=="+combineString); 		
 		System.out.println("key=="+key); 
*/
  
 		HttpClient httpclient = new HttpClient();
 		PostMethod post = new PostMethod(url);
 		
 		NameValuePair param[] = new NameValuePair[5];
 		param[0]  = new NameValuePair("transcode",transcode); 
 		param[1]  = new NameValuePair("partnerid",partnerid);
 		param[2]  = new NameValuePair("msg",strXML);
 		param[3]  = new NameValuePair("key",key);
 		param[4]  = new NameValuePair("version","2.0");
 		
 	 
 		post.getParams().setParameter(HttpClientParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
 		post.setRequestBody(param); 
 		try
 		{ 
 			int result =  httpclient.executeMethod(post);
 		    System.out.println("getPostStatus=="+post.getStatusLine()+";postReponseCode=="+result);
 		    if (result == 200) 
 		    {	
 	    	   // String txt=new String(httpget.getResponseBody(),"GBK");
 	    	   //String txt=httpget.getResponseBodyAsString();
 	    	 InputStream resStream = post.getResponseBodyAsStream();  
 	    	 //BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
 	    	  BufferedReader br = new BufferedReader(new InputStreamReader(resStream,"UTF-8"));  
 	    	  StringBuffer resBuffer = new StringBuffer();  
 	    	  String resTemp = "";  
 	    	  while((resTemp = br.readLine()) != null){  
 	    		  resBuffer.append(resTemp);  
 	    	  }  
 	    	  String txt = resBuffer.toString();  
 	    	 
 	    	  System.out.println("--return XML data==");
 	    	  System.out.println(txt);
 	    	  //fn="d:\\data\\caipiao\\"+gameId+"_"+transcode+"_"+System.currentTimeMillis()+".txt";
 	    	  checkOutFileName() ;
 	    	  writeMsg(fn,"=="+gameId+"=="+transcode+"==");
 	    	  writeMsg(fn,"--send msg:");
 	    	  writeMsg(fn,strXML);
 		      writeMsg(fn,"--return msg:");
 		      writeMsg(fn,txt);
 		      AnXml(txt);  
 		   } 
 		} 
 		catch (Exception ex) 
 		{
 			System.out.println("postErrorData:=="+ex.getMessage());
 		}
 		  finally 
 		  {
 			    post.releaseConnection();
 				post=null;
 				httpclient = null;
 		 }   
 	}
 	
     private void checkOutFileName()
     {
 	    if (fn==null || (fn!=null && "".equals(fn.trim())))
	    {
	    	//fn="d:\\data\\caipiao\\"+System.currentTimeMillis()+".txt";
 	    	fn =  getFileName();
	    }
     }
     
     protected   void AnXml(String s)
 	    {   
    	   checkOutFileName();
 	    	String xml = s ;
 	    	  if (s.indexOf("msg=")>0 && s.indexOf("&key") >0)
 	    	  {
 	    		 xml = s.substring(s.indexOf("msg=")+4,s.indexOf("&key"));
 	    	  } 	    	  
 	    	  writeMsg(fn,"==data msg of return::");
 	    	  writeMsg(fn,xml);
 	    	  writeMsg(fn,"");
 	  	       
 	    	 doReadXMLData(xml); 
 	    }
 	 
}
     