package com.oxbix.xfood.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.AuthDTO;
import com.oxbix.xfood.dto.FoodCategoryDTO;
import com.oxbix.xfood.dto.RoomDTO;
import com.oxbix.xfood.net.WSClient.StatusListener;
import com.oxbix.xfood.net.WSClientImpl;
import com.oxbix.xfood.ui.base.BaseActivity;
import com.oxbix.xfood.widget.LoadingDialog;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends BaseActivity {

//	private EditText et_customer;
	private Button btn_checkin;
	
	private ImageButton OxbixIconImgbtn;
	private int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login_welcome);
		init();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_oxbix_icon:
			i++;
			if(i == 10){
				finish();
			}
			break;
		case R.id.btn_checkin:
//			String customerName = et_customer.getText().toString();
//			if (customerName == null || customerName.equals("")) {
//				// 输入为空，弹出提示
//				Toast.makeText(WelcomeActivity.this, R.string.input_hint_message, 1000).show();
//				return;
//			} else {
				Config.cacheCurrentOrderID(getBaseContext(), -1L);
//				Config.cacheCurrentCustomerName(getBaseContext(), customerName);
//			}

			final LoadingDialog dialog = new LoadingDialog(this);
			new WSClientImpl(WelcomeActivity.this).RequestCheckIn(Config.getToken(WelcomeActivity.this), SysApplication.currentTable.getTableId(),
					new StatusListener() {

						@Override
						public void statusListener(int statusCode, Object objectResponse) {
							dialog.dismissDialog();
							switch (statusCode) {
							case 200:
								String str = (String) objectResponse;
								if (str.equals(Config.STATUS_TABLE_FREE)) { // 如果桌子自由
									loadCategories();
								} else if (str.equals(Config.STATUS_TABLE_RESERVED)) {// 如果桌子被占
									Toast toast1 = Toast.makeText(WelcomeActivity.this, R.string.table_alread_checked, Toast.LENGTH_LONG);
									toast1.setGravity(Gravity.CENTER, 0, 0);
									toast1.show();
								}
								break;
							case 401: // token不对
								break;

							default: // 访问失败
								Toast toast1 = Toast.makeText(WelcomeActivity.this, R.string.loading_failure, Toast.LENGTH_LONG);
								toast1.setGravity(Gravity.CENTER, 0, 0);
								toast1.show();
								break;
							}
						}

						@Override
						public void preLoadListener() {
							dialog.showDialog(R.string.loading_message);
						}
					});

			break;

		default:
			break;
		}

	}

	@Override
	protected void findViewById() {
//		et_customer = (EditText) findViewById(R.id.et_customer);
		btn_checkin = (Button) findViewById(R.id.btn_checkin);
		OxbixIconImgbtn = (ImageButton) findViewById(R.id.ib_oxbix_icon);
	}

	@Override
	protected void init() {
		findViewById();
		setListener();
	}

	@Override
	protected void setListener() {
		btn_checkin.setOnClickListener(this);
		OxbixIconImgbtn.setOnClickListener(this);
	}

	private void loadCategories() {
		{
			new WSClientImpl(WelcomeActivity.this).getCategories(Config.getToken(WelcomeActivity.this), new StatusListener() {

				@Override
				public void statusListener(int statusCode, Object objectResponse) {

					switch (statusCode) {
					case 200:
						SysApplication.categorys = (List<FoodCategoryDTO>) objectResponse;
						break;

					case 401: // token不对
						break;

					default: // 访问失败
						Toast toast1 = Toast.makeText(WelcomeActivity.this, R.string.loading_failure, Toast.LENGTH_LONG);
						toast1.setGravity(Gravity.CENTER, 0, 0);
						toast1.show();
						break;
					}
				}

				@Override
				public void preLoadListener() {
					
				}
			});

			Intent intent = new Intent(WelcomeActivity.this, FoodActivity.class);
			startActivity(intent);
		}
	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_BACK: // 点击物理返回键退出
//
//			break;
//		}
//		return false;
//	}

}
