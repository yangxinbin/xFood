package com.oxbix.xfood.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.adapter.OrderFoodListAdapter;
import com.oxbix.xfood.adapter.OrderSentFoodListAdapter;
import com.oxbix.xfood.adapter.OrdersManageViewpagerAdapter;
import com.oxbix.xfood.adapter.OrdersPagerAdapter;
import com.oxbix.xfood.dto.CustomerOrderDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.net.WSClientImpl;
import com.oxbix.xfood.net.WSClient.StatusListener;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.base.BaseActivity;
import com.oxbix.xfood.util.Utility;
import com.oxbix.xfood.widget.OrdersManageViewpager;
import com.oxbix.xfood.widget.LoadingDialog;
import com.oxbix.xfood.widget.OrdersManageViewpager.TabViewPagerChangeListener;

public class KitchenActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {

	private static final int POSITION_NEW = 0;
	private static final int POSITION_RUNNING = 1;
	private static final int POSITION_FINISHED = 2;

	private RadioButton newOrdersRadioButton;
	private RadioButton runningOrdersRadioButton;
	private RadioButton finishedOrdersRadioButton;

	private RadioGroup ordersRadioGroup;

	private ViewPager mViewPager;

	private Button LogOutBtn;
	private Button ShowByGroupBtn;

	private NewOrdersFragment newOrdersFragment;
	private RunningOrdersFragment runningOrdersFragment;
	private FinishedOrdersFragment finishedOrdersFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_kitchen);

		init();
	}

	@Override
	public void onClick(View v) {
		if (v instanceof RadioButton) {
			mViewPager.setCurrentItem((Integer) v.getTag());
		}
		switch (v.getId()) {
		case R.id.btn_log_out:
			final AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.show();
			View view = LayoutInflater.from(this).inflate(R.layout.dialog_alert_layout, null);
			dialog.setContentView(view);
			Window dialogWindow = dialog.getWindow();
			WindowManager m = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
			WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
			p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
			p.width = (int) (d.getWidth() * 0.4); // 宽度设置为屏幕的0.8
			dialogWindow.setAttributes(p);

			TextView dialogMessage = (TextView) view.findViewById(R.id.tv_dialog_message);
			dialogMessage.setText(R.string.logout_dialog_message);
			Button confirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);
			confirmBtn.setText(R.string.logout);
			Button cancelBtn = (Button) view.findViewById(R.id.btn_dialog_cancel);
			cancelBtn.setText(R.string.cancel);

			confirmBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});

			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			break;
		case R.id.btn_show_by_group:

			break;
		default:
			break;
		}
	}

	protected void findViewById() {
		newOrdersRadioButton = (RadioButton) findViewById(R.id.tv_tab_new_orders);
		runningOrdersRadioButton = (RadioButton) findViewById(R.id.tv_tab_running_orders);
		finishedOrdersRadioButton = (RadioButton) findViewById(R.id.tv_tab_finished_orders);
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		ordersRadioGroup = (RadioGroup) findViewById(R.id.rd_orders);

		ShowByGroupBtn = (Button) findViewById(R.id.btn_show_by_group);
		LogOutBtn = (Button) findViewById(R.id.btn_log_out);
	}

	protected void init() {
		findViewById();
		setListener();

		initPager();

	}

	protected void setListener() {
		mViewPager.setOnPageChangeListener(this);
		newOrdersRadioButton.setTag(POSITION_NEW);
		newOrdersRadioButton.setOnClickListener(this);
		runningOrdersRadioButton.setTag(POSITION_RUNNING);
		runningOrdersRadioButton.setOnClickListener(this);
		finishedOrdersRadioButton.setTag(POSITION_FINISHED);
		finishedOrdersRadioButton.setOnClickListener(this);

		ShowByGroupBtn.setOnClickListener(this);
		LogOutBtn.setOnClickListener(this);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case POSITION_NEW:
			ordersRadioGroup.check(newOrdersRadioButton.getId());
			newOrdersFragment.init();
			break;
		case POSITION_RUNNING:
			ordersRadioGroup.check(runningOrdersRadioButton.getId());
			runningOrdersFragment.init();
			break;
		case POSITION_FINISHED:
			ordersRadioGroup.check(finishedOrdersRadioButton.getId());
			finishedOrdersFragment.init();
			break;
		}
	}

	private void initPager() {

		newOrdersFragment = new NewOrdersFragment();
		runningOrdersFragment = new RunningOrdersFragment();
		finishedOrdersFragment = new FinishedOrdersFragment();

		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(newOrdersFragment);
		fragments.add(runningOrdersFragment);
		fragments.add(finishedOrdersFragment);

		OrdersPagerAdapter adapter = new OrdersPagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setOffscreenPageLimit(10);
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(0);
	}

}
