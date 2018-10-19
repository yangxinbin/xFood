package com.oxbix.xfood.net;

/**
 * 访问WebService的客户端接口类
 * @author AllenZheng 
 * @date 2014年9月17日 下午3:35:21
 */
public interface WSClient {

	/**
	 * 访问WebService结果回调类
	* @author AllenZheng 
	* @date 2014年9月17日 下午3:35:52
	 */
	public interface StatusListener {

		/**
		 * 访问WebService后的结果回调方法
		 * @param statusCode  返回的状态码
		 * @param objectResponse  返回的数据
		 */
		void statusListener(int statusCode, Object objectResponse);

		/**
		 * 网络访问WebService前的回调方法
		 */
		void preLoadListener();
	};
	
	/**
	 * 登录验证
	 * @param userName  用户名
	 * @param password  密码
	 * @param statusListener
	 */
	void auth(String userName, String password,StatusListener statusListener);

	/**
	 * 获得餐厅内所有的房间和餐桌
	 * @param statusListener
	 */
	void getRooms(long location, StatusListener statusListener);

	/**
	 * 获得所有的食物分类
	 * @param token  登录时获得的token
	 * @param statusListener
	 */
	void getCategories(String token, StatusListener statusListener);
	
	/**
	 * 创建订单
	 * @param token  登录时获得的token
	 * @param customerName  客户名称
	 * @param persons  用餐人数
	 * @param orderType  订单类型
	 * @param ordertable  桌号id
	 * @param detailJson  订单里食物的json
	 * @param statusListener
	 */
	void createOrder(String token, String customerName, int persons, int orderType, long ordertable, String detailJson, StatusListener statusListener);

	
	/**
	 * 增加订单
	 * @param token  登录时获得的token
	 * @param orderId 
	 * @param orderItems 
	 * @param statusListener
	 */
	void addItemsToOrder(String token, long orderId, String orderItems, StatusListener statusListener);
	
	
	/**
	 * 获取订单
	 * @param token  登录时获得的token
	 * @param orderId 订单Id
	 * @param statusListener
	 */
	void getOrder(String token, long orderId, StatusListener statusListener);
	
	/**
	 *
	 * @param token  登录时获得的token
	 * @param table
	 * @param statusListener
	 */
	void RequestCheckIn(String token, long table, StatusListener statusListener);
	
	/**
	 *
	 * @param token  登录时获得的token
	 * @param order
	 * @param table
	 * @param statusListener
	 */
	void requestCheckOut(String token, long order, long table, StatusListener statusListener);
	
	/**
	 *
	 * @param token  登录时获得的token
	 * @param status
	 * @param statusListener
	 */
	void listCurrentOrderItems(String token, int status, StatusListener statusListener);
	
	/**
	 *
	 * @param token  登录时获得的token
	 * @param orderItem
	 * @param statusListener
	 */
	void updateOrderItemStatus(String token, long orderItem, int new_status, int required_status, StatusListener statusListener);
}
