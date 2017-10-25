package com.huike.category.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huike.category.domain.Category;
import com.huike.utils.hibernate.HibernateUtil;
import com.huike.utils.jdbc.JdbcUtils;

public class CategoryDao {
/*
 * 先把父标题查询出来, 然后再查询子标题, 查询每一个父标题的子标题, 
 * 对于Hibernate来讲我只需要查询父标题就可以了, 因为配置的时候我给parent配置把父标题相关系的子标题把它都放在这个集合中了
 * 	Hibernate会自动根据你的配置去初始化这个Category, 把children给加载出来
		<set name="children" inverse="true" lazy="false">
            <key>
                <column name="pid" length="32" />
            </key>
            <one-to-many class="com.huike.category.domain.Category" />
        </set>
 * 	hibernate帮我们做了:
 * 		for(Category parent : parents) {
			Object[] params = {parent.getCid()};
			//根据一级分类的id, 查询二级分类
			List<Category> child = runner.query("select * from t_category where pid = ?", new BeanListHandler<Category>(Category.class),params);
//			parent.setChildren(child);
		}
 */
	/*
	 * 查询所有分类信息
	 * 1.查所有的一级分类
	 * mysql> select * from t_category where pid is null;
	 * 2.循环找出一级分类下的二级分类
	 * mysql> select * from t_category where pid = 1;
	 */
	public List<Category> findAll1() {
		
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		try {
			//有了泛型就不用强转了
			//所有的一级分类
			List<Category> parents = runner.query("select * from t_category where pid is null", 
					new BeanListHandler<Category>(Category.class));
			
			for(Category parent : parents) {
				Object[] params = {parent.getCid()};
				//根据一级分类的id, 查询二级分类
				List<Category> child = runner.query("select * from t_category where pid = ?", 
						new BeanListHandler<Category>(Category.class),params);
//				parent.setChildren(child);
			}
			return parents;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Category> findAll() {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		
		String hql = "from Category c where c.parent is null";
		List<Category> categories = session.createQuery(hql).list();
		//不用查询子级 , 因为给Hibernate配置了关系, 它自己就会去查
		transaction.commit();
		session.close();
		return categories;
	}
}
