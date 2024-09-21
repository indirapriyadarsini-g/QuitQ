package com.quitq.ECom.model;

import java.time.LocalDateTime;

import com.quitq.ECom.enums.RefundStatus;
import com.quitq.ECom.enums.ReturnStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="return_table")
public class Return {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
	@ManyToOne
	private ReturnReason returnReason;
	@ManyToOne 
	private OrderProduct orderProduct;
	private int returnQuantity;
	private int returnAmount;
	@Enumerated(EnumType.STRING)
	private ReturnStatus returnStatus;
	private LocalDateTime returnInitiatedDate;
	private LocalDateTime returnCompletedDate;
	private LocalDateTime returnPickUpDate;
	@Enumerated(EnumType.STRING)
	private RefundStatus refundStatus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ReturnReason getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(ReturnReason returnReason) {
		this.returnReason = returnReason;
	}
	public OrderProduct getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
	}
	public int getReturnQuantity() {
		return returnQuantity;
	}
	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	public int getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(int returnAmount) {
		this.returnAmount = returnAmount;
	}
	public ReturnStatus getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
	public LocalDateTime getReturnInitiatedDate() {
		return returnInitiatedDate;
	}
	public void setReturnInitiatedDate(LocalDateTime returnInitiatedDate) {
		this.returnInitiatedDate = returnInitiatedDate;
	}
	public LocalDateTime getReturnCompletedDate() {
		return returnCompletedDate;
	}
	public void setReturnCompletedDate(LocalDateTime returnCompletedDate) {
		this.returnCompletedDate = returnCompletedDate;
	}
	public LocalDateTime getReturnPickUpDate() {
		return returnPickUpDate;
	}
	public void setReturnPickUpDate(LocalDateTime returnPickUpDate) {
		this.returnPickUpDate = returnPickUpDate;
	}
	public RefundStatus getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(RefundStatus refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Return(int id, ReturnReason returnReason, OrderProduct orderProduct, int returnQuantity, int returnAmount,
			ReturnStatus returnStatus, LocalDateTime returnInitiatedDate, LocalDateTime returnCompletedDate,
			LocalDateTime returnPickUpDate, RefundStatus refundStatus) {
		super();
		this.id = id;
		this.returnReason = returnReason;
		this.orderProduct = orderProduct;
		this.returnQuantity = returnQuantity;
		this.returnAmount = returnAmount;
		this.returnStatus = returnStatus;
		this.returnInitiatedDate = returnInitiatedDate;
		this.returnCompletedDate = returnCompletedDate;
		this.returnPickUpDate = returnPickUpDate;
		this.refundStatus = refundStatus;
	}
	public Return() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
