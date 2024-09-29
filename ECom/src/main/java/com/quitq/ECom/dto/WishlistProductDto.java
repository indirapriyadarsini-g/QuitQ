package com.quitq.ECom.dto;

import java.util.List;

import com.quitq.ECom.model.Image;
import com.quitq.ECom.model.WishlistProduct;

public class WishlistProductDto {

	private WishlistProduct wishlistProduct;
	
	private List<Image> imList;

	public WishlistProductDto(WishlistProduct wishlistProduct, List<Image> imList) {
		super();
		this.wishlistProduct = wishlistProduct;
		this.imList = imList;
	}

	public WishlistProductDto() {
		// TODO Auto-generated constructor stub
	}

	public WishlistProduct getWishlistProduct() {
		return wishlistProduct;
	}

	public void setWishlistProduct(WishlistProduct wishlistProduct) {
		this.wishlistProduct = wishlistProduct;
	}

	public List<Image> getImList() {
		return imList;
	}

	public void setImList(List<Image> imList) {
		this.imList = imList;
	}
	
	
}
