package com.yuncai.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class DBUpload{
private static final int BUFFER_SIZE=16*1024;

//public static final String PATH_NAME="http://www.6636.com/upload/";//193服务器路径
//public static final String WEB_PATH="http://www.6636.com//";//193

	private static String URL = "D://upload/"; 
	
	/**
	 * 单张图片上传
	 * @param myFile
	 * @param fileName
	 * @return
	 */
	public static String upLoad(File myFile,String fileName,int num){
		if(null != myFile){
		Calendar cal=Calendar.getInstance(); //处理时间的一个类
        int year = cal.get(Calendar.YEAR);// 得到当前年
        int month = cal.get(Calendar.MONTH )+1;//月
        //int day = cal.get(Calendar.DAY_OF_MONTH);//日
        //int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
        
        String imageType=fileName.substring(fileName.lastIndexOf("."), fileName.length());//类型
        String imageFileName=String.valueOf(new Date().getTime())+num+imageType;//图片名称

        
        //生成保存图片的路径，File.separator是个跨平台的分隔符	
        String path=URL+"/pic/"+year+"/"+month; 
        String pathName="/pic/"+year+"/"+month+"/";
        
		File imageFile = new File(path);
		if(!imageFile.exists()){//如果不存在这个路径
			imageFile.mkdirs();//就创建
        }
		
		try {
			InputStream in=null;
			OutputStream out=null;
			try {
				in=new BufferedInputStream(new FileInputStream(myFile),BUFFER_SIZE);
				out=new BufferedOutputStream(new FileOutputStream(imageFile+"/"+imageFileName), BUFFER_SIZE);//要给文件名字
				byte[] buffer=new byte[BUFFER_SIZE];
				while(in.read(buffer)>0){
					out.write(buffer);
				}
			}finally{
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String image_name=pathName+imageFileName;
		return image_name;
	}
		return null;
	}
	
	/**
	 * 上传APK包、文件
	 * @param myFile
	 * @param fileName
	 * @param apk_id
	 * @return game/software/txt/apk
	 */
	public static String upLoadFile(File myFile,String fileName,String disk){
		if(disk==""){
			disk = URL;
		}
		if(null != myFile){
       String imageFileName=fileName;
        
        //生成保存图片的路径，File.separator是个跨平台的分隔符	
        String path=disk+"apk/"; 
        String pathName="/apk/";
        
		File imageFile = new File(path);
		if(!imageFile.exists()){//如果不存在这个路径
			imageFile.mkdirs();//就创建
        }
		
		try {
			InputStream in=null;
			OutputStream out=null;
			try {
				in=new BufferedInputStream(new FileInputStream(myFile),BUFFER_SIZE);
				out=new BufferedOutputStream(new FileOutputStream(imageFile+"/"+imageFileName), BUFFER_SIZE);//要给文件名字
				byte[] buffer=new byte[BUFFER_SIZE];
				while(in.read(buffer)>0){
					out.write(buffer);
				}
			}finally{
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String image_name=pathName+imageFileName;
		return image_name;
	}
		return null;
	}
	
	/**
	 * 删除文件
	 * @param fileName
	 */
	public static void deleteFile(String fileName){
		String path=URL+"/"+fileName;
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
	
	/**
	 * 获取文件大小
	 * @param f文件路径
	 * @return 格式化的文件大小
	 */
	public static String getFileSize(String fileName,String disk){
		//String fileName ="D:/webroot/sports/upload/apk/2012/6/28/12/1340856327265.docx";
		if(disk==""){
			disk = URL;
		}
		String path=disk+"/"+fileName;
		File f = new File(path);
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		long fileS =f.length();
		String size = df.format((double) fileS / 1048576);
		if(Double.valueOf(size)>0 && Double.valueOf(size)<1){
			size = "0"+size;
		}
		
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
}
