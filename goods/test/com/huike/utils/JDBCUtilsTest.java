package com.huike.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.huike.utils.jdbc.JdbcUtils;

public class JDBCUtilsTest {

	public static void main(String[] args) throws SQLException {
		
		DataSource dataSource = JdbcUtils.getDataSource();
		Connection connection = JdbcUtils.getConnection();
		System.out.println(dataSource);
		System.out.println(connection);
		
	}

}
