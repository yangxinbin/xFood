package com.oxbix.xfood.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.adapter.FoodCategoryListAdapter;
import com.oxbix.xfood.adapter.FoodGridAdapter;
import com.oxbix.xfood.dto.FoodCategoryDTO;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.base.BaseFragment;
import com.oxbix.xfood.widget.BadgeView;

/**
 * 食物列表界面
 * 
 * @author AllenZheng
 * @date 2014年9月25日 下午2:43:06
 */
public class FoodListFragment extends BaseFragment implements OnItemClickListener {

	private ImageButton shoppingCartBtn;
	private ToggleButton languageTb;
	private ListView categorylistView;
	private GridView foodlistGv;
	private FoodCategoryListAdapter foodCategoryListAdapter;
	private FoodGridAdapter foodListAdapter;
	private List<FoodCategoryDTO> categorys = new ArrayList<FoodCategoryDTO>();

	private FoodActivity foodActivity;
	public static BadgeView badge;

	/** 我的订单 */
	private ImageButton btn_my_order;
	/** 结账 */
	private ImageButton btn_check_out;

	private RelativeLayout rela_food_title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_food_list, null);
		findViewById(view);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		foodActivity = (FoodActivity) activity;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_shopping_cart:

			// 点击购物车按钮，打开侧边的购物车食物列表栏
			SysApplication.slidingMenu.toggle();
			break;
		case R.id.btn_my_order:
			Intent intent = new Intent(foodActivity, MyOrderActivity.class);
			startActivityForResult(intent, Config.QUEST_CODE_VIEW_OREDER);
			break;
		case R.id.btn_check_out:
			Intent intent1 = new Intent(foodActivity, CheckOutActivtity.class);
			startActivityForResult(intent1, Config.QUEST_CODE_CHECK_OUT);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		foodActivity.display(position + 1);
		
		foodCategoryListAdapter.setSeclection(position);
		foodCategoryListAdapter.notifyDataSetInvalidated();

		foodListAdapter = new FoodGridAdapter(this, categorys.get(position).getItems());

		foodlistGv.setAdapter(foodListAdapter);

		foodlistGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
				FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("food", foodListAdapter.getItem(arg2));
				foodDetailFragment.setArguments(bundle);
				transaction.add(R.id.content, foodDetailFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
	}

	@Override
	protected void findViewById(View view) {

		btn_check_out = (ImageButton) view.findViewById(R.id.btn_check_out);
		languageTb = (ToggleButton) view.findViewById(R.id.tb_language);
		shoppingCartBtn = (ImageButton) view.findViewById(R.id.btn_shopping_cart);

		categorylistView = (ListView) view.findViewById(R.id.ls_category);
		foodlistGv = (GridView) view.findViewById(R.id.gv_foodlist);
		btn_my_order = (ImageButton) view.findViewById(R.id.btn_my_order);
		rela_food_title = (RelativeLayout) view.findViewById(R.id.rela_food_title);

		btn_my_order.setOnClickListener(this);
		init();
	}

	@Override
	protected void init() {

		// hideOrNotIcon();

		if (SysApplication.skinColor.equals(Config.SKIN_CHOCOLATE)) {
			rela_food_title.setBackgroundResource(R.drawable.bg_food_title);
		} else if (SysApplication.skinColor.equals(Config.SKIN_GREEN)) {
			rela_food_title.setBackgroundResource(R.drawable.bg_green_food_title);
		}

		badge = new BadgeView(getActivity(), shoppingCartBtn);
		badge.setText(String.valueOf(ShoppingCartService.getFoodCount()));
		badge.show(true);
		SysApplication.badgeView = badge;

		languageTb.setChecked(SysApplication.currentLanguage);

		categorys = SysApplication.categorys;
		foodCategoryListAdapter = new FoodCategoryListAdapter(getActivity(), categorys);
		categorylistView.setAdapter(foodCategoryListAdapter);
		categorylistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		if (categorys.size() > 0) {

			foodListAdapter = new FoodGridAdapter(this, categorys.get(0).getItems());
			foodlistGv.setAdapter(foodListAdapter);

			foodlistGv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					
					
					
					
					
					FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
					FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
					Bundle bundle = new Bundle();
					bundle.putSerializable("food", foodListAdapter.getItem(arg2));
					foodDetailFragment.setArguments(bundle);
					transaction.replace(R.id.content, foodDetailFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				}
			});
		}
		setListener();
	}

	@Override
	protected void setListener() {
		btn_check_out.setOnClickListener(this);
		shoppingCartBtn.setOnClickListener(this);
		languageTb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				
				SysApplication.currentLanguage = isChecked;

				// foodActivity.changeLanguage(SysApplication.currentLanguage);
				// Intent i = new Intent(foodActivity.action);
				// foodActivity.sendBroadcast(i);

				if (foodCategoryListAdapter != null && foodListAdapter != null) {
					foodListAdapter.notifyDataSetChanged();
					foodCategoryListAdapter.notifyDataSetChanged();
				}
			}
		});

		categorylistView.setOnItemClickListener(this);

	}

	// public void hideOrNotIcon(){
	// if (SysApplication.customerOrderItems.size() == 0) {
	// btn_my_order.setVisibility(View.GONE);
	// btn_check_out.setVisibility(View.GONE);
	// } else {
	// btn_my_order.setVisibility(View.VISIBLE);
	// btn_check_out.setVisibility(View.VISIBLE);
	// }
	// }

	public void shopingCartAnima() {
		Animation tr = new TranslateAnimation(-5, 5, -5, 5);
		tr.setRepeatCount(4);
		tr.setDuration(60);
		badge.setAnimation(tr);
		shoppingCartBtn.setAnimation(tr);
		tr.start();
	}
}
