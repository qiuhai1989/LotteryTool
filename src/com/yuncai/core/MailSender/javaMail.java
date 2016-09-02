package com.yuncai.core.MailSender;
import java.util.Date; 
import java.util.Properties; 
import javax.mail.Session; 
import javax.mail.Authenticator; 
import javax.mail.PasswordAuthentication; 
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.InternetAddress; 
import javax.mail.Transport; 
public class javaMail { 
     private Properties properties; 
     private Session mailSession; 
     private MimeMessage mailMessage; 
     private Transport trans; 
     public javaMail() { 
     } 
     public void sendMail() { 
         try { 
             properties = new Properties(); 
             //设置邮件服务器 
             properties.put("mail.smtp.host", "smtp.exmail.qq.com"); 
             //验证 
             properties.put("mail.smtp.auth", "true"); 
             //根据属性新建一个邮件会话 
             mailSession = Session.getInstance(properties, 
                                               new Authenticator() { 
                 public PasswordAuthentication getPasswordAuthentication() { 
                     return new PasswordAuthentication("task@6636.com", 
                         "doing123"); 
                 } 
             }); 
             mailSession.setDebug(true); 
             //建立消息对象 
             mailMessage = new MimeMessage(mailSession); 
             //发件人 
             mailMessage.setFrom(new InternetAddress("task@6636.com")); 
             //收件人 
             mailMessage.setRecipient(MimeMessage.RecipientType.TO, 
                                 new InternetAddress("luomin@6636.com")); 
             //主题 
             mailMessage.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网"); 
             //内容 
             mailMessage.setText("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站=="); 
             //发信时间 
             mailMessage.setSentDate(new Date()); 
             //存储信息 
             mailMessage.saveChanges(); 
             // 
             trans = mailSession.getTransport("smtp"); 
             //发送 
             trans.send(mailMessage); 
         } catch (Exception e) { 
             e.printStackTrace(); 
         } finally { 
         } 
     } 
     
     /**
      * 
      * @param serverHost  服务名称
      * @param userName    用户名
      * @param passwrod     密码
      * @param fromAddress  发件人
      * @param toAddress    收件人
      * @param subject       标题
      * @param text         正文
      */
     public void sendMail(String serverHost,final String userName,final String passwrod,String fromAddress,String toAddress,String subject,String text){
    	 try { 
             properties = new Properties(); 
             //设置邮件服务器 
             properties.put("mail.smtp.host", serverHost); 
             //验证 
             properties.put("mail.smtp.auth", "true"); 
             //根据属性新建一个邮件会话 
             mailSession = Session.getInstance(properties, new Authenticator() { 
                 public PasswordAuthentication getPasswordAuthentication() { 
                    return new PasswordAuthentication(userName, passwrod); 
                 } 
             }); 
             mailSession.setDebug(true); 
             //建立消息对象 
             mailMessage = new MimeMessage(mailSession); 
             //发件人 
             mailMessage.setFrom(new InternetAddress(fromAddress)); 
             //收件人 
             mailMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toAddress)); 
             //主题 
             mailMessage.setSubject(subject); 
             //内容 
             mailMessage.setText(text); 
             //发信时间 
             mailMessage.setSentDate(new Date()); 
             //存储信息 
             mailMessage.saveChanges(); 
             // 
             trans = mailSession.getTransport("smtp"); 
             //发送 
             trans.send(mailMessage); 
         } catch (Exception e) { 
             e.printStackTrace(); 
         } finally { 
         } 
     }
     
     public static void main(String[] args) {
    	 javaMail mail=new javaMail();
    	 mail.sendMail();
	}
} 

