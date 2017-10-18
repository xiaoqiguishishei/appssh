package com.huike.utils.servlet;


import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 			BaseServlet不用了直接接受请求
 * 			BaseServlet用来作为其它Servlet的父类
 * 			
 * 			xxServlet?username=xxx&password=xxx&method=login
 * 			LoginServlet extends BaseServlet {
 * 
 * 				service(HttpServletRequest request, HttpServletResponse response) {
 * 					login(request, response);
 * 				}
 * 
 * 				login(HttpServletRequest request, HttpServletResponse response){
 * 					
 * 				}
 * 
 * 				regist(HttpServletRequest request, HttpServletResponse response){
 * 					
 * 				}
 * 			}
 *         	一个类多个请求处理方法，每个请求处理方法的原型与service相同！ 原型 = 返回值类型 + 方法名称 + 参数列表
 */
@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {
	
	/*
	 * LoginServlet  extends  HttpServlet
	 * 
	 * HttpServlet
	 * 	service
	 * 		判断了请求的类型, 如果是post请求, 调用doPost, 如果get请求, 就调用doGet方法
	 * 	doGet
	 * 	doPost
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");//处理响应编码
		
		/**
		 * 1. 获取method参数，它是用户想调用的方法 2. 把方法名称变成Method类的实例对象 3. 通过invoke()来调用这个方法
		 * 
		 * xxServlet?username=xxx&password=xxx&method=xxx
		 * 
		 */
		String methodName = request.getParameter("method");//method="login"
		Method method = null;
		
		/**
		 * 2. 通过方法名称获取Method对象
		 */
		try {
			//login(HttpServletRequest req, HttpServletResponse res);
			//获取当前类字节码文件的方法, 方法名为login的方法, 并且方法的参数必须是HttpServletRequest和HttpResponseServlet
			//拿到了当前类里的一个方法, 这个方法名必须叫methodName,方法的参数必须有两个,而且类型是....
			method = this.getClass().getMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
		} catch (Exception e) {
			throw new RuntimeException("您要调用的方法：" + methodName + "它不存在！", e);
		}
		
		/**
		 * 3. 通过method对象来调用它(login)  login()
		 */
		try {
			//this.method(request,response)  this.login(request,response)
			String result = (String)method.invoke(this, request, response);
			if(result != null && !result.trim().isEmpty()) {//如果请求处理方法返回不为空
				int index = result.indexOf(":");//获取第一个冒号的位置
				if(index == -1) {//如果没有冒号，使用转发
					request.getRequestDispatcher(result).forward(request, response);
				} else {//如果存在冒号
					String start = result.substring(0, index);//分割出前缀
					String path = result.substring(index + 1);//分割出路径
					if(start.equals("f")) {//前缀为f表示转发
						request.getRequestDispatcher(path).forward(request, response);
					} else if(start.equals("r")) {//前缀为r表示重定向
						response.sendRedirect(request.getContextPath() + path);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
