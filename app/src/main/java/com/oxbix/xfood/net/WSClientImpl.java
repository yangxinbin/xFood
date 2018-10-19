package com.oxbix.xfood.net;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.oxbix.xfood.dto.AuthDTO;
import com.oxbix.xfood.dto.CustomerOrderDTO;
import com.oxbix.xfood.dto.CustomerOrderItemDTO;
import com.oxbix.xfood.dto.FoodCategoryDTO;
import com.oxbix.xfood.dto.RoomDTO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 访问WebService的客户端实现类
 * @author AllenZheng
 * 
 */
public class WSClientImpl implements WSClient {

	/** 上下文对象 **/
	private Context context;
	/** 异步http访问网络的对象 **/
	private AsyncHttpClient client;

	public WSClientImpl(Context context) {

		this.context = context;
		this.client = new AsyncHttpClient();
		// 设置网络连接超时时长
		this.client.setTimeout(NetConstants.TIME_OUT);
		// 添加HTTP访问头
		this.client.addHeader(NetConstants.HEADER_NAME, NetConstants.HEADER_VALUE);
	}

	@Override
	public void auth(String userName, String password, StatusListener statusListener) {

		// POST请求Form表单提交
		RequestParams params = new RequestParams();
		params.put("u", userName);
		params.put("p", password);
		
		// 返回数据类型
		Type type = new TypeToken<AuthDTO>() {
		}.getType();
		
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.AUTH_PATH, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}

	@Override
	public void getRooms(long location, StatusListener statusListener) {

		// POST请求Form表单提交
		RequestParams params = new RequestParams();
		params.put("location", location);
		
		// 返回数据类型
		Type type = new TypeToken<List<RoomDTO>>() {
		}.getType();
		
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.ROOM_PATH, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}
	
	@Override
	public void getCategories(String token, StatusListener statusListener) {

		// POST请求Form表单提交
		RequestParams params = new RequestParams();
		params.put("token", token);
		
		// 返回数据类型
		Type type = new TypeToken<List<FoodCategoryDTO>>() {
		}.getType();
		
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.CATEGORY_PATH, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}
	
	@Override
	public void createOrder(String token, String customerName, int persons, int orderType, long ordertable, String orderItems,
			StatusListener statusListener) {
		// POST请求Form表单提交
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("table", ordertable);
		params.put("orderItems", orderItems);
		params.put("persons", persons);
		params.put("orderType", orderType);
		// 返回数据类型
		Type type = new TypeToken<CustomerOrderDTO>() {
		}.getType();
		
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.CREATORDER_PATH, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}
	
	@Override
	public void addItemsToOrder(String token, long orderId, String orderItems,
			StatusListener statusListener) {
		// POST请求Form表单提交
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("order", orderId);
		params.put("orderItems", orderItems);
		// 返回数据类型
		Type type = new TypeToken<CustomerOrderDTO>() {
		}.getType();
		
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.ADD_ITEMS_TO_ORDER_PATH, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}
	
	/**
	 * HTTP GET请求
	 * @param context  上下文对象
	 * @param url  请求的url
	 * @param params  提交的form表达参数
	 * @param responseHandlerInterface  处理请求响应的
	 */
	private void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandlerInterface) {
		this.client.get(context, url, params, responseHandlerInterface);

	}

	/**
	 * HTTP POST请求
	 * @param context
	 * @param url
	 * @param params
	 * @param responseHandlerInterface
	 */
	private void post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandlerInterface) {

		this.client.post(context, url, params, responseHandlerInterface);
	}

	
	@Override
	public void getOrder(String token, long orderId, StatusListener statusListener) {
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("order", orderId);
		// 返回数据类型
		Type type = new TypeToken<CustomerOrderDTO>() {
		}.getType();
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.GET_ORDER, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}

	@Override
	public void RequestCheckIn(String token, long table, StatusListener statusListener) {
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("table", table);
		// 返回数据类型
		Type type = new TypeToken<String>() {
		}.getType();
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.GET_CHECK_IN, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}

	@Override
	public void requestCheckOut(String token, long order, long table, StatusListener statusListener) {
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("order", order);
		params.put("table", table);
		// 返回数据类型
		Type type = new TypeToken<String>() {
		}.getType();
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.GET_CHECK_OUT, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}

	@Override
	public void listCurrentOrderItems(String token, int status, StatusListener statusListener) {
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("status", status);
		// 返回数据类型
		Type type = new TypeToken<ArrayList<CustomerOrderItemDTO>>() {
		}.getType();
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.GET_CURRENT_ORDER_ITEMS, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}

	@Override
	public void updateOrderItemStatus(String token, long orderItem, int new_status, int required_status, StatusListener statusListener) {
		RequestParams params = new RequestParams();
		params.put("token", token);
		params.put("orderItem", orderItem);
		params.put("new_status", new_status);
		params.put("required_status", required_status);
		// 返回数据类型
		Type type = new TypeToken<ArrayList<CustomerOrderItemDTO>>() {
		}.getType();
		post(context, NetConstants.WEBSERVICE_URL + NetConstants.UPDATE_ORDER_ITEM_STATUS, params, new MyBaseJsonHttpResponseHandler(statusListener, type));
	}

}
