package com.huike.cart.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;

import com.huike.book.domain.Book;
import com.huike.cart.domain.CartItem;
import com.huike.cart.service.CartService;
import com.huike.user.domain.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CartAction extends ActionSupport implements ModelDriven<CartItem> {

	private CartItem cartItem = new CartItem();
//	private String bid;
	private Book book = new Book();
	private String result;
	@Override
	public CartItem getModel() {
		return cartItem;
	}
	public CartItem getCartItem() {
		return cartItem;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	/**
	 * 查询我的的购物车
	 * @return
	 */
	public String myCart() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//用户登录成功的标志  像session中添加user对象 
			User user = (User) request.getSession().getAttribute("user");
			CartService cartService = new CartService();
			List<CartItem> cartItems = cartService.queryCartItemsByUid(user.getUid());
			//cartItems 存储着购物车明细数据
			request.setAttribute("cartItems", cartItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
//		return "f:/jsps/cart/list.jsp";
		
	}
	
	/**
	 * 添加商品到购物车
	 * @return
	 */
	public String addCart(){
		try {
			String bid = ServletActionContext.getRequest().getParameter("bid");
			book.setBid(bid);
			cartItem.setBook(book);
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
			cartItem.setUser(user);
			CartService cartService = new CartService();
			cartService.addCart(cartItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//这里要显示购物车列表
		return SUCCESS;
	}
	
	public String batchDeleteCartItemsByIds(){
		try {
			String ids = ServletActionContext.getRequest().getParameter("cartItemIds");
			String[] idArray = ids.split(",");
			CartService cartService = new CartService();
			cartService.deleteCartItemByCartItemIds(idArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 修改购物车中商品的数量
	// 根据购物车明细ID去修改明细中商品的数量。
	// cartItemID quantity
	// 这个方法要返回json字符串
	public String updateQuantityByCartItemId(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();
			//更改数量
			CartService cartService = new CartService();
//			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
//			cartItem.setUser(user);
			CartItem cartItem2 = cartService.updateQuantityByCartItemId(cartItem);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("quantity", cartItem2.getQuantity().toString());
//			map.put("subTotal", String.valueOf(cartItem2.getSubTotal()));
			
			
			JSONObject jsonObject = new JSONObject();
			//还需要进行一次查询, 查询更新后的商品的数量
			jsonObject.put("quantity", cartItem2.getQuantity().toString());
			jsonObject.put("subTotal", String.valueOf(cartItem2.getSubTotal()));
			//把jsonObject 以字符串{"quantity":"2"}json形式写出去
			result = jsonObject.toString();
			System.out.println(result);
//			response.getWriter().write(jsonObject.toJSONString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String loadChooseCartItems() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String cartItemIds = request.getParameter("cartItemIds");
			CartService cartService = new CartService();
			List<CartItem> cartItems = cartService.loadChooseCartItems(cartItemIds);
			request.setAttribute("cartItems", cartItems);
			request.setAttribute("cartItemIds", cartItemIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//转发页面
		return SUCCESS;
//		return "f:/jsps/cart/showitem.jsp";
	}
	
}
