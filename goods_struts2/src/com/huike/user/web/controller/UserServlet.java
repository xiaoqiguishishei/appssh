package com.huike.user.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huike.user.domain.User;
import com.huike.user.service.UserService;
import com.huike.user.web.formbean.LoginFormBean;
import com.huike.user.web.formbean.RegistFormBean;
import com.huike.user.web.formbean.UpdateFormBean;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.servlet.BaseServlet;

public class UserServlet extends BaseServlet {
	
	/*
	 * 往后台发起请求的方式(与服务端做交互)
	 * 1.表单
	 * 2.超链接
	 * 3.直接在地址栏输入
	 * 4.ajax
	 */
	
	/*
	 * 注册时会调用此方法
	 */
	//servlet/UserServlet?method=regist
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		/*
		 * formbean: 封装前台提交的数据, 做校验UserFormBean
		 */
		Map<String, String[]> map = request.getParameterMap();
		RegistFormBean formBean = CommonUtils.toBean(map, RegistFormBean.class);
		//将formBean保存到域对象
		request.setAttribute("formBean", formBean);
		
		UserService service = new UserService();
		if(!formBean.validate()) {
			//没有校验通过, 转回到注册页
			
			//以前写到doGet里面不会有异常
			//request.getRequestDispatcher("/jsps/user/regist.jsp").forward(request, response);
			
			return "f:/jsps/user/regist.jsp";//继承的父类BaseServlet的方法
		}
		
		//校验用户名是否存在
		boolean isSuccess = service.validateLoginname(formBean.getLoginname());
		if(!isSuccess) {
			formBean.getErrors().put("loginname","用户名已存在");
			return "f:/jsps/user/regist.jsp";
		}
		
		//验证码是否正确
		String code = formBean.getVerifycode();
		String vCode = (String) request.getSession().getAttribute("vCode");
		if(!code.equalsIgnoreCase(vCode)) {
			formBean.getErrors().put("verifycode", "验证码错误");
			return "f:/jsps/user/regist.jsp";
		}
		
		
		//1.封装请求中的数据
		User user = CommonUtils.toBean(map, User.class);
		
		//2.校验数据 (前台校验js, 后台校验java) 前台可以不做校验, 后台一定要做校验, 最好做前台校验
		//如果前台做了校验, 后台也要做校验
		
		
		//3.存入到数据库
		service.regist(user);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "注册成功,请到邮箱激活");
		
		//4.跳转到消息页, 注册成功
		return "f:/jsps/msg.jsp";
		
	}	
	/*
	 * 激活用户
	 */
	public String activation(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("激活"+ request.getParameter("activationCode"));
		
		String activationCode = request.getParameter("activationCode");
		UserService service = new UserService();
		service.activationUser(activationCode);
		return null;
	}

	/*
	 * 登录功能
	 */
	public String login(HttpServletRequest request,HttpServletResponse response) {
		/*
		 * 1.获取表单中的数据,后台校验,formbean
		 */
		LoginFormBean formBean = CommonUtils.toBean(request.getParameterMap(), LoginFormBean.class);
		request.setAttribute("formBean", formBean);
		if (!formBean.validate()) {
			return "f:/jsps/user/login.jsp";
		}
		/*
		 * 2.登录,校验用户名与密码是否匹配
		 */
		UserService service = new UserService();
		User user = service.login(formBean.getLoginname(),formBean.getLoginpass());
		if (user == null) {
			formBean.getErrors().put("action", "用户名和密码不匹配");
			return "f:/jsps/user/login.jsp";
		}
		
		//查看此用户是否激活 0未激活  1激活
		if (user.getStatus() == 0) {
			formBean.getErrors().put("action", "账户未激活,请到邮箱激活账户");
			return "f:/jsps/user/login.jsp";
		}
		/*
		 * 3.将查询出的用户存储到session
		 */
		request.getSession().setAttribute("user", user);
		
		/*
		 * 4. 重定向到首页
		 */
		return "r:/index.jsp";
	}
	
	/*
	 * 更新密码
	 */
	public String updateLoginpass(HttpServletRequest request,HttpServletResponse response) {
		/*
		 * 1.后台校验 校验是否为空, 以及格式
		 */
		UpdateFormBean formBean = CommonUtils.toBean(request.getParameterMap(), UpdateFormBean.class);
		request.setAttribute("formBean", formBean);
		if(!formBean.validate()) {
			return "f:/jsps/user/pwd.jsp";
		}
		
		/*
		 * 2.判断原密码是否输入正确
		 */
		String loginpass = formBean.getLoginpass();
		//真正的原密码,既然已经登录了, 说明密码就在session里面
		User user = (User) request.getSession().getAttribute("user");
		//如果30分钟session被销毁, user就为空
		if(user == null) {
			return "f:/jsps/user/pwd.jsp";
		}
		String oldpass = user.getLoginpass();
		if(!loginpass.equals(oldpass)) {
			System.out.println("原密码输入错误");
		}
		
		/*
		 * 3.更新数据库中的密码, 改为新密码
		 */
		String newPass = formBean.getNewpass();
		UserService service = new UserService();
		service.updateLoginpass(newPass, user.getUid());
		
		/*
		 * 4.重定向登录页, 重新登录
		 */
		request.setAttribute("code", "success");
		request.setAttribute("msg", "密码修改成功");
		return "f:/jsps/msg.jsp";
	}
	
	//ajax请求,判断原密码是否正确
	public String validateLoginpass(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String loginpass = request.getParameter("loginpass");
		
		User user = (User)request.getSession().getAttribute("user");
		if (user == null) {
			return "f:/jsp/user/login.jsp";
		}
		String oldpass = user.getLoginpass();
		
		response.getWriter().write(loginpass.equals(oldpass) + "");
		return null;
	}
	
	/*
	 * 退出
	 */
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.移除session中存储的user信息
		request.getSession().removeAttribute("user");
		
		//2.销毁session, 销毁就没了
		//request.getSession().invalidate();
		return "f:/jsps/user/login.jsp";
	}
	
	/*
	 * ajax请求校验用户名是否存在
	 */
	public String validateLoginname(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//js里面,  对象:{} 数组:[] 字符串:"" 数字:1 boolean:true/false   {"title","金刚狼"}
		
		String loginname = request.getParameter("loginname");
		UserService service = new UserService();
		boolean flag = service.validateLoginname(loginname);
		System.out.println(flag);
		response.getWriter().write(flag + ""); //write必须是字符串, 所以加"", 使其变成字符串
		return null;
	}
	
	/*
	 * ajax请求校验验证码
	 */
	public String validateVerifycode(HttpServletRequest request,HttpServletResponse response)throws IOException  {
		String verifycode = request.getParameter("verifycode");
		String vCode = (String)request.getSession().getAttribute("vCode");
		boolean flag = verifycode.equalsIgnoreCase(vCode);
		response.getWriter().write(flag + "");
		return null;
	}
	
	
}
