package com.oxbix.xfood.dto;

/**
 * 购物车中列表项类
 * @author AllenZheng 
 * @date 2014年9月25日 上午11:38:08
 */
public class ShoppingCartItemDTO {

	/** 食物信息  **/
	private FoodDTO food;
	/** 食物数量  **/
	private int quantity;
	private int status;

	public ShoppingCartItemDTO(FoodDTO food, int quantity) {
		this(food, quantity, 0);
	}
	public ShoppingCartItemDTO(FoodDTO food, int quantity, int status) {
		super();
		this.food = food;
		this.quantity = quantity;
		this.status=status;
	}

	public FoodDTO getFood() {
		return food;
	}

	public void setFood(FoodDTO food) {
		this.food = food;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
