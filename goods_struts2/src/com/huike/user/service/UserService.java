package com.huike.user.service;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Session;

import com.huike.user.dao.UserDao;
import com.huike.user.domain.User;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.mail.Mail;
import com.huike.utils.mail.MailUtils;

public class UserService {
	private UserDao dao = new UserDao();
	/*
	 * 注册功能
	 */
	public void regist(User user) {
		
		//1. 补全数据  uid 激活状态 激活码
		user.setUid(CommonUtils.uuid());
		user.setStatus(0); 
		// 激活状态: 0(未激活)他没有在邮箱里点我给他的链接,他一注册好我不能让他立即登录的 , 
		//		  1(激活)他得到邮箱点这个激活邮件, 这才算激活 
		//他不点,状态就是0, 他点状态我就改为1--->验证一下他的连接方式对不对
		user.setActivationCode(CommonUtils.uuid());
		UserDao dao = new UserDao();
		dao.addUser(user);
		
		//发送激活邮件 <a href="xxxServlet?--uuid--">激活</a>
		sendEmail(user);
	}
	
	public void activationUser(String code) {
		dao.updateStatusByActivationCode(code);
	}
	
	public void sendEmail(User user) {
		/*
		 * 加载资源文件
		 */
		
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("email_template.properties");
			Properties properties = new Properties();
			properties.load(is);
			
			String from = properties.getProperty("from");
			String username = properties.getProperty("username");

			String host= properties.getProperty("host");
			String subject= properties.getProperty("subject");
			String content= properties.getProperty("content");
			String password= properties.getProperty("password");
			
			//1.连接邮件服务器
			Session session =  MailUtils.createSession(host, username, password);
			
			//填充content中{0}占位符
										//(我要给谁填, 要填什么东西)
			content = MessageFormat.format(content, user.getActivationCode());
//{0} 这里是占位符, 这里的0, 是等你去填充东西的, 填充具体的激活码, 正常的占位格式是{0}{1}{2}...
//<a href="http://localhost:8080/goods/servlet/UserServlet?method=activation&activationCode={0}">这里</a>完成激活。			
			Mail mail = new Mail(from,user.getEmail(),subject,content);
			MailUtils.send(session, mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validateLoginname(String loginname) {
		 return dao.findLoginName(loginname);
	}

	public User login(String loginname, String loginpass) {
		User user = dao.findUser(loginname,loginpass);
		return user;
	}

	public void updateLoginpass(String newPass, String uid) {
		dao.updateLoginpass(newPass, uid);
	}
}