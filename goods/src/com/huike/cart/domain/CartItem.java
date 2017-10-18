package com.huike.cart.domain;

import java.math.BigDecimal;
import java.text.Bidi;

import com.huike.book.domain.Book;
import com.huike.user.domain.User;
//购物车明细对象
public class CartItem {
	
	
	private String cartItemId;
	private Integer quantity;//购物车中图书的数量
	//private String bid;		//图书的主键
	private Book book;
	//private String uid;
	private User user;
	private Integer orderBy;
	
	private double subTotal;//每一个购物车明细的总价, 这个属性的值应该由book属性中的currprice属性和quantity这个属性决定
	
	public CartItem() {
		super();
	}
	
	//只设置get方法, 
	public double getSubTotal() {
		//在Java中计算金钱都要使用一个类(计算精确一点)BigDecimal
		//要使用字符串去初始化这个值
		BigDecimal quantityBigDecimal = new BigDecimal(quantity+"");
		BigDecimal currpriceBigDecimal = new BigDecimal(book.getCurrPrice()+"");
		
		BigDecimal result = quantityBigDecimal.multiply(currpriceBigDecimal);
		subTotal = result.doubleValue();
		return subTotal;
	}
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", quantity=" + quantity
				+ ", book=" + book + ", user=" + user + ", orderBy=" + orderBy
				+ "]";
	}
	
	
}
