package com.yuncai.modules.lottery.software.lottery;

import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.CompileUtil;
import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.action.lottery.AbstractDsUpLoadAction;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChaseType;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.software.service.CommonConfig;
import com.yuncai.modules.lottery.software.service.CommonConfigFactory;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;


public class ZHNotify extends AbstractDsUpLoadAction implements SoftwareProcess{
	private static final int BUFFER_SIZE = 5024 * 1024;
	
	private SoftwareService softwareService;
	private MemberWalletService memberWalletService;
	private TradeService tradeService;
	private CommonConfigFactory softwareConfigFactory;
	private MemberScoreService memberScoreService;
	@Override
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		//内部购彩参数
		int lotteryType=0;  //彩种
		String strContent=null;  //传过来内容
		String amount=null; //总金额
		String amountPlan=null; //追号金额
		String content=null; //内容
		String multiple=null; //追号注数
		String userName=null;
		int selectType=2;  //默认选择
		int planType=2;  //1 代购 2 合买
		String term=null;  //追号彩期
		int playType=0; //子玩法
		
		Integer publicStatus=-1; //是否公开
		String stopCondition = "";// 1中奖后停止0继续追不停止
		String addAttribute="1";
		String changeType=null; //来源渠道
		int fromPage = 0;// todo 来自哪个页面的代购，暂时硬编码，需要在以后进行整理并赋值
		String currentTerm=null; //当前期数
		String uploadFilePath=null;
		
		 String phoneNo=null;//机身码
		 String sim = null;//SIM卡
		 String model = null;//手机机型
		 String systemVersion = null;//系统版本
		 String channel = null;//渠道ID
		 String version=null;
		
		//----------------新建xml包体--------------------
		Element myBody=new Element("body");
	    Element responseCodeEle=new Element("responseCode");
	    Element responseMessage=new Element("responseMessage");
	    
