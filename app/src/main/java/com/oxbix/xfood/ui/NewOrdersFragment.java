package com.oxbix.xfood.ui;

import java.util.ArrayList;
import java.util.List;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.adapter.OrderFoodListAdapter;
import com.oxbix.xfood.adapter.OrderListAdapter;
import com.oxbix.xfood.adapter.OrderSentFoodListAdapter;
import com.oxbix.xfood.dto.CustomerOrderDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.net.WSClientImpl;
import com.oxbix.xfood.net.WSClient.StatusListener;
import com.oxbix.xfood.ui.KitchenActivity;
import com.oxbix.xfood.ui.MyOrderActivity;
import com.oxbix.xfood.ui.base.BaseFragment;
import com.oxbix.xfood.util.Utility;
import com.oxbix.xfood.widget.LoadingDialog;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class NewOrdersFragment extends BaseFragment {

	private ListView lv_order_food;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.view_new_orders_manage, null);

		findViewById(view);
		init();
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	@Override
	protected void findViewById(View view) {
		lv_order_food = (ListView) view.findViewById(R.id.lv_order_food);

	}

	public void init() {
		final LoadingDialog dialog = new LoadingDialog(getActivity());
		// New items list management
		new WSClientImpl(getActivity()).listCurrentOrderItems(Config.getToken(getActivity()), 0, new StatusListener() {

			@Override
			public void statusListener(int statusCode, Object objectResponse) {

				dialog.dismissDialog();
				switch (statusCode) {
				case 200:
					OrderListAdapter orderListAdapter = new OrderListAdapter(getActivity(), (ArrayList<CustomerOrderItemDTO>) objectResponse);
					lv_order_food.setAdapter(orderListAdapter);
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
		// TODO Auto-generated method stub

	}

}
