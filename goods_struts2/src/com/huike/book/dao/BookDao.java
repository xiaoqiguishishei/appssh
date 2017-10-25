package com.huike.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.huike.book.domain.Book;
import com.huike.category.domain.Category;
import com.huike.page.Expression;
import com.huike.page.QueryInfo;
import com.huike.page.QueryResult;
import com.huike.utils.hibernate.HibernateUtil;
import com.huike.utils.jdbc.JdbcUtils;

public class BookDao {

	/*
	 * 总结: 大部分方法中都有两次查询, 分别查询了数量和数据
	 * sql: select count(*) from t_book where ... 所有的sql主体相同, 只是条件不同
	 * 		select * from t_name where ...  limit?,?  所有的sql主体相同, 只是条件不同
	 * 		思考:  编写一个方法, 动态的生成这两个sql, 把每条sql不同的地方, 使用参数传递, 并且使用程序动态生成
	 * 		观察条件: 例如: cid = ?  属性字段  操作符  属性值.
	 * 					bname like ?
	 * 					press = ?
	 * 					autho r= ?
	 * 		可以把条件抽取出来, 变成一个对象保存expression 在外部进行查询的时候
	 */
	// 这个方法要根据参数动态的拼接生成sql
	public QueryResult queryByPage1(List<Expression> expressions, 
			int startIndex, int pageSize) throws SQLException {
		//1.拼接sql
		//拼接sql的where条件Expression fieldName = bname; expression = '=' fieldValue = 'zhangsan';
		//where bname = ?
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		StringBuilder whereSql = new StringBuilder("where 1 = 1");
		List<Object> params = new ArrayList<Object>();
		for(Expression expression : expressions) {
			whereSql.append(" and ");
			whereSql.append(expression.getFieldName());//banme
			whereSql.append(" ");
			whereSql.append(expression.getExpression());//=
			whereSql.append("?");
			//存储查询条件的值
			params.add(expression.getFieldValue());//=
		}
		//where 1 = 1 and bname = ? and press = ?
		//System.out.println(whereSql.toString());
		
		//查询数量
		String sql = "select count(*) from t_book " + whereSql;
		Long count = (Long) runner.query(sql, new ScalarHandler(), params.toArray());
		//System.out.println(count);
		//查询数据
		sql = "select * from t_book " + whereSql + " limit ?,?";
		params.add(startIndex);
		params.add(pageSize);
		List<Book> books = runner.query(sql, new BeanListHandler(Book.class),params.toArray());
		
		QueryResult queryResult = new QueryResult();
		queryResult.setList(books);
		queryResult.setTotalRecord(count);;
		
		return queryResult;
	}

	//要返回QueryResult, 要进行两次查询
	public QueryResult queryByPage(Criteria criteria, 
			int startIndex, int pageSize) throws SQLException {
		
//		Long count = (Long) criteria.setProjection(Projections.rowCount())
//				.uniqueResult();	
		//这个就是查询和criteria符合的条数 hibernate会翻译成select count(*)...
		
		Integer count = criteria.list().size();	//查询图书的数量
		
		//System.out.println(count);
		//查询数据
		criteria.setMaxResults(pageSize);
		criteria.setFirstResult(startIndex);
		List<Book> books = criteria.list();
		
		QueryResult queryResult = new QueryResult();
		queryResult.setList(books);
		queryResult.setTotalRecord(count);;
		
		return queryResult;
	}
	