	    Element info=new Element("memberDetail");
	    Element ableBalance=new Element("ableBalance");
	    Element freezeBalance=new Element("freezeBalance");
	    Element takeCashQuota=new Element("takeCashQuota");
	    Element ableScore=new Element("ableScore");
	    //-------------------------------------------------
		//获取请过的body
		Element scheme=bodyEle.getChild("zhScheme");
		String node="组装节点不存在";
		try {
			
			
			amount=scheme.getChildText("amount").trim().toString();  //总金额
			strContent=scheme.getChildText("content").trim().toString();  //内容
			lotteryType=Integer.parseInt(scheme.getChildText("lotteryType")); //彩种类型
			userName=scheme.getChildText("userName").toString();  //用户名
			term=scheme.getChildText("term");  //获取追号彩期
			multiple=scheme.getChildText("multiple"); //追号倍数
			amountPlan=scheme.getChildText("amountPlan"); //每份金额
			selectType=Integer.parseInt(scheme.getChildText("selectType"));
			publicStatus=Integer.parseInt(scheme.getChildText("publicStatus"));
			playType=Integer.parseInt(scheme.getChildText("playType")); //子玩法
			currentTerm=scheme.getChildText("currentTerm"); //当前期数
			
			 phoneNo = bodyEle.getChildText("phoneNo");
			 sim = bodyEle.getChildText("sim");
			 model = bodyEle.getChildText("model");
			 systemVersion = bodyEle.getChildText("systemVersion");
			 channel = StringTools.isNullOrBlank( bodyEle.getChildText("channel"))?"":bodyEle.getChildText("channel");
			 version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
			try {
				addAttribute=scheme.getChildText("addAttribute");
			} catch (Exception e) {
				addAttribute="";
			}
			try {
				stopCondition=scheme.getChildText("stopCondition"); //终止条件 (0:中奖后停止追号)0#1000
				if(stopCondition==null || stopCondition==""){
					stopCondition="0";
				}
			} catch (Exception e) {
				stopCondition="0";
			}
			
			 
			try {
				changeType=scheme.getChildText("changeType").equals("0")?null:scheme.getChildText("changeType");
				} catch (Exception e) {
					changeType=null;
			}
			
			 node=null;
			
			 Member memberSession=(Member)request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			 if(memberSession==null){
			   return PackageXml("2000", "请登录!", "9300", agentId);
			 }
			
			 if(!userName.equals(memberSession.getAccount())){
				 return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			 }
			 
			//防重注入-----------------------------------
			//LogUtil.out("session之前:"+super.sessionCode);
			String resend=super.sessionCode+userName;
			if(session.getAttribute(Constant.SOFT_ZH)!=null){
				String messageCode=(String) session.getAttribute(Constant.SOFT_ZH);
				//LogUtil.out("session之后:"+messageCode);
				if(messageCode.equals(resend)){
					LogUtil.out("防重复......"+resend);
					return PackageXml("4009", "追号还在处理中,请查看投注记录是否投注成功!", "4005", agentId); 
				}
			}
			session.setAttribute(Constant.SOFT_ZH, resend);
			//------------------------------session结束--------------
			 
			 // 验证上传文件
			
			 //预投不做内容验证
			 if ((strContent == null && "".equals(strContent))&& selectType==SelectType.YUTOU.getValue()) {
				return PackageXml("1002", "方案内容不能为空", "9300", agentId);
			 }
			//验证金额是否超资
			 if( Double.parseDouble(amountPlan)>memberSession.getWallet().getAbleBalance()){
				 return PackageXml("1102", "对不起，您的余额不足！", "9300", agentId);
			 }
			 
			//处理单式上传
				
			if(selectType==SelectType.UPLOAD.getValue()){
				if (strContent.length() > BUFFER_SIZE) {
					return PackageXml("102", "文件超过限制大小", "9300", agentId);
				}
				
				String contextton=this.checkContext(strContent);
				
				// 对上传的文件进行本地持久化
				uploadFilePath = super.saveContextFile(contextton);
			}
			 

			//组装追号期数、倍数、金额
			String[] termsTemp = term.split("\\^");// 特别申明条件追号 页面传过来的期数格式:0^0^0
			String[] multiplesTemp = multiple.split("\\^");
			String[] amountsTemp = amount.split("\\^");
			int amountsAll=0;
			for(int i=0;i<amountsTemp.length;i++){
				amountsAll=amountsAll+Integer.parseInt(amountsTemp[i]);
			}
			{
				/*有效验证*/
				boolean isamountPlan=CompileUtil.isNumber(amountPlan);
				if(!isamountPlan)
					return PackageXml("1102", "对不起，金额错误！", "9300", agentId);
				boolean isamountsAll=CompileUtil.isNumber(amountsAll+"");
				if(!isamountsAll)
					return PackageXml("1102", "对不起，金额错误！", "9300", agentId);
			}
			if(Integer.parseInt(amountPlan)!=amountsAll){
				return PackageXml("1102", "对不起，金额错误！", "9300", agentId);
			}
			
			//非单式
			content=strContent;
			// 对9版本之前大乐透投注内容进来排序一下
			
			if(lotteryType==39&&Integer.parseInt(version)<9){
				content=sortDLTContent(content,playType);
				
			}
			//验证后处理业务
			String _s = (String) request.getSession().getAttribute("s") == null ? "0" :(String)request.getSession().getAttribute("s");
			Long ct = 0L;
			try {
				ct = Long.parseLong(_s);
			} catch (Exception e) {
			}
			String planNo=""; //单据号
			
			try {
				//处理业务
				String gentypeIn_s=null;  //购彩类型
				GenType gentypeIn=null;
				try {
					CommonConfig config_s=this.softwareConfigFactory.getCommonConfig(agentId);
					if(config_s!=null){
						gentypeIn_s = config_s.getGentypeIn();
						if(gentypeIn_s!=null && !"".equals(gentypeIn_s)){
							int index=Integer.parseInt(gentypeIn_s);
							gentypeIn=GenType.getItem(index);
						}else gentypeIn=null;
					}else{
						gentypeIn=null;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				if(changeType!=null){
					gentypeIn=GenType.getItem(Integer.parseInt(changeType));
				}
				int fromClient=1;
				
				planNo=tradeService.buyLotteryPlanChase(memberSession, ct, termsTemp, multiplesTemp, amountsTemp, lotteryType, content, Integer.valueOf(amountPlan),
						uploadFilePath, ChaseType.SELF_CHOOSE.getValue(), fromPage, super.remoteIp, WalletType.DUOBAO.getValue(), addAttribute, gentypeIn, currentTerm,
						"", Integer.parseInt(stopCondition), "谢谢大家的支持!", fromClient, playType,phoneNo,sim,model,systemVersion,super.channelID,version);
			}catch (ServiceException en) {
				en.printStackTrace();
				return PackageXml(en.getErrorCode(), en.getErrorMesg(), "9400", agentId);
			}catch (Exception e) {
				e.printStackTrace();
			    return PackageXml("1", e.getMessage(), "9400", agentId);
			}
			
			memberSession.setWallet(this.memberWalletService.findByAccount(memberSession.getAccount()));
			memberSession.setScore(memberScoreService.findByAccount(memberSession.getAccount()));
			{
				responseCodeEle.setText("0");  //上传成功
				responseMessage.setText("投注成功");
				Element planNos=new Element("planNo");
				planNos.setText(planNo.toString());
				ableBalance.setText(memberSession.getWallet().getAbleBalance().toString());
				freezeBalance.setText(memberSession.getWallet().getFreezeBalance().toString());
				
				double cashQuota=memberSession.getWallet().getTakeCashQuota();
				int IntCashQuota=(int)cashQuota;
				takeCashQuota.setText(IntCashQuota+"");
				//takeCashQuota.setText(memberSession.getWallet().getTakeCashQuota().toString());
				ableScore.setText(memberSession.getScore().getAbleScore().toString());
				info.addContent(ableBalance);
				info.addContent(freezeBalance);
				info.addContent(takeCashQuota);
				info.addContent(ableScore);
				
				myBody.addContent(responseCodeEle);
				myBody.addContent(responseMessage);
				myBody.addContent(planNos);
				myBody.addContent(info);
			}
			return softwareService.toPackage(myBody, "9300", agentId);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String message="";
				String status="200";
				if(node!=null)
					message=node;
				else{
					message=e.getMessage();
					status="1";
				}
				
				return PackageXml(status, message, "9300", agentId);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	//组装错误信息
	public String PackageXml(String CodeEle,String message,String type,String agentId) throws Exception{
		//----------------新建包体--------------------
		 String returnContent="";
		 Element myBody=new Element("body");
		 Element responseCodeEle=new Element("responseCode");
		 Element responseMessage=new Element("responseMessage");
		 responseCodeEle.setText(CodeEle); 
		 responseMessage.setText(message);
		 myBody.addContent(responseCodeEle);
		 myBody.addContent(responseMessage);
		 returnContent= this.softwareService.toPackage(myBody, type, agentId);
		 return returnContent;
		 
	}
	/**用于大乐透传进来的内容排序由于客户端9版本之前大乐透传进来投注内容存在乱序问题
	 * @param content
	 * @param playType
	 * @return
	 */
	public  String sortDLTContent(String content,int playType){
		String sortedContent=null;
		StringBuffer sbf =new StringBuffer();
		String[] rows=content.trim().split("\\\n+");
		
		switch (playType) {
		//标准
		case 3901:
			//[35 05 15 07 12 + 01 03]
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				strs[0]= sortString(strs[0].trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;		
		//标准
		case 3902:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				strs[0]= sortString(strs[0].trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;
		//胆拖	
		case 3903:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				String dan = strs[0].split(",")[0];
				String tuo= strs[0].split(",")[1];
				strs[0]= sortString(dan.trim())+","+sortString(tuo.trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
				
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;
			//胆拖	
		case 3904:
			for(int i=0;i<rows.length;i++){
				String row=rows[i];
				String[]strs=row.trim().split("\\+");
				String dan = strs[0].split(",")[0];
				String tuo= strs[0].split(",")[1];
				strs[0]= sortString(dan.trim())+" , "+sortString(tuo.trim());
				strs[1]= sortString(strs[1].trim());
				rows[i]=strs[0]+"+"+strs[1];
				sbf.append(rows[i]+"\n");
				
			}
			sortedContent=sbf.toString();
			sortedContent=sortedContent.substring(0, sortedContent.lastIndexOf("\n"));
			break;		
		default:
			sortedContent=content;
			break;
		}
		
		return sortedContent;
		
	}
	
	/**给数字组成的字符串排序后返回
	 * @param content
	 * @return
	 */
	public  String sortString(String content){
		String sortedStr=null;
		
		String[]strs=content.trim().split("\\s+");
//		System.out.println("进来时候："+content+"--"+content.length());
		//System.out.println("排序前："+Arrays.toString(strs));
		
		Arrays.sort(strs,new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				int i1=Integer.parseInt(o1);
				int i2=Integer.parseInt(o2);
				int r=i1-i2;
				if(r>0){
					return 1;
				}else if(r==0){
					
					return 0;
				}else{
					return -1;
				}
				
			}});
		sortedStr=Arrays.toString(strs);
		sortedStr=sortedStr.replaceAll("\\,", "").replaceAll("[\\[|\\]]", "");
//		System.out.println("排序后："+sortedStr.trim()+"--"+sortedStr.length());
//		System.out.println(content.trim().length()==sortedStr.trim().length());
		return sortedStr;
		
	}
	public void setSoftwareService(SoftwareService softwareService) {
		this.softwareService = softwareService;
	}
	public void setMemberWalletService(MemberWalletService memberWalletService) {
		this.memberWalletService = memberWalletService;
	}
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	public void setSoftwareConfigFactory(CommonConfigFactory softwareConfigFactory) {
		this.softwareConfigFactory = softwareConfigFactory;
	}
	public void setMemberScoreService(MemberScoreService memberScoreService) {
		this.memberScoreService = memberScoreService;
	}

}
