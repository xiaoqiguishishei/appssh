package com.huike.service;

import java.util.List;

import com.huike.bean.Classes;
import com.huike.web.formbean.PageBean;

public interface ClassesService {

	public List<Classes> findAll() ;

	public boolean modify(Classes classes);
	
	//分页
	public PageBean getPageBean(int currentPageIndex,int pageCount) ;

	public PageBean<Classes> getPageBean(Classes classes, int pageIndex, int pageCount);
}
