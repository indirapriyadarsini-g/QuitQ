package com.quitq.ECom.model;

import java.time.LocalDateTime;

import com.quitq.ECom.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	public Order(int id, OrderStatus status, LocalDateTime orderPlacedTime, LocalDateTime orderDeliveredTime,
			double orderAmount, double orderDiscount, double orderFee) {
		super();
		this.id = id;
		this.status = status;
		this.orderPlacedTime = orderPlacedTime;
		this.orderDeliveredTime = orderDeliveredTime;
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
				+ ", orderDeliveredTime=" + orderDeliveredTime +  ", orderAmount=" + orderAmount
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
	@Column(nullable = false)

	private LocalDateTime orderPlacedTime;

	private LocalDateTime orderDeliveredTime;
	
	@Column(nullable = false)

	private double orderAmount;
	@Column(nullable = false)

	private double orderDiscount;
	@Column(nullable = false)

	private double orderFee;
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	@ManyToOne
	private Cart cart;

	public Order(int id, OrderStatus status, LocalDateTime orderPlacedTime, LocalDateTime orderDeliveredTime,
			double orderAmount, double orderDiscount, double orderFee, Cart cart) {
		super();
		this.id = id;
		this.status = status;
		this.orderPlacedTime = orderPlacedTime;
		this.orderDeliveredTime = orderDeliveredTime;
		this.orderAmount = orderAmount;
		this.orderDiscount = orderDiscount;
		this.orderFee = orderFee;
		this.cart = cart;
	}
	
	
}
