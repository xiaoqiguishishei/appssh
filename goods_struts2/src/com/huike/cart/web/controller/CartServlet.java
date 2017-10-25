package com.huike.cart.web.controller;



import java.util.List;

import javax.naming.CommunicationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.huike.book.domain.Book;
import com.huike.cart.domain.CartItem;
import com.huike.cart.service.CartService;
import com.huike.user.domain.User;
import com.huike.utils.commons.CommonUtils;
import com.huike.utils.servlet.BaseServlet;

public class CartServlet extends BaseServlet {
	//购物车模块
	// 逻辑总结一句话： 在购物车中存储着 谁的什么商品
	// 解释：在购物车中  保存着哪一个用户购买的 哪一本图书
	// 当用户点击我的购物车，在页面就显示出  当前这个用户在购物车中添加的图书。
	// 在图书的详情页中，如果点击购物，其实不是真的购物，只是将图书添加到我的购物车中。
	// 数据库中 的购物车模块的表 为 t_cartitem 
	//	五个字段： 1.cartitemId 主见 UUID
	//          2.quantity : 数量
	//          3.bid        添加的图书
	//          4.uid        哪一个用户 存储用户的主键
	//          5.orderBy     排序字段
	
	
	// 查询我的的购物车
	// 要根据当前登录的用户去查
	// 能够访问myCart这个方法的请求，前提是当前用户已经登录。
	// 拿到用户登录数据后，可以在购物车表中通过用户的ID去查询数据
	// select * from t_cartitem where uid = ?
	public String myCart(HttpServletRequest request,HttpServletResponse response){
		try {
			//用户登录成功的标志  像session中添加user对象 
			User user = (User) request.getSession().getAttribute("user");
//			System.out.println(user);
			CartService cartService = new CartService();
			
			List<CartItem> cartItems = cartService.queryCartItemsByUid(user.getUid());
			//cartItems 存储着购物车明细数据
			request.setAttribute("cartItems", cartItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "f:/jsps/cart/list.jsp";
	}
	
	// 添加商品到购物车
	public String addCart(HttpServletRequest request,HttpServletResponse response){
		try {
			//bid = 'xxxx'
			Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
			//quantity = 'xxx'
			CartItem cartItem = CommonUtils.toBean(request.getParameterMap(), CartItem.class);
			cartItem.setBook(book);
			User user = (User) request.getSession().getAttribute("user");
			cartItem.setUser(user);
			CartService cartService = new CartService();
			cartService.addCart(cartItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//这里要显示购物车列表
		return myCart(request, response);
	}
	
	// 修改购物车中商品的数量
	// 根据购物车明细ID去修改明细中商品的数量。
	// cartItemID quantity
	// 这个方法要返回json字符串
	public String updateQuantityByCartItemId(HttpServletRequest request,HttpServletResponse response){
		try{
			CartItem cartItem = CommonUtils.toBean(request.getParameterMap(), CartItem.class);
			
			//更改数量
			CartService cartService = new CartService();
			
			CartItem cartItem2 = cartService.updateQuantityByCartItemId(cartItem);
			
			JSONObject jsonObject = new JSONObject();
			//还需要进行一次查询, 查询更新后的商品的数量
			jsonObject.put("quantity", cartItem2.getQuantity());
			jsonObject.put("subTotal", cartItem2.getSubTotal());
			//把jsonObject 以字符串{"quantity":"2"}json形式写出去
			response.getWriter().write(jsonObject.toJSONString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	//实现删除功能就是  把对应的数据在数据库中进行删除。
	// 前台点击删除，应该向后台发送要删除数据的Id值。
	// 后台接受到相应的Id值进行删除。
	
	public String deleteCartItemByCartItemId(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String cartItemId = request.getParameter("cartItemId");
			CartService cartService = new CartService();
			cartService.deleteCartItemByCartItemId(cartItemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myCart(request,response);
	}
	
	//实现批量删除，也是要前台后台后发送需要删除的数据的id
	// 前台可以把商品的id 按照一定的格式拼接起来  
	// "D9F6E69111DD4382AFFB72BB474FB016,699D673FF7CB4A8A8375568131F2BE60" 然后把这个字符串传动到后台。后台接受到字符串后，
	// 再按照相应的格式进行解析 变成 一个数组[D9F6E69111DD4382AFFB72BB474FB016,699D673FF7CB4A8A8375568131F2BE60]
	public String batchDeleteCartItemsByIds(HttpServletRequest request,HttpServletResponse response){
		try {
			String ids = request.getParameter("cartItemIds");
			String[] idArray = ids.split(",");
			CartService cartService = new CartService();
			cartService.deleteCartItemByCartItemIds(idArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myCart(request,response);
	}
	
	// 加载要购买的商品
	// 要在jsp页面展示出要购买的商品
	// 后台接收的参数就是 购物车明细id，然后根据购物车明细id进行查询，转发页面。
	// 后台接收参数为多个购物车明细id拼接成的字符串，以,分隔
	// 例如："41D446ACA2E347EEB3C4505488EBB27A,7D7CB4BF344D4696BF6BF16C343D9D69"
	public String loadChooseCartItems(HttpServletRequest request,HttpServletResponse response) {
		try {
			String cartItemIds = request.getParameter("cartItemIds");
			CartService cartService = new CartService();
			List<CartItem> cartItems = cartService.loadChooseCartItems(cartItemIds);
			request.setAttribute("cartItems", cartItems);
			request.setAttribute("cartItemIds", cartItemIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//转发页面
		return "f:/jsps/cart/showitem.jsp";
	}
	
	
}
