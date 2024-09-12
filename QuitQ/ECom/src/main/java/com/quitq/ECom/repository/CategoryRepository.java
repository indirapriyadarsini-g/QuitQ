package com.quitq.ECom.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

		@Query("select c from Category c where c.categoryname = ?1")
		Category getCategoryByCategoryname(String categoryname);

}