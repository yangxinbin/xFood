package com.oxbix.xfood.dto;

import java.io.Serializable;
import java.util.Date;

public class CustomerOrderItemDTO implements Serializable {

	
//	/** OrderDetai的服务器id **/
//	private Long id;
//	/** customerOrder的服务器id, 一个customerOrder可以包含多个OrderDetai **/
//	private Long customerOrder;
//	/** 订单中食物的服务器id **/
//	private Long foodServerId;
//	/** 订单中食物的名称 **/
//	private String foodName;
//	/** 订单中食物的数量 **/
//	private Integer foodQuantity;
//	/** 订单中食物的价格 **/
//	private Double foodPrice;
//	/** 客户留言 **/
//	private String note;
//	/** 订单状态，默认为0 **/
//	private Integer status;
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long customerOrder;
	private Long itemId;
	private String itemName;
	private Integer quantity;
	private Double price;
	private String note;
	private Integer status;
	private Long preparedBy;
	private Long preparedOn;
	private String statusHistory;
	private String orderTable;
	private String orderTableName;
	private long location;
	private Long orderedOn;

	public CustomerOrderItemDTO() {
	}

	public CustomerOrderItemDTO(long location) {
		this.location = location;
	}

	public CustomerOrderItemDTO(Long id, Long customerOrder, Long itemId, String itemName, Integer quantity, Double price, String note, Integer status) {
		super();
		this.id = id;
		this.customerOrder = customerOrder;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
		this.note = note;
		this.status = status;
	}

	public CustomerOrderItemDTO(Long id, Long customerOrder, Long itemId, String itemName, Integer quantity, Double price, String note, Integer status,
			Long preparedBy, Long preparedOn, String statusHistory, String orderTable, String orderTableName, long location, Long orderedOn) {
		this.id = id;
		this.customerOrder = customerOrder;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
		this.note = note;
		this.status = status;
		this.preparedBy = preparedBy;
		this.preparedOn = preparedOn;
		this.statusHistory = statusHistory;
		this.orderTable = orderTable;
		this.orderTableName = orderTableName;
		this.location = location;
		this.orderedOn = orderedOn;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerOrder() {
		return this.customerOrder;
	}

	public void setCustomerOrder(Long customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPreparedBy() {
		return this.preparedBy;
	}

	public void setPreparedBy(Long preparedBy) {
		this.preparedBy = preparedBy;
	}

	public Long getPreparedOn() {
		return this.preparedOn;
	}

	public void setPreparedOn(Long preparedOn) {
		this.preparedOn = preparedOn;
	}

	public String getStatusHistory() {
		return this.statusHistory;
	}

	public void setStatusHistory(String statusHistory) {
		this.statusHistory = statusHistory;
	}

	public String getOrderTable() {
		return this.orderTable;
	}

	public void setOrderTable(String orderTable) {
		this.orderTable = orderTable;
	}

	public String getOrderTableName() {
		return orderTableName;
	}

	public void setOrderTableName(String orderTableName) {
		this.orderTableName = orderTableName;
	}

	public long getLocation() {
		return this.location;
	}

	public void setLocation(long location) {
		this.location = location;
	}

	public Long getOrderedOn() {
		return this.orderedOn;
	}

	public void setOrderedOn(Long orderedOn) {
		this.orderedOn = orderedOn;
	}

}
