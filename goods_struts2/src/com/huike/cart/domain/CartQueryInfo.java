package com.huike.cart.domain;

import com.huike.book.domain.Book;
import com.huike.user.domain.User;

public class CartQueryInfo {

	private String cartItemId;
	private Integer quantity;//购物车中图书的数量
	//private String bid;		//图书的主键
	private Book book;
	//private String uid;
	private User user;
	private Integer orderBy;
	
	private double subTotal;
	
}
