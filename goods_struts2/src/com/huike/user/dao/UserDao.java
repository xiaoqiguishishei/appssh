package com.huike.user.dao;

import java.sql.SQLException;

import javax.management.Query;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.huike.user.domain.User;
import com.huike.utils.hibernate.HibernateUtil;
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
		Session session = HibernateUtil.getSession();//-------------
		try {
			Transaction transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 根据激活码更新状态
	 */
	public void updateStatusByActivationCode(String code) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			String hql = "from User where activationCode = ?";
			User user = (User) session.createQuery(hql).setParameter(0, code).uniqueResult();
			user.setStatus(1);
			session.update(user);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean findLoginName(String loginname) {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String hql = "from User where loginname = ?";
		User user = (User) session.createQuery(hql).setParameter(0, loginname).uniqueResult();
		if (user != null) {
			return false;
		}
		transaction.commit();
		session.close();
		return true;
	}

	public User findUser(String loginname, String loginpass) {
		Session session = HibernateUtil.getSession();//-------------
		try {
			Transaction transaction = session.beginTransaction();//-----------------
			User user = (User) session
					.createQuery("from User u where u.loginname = ? and u.loginpass = ?")
					.setParameter(0, loginname).setParameter(1, loginpass)
					.uniqueResult();
			transaction.commit();//------------
			session.close();//------------
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateLoginpass(String newPass, String uid) {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		User user = (User) session.createCriteria(User.class).add(Restrictions.idEq(uid)).uniqueResult();
		user.setLoginpass(newPass);
		session.update(user);
		transaction.commit();
		session.close();
	}
}
