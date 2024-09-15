package com.quitq.ECom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer>{

	@Query("select w from Wishlist w where customer_id = ?1")
	Optional<Wishlist> getProductByCustomerId(int id);

}
