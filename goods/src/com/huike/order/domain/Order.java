package com.huike.order.domain;

import java.util.ArrayList;
import java.util.List;

import com.huike.user.domain.User;

public class Order {

	private String oid;
	private String orderTime;
	private double total;
	private Integer status;
	private String address;

	private User user; // 对应数据库中的uid那一列

	private List<OrderItem> orderItems = new ArrayList<OrderItem>();// 用来存储当前订单中的所有订单对象。

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}
