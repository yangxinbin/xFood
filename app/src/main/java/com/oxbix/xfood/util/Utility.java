package com.oxbix.xfood.util;

import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.FoodCategoryDTO;
import com.oxbix.xfood.dto.FoodDTO;

public class Utility {
	public static FoodDTO searchFoodItems(long itemId){
		for(FoodCategoryDTO category:SysApplication.categorys){
			for(FoodDTO item:category.getItems()){
				if(item.getItemId()==itemId)
					return item;
			}
		}
		return null;
	}
}
