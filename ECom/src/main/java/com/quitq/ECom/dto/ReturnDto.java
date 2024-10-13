package com.quitq.ECom.dto;

import com.quitq.ECom.model.OrderProduct;

public class ReturnDto {

	private String returnReason;
	
	private OrderProduct orderProduct;
	
	private int returnQuantity;
	
	private int returnAmount;

	public ReturnDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReturnDto(String returnReason, OrderProduct orderProduct, int returnQuantity, int returnAmount) {
		super();
		this.returnReason = returnReason;
		this.orderProduct = orderProduct;
		this.returnQuantity = returnQuantity;
		this.returnAmount = returnAmount;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public OrderProduct getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}

	public int getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public int getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(int returnAmount) {
		this.returnAmount = returnAmount;
	}
	
	
	
	
}
