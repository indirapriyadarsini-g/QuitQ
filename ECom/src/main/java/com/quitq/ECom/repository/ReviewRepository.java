package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
	@Query("select r from Review r where r.product.v.user.username=?1 and r.product.id=?2")
	List<Review> getAllReviewOfProduct(String username,int id);

	@Query("select r from Review r where r.product = ?1")
	List<Review> getReviewByProduct(Product product);

}
