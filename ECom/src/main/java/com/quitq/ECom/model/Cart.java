package com.quitq.ECom.model;


import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;



@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany
	private List<Product> productList;
	
	public Cart(int id, List<Product> productList, Customer customer, double cartTotal) {
		super();
		this.id = id;
		this.productList = productList;

		this.customer = customer;
		this.cartTotal = cartTotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Customer getCustomer() {

		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", productList=" + productList + ", customer=" + customer + ", cartTotal=" + cartTotal
				+ "]";
	}

	@OneToOne
	private Customer customer;
	@Column(nullable = false)


	private double cartTotal;
	
}
