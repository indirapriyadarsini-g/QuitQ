package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

}
