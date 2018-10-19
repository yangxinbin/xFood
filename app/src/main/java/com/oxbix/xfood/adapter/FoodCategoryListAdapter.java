package com.oxbix.xfood.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.FoodCategoryDTO;
import com.oxbix.xfood.util.FoodMenu;

/**
 * 食物分类列表ListView的Adapter
 * 
 * @author AllenZheng
 * @date 2014年9月20日 下午8:40:51
 */
public class FoodCategoryListAdapter extends BaseAdapter {

	private Context context;
	private List<FoodCategoryDTO> categorys;
	private LayoutInflater layoutInflater;
	private int clickTemp = 0;

	public FoodCategoryListAdapter(Context context, List<FoodCategoryDTO> categorys) {

		this.context = context;
		this.categorys = categorys;
		layoutInflater = LayoutInflater.from(context);
	}

	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public int getCount() {

		return categorys.size();
	}

	@Override
	public FoodCategoryDTO getItem(int position) {

		return categorys.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.listview_food_category_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (SysApplication.currentLanguage) {

			viewHolder.getCategoryName().setText(getItem(position).getName());
		} else {

			viewHolder.getCategoryName().setText(getItem(position).getNameT());
		}

//		if (clickTemp == position) {
//			convertView.setBackgroundResource(R.drawable.bg_food_category_list_item);
//			// convertView.setBackgroundColor(Color.parseColor("#2E8B57"));
//		} else {
//			convertView.setBackgroundResource(R.drawable.bg_food_category_list_item);
//			// convertView.setBackgroundColor(Color.TRANSPARENT);
//		}
		
		if(SysApplication.skinColor.equals(Config.SKIN_CHOCOLATE)){
			convertView.setBackgroundResource(R.drawable.bg_food_category_list_item);	
		}else if(SysApplication.skinColor.equals(Config.SKIN_GREEN)){	
			convertView.setBackgroundResource(R.drawable.bg_green_food_category_list_item);	
		}
		
		return convertView;
	}

	private static class ViewHolder {

		private TextView categoryName;

		public ViewHolder(View view) {

			categoryName = (TextView) view.findViewById(R.id.category_name);
		}

		public TextView getCategoryName() {

			return this.categoryName;
		}
	}

	@SuppressLint("NewApi")
	public Drawable getDrawable(FoodMenu em) {
		int startColor = 0;
		int centerColor = 0;
		int endColor = 0;

		switch (em) {
		case BBQ:
			startColor = 0;
			centerColor = 0;
			endColor = 0;
			break;
		case DISCOUNT:
			startColor = 0;
			centerColor = 0;
			endColor = 0;
			break;
		case HOTDRINKS:
			startColor = 0;
			centerColor = 0;
			endColor = 0;
			break;
		case PASTA:
			startColor = 0;
			centerColor = 0;
			endColor = 0;
			break;
		case STAKE:
			startColor = 0;
			centerColor = 0;
			endColor = 0;
			break;
		case SWEETS:
			startColor = 0;
			centerColor = 0;
			endColor = 0;
			break;
		default:
			startColor = 0;
			centerColor = 0;
			endColor = 0;
			break;
		}
		GradientDrawable gd = new GradientDrawable();
		gd.setColors(new int[] { startColor, centerColor, endColor });
		gd.setOrientation(Orientation.LEFT_RIGHT);
		return gd;
	}
}
