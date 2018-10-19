package com.oxbix.xfood.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.CustomerOrderDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.FoodDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.net.WSClientImpl;
import com.oxbix.xfood.net.WSClient.StatusListener;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.KitchenActivity;
import com.oxbix.xfood.widget.LoadingDialog;

public class FinishedOrderListAdapter extends BaseAdapter {

	private Context context;
	private List<CustomerOrderItemDTO> data;
	private LayoutInflater layoutInflater;
	private ViewHolder viewHolder = null;
	private int position = -1;

	public FinishedOrderListAdapter(Context context, List<CustomerOrderItemDTO> data) {
		super();
		this.context = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return (data != null) ? data.size() : 0;
	}

	@Override
	public CustomerOrderItemDTO getItem(int position) {

		return (data != null) ? data.get(position) : null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		this.position = position;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.finished_order_list_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 编号
		// viewHolder.getNoTv().setText(String.valueOf(position + 1));

		// 食物名称
		if (SysApplication.currentLanguage) {

			viewHolder.getFoodNameTv().setText(getItem(position).getItemName());
		} else {

			viewHolder.getFoodNameTv().setText(getItem(position).getItemName());
		}

		// 食物价格
		// viewHolder.getPriceTv().setText(String.valueOf(getItem(position).getPrice()));

		// 食物数量
		viewHolder.getQuantityTv().setText(String.valueOf(getItem(position).getQuantity()));

		// 金额小计
		// viewHolder.getCountTv().setText(String.valueOf(getItem(position).getQuantity()
		// * getItem(position).getPrice()));
		viewHolder.gettableTv().setText(String.valueOf(getItem(position).getOrderTable()));
		
		viewHolder.getUndoBtn().setOnClickListener(new OnClickListener() {
			int _position=position;
			@Override
			public void onClick(View v) {
				final LoadingDialog dialog = new LoadingDialog(context);
				// New items list management
				new WSClientImpl(context).updateOrderItemStatus(Config.getToken(context), data.get(_position).getId(), 1, 2, new StatusListener() {

					@Override
					public void statusListener(int statusCode, Object objectResponse) {

						dialog.dismissDialog();
						switch (statusCode) {
						case 200:
							data = (List<CustomerOrderItemDTO>)objectResponse;
							notifyDataSetChanged();
							break;
						}

					}

					@Override
					public void preLoadListener() {
						//dialog.showDialog(R.string.loading_my_order_message);
					}

				});
				
			}
		});
		
		viewHolder.getDelivaBtn().setOnClickListener(new OnClickListener() {
			int _position=position;
			@Override
			public void onClick(View v) {
				final LoadingDialog dialog = new LoadingDialog(context);
				// New items list management
				new WSClientImpl(context).updateOrderItemStatus(Config.getToken(context), data.get(_position).getId(), 3, 2, new StatusListener() {

					@Override
					public void statusListener(int statusCode, Object objectResponse) {

						dialog.dismissDialog();
						switch (statusCode) {
						case 200:
							data = (List<CustomerOrderItemDTO>)objectResponse;
							notifyDataSetChanged();
							//((KitchenActivity)context).refresh();
							break;

						}

					}

					@Override
					public void preLoadListener() {
						//dialog.showDialog(R.string.loading_my_order_message);
					}

				});
				
			}
		});
		
		return convertView;
	}

	private static class ViewHolder {

		// private TextView noTv;
		private TextView foodNameTv;
		// private TextView priceTv;
		// private TextView countTv;
		private TextView quantityTv;
		private TextView tableTv;
		private Button undoBtn;
		private Button delivaBtn;

		public ViewHolder(View view) {

			// noTv = (TextView) view.findViewById(R.id.tv_no);
			foodNameTv = (TextView) view.findViewById(R.id.tv_food_name);
			// priceTv = (TextView) view.findViewById(R.id.tv_price);
			// countTv = (TextView) view.findViewById(R.id.tv_count);
			quantityTv = (TextView) view.findViewById(R.id.tv_quantity);
			tableTv = (TextView) view.findViewById(R.id.tv_table);
			undoBtn = (Button) view.findViewById(R.id.btn_undo);
			delivaBtn = (Button) view.findViewById(R.id.btn_deliva);
		}

		// public TextView getNoTv() {
		// return noTv;
		// }

		public TextView getFoodNameTv() {
			return foodNameTv;
		}

		// public TextView getPriceTv() {
		// return priceTv;
		// }
		//
		// public TextView getCountTv() {
		// return countTv;
		// }

		public TextView gettableTv() {
			return tableTv;
		}

		public TextView getQuantityTv() {
			return quantityTv;
		}
		
		public Button getUndoBtn() {
			return undoBtn;
		}
		
		public Button getDelivaBtn() {
			return delivaBtn;
		}

	}

	public void notifyDataSetChanged(List<CustomerOrderItemDTO> data) {

		this.data = data;
		this.notifyDataSetChanged();
	}
}
