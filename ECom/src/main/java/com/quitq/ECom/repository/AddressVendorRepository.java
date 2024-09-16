package com.quitq.ECom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.AddressVendor;

public interface AddressVendorRepository extends JpaRepository<AddressVendor,Integer> {

	@Query("select av from AddressVendor av join av.address a join av.vendor v where a.id=?1 and v.id=?2")
	Optional<AddressVendor> findByAddressIdAndVendorId(int addressId, int vendorId);

}
