package com.quitq.ECom.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart,Integer>{

	@Query("select c from Cart c where customer_id = ?1")
	Optional<Cart> getProductByCustomerId(int id);

}
