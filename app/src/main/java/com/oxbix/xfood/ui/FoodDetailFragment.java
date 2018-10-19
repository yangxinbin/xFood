package com.oxbix.xfood.ui;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.oxbix.xfood.Config;
import com.oxbix.xfood.R;
import com.oxbix.xfood.SysApplication;
import com.oxbix.xfood.adapter.FoodImgThumbnailListAdapter;
import com.oxbix.xfood.adapter.MyAdapter;
import com.oxbix.xfood.dto.FoodDTO;
import com.oxbix.xfood.service.ShoppingCartService;
import com.oxbix.xfood.ui.base.BaseFragment;
import com.oxbix.xfood.widget.BadgeView;
import com.oxbix.xfood.widget.DragImageView;
import com.oxbix.xfood.widget.HackyViewPager;

/**
 * 食物详情展示界面
 * 
 * @author AllenZheng
 * @date 2014年9月25日 下午2:39:11
 */
public class FoodDetailFragment extends BaseFragment {

	private ImageButton backBtn;
	private ImageButton shoppingCartBtn;
	private ToggleButton languageTb;
	private TextView foodNameTv;
	private TextView foodDescriptionTv;
	private TextView priceTv;
	private TextView foodQtyTv;
	private Button minusBtn;
	private Button plusBtn;
	private Button addToCartBtn;
	// private HorizontalListView foodImgThumbnail;
	private HackyViewPager viewPager;
	private FoodImgThumbnailListAdapter foodImgThumbnailGalleryAdapter;
	// private List<String> foodImgs;
	private int count = 1;
	private FoodDTO food;

	private RelativeLayout rela_food_title;

	private ImageButton btn_check_out;
	private ImageButton btn_my_order;
	private FoodActivity foodActivity;

