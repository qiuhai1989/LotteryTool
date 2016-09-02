package com.yuncai.core.tools;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.yuncai.core.tools.ticket.FuHao;
import com.yuncai.core.util.HttpUtil;

public class FileTools {
	public static String getPath(){
		URL fileUrl=FileTools.class.getResource("/");
		File file=new File(fileUrl.getFile());
		return file.getPath();
	}
	
	public static String getRealPath(String abPath){
		return getPath()+abPath;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getPath());
	}
	public static void uploadFile(File file, String toFilePath) throws FileNotFoundException, IOException {
		InputStream is = null;
		BufferedOutputStream os = null;
		try {
			is = new FileInputStream(file);
			os = new BufferedOutputStream(new FileOutputStream(toFilePath));
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (os != null)
				os.close();
			if (is != null)
				is.close();
		}
	}

	public static void uploadFile(InputStream fis, String toFilePath) throws FileNotFoundException, IOException {
		InputStream is = null;
		BufferedOutputStream os = null;
		try {
			is = fis;
			os = new BufferedOutputStream(new FileOutputStream(toFilePath));
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (os != null)
				os.close();
			if (is != null)
				is.close();
		}
	}

	public static String getFileContent(String filePath) throws Exception {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			StringBuffer sb = new StringBuffer();
			String temp = null;
			while ((temp = in.readLine()) != null) {
				if (temp.trim().length() > 0) {
					sb.append(temp).append("\n");
				}
			}
			return sb.toString();
		} finally {
			if (in != null)
				in.close();
		}

	}

	/**
	 * 双色球文件转换成单式格式字符串
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */

	public static String getFileSsqContent(String filePath) throws Exception {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			StringBuffer sb = new StringBuffer();
			String preStr;
			String endStr;
			String temp = null;
			while ((temp = in.readLine()) != null) {
				if (temp.trim().length() > 0) {
					temp = temp.trim().replaceAll(" ", ",");
					preStr = temp.substring(0, temp.lastIndexOf(","));
					endStr = temp.substring(temp.lastIndexOf(",") + 1);
					sb.append(preStr).append("|").append(endStr).append(FuHao.ZS);
				}
			}
			return sb.toString();
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (in != null)
				in.close();
		}

	}
	
	public static String getFileDltContent(String filePath) throws Exception{
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			StringBuffer sb = new StringBuffer();
			String preStr;
			String endStr;
			String temp = null;
			while ((temp = in.readLine()) != null) {
				if (temp.trim().length() > 0) {
					temp = temp.trim().replaceAll(" ", "");
					//preStr = temp.substring(0, temp.lastIndexOf(","));
					//endStr = temp.substring(temp.lastIndexOf(",") + 1);
					//sb.append(preStr).append(",").append(endStr).append(FuHao.ZS);
					sb.append(temp).append(FuHao.CZS);
				}
			}
			return sb.toString();
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (in != null)
				in.close();
		}
	}
	
	public static String getFileUpdateDltContent(String filePath) throws Exception{
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			StringBuffer sb = new StringBuffer();
			String preStr;
			String endStr;
			String temp = null;
			while ((temp = in.readLine()) != null) {
				if (temp.trim().length() > 0) {
					temp = temp.trim();
					sb.append(temp).append(FuHao.CZS);
				}
			}
			return sb.toString();
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (in != null)
				in.close();
		}
	}
	
	public static String getFileSDContent(String filePath) throws Exception{
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			StringBuffer sb = new StringBuffer();
			String temp = null;
			while ((temp = in.readLine()) != null) {
				if (temp.trim().length() > 0) {
					temp = temp.trim();
					String[] codes = temp.split("\\B");
					sb.append(codes[0]+"|"+codes[1]+"|"+codes[2]).append(FuHao.ZS);
				}
			}
			return sb.toString();
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (in != null)
				in.close();
		}
	}
	
	public static String getFileContentForUploadPlan(String filePath, String filterString) throws Exception {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			StringBuffer sb = new StringBuffer();
			String temp = null;
			while ((temp = in.readLine()) != null) {
				if (temp.trim().length() > 0) {
					temp = temp.trim();
					String[] filterCode = filterString.split("#");
					for (int i = 0; i < filterCode.length; i++) {
						temp = temp.replaceAll(filterCode[i], "");
					}
					sb.append(temp.trim()).append("\n");
				}
			}
			return sb.toString();
		} finally {
			if (in != null)
				in.close();
		}

	}

	public static String getFileContent(File file) throws Exception {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String temp = null;
			while ((temp = in.readLine()) != null) {
				sb.append(temp).append("\n");
			}
			return sb.toString();
		} finally {
			if (in != null)
				in.close();
		}
	}

	public static void setFileContent(String content, String filePath) throws Exception {
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath);
			fw.write(content);
		} finally {
			if (fw != null) {
				fw.close();
			}
		}

	}

	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		return file.delete();
	}

	public static boolean mkdir(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return file.mkdir();
		}
		return true;
	}

	public static boolean mkdirs(String filePath) {
		File file = new File(filePath);
		return file.mkdirs();
	}

	public static void deleteDirectory(File dir) throws IOException {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir + " is not a directory. ");
		}
		File[] entries = dir.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].isDirectory()) {
				deleteDirectory(entries[i]);
			} else {
				entries[i].delete();
			}
		}
		dir.delete();
	}

	public static void buildHtml(String target_url, String save_path) throws FileNotFoundException, IOException {
		try {
			URL openurl = new URL(target_url);
			URLConnection urlConn = openurl.openConnection();
			urlConn.setConnectTimeout(30000);
			urlConn.setReadTimeout(60000);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(false);
			urlConn.setUseCaches(false);
			urlConn.connect();
			InputStream is = urlConn.getInputStream();
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(save_path));
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			is.close();
			os = null;
			is = null;

		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	public static String getFileExt(String name) {
		if (name.lastIndexOf(".") == -1) {
			return "";
		}
		return name.substring(name.lastIndexOf(".") + 1, name.length());
	}

	public static String getFileName(String name) {
		if (name.lastIndexOf(".") == -1) {
			return "";
		}
		return name.substring(0, name.lastIndexOf("."));

	}

	/**
	 * 读取文件得到注数
	 * 
	 */
	public static int getFileCount(String filePath) throws Exception {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			int count = 0;
			while (in.readLine() != null) {
				count++;
			}
			return count;
		} catch (FileNotFoundException fnfe) {
			throw fnfe;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			if (in != null)
				in.close();
		}

	}

	public static String getRandomFileName() {
		java.util.Date dt = new java.util.Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = fmt.format(dt);
		fileName = fileName + ".txt"; // extension, you can change it.
		return fileName;
	}

	public static Document getDocument(String filePath) {
		String fileContent;
		Reader in = null;
		try {
			fileContent = getFileContent(filePath);
			SAXBuilder builder = new SAXBuilder();
			in = new StringReader(fileContent);
			Document doc = builder.build(in);
			return doc;
		} catch (FileNotFoundException fnfe) {
			return null;
		} catch (IOException ioe) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//创建document再返回
	public static Document getXMLDocument() {
		Reader reader = null;
		try {
			SAXBuilder sb = new SAXBuilder();
			reader = new StringReader("<?xml version=\"1.0\" encoding=\"GBK\" ?><xml></xml>");
			return sb.build(reader);
		} catch (Exception e) {
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//根据document和filePath保存文件
	public static void saveXMLFile(Document doc,String filePath) {
		FileWriter fw = null;
		try {
			XMLOutputter xo = new XMLOutputter();
			Format format = xo.getFormat();
			format.setEncoding("GBK");
			format.setLineSeparator("");
			xo.setFormat(format);
			
			fw = new FileWriter(filePath);
			xo.output(doc, fw);
		} catch (Exception e) {
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
	
	/**
	 * 下载文件
	 * @param url
	 * @param fileName
	 * @param saveSrc
	 */
	public static boolean download(String url,String fileName,String saveSrc){
		boolean isTrue=false;
		mkdirs(saveSrc);
		File file=new File(saveSrc+"\\"+fileName);
		FileOutputStream fos=null;
		 byte[] bytes = HttpUtil.getHtmlByGetMethod(url);
		try {
			fos=new FileOutputStream(file);
			if(bytes.length>0){
				fos.write(bytes);
				isTrue=true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {fos.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
		return isTrue;
	}
}