	/*
	 * 按照种类的id去查询
	 */
	public QueryResult findBooksByCid1(QueryInfo queryInfo) {
		
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		try {
			/*
			 * limit 分页查询
			 * 
			 * 查询前10条数据   limit start,size
			 * select * from t_book where cid = ? limit ?,?     第1页  
			 * select * from t_book where cid = ? limit 10,10   第2页
			 * select * from t_book where cid = ? limit 20,10   第3页
			 *                                           
			 * 根本当前页算出从第几条开始查                                                    start,pageSize,currentPage
			 *  start = pageSize * (currentPage - 1)
			 *  
			 
			Object[] params = {queryInfo.getCid()};
			String countSql = "select count(*) from t_book where cid=?";
			Long count = (Long)runner.query(countSql, new ScalarHandler(),params);
			
			params = new Object[]{queryInfo.getCid(),queryInfo.getStartIndex(),queryInfo.getPageSize()};
			String booksSql = "select * from t_book where cid = ? limit ?,?";
			List<Book> list = runner.query(booksSql, new BeanListHandler<Book>(Book.class),params);
			
			QueryResult result = new QueryResult();
			result.setTotalRecord(count);
			result.setList(list);
			
			return result;
			*/
			
			List<Expression> expressions = new ArrayList<Expression>();
			expressions.add(new Expression("cid", "=", queryInfo.getCid()));
			QueryResult result = 
					queryByPage1(expressions,queryInfo.getStartIndex(),queryInfo.getPageSize());
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public QueryResult findBooksByCid(QueryInfo queryInfo) {
		Session session = HibernateUtil.getSession();
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		try {
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class);
			criteria.add(Restrictions.eq("category.cid", queryInfo.getCid()));
//			criteria.add(Restrictions.ilike("bname", "%value%"));
			QueryResult result = queryByPage(criteria,
					queryInfo.getStartIndex(),queryInfo.getPageSize());
			transaction.commit();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 按照图书名称去查询
	 */
	public QueryResult findBooksByBname1(QueryInfo queryInfo) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		try {
			/*
			Object[] params = {"%" + queryInfo.getBname() + "%"};
			System.out.println(queryInfo.getBname());
			String countSql = "select count(*) from t_book where bname like ?";
			Long count = (Long)runner.query(countSql, new ScalarHandler(),params);
			
			params = new Object[]{"%" + queryInfo.getBname() + "%",queryInfo.getStartIndex(),queryInfo.getPageSize()};
			String booksSql = "select * from t_book where  bname like ? limit ?,?";
			List<Book> list = runner.query(booksSql, new BeanListHandler<Book>(Book.class),params);
			
			QueryResult result = new QueryResult();
			result.setTotalRecord(count);
			result.setList(list);
			
			return result;
			*/
			List<Expression> expressions = new ArrayList<Expression>();
//			expressions.add(new Expression("bname", "like", queryInfo.getBname()));
			expressions.add(new Expression("bname", "like", "%" + queryInfo.getBname() + "%"));
			QueryResult result = 
					queryByPage1(expressions,queryInfo.getStartIndex(),queryInfo.getPageSize());
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public QueryResult findBooksByBname(QueryInfo queryInfo) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class);
//			String str = queryInfo.getBname();
			criteria.add(Restrictions.ilike("bname", "%"+queryInfo.getBname()+"%"));
			
			QueryResult result = 
					queryByPage(criteria,queryInfo.getStartIndex(),queryInfo.getPageSize());
			transaction.commit();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * 按照出版社去查询
	 */
	public QueryResult findBooksByPress1(QueryInfo queryInfo) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		try {
			
			Object[] params = {queryInfo.getPress()};
			String countSql = "select count(*) from t_book where press = ?";
			Long count = (Long)runner.query(countSql, new ScalarHandler(),params);
			
			params = new Object[]{queryInfo.getPress(),queryInfo.getStartIndex(),queryInfo.getPageSize()};
			String booksSql = "select * from t_book where  press = ? limit ?,?";
			List<Book> list = runner.query(booksSql, new BeanListHandler<Book>(Book.class),params);
			
			QueryResult result = new QueryResult();
			result.setTotalRecord(count);
			result.setList(list);
			
			return result;
			
//			List<Expression> expressions = new ArrayList<Expression>();
//			expressions.add(new Expression("press", "=", queryInfo.getPress()));
//			QueryResult result = 
//					queryByPage(expressions,queryInfo.getStartIndex(),queryInfo.getPageSize());
//			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public QueryResult findBooksByPress(QueryInfo queryInfo) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class);
			criteria.add(Restrictions.eq("press", queryInfo.getPress()));
			QueryResult result = 
					queryByPage(criteria,queryInfo.getStartIndex(),queryInfo.getPageSize());
			transaction.commit();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 按照作者去查询
	 */
	public QueryResult findBooksByAuthor1(QueryInfo queryInfo) {
		
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		try {
/*
			Object[] params = {queryInfo.getAuthor()};
			String countSql = "select count(*) from t_book where author = ?";
			Long count = (Long)runner.query(countSql, new ScalarHandler(),params);
			
			params = new Object[]{queryInfo.getAuthor(),queryInfo.getStartIndex(),queryInfo.getPageSize()};
			String booksSql = "select * from t_book where  author = ? limit ?,?";
			List<Book> list = runner.query(booksSql, new BeanListHandler<Book>(Book.class),params);
			
			QueryResult result = new QueryResult();
			result.setTotalRecord(count);
			result.setList(list);
			
			return result;
			*/
			List<Expression> expressions = new ArrayList<Expression>();
			expressions.add(new Expression("author", "=", queryInfo.getAuthor()));
			QueryResult result = 
					queryByPage1(expressions,queryInfo.getStartIndex(),queryInfo.getPageSize());
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public QueryResult findBooksByAuthor(QueryInfo queryInfo) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class);
			criteria.add(Restrictions.eq("author", queryInfo.getAuthor()));
			
			QueryResult result = 
					queryByPage(criteria,queryInfo.getStartIndex(),queryInfo.getPageSize());
			transaction.commit();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * 按照图书id去查询
	 */
	public Book queryBookByBid1(String bid) throws SQLException {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		Object[] params = {bid};
		String booksSql = "select * from t_book where bid = ?";
		Book book  = runner.query(booksSql, new BeanHandler(Book.class),params);
		return book;
	}
	
	public Book queryBookByBid(String bid) {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class);
		Book book = (Book) criteria.add(Restrictions.eq("bid", bid)).uniqueResult();
		
//		Object[] params = {bid};
//		String booksSql = "select * from t_book where bid = ?";
//		Book book  = runner.query(booksSql, new BeanHandler(Book.class),params);
		
		transaction.commit();
		return book;
	}
	
	/*
	 * 根据条件组合查询图书数据
	 */
	public QueryResult queryBookByParamCom1(QueryInfo queryInfo) throws Exception {

		//三个条件组合查询
		//select * from t_book where bname like '%java%'
		//expression fieldName = bname expression = like fieldValue = %java%
		//不模糊查询不用%了
		//bname,press,author,List<Expression> 这个集合就有多个元素
		List<Expression> expressions = new ArrayList<Expression>();
		if(queryInfo.getBname() != null && !queryInfo.getBname().equals("")) {
			expressions.add(new Expression("bname", "like", "%"+queryInfo.getBname()+"%"));
		}
		if(queryInfo.getPress() != null && !queryInfo.getPress().equals("")) {
			expressions.add(new Expression("press", "like", "%"+queryInfo.getPress()+"%"));
		}
		if(queryInfo.getAuthor() != null && !queryInfo.getAuthor().equals("")) {
			expressions.add(new Expression("author", "like", "%"+queryInfo.getAuthor()+"%"));
		}
		QueryResult queryResult = queryByPage1(expressions, queryInfo.getStartIndex(), queryInfo.getPageSize());
		
		return queryResult;
		
	}
	
	public QueryResult queryBookByParamCom(QueryInfo queryInfo) throws Exception {

		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class);
		if(queryInfo.getBname() != null && !queryInfo.getBname().equals("")) {
			criteria.add(Restrictions.ilike("bname", "%"+queryInfo.getBname()+"%"));
		}
		if(queryInfo.getPress() != null && !queryInfo.getPress().equals("")) {
			criteria.add(Restrictions.ilike("press", "%"+queryInfo.getPress()+"%"));
		}
		if(queryInfo.getAuthor() != null && !queryInfo.getAuthor().equals("")) {
			criteria.add(Restrictions.ilike("author", "%"+queryInfo.getAuthor()+"%"));
		}
		QueryResult queryResult = 
				queryByPage(criteria,queryInfo.getStartIndex(),queryInfo.getPageSize());
		transaction.commit();
		return queryResult;
	}
}
