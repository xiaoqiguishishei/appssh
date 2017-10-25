package com.huike.utils.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
 * 这个类是提供SessionFactory对象的类
 */
public class HibernateUtil {

	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	
	static {
		//这个对象sessionFactory只需被实例化一次, 就是在HibernateUtil类加载的时候被实例化
		configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
	}
	//这个类sessionFactory最终的目的就是提供session
	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
	public static void close() {
		sessionFactory.close();
	}
	
	public static void main(String[] args) {
		getSession();
	}
}
