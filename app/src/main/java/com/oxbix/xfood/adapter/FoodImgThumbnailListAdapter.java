package com.oxbix.xfood.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;

/**
 * 食物图片缩略图横向ListView的Adapter
 * @author AllenZheng 
 * @date 2014年9月21日 上午9:45:25
 */
public class FoodImgThumbnailListAdapter extends BaseAdapter {

	private Context context;
	private List<String> data;
	private LayoutInflater layoutInflater;
	private int clickTemp = 0;

	public FoodImgThumbnailListAdapter(Context context, List<String> data) {
		super();
		this.context = context;
		this.data = data;
		layoutInflater = LayoutInflater.from(context);
	}

	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public String getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.listview_food_img_thumbnail_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		SysApplication.imageLoader.displayImage(Config.SERVER_IMAGE_URL + data.get(position % data.size()), viewHolder.getFoodImgIv(),
				SysApplication.options);

		if (clickTemp == position) {

			convertView.setAlpha(1.0f);
		} else {

			convertView.setAlpha(0.4f);
		}

		return convertView;
	}

	private static class ViewHolder {

		private ImageView foodImgIv;

		public ViewHolder(View view) {

			foodImgIv = (ImageView) view.findViewById(R.id.iv_food_img_thumbnail);
		}

		public ImageView getFoodImgIv() {
			return foodImgIv;
		}
	}
}
