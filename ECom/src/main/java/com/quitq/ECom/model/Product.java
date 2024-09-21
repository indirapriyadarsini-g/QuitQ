package com.quitq.ECom.model;

import com.quitq.ECom.enums.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Product{
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id; 

@Column(nullable = false)
private String title;

@Column(nullable = false)
private double price;
@Column(nullable = false)
private double discount;
@Column(nullable = false)
private String status="active";
private boolean isOutOfStock=false;
@Column(nullable = false)

private double quantity;
public boolean isOutOfStock() {
	return isOutOfStock;
}
public void setOutOfStock(boolean isOutOfStock) {
	this.isOutOfStock = isOutOfStock;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

@ManyToOne
private Vendor v;
@Enumerated(EnumType.STRING)

private Category c;
@ManyToOne
private Warehouse warehouse;

public Product(String title, double price, double discount, String status, boolean isOutOfStock, double quantity,
		 Category c,Vendor v) {
	super();
	this.title = title;
	this.price = price;
	this.discount = discount;
	this.status = status;
	this.isOutOfStock = isOutOfStock;
	this.quantity = quantity;
	this.v = v;
	this.c = c;
}
public Product() {
	super();
	// TODO Auto-generated constructor stub
}
public double getQuantity() {
	return quantity;
}
public void setQuantity(double quantity) {
	this.quantity = quantity;
}
public Warehouse getWarehouse() {
	return warehouse;
}
public void setWarehouse(Warehouse warehouse) {
	this.warehouse = warehouse;
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
public double getPrice() {
	return price;
}

public Category getC() {
	return c;
}
public void setC(Category c) {
	this.c = c;
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
public Vendor getV() {
	return v;
}
public void setV(Vendor v) {
	this.v = v;
}

}