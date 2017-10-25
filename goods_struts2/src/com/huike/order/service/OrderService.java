package com.huike.order.service;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huike.order.dao.OrderDao;
import com.huike.order.domain.Order;
import com.huike.page.PageBean;
import com.huike.page.QueryInfo;
import com.huike.page.QueryResult;
import com.huike.user.domain.User;
import com.huike.utils.hibernate.HibernateUtil;

public class OrderService {


	//根据用户id查询我的订单并且带分页
	public PageBean myOrders(User user, QueryInfo queryInfo) throws SQLException {

		if (queryInfo.getPageSize() != 0 && queryInfo.getCurrentPage() > 0) {
			
			OrderDao orderDao = new OrderDao();
			
			QueryResult result = orderDao.myOrders(user, queryInfo);

			PageBean pageBean = new PageBean();
			pageBean.setList(result.getList());
			pageBean.setTotalRecord(result.getTotalRecord());
			pageBean.setPageSize(queryInfo.getPageSize());
			pageBean.setCurrentPage(queryInfo.getCurrentPage());// currentPage

			return pageBean;
		}
		return null;
	}

	public void add(Order order) throws SQLException {
		OrderDao orderDao = new OrderDao();
		orderDao.add(order);
	}

}
