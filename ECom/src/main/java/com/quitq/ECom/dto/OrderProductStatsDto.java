package com.quitq.ECom.dto;

public class OrderProductStatsDto {
private String status;
private int number;
private double price;
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
public OrderProductStatsDto(String status, int number) {
	super();
	this.status = status;
	this.number = number;
}
public OrderProductStatsDto() {
	super();
	// TODO Auto-generated constructor stub
}

}
