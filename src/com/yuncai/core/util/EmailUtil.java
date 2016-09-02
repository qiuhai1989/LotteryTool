package com.yuncai.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * 发送邮件工具类
 * @author blackworm
 *
 */
public class EmailUtil {

	private static String auth;
	private static String protocol;
	private static String host;
	private static String username;
	private static String password;
	private static String from;
	
	public static String getAuth() {
		return auth;
	}

	public static void setAuth(String auth) {
		EmailUtil.auth = auth;
	}

	public static String getProtocol() {
		return protocol;
	}

	public static void setProtocol(String protocol) {
		EmailUtil.protocol = protocol;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		EmailUtil.host = host;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		EmailUtil.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		EmailUtil.password = password;
	}
	
	public static String getFrom() {
		return from;
	}

	public static void setFrom(String from) {
		EmailUtil.from = from;
	}

	static{
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void init() throws IOException{
		Properties props = new Properties();
		InputStream is=EmailUtil.class.getResourceAsStream("/email_config.properties");
		props.load(is);
		setAuth(props.getProperty("mail.smtp.auth"));
		setProtocol(props.getProperty("mail.transport.protocol"));
		setHost(props.getProperty("mail.host"));
		setUsername(props.getProperty("username"));
		setPassword(props.getProperty("password"));
		setFrom(props.getProperty("from"));
	}
	
	public static boolean sendEmail(String email,String title,String content,String param){
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", getAuth());
		props.setProperty("mail.transport.protocol", getProtocol());
		props.setProperty("mail.host", getHost());
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getUsername(), getPassword());
			}
		});

		session.setDebug(false);
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(getFrom()));
			msg.setSubject(title);
			msg.setRecipients(RecipientType.TO,InternetAddress.parse(email));
			msg.setContent(content,param);//param:text/html;charset=gbk
			Transport.send(msg);
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
