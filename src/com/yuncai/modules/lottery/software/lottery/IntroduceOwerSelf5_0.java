package com.yuncai.modules.lottery.software.lottery;

import java.util.Map;

import org.jdom.Element;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;

public class IntroduceOwerSelf5_0 extends IntroduceOwerSelf{
	@Override
	public String execute(Element bodyEle, String agentId) {
		// TODO Auto-generated method stub
		//----------------新建xml包体--------------------
	    Element myBody=new Element("body");
	    Element responseCodeEle=new Element("responseCode");
	    Element responseMessage=new Element("responseMessage");
	    Element aboutUs =new Element("aboutUs");
	    Element concatUs = new Element("concatUs");
	    //-------------------------------------------------
	    try {
		    aboutUs.setText("\t\t云彩彩票是粤传媒(002181)属下全资子公司运营的彩票信息与彩票代购服务平台。平台拥有业内一流的彩票信息服务平台云彩彩票，集互联网、移动终端于一体，为彩民提供最为便捷的彩票代购服务。\t\t云彩彩票拥有来自知名互联网公司的管理人才所组成的核心团队，经营、研发队伍100余人；建立起业内领先的彩票信息服务平台，无缝跨越互联网、无线WAP及手机客户端，提供彩票资讯、分析、投注辅助工具等多种服务。平台依托粤传媒与互联网行业巨头新浪网、腾讯网、百度、支付宝等有深入的业务合作，增速趋势预计在100%以上。\t\t平台开放以来，秉承'诚信、专业、专注'的使命，坚持以用户至上的原则、真知灼见的行业水平、脚踏实地的不断提升我们的服务质量，与广大彩民朋友携手共进，互利双赢!【C5】");
		    concatUs.setText("客服热线：400-850-6636|客服邮箱：cs@6636.com|合买邮箱：hemai@6636.com|商务合作：hoophi@6636.com");
			
			responseCodeEle.setText("0"); //处理成功
			responseMessage.setText("处理成功");
			myBody.addContent(aboutUs);
			myBody.addContent(concatUs);
			myBody.addContent(responseCodeEle);
			myBody.addContent(responseMessage);    	
			return softwareService.toPackage(myBody, "9417",agentId);
		} catch (Exception e) {
	    	e.printStackTrace();
			try {
				return PackageXml("3002", "组装节点不存在", "9001", agentId);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	
}
