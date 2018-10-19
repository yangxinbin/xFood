package com.oxbix.xfood.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnimationUtil {
	
	private static int AnimationDuration = 3000;
	private static int goodsNumber = 0;
	
	/**
	 * 创建动画层
	 * @param context  上下文对象
	 * @return
	 */
	private static ViewGroup createAnimLayout(Context context) {

		Activity activity = (Activity) context;
		ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	/**
	 * 添加视图到动画层
	 * @param vg
	 * @param view
	 * @param location
	 * @return
	 */
	private static View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {

		int x = location[0];
		int y = location[1];
		vg.addView(view);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	/**
	 * 设置动画
	 * @param v
	 * @param v
	 * @param tvNumber
	 * @param context
	 */
	public static void setAnim(Context context,View v, final TextView tvNumber) {

		Animation mScaleAnimation = new ScaleAnimation(1.5f, 0.1f, 1.5f, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f);
		mScaleAnimation.setDuration(AnimationDuration);
		mScaleAnimation.setFillAfter(true);

		int[] start_location = new int[2];
		v.getLocationInWindow(start_location);
		ViewGroup vg = (ViewGroup) v.getParent();
		vg.removeView(v);
		// 将组件添加到动画层上
		View view = addViewToAnimLayout(createAnimLayout(context), v, start_location);
		int[] end_location = new int[2];
		tvNumber.getLocationInWindow(end_location);
		// 计算位移
		int endX = end_location[0];
		int endY = end_location[1] - start_location[1];

		Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);// 移动
		mTranslateAnimation.setDuration(AnimationDuration);

		AnimationSet mAnimationSet = new AnimationSet(false);
		// 设为false,不然组件动画结束后，不会归位。
		mAnimationSet.setFillAfter(false);
		mAnimationSet.addAnimation(mScaleAnimation);
		mAnimationSet.addAnimation(mTranslateAnimation);
		view.startAnimation(mAnimationSet);

		mTranslateAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tvNumber.setText(goodsNumber + "");
			}
		});
	}

}
