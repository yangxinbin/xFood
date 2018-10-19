package com.oxbix.xfood.adapter;

import java.util.ArrayList;
import java.util.List;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.photoview.PhotoView;
import com.oxbix.xfood.ui.FoodActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class MyAdapter extends PagerAdapter {

	private Context context;
	private List<String> foodImgs = new ArrayList<String>();
	private int conunt;

	public MyAdapter(Context context, List<String> foodImgs) {
		this.context = context;
		this.foodImgs = foodImgs;
		conunt = foodImgs.size();
	}

	@Override
	public int getCount() {
		return conunt;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		PhotoView photoView = new PhotoView(context);
		SysApplication.imageLoader.displayImage(Config.SERVER_IMAGE_URL + foodImgs.get(position), photoView, SysApplication.options);
		container.addView(photoView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		return photoView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	public int getConunt() {
		return conunt;
	}

	public void setConunt(int conunt) {
		this.conunt = conunt;
	}

}
