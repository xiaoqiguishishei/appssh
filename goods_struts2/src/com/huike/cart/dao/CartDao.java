package com.huike.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.huike.book.domain.Book;
import com.huike.cart.domain.CartItem;
import com.huike.user.domain.User;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.hibernate.HibernateUtil;
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
	public List<CartItem> queryCartItemsByUid1(String uid) throws SQLException {
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
	
	public List<CartItem> queryCartItemsByUid(String uid) {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where uid = ?";
		List<Map<String, Object>> results =  session.createSQLQuery(sql)
											.setParameter(0, uid)
											.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
											.list();
		List<CartItem> cartItems = new ArrayList<CartItem>();
		for (Map<String, Object> map : results) {
			CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
			Book book = CommonUtils.toBean(map, Book.class);
			cartItem.setBook(book);
			User user = CommonUtils.toBean(map, User.class);
			cartItem.setUser(user);
			cartItems.add(cartItem);
		}
		transaction.commit();
		session.close();
		return cartItems;
	}

	/*
	 * 根据cartItem中的uid和bid进行查询
	 */
	public CartItem queryCartItemByUidAndBid1(CartItem cartItem) throws SQLException {
		String sql = "select * from t_cartitem where bid = ? and uid = ?";
		Object [] params = {cartItem.getBook().getBid(),cartItem.getUser().getUid()};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		return runner.query(sql, new BeanHandler<CartItem>(CartItem.class),params);
	}
	
	public CartItem queryCartItemByUidAndBid(CartItem cartItem) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery("select * from t_cartitem where bid = ? and uid = ?");
		query.setParameter(0, cartItem.getBook().getBid()).setParameter(1, cartItem.getUser().getUid());
		cartItem = (CartItem) query.uniqueResult();
		transaction.commit();
		session.close();
		return cartItem;
	}

	public void insertCartItem1(CartItem cartItem) throws SQLException {
		String sql = "insert into t_cartitem (cartItemId,uid,bid,quantity,orderBy) values (?,?,?,?,?)";
		Object[] params = {CommonUtils.uuid(),cartItem.getUser().getUid(),cartItem.getBook().getBid(),cartItem.getQuantity(),1};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql,params);
	}
	
	public void insertCartItem(CartItem cartItem) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		User user = (User) session.createCriteria(User.class).add(Restrictions.idEq(cartItem.getUser().getUid())).uniqueResult();
		Book book = (Book) session.createCriteria(Book.class).add(Restrictions.idEq(cartItem.getBook().getBid())).uniqueResult();
		CartItem cartItem1 = new CartItem();
		cartItem1.setCartItemId(CommonUtils.uuid());
		cartItem1.setUser(user);
		cartItem1.setBook(book);
		cartItem1.setQuantity(cartItem.getQuantity());
		cartItem1.setOrderBy(1);
		session.save(cartItem1);
		transaction.commit();
		session.close();
	}

	public void updateQuantityBuUidAndBid1(CartItem cartItem) throws SQLException {
		String sql = "update t_cartitem set quantity = quantity + ? where uid = ? and bid = ?";
		Object[] params = {cartItem.getQuantity(),cartItem.getUser().getUid(),cartItem.getBook().getBid()};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql,params);
	}
	
	public void updateQuantityBuUidAndBid(CartItem cartItem) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "update Cartitem set quantity = quantity + ? where uid = ? and bid = ?";
		session.createSQLQuery(sql).setParameter(0, cartItem.getQuantity())
									.setParameter(1, cartItem.getUser().getUid())
									.setParameter(2, cartItem.getBook().getBid())
									.executeUpdate();
		transaction.commit();
		session.close();
	}

	public void updateQuantityByCartItemId1(CartItem cartItem) throws SQLException {
		String sql = "update t_cartItem set quantity = ? where cartItemId = ?";
		Object[] params = {cartItem.getQuantity(), cartItem.getCartItemId()};
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		runner.update(sql,params);
	}
	
	public void updateQuantityByCartItemId(CartItem cartItem) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		CartItem cartItem1 = (CartItem) session.createCriteria(CartItem.class).add(Restrictions.idEq(cartItem.getCartItemId())).uniqueResult();
		cartItem1.setQuantity(cartItem.getQuantity());
		session.update(cartItem1);
		transaction.commit();
		session.close();
	}
	
	//根据cartitemid 进行查询
	public CartItem findCartItemById1(CartItem cartItem) throws SQLException {
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
	
	public CartItem findCartItemById(CartItem cartItem) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where cartItemId = ?";
//		Map<String, Object> map =  (Map<String, Object>) session.createSQLQuery(sql)
//											.setParameter(0, cartItem.getCartItemId())
//											.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
//											
//		CartItem cartItem1 = new CartItem();
//		
//			cartItem1 = CommonUtils.toBean(map, CartItem.class);
//			Book book = CommonUtils.toBean(map, Book.class);
//			cartItem1.setBook(book);
//		transaction.commit();
//		session.close();
//		return cartItem1;
		List<Map<String, Object>> results =  session.createSQLQuery(sql)
				.setParameter(0, cartItem.getCartItemId())
				.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
				.list();
		for (Map<String, Object> map : results) {
		cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		
		cartItem.setBook(book);
		}
		transaction.commit();
		session.close();
		return cartItem;
	}

	//根据Id去删除购物车明细数据
	public void deleteCartItemByCartItemId1(String[] ids) throws SQLException {
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
	
	public void deleteCartItemByCartItemId(String[] ids) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
//		StringBuilder sb  = new StringBuilder();
		//[D9F6E69111DD4382AFFB72BB474FB016,699D673FF7CB4A8A8375568131F2BE60]
		for (String id : ids) {
			CartItem cartItem = (CartItem)session.createCriteria(CartItem.class).add(Restrictions.idEq(id)).uniqueResult();
			session.delete(cartItem); // 计划执行一个delete语句  
		}
		transaction.commit();
		session.close();
	}

	//根据购物车明细的id去查询购物车明细，并且，要查询出购物车明细中的图书的信息
	public List<CartItem> loadChooseCartItems1(String[] ids) throws SQLException {
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
	
	public List<CartItem> loadChooseCartItems(String[] ids) throws SQLException {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		//?,?,?,?,
		StringBuilder sb = new StringBuilder();
		for (String id : ids) {
			sb.append("?").append(",");
		}
		String idsPosition = sb.substring(0, sb.length() - 1);
		String sql1 = "select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where t_cartitem.cartItemId in ("+idsPosition+")";
//		List<Map<String, Object>> list =  session.createSQLQuery(sql)
//											.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
//											.list();
//		List<CartItem>cartItems = new ArrayList<CartItem>();// 数组  数据查询 数组不方便增删
		//List<CartItem>cartItems = new LinkedList<CartItem>();// 底层是用链表去实现的 内存不是连续的，这个方法集合进行增删
//		for (Map<String, Object> map : list) {
//			CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
//			Book book = CommonUtils.toBean(map, Book.class);
//			cartItem.setBook(book);
//			cartItems.add(cartItem);
//		}
//		transaction.commit();
//		return cartItems;
		List<CartItem> cartItems = new ArrayList<CartItem>();
		for( String id : ids) {
			String sql = "select * from t_cartitem join t_book on t_cartitem.bid = t_book.bid where t_cartitem.cartItemId = ?";
			List<Map<String, Object>> list =  session.createSQLQuery(sql)
										.setParameter(0, id)
										.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
										.list();
			for (Map<String, Object> map : list) {
				CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
				Book book = CommonUtils.toBean(map, Book.class);
				cartItem.setBook(book);
				cartItems.add(cartItem);
			}
		}
		transaction.commit();
		return cartItems;
	}
	
}
