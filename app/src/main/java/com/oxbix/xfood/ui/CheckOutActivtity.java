package com.oxbix.xfood.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
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
 * 结算界面
 * 
 * @author adam.lu
 * @date 2015年2月26日 15:51
 */
public class CheckOutActivtity extends BaseActivity {

	private Button btn_cancel;
	private Button btn_check_out;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_check_out);

		findViewById();
		init();
		setListener();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_check_out:
			if (SysApplication.customerOrderItems == null || SysApplication.customerOrderItems.isEmpty()) {
				Toast.makeText(CheckOutActivtity.this, R.string.no_order, Toast.LENGTH_LONG).show();
				return;
			}
			final LoadingDialog dialog = new LoadingDialog(this);
			new WSClientImpl(CheckOutActivtity.this).requestCheckOut(Config.getToken(CheckOutActivtity.this), Config.getCurrentOrderId(CheckOutActivtity.this),
					SysApplication.currentTable.getTableId(), new StatusListener() {

						@Override
						public void statusListener(int statusCode, Object objectResponse) {
							dialog.dismissDialog();
							switch (statusCode) {
							case 200:
								// showDailog();
								Intent intent = new Intent(CheckOutActivtity.this, GradeActivtity.class);
								startActivityForResult(intent, Config.QUEST_CODE_GRADE);
								break;
							case 401: // token不对
								break;

							default: // 访问失败
								Toast toast1 = Toast.makeText(CheckOutActivtity.this, R.string.loading_failure, Toast.LENGTH_LONG);
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
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_check_out = (Button) findViewById(R.id.btn_check_out);
	}

	@Override
	protected void init() {

	}

	@Override
	protected void setListener() {
		btn_cancel.setOnClickListener(this);
		btn_check_out.setOnClickListener(this);
	}

	private void showDailog() {
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_alert_layout, null);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		WindowManager m = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * 0.4); // 宽度设置为屏幕的0.8
		dialogWindow.setAttributes(p);

		TextView dialogMessage = (TextView) view.findViewById(R.id.tv_dialog_message);
		dialogMessage.setText(R.string.confirm_success);
		Button confirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);
		confirmBtn.setText(R.string.thank_you);

		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				setResult(Config.RETURN_CODE_CHECK_OUT, getIntent());
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Config.RETURN_CODE_GRADE) {
			setResult(Config.RETURN_CODE_CHECK_OUT, getIntent());
			finish();
		}
	}

}