	private BadgeView badge;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_food_detail, null);
		findViewById(view);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//foodActivity = (FoodActivity) activity;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_my_order:
			Intent intent1 = new Intent(getActivity(), MyOrderActivity.class);
			startActivityForResult(intent1, Config.QUEST_CODE_VIEW_OREDER);
			break;
		case R.id.btn_check_out:
			Intent intent = new Intent(getActivity(), CheckOutActivtity.class);
			startActivityForResult(intent, Config.QUEST_CODE_CHECK_OUT);
			break;
		case R.id.btn_back:

			// 返回到上一层
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.popBackStack();
			break;
		case R.id.ib_shopping_cart_food_detail:

			// 点击购物车按钮，打开侧边的购物车食物列表栏
			SysApplication.slidingMenu.toggle();
			break;

		case R.id.btn_minus:

			if (count > 1) {
				foodQtyTv.setText(String.valueOf(--count));
			}
			priceTv.setText(String.valueOf(Config.getCurrency(getActivity()) + food.getDefaultPrice() * count));
			break;

		case R.id.btn_plus:
			foodQtyTv.setText(String.valueOf(++count));
			priceTv.setText(String.valueOf(Config.getCurrency(getActivity()) + food.getDefaultPrice() * count));
			break;

		case R.id.btn_add_to_cart:

			ShoppingCartService.addToCart(food, count);

			// if (SysApplication.badgeView != null) {
			//
			// SysApplication.badgeView.setText(String.valueOf(ShoppingCartService.getFoodCount()));
			// }
			FoodListFragment.badge.setText(String.valueOf(ShoppingCartService.getFoodCount()));

			//shopingCartAnima();

			break;

		default:
			break;
		}
	}

	@Override
	protected void findViewById(View view) {

		btn_my_order = (ImageButton) view.findViewById(R.id.btn_my_order);
		btn_check_out = (ImageButton) view.findViewById(R.id.btn_check_out);
		backBtn = (ImageButton) view.findViewById(R.id.btn_back);
		languageTb = (ToggleButton) view.findViewById(R.id.tb_language_food_detail);
		shoppingCartBtn = (ImageButton) view.findViewById(R.id.ib_shopping_cart_food_detail);
		foodNameTv = (TextView) view.findViewById(R.id.tv_food_name);
		foodDescriptionTv = (TextView) view.findViewById(R.id.tv_food_description);
		priceTv = (TextView) view.findViewById(R.id.tv_price);
		// foodImgThumbnail = (HorizontalListView)
		// view.findViewById(R.id.lv_food_img_thumbnail);
		viewPager = (HackyViewPager) view.findViewById(R.id.viewPager);
		foodQtyTv = (TextView) view.findViewById(R.id.tv_food_qty);
		minusBtn = (Button) view.findViewById(R.id.btn_minus);
		plusBtn = (Button) view.findViewById(R.id.btn_plus);
		addToCartBtn = (Button) view.findViewById(R.id.btn_add_to_cart);

		rela_food_title = (RelativeLayout) view.findViewById(R.id.rela_food_title);

		init();
	}

	@Override
	protected void init() {

		if (SysApplication.skinColor.equals(Config.SKIN_CHOCOLATE)) {
			rela_food_title.setBackgroundResource(R.drawable.bg_food_title);
			addToCartBtn.setBackgroundResource(R.drawable.btn_bg_add_to_cart);
		} else if (SysApplication.skinColor.equals(Config.SKIN_GREEN)) {
			rela_food_title.setBackgroundResource(R.drawable.bg_green_food_title);
			addToCartBtn.setBackgroundResource(R.drawable.button_bg_bright_red);
		}

		badge = new BadgeView(getActivity(), shoppingCartBtn);
		badge.setText(String.valueOf(ShoppingCartService.getFoodCount()));
		badge.show(true);
		SysApplication.badgeView = badge;

		languageTb.setChecked(SysApplication.currentLanguage);

		food = (FoodDTO) getArguments().get("food");
		List<String> imaStrings = new ArrayList<String>();
		imaStrings.add(0, food.getIcon());
		// imaStrings.addAll(food.getImages());
		// foodImgs = imaStrings;
		// foodImgThumbnailGalleryAdapter = new
		// FoodImgThumbnailListAdapter(getActivity(), foodImgs);
		// foodImgThumbnail.setAdapter(foodImgThumbnailGalleryAdapter);
		viewPager.setAdapter(new MyAdapter(getActivity(), imaStrings));

		priceTv.setText(String.valueOf(Config.getCurrency(getActivity()) + food.getDefaultPrice()));
		if (SysApplication.currentLanguage) {
			// addToCartBtn.setText(R.string.add_to_cart);
			foodNameTv.setText(food.getName());
			foodDescriptionTv.setText(food.getDescription());
		} else {
			// addToCartBtn.setText(R.string.add_to_cart);
			foodNameTv.setText(food.getNameT());
			foodDescriptionTv.setText(food.getDescriptionT());
		}
		// if (foodImgs != null && foodImgs.size() > 0) {
		//
		// SysApplication.imageLoader.displayImage(Config.SERVER_IMAGE_URL +
		// foodImgs.get(0), foodImgIv, SysApplication.options);
		// }

		setListener();
	}

	@Override
	protected void setListener() {

		btn_my_order.setOnClickListener(this);
		btn_check_out.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		minusBtn.setOnClickListener(this);
		plusBtn.setOnClickListener(this);
		addToCartBtn.setOnClickListener(this);
		shoppingCartBtn.setOnClickListener(this);
		languageTb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				SysApplication.currentLanguage = isChecked;
				// foodActivity.changeLanguage(SysApplication.currentLanguage);
				if (SysApplication.currentLanguage) {
					// addToCartBtn.setText(R.string.add_to_cart);
					foodNameTv.setText(food.getNameT());
					foodDescriptionTv.setText(food.getDescriptionT());
				} else {
					// addToCartBtn.setText(R.string.add_to_cart);
					foodNameTv.setText(food.getName());
					foodDescriptionTv.setText(food.getDescription());
				}
			}
		});
		// foodImgThumbnail.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		//
		// foodImgThumbnailGalleryAdapter.setSeclection(arg2);
		// foodImgThumbnailGalleryAdapter.notifyDataSetChanged();
		//
		// SysApplication.imageLoader.displayImage(Config.SERVER_IMAGE_URL +
		// foodImgs.get(arg2 % foodImgs.size()), foodImgIv,
		// SysApplication.options);
		// }
		// });
	}

	public void shopingCartAnima() {
		Animation tr = new TranslateAnimation(-5, 5, -5, 5);
		tr.setRepeatCount(4);
		tr.setDuration(60);
		badge.setAnimation(tr);
		shoppingCartBtn.setAnimation(tr);
		tr.start();
	}
}
