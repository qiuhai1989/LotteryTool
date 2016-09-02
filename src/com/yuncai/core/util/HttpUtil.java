package com.yuncai.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.struts2.ServletActionContext;
//import org.htmlparser.Parser;
//import org.htmlparser.util.ParserException;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.yuncai.core.tools.LogUtil;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class HttpUtil {
	

	/**发送短信验证码
	 * @param phone  接收短信的手机号
	 * @param context 短信内容
	 * @return
	 */
	public  static  Boolean SmsSend(String phone,String context){
		Boolean tag = true;
//		String url = "http://wayin.5166.info:100/resms.aspx?phone="+phone+"&context="+context;
		context = escape(context);
		String url = "http://cpweb.wayin.cn:81/common/sendMsg.php?phone="+phone+"&context="+context;
//		LogUtil.out(url);
		HttpClient httpClient = new HttpClient();
		
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		
		// 设置 Http 连接超时为5秒
		try {
		GetMethod getMethod = new GetMethod();
		
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 50000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		getMethod.getParams().setUriCharset("UTF-8");
		getMethod.setURI(new URI(url,false,"UTF-8"));
	
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				tag=false;
				
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			byte[]reponseBody = getMethod.getResponseBody();
//			LogUtil.out(new String (reponseBody));
			LogUtil.out(statusCode);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return tag;
	}
	
	public static String escape (String src)
	 {
	  int i;
	  char j;
	  StringBuffer tmp = new StringBuffer();
	  tmp.ensureCapacity(src.length()*6);
	  for (i=0;i<src.length() ;i++ )
	  {
	   j = src.charAt(i);
	   if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
	    tmp.append(j);
	   else
	    if (j<256)
	    {
	    tmp.append( "%" );
	    if (j<16)
	     tmp.append( "0" );
	    tmp.append( Integer.toString(j,16) );
	    }
	    else
	    {
	    tmp.append( "%u" );
	    tmp.append( Integer.toString(j,16) );
	    }
	  }
	  return tmp.toString();
	 }

	static public InputStream getHtmlStreamByGetMethod(String url) {
		InputStream response = null;
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(url);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
			// 读取 HTTP 响应内容，这里简单打印网页内容
			byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			response = getMethod.getResponseBodyAsStream();//
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		return response;
	}
	static public byte[] getHtmlByGetMethod(String url) {
		System.setProperty(
				"org.apache.commons.logging.Log", 
				"org.apache.commons.logging.impl.SimpleLog");
				System.setProperty(
				"org.apache.commons.logging.simplelog.showdatetime", 
				"true");
				System.setProperty(
				"org.apache.commons.logging" + 
				".simplelog.log.org.apache.commons.httpclient", 
				"error");
		byte[] responseBody=new byte[]{};
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(url);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
			// 读取 HTTP 响应内容，这里简单打印网页内容
			responseBody = getMethod.getResponseBody();// 读取为字节数组
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		return responseBody;
	}
	static public String getHttpUrl(String urlString) {

		URL url = null;
		URLConnection connection = null;
		BufferedReader in = null;
		if (urlString != null && !urlString.trim().startsWith("http:")) {
			LogUtil.out("http tools: 非法请求(" + urlString + ")");
			return "";
		}

		try {
			url = new URL(urlString);
			connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			if (connection instanceof HttpsURLConnectionImpl) {

				HttpsURLConnection https = (HttpsURLConnection) connection;
				https.setHostnameVerifier(new MyVerified());

				X509TrustManager xtm = new MyTrustManager();
				TrustManager mytm[] = { xtm };
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, mytm, null);
				SSLSocketFactory sf = ctx.getSocketFactory();
				https.setSSLSocketFactory(sf);

			}

			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String temp = in.readLine();
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}

	static public Document getHttpUrlDocument(String urlString) {
		try {
			SAXBuilder sb = new SAXBuilder();
			Reader reader = new StringReader(getHttpUrlContent(urlString));
			return sb.build(reader);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static public String getHttpUrlContent(String urlString) {
		return getHttpUrlContent(urlString ,"UTF-8");
	}
	
	static public String getHttpUrlContent(String urlString,String charset) {

		URL url = null;
		URLConnection connection = null;
		BufferedReader in = null;

		if (urlString != null && !urlString.trim().startsWith("http:")) {
			LogUtil.out("http tools: 非法请求(" + urlString + ")");
			return "";
		}

		try {
			url = new URL(urlString);
			connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			connection.setRequestProperty("Referer", urlString);

			if (connection instanceof HttpsURLConnectionImpl) {

				HttpsURLConnection https = (HttpsURLConnection) connection;
				https.setHostnameVerifier(new MyVerified());

				X509TrustManager xtm = new MyTrustManager();
				TrustManager mytm[] = { xtm };
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, mytm, null);
				SSLSocketFactory sf = ctx.getSocketFactory();
				https.setSSLSocketFactory(sf);

			}
			// com.cailele.lottery.tools.LogUtil.out(connection.getContentEncoding());
			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
				/*
				 * com.cailele.lottery.tools.LogUtil.out(headers.keySet().toArray().length);
				 * for(Object o:headers.keySet().toArray()) {
				 * com.cailele.lottery.tools.LogUtil.out(o==null?"":o.toString()
				 * +"="+headers.get(o)); } //
				 */
				try {
					String contentType = headers.get("Content-Type").toString().replaceAll("\\[|\\]", "");
					String ct[] = contentType.split(";");
					if (ct.length > 1) {
						String[] cs = ct[1].split("=");
						if (cs.length > 1) {
							charset = cs[1];
						}
					}
				} catch (Exception e) {
					LogUtil.out(e.getMessage() + "");
				}
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			StringBuffer sb = new StringBuffer();
			String temp = "";
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}

	static public String getUrlTextContent(String urlString) {

		URL url = null;
		URLConnection connection = null;
		BufferedReader in = null;

		if (urlString != null && !urlString.trim().startsWith("http:")) {
			LogUtil.out("http tools: 非法请求(" + urlString + ")");
			return "";
		}

		try {
			url = new URL(urlString);
			connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");

			if (connection instanceof HttpsURLConnectionImpl) {

				HttpsURLConnection https = (HttpsURLConnection) connection;
				https.setHostnameVerifier(new MyVerified());

				X509TrustManager xtm = new MyTrustManager();
				TrustManager mytm[] = { xtm };
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, mytm, null);
				SSLSocketFactory sf = ctx.getSocketFactory();
				https.setSSLSocketFactory(sf);

			}
			// com.cailele.lottery.tools.LogUtil.out(connection.getContentEncoding());
			Map headers = connection.getHeaderFields();
			String charset = "UTF-8"; // default IE charset
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
				/*
				 * com.cailele.lottery.tools.LogUtil.out(headers.keySet().toArray().length);
				 * for(Object o:headers.keySet().toArray()) {
				 * com.cailele.lottery.tools.LogUtil.out(o==null?"":o.toString()
				 * +"="+headers.get(o)); } //
				 */
				try {
					String contentType = headers.get("Content-Type").toString().replaceAll("\\[|\\]", "");
					String ct[] = contentType.split(";");
					if (ct.length > 1) {
						String[] cs = ct[1].split("=");
						if (cs.length > 1) {
							charset = cs[1];
						}
					}
				} catch (Exception e) {
					LogUtil.out(e.getMessage() + "");
				}
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			StringBuffer sb = new StringBuffer();
			String temp = "";
			while ((temp = in.readLine()) != null) {
				sb.append(temp + "\r\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}

	static public String getUrl(String urlString) {

		URL url = null;
		URLConnection connection = null;
		InputStream in = null;

		if (urlString != null && !urlString.trim().startsWith("http:")) {
			LogUtil.out("http tools: 非法请求(" + urlString + ")");
			return "";
		}

		try {
			url = new URL(urlString);
			connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			connection.setRequestProperty("Referer", urlString);
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

			String charset = "GBK"; // default IE charset
			String encoding = "";
			if (connection instanceof HttpsURLConnectionImpl) {

				HttpsURLConnection https = (HttpsURLConnection) connection;
				https.setHostnameVerifier(new MyVerified());

				X509TrustManager xtm = new MyTrustManager();
				TrustManager mytm[] = { xtm };
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, mytm, null);
				SSLSocketFactory sf = ctx.getSocketFactory();
				https.setSSLSocketFactory(sf);

			}
			if (connection instanceof HttpURLConnection) {

				HttpURLConnection http = (HttpURLConnection) connection;
				encoding = http.getContentEncoding();

			}
			// com.cailele.lottery.tools.LogUtil.out(connection.getContentEncoding());
			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
				/*
				 * com.cailele.lottery.tools.LogUtil.out(headers.keySet().toArray().length);
				 * for(Object o:headers.keySet().toArray()) {
				 * com.cailele.lottery.tools.LogUtil.out(o==null?"":o.toString()
				 * +"="+headers.get(o)); } //
				 */
				// *
				try {
					String contentType = headers.get("Content-Type").toString().replaceAll("\\[|\\]", "");
					String ct[] = contentType.split(";");
					if (ct.length > 1) {
						String[] cs = ct[1].split("=");
						if (cs.length > 1) {
							charset = cs[1];
						}
					}
				} catch (Exception e) {
					LogUtil.out(e.getMessage() + "");
				}
				// */
			}

			if (("gzip").equals(encoding)) {
				in = new GZIPInputStream(connection.getInputStream());
			} else {
				in = connection.getInputStream();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer sb = new StringBuffer();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp + "\r\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}
	
	static public String getUrlHttps(String urlString) {

		URL url = null;
		URLConnection connection = null;
		InputStream in = null;

		if (urlString != null && !urlString.trim().startsWith("https:")) {
			LogUtil.out("http tools: 非法请求(" + urlString + ")");
			return "";
		}

		try {
			url = new URL(urlString);
			connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			connection.setRequestProperty("Referer", urlString);
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

			String charset = "GBK"; // default IE charset
			String encoding = "";
			if (connection instanceof HttpsURLConnectionImpl) {

				HttpsURLConnection https = (HttpsURLConnection) connection;
				https.setHostnameVerifier(new MyVerified());

				X509TrustManager xtm = new MyTrustManager();
				TrustManager mytm[] = { xtm };
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, mytm, null);
				SSLSocketFactory sf = ctx.getSocketFactory();
				https.setSSLSocketFactory(sf);

			}
			if (connection instanceof HttpURLConnection) {

				HttpURLConnection http = (HttpURLConnection) connection;
				encoding = http.getContentEncoding();

			}
			// com.cailele.lottery.tools.LogUtil.out(connection.getContentEncoding());
			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
				/*
				 * com.cailele.lottery.tools.LogUtil.out(headers.keySet().toArray().length);
				 * for(Object o:headers.keySet().toArray()) {
				 * com.cailele.lottery.tools.LogUtil.out(o==null?"":o.toString()
				 * +"="+headers.get(o)); } //
				 */
				// *
				try {
					String contentType = headers.get("Content-Type").toString().replaceAll("\\[|\\]", "");
					String ct[] = contentType.split(";");
					if (ct.length > 1) {
						String[] cs = ct[1].split("=");
						if (cs.length > 1) {
							charset = cs[1];
						}
					}
				} catch (Exception e) {
					LogUtil.out(e.getMessage() + "");
				}
				// */
			}

			if (("gzip").equals(encoding)) {
				in = new GZIPInputStream(connection.getInputStream());
			} else {
				in = connection.getInputStream();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer sb = new StringBuffer();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp + "\r\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}
	static public String getUrlUtf8(String urlString) {

		URL url = null;
		URLConnection connection = null;
		InputStream in = null;

		if (urlString != null && !urlString.trim().startsWith("http:")) {
			LogUtil.out("http tools: 非法请求(" + urlString + ")");
			return "";
		}

		try {
			url = new URL(urlString);
			connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			connection.setRequestProperty("Referer", urlString);
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

			String charset = "utf-8"; // default IE charset
			String encoding = "";
			if (connection instanceof HttpsURLConnectionImpl) {

				HttpsURLConnection https = (HttpsURLConnection) connection;
				https.setHostnameVerifier(new MyVerified());

				X509TrustManager xtm = new MyTrustManager();
				TrustManager mytm[] = { xtm };
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, mytm, null);
				SSLSocketFactory sf = ctx.getSocketFactory();
				https.setSSLSocketFactory(sf);

			}
			if (connection instanceof HttpURLConnection) {

				HttpURLConnection http = (HttpURLConnection) connection;
				encoding = http.getContentEncoding();

			}
			// com.cailele.lottery.tools.LogUtil.out(connection.getContentEncoding());
			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
				/*
				 * com.cailele.lottery.tools.LogUtil.out(headers.keySet().toArray().length);
				 * for(Object o:headers.keySet().toArray()) {
				 * com.cailele.lottery.tools.LogUtil.out(o==null?"":o.toString()
				 * +"="+headers.get(o)); } //
				 */
				// *
				try {
					String contentType = headers.get("Content-Type").toString().replaceAll("\\[|\\]", "");
					String ct[] = contentType.split(";");
					if (ct.length > 1) {
						String[] cs = ct[1].split("=");
						if (cs.length > 1) {
							charset = cs[1];
						}
					}
				} catch (Exception e) {
					LogUtil.out(e.getMessage() + "");
				}
				// */
			}

			if (("gzip").equals(encoding)) {
				in = new GZIPInputStream(connection.getInputStream());
			} else {
				in = connection.getInputStream();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer sb = new StringBuffer();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp + "\r\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}
//	@SuppressWarnings( { "static-access", "unchecked" })
//	public static Parser getWebParser(String pkUrl) throws ParserException {
//
//		String content = getUrl(pkUrl);
//		// Parser parser = new Parser();
//		// parser.getConnectionManager().getDefaultRequestProperties().put("User-Agent",
//		// "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_1; zh-cn)
//		// AppleWebKit/531.22.7 (KHTML, like Gecko) Version/4.0.5
//		// Safari/531.22.7");
//		// parser.setURL(pkUrl);
//		return Parser.createParser(content, "gbk");
//	}
//	public static Parser getWebParserUtf8(String pkUrl) throws ParserException {
//
//		String content = getUrlUtf8(pkUrl);
//		return Parser.createParser(content, "utf-8");
//	}

	// 以无参数post的方式发送数据
	public static String sendMessage(String xml, String urlpath) // ///////发送数据的方法，需要2个参数，一个地址，一个参数，返回对方页面返回的数据
	{
		
		String msg = "";
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		
		try {
			URL url = new URL(urlpath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(60000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/html");
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(xml);
			wr.flush();
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				msg = msg + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wr != null)
					wr.close();
				if (rd != null)
					rd.close();
			} catch (Exception e) {

			}
		}
		return msg;
		
	}
	
	public static Boolean SmsSend(String phone, String context, Integer type) {
		Boolean tag1 = false;
		Boolean tag2 = false;

		if (type == 1 || type == 0) {	
			String pre3 = phone.substring(0, 3);
			String pre4 = phone.substring(0, 4);
			// 检测是否是中国电信号码段
			if(pre3.equals("133")||pre3.equals("153")||pre3.equals("180")||pre3.equals("181")||pre3.equals("189")||pre4.equals("1349")){
				// 发送短信(短信猫)
				tag2 = SmsSend2(phone, context);
			}else{			
				JSONObject json = new JSONObject();
				JSONArray mobileJson = JSONArray.fromObject(phone.split(","));
				json.put("phone", mobileJson);
				json.put("SmsContent", context);
				json.put("SentFrom", 3);
				// 发送短信(MAS机)
				tag1 = SmsGroupSend(json.toString());
			}
			
			
		}
		
		if(type == 2 || type == 0){
			// 发送短信(短信猫)
			tag2 = SmsSend2(phone, context);
		}

		return tag1||tag2;
	}
	

	/**
	 * 发送短信验证码(林总)
	 * 
	 * @param phone
	 *            接收短信的手机号
	 * @param context
	 *            短信内容
	 * @return
	 */
	public static Boolean SmsSend2(String phone, String context) {
		Boolean tag = true;
		//String url = "?phone=" + phone + "&context=" + context;
		String url = "http://wayin.5166.info:8636/yc_sms.jsp";

		// post
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Connection", "close");

		NameValuePair param[] = new NameValuePair[2];
		param[0] = new NameValuePair("phone", phone);
		param[1] = new NameValuePair("context", context);

		post.getParams().setParameter(HttpClientParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		post.getParams().setContentCharset("UTF-8");

		post.setRequestBody(param);
		
		try {
			int result = httpclient.executeMethod(post);
			if (result == 200) {
				InputStream resStream = post.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "UTF-8"));
				StringBuffer resBuffer = new StringBuffer();
				String resTemp = "";
				while ((resTemp = br.readLine()) != null) {
					resBuffer.append(resTemp);
				}
				String txt = resBuffer.toString().trim();

				//JSONObject dataJson = JSONObject.fromObject(txt);

				if (txt.equals("1")) {
					tag = true;
				}else{
					tag = false;
				}
			} else {
				tag = false;
			}
		} catch (Exception ex) {
			tag = false;
		}
		
		return tag;
	}
	
	/**
	 * 批量发送短信
	 * 
	 * @param context
	 *            json格式参数示例：{"phone":["13800138000","13800138000"],"SmsContent"
	 *            :"发送内容","SentFrom":3} SentFrom：1代表购彩WEB，2代表APK ，3代表服务端
	 * @return
	 */
	public static Boolean SmsGroupSend(String context) {
		Boolean tag = true;
		String url = "http://wayin.5166.info:100/Default.aspx";

		// post
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Connection", "close");

		NameValuePair param[] = new NameValuePair[1];
		param[0] = new NameValuePair("json", toUnicode(context));

		post.getParams().setParameter(HttpClientParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		post.setRequestBody(param);

		try {
			int result = httpclient.executeMethod(post);
			if (result == 200) {
				InputStream resStream = post.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "UTF-8"));
				StringBuffer resBuffer = new StringBuffer();
				String resTemp = "";
				while ((resTemp = br.readLine()) != null) {
					resBuffer.append(resTemp);
				}
				String txt = resBuffer.toString().trim();

				JSONObject dataJson = JSONObject.fromObject(txt);

				if (dataJson.get("result").toString().equals("-1")) {
					tag = false;
				}
			} else {
				tag = false;
			}
		} catch (Exception ex) {
			tag = false;
		}
		return tag;
	}
	
	// 将汉字转 Unicode 码
	public static String toUnicode(String s) {
		String as[] = new String[s.length()];
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			String regEx = "[\u00a4-\uffe5]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(s.charAt(i) + "");
			if (m.find()) {
				as[i] = Integer.toHexString(s.charAt(i));
				s1 = s1 + "\\u" + as[i];
			} else {
				s1 = s1 + s.charAt(i);
			}
		}
		return s1;
	}
	
	// 以无参数post的方式发送数据保存sessionID
	public static String sendMessageSessionID(String xml, String urlpath) // ///////发送数据的方法，需要2个参数，一个地址，一个参数，返回对方页面返回的数据
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		String msg = "";
		String[]  sessionIds=null;
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		String session_value="";
		try {
			URL url = new URL(urlpath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(60000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			String sessionId=(String)request.getSession().getAttribute("sessionId");
			if(sessionId!=null && !"".equals(sessionId)){
			  conn.setRequestProperty("Cookie", sessionId);
			}
			 
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(xml);
			wr.flush();
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println(conn.getHeaderFields());
			if(sessionId==null){
				session_value = conn.getHeaderField("Set-Cookie") ==null ? "" : conn.getHeaderField("Set-Cookie");
				//session_value = conn.getHeaderField("Set-Cookie");
				System.out.println(conn.getHeaderFields());
				if(session_value!=""){
					sessionIds= session_value.split(";");
					if(sessionIds[0]!=null &&!"".equals(sessionIds[0])){
						request.getSession().setAttribute("sessionId", sessionIds[0]);
						System.out.println(sessionIds[0]);
					}
				}
				
			}
			String line;
			while ((line = rd.readLine()) != null) {
				msg = msg + line;
			}

			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wr != null)
					wr.close();
				if (rd != null)
					rd.close();
			} catch (Exception e) {

			}
		}
		return msg;
	}
	static public String connectBySSL(String content, String urlStr) {
		return connectBySSL(content,urlStr,"utf-8");
	}
	/**
	 * 使用SSL方式发送请求
	 * 
	 * @param content
	 *            请求内容
	 * @return 回应信息
	 */
	@SuppressWarnings("unchecked")
	static public String connectBySSL(String content, String urlStr,String encode) {
		URL url = null;
		URLConnection connection = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			url = new URL(urlStr);

			connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(60000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			if (connection instanceof HttpsURLConnectionImpl) {

				HttpsURLConnection https = (HttpsURLConnection) connection;
				https.setHostnameVerifier(new MyVerified());

				X509TrustManager xtm = new MyTrustManager();
				TrustManager mytm[] = { xtm };
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(null, mytm, null);
				SSLSocketFactory sf = ctx.getSocketFactory();
				https.setSSLSocketFactory(sf);
			}
			connection.connect();
			writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), encode));
			//com.cailele.lottery.tools.LogUtil.out("发送请求:\n" + content);
			writer.write(content);
			writer.flush();
			writer.close();
			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
			}
			StringBuffer sb = new StringBuffer();

			InputStream is = connection.getInputStream();
			byte[] b = new byte[200];
			int index = is.read(b);
			while (index != -1) {
				sb.append(new String(b, 0, index, encode));
				index = is.read(b);
			}
			// reader = new BufferedReader(new InputStreamReader(connection
			// .getInputStream(), encode));
			//
			// String temp = null;
			// while ((temp = reader.readLine()) != null) {
			// sb.append(temp+"\n");
			// }
			//com.cailele.lottery.tools.LogUtil.out("收到响应:\n" + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (writer != null)
					writer.close();
			} catch (Exception e) {
			}
		}
	}

}

class MyVerified implements HostnameVerifier {
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}

class MyTrustManager implements X509TrustManager {
	MyTrustManager() { // constructor
		// create/load keystore
	}

	public void checkClientTrusted(X509Certificate chain[], String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate chain[], String authType) throws CertificateException {
		// special handling such as poping dialog boxes
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	

	
}
