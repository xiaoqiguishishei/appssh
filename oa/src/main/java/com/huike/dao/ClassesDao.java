package com.huike.dao;

import java.util.List;
import java.util.Map;

import com.huike.bean.Classes;

public interface ClassesDao {

	public List<Classes> findAll() ;

	public boolean update(Classes classes);

	public int getTotalCount();

	public List<Classes> getPageList(Map<String,Integer> map);

	public int getTotalCountByCondition(Classes classes);
	
	public List<Classes> getPageListByCondition(Map<String,Object> map);
}
