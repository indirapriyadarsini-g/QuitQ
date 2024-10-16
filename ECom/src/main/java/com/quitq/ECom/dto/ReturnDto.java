package com.quitq.ECom.dto;



public class ReturnDto {

	private String returnReason;
	
	private int opId;
	
	private int returnQuantity;
	
	private int returnAmount;

	public ReturnDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public int getOpId() {
		return opId;
	}



	public void setOpId(int opId) {
		this.opId = opId;
	}



	public ReturnDto(String returnReason, int opId, int returnQuantity, int returnAmount) {
		super();
		this.returnReason = returnReason;
		this.opId = opId;
		this.returnQuantity = returnQuantity;
		this.returnAmount = returnAmount;
	}



	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
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
	
	
	
	
}
