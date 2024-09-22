package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Customer;
import com.quitq.ECom.model.UserInfo;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{

	@Query("select c from Customer c where c.userInfo in (select u from UserInfo u where username = ?1)")
	Customer getCustomerByUsername(String username);

	@Query("select c from Customer c where c.userInfo = ?1")
	Customer getCustomerByUser(UserInfo user);

}
