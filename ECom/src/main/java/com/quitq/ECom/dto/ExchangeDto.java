package com.quitq.ECom.dto;


public class ExchangeDto {

private String exchangeReason;
	
	private int opId;
	
	private int exchangeQuantity;

	

	public int getOpId() {
		return opId;
	}

	public void setOpId(int opId) {
		this.opId = opId;
	}

	public ExchangeDto(String exchangeReason, int opId, int exchangeQuantity) {
		super();
		this.exchangeReason = exchangeReason;
		this.opId = opId;
		this.exchangeQuantity = exchangeQuantity;
	}

	public ExchangeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}

	

	public int getExchangeQuantity() {
		return exchangeQuantity;
	}

	public void setExchangeQuantity(int exchangeQuantity) {
		this.exchangeQuantity = exchangeQuantity;
	}
	


	
	
	
}
