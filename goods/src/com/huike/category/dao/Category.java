package com.huike.category.dao;

import java.util.List;

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
	private int orderby;
//	private Category parent;  多对一
	
	private List<Category> children; //一对多      所有的子分类

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

	public int getOrderby() {
		return orderby;
	}

	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}
	
	
}
