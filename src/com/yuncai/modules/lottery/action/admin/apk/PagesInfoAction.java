package com.yuncai.modules.lottery.action.admin.apk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yuncai.modules.lottery.action.BaseAction;
import com.yuncai.modules.lottery.action.admin.search.PagesInfoSearch;
import com.yuncai.modules.lottery.model.Oracle.Card;
import com.yuncai.modules.lottery.model.Oracle.PagesInfo;
import com.yuncai.modules.lottery.model.Oracle.toolType.LotteryType;
import com.yuncai.modules.lottery.model.Oracle.toolType.PageType;
import com.yuncai.modules.lottery.service.oracleService.member.PagesInfoService;
import com.yuncai.modules.lottery.sporttery.support.CommonUtils;
import com.yuncai.modules.lottery.sporttery.support.SportteryOption;

@Controller("pagesInfoAction")
@Scope("prototype")
public class PagesInfoAction extends BaseAction {

	@Resource
	private PagesInfoService pagesInfoService;

	private String commandType;
	private Integer pageId;
	private String pageName;
	private PagesInfo pagesInfo;
	private String returnUrl;
	private String message;
	private PagesInfoSearch search;
	private List<PagesInfo> list;// 对象集合
	private List<LotteryType> lotteryTypeList = LotteryType.list;
	private List<PageType> pageTypeList = PageType.list;
	private Integer lotteryTypeId;
	private Integer pageTypeId;
	private File json;// 导入json文件
	private String jsonFileName;
	private String jsonContentType;

	public String index() {
		return INDEX;
	}

	// 获取列表
	public void findList() throws IOException {
		super.pageSize = 20;
		list = pagesInfoService.findBySearch(search, (page - 1) * pageSize, pageSize);
		super.total = (int) pagesInfoService.findCountBySearch(search);

		JSONArray jsonList = JSONArray.fromObject(list);
		JSONObject jsonPager = new JSONObject();

		jsonPager.element("pageSize", super.pageSize);
		jsonPager.element("total", super.total);
		jsonPager.element("page", page);

		JSONObject jsonObject = new JSONObject();
		jsonObject.element("list", jsonList);
		jsonObject.element("pager", jsonPager);

		String msg = jsonObject.toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}
	
	// 获取列表
	public void findAllList() throws IOException {
		//super.pageSize = 20;
		//search.setPlatform("");
		super.total = (int) pagesInfoService.findCountBySearch(search);
		list = pagesInfoService.findBySearch(search, (page - 1) * pageSize, total);
		
		JSONArray jsonList = JSONArray.fromObject(list);

		String msg = jsonList.toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}

	public void findPageInfo(){
		
	}
	
	// 跳转到添加或修改
	public String command() {
		if (commandType.equals("add")) {
			if (pageId != null) {
				pageName = pagesInfoService.find(pageId).getPageName();
				// newsType.setParentId(typeId);

			}
			return "add";
		}
		if (commandType.equals("edit")) {
			if (pageId != null) {
				pagesInfo = pagesInfoService.find(pageId);
				lotteryTypeId = pagesInfo.getLotteryType().getValue();
				pageTypeId = pagesInfo.getPageType().getValue();
				return "edit";
			}
		}
		return SUCCESS;
	}

	// 添加页面信息
	public String addPagesInfo() {
		try {
			pagesInfo.setCreateDateTime(new Date());
			pagesInfo.setLotteryType(LotteryType.getItem(lotteryTypeId));
			pagesInfo.setPageType(PageType.getItem(pageTypeId));

			pagesInfoService.save(pagesInfo);

			message = "添加成功";
		} catch (Exception e) {
			message = "添加失败" + e.getMessage();
		}

		returnUrl = "/admin/pagesInfo.php";
		return INDEX;
	}

