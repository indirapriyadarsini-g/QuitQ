package com.quitq.ECom.model;

import java.time.LocalDateTime;

import com.quitq.ECom.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	public Order(int id, OrderStatus status, LocalDateTime orderPlacedTime, LocalDateTime orderDeliveredTime, Cart cart,
			double orderAmount, double orderDiscount, double orderFee) {
		super();
		this.id = id;
		this.status = status;
		this.orderPlacedTime = orderPlacedTime;
		this.orderDeliveredTime = orderDeliveredTime;
		this.cart = cart;
		this.orderAmount = orderAmount;
		this.orderDiscount = orderDiscount;
		this.orderFee = orderFee;
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", status=" + status + ", orderPlacedTime=" + orderPlacedTime
				+ ", orderDeliveredTime=" + orderDeliveredTime + ", cart=" + cart + ", orderAmount=" + orderAmount
				+ ", orderDiscount=" + orderDiscount + ", orderFee=" + orderFee + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public LocalDateTime getOrderPlacedTime() {
		return orderPlacedTime;
	}

	public void setOrderPlacedTime(LocalDateTime orderPlacedTime) {
		this.orderPlacedTime = orderPlacedTime;
	}

	public LocalDateTime getOrderDeliveredTime() {
		return orderDeliveredTime;
	}

	public void setOrderDeliveredTime(LocalDateTime orderDeliveredTime) {
		this.orderDeliveredTime = orderDeliveredTime;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public double getOrderDiscount() {
		return orderDiscount;
	}

	public void setOrderDiscount(double orderDiscount) {
		this.orderDiscount = orderDiscount;
	}

	public double getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(double orderFee) {
		this.orderFee = orderFee;
	}

	private LocalDateTime orderPlacedTime;
	
	private LocalDateTime orderDeliveredTime;
	
	@OneToOne
	private Cart cart;
	
	private double orderAmount;
	
	private double orderDiscount;
	
	private double orderFee;
}
