package com.yuncai.modules.lottery.action.admin.apk;


import java.util.List;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.yuncai.core.util.DBHelper;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.action.admin.search.Search;
import com.yuncai.modules.lottery.model.Oracle.toolType.JumpType;
import com.yuncai.modules.lottery.model.Sql.ApkNotice;
import com.yuncai.modules.lottery.model.Sql.Channel;
import com.yuncai.modules.lottery.service.sqlService.apk.ApkNoticeService;
import com.yuncai.modules.lottery.service.sqlService.user.ChannelService;

/***
 * 客户端公告管理
 * @author TYH
 *
 */
@Controller("adminApkNoticeAction")
@Scope("prototype")
public class ApkNoticeAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private ApkNoticeService apkNoticeService;
	@Resource
	private ChannelService sqlchannelService;
	private List<ApkNotice> noticeList;
	private int noticeId = 0;//公告ID
	private ApkNotice apkNotice;
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
		if(!DBHelper.isNoNull(search)){
			search = new Search();
//			search.setStartDate(DBHelper.getDate(-15)+" 00:00:00");
		}
		super.pageSize = 20;
		super.total = apkNoticeService.getCount(search);
		noticeList = apkNoticeService.find((page-1)*pageSize, pageSize,search);
		
		super.isNeedBuildPage = true;
		return "list";
	}
	
	//view
	public String view(){

		apkNotice = apkNoticeService.find(noticeId);
		return "view";
	}
	//edit
	public String edit(){
		channelList = sqlchannelService.findByPlatformId(-1);
		if (commandType.equals("add")) {
		}
		if (commandType.equals("edit")) {
			apkNotice = apkNoticeService.find(noticeId);
			use_receiveUser = apkNotice.getReceiveUser();
			if(apkNotice.getSendGroup()==1&&DBHelper.isNoNull(apkNotice.getReceiveUser())){//取出具体渠道
				channelListByID = sqlchannelService.findByIds("('"+apkNotice.getReceiveUser().replaceAll(" ", "").replaceAll(",", "','")+"')");
			}
		}
		return "edit";
	}
	
	//save
	public String save(){
		if(DBHelper.isNoNull(apkNotice)){
			apkNotice.setUpdcode(DBHelper.updCode());
			if(apkNotice.getPushContent()==1)//1表示显示内容 公告类型属于文本2
				apkNotice.setType(2);
			else if(apkNotice.getPushContent()==7)//7调用浏览器链接 公告类型属于文本1站外
				apkNotice.setType(1);
			else
				apkNotice.setType(0);
			if(apkNotice.getSendGroup()==2)
				apkNotice.setReceiveUser(use_receiveUser);
			//判断编号是否存在
			if(DBHelper.isNoNull(apkNotice.getNoticeNo())){
				ApkNotice c1 = apkNoticeService.findObjectByProperty("noticeNo", apkNotice.getNoticeNo());
				if(DBHelper.isNoNull(c1)){
					if(apkNotice.getId() > 0){
						ApkNotice an = apkNoticeService.find(apkNotice.getId());
						if(an.getNoticeNo()!=apkNotice.getNoticeNo())
							apkNotice.setNoticeNo(apkNoticeService.getMaxCode()+1);
					}else{
						apkNotice.setNoticeNo(apkNoticeService.getMaxCode()+1);
					}
					
				}
			}
			
			if(apkNotice.getId() > 0){
				apkNoticeService.update(apkNotice);
			}
			else {
				apkNoticeService.save(apkNotice);
			}
			return "success";
		}
		
		return "error";
	}
	//del
	public String del(){
		
		if(noticeId > 0){
			apkNotice = new ApkNotice();
			apkNotice.setId(noticeId);
			try {
				apkNoticeService.delete(apkNotice);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "error";
	}
	
	
	/*****************************************************/
	public List<ApkNotice> getNoticeList() {
		return noticeList;
	}
	public void setNoticeList(List<ApkNotice> noticeList) {
		this.noticeList = noticeList;
	}
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public ApkNotice getApkNotice() {
		return apkNotice;
	}
	public void setApkNotice(ApkNotice apkNotice) {
		this.apkNotice = apkNotice;
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

	public ChannelService getSqlchannelService() {
		return sqlchannelService;
	}

	public void setSqlchannelService(ChannelService sqlchannelService) {
		this.sqlchannelService = sqlchannelService;
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
