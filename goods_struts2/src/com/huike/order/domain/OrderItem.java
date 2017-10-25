package com.huike.order.domain;

import com.huike.book.domain.Book;

/**
 * 订单明细对象，记录订单当中的一种商品
 * @author byy
 *
 */
public class OrderItem {
	
	private String orderItemId;
	private Integer quantity;
	private double subtotal;
	
	private Book book; //对应库中的bid字段
	
	private String bname;
	private String image_b;
	private double currPrice;
	
	private String bid;
	
	private Order order; //对应库中的oid字段

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getImage_b() {
		return image_b;
	}

	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}

	public double getCurrPrice() {
		return currPrice;
	}

	public void setCurrPrice(double currPrice) {
		this.currPrice = currPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
	
	
	
}
