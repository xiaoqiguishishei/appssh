package com.huike.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huike.bean.Classes;
import com.huike.dao.ClassesDao;
import com.huike.service.ClassesService;
import com.huike.web.formbean.PageBean;

@Service
public class ClassesServiceImpl implements ClassesService {
	
	@Autowired
	private ClassesDao dao ;

	@Override
	public List<Classes> findAll() {
		return dao.findAll();
	}

	//修改状态
	@Override
	public boolean modify(Classes classes) {
		return dao.update(classes);
	}

	@Override
	public PageBean getPageBean(int currentPageIndex, int pageCount) {
		//查询所有的数据
		int totalCount = dao.getTotalCount() ;
		
		//创建分页对象Pagebean
		PageBean<Classes> page = new PageBean<Classes>(totalCount, currentPageIndex, pageCount) ;
		
		//向page对象的集合属性中插入数据
		//计算页面的首条记录的索引
		int itemIndex = (page.getCurrentPageIndex() -1) * pageCount ;
		
		Map<String,Integer> map = new HashMap<String,Integer>() ; 
		
		map.put("itemIndex", itemIndex) ;
		map.put("pageCount",page.getPageCount()) ;
		
		page.setList(dao.getPageList(map)) ;
		
		
		return page;
	}

	@Override
	public PageBean<Classes> getPageBean(Classes classes, int pageIndex,
			int pageCount) {
		//查询所有的数据
		int totalCount = dao.getTotalCountByCondition(classes) ;
		
		System.out.println("totalCount:" + totalCount);
		
		//创建分页对象Pagebean
		PageBean<Classes> page = new PageBean<Classes>(totalCount, pageIndex, pageCount) ;
		
		
		//计算页面的首条记录的索引
		int itemIndex = (page.getCurrentPageIndex() -1) * pageCount ;
		
		Map<String,Object> map = new HashMap<String,Object>() ; 
		
		map.put("itemIndex", itemIndex) ;
		map.put("pageCount",page.getPageCount()) ;
		map.put("classes", classes) ;
		
		//向page对象的集合属性中插入数据
		List<Classes> list = dao.getPageListByCondition(map) ;
		System.out.println(list.size());
		page.setList(dao.getPageListByCondition(map)) ;
			
		return page;
	}
}
