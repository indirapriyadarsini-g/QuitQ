package com.quitq.ECom.dto;

import com.quitq.ECom.model.OrderProduct;

public class ExchangeDto {

private String exchangeReason;
	
	private OrderProduct orderProduct;
	
	private int exchangeQuantity;

	@Override
	public String toString() {
		return "ExchangeDto [exchangeReason=" + exchangeReason + ", orderProduct=" + orderProduct
				+ ", exchangeQuantity=" + exchangeQuantity + "]";
	}

	public ExchangeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}

	public OrderProduct getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}

	public int getExchangeQuantity() {
		return exchangeQuantity;
	}

	public void setExchangeQuantity(int exchangeQuantity) {
		this.exchangeQuantity = exchangeQuantity;
	}
	


	
	
	
}
