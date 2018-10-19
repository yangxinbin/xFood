package com.oxbix.xfood.ui;

import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityHelper;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;

/**
 * 食物展示界面（包括食物列表和食物详情页面，还有购物车页面）
 * 
 * @author AllenZheng
 * @date 2014年9月25日 下午2:40:56
 */
public class FoodActivity extends SlidingFragmentActivity {

	/** SlidingMenu对象 **/
	private SlidingMenu slidingMenu;

	private ShopingCartFragment shopingCartFragment;
	private FoodListFragment foodListFragment;

	public final String action = "com.oxbix.xfood.language";

	public boolean flag = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置SlidingNMenu中默认展示的前端页面
		setContentView(R.layout.frame_slidingmenu_content);
		// 设置SlidingMenu打开menu后的界面
		setBehindContentView(R.layout.frame_slidingmenu_menu);

		slidingMenu = getSlidingMenu();
		// SlidingMenu滑出时默认展示的前端页面显示的剩余宽度
		slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		// SlidingMenu打开menu时的触摸模式，TOUCHMODE_MARGIN表示触摸边缘才能滑动出menu
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 设置SlidingMenu展示的位置
		slidingMenu.setMode(SlidingMenu.RIGHT);

		SysApplication.slidingMenu = slidingMenu;

		shopingCartFragment = new ShopingCartFragment();
		foodListFragment = new FoodListFragment();
		// 设置 SlidingMenu的内容
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content, foodListFragment);
		fragmentTransaction.replace(R.id.menu, shopingCartFragment);
		fragmentTransaction.commit();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU: // 点击物理菜单按键，显示菜单
			toggle();
			break;
		case KeyEvent.KEYCODE_BACK: // 点击物理返回键退出
			FragmentManager fm = getSupportFragmentManager();
			if (fm.getBackStackEntryCount() < 1) {
				return false;
			}
			// return false;
			// FragmentManager fm = getSupportFragmentManager();
			// if (fm.getBackStackEntryCount() < 1) {
			//
			// final AlertDialog minusRemoveDialog = new
			// AlertDialog.Builder(this).create();
			// minusRemoveDialog.show();
			// View view =
			// LayoutInflater.from(this).inflate(R.layout.dialog_alert_layout,
			// null);
			// minusRemoveDialog.setContentView(view);
			// Window dialogWindow = minusRemoveDialog.getWindow();
			// WindowManager m = (WindowManager)
			// this.getSystemService(Context.WINDOW_SERVICE);
			// Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
			// WindowManager.LayoutParams p = dialogWindow.getAttributes(); //
			// 获取对话框当前的参数值
			// p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
			// p.width = (int) (d.getWidth() * 0.4); // 宽度设置为屏幕的0.8
			// dialogWindow.setAttributes(p);
			//
			// TextView dialogMessage = (TextView)
			// view.findViewById(R.id.tv_dialog_message);
			// dialogMessage.setText(R.string.logout_dialog_message);
			// Button confirmBtn = (Button)
			// view.findViewById(R.id.btn_dialog_confirm);
			// confirmBtn.setText(R.string.logout);
			// Button cancelBtn = (Button)
			// view.findViewById(R.id.btn_dialog_cancel);
			// cancelBtn.setText(R.string.cancel);
			//
			// confirmBtn.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// SysApplication.shoppingCartItems.clear();
			// FoodActivity.this.finish();
			// }
			// });
			//
			// cancelBtn.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// minusRemoveDialog.dismiss();
			// }
			// });
			// }
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// foodListFragment.hideOrNotIcon();
		if (resultCode == Config.RETURN_CODE_VIEW_OREDER) { // 查看订单
			toggle();
			getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			Intent intent = new Intent(FoodActivity.this, MyOrderActivity.class);
			startActivityForResult(intent, Config.QUEST_CODE_VIEW_OREDER);
		} else if (resultCode == Config.RETURN_CODE_OREDER_MORE) {
			toggle();
			getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		} else if (resultCode == Config.RETURN_CODE_CHECK_OUT) {
			finish();
		}
	}

	public void changeLanguage(boolean language) {
		Resources resources = getResources();// 获得res资源对象
		Configuration config = resources.getConfiguration();// 获得设置对象
		DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
		if (language) {
			config.locale = Locale.ENGLISH;
		} else {
			config.locale = Locale.CHINA;
		}
		resources.updateConfiguration(config, dm);
	}

	// public void refreshShoppingCart(){
	// FragmentTransaction fragmentTransaction =
	// getSupportFragmentManager().beginTransaction();
	// fragmentTransaction.replace(R.id.menu, shopingCartFragment);
	// fragmentTransaction.commit();
	// }

	public void display(int sound) { 
		AudioManager am = (AudioManager) getSystemService(this.AUDIO_SERVICE);// 实例化
		float audioMaxVolum = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 音效最大值
		float audioCurrentVolum = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		float audioRatio = audioCurrentVolum / audioMaxVolum;
		SysApplication.sp.play(SysApplication.map.get(sound%8), audioRatio,// 左声道音量
				audioRatio,// 右声道音量
				1, // 优先级
				0,// 循环播放次数
				1);// 回放速度，该值在0.5-2.0之间 1为正常速度
	}
}
