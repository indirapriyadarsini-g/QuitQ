package com.quitq.ECom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

	@Query("select o from Order o join o.cart c join c.customer cust join cust.userInfo u where u.username = ?1")
	Optional<Order> getOrderByUsername(String name);

}
