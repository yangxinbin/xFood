package com.oxbix.xfood.widget;

import com.oxbix.xfood.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrdersManageViewpager extends LinearLayout {

	private Context mContext;

	private LinearLayout mTabHost;
	private ImageView mUnderline;
	private ViewPager mViewpager;

	private TextView[] tv1;
	private TextView[] tv2; // 用来存储标题
	private int mTabWidth;

	private TabViewPagerChangeListener pagerChangeListener;

	// 记录当前位置（未设置选中前）
	private int currentPosition;

	public OrdersManageViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		inflate(context, R.layout.orders_manage_viewpager, this);
		mTabHost = (LinearLayout) findViewById(R.id.tab_host);
		mUnderline = (ImageView) findViewById(R.id.tab_underline);
		mViewpager = (ViewPager) findViewById(R.id.view_pager);
		// 设置缓存view的个数
		mViewpager.setOffscreenPageLimit(10);
	}

	/**
	 * 初始化Tab数据(要据不同需求更新此方法及布局)
	 * 
	 * @param txt1
	 *            Tab项标题1组,不可为null, 数量需和标题2数量保持一至
	 * @param txt2
	 *            Tab项标题2组,不可为null, 数量需和标题1数量保持一至
	 * @param parentWidth
	 *            父窗口宽度
	 * @param listener
	 *            对ViewPager页面更改的监听
	 */
	public void initTabs(String[] txt1, String[] txt2, int parentWidth, TabViewPagerChangeListener listener) {
		pagerChangeListener = listener;
		if (txt1 == null) {
			return;
		}
		tv1 = new TextView[txt1.length];
		if (txt2 != null) {
			tv2 = new TextView[txt2.length];
		}
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);

		// 每个tab项目的宽度
		mTabWidth = parentWidth / tv2.length;

		// 动态添加tab
		for (int i = 0; i < tv2.length; i++) {
			// tab布局
			LinearLayout tabTitleView = (LinearLayout) layoutInflater.inflate(R.layout.orders_manage_viewpager_tab, null);
			// 文本进行更新
			tv1[i] = (TextView) tabTitleView.findViewById(R.id.tabViewPager_text1);
			tv1[i].setText(txt1[i]);
			tv2[i] = (TextView) tabTitleView.findViewById(R.id.tabViewPager_text2);
			tv2[i].setText(txt2[i]);
			if (i == 0) {
				tv1[i].setTextColor(getResources().getColor(R.color.green));
				tv2[i].setTextColor(getResources().getColor(R.color.green));
			}
			// 设置这个tab布局
			LinearLayout.LayoutParams tabHostLayoutParams = new LinearLayout.LayoutParams(mTabWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
			tabHostLayoutParams.weight = 1;
			tabHostLayoutParams.gravity = Gravity.CENTER_VERTICAL;
			tabTitleView.setLayoutParams(tabHostLayoutParams);

			// 监听这个tab布局
			tabTitleView.setOnClickListener(new TabOnClickListener(i));

			// 将这个tab添加到Tabhost中
			mTabHost.addView(tabTitleView);
		}

		// 设置underline宽度，使得下划线与tab保持一致
		FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(mTabWidth, FrameLayout.LayoutParams.WRAP_CONTENT);
		frameLayoutParams.gravity = Gravity.BOTTOM;
		mUnderline.setLayoutParams(frameLayoutParams);
		mUnderline.setBackgroundDrawable(getResources().getDrawable(R.drawable.tabviewpager_indicator_selected));
		setTabCurrentItem(0, true);
	}

	public int getCurrentItem() {
		return mViewpager.getCurrentItem();
	}

	public void setTabCurrentItem(int position, boolean isclick) {
		if (isclick) {
			int currentPosition = mViewpager.getCurrentItem();
			// 设置当前选中位置
			mViewpager.setCurrentItem(position);
			// 记录当前位置（以设置选中后）
			int nextposition = mViewpager.getCurrentItem();
			// underline移动动画
			mUnderline.startAnimation(new UnderlineTranslateAnimation(currentPosition * mTabWidth, nextposition * mTabWidth, 0, 0));

		} else {
			// 设置当前选中位置
			mViewpager.setCurrentItem(position);
			// 记录当前位置（以设置选中后）
			int nextposition = mViewpager.getCurrentItem();
			// underline移动动画
			mUnderline.startAnimation(new UnderlineTranslateAnimation(currentPosition * mTabWidth, nextposition * mTabWidth, 0, 0));
		}
	}

	public void setAdapter(PagerAdapter pagerAdapter) {
		mViewpager.setAdapter(pagerAdapter);
		mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				setTabCurrentItem(position, false);
				setTabCurrentItemColor(position);
				currentPosition = position;
				if (pagerChangeListener != null) {
					pagerChangeListener.onPageSelected(position);
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (pagerChangeListener != null) {
					pagerChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (pagerChangeListener != null) {
					pagerChangeListener.onPageScrollStateChanged(state);
				}
			}
		});
	}

	private class TabOnClickListener implements OnClickListener {
		private int viewPosition = -1;

		public TabOnClickListener(int position) {
			viewPosition = position;
		}

		@Override
		public void onClick(View v) {
			setTabCurrentItem(viewPosition, true);
			setTabCurrentItemColor(viewPosition);
		}
	}

	private void setTabCurrentItemColor(int currentPosition) {
		for (int i = 0; i < mTabHost.getChildCount(); i++) {
			LinearLayout linear = (LinearLayout) mTabHost.getChildAt(i);
			TextView tv = (TextView) linear.findViewById(R.id.tabViewPager_text1);
			TextView tv1 = (TextView) linear.findViewById(R.id.tabViewPager_text2);
			if (i == currentPosition) {
				tv.setTextColor(getResources().getColor(R.color.green));
				tv1.setTextColor(getResources().getColor(R.color.green));
			} else {
				tv.setTextColor(getResources().getColor(R.color.txt_black));
				tv1.setTextColor(getResources().getColor(R.color.txt_black));
			}
		}
	}

	private class UnderlineTranslateAnimation extends TranslateAnimation {

		public UnderlineTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
			super(fromXDelta, toXDelta, fromYDelta, toYDelta);
			setFillAfter(true);
			setDuration(300);
		}
	}

	public interface TabViewPagerChangeListener {
		public void onPageSelected(int position);

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

		public void onPageScrollStateChanged(int state);
	}

	public void setTabItemWordNum(String[] txt1) {
		for (int i = 0; i < tv1.length; i++) {
			tv1[i].setText(txt1[i]);
		}
	}

}
