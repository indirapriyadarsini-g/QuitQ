package com.quitq.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class WishlistProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private Wishlist wishlist;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private CartProduct cartProduct;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public CartProduct getCartProduct() {
		return cartProduct;
	}

	public void setCartProduct(CartProduct cartProduct) {
		this.cartProduct = cartProduct;
	}

	@Override
	public String toString() {
		return "WishlistProduct [id=" + id + ", wishlist=" + wishlist + ", product=" + product + ", cartProduct="
				+ cartProduct + "]";
	}

	public WishlistProduct(int id, Wishlist wishlist, Product product, CartProduct cartProduct) {
		super();
		this.id = id;
		this.wishlist = wishlist;
		this.product = product;
		this.cartProduct = cartProduct;
	}

	public WishlistProduct() {
		// TODO Auto-generated constructor stub
	}
	
}
