package com.huike.category.controller;

import java.util.List;

import com.huike.category.domain.Category;
import com.huike.category.service.CategoryService;
import com.opensymphony.xwork2.ActionSupport;

public class CategoryAction extends ActionSupport {
	//提示, 不要修改list的名字
	private List<Category> list;  //${list}
	private CategoryService service = new CategoryService();
	public String findAll() {
		//查询数据库中的所有分类信息
		list = service.findAllCategory();
		// 转发到left.jsp显示分类信息
		return "success";
	}
	public List<Category> getList() {
		return list;
	}
	
	/*//查询数据库中的所有分类信息
	CategoryService service = new CategoryService();
	List<Category> list = service.findAllCategory();
	
	//绑定到域对象
	request.setAttribute("list", list); ${list}
	
	//转发到left.jsp显示分类信息
	return "f:/jsps/left.jsp";*/
	
}
