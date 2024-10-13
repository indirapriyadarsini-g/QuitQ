package com.quitq.ECom.dto;

import java.util.List;

import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;

public class OrderSummaryDto {
	
	private Order order;
	
	private List<OrderProduct> orderProductList;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}

	public OrderSummaryDto(Order order, List<OrderProduct> orderProductList) {
		super();
		this.order = order;
		this.orderProductList = orderProductList;
	}

	public OrderSummaryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
