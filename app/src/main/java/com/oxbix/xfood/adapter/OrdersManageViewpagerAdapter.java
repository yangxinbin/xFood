package com.oxbix.xfood.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class OrdersManageViewpagerAdapter extends PagerAdapter{

	private Context mContext;
	private List<View> mViews = new ArrayList<View>();
	
	public OrdersManageViewpagerAdapter(Context context) {
		mContext = context;
	}
	
	public List<View> getAdapterData() {
		return mViews;
	}
	
	public void appendToList(View view, boolean isClearOld) {
		if(view == null) {
			return;
		} 
		if(isClearOld) {
			mViews.clear();
		}
		mViews.add(view);
	}
	
	public void appendToList(List<View> views, boolean isClearOld) {
		if (views == null) {
			return;
		}
		if (isClearOld) {
			mViews.clear();
		}
		mViews.addAll(views);
	}

	public void appendToTopList(View view, boolean isClearOld) {
		if (view == null) {
			return;
		}
		if (isClearOld) {
			mViews.clear();
		}
		mViews.add(0, view);
	}
	
	public void appendToListTop(List<View> views, boolean isClearOld) {
		if (views == null) {
			return;
		}
		if (isClearOld) {
			mViews.clear();
		}
		mViews.addAll(0, views);
	}
	
	public void update() {
		notifyDataSetChanged();
	}
	
	public void clear() {
		mViews.clear();
	}
	
	public void clearAndUpate() {
		clear();
		update();
	}

	@Override
	public int getCount() {
		return mViews == null ? 0 : mViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViews.get(position));	
		return mViews.get(position);
	}

}
