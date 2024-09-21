package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ExchangeReason {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
		private String exchangeReason;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getExchangeReason() {
			return exchangeReason;
		}
		public void setExchangeReason(String exchangeReason) {
			this.exchangeReason = exchangeReason;
		}
		public ExchangeReason(int id, String exchangeReason) {
			super();
			this.id = id;
			this.exchangeReason = exchangeReason;
		}
		public ExchangeReason() {
			super();
			// TODO Auto-generated constructor stub
		}

}
