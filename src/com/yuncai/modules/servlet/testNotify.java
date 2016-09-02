package com.yuncai.modules.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import java.util.Date;
import java.util.List;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yuncai.core.hibernate.CommonStatus;
import com.yuncai.core.tools.AmountUtils;
import com.yuncai.core.tools.ip.IpSeeker;
import com.yuncai.core.util.DBHelper;

import com.yuncai.modules.lottery.model.Oracle.GiftDatailLine;
import com.yuncai.modules.lottery.model.Oracle.GiftManage;
import com.yuncai.modules.lottery.model.Oracle.Member;
import com.yuncai.modules.lottery.model.Oracle.MemberChargeLine;
import com.yuncai.modules.lottery.model.Oracle.MemberOperLine;
import com.yuncai.modules.lottery.model.Oracle.toolType.ChargeType;
import com.yuncai.modules.lottery.model.Oracle.toolType.MemberOperType;
import com.yuncai.modules.lottery.model.Oracle.toolType.OperLineStatus;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletTransType;
import com.yuncai.modules.lottery.model.Oracle.toolType.WalletType;
import com.yuncai.modules.lottery.service.oracleService.member.GiftDatailLineService;
import com.yuncai.modules.lottery.service.oracleService.member.GiftManageService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberChargeLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberOperLineService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberService;
import com.yuncai.modules.lottery.service.oracleService.member.MemberWalletService;


public class testNotify extends HttpServlet {

	// 定义Spring上下文环境
	private static ApplicationContext ctx = null;

	// 获取Spring上下文环境
	public Object getBean(String name) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		}
		return ctx.getBean(name);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	@SuppressWarnings( { "unchecked", "static-access" })
	protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.addHeader("Expires", "0");
		response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.addHeader("Pragma", "no-cache");
		

		String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
		try {
			
			GiftManageService giftManageService = (GiftManageService)this.getBean("giftManageService");
			
			GiftDatailLineService  giftDatailLineService =(GiftDatailLineService)this.getBean("giftDatailLineService");
			
			MemberService memberService =(MemberService)this.getBean("memberService");
			
			MemberWalletService memberWalletService =(MemberWalletService)this.getBean("memberWalletService");
			

			IpSeeker ipSeeker = (IpSeeker)this.getBean("ipSeeker");
			
			MemberOperLineService memberOperLineService=(MemberOperLineService)this.getBean("memberOperLineService");
			
			
			MemberChargeLine charge=new MemberChargeLine();
			charge.setChannel("ycAndroid");
			charge.setAmount(13.0);
			charge.setAccount("test3");
			
						//进行活动赠送处理
						{
							{
								//处理活动 1.先获取渠道 2.根据渠道查询是否有这个活动 3.检测是否存在
								String channlId=charge.getChannel();
								String account=charge.getAccount();
								double amount_=charge.getAmount();
								
								List<GiftManage> listManage=giftManageService.findByChannelList(channlId, 1);
								if(!listManage.isEmpty()){
									for(int i=0; i<listManage.size();i++){
										GiftManage bean=(GiftManage)listManage.get(i);
										//获取这个活动的信息,判断是否使用过一次
										String topNumber=bean.getTopNumber(); //字头
										String postLimit =bean.getPostLimit(); //限制
										Double giftRequest=bean.getGiftRequest(); //要求
										Integer giftType=bean.getGiftType(); //类型
										Double giftMoney=bean.getGiftMoney(); //红包金额，单位元
										//判断活动是否开启
										Integer is_val=bean.getIsValid()==null?0:bean.getIsValid() ;
										if(is_val==1){
											//判断要求是否大于金额
											Integer reMonery=Double.valueOf(giftRequest).intValue();
											if(amount_>=reMonery){
												//判断用户是否使用过一次
												List<GiftDatailLine> listLine=giftDatailLineService.findByAccountList(account, topNumber);
												Member member=memberService.findByAccount(account);
												if(!listLine.isEmpty()){
													//限制如果小于就进行充值
													if(listLine.size()<Integer.parseInt(postLimit)){
														{
															giftManageLine(member, bean, giftMoney,memberWalletService,giftDatailLineService,ipSeeker,memberOperLineService,ip);
														}
													}
												}else{
													//没有进行充值
													giftManageLine(member, bean, giftMoney,memberWalletService,giftDatailLineService,ipSeeker,memberOperLineService,ip);
												}
											}
											
										}
									}
								}
								
							}
						}
					
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response.getWriter() != null) {
				out.close();
			}
		}
	}
	
		public Integer giftManageLine(Member member,GiftManage bean,Double giftMoney ,MemberWalletService memberWalletService,GiftDatailLineService giftDatailLineService,IpSeeker ipSeeker,MemberOperLineService memberOperLineService,String ip){
			// 保存会员操作流水
		MemberOperLine operLine = new MemberOperLine();
		operLine.setAccount(member.getAccount());
		operLine.setSourceId(new Integer(0));
		operLine.setCreateDateTime(new Date());
		operLine.setExtendInfo("");
		operLine.setReferer("");
		operLine.setUrl("");
		operLine.setFromPlace(ipSeeker.getCountry(ip) + ipSeeker.getArea(ip));
		operLine.setIp(ip);
		operLine.setTerminalId((long)0);
		operLine.setOperType(MemberOperType.CHARGE_PRESENT);
		operLine.setStatus(OperLineStatus.SUCCESS);
		
		operLine.setChannel(bean.getChannel().getName());
		
		memberOperLineService.save(operLine);
		Integer operLineNo = operLine.getOperLineNo();
		
		try {
			memberWalletService.increaseAble(member, giftMoney, -1, 0, 0, operLineNo, WalletType.DUOBAO.getValue(),
					WalletTransType.CHARGE_PRESENT.getValue(), member.getSourceId(), bean.getGiftName());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		//保存记录
		GiftDatailLine line=new GiftDatailLine();
		line.setGiftName(bean.getGiftName());
		line.setPlatFormeId(bean.getPlatform().getId());
		line.setPlatformeName(bean.getPlatform().getName());
		line.setChannelId(bean.getChannel().getId());
		line.setChannelName(bean.getChannel().getName());
		line.setMemberId(member.getId());
		line.setAccount(member.getAccount());
		line.setAmount(giftMoney);
		//组装编号字头
		if(DBHelper.isNoNull(member.getMobile())){
			StringBuilder number=new StringBuilder();
			number.append(bean.getTopNumber()).append(member.getMobile().substring(5, 11));
			line.setGiftId(number.toString());
		}else{
			StringBuilder number=new StringBuilder();
			number.append(bean.getTopNumber()).append(member.getAccount());
			line.setGiftId(number.toString());
		}
		line.setCreateTimeDate(new Date());
		giftDatailLineService.save(line);
	
		return line.getId();
	}


}
