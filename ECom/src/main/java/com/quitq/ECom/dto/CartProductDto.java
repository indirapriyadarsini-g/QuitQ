package com.quitq.ECom.dto;

import java.util.List;

import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Image;

public class CartProductDto {
	
	private CartProduct cartProduct;
	
	private List<Image> imList;
	
	
		



	public CartProductDto(CartProduct cartProduct, List<Image> imList) {
		super();
		this.cartProduct = cartProduct;
		this.imList = imList;
	}






	public CartProduct getCartProduct() {
		return cartProduct;
	}






	public void setCartProduct(CartProduct cartProduct) {
		this.cartProduct = cartProduct;
	}






	public List<Image> getImList() {
		return imList;
	}






	public void setImList(List<Image> imList) {
		this.imList = imList;
	}






	public CartProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
