package com.yuncai.modules.lottery.action.admin.apk;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.yuncai.core.util.DBHelper;
import com.yuncai.core.util.DBUpload;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.model.Oracle.ApkManageSupplement;
import com.yuncai.modules.lottery.model.Sql.ApkManage;
import com.yuncai.modules.lottery.model.Sql.ApkPackage;
import com.yuncai.modules.lottery.model.Sql.Channel;
import com.yuncai.modules.lottery.model.Sql.Platform;
import com.yuncai.modules.lottery.service.oracleService.apk.ApkManageSupplementService;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkManageService;
import com.yuncai.modules.lottery.service.sqlService.user.ChannelService;
import com.yuncai.modules.lottery.service.sqlService.user.PlatformService;

/*******************************************************************************
 * 客户端APK包管理
 * 
 * @author gx
 * 
 */
@SuppressWarnings("serial")
@Controller("adminApkManageAction")
@Scope("prototype")
public class ApkManageAction extends BaseAction {

	@Autowired
	private ApkManageService apkManageService;
	@Autowired
	private PlatformService sqlPlatformService;
	@Autowired
	private ChannelService sqlchannelService;
	@Autowired
	private ApkManageSupplementService apkManageSupplementService;
	private Search search;// 查找
	private int manageId;
	private List<Map<String, Object>> manageList;
	private List<ApkManageSupplement> manageSupplementList;
	private List<ApkPackage> packageList;
	private ApkManage apkManage;
	private ApkManageSupplement apkManageSupplement;
	private Platform platform;
	private Channel channel;
	private String curVersion = "0.0";

	private List<Platform> platformList;
	private List<Channel> channelList;
	private int platformId = -1;
	private int channelId = 0;
	private List<Map<String, String>> updVersionList;
	private String msg;// 标记添加成功。

	// 上传APK包
	private File apkFile;
	private String fileName;
	
	public String index() {
		if (!DBHelper.isNoNull(search)) {
			search = new Search();
			search.setChannelId(channelId);
			try {
				platformId = sqlPlatformService.findObjectByProperty("agentId", "ycAndroid").getId();// 默认安卓平台
			} catch (Exception e) {
				e.printStackTrace();
			}
			search.setPlatformId(platformId);
		}
		platformId = search.getPlatformId();
		super.pageSize = 20;
		updVersionList = apkManageService.findUpdVersion(search.getPlatformId(), search.getChannelId());
		// 默认查询最大版本号,版本号格式必须为v1.1.1这样的 v1 这种格式都不行
		if (updVersionList.size() > 0) {
			String max_updVersion = updVersionList.get(0).get("updVersion");
			String min_updVersion = updVersionList.get(updVersionList.size() - 1).get("updVersion");
			if (!DBHelper.isNoNull(search.getUpdVersion())){
				search.setUpdVersion(max_updVersion);
			}else {
				if (search.getUpdVersion().hashCode() > max_updVersion.hashCode() || search.getUpdVersion().hashCode() < min_updVersion.hashCode())
					search.setUpdVersion(max_updVersion);
			}
		}

		platformList = sqlPlatformService.findAllPlatform();
		manageList = apkManageService.find((page - 1) * pageSize, pageSize, search.getChannelId(), search);
		super.total = apkManageService.getCount(search, search.getChannelId());
		channelList = sqlchannelService.findByPlatformId(search.getPlatformId());
		super.isNeedBuildPage = true;
		return "list";
	}

	// view
	public String view() {
		apkManage = apkManageService.find(manageId);

		if (DBHelper.isNoNull(apkManage)) {
			channel = sqlchannelService.find(apkManage.getPackageId());
			if (DBHelper.isNoNull(channel)) {
				platform = sqlPlatformService.find(channel.getPlatformId());
			}
			manageSupplementList = apkManageSupplementService.findAll();
			if (manageSupplementList.size() > 0)
				apkManageSupplement = manageSupplementList.get(0);
		}
		return "view";
	}

	// edit
	public String edit() {
		apkManage = apkManageService.find(manageId);
		channelList = sqlchannelService.findByPlatformId(platformId);

		if (DBHelper.isNoNull(apkManage)) {
			curVersion = apkManage.getCurVersion();
			channelId = apkManage.getPackageId();// 渠道ID
			manageSupplementList = apkManageSupplementService.findAll();
			if (manageSupplementList.size() > 0)
				apkManageSupplement = manageSupplementList.get(0);

		} else {
			curVersion = apkManageService.getMaxVersion(channelId);
			manageSupplementList = apkManageSupplementService.findAll();
			if (manageSupplementList.size() > 0)
				apkManageSupplement = manageSupplementList.get(0);
			return "add";
		}

		return "edit";
	}

