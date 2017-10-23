package com.huike.web.formbean;

import java.util.ArrayList;
import java.util.List;

//通用的泛型分页bean
public class PageBean<T> {

	private int currentPageIndex = 1;
	
	private int pageCount = 5 ;   //页面显示的数据量
	
	private int sumPage ;  //总页数
	
	private List<T> list = new ArrayList<T>(); 
	
	public PageBean() {
	}
	
	public PageBean(int totalCount,int curentePageIndex,int pageCount) {
		
		if(curentePageIndex < 0)
			currentPageIndex = 1 ;
		//计算总页数
		sumPage = (totalCount + pageCount - 1) /pageCount ;
		
		if(curentePageIndex > sumPage  && sumPage !=0)
			curentePageIndex  = sumPage ;
		
		this.currentPageIndex = curentePageIndex ;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getSumPage() {
		return sumPage;
	}

	public void setSumPage(int sumPage) {
		this.sumPage = sumPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
