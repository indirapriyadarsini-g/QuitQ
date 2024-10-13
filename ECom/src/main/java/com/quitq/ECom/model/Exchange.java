package com.quitq.ECom.model;

import java.time.LocalDateTime;

import com.quitq.ECom.enums.ExchangeStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Exchange {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
	@ManyToOne
	private ExchangeReason exchangeReason;
	@ManyToOne 
	private OrderProduct orderProduct;
	private int exchangeQuantity;
	@Enumerated(EnumType.STRING)
	private ExchangeStatus exchangeStatus;
	@ManyToOne
	private Product exchangeProduct;
	private LocalDateTime exchangeInitiatedDate;
	private LocalDateTime exchangeCompletedDate;
	private LocalDateTime exchangePickUpDate;
	public Exchange() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Exchange(int id, ExchangeReason exchangeReason, OrderProduct orderProduct, int exchangeQuantity,
			ExchangeStatus returnStatus, Product exchangeProduct, LocalDateTime exchangeInitiatedDate,
			LocalDateTime exchangeCompletedDate, LocalDateTime exchangePickUpDate) {
		super();
		this.id = id;
		this.exchangeReason = exchangeReason;
		this.orderProduct = orderProduct;
		this.exchangeQuantity = exchangeQuantity;
		this.exchangeStatus = returnStatus;
		this.exchangeProduct = exchangeProduct;
		this.exchangeInitiatedDate = exchangeInitiatedDate;
		this.exchangeCompletedDate = exchangeCompletedDate;
		this.exchangePickUpDate = exchangePickUpDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ExchangeReason getExchangeReason() {
		return exchangeReason;
	}
	public void setExchangeReason(ExchangeReason exchangeReason) {
		this.exchangeReason = exchangeReason;
	}
	public OrderProduct getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}
	public int getExchangeQuantity() {
		return exchangeQuantity;
	}
	public void setExchangeQuantity(int exchangeQuantity) {
		this.exchangeQuantity = exchangeQuantity;
	}
	public ExchangeStatus getExchangeStatus() {
		return exchangeStatus;
	}
	public void setExchangeStatus(ExchangeStatus returnStatus) {
		this.exchangeStatus = returnStatus;
	}
	public Product getExchangeProduct() {
		return exchangeProduct;
	}
	public void setExchangeProduct(Product exchangeProduct) {
		this.exchangeProduct = exchangeProduct;
	}
	public LocalDateTime getExchangeInitiatedDate() {
		return exchangeInitiatedDate;
	}
	public void setExchangeInitiatedDate(LocalDateTime exchangeInitiatedDate) {
		this.exchangeInitiatedDate = exchangeInitiatedDate;
	}
	public LocalDateTime getExchangeCompletedDate() {
		return exchangeCompletedDate;
	}
	public void setExchangeCompletedDate(LocalDateTime exchangeCompletedDate) {
		this.exchangeCompletedDate = exchangeCompletedDate;
	}
	public LocalDateTime getExchangePickUpDate() {
		return exchangePickUpDate;
	}
	public void setExchangePickUpDate(LocalDateTime exchangePickUpDate) {
		this.exchangePickUpDate = exchangePickUpDate;
	}


}
