package com.huike.test;

import org.apache.commons.dbutils.QueryRunner;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.huike.book.domain.Book;
import com.huike.utils.hibernate.HibernateUtil;
import com.huike.utils.jdbc.JdbcUtils;

public class Test {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
//		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		
		Criteria criteria = session.createCriteria(Book.class);
		criteria.add(Restrictions.eq("category.cid", 
				"5F79D0D246AD4216AC04E9C5FAB3199E"));
		System.out.println(criteria.list());;
		
		transaction.commit();
	}
}
