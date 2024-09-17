package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quitq.ECom.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer>{

}
