package com.hyt.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	/**
	 * 外网邮件发送
	 * to
	 * code
	 */
	public static void sendMail(String to,String code) {
//		session对象
		// 创建一个程序与邮件服务器会话对象 Session
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "SMTP");
		properties.setProperty("mail.host", "smtp.163.com");
		properties.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				//用户名（不要后缀）和授权码
				return new PasswordAuthentication("yongtaohu39","Kalihyt38");
			}
		});
//		message对象
		Message message = new MimeMessage(session);
//		设置发件人
		try {
			message.setFrom(new InternetAddress("yongtaohu39@163.com"));
//			设置收件人
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
//			设置主题
			message.setSubject("来自购物天堂的激活邮件");
//			设置内容
			String url= "http://139.199.83.47:80/WEB20/activate.user?code="+code;
			message.setContent("<h1>来自购物天堂的激活邮件！请点击以下链接</h1><h3>"
					+ "<a href='"+url+"'>"+url+"</a></h3>","text/html;charset=UTF-8");
			Transport.send(message);
			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
