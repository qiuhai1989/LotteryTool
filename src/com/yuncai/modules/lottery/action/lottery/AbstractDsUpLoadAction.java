package com.yuncai.modules.lottery.action.lottery;

import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.FileTools;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.action.BaseAction;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

public class AbstractDsUpLoadAction extends BaseAction {
	
	private static final int BUFFER_SIZE = 5024 * 1024;

	public boolean checkFile(File file, String fileFileName) {
		if (file == null) {
			super.errorMessage = "文件不能为空!";
			return false;
		}
		if (fileFileName.toLowerCase().indexOf(".txt") < 0) {
			super.errorMessage = "文件类型不对!";
			return false;
		}
		if (file.length() > BUFFER_SIZE) {
			super.errorMessage = "文件超过限制大小!";
			return false;
		}
		return true;
	}

	public String saveFile(File file) {
		String times = StringTools.randomString(18);

		// 根据当前时间创建文件名
		String newFileName = ServletActionContext.getServletContext().getRealPath(File.separator + "dsUpFile") + File.separator
				+ DateTools.getNowDateYYMMDD() + File.separator + times + ".txt";

		// 创建文件
		File dirFile = new File(ServletActionContext.getServletContext().getRealPath(
				File.separator + "dsUpFile" + File.separator + DateTools.getNowDateYYMMDD()));
		if (!dirFile.exists()) {
			if (!dirFile.mkdir()) {
				super.errorMessage = "创建文件失败，请联系客服解决此问题!";
				return null;
			}
		}
		try {
			FileTools.uploadFile(file, newFileName);
			String abstrastFileName = new File(newFileName).getPath();
			System.out.println(abstrastFileName);
		} catch (Exception e) {
			e.printStackTrace();
			super.errorMessage = "上传出现异常,请联系客服解决此问题!";
			return null;
		}
		return "/dsUpFile/" + DateTools.getNowDateYYMMDD() + "/" + times + ".txt";
	}
	
	public String saveContextFile(String strContext) {
		String times = StringTools.randomString(18);

		// 根据当前时间创建文件名
		String newFileName = ServletActionContext.getServletContext().getRealPath(File.separator + "dsUpFile") + File.separator
				+ DateTools.getNowDateYYMMDD() + File.separator + times + ".txt";

		// 创建文件
		File dirFile = new File(ServletActionContext.getServletContext().getRealPath(
				File.separator + "dsUpFile" + File.separator + DateTools.getNowDateYYMMDD()));
		if (!dirFile.exists()) {
			if (!dirFile.mkdir()) {
				super.errorMessage = "创建文件失败，请联系客服解决此问题!";
				return null;
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(strContext.getBytes());
			InputStream is=stream;
			FileTools.uploadFile(is, newFileName);
			String abstrastFileName = new File(newFileName).getPath();
			System.out.println(abstrastFileName);
		} catch (Exception e) {
			e.printStackTrace();
			super.errorMessage = "上传出现异常,请联系客服解决此问题!";
			return null;
		}
		return "/dsUpFile/" + DateTools.getNowDateYYMMDD() + "/" + times + ".txt";
	}
	
	public String checkContext(String context){
		StringBuffer bf=new StringBuffer();
		String[] temp=context.split("\\\n");
		if(temp.length>0){
			for(int i=0;i<temp.length;i++){
				if(i==(temp.length-1)){
					bf.append(temp[i].trim());
				}else{
					bf.append(temp[i].trim()).append("\r\n");
				}
			}
		}else{
			bf.append(context);
		}
		return bf.toString();
	}

	public static void main(String[] args) {
       String context="34:[3]/35:[1]/37:[0]/38:[0]/40:[1]/41:[3]/42:[3]|34:[3]/35:[1]/37:[0]/38:[0]/40:[1]/41:[3]/42:[3]|34:[3]/35:[1]/37:[0]/38:[0]/40:[1]/41:[3]/42:[3]";
       String context2="34:[3]/35:[1]/37:[0]/38:[0]/40:[1]/41:[3]/42:[3]";
       context2="123456\n78955\n45621";
       StringBuffer bf=new StringBuffer();
      // String a=context.split("\\|")[0];
       //System.out.println(a);
		String[] temp=context2.split("\\\n");
		if(temp.length>0){
			for(int i=0;i<temp.length;i++){
				if(i==(temp.length-1)){
					bf.append(temp[i].trim());
				}else{
					bf.append(temp[i].trim()).append("\r\n");
				}
				
			}
		}else{
			bf.append(context2);
		}
		String ff=bf.toString();
		System.out.println(ff);
	}

}
