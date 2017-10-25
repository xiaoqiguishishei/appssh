package com.huike.utils;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.junit.Test;

import com.huike.utils.mail.Mail;
import com.huike.utils.mail.MailUtils;

public class MailTest {

	@Test
	public void sendEmail() throws Exception {
		//1. 连接到邮件服务器163, 传入163邮箱的邮件服务器地址, 邮箱账号, 授权码(不是邮箱登录密码)
		//可以连接163服务器							 ( host--服务器, username, password)
		Session session = MailUtils.createSession("smtp.163.com", "hqun655@163.com", "hq613523");
		
		
		//2. 创建邮件对象
		Mail mail = new Mail("hqun655@163.com","hqun655@163.com","测试","你已经成功注册,请点<a href='http://www.baidu.com'>这里</a>激活");
		
		
		//3. 发送邮件
		MailUtils.send(session, mail);
	}
}
