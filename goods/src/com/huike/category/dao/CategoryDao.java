package com.huike.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.huike.utils.jdbc.JdbcUtils;

public class CategoryDao {

	/*
	 * 查询所有分类信息
	 * 1.查所有的一级分类
	 * mysql> select * from t_category where pid is null;
	 * 2.循环找出一级分类下的二级分类
	 * mysql> select * from t_category where pid = 1;
	 */
	public List<Category> findAll() {
		
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		try {
			//有了泛型就不用强转了
			//所有的一级分类
			List<Category> parents = runner.query("select * from t_category where pid is null", new BeanListHandler<Category>(Category.class));
			for(Category parent : parents) {
				Object[] params = {parent.getCid()};
				//根据一级分类的id, 查询二级分类
				List<Category> child = runner.query("select * from t_category where pid = ?", new BeanListHandler<Category>(Category.class),params);
				parent.setChildren(child);
			}
			return parents;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
