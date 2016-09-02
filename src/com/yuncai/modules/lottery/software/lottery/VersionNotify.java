package com.yuncai.modules.lottery.software.lottery;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yuncai.modules.lottery.WebConstants;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.software.service.CommonConfig;
import com.yuncai.modules.lottery.software.service.CommonConfigFactory;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import com.yuncai.modules.lottery.software.service.VersionBean;

public class VersionNotify extends BaseAction implements SoftwareProcess{

	private SoftwareService softwareService; 
	private CommonConfigFactory softwareConfigFactory;
	@SuppressWarnings({"deprecation", "unchecked"})
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String versionName = null; // 版本名称
		String versionNo = null; // 版本编号
		// -------------------新建xml包体--------------------------
		Element myBody = new Element("body");
		Element responseCodeEle = new Element("responseCode"); // 返回值
		Element responseMessage = new Element("responseMessage");
		// -------------------------------------------------
		Element version = bodyEle.getChild("version");
		String message = "组装节点不存在";
		try {
			versionName = version.getChildText("versionName").trim();
			versionNo = version.getChildText("versionNo");
			message = null;
			CommonConfig config = this.softwareConfigFactory.getCommonConfig(agentId);
			if (config != null) {
				// 判断版本问题
				String isVersion = config.getIsVersion();
				if (isVersion != null && !"".equals(isVersion) && "0".equals(isVersion)) {
					if (versionName != null && !"".equals(versionName) && versionNo != null && !"".equals(versionNo)) {
						// 读取设置的版本号
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						try {
							DocumentBuilder db = dbf.newDocumentBuilder();
							Document doc = db.parse(new FileInputStream(WebConstants.WEB_PATH+"/version/version.xml"));

							NodeList serverslist = doc.getElementsByTagName(versionName);
							ArrayList nodeList = new ArrayList();
							Map mapList = new HashMap();
							int count=0;
							int countBig=0;
							String url = "";
							List<VersionBean> nodeList_s = new ArrayList<VersionBean>();
							String take = null;
							String str=null;
							String instruction = null;
							if (serverslist != null) {
								Node node = serverslist.item(0);
								NodeList childList = node.getChildNodes();
								for (int i = 0; i < childList.getLength(); i++) {
									Node child = childList.item(i);
									if (child != null && child.getNextSibling() != null && child.getNextSibling().getNodeName().equals("ver")) {
										String key = child.getNextSibling().getAttributes().getNamedItem("code").getNodeValue();
										String value = child.getNextSibling().getTextContent().toString();
										int index=Integer.parseInt(child.getNextSibling().getAttributes().getNamedItem("index").getNodeValue().toString());
										String url_s=child.getNextSibling().getAttributes().getNamedItem("url").getNodeValue().toString();
										String ins = child.getNextSibling().getAttributes().getNamedItem("instruction").getNodeValue().toString();
										String test="";
										if(child.getNextSibling().getAttributes().getNamedItem("test") != null){
											test=child.getNextSibling().getAttributes().getNamedItem("test").getNodeValue().toString()==null?"":child.getNextSibling().getAttributes().getNamedItem("test").getNodeValue().toString();
										}
										VersionBean bean = new VersionBean();
										bean.setIndex(index);
										bean.setUrl(url_s);
										bean.setTest(test);
										bean.setInstruction(ins);
										//则判断是否相同
										if(versionNo.equals(key)){
											take=value;
											count++;
											countBig=index;
										}
										nodeList_s.add(bean);
									}
								}
								if(count==0){
									return PackageXml("3003", "版本号不存在", "9005", agentId);
								}
								
								if(nodeList_s.size()>0 && !nodeList_s.isEmpty()){
									VersionBean bean=(VersionBean)nodeList_s.get(nodeList_s.size()-1);
									int index=bean.getIndex();
									String test=bean.getTest();
									
									if(index>countBig){
										if(test==null || "".equals(test)){
											str="新";
											url=bean.getUrl();
											instruction=bean.getInstruction();
										}
									
									}
								}
								// 处理成功
								responseCodeEle.setText("0");
								responseMessage.setText("处理成功");
								Element ver = new Element("version");
								Element bool = new Element("bool"); // 可用与不可用
								bool.setText(take);
								Element newVersion = new Element("newVersion"); // 为false是否有新版本
								newVersion.setText(str);
								Element newUrl = new Element("newUrl");
								newUrl.setText(url);
								Element instructionEle = new Element("instruction");
								instructionEle.setText(instruction);
								ver.addContent(bool);
								ver.addContent(newVersion);
								ver.addContent(newUrl);
								ver.addContent(instructionEle);
								myBody.addContent(responseCodeEle);
								myBody.addContent(responseMessage);
								myBody.addContent(ver);
								return this.softwareService.toPackage(myBody, "9005", agentId);

							} else {
								return PackageXml("3002", "版本名称不存在", "9005", agentId);
							}

						} catch (Exception e) {
							e.printStackTrace();
							return PackageXml("4009", "处理中或许存在异常", "9005", agentId);
						}
					} else {
						return PackageXml("4009", "版本号不能为空", "9005", agentId);
					}
				} else {
					return PackageXml("4007", "不需要使用版本的权限", "9005", agentId);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				String code = "";
				if (message != null) {
					code = message;
				} else {
					code = "处理中或许存在异常";
				}
				return PackageXml("3002", code, "9005", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	// 组装错误信息
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

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Map mapList = new HashMap();
		ArrayList nodeList = new ArrayList();
		nodeList.add("1.0.0");
		nodeList.add("1.0.1");
		nodeList.add("1.0.2");
		nodeList.add("1.0.3");
		mapList.put("1.0.0", false);
		mapList.put("1.0.1", true);
		mapList.put("1.0.2", true);
		mapList.put("1.0.3", true);
		String no = mapList.get("1.0.0").toString();
		String is = null;
		if (no != null && !"".equals(no)) {

			if (no.equals("false")) {
				for (int i = 0; i < nodeList.size(); i++) {
					String str = (String) nodeList.get(i);
					System.out.println("str" + str);
					String value = (String) mapList.get(str).toString();
					if ("true".equals(value)) {
						is = str;
						break;
					}
				}
				System.out.println(is);
			}
		} else {
			System.out.println("不存在");
		}
	}

	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	public void setSoftwareConfigFactory(CommonConfigFactory softwareConfigFactory) {
		this.softwareConfigFactory = softwareConfigFactory;
	}

}
