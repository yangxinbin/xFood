package com.oxbix.xfood.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
 * 评分界面
 * 
 * @author adam.lu
 * @date 2015年2月26日 15:51
 */
public class GradeActivtity extends BaseActivity {

	private Button btn_thanks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_grade);

		findViewById();
		init();
		setListener();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_thanks:
			setResult(Config.RETURN_CODE_GRADE, getIntent());
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void findViewById() {
		btn_thanks = (Button) findViewById(R.id.btn_thanks);
	}

	@Override
	protected void init() {

	}

	@Override
	protected void setListener() {
		btn_thanks.setOnClickListener(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK: // 点击物理返回键退出
			setResult(Config.RETURN_CODE_GRADE, getIntent());
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
