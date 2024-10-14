package com.quitq.ECom.dto;

import java.util.List;

import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;

public class OrderSummaryDto {
	
	private Order order;
	
	private List<OrderProductWImageDto> opwiList;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderProductWImageDto> getOpwiList() {
		return opwiList;
	}

	public void setOpwiList(List<OrderProductWImageDto> opwiList) {
		this.opwiList = opwiList;
	}

	public OrderSummaryDto(Order order, List<OrderProductWImageDto> opwiList) {
		super();
		this.order = order;
		this.opwiList = opwiList;
	}

	public OrderSummaryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
