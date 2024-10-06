package com.quitq.ECom.dto;

import java.time.LocalDate;

public class OrderProductDetailDto {
private int orderId;
private String orderStatus;
private int orderQuantity;
private double amountPayable;
private double totalAmount;
private double discountAmount;
private String title;
private double price;
private double discount;
private LocalDate orderPlacedTime;
public OrderProductDetailDto(int orderId, String orderStatus, int orderQuantity, double amountPayable,
		double totalAmount, double discountAmount, String title, double price, double discount,
		LocalDate orderPlacedTime) {
	super();
	this.orderId = orderId;
	this.orderStatus = orderStatus;
	this.orderQuantity = orderQuantity;
	this.amountPayable = amountPayable;
	this.totalAmount = totalAmount;
	this.discountAmount = discountAmount;
	this.title = title;
	this.price = price;
	this.discount = discount;
	this.orderPlacedTime = orderPlacedTime;
}
public LocalDate getOrderPlacedTime() {
	return orderPlacedTime;
}
public void setOrderPlacedTime(LocalDate orderPlacedTime) {
	this.orderPlacedTime = orderPlacedTime;
}
public int getOrderId() {
	return orderId;
}
public void setOrderId(int orderId) {
	this.orderId = orderId;
}
public String getOrderStatus() {
	return orderStatus;
}
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
}
public int getOrderQuantity() {
	return orderQuantity;
}
public void setOrderQuantity(int orderQuantity) {
	this.orderQuantity = orderQuantity;
}
public double getAmountPayable() {
	return amountPayable;
}
public void setAmountPayable(double amountPayable) {
	this.amountPayable = amountPayable;
}
public double getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
}
public double getDiscountAmount() {
	return discountAmount;
}
public void setDiscountAmount(double discountAmount) {
	this.discountAmount = discountAmount;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public double getDiscount() {
	return discount;
}
public void setDiscount(double discount) {
	this.discount = discount;
}
public OrderProductDetailDto() {
	super();
	// TODO Auto-generated constructor stub
}

}