package com.oxbix.xfood.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.adapter.OrderListAdapter;
import com.oxbix.xfood.adapter.OrderSentFoodListAdapter;
import com.oxbix.xfood.dto.CustomerOrderDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.net.WSClientImpl;
import com.oxbix.xfood.net.WSClient.StatusListener;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.base.BaseActivity;
import com.oxbix.xfood.widget.LoadingDialog;

/**
 * 订单完成界面
 * 
 * @author adam.lu
 * @date 2015年2月26日 15:51
 */
public class OrderSuccessActivtity extends BaseActivity {

	private Button btn_view_order;
	private Button btn_order_more;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_order_success);

		findViewById();
		init();
		setListener();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_view_order:
			setResult(Config.RETURN_CODE_VIEW_OREDER, getIntent());
			finish();
			break;
		case R.id.btn_order_more:
			setResult(Config.RETURN_CODE_OREDER_MORE, getIntent());
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void findViewById() {
		btn_view_order = (Button) findViewById(R.id.btn_view_order);
		btn_order_more = (Button) findViewById(R.id.btn_order_more);
	}

	@Override
	protected void init() {

	}

	@Override
	protected void setListener() {
		btn_view_order.setOnClickListener(this);
		btn_order_more.setOnClickListener(this);
	}

}
