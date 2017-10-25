package com.huike.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huike.utils.servlet.BaseServlet;

public class DemoServlet extends BaseServlet {

	//DemoServlet?method=login
	public String login(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("------------------");
		return null;
	}
	//DemoServlet?method=regist
	public String regist(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("------------------");
		return null;
	}
}

