package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Integer> {

    @Query("SELECT p FROM Product p WHERE p.vendor.warehouse.id = :warehouseId")
    List<Product> findProductsByWarehouseId(Integer warehouseId);
    @Query("select v from Product p join p.v vendor where p.id=?1")
	Vendor findByProductId(int id);
    @Query("select v from Product p join p.v vendor join p.c cat where cat.categoryName=?1")
    List<Vendor> findByCategoryName(String categoryName);
}
