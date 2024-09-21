package com.quitq.ECom.dto;

public class ProductStatsDto {
private int id;
private String title;
private double discount;
private double price;
private String status;
private double noOfTimesOrdered;
private double noOfQuantitySold;
private double averageRating;
public ProductStatsDto() {
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
public double getNoOfTimesOrdered() {
	return noOfTimesOrdered;
}
public void setNoOfTimesOrdered(double noOfTimesOrdered) {
	this.noOfTimesOrdered = noOfTimesOrdered;
}
public double getNoOfQuantitySold() {
	return noOfQuantitySold;
}
public void setNoOfQuantitySold(double noOfQuantitySold) {
	this.noOfQuantitySold = noOfQuantitySold;
}
public double getAverageRating() {
	return averageRating;
}
public void setAverageRating(double averageRating) {
	this.averageRating = averageRating;
}

}
