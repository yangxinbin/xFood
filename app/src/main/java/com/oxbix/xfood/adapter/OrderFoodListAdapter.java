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
import com.oxbix.xfood.dto.FoodDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.service.ShoppingCartService;

/**
 * 订单中食物列表ListView的Adapter类
 * @author AllenZheng 
 * @date 2014年9月23日 上午3:09:08
 */
public class OrderFoodListAdapter extends BaseAdapter {

	private Context context;
	private List<ShoppingCartItemDTO> data;
	private LayoutInflater layoutInflater;
	private ViewHolder viewHolder = null;
	private int position = -1;

	public OrderFoodListAdapter(Context context, List<ShoppingCartItemDTO> data) {
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

		this.position = position;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.listview_order_food_item_layout, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 编号
		viewHolder.getNoTv().setText(String.valueOf(position + 1));

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

		// 删除按钮
		viewHolder.getRemoveBtn().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final AlertDialog removeDialog = new AlertDialog.Builder(context).create();
				removeDialog.show();
				View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert_layout, null);
				removeDialog.setContentView(view);
				Window dialogWindow = removeDialog.getWindow();
				WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
				Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
				WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
				p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
				p.width = (int) (d.getWidth() * 0.4); // 宽度设置为屏幕的0.8
				dialogWindow.setAttributes(p);

				TextView dialogMessage = (TextView) view.findViewById(R.id.tv_dialog_message);
				dialogMessage.setText(R.string.remove_dialog_message);
				Button confirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);
				confirmBtn.setText(R.string.remove);
				Button cancelBtn = (Button) view.findViewById(R.id.btn_dialog_cancel);
				cancelBtn.setText(R.string.cancel);

				confirmBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						notifyDataSetChanged(ShoppingCartService.removeShoppingCartItem(String.valueOf(getItem(position).getFood().getItemId())));
						if (SysApplication.badgeView != null) {

							SysApplication.badgeView.setText(String.valueOf(ShoppingCartService.getFoodCount()));
						}
						SysApplication.totalTv.setText(Config.getCurrency(context) + ShoppingCartService.getTotal());
						removeDialog.dismiss();
						SysApplication.itemsTv.setText(String.valueOf(SysApplication.shoppingCartItems.size()));
					}
				});

				cancelBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						removeDialog.dismiss();
					}
				});
			}
		});

		// 数量减少按钮
		viewHolder.getQuantityMinusBtn().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 点击数量减少按钮，如果只剩最后一件，则提示用户是否删除
				if (getItem(position).getQuantity() > 1) {

					notifyDataSetChanged(ShoppingCartService.updateShoppingCartItemQuantity(0,
							String.valueOf(getItem(position).getFood().getItemId())));
					if (SysApplication.badgeView != null) {

						SysApplication.badgeView.setText(String.valueOf(ShoppingCartService.getFoodCount()));
					}
					SysApplication.totalTv.setText("￥" + ShoppingCartService.getTotal());
				} else {

					final AlertDialog minusRemoveDialog = new AlertDialog.Builder(context).create();
					minusRemoveDialog.show();
					View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert_layout, null);
					minusRemoveDialog.setContentView(view);
					Window dialogWindow = minusRemoveDialog.getWindow();
					WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
					// 获取屏幕宽、高
					Display d = m.getDefaultDisplay(); 
					// 获取对话框当前的参数值
					WindowManager.LayoutParams p = dialogWindow.getAttributes(); 
					// 高度设置为屏幕的0.4
					p.height = (int) (d.getHeight() * 0.4); 
					// 宽度设置为屏幕的0.4
					p.width = (int) (d.getWidth() * 0.4);
					dialogWindow.setAttributes(p);

					TextView dialogMessage = (TextView) view.findViewById(R.id.tv_dialog_message);
					dialogMessage.setText(R.string.remove_dialog_message);
					Button confirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);
					confirmBtn.setText(R.string.remove);
					Button cancelBtn = (Button) view.findViewById(R.id.btn_dialog_cancel);
					cancelBtn.setText(R.string.cancel);

					confirmBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							notifyDataSetChanged(ShoppingCartService.removeShoppingCartItem(String.valueOf(getItem(position).getFood().getItemId())));
							if (SysApplication.badgeView != null) {

								SysApplication.badgeView.setText(String.valueOf(ShoppingCartService.getFoodCount()));
							}
							SysApplication.totalTv.setText("￥" + ShoppingCartService.getTotal());
							minusRemoveDialog.dismiss();
						}
					});

					cancelBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							minusRemoveDialog.dismiss();
						}
					});

				}
			}
		});

		// 数量增加按钮
		viewHolder.getQuantityPlusBtn().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				notifyDataSetChanged(ShoppingCartService.updateShoppingCartItemQuantity(1, String.valueOf(getItem(position).getFood().getItemId())));
				if (SysApplication.badgeView != null) {

					SysApplication.badgeView.setText(String.valueOf(ShoppingCartService.getFoodCount()));
				}
				SysApplication.totalTv.setText("￥" + ShoppingCartService.getTotal());
			}
		});

		return convertView;
	}

	private static class ViewHolder {

		private TextView noTv;
		private TextView foodNameTv;
		private Button quantityMinusBtn;
		private Button quantityPlusBtn;
		private TextView priceTv;
		private TextView countTv;
		private TextView quantityTv;
		private Button removeBtn;

		public ViewHolder(View view) {

			noTv = (TextView) view.findViewById(R.id.tv_no);
			foodNameTv = (TextView) view.findViewById(R.id.tv_food_name);
			quantityMinusBtn = (Button) view.findViewById(R.id.btn_quantity_minus);
			quantityPlusBtn = (Button) view.findViewById(R.id.btn_quantity_plus);
			priceTv = (TextView) view.findViewById(R.id.tv_price);
			countTv = (TextView) view.findViewById(R.id.tv_count);
			quantityTv = (TextView) view.findViewById(R.id.tv_quantity);
			removeBtn = (Button) view.findViewById(R.id.btn_remove);
		}

		public TextView getNoTv() {
			return noTv;
		}

		public TextView getFoodNameTv() {
			return foodNameTv;
		}

		public Button getQuantityMinusBtn() {
			return quantityMinusBtn;
		}

		public Button getQuantityPlusBtn() {
			return quantityPlusBtn;
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

		public Button getRemoveBtn() {
			return removeBtn;
		}
	}

	public void notifyDataSetChanged(List<ShoppingCartItemDTO> data) {

		this.data = data;
		this.notifyDataSetChanged();
	}
}
