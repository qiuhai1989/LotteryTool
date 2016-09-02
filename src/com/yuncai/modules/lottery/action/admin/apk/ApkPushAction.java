package com.yuncai.modules.lottery.action.admin.apk;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.model.Oracle.toolType.JumpType;
import com.yuncai.modules.lottery.model.Sql.ApkPush;
import com.yuncai.modules.lottery.model.Sql.Channel;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkPushService;
import com.yuncai.modules.lottery.service.sqlService.user.ChannelService;

/***
 * 客户端公告管理
 * @author gx
 *
 */
@SuppressWarnings("serial")
@Controller("adminApkPushAction")
@Scope("prototype")
public class ApkPushAction extends BaseAction{

	@Resource
	private ApkPushService apkPushService;
	@Resource
	private ChannelService sqlchannelService;
	private List<ApkPush> pushList;
	private int pushId = 0;//公告ID
	private ApkPush apkPush;
	private Search search;
	private String commandType;
	private List<JumpType> jumpTypeList = JumpType.list;
	private List<JumpType> jumpTypeList1 = JumpType.list1;
	private List<JumpType> sendGroupList = JumpType.sendGroupList;
	private List<Channel> channelList;
	private List<Channel> channelListByID;
	private String use_receiveUser;
	//index
	public String index(){
		if(!DBHelper.isNoNull(search)) {
			search = new Search();
			search.setStartDate(DBHelper.getDate(-15)+" 00:00:00");
		}
		super.pageSize = 20;
		super.total = apkPushService.getCount(search);
		pushList = apkPushService.find((page-1)*pageSize, pageSize,search);
		super.isNeedBuildPage = true;
		return "list";
	}
	
	//view
	public String view(){

		apkPush = apkPushService.find(pushId);
		return "view";
	}
	//edit
	public String edit(){
		channelList = sqlchannelService.findByPlatformId(-1);
		if (commandType.equals("add")) {
		}
		if (commandType.equals("edit")) {
			apkPush = apkPushService.find(pushId);
			use_receiveUser = apkPush.getReceiveUser();
			if(apkPush.getSendGroup()==1&&DBHelper.isNoNull(apkPush.getReceiveUser())){//取出具体渠道
				channelListByID = sqlchannelService.findByIds("('"+apkPush.getReceiveUser().replaceAll(" ", "").replaceAll(",", "','")+"')");
			}
		}
		return "edit";
	}
	
	//save
	public String save(){
		
		if(DBHelper.isNoNull(apkPush)){
			
			String updcode = DBHelper.updCode();
			apkPush.setUpdtime(new Date());
			apkPush.setUpdcode(updcode);
			if(apkPush.getPushContent()==1)//1表示显示内容 公告类型属于文本2
				apkPush.setType(2);
			else if(apkPush.getPushContent()==7)//7调用浏览器链接 公告类型属于文本1站外
				apkPush.setType(1);
			else
				apkPush.setType(0);
			if(apkPush.getSendGroup()==2)
				apkPush.setReceiveUser(use_receiveUser);
			
			if(apkPush.getId() > 0){
				apkPushService.update(apkPush);
			}
			else {
				apkPush.setAddtime(new Date());
				apkPushService.save(apkPush);
			}
			return "success";
		}
		
		return "error";
	}
	//del
	public String del(){
		
		if(pushId > 0){
			apkPush = new ApkPush();
			apkPush.setId(pushId);
			try {
				apkPushService.delete(apkPush);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "error";
	}
	
	
	/*****************************************************/
	public ApkPush getApkPush() {
		return apkPush;
	}
	public List<ApkPush> getPushList() {
		return pushList;
	}

	public void setPushList(List<ApkPush> pushList) {
		this.pushList = pushList;
	}

	public int getPushId() {
		return pushId;
	}

	public void setPushId(int pushId) {
		this.pushId = pushId;
	}

	public void setApkPush(ApkPush apkPush) {
		this.apkPush = apkPush;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public ApkPushService getApkPushService() {
		return apkPushService;
	}

	public void setApkPushService(ApkPushService apkPushService) {
		this.apkPushService = apkPushService;
	}

	public ChannelService getSqlchannelService() {
		return sqlchannelService;
	}

	public void setSqlchannelService(ChannelService sqlchannelService) {
		this.sqlchannelService = sqlchannelService;
	}

	public List<JumpType> getJumpTypeList() {
		return jumpTypeList;
	}

	public void setJumpTypeList(List<JumpType> jumpTypeList) {
		this.jumpTypeList = jumpTypeList;
	}

	public List<JumpType> getJumpTypeList1() {
		return jumpTypeList1;
	}

	public void setJumpTypeList1(List<JumpType> jumpTypeList1) {
		this.jumpTypeList1 = jumpTypeList1;
	}

	public List<JumpType> getSendGroupList() {
		return sendGroupList;
	}

	public void setSendGroupList(List<JumpType> sendGroupList) {
		this.sendGroupList = sendGroupList;
	}

	public List<Channel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<Channel> channelList) {
		this.channelList = channelList;
	}

	public List<Channel> getChannelListByID() {
		return channelListByID;
	}

	public void setChannelListByID(List<Channel> channelListByID) {
		this.channelListByID = channelListByID;
	}

	public String getUse_receiveUser() {
		return use_receiveUser;
	}

	public void setUse_receiveUser(String use_receiveUser) {
		this.use_receiveUser = use_receiveUser;
	}
	
}
