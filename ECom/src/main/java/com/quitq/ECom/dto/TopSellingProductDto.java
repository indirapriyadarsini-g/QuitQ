package com.quitq.ECom.dto;

public class TopSellingProductDto {
private int id;
private String title;
private double discount;
private double price;
private String status;
private double noOfQuantitySold;
public TopSellingProductDto(int id, String title, double discount, double price, String status,
		double noOfQuantitySold) {
	super();
	this.id = id;
	this.title = title;
	this.discount = discount;
	this.price = price;
	this.status = status;
	this.noOfQuantitySold = noOfQuantitySold;
}
public TopSellingProductDto() {
	super();
	// TODO Auto-generated constructor stub
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public double getDiscount() {
	return discount;
}
public void setDiscount(double discount) {
	this.discount = discount;
}
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
public double getNoOfQuantitySold() {
	return noOfQuantitySold;
}
public void setNoOfQuantitySold(double noOfQuantitySold) {
	this.noOfQuantitySold = noOfQuantitySold;
}


}
