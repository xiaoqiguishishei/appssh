package com.huike.category.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huike.category.domain.Category;
import com.huike.category.service.CategoryService;
import com.huike.utils.servlet.BaseServlet;

public class CategoryServlet extends BaseServlet {

	public String findAll(HttpServletRequest request, HttpServletResponse response) {
			
		//查询数据库中的所有分类信息
		CategoryService service = new CategoryService();
		List<Category> list = service.findAllCategory();
		
		//绑定到域对象
		request.setAttribute("list", list);
		
		//转发到left.jsp显示分类信息
		return "f:/jsps/left.jsp";
	}
}
