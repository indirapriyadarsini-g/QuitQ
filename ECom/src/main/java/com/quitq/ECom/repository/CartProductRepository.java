package com.quitq.ECom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.CartProduct;
import com.quitq.ECom.model.Product;

import jakarta.transaction.Transactional;

public interface CartProductRepository extends JpaRepository<CartProduct,Integer>{

	@Query("select cp from CartProduct cp where cp.cart = ?1")
	Optional<List<CartProduct>>  getCartProductsByCart(Cart cart);
	
	@Transactional
	@Modifying
	@Query("delete from CartProduct cp where cp.cart = ?1")
	int deleteCartProductsByCart(Cart cart);

	
	@Transactional
	@Modifying
	@Query("update CartProduct cp set cp.productQuantity = cp.productQuantity+1 where cp=?1")
	int addProductCount(CartProduct cartProduct);

	
	@Transactional
	@Modifying
	@Query("update CartProduct cp set cp.productQuantity = cp.productQuantity-1 where cp=?1")
	int subProductCount(CartProduct cartProduct);

	
	
	
	@Query("select cp from CartProduct cp join cp.cart c join c.customer cust join cust.userInfo u where u.username = ?1")
	Optional<List<CartProduct>> getCartProductByUsername(String username);

	@Query("select p from CartProduct cp join cp.product p where cp = ?1")
	Product getProductByCartProduct(CartProduct cp);

	@Query("select cp from CartProduct cp where cp.cart = ?1 and cp.product = ?2")
	Optional<CartProduct> getCartProductByCartAndProduct(Cart cart,Product product);
}
