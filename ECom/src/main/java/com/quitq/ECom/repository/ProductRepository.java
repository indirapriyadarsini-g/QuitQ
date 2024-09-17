package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.enums.Category;
import com.quitq.ECom.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

	@Query("select p  from Product p where p.c=?1")
	List<Product> getByCategoryName(Category category);
@Query("select p from Product p join p.v where v.name=?1")
	List<Product> getByVendorName(String name);
@Query("select p from Product p join p.v where v.id=?1")
List<Product> getAllProductOfVendor(int vid);
@Query("select p from Product p where p.status=?1")
List<Product> findByStatus(String status);
@Query("select p from Product p join p.v where p.status=?1 and v.id=?2")
List<Product> findByStatusAndVendor(String status, int id);
@Query("select p from Product p join p.warehouse w where w.id=?1")
List<Product> getByWarehouseId(int id);
@Query("select p from Product p join p.v where v.id=?1 and p.isOutOfStock=?2")
List<Product> findOutOfStockProduct(int id,boolean value);

}
