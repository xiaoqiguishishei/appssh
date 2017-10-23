package com.huike.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huike.bean.Admin;
import com.huike.bean.Student;
import com.huike.service.AdminService;
import com.huike.service.StudentService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AdminService as ;
	
	@Autowired
	private StudentService  stuService  ;
	
	@RequestMapping("/login")
	public String login(String username ,String password,String role,HttpServletRequest request){
		//根据角色转向相应的页面
		if("admin".equals(role)){
			//管理员登录
			Admin admin = as.login(new Admin(username,password)) ;
			
			if(admin == null){
				request.setAttribute("error", "用户名或者密码错误") ;
				return "/login/login" ;
			}else{
				//登录成功
				//将管理员放入session
				request.getSession().setAttribute("admin", admin) ;
				//转向查询所有班级的controller
				return "redirect:/admin/page?currentPageIndex=1&pageCount=5" ;
			}
		}else{
			Student stu = stuService.login(new Student(username,password)) ;
			if(stu == null){
				request.setAttribute("error", "用户名或者密码错误") ;
				return "login/login" ;
			}else{
				//登录成功
				request.getSession().setAttribute("student", stu) ;
				
				return "student/index" ;
			}
		}
	}
}