	// save
	public String save() {
		String disk = getServletContext().getRealPath("");
		String path = disk + "/upload/";
		String apkPath = DBUpload.upLoadFile(apkFile, fileName, path);
		String now = DBHelper.getNow();

		apkManage.setUpdTime(now);

		// 上传了新包
		if (DBHelper.isNoNull(apkPath)) {
			apkManage.setApkPath("/upload/" + apkPath);
			String size = DBUpload.getFileSize("/upload/" + apkPath, disk);
			apkManage.setApkSize(size);
			apkManage.setApkName(fileName);

			String fullPath = disk + "/upload/" + apkPath;
			if (new File(fullPath).exists()) {
				try {
					ApkInfo apkInfo = GetApkInfo.getApkInfoByFilePath(fullPath);
					if (DBHelper.isNoNull(apkInfo)) {
						apkManage.setPackageName(apkInfo.getPackageName());// 包名
						apkManage.setVersionCode(Integer.valueOf(apkInfo.getVersionCode()));// 版本更新号
						String verssionName = apkInfo.getVersionName();
						int i = verssionName.lastIndexOf("_");
						if (i >= 0) {
							apkManage.setVersionNameCode(Integer.valueOf(verssionName.substring(i + 1, verssionName.length())));
						}

						apkManage.setVersionName(verssionName);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			// 修改
			int aid = apkManage.getId();
			if (aid > 0) {
				apkManageService.update(apkManage);

			} else {// 新增
				apkManage.setAddTime(now);
				apkManageService.save(apkManage);
				channelList = sqlchannelService.findByPlatformId(platformId);
				channelId = apkManage.getPackageId();
				curVersion = apkManage.getCurVersion();
				msg = "添加成功";
			}
			manageSupplementList = apkManageSupplementService.findByProperty("channelid", apkManage.getPackageId());

			String bak = apkManageService.findLastBak(apkManage.getPackageId(), apkManageService.getMaxVersion(apkManage.getPackageId()));
			String channelName = sqlchannelService.find(apkManage.getPackageId()).getName();
			apkManageSupplement.setBak(bak);
			apkManageSupplement.setPackageid(channelName);
			if (manageSupplementList.size() > 0) {
				ApkManageSupplement ams = manageSupplementList.get(0);
				ams.setQrCode(apkManageSupplement.getQrCode());
				ams.setTestQrCode(apkManageSupplement.getTestQrCode());
				ams.setUrl(apkManageSupplement.getUrl());
				ams.setBak(bak);
				ams.setPackageid(channelName);
				apkManageSupplementService.update(ams);
			} else {
				apkManageSupplementService.save(apkManageSupplement);
			}
			List<ApkManageSupplement> amsList = apkManageSupplementService.findAll();
			for (ApkManageSupplement amsm : amsList) {
				amsm.setQrCode(apkManageSupplement.getQrCode());
				amsm.setTestQrCode(apkManageSupplement.getTestQrCode());
				amsm.setUrl(apkManageSupplement.getUrl());
				apkManageSupplementService.update(amsm);
			}
			if (aid <= 0)
				return "add";
		} catch (Exception e) {
			return ERROR;
		}

		return SUCCESS;
	}

	// del
	public String del() {
		if (manageId > 0) {
			ApkManage oper = new ApkManage();
			oper.setId(manageId);
			apkManageService.delete(oper);
			return "success";
		} else
			return "error";
	}

	
	/** ********************************* */
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public int getManageId() {
		return manageId;
	}
	public void setManageId(int manageId) {
		this.manageId = manageId;
	}
	public List<ApkPackage> getPackageList() {
		return packageList;
	}
	public void setPackageList(List<ApkPackage> packageList) {
		this.packageList = packageList;
	}
	public ApkManage getApkManage() {
		return apkManage;
	}
	public void setApkManage(ApkManage apkManage) {
		this.apkManage = apkManage;
	}
	public String getCurVersion() {
		return curVersion;
	}
	public void setCurVersion(String curVersion) {
		this.curVersion = curVersion;
	}
	public void setApkFile(File apkFile) {
		this.apkFile = apkFile;
	}
	public void setApkFileFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<Map<String, Object>> getManageList() {
		return manageList;
	}
	public void setManageList(List<Map<String, Object>> manageList) {
		this.manageList = manageList;
	}
	public List<Platform> getPlatformList() {
		return platformList;
	}

	public void setPlatformList(List<Platform> platformList) {
		this.platformList = platformList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public List<Map<String, String>> getUpdVersionList() {
		return updVersionList;
	}

	public void setUpdVersionList(List<Map<String, String>> updVersionList) {
		this.updVersionList = updVersionList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ApkManageSupplementService getApkManageSupplementService() {
		return apkManageSupplementService;
	}

	public void setApkManageSupplementService(ApkManageSupplementService apkManageSupplementService) {
		this.apkManageSupplementService = apkManageSupplementService;
	}

	public List<ApkManageSupplement> getManageSupplementList() {
		return manageSupplementList;
	}

	public void setManageSupplementList(List<ApkManageSupplement> manageSupplementList) {
		this.manageSupplementList = manageSupplementList;
	}

	public ApkManageSupplement getApkManageSupplement() {
		return apkManageSupplement;
	}

	public void setApkManageSupplement(ApkManageSupplement apkManageSupplement) {
		this.apkManageSupplement = apkManageSupplement;
	}



}
