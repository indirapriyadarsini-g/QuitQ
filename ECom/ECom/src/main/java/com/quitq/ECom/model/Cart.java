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
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Customer customer;
	
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

	public double getCartTotalAmount() {
		return cartTotalAmount;
	}

	public void setCartTotalAmount(double cartTotalAmount) {
		this.cartTotalAmount = cartTotalAmount;
	}

	public StatusType getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(StatusType cartStatus) {
		this.cartStatus = cartStatus;
	}

	public int getCartQuantity() {
		return cartQuantity;
	}

	public void setCartQuantity(int cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	private double cartTotalAmount;
	
	@Enumerated(EnumType.STRING)
	private StatusType cartStatus;
	
	private int cartQuantity;

	@Override
	public String toString() {
		return "Cart [id=" + id + ", customer=" + customer + ", cartTotalAmount=" + cartTotalAmount + ", cartStatus="
				+ cartStatus + ", cartQuantity=" + cartQuantity + "]";
	}

	public Cart(int id, Customer customer, double cartTotalAmount, StatusType cartStatus, int cartQuantity) {
		super();
		this.id = id;
		this.customer = customer;
		this.cartTotalAmount = cartTotalAmount;
		this.cartStatus = cartStatus;
		this.cartQuantity = cartQuantity;
	}
	
	public Cart() {
		
	}
	
}