	// 添加页面信息(异步)
	public void addPagesInfo2() throws IOException {
		String msg = "";
		try {
			pagesInfo.setCreateDateTime(new Date());
			pagesInfo.setLotteryType(LotteryType.getItem(lotteryTypeId));
			pagesInfo.setPageType(PageType.getItem(pageTypeId));

			List<PagesInfo> tmp = pagesInfoService.findByProperty("pageId", pagesInfo.getPageId());
			if (tmp != null && tmp.size() > 0) {
				msg = "{\"code\":1,\"message\":\"对不起，此页面编号已存在，请重新输入!\"}";
			} else {
				pagesInfoService.save(pagesInfo);
				msg = "{\"code\":0,\"message\":\"添加成功。\"}";
			}
		} catch (Exception e) {
			msg = "{\"code\":1,\"message\":\"对不起，添加失败!" + e.getMessage() + "\"}";
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}

	// 修改页面信息
	public String editPagesInfo() {
		try {
			pagesInfo.setLotteryType(LotteryType.getItem(lotteryTypeId));
			pagesInfo.setPageType(PageType.getItem(pageTypeId));
			pagesInfoService.update(pagesInfo);
			message = "修改成功";
		} catch (Exception e) {
			message = "修改失败" + e.getMessage();
		}

		returnUrl = "/admin/pagesInfo.php";
		return INDEX;
	}

	// 删除页面信息
	public String deletePagesInfo() {
		pagesInfo = pagesInfoService.find(pageId);
		try {
			pagesInfoService.delete(pagesInfo);
			message = "删除成功";
		} catch (Exception e) {
			message = "删除失败" + e.getMessage();
		}

		returnUrl = "/admin/platform.php";
		return INDEX;
	}

	// 删除页面信息(异步)
	public void deletePagesInfo2() throws IOException {
		String msg = "";
		pagesInfo = pagesInfoService.find(pageId);
		try {
			pagesInfoService.delete(pagesInfo);
			msg = "{\"code\":0,\"message\":\"删除成功。\"}";
		} catch (Exception e) {
			msg = "{\"code\":1,\"message\":\"删除失败。" + e.getMessage() + "\"}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}

	// 生成json文件并提供下载
	public void exportJson() {
		List<Card> cardList = new ArrayList<Card>();
		total = pagesInfoService.findCountBySearch(search);
		list = pagesInfoService.findBySearch(search, 0, total);

		JSONArray jsonList = JSONArray.fromObject(list);
		String str = jsonList.toString();

		String fullPath = httpServletRequest.getSession().getServletContext().getRealPath("/");
		String fileName = fullPath + "staticFiles/downloads/pagesList.json";

		try {
			FileOutputStream fis = new FileOutputStream(fileName);
			OutputStreamWriter isr = new OutputStreamWriter(fis, "UTF-8");
			BufferedWriter br = new BufferedWriter(isr);
			br.write(str);
			br.close();

			System.out.println("json导出成功！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 下载生成的TXT文件
		try {
			download(httpServletResponse, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 下载文件 ：filePath文件的存放路径
	public void download(HttpServletResponse response, String filePath) throws IOException {

		File file = new File(filePath);
		String filename = file.getName();
		InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename)));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
	}

	// 导入json文件
	@SuppressWarnings("unchecked")
	public void importJson() throws IOException {
		String msg = "";
		String fileType = "";
		try {
			fileType = jsonFileName.substring(jsonFileName.lastIndexOf(".") + 1, jsonFileName.length()).toUpperCase();
		} catch (Exception e) {
			msg = "{\"code\":1,\"message\":\"对不起，只能上传json文件!\"}";
		}
		if (!fileType.equals("JSON")) {
			msg = "{\"code\":1,\"message\":\"对不起，只能上传json文件!\"}";
		} else {
			try {

				InputStreamReader read = new InputStreamReader(new FileInputStream(json), "UTF-8");// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				String content = "";
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (!content.equals("")) {
						content += "\\n" + lineTxt;
					} else {
						content = lineTxt;
					}
				}

				// JSONArray jsonArray = JSONArray.fromObject(content);
				Map classMap = new HashMap<String, Class>();
				classMap.put("lotteryType", LotteryType.class);
				classMap.put("pageType", PageType.class);
				List<PagesInfo> list = CommonUtils.getList4Json(content, PagesInfo.class, classMap);

				if (list != null) {
					for (PagesInfo page : list) {
						pagesInfoService.save(page);
					}
				}

				msg = "{\"code\":0,\"message\":\"导入成功。\"}";
				read.close();
			} catch (Exception e) {
				msg = "{\"code\":1,\"message\":\"对不起，导入文件失败!\"}";
			}

		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public PagesInfo getPagesInfo() {
		return pagesInfo;
	}

	public void setPagesInfo(PagesInfo pagesInfo) {
		this.pagesInfo = pagesInfo;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PagesInfoSearch getSearch() {
		return search;
	}

	public void setSearch(PagesInfoSearch search) {
		this.search = search;
	}

	public List<PagesInfo> getList() {
		return list;
	}

	public void setList(List<PagesInfo> list) {
		this.list = list;
	}

	public List<LotteryType> getLotteryTypeList() {
		return lotteryTypeList;
	}

	public void setLotteryTypeList(List<LotteryType> lotteryTypeList) {
		this.lotteryTypeList = lotteryTypeList;
	}

	public List<PageType> getPageTypeList() {
		return pageTypeList;
	}

	public void setPageTypeList(List<PageType> pageTypeList) {
		this.pageTypeList = pageTypeList;
	}

	public Integer getLotteryTypeId() {
		return lotteryTypeId;
	}

	public void setLotteryTypeId(Integer lotteryTypeId) {
		this.lotteryTypeId = lotteryTypeId;
	}

	public Integer getPageTypeId() {
		return pageTypeId;
	}

	public void setPageTypeId(Integer pageTypeId) {
		this.pageTypeId = pageTypeId;
	}

	public File getJson() {
		return json;
	}

	public void setJson(File json) {
		this.json = json;
	}

	public String getJsonFileName() {
		return jsonFileName;
	}

	public void setJsonFileName(String jsonFileName) {
		this.jsonFileName = jsonFileName;
	}

	public String getJsonContentType() {
		return jsonContentType;
	}

	public void setJsonContentType(String jsonContentType) {
		this.jsonContentType = jsonContentType;
	}

}
