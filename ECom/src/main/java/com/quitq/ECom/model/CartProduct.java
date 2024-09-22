package com.quitq.ECom.model;

import com.quitq.ECom.enums.StatusType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class CartProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int productQuantity;
	
	private double productTotalAmount;
	
	private double productDiscount;
	
	private double amountPayable;
	
	@Enumerated(EnumType.STRING)
	private StatusType status;
	
	@OneToOne
	private Cart cart;
	@OneToOne
	private Product product;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public double getProductTotalAmount() {
		return productTotalAmount;
	}
	public void setProductTotalAmount(double productTotalAmount) {
		this.productTotalAmount = productTotalAmount;
	}
	public double getProductDiscount() {
		return productDiscount;
	}
	public void setProductDiscount(double productDiscount) {
		this.productDiscount = productDiscount;
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
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "CartProduct [id=" + id + ", productQuantity=" + productQuantity + ", productTotalAmount="
				+ productTotalAmount + ", productDiscount=" + productDiscount + ", amountPayable=" + amountPayable
				+ ", status=" + status + ", cart=" + cart + ", product=" + product + "]";
	}
	public CartProduct(int id, int productQuantity, double productTotalAmount, double productDiscount,
			double amountPayable, StatusType status, Cart cart, Product product) {
		super();
		this.id = id;
		this.productQuantity = productQuantity;
		this.productTotalAmount = productTotalAmount;
		this.productDiscount = productDiscount;
		this.amountPayable = amountPayable;
		this.status = status;
		this.cart = cart;
		this.product = product;
	}
	
	
}