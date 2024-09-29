package com.quitq.ECom.dto;

import java.util.List;

import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Review;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class OrderProductDto {
	
	private int orderProdId;
	
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
	
	private List<Image> imList;
	
	
	
	public int getOrderProdId() {
		return orderProdId;
	}

	public void setOrderProdId(int orderProdId) {
		this.orderProdId = orderProdId;
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

	public List<Image> getImList() {
		return imList;
	}

	public void setImList(List<Image> imList) {
		this.imList = imList;
	}

	public OrderProductDto() {
		
	}

	public OrderProductDto(OrderProduct op, List<Image> imList) {
		super();
		this.orderProdId = op.getId();
		this.order = op.getOrder();
		this.product = op.getProduct();
		this.review = op.getReview();
		this.quantity = op.getQuantity();
		this.totalAmount = op.getTotalAmount();
		this.discount = op.getDiscount();
		this.amountPayable = op.getAmountPayable();
		this.imList = imList;
	}
	
}
