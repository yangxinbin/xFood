package com.oxbix.xfood.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.adapter.OrderFoodListAdapter;
import com.oxbix.xfood.adapter.OrderListAdapter;
import com.oxbix.xfood.adapter.OrderSentFoodListAdapter;
import com.oxbix.xfood.dto.CustomerOrderDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.net.WSClientImpl;
import com.oxbix.xfood.net.WSClient.StatusListener;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.base.BaseFragment;
import com.oxbix.xfood.util.Utility;
import com.oxbix.xfood.widget.LoadingDialog;

/**
 * 购物车界面
 * 
 * @author AllenZheng
 * @date 2014年9月25日 下午5:25:13
 */
public class ShopingCartFragment extends BaseFragment {

	private TextView itemsTv;
	private TextView totalTv;
	private List<ShoppingCartItemDTO> _shoppingCartItems;

	private Button confirmOrderBtn;

	/** 未确认的订单列表 */
	private ListView listView;
	/** 已下的订单列表 */
	private ListView lv_order_list;
	/** 未确认的订单列表适配器 */
	private OrderFoodListAdapter orderFoodListAdapter;

	private FoodActivity foodActivity;

	/** 未确认的订单列表标题 */
	private TextView tv_order_items;

	private RelativeLayout layout_title_bar;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		foodActivity = (FoodActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_order, null);

		findViewById(view);
		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_confirm_order:

			if (SysApplication.shoppingCartItems.size() == 0)
				return;// 若没有订单，返回

			long currentOrderId = Config.getCurrentOrderId(foodActivity);
			if (currentOrderId == -1) {
				new WSClientImpl(getActivity()).createOrder(Config.getToken(getActivity()), Config.getCurrentCustomerName(getActivity()), 0, 0,
						SysApplication.currentTable.getTableId(), ShoppingCartService.createOrderJson(), new StatusListener() {

							LoadingDialog loadingDialog = new LoadingDialog(getActivity());

							@Override
							public void statusListener(int statusCode, Object objectResponse) {
								_setOrder(statusCode, objectResponse);
								loadingDialog.dismissDialog();
							}

							@Override
							public void preLoadListener() {
								loadingDialog.showDialog(R.string.create_order_dialog_message);
							}

						});
			} else {
				new WSClientImpl(getActivity()).addItemsToOrder(Config.getToken(getActivity()), currentOrderId, ShoppingCartService.createOrderJson(),
						new StatusListener() {

							LoadingDialog loadingDialog = new LoadingDialog(getActivity());

							@Override
							public void statusListener(int statusCode, Object objectResponse) {
								_setOrder(statusCode, objectResponse);
								loadingDialog.dismissDialog();
							}

							@Override
							public void preLoadListener() {
								loadingDialog.showDialog(R.string.create_order_dialog_message);
							}

						});
			}

			break;
		case R.id.btn_check_out:

			if (SysApplication.shoppingCartItems.size() == 0)
				return;// 若没有下订单，返回

			final AlertDialog dialog = new AlertDialog.Builder(foodActivity).create();
			dialog.show();
			View view = LayoutInflater.from(foodActivity).inflate(R.layout.dialog_alert_layout, null);
			dialog.setContentView(view);
			Window dialogWindow = dialog.getWindow();
			WindowManager m = (WindowManager) foodActivity.getSystemService(Context.WINDOW_SERVICE);
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
			WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
			p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
			p.width = (int) (d.getWidth() * 0.4); // 宽度设置为屏幕的0.8
			dialogWindow.setAttributes(p);

			TextView dialogMessage = (TextView) view.findViewById(R.id.tv_dialog_message);
			dialogMessage.setText(R.string.ask_checkout);
			Button confirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);
			confirmBtn.setText(R.string.yes);
			Button cancelBtn = (Button) view.findViewById(R.id.btn_dialog_cancel);
			cancelBtn.setText(R.string.cancel);

			confirmBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					SysApplication.shoppingCartItems.clear();
					SysApplication.customerOrderItems.clear();
				}
			});

			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			break;

		default:
			break;
		}
	}

	@Override
	protected void findViewById(View view) {

		layout_title_bar = (RelativeLayout) view.findViewById(R.id.layout_title_bar);
		if (SysApplication.skinColor.equals(Config.SKIN_CHOCOLATE)) {
			layout_title_bar.setBackgroundResource(R.drawable.bg_food_title);
		} else if (SysApplication.skinColor.equals(Config.SKIN_GREEN)) {
			layout_title_bar.setBackgroundResource(R.drawable.bg_green_food_title);
		} 

		listView = (ListView) view.findViewById(R.id.lv_order_food);
		lv_order_list = (ListView) view.findViewById(R.id.lv_order_list);
		itemsTv = (TextView) view.findViewById(R.id.tv_items);
		totalTv = (TextView) view.findViewById(R.id.tv_total);
		SysApplication.totalTv = totalTv;
		SysApplication.itemsTv = itemsTv;
		confirmOrderBtn = (Button) view.findViewById(R.id.btn_confirm_order);
		SysApplication.slidingMenu.setOnOpenedListener(new OnOpenedListener() {
			@Override
			public void onOpened() {
				init();
			}
		});

	}

	@Override
	protected void init() {

		// 获取订单
		_shoppingCartItems = new ArrayList<ShoppingCartItemDTO>();

		if (SysApplication.shoppingCartItems != null && SysApplication.shoppingCartItems.size() > 0) {

			for (Map<String, ShoppingCartItemDTO> shoppingCartItem : SysApplication.shoppingCartItems) {

				for (ShoppingCartItemDTO cartItem : shoppingCartItem.values()) {
					_shoppingCartItems.add(cartItem);
				}
			}

			itemsTv.setText(String.valueOf(SysApplication.shoppingCartItems.size()));
			totalTv.setText(Config.getCurrency(foodActivity) + ShoppingCartService.getTotal());
			orderFoodListAdapter = new OrderFoodListAdapter(foodActivity, _shoppingCartItems);
			listView.setAdapter(orderFoodListAdapter);

			setListener();

		}

	}

	@Override
	protected void setListener() {

		confirmOrderBtn.setOnClickListener(this);
	}

	private void _setOrder(int statusCode, Object objectResponse) {

		switch (statusCode) {
		case 200:

			CustomerOrderDTO orders = (CustomerOrderDTO) objectResponse;
			Config.cacheCurrentOrderID(getActivity().getBaseContext(), orders.getOrderId());
			// Toast toast = Toast.makeText(getActivity(),
			// "创建订单成功", Toast.LENGTH_LONG);
			// toast.setGravity(Gravity.CENTER, 0, 0);
			// toast.show();

			SysApplication.customerOrderItems = (ArrayList<CustomerOrderItemDTO>) orders.getOrderItems();

			Intent intent = new Intent(foodActivity, OrderSuccessActivtity.class);
			startActivityForResult(intent, Config.QUEST_CODE_OREDER_SUCCESS);

			SysApplication.shoppingCartItems.clear();
			_shoppingCartItems.clear();
			orderFoodListAdapter.notifyDataSetChanged(_shoppingCartItems);
			SysApplication.badgeView.setText(String.valueOf(ShoppingCartService.getFoodCount()));
			break;

		case 401:

			getActivity().finish();
			break;

		default:

			Toast toast2 = Toast.makeText(getActivity(), getResources().getString(R.string.create_order_error), Toast.LENGTH_LONG);
			toast2.setGravity(Gravity.CENTER, 0, 0);
			toast2.show();
			break;
		}

	}

}
