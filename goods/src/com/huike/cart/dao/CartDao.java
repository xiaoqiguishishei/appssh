package com.huike.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.huike.book.domain.Book;
import com.huike.cart.domain.CartItem;
import com.huike.user.domain.User;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.jdbc.JdbcUtils;

public class CartDao {

	//根据用户的uid查询购物车明细数据
	//select * from t_cartitem where uid = '4DE7E4D829A54D4FAB150B7451407198'
	//这个查询只能查询购物车明细对象, 不能一次查询出和购物车明细对象关联的book对象和用户对象;
	//需要进行关联查询 需要t_cartitem 和 t_book 这个表进行关联查询
	//select * from t_cartitem join t_book  查询结果是两个表的笛卡尔积数据
	//加上过滤条件, 就可以查询 购物车的数据, 购物车中的图书
	//select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid;
	//查询购物车中所有的数据以及和购物车关联的图书数据
	//select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where uid = ?
	public List<CartItem> queryCartItemsByUid(String uid) throws SQLException {
		String sql = "select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where uid = ?";
		Object[] params = {uid};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		//new MapListHandler() 使用这个handler 会把一条结果集以key-value的形式封装到map中。
		// 把每一个map在封装到list中
		List<Map<String,Object>> results = runner.query(sql, new MapListHandler(),params);
		List<CartItem>cartItems = new ArrayList<CartItem>();
		for (Map<String, Object> map : results) {
			// cartItemId quantity orderBy
			CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
			//把map中的和图书相关的属性封装到book中
			// bname author bid press...
			Book book = CommonUtils.toBean(map, Book.class);
			// 这一步就是将cartItem这个对象中book属性赋值。
			cartItem.setBook(book);
			// map 中还存在UID这属性。
			User user = CommonUtils.toBean(map, User.class);
			cartItem.setUser(user);
			cartItems.add(cartItem);
		}
		return cartItems;
	}

	/*
	 * 根据cartItem中的uid和bid进行查询
	 */
	public CartItem queryCartItemByUidAndBid(CartItem cartItem) throws SQLException {
		String sql = "select * from t_cartitem where bid = ? and uid = ?";
		Object [] params = {cartItem.getBook().getBid(),cartItem.getUser().getUid()};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		return runner.query(sql, new BeanHandler(CartItem.class),params);
	}

	public void insertCartItem(CartItem cartItem) throws SQLException {
		String sql = "insert into t_cartitem (cartItemId,uid,bid,quantity,orderBy) values (?,?,?,?,?)";
		Object[] params = {CommonUtils.uuid(),cartItem.getUser().getUid(),cartItem.getBook().getBid(),cartItem.getQuantity(),1};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql,params);
	}

	public void updateQuantityBuUidAndBid(CartItem cartItem) throws SQLException {
		String sql = "update t_cartitem set quantity = quantity + ? where uid = ? and bid = ?";
		Object[] params = {cartItem.getQuantity(),cartItem.getUser().getUid(),cartItem.getBook().getBid()};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql,params);
	}

	public void updateQuantityByCartItemId(CartItem cartItem) throws SQLException {
		String sql = "update t_cartItem set quantity = ? where cartItemId = ?";
		Object[] params = {cartItem.getQuantity(), cartItem.getCartItemId()};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql,params);
	}
	
	//根据cartitemid 进行查询
	public CartItem findCartItemById(CartItem cartItem) throws SQLException {
		String sql = "select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where cartItemId = ?";
		Object[] params = {cartItem.getCartItemId()};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		Map<String, Object> map = runner.query(sql, new MapHandler(),params);
		//cartItemId=xxxx; quantity = 1
		CartItem cartItem2 = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		cartItem2.setBook(book);
		return cartItem2;
	}

	//根据Id去删除购物车明细数据
	public void deleteCartItemByCartItemId(String[] ids) throws SQLException {
		/*
		String sql = "delete from t_cartitem where cartItemId = ?";
		Object[] params = {cartItemId};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql,params);
		*/
		// delete from t_cartitem where cartItemId 
		//	in (?,?);
		StringBuilder sb  = new StringBuilder();
		//[D9F6E69111DD4382AFFB72BB474FB016,699D673FF7CB4A8A8375568131F2BE60]
		for (String id : ids) {
			sb.append(" ? ").append(",");
		}
		// ?,?,  ---> ?,?
		String params = sb.substring(0,sb.length()-1);
		//delete from t_cartitem where cartItemId in (?,?);
		String sql = "delete from t_cartitem where cartItemId in ("+params+")";
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql, ids);
	}

	//根据购物车明细的id去查询购物车明细，并且，要查询出购物车明细中的图书的信息
	public List<CartItem> loadChooseCartItems(String[] ids) throws SQLException {
		//?,?,?,?,
		StringBuilder sb = new StringBuilder();
		for (String id : ids) {
			sb.append("?").append(",");
		}
		String idsPosition = sb.substring(0, sb.length() - 1);
		String sql = "select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where t_cartitem.cartItemId in ("+idsPosition+")";
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		List<Map<String, Object>> list = runner.query(sql, new MapListHandler(),ids);
		List<CartItem>cartItems = new ArrayList<CartItem>();// 数组  数据查询 数组不方便增删
		//List<CartItem>cartItems = new LinkedList<CartItem>();// 底层是用链表去实现的 内存不是连续的，这个方法集合进行增删
		for (Map<String, Object> map : list) {
			CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
			Book book = CommonUtils.toBean(map, Book.class);
			cartItem.setBook(book);
			cartItems.add(cartItem);
		}
		return cartItems;
	}
}
