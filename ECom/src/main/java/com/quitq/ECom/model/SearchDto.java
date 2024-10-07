package com.quitq.ECom.model;

public class SearchDto {
	
	private String category;
	
	private int minDiscount;
	
	private String prodName;
	
	private String includeOutOfStock;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getMinDiscount() {
		return minDiscount;
	}

	public void setMinDiscount(int minDiscount) {
		this.minDiscount = minDiscount;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getIncludeOutOfStock() {
		return includeOutOfStock;
	}

	public void setIncludeOutOfStock(String includeOutOfStock) {
		this.includeOutOfStock = includeOutOfStock;
	}

	public SearchDto(String category, int minDiscount, String prodName, String includeOutOfStock) {
		super();
		this.category = category;
		this.minDiscount = minDiscount;
		this.prodName = prodName;
		this.includeOutOfStock = includeOutOfStock;
	}

	public SearchDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
