package com.quitq.ECom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Vendor;

public interface VendorRepository extends JpaRepository<Vendor,Integer> {
@Query("select v from Vendor v join v.user u where u.id=?1")
	Vendor findByUserId(int uid);
@Query("select   v from AddressVendor av join av.address a join av.vendor v where a.city=?1")
List<Vendor> getListOfVendorInSameCity(String cityName);

}
