package com.oxbix.xfood.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerOrderDTO {

	private Long orderId;
	private Integer persons;
	private String customerName;
	private Long takenOn;
	private Integer status;
	private String note;
	private Integer orderType;
	private Long total;
	private String discountType;
	private Long discount;
	private Long payedAmount;
	private List<CustomerOrderItemDTO> orderItems = new ArrayList<CustomerOrderItemDTO>(0);

	public CustomerOrderDTO(Long orderId, Integer persons, String customerName, Long takenOn, Integer status,
			String note, Integer orderType, Long total, String discountType, Long discount, Long payedAmount,
			List<CustomerOrderItemDTO> orderItems) {
		super();
		this.orderId = orderId;
		this.persons = persons;
		this.customerName = customerName;
		this.takenOn = takenOn;
		this.status = status;
		this.note = note;
		this.orderType = orderType;
		this.total = total;
		this.discountType = discountType;
		this.discount = discount;
		this.payedAmount = payedAmount;
		this.orderItems = orderItems;
	}

	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getPersons() {
		return persons;
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getTakenOn() {
		return takenOn;
	}

	public void setTakenOn(Long takenOn) {
		this.takenOn = takenOn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getPayedAmount() {
		return payedAmount;
	}

	public void setPayedAmount(Long payedAmount) {
		this.payedAmount = payedAmount;
	}

	public List<CustomerOrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<CustomerOrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	
	

}
