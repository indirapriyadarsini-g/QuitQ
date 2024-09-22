	package com.quitq.ECom.repository;
	
	import java.util.List;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.jpa.repository.Query;
	import org.springframework.stereotype.Repository;
	
	import com.quitq.ECom.model.Product;
	import com.quitq.ECom.model.Warehouse;
	import com.quitq.ECom.model.WarehouseManager;
	
	@Repository
	public interface WarehouseManagerRepository extends JpaRepository<WarehouseManager, Integer> {
		WarehouseManager findByWarehouse(Warehouse warehouse);
	
		@Query("SELECT p FROM Product p WHERE p NOT IN (SELECT w.products FROM Warehouse w WHERE w.id = ?1)")
		List<Product> findProductsNotInWarehouse(Integer warehouseId);
	
		@Query("SELECT p.quantity FROM Product p WHERE p.title = ?1")
		Integer fetchStockQtyByProductName(String productName);
	
		@Query("SELECT p FROM Product p WHERE p.quantity < ?1")
		List<Product> getStockLessThan(Integer threshold);
	
		@Query("SELECT p FROM Product p WHERE p.quantity = 0")
		List<Product> getIsZeroStock();
	
		@Query("SELECT p FROM Product p WHERE p.title = ?1")
		Product findByName(String name);
	
		@Query("SELECT p FROM Product p WHERE p.quantity < ?1")
		List<Product> findByQuantityLessThan(Integer threshold);
	

		@Query("SELECT p FROM Product p WHERE p.warehouse = ?1")
		List<Product> getProductsInWarehouse(Warehouse warehouse);
	
	}