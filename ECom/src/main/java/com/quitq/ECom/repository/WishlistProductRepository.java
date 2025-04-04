package com.quitq.ECom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.WishlistProduct;

public interface WishlistProductRepository extends JpaRepository<WishlistProduct,Integer>{

	@Query("select wp from WishlistProduct wp join wp.wishlist w where w.customer = ?1")
	Optional<List<WishlistProduct>> getWishlistProductByCustomer(Customer customer);

	@Query("select wp from WishlistProduct wp join wp.wishlist w join w.customer c join c.user u where u.username = ?1")
	List<WishlistProduct> getWishlistProductByUsername(String name);

	@Query("select p from WishlistProduct wp join wp.product p where wp = ?1")
	Product getProductByWishlistProduct(WishlistProduct wp);
}
