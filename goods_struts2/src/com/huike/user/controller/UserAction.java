package com.huike.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.huike.user.domain.User;
import com.huike.user.service.UserService;
import com.huike.user.web.formbean.LoginFormBean;
import com.huike.user.web.formbean.RegistFormBean;
import com.huike.user.web.formbean.UpdateFormBean;
import com.huike.utils.commons.CommonUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User> {

	//以前把数据放到request域当中, 现在把数据放到值栈当中
	//接收数据
	private User user = new User();
	private String verifyCode;
	
	//向页面传递数据
	private Map<String, String> error = new HashMap<String, String>();
	private String code;
	private String msg;
	private UserService service = new UserService();
	
	@Override
	public User getModel() {
		return user;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public Map<String, String> getError() {
		return error;
	}
	public User getUser() {
		return user;
	}
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 登录的方法
	 * @return
	 */
	public String login() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//1.获取表单中的数据,后台校验,formbean
		//2.登录,校验用户名与密码是否匹配
		user = service.login(user.getLoginname(), user.getLoginpass());
		if (user == null) {
			//request.setAttribute("action", "用户名和密码不匹配");
			error.put("action", "用户名和密码不匹配");
			return "login";
//			return "f:/jsps/user/login.jsp";
		}
		
		//查看此用户是否激活 0未激活  1激活
		if (user.getStatus() == 0) {
			error.put("action", "账户未激活,请到邮箱激活账户");
			return "login";
//			return "f:/jsps/user/login.jsp";
		}
		
		//验证码校验
		String code = this.getVerifyCode();
		String vCode = (String) request.getSession().getAttribute("vCode");
		if(!code.equalsIgnoreCase(vCode)) {
			error.put("verifycode", "验证码错误");
			return "login";
		}

		//3.将查询出的用户存储到session
		request.getSession().setAttribute("user", user);
		
		
		
		//4. 重定向到首页
//		return "r:/index.jsp";
		return SUCCESS;
	}

	/**
	 * 注册的方法
	 * @return
	 */
	public String register() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//校验用户名是否存在
		boolean isSuccess = service.validateLoginname(user.getLoginname());
		if(!isSuccess) {
			error.put("loginname","用户名已存在");
//			return "f:/jsps/user/regist.jsp";
			return "register";
		}
		
		//验证码校验
		String code = request.getParameter("verifycode");
		String vCode = (String) request.getSession().getAttribute("vCode");
		if(!code.equalsIgnoreCase(vCode)) {
			error.put("verifycode", "验证码的错误");
			return "register";
		}
		
		
		//1.封装请求中的数据
		//2.校验数据 (前台校验js, 后台校验java) 前台可以不做校验, 后台一定要做校验, 最好做前台校验
		//如果前台做了校验, 后台也要做校验
		
		//3.存入到数据库
		service.regist(user);
		code = "success";
		msg = "注册成功,请到邮箱激活";
//		request.setAttribute("code", "success");
//		request.setAttribute("msg", "注册成功,请到邮箱激活");
		
		//4.跳转到消息页, 注册成功
//		return "f:/jsps/msg.jsp";
		return "msg";
	}

	
	/*
	 * 更新密码
	 */
	public String updateLoginpass() {
		/*
		 * 1.后台校验 校验是否为空, 以及格式
		 */
		HttpServletRequest request = ServletActionContext.getRequest();
		UpdateFormBean formBean = CommonUtils.toBean(request.getParameterMap(), UpdateFormBean.class);
		request.setAttribute("formBean", formBean);
		if(!formBean.validate()) {
			return "pwd";
//			return "f:/jsps/user/pwd.jsp";
		}
		
		/*
		 * 2.判断原密码是否输入正确
		 */
		String loginpass = formBean.getLoginpass();
		//真正的原密码,既然已经登录了, 说明密码就在session里面
		User user = (User) request.getSession().getAttribute("user");
		//如果30分钟session被销毁, user就为空
		if(user == null) {
			return "pwd";
//			return "f:/jsps/user/pwd.jsp";
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
		code = "success";
		msg = "密码修改成功";
		request.setAttribute("code", code);
		request.setAttribute("msg", msg);
		return "msg";
//		return "f:/jsps/msg.jsp";
	}
	
	//ajax请求,判断原密码是否正确
	public String validateLoginpass() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String loginpass = request.getParameter("loginpass");
		
		User user = (User)request.getSession().getAttribute("user");
		if (user == null) {
			return SUCCESS;
//			return "f:/jsp/user/login.jsp";
		}
		String oldpass = user.getLoginpass();
		
		response.getWriter().write(loginpass.equals(oldpass) + "");
		return null;
	}
	
	/*
	 * 退出
	 */
	public String logout() {
		//1.移除session中存储的user信息
		ServletActionContext.getRequest().getSession().removeAttribute("user");
		
		//2.销毁session, 销毁就没了
		//request.getSession().invalidate();
		return SUCCESS;
//		return "f:/jsps/user/login.jsp";
	}
	
	/**
	 * ajax请求校验用户名是否存在
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String validateLoginname() throws IOException {
		//js里面,  对象:{} 数组:[] 字符串:"" 数字:1 boolean:true/false   {"title","金刚狼"}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String loginname = request.getParameter("loginname");
		UserService service = new UserService();
		boolean flag = service.validateLoginname(loginname);
		System.out.println(flag);
		response.getWriter().write(flag + ""); //write必须是字符串, 所以加"", 使其变成字符串
		return null;
	}
	
	
	/**
	 * ajax请求校验验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String validateVerifycode() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String verifycode = request.getParameter("verifycode");
		String vCode = (String)request.getSession().getAttribute("vCode");
		boolean flag = verifycode.equalsIgnoreCase(vCode);
		response.getWriter().write(flag + "");
		return null;
	}
}
