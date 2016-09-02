package com.yuncai.modules.lottery.action.admin.apk;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.model.Sql.ApkPackage;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPackageService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
@Controller("adminApkPackageAction")
@Scope("prototype")
public class ApkPackageAction extends BaseAction{

	@Resource
	private ApkPackageService apkPackageService;
	
	private List<ApkPackage> packageList;
	private int packageId = 0;//公告ID
	private ApkPackage apkPackage;
	private Search search;
	
	//index
	public String index(){
		
		super.pageSize = 15;
		super.total = apkPackageService.getCount(search);
		packageList = apkPackageService.find((page-1)*pageSize, pageSize,search);
		
		super.isNeedBuildPage = true;
		return "list";
	}
	
	//view
	public String view(){

		apkPackage = apkPackageService.find(packageId);
		return "view";
	}
	//edit
	public String edit(){
		
		apkPackage = apkPackageService.find(packageId);
		return "edit";
	}
	
	//save
	public String save(){
		
		if(DBHelper.isNoNull(apkPackage)){
			if(apkPackage.getId() > 0){
				apkPackageService.update(apkPackage);
			}
			else {
				apkPackageService.save(apkPackage);
			}
			return "success";
		}
		
		return "error";
	}
	//del
	public String del(){
		
		if(packageId > 0){
			apkPackage = new ApkPackage();
			apkPackage.setId(packageId);
			try {
				apkPackageService.delete(apkPackage);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "error";
	}
	
	
	/*****************************************************/
	public ApkPackage getApkPackage() {
		return apkPackage;
	}
	public void setApkPackage(ApkPackage apkPackage) {
		this.apkPackage = apkPackage;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}

	public List<ApkPackage> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<ApkPackage> packageList) {
		this.packageList = packageList;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
}
