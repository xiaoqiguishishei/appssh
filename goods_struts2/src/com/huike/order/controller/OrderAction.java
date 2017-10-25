package com.huike.order.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;



import com.huike.cart.domain.CartItem;
import com.huike.cart.service.CartService;
import com.huike.order.domain.Order;
import com.huike.order.domain.OrderItem;
import com.huike.order.service.OrderService;
import com.huike.page.PageBean;
import com.huike.page.QueryInfo;
import com.huike.user.domain.User;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.jdbc.JdbcUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class OrderAction extends ActionSupport implements ModelDriven<QueryInfo> {
	
	private QueryInfo queryInfo = new QueryInfo();
	@Override
	public QueryInfo getModel() {
		return queryInfo;
	}
	public QueryInfo getQueryInfo() {
		return queryInfo;
	}
	// 新增订单
	// Order , OrderItems 二者组合 从购物车中新增商品，生成订单后，要删除购物车中的数据
	public String addOrder() {
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			// 需要参数 uid address
			String address = request.getParameter("address");
			// 生成订单明细 可以根据购物车中的购物车明细去生成
			// 后台要接受购物车明细的id，以字符串拼接传过来
			// xxxx,aaaa
			String cartItemIds = request.getParameter("cartItemIds");

			User user = (User) request.getSession().getAttribute("user");

			Order order = new Order();
			order.setAddress(address);
			order.setUser(user);
			order.setOid(CommonUtils.uuid());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			
			System.out.println(simpleDateFormat);
			System.out.println(date);
			
			String formatDateString = simpleDateFormat.format(date);
			
			System.out.println(formatDateString);
			
			order.setOrderTime(formatDateString);
			order.setStatus(1);
			// 设置Total属性 需要生成OrderItems
			// 专门用来存放订单明细
			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			// 通过cartItemIds 可以查询到相应的购物车明细信息
			CartService cartService = new CartService();
			List<CartItem> cartItems = cartService.loadChooseCartItems(cartItemIds);
			// 根据购物车明细创建订单明细
			for (CartItem cartItem : cartItems) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderItemId(CommonUtils.uuid());
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setSubtotal(cartItem.getSubTotal());
				orderItem.setBook(cartItem.getBook());
				orderItem.setBname(cartItem.getBook().getBname());
				orderItem.setCurrPrice(cartItem.getBook().getCurrPrice());
				orderItem.setImage_b(cartItem.getBook().getImage_b());
				orderItem.setOrder(order);
				orderItems.add(orderItem);
			}
			// 计算订单小计
			BigDecimal totalDecimal = new BigDecimal("0");
			for (OrderItem orderItem : orderItems) {
				totalDecimal = totalDecimal.add(new BigDecimal(orderItem.getSubtotal() + ""));
			}
			order.setTotal(totalDecimal.doubleValue());
			// 给订单增加订单明细的数据
			order.setOrderItems(orderItems);
			// 创建service ，保存订单和订单明细数据
			OrderService orderService = new OrderService();
			JdbcUtils.beginTransaction();
			orderService.add(order);
			// 删除购物车数据
			cartService.deleteCartItemByCartItemIds(cartItemIds.split(","));
			JdbcUtils.commitTransaction();

			request.setAttribute("order", order);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
//		return "f:/jsps/order/ordersucc.jsp";
	}
	
	public String myOrders() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			// 1.得到我的信息，uid
			User user = (User) request.getSession().getAttribute("user");
			OrderService orderService = new OrderService();
			PageBean pageBean = orderService.myOrders(user, queryInfo);
			setUrl2Bean(request, pageBean);
			
		} catch (Exception e) {
			// 处理异常。
			e.printStackTrace();
		}
		return SUCCESS;
//		return "f:/jsps/order/list.jsp";
	}
	
	
	
	public void setUrl2Bean(HttpServletRequest request, PageBean pageBean) {
		// request.getQueryString(); 是截取URL后边的参数。
		String str = request.getQueryString();
		String url = request.getRequestURI() + "?" + request.getQueryString();
		if(str == null) {
			url = request.getRequestURI() + "?";
		}
		int index = url.indexOf("&currentPage");
		if (index > 0) {
			url = url.substring(0, index);
		}
		pageBean.setUrl(url);

		request.setAttribute("pageBean", pageBean);
	}

}
