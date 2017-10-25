package com.huike.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import com.huike.book.domain.Book;
import com.huike.cart.domain.CartItem;
import com.huike.order.domain.Order;
import com.huike.order.domain.OrderItem;
import com.huike.page.Expression;
import com.huike.page.PageBean;
import com.huike.page.QueryInfo;
import com.huike.page.QueryResult;
import com.huike.user.domain.User;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.hibernate.HibernateUtil;
import com.huike.utils.jdbc.JdbcUtils;

public class OrderDao {


	public QueryResult myOrders(User user, QueryInfo queryInfo) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		
		String hql = "from Order where uid = ?";
		Query query = session.createQuery(hql).setParameter(0, user.getUid());
		Integer count = query.list().size();	//查询图书的数量
		query.setMaxResults(queryInfo.getPageSize());
		query.setFirstResult(queryInfo.getStartIndex());
		List<Order> orders = query.list();
		QueryResult queryResult = new QueryResult();
		queryResult.setList(orders);
		queryResult.setTotalRecord(count);;
		//查询数据
		
		transaction.commit();
		session.close();
		return queryResult;
	}
	
	public void add(Order order) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		// 保存订单
		Order order1 = new Order();
		order1.setOid(order.getOid());
		order1.setOrderTime(order.getOrderTime());
		order1.setTotal(order.getTotal());
		order1.setStatus(order.getStatus());
		order1.setAddress(order.getAddress());
		order1.setUser(order.getUser());
		order1.setOrderItems(order.getOrderItems());
		session.save(order1);
		transaction.commit();
		session.close();
	}
}
