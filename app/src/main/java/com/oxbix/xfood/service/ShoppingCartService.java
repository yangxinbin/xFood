package com.oxbix.xfood.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.FoodDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.RoomDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;

/**
 * 购物车业务类
 * @author AllenZheng
 * @date 2014年9月25日 上午10:54:50
 */
public class ShoppingCartService {

	/**
	 * 添加到购物车
	 * @param itemDto
	 *            添加的食物
	 * @param quantity
	 *            加入的数量
	 */
	public static void addToCart(FoodDTO itemDto, int quantity) {

		for (Map<String, ShoppingCartItemDTO> shoppingCartItem : SysApplication.shoppingCartItems) {

			if (shoppingCartItem.containsKey(String.valueOf(itemDto.getItemId()))) {

				ShoppingCartItemDTO itemShoppingCart = shoppingCartItem.get(String.valueOf(itemDto.getItemId()));
				itemShoppingCart.setQuantity(itemShoppingCart.getQuantity() + quantity);

				return;
			}
		}

		Map<String, ShoppingCartItemDTO> newShoppingCartItem = new HashMap<String, ShoppingCartItemDTO>();
		ShoppingCartItemDTO cartItem = new ShoppingCartItemDTO(itemDto, quantity);
		newShoppingCartItem.put(String.valueOf(itemDto.getItemId()), cartItem);
		SysApplication.shoppingCartItems.add(newShoppingCartItem);
	}

	/**
	 * 删除购物车中的某一项
	 * @param id
	 *            该食物的id
	 */
	public static List<ShoppingCartItemDTO> removeShoppingCartItem(String id) {

		List<ShoppingCartItemDTO> shoppingCartItems = new ArrayList<ShoppingCartItemDTO>();

		for (int i = 0; i < SysApplication.shoppingCartItems.size(); i++) {

			Map<String, ShoppingCartItemDTO> shoppingCartItem = SysApplication.shoppingCartItems.get(i);
			if (shoppingCartItem.containsKey(id)) {

				SysApplication.shoppingCartItems.remove(i);

				for (Map<String, ShoppingCartItemDTO> Item : SysApplication.shoppingCartItems) {

					for (ShoppingCartItemDTO cartItem : Item.values()) {
						shoppingCartItems.add(cartItem);
					}
				}
				return shoppingCartItems;
			}
		}

		return shoppingCartItems;
	}

	/**
	 * 修改购物车中某一款食物的数量
	 * @param operationTypeCode
	 *            操作类型(加或减,其中0代表减，1代表加)
	 * @param id
	 *            该食物的id
	 * @return
	 */
	public static List<ShoppingCartItemDTO> updateShoppingCartItemQuantity(int operationTypeCode, String id) {

		List<ShoppingCartItemDTO> shoppingCartItems = new ArrayList<ShoppingCartItemDTO>();
		for (Map<String, ShoppingCartItemDTO> shoppingCartItem : SysApplication.shoppingCartItems) {

			if (shoppingCartItem.containsKey(String.valueOf(id))) {

				ShoppingCartItemDTO itemShoppingCart = shoppingCartItem.get(String.valueOf(id));

				switch (operationTypeCode) {

				case 0:

					itemShoppingCart.setQuantity(itemShoppingCart.getQuantity() - 1);
					break;

				case 1:

					itemShoppingCart.setQuantity(itemShoppingCart.getQuantity() + 1);
					break;
				}

				for (Map<String, ShoppingCartItemDTO> Item : SysApplication.shoppingCartItems) {

					for (ShoppingCartItemDTO cartItem : Item.values()) {
						shoppingCartItems.add(cartItem);
					}
				}

				return shoppingCartItems;
			}
		}

		return shoppingCartItems;
	}

	/**
	 * 获得购物车中的食物总数
	 * @return
	 */
	public static int getFoodCount() {

		int count = 0;

		for (Map<String, ShoppingCartItemDTO> Item : SysApplication.shoppingCartItems) {
			for (ShoppingCartItemDTO cartItem : Item.values()) {

				count += cartItem.getQuantity();
			}
		}

		return count;
	}

	/**
	 * 所有食物的总价值
	 * @return
	 */
	public static double getTotal() {

		double total = 0;
		for (Map<String, ShoppingCartItemDTO> Item : SysApplication.shoppingCartItems) {

			for (ShoppingCartItemDTO cartItem : Item.values()) {

				total += cartItem.getFood().getDefaultPrice() * cartItem.getQuantity();
			}
		}

		return total;
	}

	public static String createOrderJson() {

		List<CustomerOrderItemDTO> orderItems = new ArrayList<CustomerOrderItemDTO>();
		for (Map<String, ShoppingCartItemDTO> Item : SysApplication.shoppingCartItems) {

			for (ShoppingCartItemDTO cartItem : Item.values()) {

				CustomerOrderItemDTO orderDetailDTO = new CustomerOrderItemDTO(0L, 0L, cartItem.getFood().getItemId(), cartItem.getFood().getName(),
						cartItem.getQuantity(), cartItem.getFood().getDefaultPrice(), "", 0);
				orderItems.add(orderDetailDTO);
			}
		}
		
		Type type = new TypeToken<List<CustomerOrderItemDTO>>() {
		}.getType();
		
		String orderJson=new Gson().toJson(orderItems, type);
		return orderJson;
	}
}
