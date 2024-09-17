package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.OrderProduct;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer>{

}
