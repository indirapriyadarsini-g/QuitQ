package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quitq.ECom.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

}
