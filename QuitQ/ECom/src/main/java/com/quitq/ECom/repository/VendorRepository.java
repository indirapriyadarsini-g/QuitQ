package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Vendor;

public interface VendorRepository extends CrudRepository<Vendor, Integer> {

    @Query("SELECT p FROM Product p WHERE p.vendor.warehouse.id = :warehouseId")
    List<Product> findProductsByWarehouseId(Integer warehouseId);
}
