package com.yuncai.modules.lottery.action.software;

import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MD5;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.model.Oracle.Channel;
import com.yuncai.modules.lottery.service.oracleService.member.ChannelService;
import com.yuncai.modules.lottery.software.service.CommonConfig;
import com.yuncai.modules.lottery.software.service.CommonConfigFactory;
import com.yuncai.modules.lottery.software.service.HeadResposnseBean;
import com.yuncai.modules.lottery.software.service.ProcessSoftware;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Decoder;

@Controller("ycSoftLotteryAction")
@Scope("prototype")
public class SoftwareAction extends BaseAction {
	@Resource
	private ProcessSoftware softwareProcessManager;
	@Resource
	private CommonConfigFactory softwareConfigFactory;
	@Resource
	private SoftwareService softwareService;
	@Resource
	private ChannelService channelService;
	
	public SoftwareAction(){
		
	}
	
	public String index() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String returnContent = "erro";

		String transType = request.getParameter("type");
		String transMessage = request.getParameter("message");
		
		Long startTime = System.currentTimeMillis();  //进来当前时间表
		//LogUtil.out("服务接口:"+transType+"请求入口时间!");
		String key="";
		if (transMessage != null && !"".equals(transMessage)) {
			//LogUtil.out(transMessage);
			String type = getTransType(transType);
			byte[] decompressed = new BASE64Decoder().decodeBuffer(transMessage);
			transMessage = CompressFile.decompress(decompressed);
			if (transMessage != null && !"".equals(transMessage)) {


				SAXBuilder builder = new SAXBuilder();
				Document doc = null;
				Reader in = new StringReader(transMessage);
				try {
					doc = builder.build(in);
					Element root = doc.getRootElement();
					HeadResposnseBean head = new HeadResposnseBean();
					head.elementToObje(root.getChild("head"));
					key=head.getAgentId(); //获取那个接口
					//访重复提交投注-------------------------
					String sessionMessage=key+transType+head.getMessageId(); 
//					
					// 根据维一编号获取相应的key
					if (!"yctest".equals(head.getAgentId())) {
						CommonConfig config = this.softwareConfigFactory.getCommonConfig(head.getAgentId());
						if (config != null) {
							super.channelID = head.getChannelId();
							super.sessionCode=sessionMessage;
							List<Channel> channelList = channelService.findByProperty("name", super.channelID);
							//channelList != null && !channelList.isEmpty() && channelList.size() > 0
							if (channelList != null && !channelList.isEmpty() && channelList.size() > 0) {
								String forMd5 = head.getMessageId() + head.getAgentId() + config.getKey();
								String myDigest = MD5.encode(forMd5, "utf-8").toUpperCase();
								
								// LogUtil.out("forMd5:" + forMd5);
								// 验证成功
								// if (myDigest.equals(head.getDigest())) {
								if (myDigest.equals(head.getDigest().toUpperCase())) {
									// 得到相应交易码的处理器 获取相应的通知
									SoftwareProcess softwareProcess = (SoftwareProcess) softwareProcessManager.getProcessMap().get(transType);
									Class yourClass = softwareProcess.getClass();

									Class[] argc = new Class[1];
									argc[0] = String.class;
									Method setMethod = yourClass.getMethod("setRemoteIp", argc);
									Method setMethod2 = yourClass.getMethod("setChannelID", argc);
									Method setMethod3 = yourClass.getMethod("setSessionCode", argc);

									Object[] argo = new Object[1];
									argo[0] = super.remoteIp;
									
									Object[] argo2 = new Object[1];
									argo2[0] = super.channelID;
									
									Object[] argo3 = new Object[1];
									argo3[0] = super.sessionCode;
									
									setMethod.invoke(softwareProcess, argo);
									setMethod2.invoke(softwareProcess, argo2);
									setMethod3.invoke(softwareProcess, argo3);
									
									
									if (softwareProcess != null) {
										// 执行业务处理
										if(transType.equals("1007")){
											//session.setAttribute("messageCode", sessionMessage);
											//处理投注重复提交拦截
											LogUtil.out("------------服务"+key+"平台或渠道" + transType+"接口处理开始！！");
										}
										returnContent = softwareProcess.execute(root.getChild("body"), head.getAgentId());
										
										if(transType.equals("1007")){
											//处理投注重复提交拦截
											LogUtil.out("------------服务"+key+"平台或渠道" + transType+"接口处理结束！！");
										}
										
										if (returnContent == null) {
											returnContent = PackageXml("4008", "业务处理过程中获取为空值", type, head.getAgentId());
										}
									} else {
										returnContent = PackageXml("4005", "交易类型错误", type, head.getAgentId());
									}

								} else {
									returnContent = PackageXml("4003", "Md5验证密钥错误", type, head.getAgentId());
								}
							}else {
								returnContent = PackageXml("4001", "渠道编号错误", type, head.getChannelId());
							}

							

						} else {
							returnContent = PackageXml("4001", "电子销售商务唯一编号错误", type, head.getAgentId());
						}

					} else {
						returnContent = PackageXml("4001", "电子销售商务唯一编号错误,编号\"yctest\"为解释通过码，紧属云彩维一编号!", type, head.getAgentId());
					}
			    
					//LogUtil.out("------------" + returnContent);
					out.write(returnContent);
					out.flush();
					out.close();
					// LogUtil.out(returnContent);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (response.getWriter() != null) {
						out.close();
					}
				}
				
			} else {
				returnContent = PackageXml("4006", "消息体GZip解压为空，请将消息体GZip压缩则Base64编码，再进行Url编码格式才正确。编号\"yctest\"为解释通过码，紧属云彩维一编号!", "9100", "yctest");
				out.write(returnContent);
				out.flush();
				out.close();
			}

		} else {
			returnContent = PackageXml("4007", "提交post方法错误,编号\"yctest\"为解释通过码，紧属云彩维一编号!", "9100", "yctest");
			out.write(returnContent);
			out.flush();
			out.close();
		}
		
		Long endTime = System.currentTimeMillis();
		//LogUtil.out("服务"+key+"平台或渠道"+transType+"接口处理--共花:" + (endTime - startTime) + "ms!");
		//LogUtil.out("服务"+key+"平台或渠道"+transType+"接口出口时间!");
		return this.renderTextHtml(returnContent);
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
	// 跟据请求的交易类型到得响应类型
	public String getTransType(String transType) {
		String type = "";
		if ("1001".equals(transType)) {
			type = "9001";
		} else if ("1002".equals(transType)) {
			type = "9002";
		} else if ("1003".equals(transType)) {
			type = "9003";
		} else if ("1005".equals(transType)) {
			type = "9005";
		} else if ("1006".equals(transType)) {
			type = "9006";
		} else {
			type = transType;
		}
		return type;
	}

}
