package com.huike.book.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huike.book.domain.Book;
import com.huike.book.service.BookService;
import com.huike.page.PageBean;
import com.huike.page.QueryInfo;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.servlet.BaseServlet;

public class BookServlet extends BaseServlet {

	//select * from t_book limit ?,?
	//select count(*) from t_book 
	//第一个参数: 告诉数据库从结果集的第几条数据开始查询
	//第二个参数: 告诉数据库一次提取多少条数据,
	
	//用户提供的是当前是第几页     第几条数据 = (页数 - 1) * 提取多少条数据
	
	//QueryInfo  封装前台传过来的参数, 用于分页, (分数, 每页显示的条数)
	//QueryResult  从数据库查询出结果, 把两次结果集封装在这个对象中. 
	//pageBean  前台完成分页功能所需要的所有的数据.
	//queryInfo  ---> 程序查询  ----> queryResult ----> pageBean
	
	
	/*
	 * 根据cid查询数据
	 */
	public String findBooksByCid(HttpServletRequest request,HttpServletResponse response) {
		/*
		 * 将前台传递的分类的id以及需要查询的页码封装到queryInfo中
		 */
		//cid currentPage 
		QueryInfo queryInfo = CommonUtils.toBean(request.getParameterMap(), QueryInfo.class);
		BookService service = new BookService();
		PageBean pageBean = service.findBooksByCid(queryInfo);
		setUrl2Bean(request, pageBean);
		return "f:/jsps/book/list.jsp";
	}
	/*
	 * 根据出版社查询书籍
	 */
	public String findBooksByPress(HttpServletRequest request,HttpServletResponse response) throws IOException {
		/*
		 * 
		 */
		
		QueryInfo queryInfo = CommonUtils.toBean(request.getParameterMap(), QueryInfo.class);
		BookService service = new BookService();
		PageBean pageBean = service.findBooksByPress(queryInfo);
		setUrl2Bean(request, pageBean);
		return "f:/jsps/book/list.jsp";
	}
	/*
	 * 根据书名查询书籍
	 */
	public String findBooksByBname(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//bname
		QueryInfo queryInfo = CommonUtils.toBean(request.getParameterMap(), QueryInfo.class);
		BookService service = new BookService();
		PageBean pageBean = service.findBooksByBname(queryInfo);
		setUrl2Bean(request, pageBean);
		return "f:/jsps/book/list.jsp";
	}
	/*
	 * 根据作者查询书籍
	 */
	public String findBooksByAuthor(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//bname
		QueryInfo queryInfo = CommonUtils.toBean(request.getParameterMap(), QueryInfo.class);
		/*
		 * /goods/servlet/BookServlet
			method=findBooksByAuthor&author=xxx
		 */
		BookService service = new BookService();
		PageBean pageBean = service.findBooksByAuthor(queryInfo);
		setUrl2Bean(request, pageBean);
		return "f:/jsps/book/list.jsp";
	}
	
	/*
	 * 根据图书id去查询图书
	 */
	public String queryBookByBid(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String bid = request.getParameter("bid");
			BookService service = new BookService();
			Book book = service.queryBookByBid(bid);
			request.setAttribute("book", book);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "f:/jsps/book/desc.jsp";
	}
	
	/*
	 * 条件组合查询图书, 要支持分页
	 */
	public String queryBookByParamCom(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			// 由于 前台要传入 分页的参数和  bname,press,author
			QueryInfo queryInfo = CommonUtils.toBean(request.getParameterMap(), QueryInfo.class);
			BookService service = new BookService();
			PageBean pageBean = service.queryBookByParamCom(queryInfo);
			setUrl2Bean(request, pageBean);
			return "f:/jsps/book/list.jsp";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * 将查询的url设置到pageBean,包含需要查询的条件信息
	 */
	//给URL添加分页的查询参数
	public void setUrl2Bean(HttpServletRequest request, PageBean pageBean) {
		//request.getQueryString(); 是截取URL后面的参数
		String url = request.getRequestURI() + "?" + request.getQueryString();
		int index = url.indexOf("&currentPage");
		if (index > 0) {
			url = url.substring(0,index);
		}
		pageBean.setUrl(url);
		//URL是/goods/servlet/BookServle？tmethod=findBooksByCid&cid=xxx
		request.setAttribute("pageBean", pageBean);
	}
}
