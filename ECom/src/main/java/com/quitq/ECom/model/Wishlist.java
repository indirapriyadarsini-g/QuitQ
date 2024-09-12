package com.quitq.ECom.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Wishlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Customer customer;
	
	@OneToMany
	private List<Product> productList;

	public Wishlist(int id, Customer customer, List<Product> productList) {
		super();
		this.id = id;
		this.customer = customer;
		this.productList = productList;
	}

	@Override
	public String toString() {
		return "Wishlist [id=" + id + ", customer=" + customer + ", productList=" + productList + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
