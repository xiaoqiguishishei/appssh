package com.huike.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.huike.order.domain.Order;
import com.huike.order.domain.OrderItem;
import com.huike.page.Expression;
import com.huike.page.QueryInfo;
import com.huike.page.QueryResult;
import com.huike.user.domain.User;
import com.huike.utils.jdbc.JdbcUtils;

public class OrderDao {

	public static void main(String[] args) throws SQLException {
		OrderDao orderDao = new OrderDao();
		User user = new User();
		user.setUid("32DB3700D2564254982BC58B0E4D95BC");
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.setStartIndex(0);
		queryInfo.setPageSize(20);
		QueryResult queryResult = orderDao.findOrdersByUid(user, queryInfo);
		System.out.println(queryResult);
	}

	public QueryResult findOrdersByUid(User user, QueryInfo queryInfo)
			throws SQLException {
		List<Expression> expressions = new ArrayList<Expression>();
		expressions.add(new Expression("uid", "=", user.getUid()));
		QueryResult result = queryByPage(expressions,
				queryInfo.getStartIndex(), queryInfo.getPageSize());
		return result;
	}

	// 这个方法要根据参数动态的拼接生成sql
	public QueryResult queryByPage(List<Expression> expressions,
			int startIndex, int pageSize) throws SQLException {
		// 1.拼接sql
		// 拼接sql的where条件Expression fieldName = bname;expression = '=' fieldValue
		// = 'zhangsan'
		// where bname = ?
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		StringBuilder whereSql = new StringBuilder("where 1=1");
		List<Object> params = new ArrayList<Object>();
		for (Expression expression : expressions) {
			whereSql.append(" and ");
			whereSql.append(expression.getFieldName());
			whereSql.append(" ");
			whereSql.append(expression.getExpression());
			whereSql.append("?");
			// 存储查询条件的值。
			params.add(expression.getFieldValue());
		}

		String sql = "select count(*) from t_order " + whereSql;
		Long count = (Long) runner.query(sql, new ScalarHandler(),
				params.toArray());
		// System.out.println(count);
		// 查询数据
		sql = "select * from t_order " + whereSql + " limit ?,?";
		params.add(startIndex);
		params.add(pageSize);
		List<Order> orders = runner.query(sql, new BeanListHandler(Order.class), params.toArray());

		// 思考：查询订单的同事也要把订单的明细信息查询出来
		for (Order order : orders) {
			List<OrderItem> orderItems = findOrderItemsByOrderId(order.getOid());
			order.setOrderItems(orderItems);
		}

		QueryResult queryResult = new QueryResult();
		queryResult.setList(orders);
		queryResult.setTotalRecord(count);

		return queryResult;
	}

	private List<OrderItem> findOrderItemsByOrderId(String oid)
			throws SQLException {
		String sql = "select * from t_orderitem where oid = ?";
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		List<OrderItem> orderItems = runner.query(sql, new BeanListHandler(OrderItem.class), oid);
		return orderItems;
	}
	
	public void add(Order order) throws SQLException {
		// 保存订单
		String sql = "insert into t_order (oid,ordertime,total,status,address,uid) values (?,?,?,?,?,?)";
		Object[] params = { order.getOid(), order.getOrderTime(),
				order.getTotal(), order.getStatus(), order.getAddress(),
				order.getUser().getUid() };
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql, params);
		// 保存订单明细
		sql = "insert into t_orderitem (orderItemId,quantity,subtotal,bid,bname,currPrice,image_b,oid) values (?,?,?,?,?,?,?,?)";
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			Object[] itemParams = new Object[] { 
					orderItem.getOrderItemId(),
					orderItem.getQuantity(), 
					orderItem.getSubtotal(),
					orderItem.getBook().getBid(),
					orderItem.getBook().getBname(),
					orderItem.getBook().getCurrPrice(),
					orderItem.getBook().getImage_b(), 
					order.getOid() };
			runner.update(sql, itemParams);
		}
	}

}
