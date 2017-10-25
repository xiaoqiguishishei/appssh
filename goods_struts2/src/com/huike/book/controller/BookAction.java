package com.huike.book.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.huike.book.domain.Book;
import com.huike.book.service.BookService;
import com.huike.page.PageBean;
import com.huike.page.QueryInfo;
import com.huike.utils.commons.CommonUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BookAction extends ActionSupport implements ModelDriven<QueryInfo> {
	
	private QueryInfo queryInfo = new QueryInfo();

	//向页面传递参数
	private PageBean pageBean;
	private Book book;
	@Override
	
	public QueryInfo getModel() {
		return queryInfo;
	}
	public Book getBook() {
		return book;
	}
	public PageBean getPageBean() {
		return pageBean;
	}


	public String findBooksByCid() {
		BookService service = new BookService();
		pageBean = service.findBooksByCid(queryInfo);
		setUrl2Bean(ServletActionContext.getRequest(), pageBean);
		return SUCCESS;
	}

	/**
	 * 根据图书id去查询图书
	 * @return
	 */

	public String queryBookByBid() {
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String bid = request.getParameter("bid");
			BookService service = new BookService();
			Book book = service.queryBookByBid(bid);
			request.setAttribute("book", book);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
//		return "f:/jsps/book/desc.jsp";
	}
	/**
	 * 根据书名查询书籍
	 * @return
	 */
	public String findBooksByBname() {
		
		//bname
		BookService service = new BookService();
		pageBean = service.findBooksByBname(queryInfo);
		setUrl2Bean(ServletActionContext.getRequest(), pageBean);
		return SUCCESS;
//		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 根据作者查询书籍
	 * @return
	 */
	public String findBooksByAuthor() {
		//bname
		BookService service = new BookService();
		pageBean = service.findBooksByAuthor(queryInfo);
		setUrl2Bean(ServletActionContext.getRequest(), pageBean);
		return SUCCESS;
//		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 根据出版社查询书籍
	 * @return
	 */
	public String findBooksByPress() {
		
		BookService service = new BookService();
		pageBean = service.findBooksByPress(queryInfo);
		setUrl2Bean(ServletActionContext.getRequest(), pageBean);
		return SUCCESS;
//		return "f:/jsps/book/list.jsp";
	}
	
	/**
	 * 条件组合查询图书, 要支持分页
	 * @return
	 */
	public String queryBookByParamCom() throws Exception {
		try {
			// 由于 前台要传入 分页的参数和  bname,press,author
			BookService service = new BookService();
			pageBean = service.queryBookByParamCom(queryInfo);
			setUrl2Bean(ServletActionContext.getRequest(), pageBean);
			return SUCCESS;
//			return "f:/jsps/book/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void setUrl2Bean(HttpServletRequest request, PageBean pageBean) {
		//request.getQueryString(); 是截取URL后面的参数
		String url = request.getRequestURI() + "?" + request.getQueryString();
		int index = url.indexOf("&currentPage");
		if (index > 0) {
			url = url.substring(0,index);
		}
		pageBean.setUrl(url);
	}

	

}
