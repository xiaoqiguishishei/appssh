package com.huike.book.service;


import java.sql.SQLException;

import com.huike.book.dao.BookDao;
import com.huike.book.domain.Book;
import com.huike.page.PageBean;
import com.huike.page.QueryInfo;
import com.huike.page.QueryResult;

public class BookService {

	public PageBean findBooksByCid(QueryInfo queryInfo) {
		
		if(queryInfo.getPageSize() != 0 && queryInfo.getCurrentPage() > 0) {
			
			BookDao dao = new BookDao();
			QueryResult result = dao.findBooksByCid(queryInfo);
			// list total
			
			/*
			 * 根据queryinfo和queryresult封装pagebean
			 */
			PageBean pageBean = new PageBean();
			pageBean.setList(result.getList());
			pageBean.setTotalRecord(result.getTotalRecord());
			pageBean.setPageSize(queryInfo.getPageSize());
			pageBean.setCurrentPage(queryInfo.getCurrentPage()); //currentPage  前台传过来的参数
			
			return pageBean;
		}
		return null;
		
	}
	
	//根据书名寻找
	public PageBean findBooksByBname(QueryInfo queryInfo) {
		BookDao dao = new BookDao();
		QueryResult result = dao.findBooksByBname(queryInfo);
		
		/*
		 * 根据queryinfo和queryresult封装pagebean
		 */
		PageBean pageBean = new PageBean();
		pageBean.setList(result.getList());
		pageBean.setTotalRecord(result.getTotalRecord());
		pageBean.setPageSize(queryInfo.getPageSize());
		pageBean.setCurrentPage(queryInfo.getCurrentPage());;
		return pageBean;
	}

	public PageBean findBooksByPress(QueryInfo queryInfo) {
		
		BookDao dao = new BookDao();
		QueryResult result = dao.findBooksByPress(queryInfo);
		
		/*
		 * 根据queryinfo和queryresult封装pagebean
		 */
		PageBean pageBean = new PageBean();
		pageBean.setList(result.getList());
		pageBean.setTotalRecord(result.getTotalRecord());
		pageBean.setPageSize(queryInfo.getPageSize());
		pageBean.setCurrentPage(queryInfo.getCurrentPage());;
		return pageBean;
	}

	public PageBean findBooksByAuthor(QueryInfo queryInfo) {
		BookDao dao = new BookDao();
		QueryResult result = dao.findBooksByAuthor(queryInfo);
		
		/*
		 * 根据queryinfo和queryresult封装pagebean
		 */
		PageBean pageBean = new PageBean();
		pageBean.setList(result.getList());
		pageBean.setTotalRecord(result.getTotalRecord());
		pageBean.setPageSize(queryInfo.getPageSize());
		pageBean.setCurrentPage(queryInfo.getCurrentPage());;
		return pageBean;
	}

	public Book queryBookByBid(String bid) throws SQLException {
		if(bid != null && !bid.equals("")) {
			BookDao dao = new BookDao();
			return dao.queryBookByBid(bid);
		}
		return null;
	}

	public PageBean queryBookByParamCom(QueryInfo queryInfo) throws Exception {

		if(queryInfo.getPageSize() > 0 && queryInfo.getCurrentPage() > 0) {
			//来计算startIndex
			queryInfo.setStartIndex((queryInfo.getCurrentPage()-1)* queryInfo.getPageSize());
		
			BookDao dao = new BookDao();
			QueryResult result = dao.queryBookByParamCom(queryInfo);
			
			PageBean pageBean = new PageBean();
			pageBean.setList(result.getList());
			pageBean.setTotalRecord(result.getTotalRecord());
			pageBean.setPageSize(queryInfo.getPageSize());
			pageBean.setCurrentPage(queryInfo.getCurrentPage());;
			return pageBean;
		}
		return null;
	}

}
