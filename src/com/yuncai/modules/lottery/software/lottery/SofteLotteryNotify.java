package com.yuncai.modules.lottery.software.lottery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jdom.Element;

import com.yuncai.core.common.Constant;
import com.yuncai.core.longExce.ServiceException;
import com.yuncai.core.longExce.WebDataException;
import com.yuncai.core.tools.LogUtil;
import com.yuncai.core.tools.StringTools;
import com.yuncai.core.util.CompileUtil;
import com.yuncai.modules.lottery.action.lottery.AbstractDsUpLoadAction;
import com.yuncai.modules.lottery.model.Oracle.FollowingPlanInfo;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.toolType.FollowingPlanStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.GenType;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberFollowingType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlanType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PlayType;
import com.yuncai.modules.lottery.model.Oracle.toolType.SelectType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.model.Sql.SensitiveKeyWords;
import com.yuncai.modules.lottery.service.oracleService.lottery.TradeService;
import com.yuncai.modules.lottery.service.oracleService.member.FollowingPlanInfoService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberScoreService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;
import com.yuncai.modules.lottery.service.sqlService.user.CustomFriendFollowSchemesService;
import com.yuncai.modules.lottery.service.sqlService.user.UsersService;
import com.yuncai.modules.lottery.software.service.CommonConfig;
import com.yuncai.modules.lottery.software.service.CommonConfigFactory;
import com.yuncai.modules.lottery.software.service.SoftwareProcess;
import com.yuncai.modules.lottery.software.service.SoftwareService;
import freemarker.cache.StrongCacheStorage;

@SuppressWarnings("serial")

public class SofteLotteryNotify extends AbstractDsUpLoadAction implements SoftwareProcess{
	private static final int BUFFER_SIZE = 5024 * 1024;
	
