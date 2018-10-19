package com.oxbix.xfood;

import com.oxbix.xfood.dto.AuthDTO;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 应用配置类
 * 
 * @author AllenZheng
 * @date 2014年9月17日 下午4:44:48
 */
public class Config {

	/** 服务器图片Url **/
	 public static final String SERVER_BASE_URL =
	 "http://211.149.189.225:8080/foodcloud";
//	public static final String SERVER_BASE_URL = "http://192.168.0.144:8080/foodcloud";
	public static final String SERVER_IMAGE_URL = SERVER_BASE_URL + "/cdn/image?name=";
	/** 应用SharedPreference文件的文件名 **/
	public static final String SHAREDPREFERENCE_FILENAME = "xfood_sharedpre";
	/** SharedPreference文件中的缓存的token的key **/
	public static final String KEY_TOKEN = "TOKEN";
	public static final String KEY_USERNAME = "USERNAME";
	public static final String KEY_USERTYPE = "USERTYPE";
	public static final String KEY_LOCATION = "LOCATION";
	public static final String KEY_CURRENCY = "CURRENCY";
	public static final String KEY_BRANDURL = "BRANDURL";
	public static final String KEY_CURRENT_ORDER_ID = "UREENT_ORDERID";
	public static final String KEY_CURRENT_CUSTOMER_NAME = "CUREENT_CUSTOMER";

	public static final int RETURN_CODE_VIEW_OREDER = 100;// 查看订单的返回码
	public static final int RETURN_CODE_OREDER_MORE = 200;// 继续订单的返回码
	public static final int RETURN_CODE_CHECK_OUT = 300;// 结账的返回码
	public static final int RETURN_CODE_GRADE = 400;// 评分的返回码

	public static final int QUEST_CODE_OREDER_SUCCESS = 100;// 订单成功的请求码
	public static final int QUEST_CODE_CHECK_OUT = 200;// 结账的请求码
	public static final int QUEST_CODE_VIEW_OREDER = 300;// 查看订单的请求码
	public static final int QUEST_CODE_GRADE = 400;// 评分的请求码

	public static final String STATUS_TABLE_FREE = "FREE"; // 桌子是否空闲
	public static final String STATUS_TABLE_RESERVED = "RESERVED";

	public static final String STATUS_USER_WAITER = "waiter"; // 用户身份
	public static final String STATUS_USER_KITCHEN = "kitchen";
	
	public static final String SKIN_CHOCOLATE = "Chocolate";
	public static final String SKIN_GREEN = "Green";

	/**
	 * 缓存token
	 * 
	 * @param context
	 */
	public static void cacheAuthInfo(Context context, AuthDTO authInfo) {

		SharedPreferences.Editor editor = context.getSharedPreferences(SHAREDPREFERENCE_FILENAME, Context.MODE_PRIVATE).edit();
		editor.putString(KEY_TOKEN, authInfo.getToken());
		editor.putString(KEY_USERNAME, authInfo.getUsername());
		editor.putString(KEY_USERTYPE, authInfo.getUserType());
		editor.putLong(KEY_LOCATION, authInfo.getLocation());
		editor.putString(KEY_CURRENCY, authInfo.getCurrency());
		editor.putString(KEY_BRANDURL, authInfo.getTenantBrandURL());
		editor.commit();
	}

	public static void cacheCurrentOrderID(Context context, Long orderId) {
		SharedPreferences.Editor editor = context.getSharedPreferences(SHAREDPREFERENCE_FILENAME, Context.MODE_PRIVATE).edit();
		if (orderId == -1)
			editor.remove(KEY_CURRENT_ORDER_ID);
		else
			editor.putLong(KEY_CURRENT_ORDER_ID, orderId);
		editor.commit();
	}

	public static void cacheCurrentCustomerName(Context context, String customerName) {
		SharedPreferences.Editor editor = context.getSharedPreferences(SHAREDPREFERENCE_FILENAME, Context.MODE_PRIVATE).edit();
		editor.putString(KEY_CURRENT_CUSTOMER_NAME, customerName);
		editor.commit();
	}

	/**
	 * @Description: 获取缓存的token
	 * @param @param context
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getToken(Context context) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_FILENAME, Context.MODE_PRIVATE);
		String token = sharedPreferences.getString(KEY_TOKEN, "");

		return token;
	}

	public static String getCurrency(Context context) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_FILENAME, Context.MODE_PRIVATE);
		String token = sharedPreferences.getString(KEY_CURRENCY, "");

		return token;
	}

	public static long getCurrentOrderId(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getLong(KEY_CURRENT_ORDER_ID, -1);
	}

	public static String getCurrentCustomerName(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(KEY_CURRENT_CUSTOMER_NAME, "");
	}
}
