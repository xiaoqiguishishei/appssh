package com.huike.category.service;

import java.util.List;

import com.huike.category.domain.Category;
import com.huike.category.dao.CategoryDao;

public class CategoryService {
	private CategoryDao dao = new CategoryDao();
	public List<Category> findAllCategory() {
		
		return dao.findAll();
	}
}
