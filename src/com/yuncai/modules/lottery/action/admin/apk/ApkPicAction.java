package com.yuncai.modules.lottery.action.admin.apk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.action.admin.search.ApkPicSearch;
import com.yuncai.modules.lottery.model.Oracle.toolType.JumpType;
import com.yuncai.modules.lottery.model.Sql.ApkPic;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPicService;

/*******************************************************************************
 * 广告图管理
 * 
 * @author gx
 * 
 */
@Controller("adminApkPicAction")
@Scope("prototype")
public class ApkPicAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ApkPicService apkPicService;
	private ApkPicSearch search;// 查找
	private int picId;
	private List<Map<String, Object>> picList;
	private ApkPic apkPic;
	private String commandType;
	private List<JumpType> jumpTypeList = JumpType.list;
	private File image; // 上传的文件
	private File image_big;
	private File image_small;
	private String imageFileName; // 文件名称
	private String image_bigFileName;
	private String image_smallFileName;
	private String imageContentType; // 文件类型
	private String str;
	public String index() {
		super.pageSize = 20;
		picList = apkPicService.find(search, (page - 1) * pageSize, pageSize);
		super.total = apkPicService.getCount(search);
		super.isNeedBuildPage = true;
		return "list";
	}

	// 保存---新增
	public String edit() {
		if (commandType.equals("add")) {
			return "edit";
		}
		if (commandType.equals("edit")) {
			apkPic = apkPicService.find(picId);
			return "view";
		}
		return SUCCESS;
	}

	// 新增
	public String save() {
		try {
			String imageAdd = "noPicture";
			String imageAddB = "noPicture";
			String imageAddS = "noPicture";
			String realpath = ServletActionContext.getServletContext().getRealPath("/upload");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			if (image != null) {
				if(image.length()>200*1024){
					str = "图片过大";
					return "edit";
				}	
				imageAdd = sdf.format(new Date()) + getImageFileName();
				FileOutputStream fos = new FileOutputStream(realpath + File.separator + imageAdd);
				// 以上传文件建立一个文件上传流
				FileInputStream fis = new FileInputStream(getImage());
				// 将上传文件的内容写入服务器
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			}
			if (image_small!=null) {
				if(image_small.length()>200*1024){
					str = "图片过大";
					return "edit";
				}	
				imageAddS = sdf.format(new Date()) + getImage_smallFileName();
				FileOutputStream fos = new FileOutputStream(realpath + File.separator + imageAddS);
				FileInputStream fis = new FileInputStream(getImage_small());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			}
			if (image_big!=null) {
				if(image_big.length()>200*1024){
					str = "图片过大";
					return "edit";
				}	
				imageAddB = sdf.format(new Date()) + getImage_bigFileName();
				FileOutputStream fos = new FileOutputStream(realpath + File.separator + imageAddB);
				// 以上传文件建立一个文件上传流
				FileInputStream fis = new FileInputStream(getImage_big());
				// 将上传文件的内容写入服务器
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			}
			apkPic.setImageAdd("/upload/"+imageAdd);
			apkPic.setImageAddB("/upload/"+imageAddB);
			apkPic.setImageAddS("/upload/"+imageAddS);
			apkPic.setUpdtime(new Date());
			apkPic.setUpdcode(DBHelper.updCode());
			if(DBHelper.isNoNull(apkPic.getCode())){
				ApkPic c1 = apkPicService.findObjectByProperty("code", apkPic.getCode());
				if(DBHelper.isNoNull(c1))
					apkPic.setCode(apkPicService.getMaxCode()+1);
			}
			apkPicService.save(apkPic);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return SUCCESS;
	}
	// 修改
	public String update() {
		try {
			String realpath = ServletActionContext.getServletContext().getRealPath("/upload");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			byte[] buffer = new byte[1024];
			int len = 0;
			if (image != null) {
				if(image.length()>200*1024){
					str = "图片过大";
					apkPic = apkPicService.find(apkPic.getId());
					return "view";
				}
				String imageAdd = sdf.format(new Date()) + getImageFileName();
				FileOutputStream fos = new FileOutputStream(realpath + File.separator + imageAdd);
				// 以上传文件建立一个文件上传流
				FileInputStream fis = new FileInputStream(getImage());
				// 将上传文件的内容写入服务器
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				apkPic.setImageAdd("/upload/"+imageAdd);
			}
			
			if (image_big != null) {
				if(image_big.length()>200*1024){
					str = "图片过大";
					apkPic = apkPicService.find(apkPic.getId());
					return "view";
				}
				String imageAddB = sdf.format(new Date()) + getImage_bigFileName();
				FileOutputStream fos = new FileOutputStream(realpath + File.separator + imageAddB);
				// 以上传文件建立一个文件上传流
				FileInputStream fis = new FileInputStream(getImage_big());
				// 将上传文件的内容写入服务器
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				apkPic.setImageAddB("/upload/"+imageAddB);
			}
			
			if (image_small != null) {
				if(image_small.length()>200*1024){
					str = "图片过大";
					apkPic = apkPicService.find(apkPic.getId());
					return "view";
				}
				String imageAddS = sdf.format(new Date()) + getImage_smallFileName();
				FileOutputStream fos = new FileOutputStream(realpath + File.separator + imageAddS);
				// 以上传文件建立一个文件上传流
				FileInputStream fis = new FileInputStream(getImage_small());
				// 将上传文件的内容写入服务器
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				apkPic.setImageAddS("/upload/"+imageAddS);
			}
			
			int size = apkPicService.findByCode(apkPic.getCode());
			if(size>0){
				int code = apkPicService.findById(apkPic.getId());
				if(code!=apkPic.getCode())
					apkPic.setCode(apkPicService.getMaxCode() + 1);
			}
			apkPic.setUpdtime(new Date());
			apkPic.setUpdcode(DBHelper.updCode());
			apkPicService.update(apkPic);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return SUCCESS;
	}

	// 删除
	public String del() {
		apkPic = apkPicService.find(picId);
		apkPicService.delete(apkPic);
		return SUCCESS;
	}
	
	/** ********************************* */

	public int getPicId() {
		return picId;
	}

	public void setPicId(int picId) {
		this.picId = picId;
	}

	public ApkPic getApkPic() {
		return apkPic;
	}
	public void setApkPic(ApkPic apkPic) {
		this.apkPic = apkPic;
	}

	public List<Map<String, Object>> getPicList() {
		return picList;
	}

	public void setPicList(List<Map<String, Object>> picList) {
		this.picList = picList;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public List<JumpType> getJumpTypeList() {
		return jumpTypeList;
	}

	public void setJumpTypeList(List<JumpType> jumpTypeList) {
		this.jumpTypeList = jumpTypeList;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public ApkPicSearch getSearch() {
		return search;
	}

	public void setSearch(ApkPicSearch search) {
		this.search = search;
	}

	public File getImage_big() {
		return image_big;
	}

	public void setImage_big(File image_big) {
		this.image_big = image_big;
	}

	public File getImage_small() {
		return image_small;
	}

	public void setImage_small(File image_small) {
		this.image_small = image_small;
	}

	public String getImage_bigFileName() {
		return image_bigFileName;
	}

	public void setImage_bigFileName(String image_bigFileName) {
		this.image_bigFileName = image_bigFileName;
	}

	public String getImage_smallFileName() {
		return image_smallFileName;
	}

	public void setImage_smallFileName(String image_smallFileName) {
		this.image_smallFileName = image_smallFileName;
	}

}
