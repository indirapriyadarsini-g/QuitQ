
package com.quitq.ECom.dto;

import org.springframework.stereotype.Component;

@Component
public class OrderInvoiceDto {
	
	private int orderId;
	
	private String msg;
	
	private double amount;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public OrderInvoiceDto(int orderId, String msg, double amount) {
		super();
		this.orderId = orderId;
		this.msg = msg;
		this.amount = amount;
	}
	
	public OrderInvoiceDto() {
		
	}
	
}
