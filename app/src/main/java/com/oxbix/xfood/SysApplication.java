package com.oxbix.xfood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.RoomDTO;
import com.oxbix.xfood.dto.TableDTO;
import com.oxbix.xfood.dto.FoodCategoryDTO;
import com.oxbix.xfood.dto.ShoppingCartItemDTO;
import com.oxbix.xfood.widget.BadgeView;

/**
 * 应用全局类
 * 
 * @author AllenZheng
 * @date 2014年9月17日 下午4:45:07
 */
public class SysApplication extends Application {

	/** 从服务器获得的所有房间和桌位数据 **/
	public static List<RoomDTO> rooms = new ArrayList<RoomDTO>();
	/** 客户当前所在的房间 **/
	public static RoomDTO currentRoom = null;
	/** 客户当前所在的桌位 **/
	public static TableDTO currentTable = null;
	/** 从服务器获得的所有的食物分类数据 **/
	public static List<FoodCategoryDTO> categorys = new ArrayList<FoodCategoryDTO>();
	/** 当前显示的语言环境，false表示中文，true表示英文 **/
	public static boolean currentLanguage = false;
	/** 购物车中的选项 **/
	public static List<Map<String, ShoppingCartItemDTO>> shoppingCartItems = new ArrayList<Map<String, ShoppingCartItemDTO>>();
	/** 侧滑菜单 **/
	public static SlidingMenu slidingMenu = null;

	/** 购物车上显示的数字控件 **/
	public static BadgeView badgeView = null;
	/** 订单总价TextView控件 **/
	public static TextView totalTv = null;

	public static TextView itemsTv = null;

	/** 网络图片加载对象 **/
	public static ImageLoader imageLoader;
	//public static BitmapUtils ut = null;
	/** 图片加载显示图片的配置选项 **/
	public static DisplayImageOptions options;

	/** 存放所有订单 **/
	public static ArrayList<CustomerOrderItemDTO> customerOrderItems = new ArrayList<CustomerOrderItemDTO>();

	public static String skinColor = null;
	public static SoundPool sp;
	public static HashMap<Integer, Integer> map;

	@Override
	public void onCreate() {

		super.onCreate();
		// 实例化一个ImageLoader对象
		imageLoader = ImageLoader.getInstance();
		// 初始化整个ImageLoader对象
		imageLoader.init(ImageLoaderConfiguration.createDefault(this.getApplicationContext()));
		// 对ImageLoader的一些配置
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading_img) // 正在加载时显示的加载图片
				.showImageOnFail(R.drawable.loading_img) // 加载失败时显示的图片
				.cacheInMemory(true) // 是否缓存到内存中
				.cacheOnDisk(true) // 是否缓存到SD卡中
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		/*
		 * 真正的闪烁原因不是display和imageload方法的缘故，而 是在设置option时，设置了,displayer(new
		 * FadeInBitmapD isplayer(200))的缘故，直接设置为.displayer(new
		 * SimpleBitmapDisplayer())，然后 再getview方法中调用display方法，就不会闪烁了.
		 */

		// 获取当前的语言环境
		currentLanguage = isZh() ? false : true;
		initMusicPool();

	}

	/**
	 * 判断当前的系统语言环境是否是中文
	 * 
	 * @return
	 */
	private boolean isZh() {

		Locale locale = getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh")) {

			return true;
		} else {

			return false;
		}
	}

	public void play() {

	}

	private void initMusicPool() {
		sp = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		map = new HashMap<Integer, Integer>();
		map.put(1, sp.load(this, R.raw.button1, 1));
		map.put(2, sp.load(this, R.raw.button2, 1));
		map.put(3, sp.load(this, R.raw.button3, 1));
		map.put(4, sp.load(this, R.raw.button4, 1));
		map.put(5, sp.load(this, R.raw.button6, 1));
		map.put(6, sp.load(this, R.raw.button7, 1));
		map.put(7, sp.load(this, R.raw.button8, 1));
	}
}
