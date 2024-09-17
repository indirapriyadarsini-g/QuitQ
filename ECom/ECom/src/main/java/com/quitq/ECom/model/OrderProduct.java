package com.quitq.ECom.model;

import com.quitq.ECom.enums.StatusType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Product product;
	
	@OneToOne
	private Review review;
	
	@ManyToOne
	private Warehouse warehouse;
	
	private int quantity;
	
	private double totalAmount;
	
	private double discount;
	
	private double amountPayable;
	
	private StatusType status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(double amountPayable) {
		this.amountPayable = amountPayable;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", order=" + order + ", product=" + product + ", review=" + review
				+ ", warehouse=" + warehouse + ", quantity=" + quantity + ", totalAmount=" + totalAmount + ", discount="
				+ discount + ", amountPayable=" + amountPayable + "]";
	}

	public OrderProduct(int id, Order order, Product product, Review review, Warehouse warehouse, int quantity,
			double totalAmount, double discount, double amountPayable, StatusType status) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.review = review;
		this.warehouse = warehouse;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.discount = discount;
		this.amountPayable = amountPayable;
		this.status = status;
	}

	public OrderProduct() {
		// TODO Auto-generated constructor stub
	}
	
	
}
