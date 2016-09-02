package com.yuncai.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import com.yuncai.core.tools.FileTools;

/**
 * json格式文件操作工具类
 * @author blackworm
 *
 */
public class JsonFileUtil {
	public static boolean writeJson(String content,String path,String fileName){
		boolean isTrue=false;
		try {
		    JsonFactory jfactory = new JsonFactory();
		    /*** write to file ***/
		    JsonGenerator jGenerator = jfactory.createJsonGenerator(new File(path+fileName), JsonEncoding.UTF8);
		    /*jGenerator.writeStartObject(); // {
		    jGenerator.writeStringField("name", "java牛"); // "name" : "java牛"
		    jGenerator.writeNumberField("age", 29); // "age" : 29

		    jGenerator.writeFieldName("messages"); // "messages" :
		    jGenerator.writeStartArray(); // [

		    jGenerator.writeString("msg 1"); // "msg 1"
		    jGenerator.writeString("msg 2"); // "msg 2"
		    jGenerator.writeString("msg 3"); // "msg 3"
		    jGenerator.writeEndArray(); // ]
		    jGenerator.writeEndObject(); // }
		    */		    
		    jGenerator.writeString(content);
		    jGenerator.close();
		    
		} catch (JsonGenerationException e) {
		    e.printStackTrace();
		    return isTrue;
		} catch (IOException e) {
		    e.printStackTrace();
		    return isTrue;
		}
		isTrue=true;
		return isTrue;
	}
	
	public static void readJson(String path){
		try {
		    JsonFactory jfactory = new JsonFactory();
		    JsonParser jParser = jfactory.createJsonParser(new File(path));
		    // loop until token equal to "}"
		    while (jParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jParser.getCurrentName();
				if ("name".equals(fieldname)) {
				    // current token is "name",
				    // move to next, which is "name"'s value
				    jParser.nextToken();
				    System.out.println(jParser.getText());
				}
				if ("age".equals(fieldname)) {
				    // current token is "age",
				    // move to next, which is "name"'s value
				    jParser.nextToken();
				    System.out.println(jParser.getIntValue()); // display 29
				}
				if ("messages".equals(fieldname)) {
				    jParser.nextToken(); // current token is "[", move next
				    // messages is array, loop until token equal to "]"
				    while (jParser.nextToken() != JsonToken.END_ARRAY) {
				    	// display msg1, msg2, msg3
				    	System.out.println(jParser.getText());
				    }
				}
		    }
		    jParser.close();
		} catch (JsonGenerationException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static String readJsonFile(String path) throws Exception{
		InputStream is=null;
		StringBuffer sb = new StringBuffer();
		try {
			File file=new File(path);
			if(file.exists()){
				is = new FileInputStream(file);
				byte[] bytes=new byte[1024];
				int length=0;
				while(length!=-1){
					length=is.read(bytes);
					sb.append(new String(bytes,0,length==-1?0:length));//,"gbk"
				}
			}
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if(is!=null) is.close();
		}
		return sb.toString();
	}
	
	public static void writeJsonFile(String content,String path,String fileName){
		FileWriter fw = null;
		if(content!=null){
			try {
				FileTools.mkdirs(path);
				fw = new FileWriter(path+fileName);
				fw.write(content);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}

	}
}
