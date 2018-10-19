package com.oxbix.xfood.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * 登录界面
 * 
 * @author AllenZheng
 * @date 2014年9月25日 下午5:23:58
 */
public class LoginActivity extends BaseActivity {

	private Button loginBtn;
	private Button exitBtn;
	private EditText loginUserNameEt;
	private EditText loginPasswordEt;

	/** 登陆返回信息 */
	private AuthDTO authInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		findViewById();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_login:

			if (TextUtils.isEmpty(loginUserNameEt.getText())) {
				Toast toast5 = Toast.makeText(this, R.string.username_cannot_be_empty, Toast.LENGTH_LONG);
				toast5.setGravity(Gravity.CENTER, 0, 0);
				toast5.show();
				return;
			}

			if (TextUtils.isEmpty(loginPasswordEt.getText())) {
				Toast toast6 = Toast.makeText(this, R.string.password_cannot_be_empty, Toast.LENGTH_LONG);
				toast6.setGravity(Gravity.CENTER, 0, 0);
				toast6.show();
				return;
			}

			final LoadingDialog dialog = new LoadingDialog(this);
			new WSClientImpl(this).auth(loginUserNameEt.getText().toString().trim(), loginPasswordEt.getText().toString().trim(), new StatusListener() {

				@Override
				public void statusListener(int statusCode, Object objectResponse) {

					switch (statusCode) {
					case 200: // 登录成功
						if (objectResponse != null) {
							// 保存用户信息
							authInfo = (AuthDTO) objectResponse;

							// 缓存token
							Config.cacheAuthInfo(LoginActivity.this, authInfo);

							if (authInfo.getUserType().equals(Config.STATUS_USER_KITCHEN)) {
								dialog.dismissDialog();
								// 跳转到餐桌设置界面
								Intent intent = new Intent(LoginActivity.this, KitchenActivity.class);
								startActivity(intent);
							} else if (authInfo.getUserType().equals(Config.STATUS_USER_WAITER)) {

								new WSClientImpl(LoginActivity.this).getRooms(authInfo.getLocation(), new StatusListener() {

									@Override
									public void statusListener(int statusCode, Object objectResponse) {

										switch (statusCode) {
										case 200: // 获取rooms成功

											if (objectResponse != null) {

												SysApplication.rooms = (List<RoomDTO>) objectResponse;

												new WSClientImpl(LoginActivity.this).getCategories(Config.getToken(LoginActivity.this), new StatusListener() {

													@Override
													public void statusListener(int statusCode, Object objectResponse) {

														switch (statusCode) {
														case 200:

															SysApplication.categorys = (List<FoodCategoryDTO>) objectResponse;

															// 跳转到餐桌设置界面
															Intent intent = new Intent(LoginActivity.this, RestaurantSettingActivity.class);
															startActivity(intent);
															loginPasswordEt.getText().clear();
															dialog.dismissDialog();
															break;

														case 401: // token不对

															dialog.dismissDialog();
															break;

														default: // 访问失败

															dialog.dismissDialog();
															Toast toast1 = Toast.makeText(LoginActivity.this, R.string.loading_failure, Toast.LENGTH_LONG);
															toast1.setGravity(Gravity.CENTER, 0, 0);
															toast1.show();
															break;
														}
													}

													@Override
													public void preLoadListener() {
														dialog.showDialog(R.string.loading_food_data_message);
													}
												});

											}
											break;

										case 401: // token不对

											dialog.dismissDialog();
											break;

										default: // 无网络或服务器出错

											dialog.dismissDialog();
											Toast toast2 = Toast.makeText(LoginActivity.this, R.string.access_server_failure, Toast.LENGTH_LONG);
											toast2.setGravity(Gravity.CENTER, 0, 0);
											toast2.show();
											break;
										}
									}

									@Override
									public void preLoadListener() {

										dialog.showDialog(R.string.loading_room_data_message);
									}
								});
							}
						}

						break;

					case 401: // 用户名或密码不正确

						Toast toast3 = Toast.makeText(LoginActivity.this, R.string.login_failure_userinfo_error_message, Toast.LENGTH_LONG);
						toast3.setGravity(Gravity.CENTER, 0, 0);
						toast3.show();
						dialog.dismissDialog();
						break;

					default: // 无网络或服务器出错

						Toast toast4 = Toast.makeText(LoginActivity.this, R.string.login_failure_server_error_message, Toast.LENGTH_LONG);
						toast4.setGravity(Gravity.CENTER, 0, 0);
						toast4.show();
						dialog.dismissDialog();
						break;
					}

				}

				@Override
				public void preLoadListener() {

					dialog.showDialog(R.string.login_loading_message);
				}
			});

			break;

		case R.id.btn_exit:

			System.exit(0);
			break;

		default:
			break;
		}
	}

	@Override
	protected void findViewById() {

		loginBtn = (Button) findViewById(R.id.btn_login);
		exitBtn = (Button) findViewById(R.id.btn_exit);
		loginUserNameEt = (EditText) findViewById(R.id.et_login_username);
		loginPasswordEt = (EditText) findViewById(R.id.et_login_password);
		init();
	}

	@Override
	protected void init() {
		setListener();
	}

	@Override
	protected void setListener() {

		loginBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}

}
