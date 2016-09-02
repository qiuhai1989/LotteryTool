package com.yuncai.modules.lottery.factorys.volecity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.web.context.ServletContextAware;

import com.yuncai.core.tools.FileTools;
import com.yuncai.core.tools.LogUtil;


public class VelocityFactory implements ServletContextAware{
	
	private ServletContext servletContext;

	// 获取VM文件MAP[文件名][文件内容]
	private Map vmFileNameMap = new HashMap();

	// 从SPRING里获取要读取的文件路径
	private List configPath = new ArrayList();

	/**
	 * 功能： 重新载入配置文件 输入参数：无 输出参数：无 作者： lm 日期： 2013-4-28
	 */
	public void load() {

		if (getVMFileNameList())
			LogUtil.out("load File Success!");
	}

	/**
	 * 功能： 从SPRING里面获取configPath，遍历configPath,
	 * 获取文件名和对应的路径存放入vmFileNameMap[文件名][文件对应的路径] 输入参数：无
	 * 输出参数：boolean的结果值，用来判断是否成功 作者：lm 日期：2013-6-15
	 */
	public boolean getVMFileNameList() {

		List path = new ArrayList();

		for (int j = 0; j < configPath.size(); j++) {
			try {
				LogUtil.out(configPath.get(j));
				// String str =
				// Thread.class.getClassLoader().getSystemResource(configPath.get(j).toString()).getFile().toString();
				String str = servletContext.getRealPath(File.separator + "WEB-INF" + File.separator + "classes" + File.separator
						+ configPath.get(j).toString());

				LogUtil.out(str);
				// 根据configPath里的路径获取对应的绝对路径
				path.add(str);
			} catch (Exception e) {

			}
		}

		for (int k = 0; k < path.size(); k++) {
			File file = new File(path.get(k).toString());

			if (path.get(k).toString().equals("null"))
				continue;

			// 获取对应路径下的所有文件名，存入fileList
			File fileList[] = file.listFiles();
			if (fileList == null) {
				continue;
			}

			for (int i = 0; i < fileList.length; i++) {
				try {
					String strFileName = FileTools.getFileName(fileList[i].getName());
					String strContent = new String();

					FileReader fr = new FileReader(fileList[i].getAbsoluteFile());
					BufferedReader br = new BufferedReader(fr);
					String line = br.readLine();
					while (line != null) {
						strContent = strContent + line + "\n";
						line = br.readLine();
					}
					br.close();
					fr.close();
					LogUtil.out("init..." + strFileName);
					this.vmFileNameMap.put(strFileName, strContent);
				} catch (Exception e) {
					LogUtil.out(e.getStackTrace());
				}
			}
		}

		return true;
	}

	/**
	 * 功能： 获取对应模板的String输出 输入参数：vmFileName ---输入的模板名称
	 * vmVarMap[当前模板对应的变量名][当前模板对应的变量值] 输出参数：boolean的结果值，用来判断是否成功
	 * 作者 ：lm 日期：2013-6-15
	 */
	public String getStaticString(String vmFileName, Map vmVarMap) throws Exception {

		String fileContent = this.vmFileNameMap.get(vmFileName).toString();

		Velocity.init();

		VelocityContext context = new VelocityContext();

		// 将获取的变量放入context里面
		Set set = vmVarMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			context.put(entry.getKey().toString(), entry.getValue());
		}

		StringWriter writer = new StringWriter();

		Velocity.evaluate(context, writer, "evaluate...", fileContent);

		String ret = writer.toString();

		// com.cailele.lottery.tools.LogUtil.out(ret);

		return ret;

	}

	public Map getVmFileNameMap() {
		return vmFileNameMap;
	}

	public void setVmFileNameMap(Map vmFileNameMap) {
		this.vmFileNameMap = vmFileNameMap;
	}

	public List getConfigPath() {
		return configPath;
	}

	public void setConfigPath(List configPath) {
		this.configPath = configPath;
	}

	public void setServletContext(ServletContext arg0) {
		servletContext = arg0;
	}

}
