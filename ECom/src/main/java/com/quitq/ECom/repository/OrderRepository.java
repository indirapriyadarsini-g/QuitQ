package com.quitq.ECom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Cart;
import com.quitq.ECom.model.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{

	@Query("select o from Order o join c.customer cust join cust.user u where u.username = ?1")
	Optional<Order> getOrderByUsername(String name);

}
