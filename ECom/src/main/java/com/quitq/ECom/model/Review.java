package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int stars;
	
	private String reviewContent;
	
	@OneToOne
	private Customer customer;
	
	@OneToOne
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", stars=" + stars + ", reviewContent=" + reviewContent + ", customer=" + customer
				+ ", product=" + product + "]";
	}

	public Review(int id, int stars, String reviewContent, Customer customer, Product product) {
		super();
		this.id = id;
		this.stars = stars;
		this.reviewContent = reviewContent;
		this.customer = customer;
		this.product = product;
	}
	
}
