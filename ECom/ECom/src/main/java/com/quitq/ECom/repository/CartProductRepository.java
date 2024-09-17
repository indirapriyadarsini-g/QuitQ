package com.quitq.ECom.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;

import jakarta.transaction.Transactional;


public interface CartProductRepository extends JpaRepository<CartProduct,Integer>{

	

	@Query("select cp from CartProduct cp where cp.cart = ?1")
	Optional<List<CartProduct>>  getCartProductsByCart(Cart cart);
	
	@Transactional
	@Modifying
	@Query("delete from CartProduct cp where cp.cart = ?1")
	void deleteCartProductsByCart(Cart cart);

}
