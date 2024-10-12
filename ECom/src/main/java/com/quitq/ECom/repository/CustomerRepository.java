package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.User;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{

	@Query("select c from Customer c where c.user in (select u from User u where username = ?1)")
	Customer getCustomerByUsername(String username);

	@Query("select c from Customer c where c.user = ?1")
	Customer getCustomerByUser(User user);

}
