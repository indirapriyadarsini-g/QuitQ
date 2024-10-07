package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.enums.Category;
import com.quitq.ECom.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

	@Query("select p  from Product p where p.c=?1")
	List<Product> getByCategoryName(Category category);
@Query("select p from Product p join p.v where v.name=?1 and p.status!='deleted'")
	List<Product> getByVendorName(String name);
@Query("select p from Product p join p.v where v.id=?1 and p.status!='deleted'")
List<Product> getAllProductOfVendor(int vid);
@Query("select p from Product p where p.status=?1 and p.status!='deleted'")
List<Product> findByStatus(String status);
@Query("select p from Product p join p.v vendor where p.status=?1 and vendor.user.username=?2 and p.status!='deleted'")
List<Product> findByStatusAndUsername(String status,String username);
@Query("select p from Product p join p.warehouse w where w.id=?1 and p.status!='deleted'")
List<Product> getByWarehouseId(int id);
@Query("select p from Product p join p.v where v.user.username=?1 and p.isOutOfStock=?2 and p.status!='deleted'")
List<Product> findOutOfStockProduct(String username,boolean value);
@Query("select p  from Product p join p.v vendor where vendor.user.username=?1 and p.status!='deleted'")
List<Product> findByVendorUsrname(String username);
@Query("select distinct p.c from Product p join p.v vendor where vendor.user.username=?1 and p.status!='deleted'")
List<Object[]> findCategorySoldByVendor(String username);
@Query("select p from Product p join p.v vendor where vendor.user.username=?1 and p.title like %?2% and p.status!='deleted'" )
List<Product> findProductByNameAndUsername(String username, String name);
@Query("select p  from Product p join p.v vendor where vendor.user.username=?1 and p.status!='deleted'")

Page<Product> findByVendorUsrnameAndPage(String username, Pageable pageAble);



}
