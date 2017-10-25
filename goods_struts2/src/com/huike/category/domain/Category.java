package com.huike.category.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huike.book.domain.Book;

public class Category {
/*
CREATE TABLE `t_category` (
  `cid` char(32) NOT NULL,
  `cname` varchar(50) default NULL,
  `pid` char(32) default NULL,
  `desc` varchar(100) default NULL,
  `orderBy` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`cid`),
  UNIQUE KEY `cname` (`cname`),		//分类的名称是唯一的
  KEY `FK_t_category_t_category` (`pid`),
  KEY `orderBy` (`orderBy`),
  CONSTRAINT `FK_t_category_t_category` FOREIGN KEY (`pid`) REFERENCES `t_category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	一对多 : 一方记住多方 (一般不会设计)        多对一:多方记住一方
 */
	private String cid;
	private String cname;
	private String desc;
	private int orderBy;
	private Category parent;  //多对一   ---> pid
	
	private Set<Category> children = new HashSet<Category>(); //一对多      所有的子分类
	private Set<Book> books = new HashSet<Book>();
	
	public Category() {
	super();
}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	
	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
}
