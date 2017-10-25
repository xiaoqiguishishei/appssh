package com.huike.cart.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huike.cart.dao.CartDao;
import com.huike.cart.domain.CartItem;

public class CartService {

	public void queryCartItemByUid() {
		
	}

	public List<CartItem> queryCartItemsByUid(String uid) throws SQLException {
		
		if (null != uid && !uid.equals("")) {
			CartDao cartDao = new CartDao();
			return cartDao.queryCartItemsByUid(uid);
		}
		return null;
	}

	public void addCart(CartItem cartItem) throws SQLException {
		
		//1.首先根据UID和bid去查询购物车明细
		if(cartItem.getUser().getUid() != null && cartItem.getBook().getBid() != null) {
			CartDao cartDao = new CartDao();
			CartItem cartItemResult = cartDao.queryCartItemByUidAndBid(cartItem);
			if(cartItemResult == null) {
				//保存操作
				cartDao.insertCartItem(cartItem);
				
			} else {
				//更新操作
				cartDao.updateQuantityBuUidAndBid(cartItem);
			}
		}
	}
	
	//根据cartItemId修改商品数量, 并且返回更新后的对象
	public CartItem updateQuantityByCartItemId(CartItem cartItem) throws SQLException {
		if (cartItem.getCartItemId() !=null && !cartItem.getCartItemId().equals("")) {
			CartDao cartDao = new CartDao();
			cartDao.updateQuantityByCartItemId(cartItem);
			return cartDao.findCartItemById(cartItem);
		}
		return null;
	}

	public void deleteCartItemByCartItemId(String cartItemId) throws SQLException {
		CartDao cartDao = new CartDao();
		//cartDao.deleteCartItemByCartItemId(cartItemId);
	}

	public void deleteCartItemByCartItemIds(String[] idArray) throws SQLException {
		CartDao cartDao = new CartDao();
		/*for(String id : idArray) {		//一个一个的进行删除, 效率低
			cartDao.deleteCartItemByCartItemId(id);
		}*/
		cartDao.deleteCartItemByCartItemId(idArray);
	}

	public List<CartItem> loadChooseCartItems(String cartItemIds) throws SQLException {
		if(cartItemIds != null && !cartItemIds.equals("")) {
			String[] ids = cartItemIds.split(",");
			CartDao cartDao = new CartDao();
			return cartDao.loadChooseCartItems(ids);
		}
		return null;
	}
}
