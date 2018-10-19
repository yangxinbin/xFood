package com.oxbix.xfood.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 食物类别类
 * @author AllenZheng
 * @date 2014年9月25日 上午11:21:34
 */
public class FoodCategoryDTO implements Comparable<FoodCategoryDTO> {

	/** 食物类别服务器id **/
	private Long categoryId;
	/** 食物类别名 **/
	private String name;
	/** 食物类别名翻译 **/
	private String nameT;
	/** 食物类别图标 **/
	private String icon;
	/** 食物类别排列顺序 **/
	private int orderIndex;
	/** 一个类别下包含的所有食物 **/
	private List<FoodDTO> items = new ArrayList<FoodDTO>(0);

	public FoodCategoryDTO() {
	}

	public FoodCategoryDTO(Long categoryId, String name, String nameT, String icon, int orderIndex, List<FoodDTO> items) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.nameT = nameT;
		this.icon = icon;
		this.orderIndex = orderIndex;
		this.items = items;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameT() {
		return nameT;
	}

	public void setNameT(String nameT) {
		this.nameT = nameT;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<FoodDTO> getItems() {
		return items;
	}

	public void setItems(List<FoodDTO> items) {
		this.items = items;
	}

	@Override
	public int compareTo(FoodCategoryDTO o) {
		return new Integer(orderIndex).compareTo(o.orderIndex);
	}

}
