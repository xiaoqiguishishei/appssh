package com.huike.user.dao;

import java.sql.SQLException;

import javax.management.Query;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.huike.user.domain.User;
import com.huike.utils.jdbc.JdbcUtils;

public class UserDao {

	/*
	 * 添加用户
CREATE TABLE `t_user` (
  `uid` char(32) NOT NULL,
  `loginname` varchar(50) default NULL,
  `loginpass` varchar(50) default NULL,
  `email` varchar(50) default NULL,
  `status` tinyint(1) default NULL,
  `activationCode` char(64) default NULL,
  PRIMARY KEY  (`uid`),
  UNIQUE KEY `loginname` (`loginname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	 */
	public void addUser(User user) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		
		try {
			Object[] params = {user.getUid(),user.getLoginname(),user.getLoginpass(),user.getEmail(),user.getStatus(),user.getActivationCode()};
			runner.update("insert into t_user(uid,loginname,loginpass,email,status,activationCode) values(?,?,?,?,?,?)",params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 根据激活码更新状态
	 */
	public void updateStatusByActivationCode(String code) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		
		try {
			Object[] params = {code};
			runner.update("update t_user set status = 1 where activationCode=?",params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean findLoginName(String loginname) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		
		try {
			Object[] params = {loginname};
			User user = runner.query("select * from t_user where loginname=?",new BeanHandler<User>(User.class), params);
			if (user != null) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public User findUser(String loginname, String loginpass) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		
		try {
			Object[] params = {loginname, loginpass};
			User user = runner.query("select * from t_user where loginname=? and loginpass=?",new BeanHandler<User>(User.class), params);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateLoginpass(String newPass, String uid) {
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		
		try {
			Object[] params = {newPass, uid};
			runner.update("update t_user set loginpass = ? where uid = ?",params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
