package com.yuncai.modules.servlet;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;


import com.oreilly.servlet.MultipartRequest;
import com.yuncai.core.tools.DBConn;
import com.yuncai.core.tools.HttpOut;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.util.DBHelper;
import com.yuncai.core.util.DBUpload;

/**
 * @author gx
 * @version 2014-4-3
 */
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -3096800116651263134L;

	private String fileSizeLimit;

	public void init(ServletConfig config) throws ServletException {
		this.fileSizeLimit = config.getInitParameter("fileSizeLimit");
	}

	public void destroy() {
		super.destroy();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uploadDir = "upload" + File.separatorChar + "apk" + File.separatorChar;
		String ctxDir = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
			ctxDir = ctxDir + File.separatorChar;
		}
		File savePath = new File(ctxDir + uploadDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		String saveDirectory = ctxDir + uploadDir;
		int maxPostSize = 80 * 1024 * 1024;
		String encoding = "UTF-8";
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Enumeration<?> files = multi.getFileNames();
		while (files.hasMoreElements()) {
			String name = (String) files.nextElement();
			File f = multi.getFile(name);
			if (f != null) {
				String  apkPath = "/upload/apk/"+f.getName();
				String channelName = f.getName().substring(f.getName().lastIndexOf("_")+1, f.getName().lastIndexOf("."));
				Connection conn = DBConn.getConn("sqlServer");
				String sql ="";
				PreparedStatement ps=null;
				sql = "select ID from T_Channel where NAME=?";
				ResultSet rs =null;
				int packageId=0;
				String curVersion="v1.0.0";
				String updVersion = request.getParameter("updVersion");
				if(!DBHelper.isNoNull(updVersion))
					updVersion = "v1.0.0";
				if(updVersion.split("\\.").length!=3)
					updVersion = "v1.0.0";
				try {
					ps=conn.prepareStatement(sql);
					ps.setString(1, channelName);
					rs = ps.executeQuery();
					while(rs.next())
						packageId =rs.getInt(1);
					if(packageId==0){
						LogUtil.out(channelName+"渠道未定义");
						HttpOut.httpOut("渠道未定义");
					} 
					else LogUtil.out(channelName+"渠道上传完成");
					sql  = "select max(updVersion) from T_ApkManage where packageId =?";
					ps=conn.prepareStatement(sql);
					ps.setInt(1, packageId);
					rs = ps.executeQuery();
					while(rs.next())
						curVersion =rs.getString(1);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}finally{
					try {
						if(DBHelper.isNoNull(ps))
							ps.close();
						if(DBHelper.isNoNull(conn))
							conn.close();
					} catch (SQLException e) {
					}
				}
				
				String fullPath = ctxDir + apkPath;
				String packageName = "";
				int versionCode = 0;
				int versionNameCode = 0;
				String versionName = "";
				String size = DBUpload.getFileSize(apkPath, ctxDir);
				if (new File(fullPath).exists()) {
					try {
						ApkInfo apkInfo = GetApkInfo.getApkInfoByFilePath(fullPath);
						if (DBHelper.isNoNull(apkInfo)) {
							packageName=apkInfo.getPackageName();// 包名
							versionCode= Integer.valueOf(apkInfo.getVersionCode());// 版本更新号
							versionName = apkInfo.getVersionName();
							int i = versionName.lastIndexOf("_");
							if (i >= 0) {
								versionNameCode=Integer.valueOf(versionName.substring(i + 1, versionName.length()));
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				try {
					conn = DBConn.getConn("sqlServer");
					sql ="insert into T_ApkManage(packageName,apkName" +
							",apkSize,apkPath,netPath,downType,curVersion," +
							"updVersion,info,isValid,isForce,addTime,updTime," +
							"versionCode,versionName,versionNameCode,PackageId) " +
							"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					ps=conn.prepareStatement(sql);
					ps.setString(1, packageName);
					ps.setString(2, f.getName());
					ps.setString(3, size);
					ps.setString(4, apkPath);
					ps.setString(5, "");
					ps.setString(6, request.getParameter("downType"));
					ps.setString(7, curVersion);
					ps.setString(8, updVersion);
					ps.setString(9, request.getParameter("info"));
					ps.setString(10, request.getParameter("isValid"));
					ps.setString(11, request.getParameter("isForce"));
					ps.setString(12, DBHelper.getNow());
					ps.setString(13, DBHelper.getNow());
					ps.setInt(14, versionCode);
					ps.setString(15, versionName);
					ps.setInt(16, versionNameCode);
					ps.setInt(17, packageId);
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
						try {
							if(DBHelper.isNoNull(ps))
								ps.close();
							if(DBHelper.isNoNull(conn))
								conn.close();
						} catch (SQLException e) {
						}
				}
//				HttpOut.httpOut(apkPath);
			}
		}
		
	}
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}

	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}

}
