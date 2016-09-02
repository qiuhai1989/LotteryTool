package com.yuncai.modules.lottery.software.service;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.xml.XMLSerializer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.yuncai.core.common.Constant;
import com.yuncai.core.tools.CompressFile;
import com.yuncai.core.tools.DateTools;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.MD5;
import com.yuncai.core.tools.StringTools;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;

public class SoftwareService {
	private ProcessSoftware softwareProcessManager;
	private CommonConfigFactory softwareConfigFactory;

	/**
	 * 组装成完整的消息
	 * 
	 * @param bodyEle
	 *            消息体
	 * @return type=xx&message=消息头+消息体
	 * @throws Exception
	 */
	public String toPackage(Element bodyEle, String transType, String agentId) throws Exception {
		SAXBuilder sb = new SAXBuilder();
		Reader reader = new StringReader(Constant.HB_EMPTY_XML_STRINGCFG);
		Document sendDoc = sb.build(reader);
		Element rootElement = sendDoc.getRootElement();
		Element headEle = new Element("head");
		// 消息编号
		Element messageIdEle = new Element("messageId");
		messageIdEle.setText(agentId + DateTools.getNowDateYYYYMMDDHHMMSSSS() + ((int) Math.random()) % 10000);
		// 商户编号
		Element agentIdEle = new Element("agentId");
		agentIdEle.setText(agentId);
		// 验证码
		Element digestEle = new Element("digest");
		CommonConfig config = this.softwareConfigFactory.getCommonConfig(agentId);
		String digestStr = "";
		String genTypeIn = null;
		if (config != null) {
			digestStr = MD5.encode(messageIdEle.getText() + agentIdEle.getText() + config.getKey(), "utf-8");
			genTypeIn = config.getGentypeIn();
		} else {
			digestStr = MD5.encode(messageIdEle.getText() + agentIdEle.getText(), "utf-8");
		}
		digestEle.setText(digestStr);
		headEle.addContent(messageIdEle);
		headEle.addContent(agentIdEle);
		headEle.addContent(digestEle);
		rootElement.addContent(headEle);
		rootElement.addContent(bodyEle);
		sendDoc.setRootElement(rootElement);
		String message = xmlFormat(sendDoc);
//		LogUtil.out(message);
		// 手机端请求XML转Json
		if (genTypeIn != null) {
			if(Integer.parseInt(genTypeIn)==GenType.SJ.getValue()){
				message = xml2JSON(message);
			}
		}

		// if("1002".equals(transType)){
		// zip压缩与base64编码
		String isGzip = "";
		if (config != null) {
			isGzip = config.getIsGzip(); // 因php不支持Gzip
			if (isGzip != null && !"".equals(isGzip) && "0".equals(isGzip)) {
				String base64 = message;
				message = Base64.encode(base64.toString().getBytes());
			} else {
				byte[] compress = CompressFile.compress(message);
				message = Base64.encode(compress);
			}
		} else {
			byte[] compress = CompressFile.compress(message);
			message = Base64.encode(compress);
		}

		// message=URLEncoder.encode(message);
		// }
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("type", transType);
		requestMap.put("message", message);

		String content = message;
		return content;
	}

	public String toPackageRs(Element bodyEle, String transType, String agentId,String channelId) throws Exception {
		SAXBuilder sb = new SAXBuilder();
		Reader reader = new StringReader(Constant.HB_EMPTY_XML_STRINGCFG);
		Document sendDoc = sb.build(reader);
		Element rootElement = sendDoc.getRootElement();
		Element headEle = new Element("head");
		// 消息编号
		Element messageIdEle = new Element("messageId");
		messageIdEle.setText(agentId + DateTools.getNowDateYYYYMMDDHHMMSSSS() + ((int) Math.random()) % 10000);
		// 商户编号
		Element agentIdEle = new Element("agentId");
		agentIdEle.setText(agentId);
		// 验证码
		Element digestEle = new Element("digest");
		CommonConfig config = this.softwareConfigFactory.getCommonConfig(agentId);
		Element channelIdEle = new Element("channelId");
		channelIdEle.setText(channelId);
		String digestStr = "";
		if (config != null) {
			digestStr = MD5.encode(messageIdEle.getText() + agentIdEle.getText() + config.getKey(), "utf-8");
		} else {
			digestStr = MD5.encode(messageIdEle.getText() + agentIdEle.getText(), "utf-8");
		}
		digestEle.setText(digestStr.toUpperCase());
		headEle.addContent(messageIdEle);
		headEle.addContent(agentIdEle);
		headEle.addContent(digestEle);
		headEle.addContent(channelIdEle);
		rootElement.addContent(headEle);
		rootElement.addContent(bodyEle);
		sendDoc.setRootElement(rootElement);
		String message = xmlFormat(sendDoc);
		String isGzip = "";
		if (config != null) {
			isGzip = config.getIsGzip();
			if (isGzip != null && !"".equals(isGzip) && "0".equals(isGzip)) {
				String base64 = message;
				message = Base64.encode(base64.toString().getBytes());
				message = URLEncoder.encode(message, "gbk");
			} else {
				byte[] compress = CompressFile.compress(message);
				message = Base64.encode(compress);
				message = URLEncoder.encode(message);
			}
		} else {
			byte[] compress = CompressFile.compress(message);
			message = Base64.encode(compress);
			message = URLEncoder.encode(message);
		}

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("type", transType);
		requestMap.put("message", message);
		String content = StringTools.getStringByHashMap(requestMap);
		return content;
	}

	public String xmlFormat(Element ele) {
		XMLOutputter xo = new XMLOutputter();
		Format format = xo.getFormat();
		format.setEncoding("utf-8");
		format.setLineSeparator("");
		xo.setFormat(format);
		return xo.outputString(ele);
	}

	public String xmlFormat(Document doc) {
		XMLOutputter xo = new XMLOutputter();
		Format format = xo.getFormat();
		format.setEncoding("utf-8");
		format.setLineSeparator("");
		xo.setFormat(format);
		return xo.outputString(doc);
	}

	// XML转Json
	public static String xml2JSON(String xml) {
		return new XMLSerializer().read(xml).toString();
	}

	/**
	 * 不带body标签
	 * 
	 * @param doc
	 * @return
	 */
	public static String elementFormat(String content) {
		String startStr = "<body>";
		String endStr = "</body>";
		int startIndex = content.indexOf(startStr);
		if (startIndex != -1) {
			content = content.substring(startStr.length() + startIndex);

		}
		int endIndex = content.indexOf(endStr);
		if (endIndex != -1) {
			content = content.substring(0, endIndex);
		}
		return content;
	}

	public ProcessSoftware getSoftwareProcessManager() {
		return softwareProcessManager;
	}

	public void setSoftwareProcessManager(ProcessSoftware softwareProcessManager) {
		this.softwareProcessManager = softwareProcessManager;
	}

	public CommonConfigFactory getSoftwareConfigFactory() {
		return softwareConfigFactory;
	}

	public void setSoftwareConfigFactory(CommonConfigFactory softwareConfigFactory) {
		this.softwareConfigFactory = softwareConfigFactory;
	}

}
