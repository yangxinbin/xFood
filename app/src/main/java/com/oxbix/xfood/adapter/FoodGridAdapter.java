package com.oxbix.xfood.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.FoodDTO;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.FoodListFragment;
import com.oxbix.xfood.util.AnimationUtil;

/**
 * 食物列表GridView的Adapter
 * @author AllenZheng 
 * @date 2014年9月25日 上午10:44:07
 */
public class FoodGridAdapter extends BaseAdapter {

	private Context context;
	private List<FoodDTO> data;
	private LayoutInflater layoutInflater;
	private FoodListFragment fragment = null;
	public FoodGridAdapter(FoodListFragment fragment, List<FoodDTO> data) {
		super();
		this.fragment = fragment;
		this.context = fragment.getActivity();
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public FoodDTO getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.gridview_food_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 设置食物图片
			SysApplication.imageLoader.displayImage(Config.SERVER_IMAGE_URL + getItem(position).getIcon(), viewHolder.getFoodImgIv(),SysApplication.options);
//		SysApplication.ut.display(viewHolder.getFoodImgIv(), Config.SERVER_IMAGE_URL + getItem(position).getIcon());
		// 设置食物价格
		viewHolder.getFoodPriceTv().setText(Config.getCurrency(context)+String.valueOf(getItem(position).getDefaultPrice()));
		
		// 根据当前显示的语言设置食物名称		
		if (SysApplication.currentLanguage) {

			viewHolder.getFoodNameTv().setText(getItem(position).getName());
		} else {

			viewHolder.getFoodNameTv().setText(getItem(position).getNameT());
		}
		
		// 添加到购物车
//		viewHolder.getAddToCartBtn().setOnClickListener(fragment);
		viewHolder.getAddToCartBtn().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ShoppingCartService.addToCart(data.get(position), 1);
				if(SysApplication.badgeView!=null){
					
					FoodListFragment.badge.setText(String.valueOf(ShoppingCartService.getFoodCount()));
				}
				fragment.shopingCartAnima();
			}
			
		});
		
		if(SysApplication.skinColor.equals(Config.SKIN_CHOCOLATE)){
			viewHolder.getAddToCartBtn().setBackgroundResource(R.drawable.btn_bg_add_to_cart);	
		}else if(SysApplication.skinColor.equals(Config.SKIN_GREEN)){	
			viewHolder.getAddToCartBtn().setBackgroundResource(R.drawable.btn_bg_green_add_to_cart);	
		}
		
		//viewHolder.getAddToCartBtn().setText(R.string.add_to_cart);
		
		return convertView;
	}

	private static class ViewHolder {

		private ImageView foodImgIv;
		private TextView foodPriceTv;
		private TextView foodNameTv;
		private Button addToCartBtn;

		public ViewHolder(View view) {

			foodImgIv = (ImageView) view.findViewById(R.id.iv_food_img_thumbnail);
			foodPriceTv = (TextView) view.findViewById(R.id.tv_food_price);
			foodNameTv = (TextView) view.findViewById(R.id.tv_food_name);
			addToCartBtn = (Button) view.findViewById(R.id.btn_add_to_cart);
		}

		public ImageView getFoodImgIv() {
			return foodImgIv;
		}

		public TextView getFoodPriceTv() {
			return foodPriceTv;
		}

		public TextView getFoodNameTv() {
			return foodNameTv;
		}

		public Button getAddToCartBtn() {
			return addToCartBtn;
		}
	}
	
	private static boolean exchageTag = true; 
	public void notify1() {
		exchageTag = false;
		this.notifyDataSetChanged();
	}
}
