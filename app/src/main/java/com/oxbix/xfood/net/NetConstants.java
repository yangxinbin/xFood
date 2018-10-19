/**
 * 
 */
package com.oxbix.xfood.net;

import com.oxbix.xfood.Config;

/**
 * 网络请求中所需用到的常量类
 * 
 * @author AllenZheng
 * @date 2014年9月17日 下午3:43:06
 */
interface NetConstants {

	/** 服务器 WebService Url **/
	String WEBSERVICE_URL = Config.SERVER_BASE_URL + "/ws/service";
	/** 登录验证的path **/
	String AUTH_PATH = "/auth";
	/** 获取所有房间和桌位的path **/
	String ROOM_PATH = "/rooms";
	/** 获取所有食物分类的path **/
	String CATEGORY_PATH = "/categories";
	/** 确认订单的path **/
	String CREATORDER_PATH = "/waiter/creatorder";

	String ADD_ITEMS_TO_ORDER_PATH = "/waiter/additems";
	
	/** 获取订单 **/
	String GET_ORDER = "/waiter/getorder";
	
	/** Check In **/
	String GET_CHECK_IN = "/waiter/requestcheckin";
	
	/** Check OUT **/
	String GET_CHECK_OUT = "/waiter/requestcheckout";
	
	String GET_CURRENT_ORDER_ITEMS = "/kitchen/listRecentOrderItems";
	
	String UPDATE_ORDER_ITEM_STATUS = "/kitchen/updateOrderItemStatus";
	
	
	/** 请求超时时长10s **/
	int TIME_OUT = 10000;
	/** HTTP的POST请求头字段名 **/
	String HEADER_NAME = "key";
	/** HTTP的POST请求头字段值 **/
	String HEADER_VALUE = "$@!m@^l!49k58z10203";
}
