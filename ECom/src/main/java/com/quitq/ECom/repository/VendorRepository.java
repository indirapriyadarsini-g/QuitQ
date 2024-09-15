package com.quitq.ECom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor,Integer> {
@Query("select v from Vendor v join v.user u where u.id=?1")
	Vendor findByUserId(int uid);

}
