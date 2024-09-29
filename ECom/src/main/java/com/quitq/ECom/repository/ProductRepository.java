package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.enums.Category;
import com.quitq.ECom.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

	@Query("select p  from Product p where p.c=?1")
	List<Product> getByCategoryName(Category category);
@Query("select p from Product p join p.v where v.name=?1")
	List<Product> getByVendorName(String name);
@Query("select p from Product p join p.v where v.id=?1")
List<Product> getAllProductOfVendor(int vid);
@Query("select p from Product p where p.status=?1")
List<Product> findByStatus(String status);
@Query("select p from Product p join p.v vendor where p.status=?1 and vendor.user.username=?2")
List<Product> findByStatusAndUsername(String status,String username);
@Query("select p from Product p join p.warehouse w where w.id=?1")
List<Product> getByWarehouseId(int id);
@Query("select p from Product p join p.v where v.user.username=?1 and p.isOutOfStock=?2")
List<Product> findOutOfStockProduct(String username,boolean value);
@Query("select p  from Product p join p.v vendor where vendor.user.username=?1")
List<Product> findByVendorUsrname(String username);
@Query("select distinct p.c from Product p join p.v vendor where vendor.user.username=?1")
List<Object[]> findCategorySoldByVendor(String username);
@Query("select p from Product p join p.v vendor where vendor.user.username=?1 and p.title like %?2%")
List<Product> findProductByNameAndUsername(String username, String name);



}
