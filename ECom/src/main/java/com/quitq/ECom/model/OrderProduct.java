package com.quitq.ECom.model;

import com.quitq.ECom.enums.StatusType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class OrderProduct {
	public OrderProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Product product;
	
	@OneToOne
	private Review review;
	
	
	private int quantity;
	
	private double totalAmount;
	
	private double discount;
	
	private double amountPayable;
	

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



	public OrderProduct(int id, Order order, Product product, Review review, int quantity, double totalAmount,
			double discount, double amountPayable) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.review = review;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.discount = discount;
		this.amountPayable = amountPayable;
	}

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", order=" + order + ", product=" + product + ", review=" + review
				+ ", quantity=" + quantity + ", totalAmount=" + totalAmount + ", discount=" + discount
				+ ", amountPayable=" + amountPayable + "]";
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

	

	

	
	
}
