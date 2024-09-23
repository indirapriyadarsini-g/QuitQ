package com.quitq.ECom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.WishlistProduct;

public interface WishlistProductRepository extends JpaRepository<WishlistProduct,Integer>{

	@Query("select wp from WishlistProduct wp join wp.wishlist w where w.customer = ?1")
	Optional<List<WishlistProduct>> getWishlistProductByCustomer(Customer customer);
}
