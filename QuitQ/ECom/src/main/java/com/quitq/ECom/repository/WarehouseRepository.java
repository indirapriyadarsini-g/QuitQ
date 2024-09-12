package com.quitq.ECom.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>{

		@Query("select w from Warehouse w where w.name = ?1")
		Warehouse getCategoryByCategoryname(String name);

}