	private SoftwareService softwareService; 
	private MemberWalletService memberWalletService;
	private TradeService tradeService;
	private CommonConfigFactory softwareConfigFactory;
	private MemberScoreService memberScoreService;
	private CustomFriendFollowSchemesService sqlCustomFriendFollowSchemesService;
	private FollowingPlanInfoService followingPlanInfoService; //处理存储自动跟单号
	private UsersService sqlUsersService;
	public void setSqlUsersService(UsersService sqlUsersService) {
		this.sqlUsersService = sqlUsersService;
	}
	@SuppressWarnings({ "unused", "unchecked" })
	public String execute(Element bodyEle, String agentId) {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		//内部购彩参数
		int lotteryType=0;  //彩种
		String strContent=null;  //传过来内容
		
		String content=null; //内容
		int count=0; //注数
		int multiple=0; //倍数
		String userName=null;
		String userPwd=null;
		int selectType=2;  //默认选择
		int planType=2;  //1 代购 2 合买
		String term=null;  //彩期
		int playType=0; //子玩法
		Integer planNo=0;
//		Integer orderNo=0;
		String addAttribute = "";  //是否追加投注0是1否
		int founderAmount=1;  //我要认购。。。
		int founderBdAmount=0;  //我要保底
		String memo=null;
		String other="团结就是力量！";
		int commision=0;
		int perAmount=0;
		int Part=0;
		Integer publicStatus=3;
		String filePath=null;
		int fromPage = 0;// todo 来自哪个页面的代购，暂时硬编码，需要在以后进行整理并赋值
		double amount=0;    ///认购
		String changeType=null;
		
		//竟彩
		int passMode=1;
		String  sqlContext=null; //sql竞彩原本的封装内容
		String uploadFilePath=null;
		String orderNo=null;
		
		 String phoneNo=null;//机身码
		 String sim = null;//SIM卡
		 String model = null;//手机机型
		 String systemVersion = null;//系统版本
		 String channel = null;//渠道ID
		
		String title = null;
		
		String commision_=null;
		
		String version = null;
		
		Integer easyType = null;
		
		//scheme
		
		//----------------新建xml包体--------------------
	    @SuppressWarnings("unused")
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
		Element scheme=bodyEle.getChild("scheme");
		String node="组装节点不存在";
		try {
			
			
			
			Member memberSession = (Member) request.getSession().getAttribute(Constant.MEMBER_LOGIN_SESSION);
			if(memberSession==null){
				return PackageXml("2000", "请登录!", "9300", agentId);
			}
			if(memberSession!=null){
			Integer userGradeType = memberSession.getUserGradeType()==null?-2:memberSession.getUserGradeType();
			if((userGradeType==1&& !"admin".equals(memberSession.getAccount()))){
					return PackageXml("012", "对不起！您的账户存在异常情况，请联系云彩客服：400-850-6636", "9001", agentId);
				}
			}
			 planType=Integer.parseInt(scheme.getChildText("planType"));  //选择玩法 代购、合买
			//防重注入-----------------------------------
			//LogUtil.out("session之前:"+super.sessionCode);
			String resend=super.sessionCode+memberSession.getAccount();
			if(session.getAttribute(Constant.SOFT_LOTTERY)!=null){
				String messageCode=(String) session.getAttribute(Constant.SOFT_LOTTERY);
				//LogUtil.out("session之后:"+messageCode);
				if(messageCode.equals(resend)){
					LogUtil.out("防重复......"+resend);
					return PackageXml("4009", PlanType.getItem(planType).getName()+"还在处理中,请查看投注记录是否投注成功!", "4005", agentId); 
				}
			}
			session.setAttribute(Constant.SOFT_LOTTERY, resend);
			//------------------------------session结束--------------
			
			 lotteryType=Integer.parseInt(scheme.getChildText("lotteryType"));  //彩种
			 //planNo=Integer.parseInt(scheme.getChildText("planNo")); //方案号
			 multiple=Integer.parseInt(scheme.getChildText("multiple"));  //倍投
			 userName=scheme.getChildText("userName").toString();  //用户名
			 userPwd=scheme.getChildText("userPwd").toString();
			 term=scheme.getChildText("term");  //获取彩期
			 amount=Double.parseDouble(scheme.getChildText("amount"));  //购买金额
			 strContent=scheme.getChildText("content");  //方案内容	 			 
			 //LogUtil.out(strContent);
			
			 addAttribute=scheme.getChildText("addAttribute").trim().toString(); //追加
			 selectType=Integer.parseInt(scheme.getChildText("selectType"));  //购买类型
			 playType=Integer.parseInt(scheme.getChildText("playType")); //子玩法
			// LogUtil.out("playType="+playType);
			 //轻松购彩标志
			 easyType=StringTools.isNullOrBlank(scheme.getChildText("easyType"))?null:Integer.parseInt(scheme.getChildText("easyType"));
			 
			 phoneNo = bodyEle.getChildText("phoneNo");
			 sim = bodyEle.getChildText("sim");
			 model = bodyEle.getChildText("model");
			 systemVersion = bodyEle.getChildText("systemVersion");
			 channel = StringTools.isNullOrBlank(bodyEle.getChildText("channel")) ?null:bodyEle.getChildText("channel") ;
			
			 LotteryType lotType = (LotteryType) LotteryType.getItem(lotteryType);
			 
				version = StringTools.isNullOrBlank( bodyEle.getChildText("version"))?null: bodyEle.getChildText("version");
				
				//特殊处理6版本之前客户端 组三复式 组六 玩法错误
				if(Integer.parseInt(version.trim())<=5){
					if(playType==6304){
						playType=6303;
					}else if (playType==6303) {
						playType=6304;
					}
					
				}
//				
//				//特殊处理IOS大乐透不支持投注
//				if(LotteryType.TCCJDLT.getValue()==lotType.getValue()){
//					return PackageXml("002", "因规则调整暂停14052期投注。详询400-850-6636", "9001", agentId); 
//				}

			 
				// 如果是竞彩足球、竞彩篮球另外做处理
			 if (LotteryType.JCZQList.contains(lotType)) {
				 if(version ==null||Integer.parseInt(version)<5){
						return PackageXml("002", "当前版本不支持竞彩足球，请在[个人设置]中升级后使用。详询400-850-6636", "9001", agentId); 
				 }
			 } 
				// 如果是老足彩版本9以下不允许投注
			 if (LotteryType.CTZQList.contains(lotType)) {
				 if(version ==null||Integer.parseInt(version)<9){
						return PackageXml("002", "新版本增加投注参考数据，请更新版本继续投注。", "9001", agentId); 
				 }
			 } 
			 
			 //判断是合买的一些参数
			 if(planType==PlanType.HM.getValue()){
			   publicStatus=Integer.parseInt(scheme.getChildText("publicStatus")); //方案公开还是不公开
			   founderBdAmount=Integer.parseInt(scheme.getChildText("founderBdAmount")); //合买就有保底
			   other=scheme.getChildText("other").toString(); //合买宣言
			   commision=Integer.parseInt(scheme.getChildText("commision").toString()); //购买提成
			   founderAmount=Integer.parseInt(scheme.getChildText("founderAmount").toString()); //合买份数
			   perAmount=Integer.parseInt(scheme.getChildText("perAmount").toString()); //每份金额
			   Part=Integer.parseInt(scheme.getChildText("part").toString()); //总份数
			   title = scheme.getChildText("title");//合买方案宣传标题
			   
				 List<SensitiveKeyWords>objs = sqlUsersService.findSensitiveKeyWords();
					for(SensitiveKeyWords obj :objs){
						//习近平,温家宝,江泽民
						String str = obj.getKeywords();
						String []keys = str.split(",");
						for(String key:keys){
							if(title.indexOf(key)>=0||other.indexOf(key)>0){
								super.errorMessage = "标题或描述存在敏感字眼，请尝试其它内容!";
								return PackageXml("002", super.errorMessage, "9004", agentId);								
							}
						}
						
					}
			   
			 }
			
			 
			 int founderAmount_=founderAmount * perAmount; //认购总金额
			 
			 try {
				passMode=Integer.parseInt(scheme.getChildText("passMode"));
			} catch (Exception e) {
				passMode=1;
			}
			try {
				sqlContext=scheme.getChildText("passType");
			} catch (Exception e) {
				sqlContext=null;
			}
			try {
				uploadFilePath=scheme.getChildText("uploadFilePath");
			} catch (Exception e) {
				uploadFilePath=null;
			}
			
			try {
				changeType=scheme.getChildText("changeType").equals("0")?null:scheme.getChildText("changeType");
			} catch (Exception e) {
				changeType=null;
			}
			
			
			node=null;
			
			
			if(!userName.equals(memberSession.getAccount())){
				return PackageXml("9001", "用户名与Cokie用户不匹配", "9300", agentId);
			}
			// 验证上传文件
			
			//预投不做内容验证
			if ((strContent == null && "".equals(strContent))&& selectType==SelectType.YUTOU.getValue()) {
				return PackageXml("1002", "方案内容不能为空", "9300", agentId);
			}
			
			/*有效数字验证*/
			{
				boolean isamount=CompileUtil.isNumber(Float.valueOf(Double.toString(amount)).intValue()+"");
				if(!isamount)
					return PackageXml("1111", "对不起,输入金额有误!", "9300", agentId);
		
				boolean ismultiple=CompileUtil.isNumber(multiple+"");
				if(!ismultiple)
					return PackageXml("1111", "对不起,输入倍数有误!", "9300", agentId);
			}
			
			if(amount<0){
				return PackageXml("1111", "对不起,输入金额有误!", "9300", agentId);
			}
			
			if(multiple<0 || multiple>999){
				return PackageXml("1111", "对不起,输入倍数有误,最大倍数为999!", "9300", agentId);
			}
			
			
			//验证金额是否超资
			if((planType ==PlanType.DG.getValue() &&amount>memberSession.getWallet().getAbleBalance()) ||
					(planType ==PlanType.HM.getValue() && founderAmount_ >memberSession.getWallet().getAbleBalance())){
				return PackageXml("1102", "对不起，您的余额不足！", "9300", agentId);
			}
			
			if(planType==PlanType.HM.getValue()){
				/*有效验证*/
				{
					boolean isfounderAmount_=CompileUtil.isNumber(founderAmount_+"");
					if(!isfounderAmount_)
						return PackageXml("1111", "对不起,输入金额有误!", "9300", agentId);
					boolean isfounderBdAmount=CompileUtil.isNumber(founderBdAmount+"");
					if(!isfounderBdAmount)
						return PackageXml("1111", "对不起,保底金额有误!", "9300", agentId);
					boolean iscommision=CompileUtil.isNumber(commision+"");
					if(!iscommision)
						return PackageXml("1111", "对不起,提成盈利输入有误!", "9300", agentId);
				}
				
				if(founderAmount_<0){
					return PackageXml("1111", "对不起,输入金额有误!", "9300", agentId);
				}
				
				if(founderAmount_ > amount){
					return PackageXml("1111", "上传金额错误", "9300", agentId);
				}
				
				if(founderBdAmount<0){
					return PackageXml("1111", "对不起,保底金额有误!", "9300", agentId);
				}
				//处理提成
				if(commision<0 || commision>5){
					return PackageXml("1111", "对不起,提成盈利0～5%!", "9300", agentId);
				}
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
			
			//预头处理
			
			if(selectType==SelectType.YUTOU.getValue()){
				if(planType==PlanType.HM.getValue()){
					content="";
					selectType=SelectType.UPLOAD.getValue();
				}else{
					return PackageXml("102", "先发起后上传投注错误！投注限于合买", "9300", agentId);
				}
			}
			
			
			 //LogUtil.out("oralce接收前端值："+strContent);
			 
			// LogUtil.out("sql接收前端值："+sqlContext);

			
			
			//非单式
			content=strContent;
			
			//处理业务逻辑
			
		
			String _s = (String) request.getSession().getAttribute("s") == null ? "0" :(String)request.getSession().getAttribute("s");
			Long ct = 0L;
			try {
				ct = Long.parseLong(_s);
			} catch (Exception e) {
			}
			
			try{
			//处理业务
				
				String gentypeIn_s=null;  //购彩类型
				GenType gentypeIn=null;
				String resultPath_=null; //<!-- 投注前是否返回路径 0是1否 -->
				try {
					CommonConfig config_s=this.softwareConfigFactory.getCommonConfig(agentId);
					if(config_s!=null){
						gentypeIn_s = config_s.getGentypeIn();
						resultPath_=config_s.getResultPath();
						if(gentypeIn_s!=null && !"".equals(gentypeIn_s)){
							int index=Integer.parseInt(gentypeIn_s);
							gentypeIn=GenType.getItem(index);
						}else gentypeIn=null;
					}else{
						gentypeIn=null;
						resultPath_=null;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if(changeType!=null){
					gentypeIn=GenType.getItem(Integer.parseInt(changeType));
				}
				
				String planNO_="";
			//	LogUtil.out("content="+content);
				// 对9版本之前大乐透投注内容进来排序一下
				
				if(lotteryType==39&&Integer.parseInt(version)<9){
					content=sortDLTContent(content,playType);
					
				}
				
				
				
				try {
					if (planType == 1) {
						planNO_ = tradeService.buyLotteryPlanBySource(memberSession, ct, memberSession.getAccount(), term, lotteryType, content,
								Float.valueOf(Double.toString(amount)).intValue(), selectType, uploadFilePath, fromPage, super.remoteIp,
								WalletType.DUOBAO.getValue(), publicStatus, MemberFollowingType.ALL.getValue(), gentypeIn, addAttribute, multiple,
								playType, sqlContext, phoneNo, sim, model, systemVersion, super.channelID, easyType, version);
					} else {
						//					LogUtil.out("title="+title);
						int soldPart = founderAmount;//+founderBdAmount; //认购份数+保底份数
						planNO_ = tradeService.buyLotteryPlanTogether(memberSession, ct, term, lotteryType, content, Float.valueOf(
								Double.toString(amount)).intValue(), perAmount, Part, founderBdAmount, soldPart, uploadFilePath, fromPage,
								super.remoteIp, WalletType.DUOBAO.getValue(), founderAmount, other, selectType, publicStatus, commision, gentypeIn,
								null, MemberFollowingType.ALL.getValue(), addAttribute, multiple, playType, sqlContext, phoneNo, sim, model,
								systemVersion, super.channelID, title, version);

					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					
					return PackageXml("1002", "投注错误或官方未有数据，请稍后再试。400-850-6636", "9300", agentId);
				}
				String []strs = planNO_.split("\\|");
				
				planNo=Integer.parseInt(strs[0]);
				orderNo = strs[1];
			}catch (ServiceException en) {
				en.printStackTrace();
				return PackageXml(en.getErrorCode(), en.getErrorMesg(), "9300", agentId);
			}catch (WebDataException e) {
				e.printStackTrace();
				String code=webException(e.getMessage());
				return PackageXml(code, e.getMessage(), "9300", agentId);
			} catch (Exception e) {
			    e.printStackTrace();
			    return PackageXml("1", e.getMessage(), "9300", agentId);
			}
			memberSession.setWallet(this.memberWalletService.findByAccount(memberSession.getAccount()));
			memberSession.setScore(memberScoreService.findByAccount(memberSession.getAccount()));
			
			//判定是否红人，记录为自动跟单方案
			List redlist =new ArrayList();
			if(planType==PlanType.HM.getValue()){
				//soldPart + reservePart > part
				//总份数-认购份数如果还大于0还可以自动跟单
				if(Part-founderAmount>0){
					redlist=this.sqlCustomFriendFollowSchemesService.findUserBySchemes(userName,lotteryType);
					if(redlist!=null && !redlist.isEmpty() && redlist.size()>0){
						FollowingPlanInfo following = new FollowingPlanInfo();
						following.setPlanNo(planNo);
						following.setFollowingType(MemberFollowingType.HEMAI.getValue());
						following.setPlanStatus(FollowingPlanStatus.UNFINISHED.getValue());
						following.setProcessedStatus(-1);
						followingPlanInfoService.save(following);
					}
				}
			}
			
			responseCodeEle.setText("0");  //上传成功
			responseMessage.setText("投注成功");
			Element planNos=new Element("planNo");
			Element orderNoEle = new Element("orderNo");
			
			planNos.setText(planNo.toString());
			orderNoEle.setText(orderNo);
//			Element orderNos=new Element("orderNo");
//			orderNos.setText(orderNo);
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
			myBody.addContent(orderNoEle);
			myBody.addContent(info);
			return softwareService.toPackage(myBody, "9300", agentId);
			
		} catch (ServiceException en) {
			  en.printStackTrace();
			  try {
				 return PackageXml(en.getErrorCode(), en.getErrorMesg(), "9300", agentId);
			  } catch (Exception e) {
				e.printStackTrace();
			  }
		}catch (Exception e) {
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
	
	public String webException(String Exception){
		String ExceptionCode="";
		 if("方案内容不能为空".equals(Exception)){
			ExceptionCode="1002";
		}else if("您没有选择任何场次".equals(Exception)){
			ExceptionCode="1006";
		}else if("您没有选择过关方式".equals(Exception)){
			ExceptionCode="1007";
		}else if("过滤投注暂不支持多个过关方式".equals(Exception)){
			ExceptionCode="";
		}else if("倍数不允许小于1".equals(Exception)){
			ExceptionCode="1008";
		}else if("您选择的场次中有已经截止的场次".equals(Exception)){
			ExceptionCode="1009";
		}else if("投注内容格式出错，请联系管理员".equals(Exception)){
			ExceptionCode="1100";
		}else if("上传的文件有空行".equals(Exception)){
			ExceptionCode="1110";
		}else{
			ExceptionCode="1";
		}
		
		return ExceptionCode;
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

	public void setSqlCustomFriendFollowSchemesService(CustomFriendFollowSchemesService sqlCustomFriendFollowSchemesService) {
		this.sqlCustomFriendFollowSchemesService = sqlCustomFriendFollowSchemesService;
	}

	public void setFollowingPlanInfoService(FollowingPlanInfoService followingPlanInfoService) {
		this.followingPlanInfoService = followingPlanInfoService;
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
	
	public static void main(String[] args) {
		Integer comission=13;
		System.out.println(comission/100d);
		System.out.println((comission/100d)*100d);
	}

}
