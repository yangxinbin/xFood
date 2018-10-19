package com.oxbix.xfood.adapter;

import java.util.List;
import android.annotation.SuppressLint;
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
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.service.ShoppingCartService;

/**
 * 订单中食物列表ListView的Adapter类
 * 
 * @author AllenZheng
 * @date 2014年9月23日 上午3:09:08
 */
@SuppressLint("ResourceAsColor")
public class OrderSentFoodListAdapter extends BaseAdapter {

	private Context context;
	private List<ShoppingCartItemDTO> data;
	private LayoutInflater layoutInflater;
	private ViewHolder viewHolder = null;

	public OrderSentFoodListAdapter(Context context, List<ShoppingCartItemDTO> data) {
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
	public ShoppingCartItemDTO getItem(int position) {

		return (data != null) ? data.get(position) : null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.listview_order_sent_food_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 编号
		viewHolder.getNoTv().setText(String.valueOf(position + 1));

		if (getItem(position).getFood() != null && !getItem(position).getFood().equals("")) {
			// 食物名称
			if (SysApplication.currentLanguage) {

				viewHolder.getFoodNameTv().setText(getItem(position).getFood().getName());
			} else {
				viewHolder.getFoodNameTv().setText(getItem(position).getFood().getNameT());
			}

			// 食物价格
			viewHolder.getPriceTv().setText(String.valueOf(getItem(position).getFood().getDefaultPrice()));

			// 食物数量
			viewHolder.getQuantityTv().setText(String.valueOf(getItem(position).getQuantity()));

			// 金额小计
			viewHolder.getCountTv().setText(String.valueOf(getItem(position).getQuantity() * getItem(position).getFood().getDefaultPrice()));
		} else {
			viewHolder.getFoodNameTv().setText("");
			viewHolder.getPriceTv().setText("");
			viewHolder.getQuantityTv().setText("");
			viewHolder.getCountTv().setText("");
		}
		// 状态
		int status = getItem(position).getStatus();
		if (status == 0) {// sent
			viewHolder.getStatusTv().setText(R.string.sent);
			viewHolder.getStatusTv().setTextColor(R.color.black);
		} else if (status == 1) {// started
			viewHolder.getStatusTv().setText(R.string.started);
			viewHolder.getStatusTv().setTextColor(R.color.red);
		} else if (status == 2) {// finished
			viewHolder.getStatusTv().setText(R.string.finished);
			viewHolder.getStatusTv().setTextColor(R.color.dark_green);
		} else if (status == 3) {// comming
			viewHolder.getStatusTv().setText(R.string.comming);
			viewHolder.getStatusTv().setTextColor(R.color.gray);
		}

		return convertView;
	}

	private static class ViewHolder {

		private TextView noTv;
		private TextView foodNameTv;
		private TextView priceTv;
		private TextView countTv;
		private TextView quantityTv;
		private TextView statusTv;

		public ViewHolder(View view) {

			noTv = (TextView) view.findViewById(R.id.tv_no);
			foodNameTv = (TextView) view.findViewById(R.id.tv_food_name);
			priceTv = (TextView) view.findViewById(R.id.tv_price);
			countTv = (TextView) view.findViewById(R.id.tv_count);
			quantityTv = (TextView) view.findViewById(R.id.tv_quantity);
			statusTv = (TextView) view.findViewById(R.id.tv_status);
		}

		public TextView getNoTv() {
			return noTv;
		}

		public TextView getFoodNameTv() {
			return foodNameTv;
		}

		public TextView getPriceTv() {
			return priceTv;
		}

		public TextView getCountTv() {
			return countTv;
		}

		public TextView getQuantityTv() {
			return quantityTv;
		}

		public TextView getStatusTv() {
			return statusTv;
		}

	}

	public void notifyDataSetChanged(List<ShoppingCartItemDTO> data) {

		this.data = data;
		this.notifyDataSetChanged();
	}
}
