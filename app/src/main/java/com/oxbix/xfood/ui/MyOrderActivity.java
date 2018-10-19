package com.oxbix.xfood.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.adapter.OrderFoodListAdapter;
import com.oxbix.xfood.adapter.OrderSentFoodListAdapter;
import com.oxbix.xfood.dto.CustomerOrderDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.net.WSClientImpl;
import com.oxbix.xfood.net.WSClient.StatusListener;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.base.BaseActivity;
import com.oxbix.xfood.util.Utility;
import com.oxbix.xfood.widget.LoadingDialog;

public class MyOrderActivity extends BaseActivity {

	private List<ShoppingCartItemDTO> _shoppingCartOrderedItems;

	/** 已下的订单列表 */
	private ListView lv_order_list;
	/** 已下的订单适配器 */
	private OrderSentFoodListAdapter orderSentFoodListAdapter;
	/** 结账 */
	private Button btn_check_out;

	private ImageButton btn_back;

	private TextView tv_total;
	private TextView tv_items;
	
	private RelativeLayout layout_title_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_my_order);

		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_check_out:
			Intent intent = new Intent(MyOrderActivity.this, CheckOutActivtity.class);
			startActivityForResult(intent, Config.QUEST_CODE_CHECK_OUT);
			break;
		default:
			break;
		}

	}

	@Override
	protected void findViewById() {
		btn_check_out = (Button) findViewById(R.id.btn_check_out);
		lv_order_list = (ListView) findViewById(R.id.lv_order_list);
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		tv_total = (TextView) findViewById(R.id.tv_total);
		tv_items = (TextView) findViewById(R.id.tv_items);
		
		layout_title_bar = (RelativeLayout) findViewById(R.id.layout_title_bar);
		
		if(SysApplication.skinColor.equals(Config.SKIN_CHOCOLATE)){
			layout_title_bar.setBackgroundResource(R.drawable.bg_food_title);	
		}else if(SysApplication.skinColor.equals(Config.SKIN_GREEN)){	
			layout_title_bar.setBackgroundResource(R.drawable.bg_green_food_title);	
		}
	}

	@Override
	protected void init() {
		findViewById();
		setListener();

		final LoadingDialog dialog = new LoadingDialog(this);
		// New items list management
		new WSClientImpl(this).getOrder(Config.getToken(this), Config.getCurrentOrderId(this), new StatusListener() {

			@Override
			public void statusListener(int statusCode, Object objectResponse) {

				dialog.dismissDialog();
				switch (statusCode) {
				case 200:
					CustomerOrderDTO orderDTO = (CustomerOrderDTO) objectResponse;
					List<CustomerOrderItemDTO> customerOrders = orderDTO.getOrderItems();
					_shoppingCartOrderedItems = new ArrayList<ShoppingCartItemDTO>();
					for (CustomerOrderItemDTO orderItem : customerOrders) {
						_shoppingCartOrderedItems.add(new ShoppingCartItemDTO(Utility.searchFoodItems(orderItem.getItemId()), orderItem.getQuantity(),
								orderItem.getStatus()));
					}
					orderSentFoodListAdapter = new OrderSentFoodListAdapter(MyOrderActivity.this, _shoppingCartOrderedItems);
					lv_order_list.setAdapter(orderSentFoodListAdapter);
					// 获得订单总价
					double j = 0;
					for (int i = 0; i < _shoppingCartOrderedItems.size(); i++) {
						if(_shoppingCartOrderedItems.get(i) != null && !_shoppingCartOrderedItems.get(i).equals("")){
							int quantity = _shoppingCartOrderedItems.get(i).getQuantity();
							if(_shoppingCartOrderedItems.get(i).getFood() != null && !_shoppingCartOrderedItems.get(i).getFood().equals("")){
								double price = _shoppingCartOrderedItems.get(i).getFood().getDefaultPrice();
								j += quantity * price;						
							}
						}
					}
					tv_items.setText(_shoppingCartOrderedItems.size() + "");
					tv_total.setText(Config.getCurrency(MyOrderActivity.this) + j);

					break;
				}

			}

			@Override
			public void preLoadListener() {
				dialog.showDialog(R.string.loading_my_order_message);
			}

		});

	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_check_out.setOnClickListener(this);
		tv_items.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Config.RETURN_CODE_CHECK_OUT) {
			setResult(Config.RETURN_CODE_CHECK_OUT, getIntent());
			finish();
		}
	}

}
