package com.yuncai.modules.lottery.software.lottery;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.jdom.Element;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.bean.search.VipBonusLineSearch;
import com.yuncai.modules.lottery.bean.search.VipGiftDetailSearch;
import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.model.Oracle.VipBonusLine;
import com.yuncai.modules.lottery.model.Oracle.VipGiftDetail;
import com.yuncai.modules.lottery.service.oracleService.member.VipBonusLineService;
import com.yuncai.modules.lottery.service.oracleService.member.VipGiftDetailService;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class VipGiftListNotify extends BaseAction implements SoftwareProcess{
	private SoftwareService softwareService;
	private VipGiftDetailService vipGiftDetailService;
	private VipBonusLineService vipBonusLineService;

	@SuppressWarnings("unchecked")
	@Override
	public String execute(Element bodyEle, String agentId) {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = null;
		String searchId= null;
		String searchId2= null;
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		Element giftList = new Element("vipGiftList");
		// -------------------------------------------------
		// 获取请过的body
		Element giftType = bodyEle.getChild("giftType");
		String message = "组装节点错误!";
		try {
			userName = giftType.getChildText("account");// 用户名
			try {
				searchId = giftType.getChildText("searchId");// 红包Id
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				searchId2 = giftType.getChildText("searchId2");// 结算明细ID
			} catch (Exception e) {
				// TODO: handle exception
			}
			// 查找红包列表

			VipGiftDetailSearch search = new VipGiftDetailSearch();
			search.setAccount(userName);//受赠人
			if(searchId!=null&&!searchId.equals("")){
				search.setGiftId(Integer.parseInt(searchId));
			}
			
			List<VipGiftDetail> giftDatailList = vipGiftDetailService.findAllBySearch(search);//受赠的红包

			search.setAccount(null);//受赠人
			search.setVipAccount(userName);// 赠送人			
			
			List<VipGiftDetail> giftToDatailList = vipGiftDetailService.findAllBySearch(search);//送出的红包
			
			// 红包结算明细
			VipBonusLineSearch search2 = new VipBonusLineSearch();
			search2.setAccount(userName);
			search2.setStatus(CommonStatus.YES);
			
			if(searchId2!=null&&!searchId2.equals("")){
				search2.setId(Integer.parseInt(searchId2));
			}
			
			List<VipBonusLine> vipBonusList =  vipBonusLineService.findAllBySearch(search2);
			
			int giftCount=0;
			
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			DecimalFormat df = new DecimalFormat("#0.00"); 
			if (giftToDatailList != null && !giftToDatailList.isEmpty()){
				// 组装内容
				for (VipGiftDetail menuItem : giftToDatailList) {
					giftCount+=1;
					Element gift = new Element("gift");
					Element account = new Element("account");// 赠送者账号
					Element vipAccount = new Element("vipAccount");// 受赠者账号
					Element giftAmount = new Element("amount");// 金额
					Element giftId = new Element("giftId");// 红包ID
					Element createDateTime = new Element("createDateTime");// 生成时间
					Element status = new Element("status");// 0：未使用，1已使用，2已过期
					Element giftName = new Element("giftName");
					Element type = new Element("type");//0：送出，1：受赠，2：日结红包

					giftId.setText(menuItem.getId().toString());
					account.setText(menuItem.getAccount());
					vipAccount.setText(menuItem.getVipAccount());
					giftAmount.setText(menuItem.getAmount().toString());
					createDateTime.setText(sdf.format(menuItem.getCreateTimeDate()));
					status.setText("1");
					giftName.setText("赠送好友 "+menuItem.getAccount());
					type.setText("0");
					
					gift.addContent(giftId);
					gift.addContent(account);
					gift.addContent(vipAccount);
					gift.addContent(giftAmount);
					gift.addContent(createDateTime);
					gift.addContent(status);
					gift.addContent(giftName);
					gift.addContent(type);
					
					giftList.addContent(gift);
				}
			}
						
			if (giftDatailList != null && !giftDatailList.isEmpty()) {
				// 组装内容
				for (VipGiftDetail menuItem : giftDatailList) {
					giftCount+=1;
					Element gift = new Element("gift");
					Element giftId = new Element("giftId");// 红包ID
					Element account = new Element("account");// 赠送者账号
					Element vipAccount = new Element("vipAccount");// 受赠者账号
					Element giftAmount = new Element("amount");// 金额
					Element createDateTime = new Element("createDateTime");// 生成时间
					Element status = new Element("status");// 0：未使用，1已使用，2已过期
					Element giftName = new Element("giftName");
					Element type = new Element("type");//0：送出，1：受赠，2：日结红包

					giftId.setText(menuItem.getId().toString());
					account.setText(menuItem.getAccount());
					vipAccount.setText(menuItem.getVipAccount());
					giftAmount.setText(menuItem.getAmount().toString());
					createDateTime.setText(sdf.format(menuItem.getCreateTimeDate()));
					status.setText("1");
					giftName.setText(menuItem.getVipAccount()+" 好友赠送");
					type.setText("1");
					
					gift.addContent(giftId);
					gift.addContent(account);
					gift.addContent(vipAccount);
					gift.addContent(giftAmount);
					gift.addContent(createDateTime);
					gift.addContent(status);
					gift.addContent(giftName);
					gift.addContent(type);
					
					giftList.addContent(gift);
				}
			}
			
			if(vipBonusList!=null&&!vipBonusList.isEmpty()){
				// 组装内容
				for(VipBonusLine menuItem : vipBonusList ){
					giftCount+=1;
					Element gift = new Element("gift");
					Element giftId = new Element("giftId");// 红包ID
					Element account = new Element("account");// 赠送者账号
					Element vipAccount = new Element("vipAccount");// 受赠者账号
					Element giftAmount = new Element("amount");// 金额
					Element createDateTime = new Element("createDateTime");// 生成时间
					Element status = new Element("status");// 0：未使用，1已使用，2已过期，3已结算
					Element giftName = new Element("giftName");
					Element type = new Element("type");//0：送出，1：受赠，2：日结红包
					
					giftId.setText(menuItem.getId().toString());
					account.setText(menuItem.getAccount());
					vipAccount.setText("YCsystem");
					giftAmount.setText(df.format(menuItem.getBonus()));
					createDateTime.setText(sdf.format(menuItem.getSettlementTime()));
					status.setText("3");
					giftName.setText("红包活动奖励");
					type.setText("2");
					
					gift.addContent(giftId);
					gift.addContent(account);
					gift.addContent(vipAccount);
					gift.addContent(giftAmount);
					gift.addContent(createDateTime);
					gift.addContent(status);
					gift.addContent(giftName);
					gift.addContent(type);
					
					giftList.addContent(gift);
				}
			}
			
			if(giftCount==1){
				Element gift = new Element("gift");
				Element giftId = new Element("giftId");// 红包ID
				Element account = new Element("account");// 赠送者账号
				Element vipAccount = new Element("vipAccount");// 受赠者账号
				Element giftAmount = new Element("amount");// 金额
				Element createDateTime = new Element("createDateTime");// 生成时间
				Element status = new Element("status");// 0：未使用，1已使用，2已过期
				Element giftName = new Element("giftName");
				Element type = new Element("type");//0：送出，1：受赠
				
				giftId.setText("");
				account.setText("");
				vipAccount.setText("");
				giftAmount.setText("");
				createDateTime.setText("");
				status.setText("");
				giftName.setText("");
				type.setText("");
				
				gift.addContent(giftId);
				gift.addContent(account);
				gift.addContent(vipAccount);
				gift.addContent(giftAmount);
				gift.addContent(createDateTime);
				gift.addContent(status);
				gift.addContent(giftName);
				gift.addContent(type);
				
				giftList.addContent(gift);
			}
			List<Element> list = new ArrayList<Element>();
			list = giftList.cloneContent();
			Collections.sort(list, new Comparator<Element>() {
				@Override
				public int compare(Element o1, Element o2) {
					long time1 = 0;
					try {
						time1 = sdf.parse(o1.getChildText("createDateTime")).getTime();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long time2 =1;
					try {
						time2 = sdf.parse(o2.getChildText("createDateTime")).getTime();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
					// 先按时间排序，再按类型排序
					if(time1 < time2){
						return 1;
					}else if(time1 == time2){
						int type1 = Integer.parseInt(o1.getChildText("type"));
						int type2 = Integer.parseInt(o2.getChildText("type"));
						if(type1 < type2){
							return 1;
						}
					}
					return 0;
				}
			});
			
			giftList.removeContent();
			giftList.setContent(list);
			
			responseCodeEle.setText("0");
			responseMessage.setText("处理成功！");
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);
			
			
			if (giftCount > 0) {
				myBody.addContent(giftList);
			}else{
				return PackageXml("3001", "暂无新红包！", "9060", agentId);
			}
			
			return softwareService.toPackage(myBody, "9060", agentId);

		}catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "查询处理中或许存在异常";
				}
				return PackageXml("3002", code, "9060", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	public String PackageXml(String CodeEle, String message, String type, String agentId) throws Exception {
		// ----------------新建包体--------------------
		String returnContent = "";
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode");
		Element responseMessage = new Element("responseMessage");
		responseCodeEle.setText(CodeEle);
		responseMessage.setText(message);
		myBody.addContent(responseCodeEle);
		myBody.addContent(responseMessage);
		returnContent = this.softwareService.toPackage(myBody, type, agentId);
		return returnContent;
	}
	public SoftwareService getSoftwareService() {
		return softwareService;
	}
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	public VipGiftDetailService getVipGiftDetailService() {
		return vipGiftDetailService;
	}
	public void setVipGiftDetailService(VipGiftDetailService vipGiftDetailService) {
		this.vipGiftDetailService = vipGiftDetailService;
	}
	public void setVipBonusLineService(VipBonusLineService vipBonusLineService) {
		this.vipBonusLineService = vipBonusLineService;
	}
}
